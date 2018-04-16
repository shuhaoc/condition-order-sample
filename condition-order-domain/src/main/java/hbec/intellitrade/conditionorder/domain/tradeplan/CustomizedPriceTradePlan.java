package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.math.BigDecimal;
import java.util.Objects;

import static hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy.CUSTOMIZED_PRICE;

/**
 * 自定义价格交易计划
 * <p>
 * 其委托策略为{@link EntrustStrategy#CUSTOMIZED_PRICE}，订单类别为限价单
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/24
 */
public class CustomizedPriceTradePlan extends BaseTradePlan {
    private final BigDecimal entrustPrice;

    /**
     * 构造交易计划，其中委托策略为{@link EntrustStrategy#CUSTOMIZED_PRICE}，订单类别为限价单
     *
     * @param exchangeType 交易类别
     * @param entrustPrice 委托价格
     * @param tradeNumber  交易数量
     */
    public CustomizedPriceTradePlan(ExchangeType exchangeType,
                                    BigDecimal entrustPrice,
                                    TradeNumber tradeNumber) {
        super(exchangeType, tradeNumber);
        Preconditions.checkNotNull(entrustPrice, "entrustPrice cannot be null");
        this.entrustPrice = entrustPrice;
    }

    @Override
    public EntrustStrategy getEntrustStrategy() {
        return CUSTOMIZED_PRICE;
    }

    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.LIMITED;
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
                                               SecurityInfo securityInfo,
                                               TradingMarketSupplier tradingMarketSupplier) {
        return new EntrustCommand(securityInfo,
                                  getExchangeType(),
                                  entrustPrice,
                                  getTradeNumber().getNumber(securityInfo, entrustPrice),
                                  OrderType.LIMITED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CustomizedPriceTradePlan that = (CustomizedPriceTradePlan) o;
        return Objects.equals(entrustPrice, that.entrustPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), entrustPrice);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(CustomizedPriceTradePlan.class).omitNullValues()
                          .add("exchangeType", getExchangeType())
                          .add("entrustPrice", entrustPrice)
                          .add("tradeNumber", getTradeNumber())
                          .toString();
    }
}
