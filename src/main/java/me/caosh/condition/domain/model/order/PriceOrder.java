package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/1.
 */
public class PriceOrder extends ConditionOrder implements MarketTickListener {
    private static final Logger logger = LoggerFactory.getLogger(PriceOrder.class);

    public PriceOrder(Long orderId, OrderState orderState, SecurityInfo securityInfo, SimplePriceCondition simplePriceCondition,
                      TradePlan tradePlan, LocalDateTime createTime, LocalDateTime updateTime) {
        super(orderId, orderState, securityInfo, simplePriceCondition, tradePlan, createTime, updateTime);
    }

    public SimplePriceCondition getSimplePriceCondition() {
        return (SimplePriceCondition) getCondition();
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        logger.debug("Check price condition, currentPrice={}, condition={}", currentPrice, getCondition());
        if (getSimplePriceCondition().isSatisfiedBy(currentPrice)) {
            return SignalFactory.getInstance().toBeDetermined();
        }
        return SignalFactory.getInstance().none();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .toString();
    }
}
