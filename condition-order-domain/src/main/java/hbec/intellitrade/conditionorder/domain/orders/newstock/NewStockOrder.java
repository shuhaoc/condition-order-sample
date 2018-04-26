package hbec.intellitrade.conditionorder.domain.orders.newstock;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.common.security.newstock.NewStock;
import hbec.intellitrade.common.security.newstock.NewStocksSupplier;
import hbec.intellitrade.conditionorder.domain.AbstractConditionOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.conditionorder.domain.trigger.AutoPurchaseContext;
import hbec.intellitrade.conditionorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustSuccessResult;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * 自动打新条件单
 * <p>
 * Created by caosh on 2017/8/31.
 * <p>
 * 自动打新条件单由于监管原因已经下线，此类实现仅用于演示单次触发多笔委托模型
 *
 * @author caoshuhao@touker.com
 */
public class NewStockOrder extends AbstractConditionOrder implements TimeDrivenStrategy {
    private static final Logger logger = LoggerFactory.getLogger(NewStockOrder.class);

    private final NewStockPurchaseCondition newStockPurchaseCondition;

    public NewStockOrder(Long orderId,
            TradeCustomerInfo tradeCustomerInfo,
            OrderState orderState, NewStockPurchaseCondition newStockPurchaseCondition,
            LocalDateTime expireTime) {
        super(orderId, tradeCustomerInfo, orderState, expireTime);
        this.newStockPurchaseCondition = newStockPurchaseCondition;
    }

    @Override
    public NewStockPurchaseCondition getCondition() {
        return newStockPurchaseCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.NEW_STOCK;
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        if (isMonitoringState() && isExpiredAt(localDateTime)) {
            return Signals.expire();
        }

        if (getOrderState() != OrderState.ACTIVE) {
            return Signals.none();
        }

        boolean satisfiedAt = getCondition().getTimeFactor().apply(localDateTime);
        if (satisfiedAt) {
            return Signals.autoPurchase();
        }
        return Signals.none();
    }

    @Override
    public void onTradeSignal(TriggerTradingContext triggerTradingContext) {
        onAutoPurchase((AutoPurchaseContext) triggerTradingContext);
    }

    private void onAutoPurchase(AutoPurchaseContext autoPurchaseContext) {
        NewStocksSupplier newStocksSupplier = autoPurchaseContext.getNewStocksSupplier();
        List<NewStock> currentPurchasable = newStocksSupplier.getCurrentPurchasable();
        logger.info("Current purchasables: {}", currentPurchasable);

        for (NewStock newStock : currentPurchasable) {
            EntrustCommand entrustCommand = createPurchaseCommand(newStock);
            executeEntrustCommand(autoPurchaseContext, entrustCommand);
        }

        newStockPurchaseCondition.setTriggeredToday();
        logger.info("Set triggered today, orderId={}", getOrderId());
    }

    private EntrustCommand createPurchaseCommand(NewStock newStock) {
        SecurityInfo securityInfo = new SecurityInfo(SecurityType.STOCK, newStock.getPurchaseCode(),
                newStock.getSecurityExchange(), newStock.getPurchaseName());
        return new EntrustCommand(securityInfo, ExchangeType.QUOTA_PURCHASE,
                newStock.getIssuePrice(), newStock.getPurchaseUpperLimit(), OrderType.LIMITED);
    }

    @Override
    public void afterEntrustSuccess(TriggerTradingContext triggerTradingContext,
            EntrustCommand entrustCommand,
            EntrustSuccessResult entrustSuccessResult) {
        newStockPurchaseCondition.increasePurchasedCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        NewStockOrder that = (NewStockOrder) o;
        return Objects.equals(newStockPurchaseCondition, that.newStockPurchaseCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), newStockPurchaseCondition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStockOrder.class).omitNullValues()
                .add("orderId", getOrderId())
                .add("customer", getCustomer())
                .add("orderState", getOrderState())
                .add("newStockPurchaseCondition", newStockPurchaseCondition)
                .add("expireTime", getExpireTime())
                .toString();
    }
}
