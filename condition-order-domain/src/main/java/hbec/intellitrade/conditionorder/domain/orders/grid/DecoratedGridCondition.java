package hbec.intellitrade.conditionorder.domain.orders.grid;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.conditionorder.domain.orders.DecoratedMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public class DecoratedGridCondition implements MarketCondition, DynamicCondition {

    /**
     * 基准价
     */
    private BigDecimal basePrice;

    private DecoratedMarketCondition<? extends GridSubCondition> buyCondition;
    private DecoratedMarketCondition<? extends GridSubCondition> sellCondition;

    private final BinaryFactorType binaryFactorType;
    private final boolean useGuaranteedPrice;
    private final DelayConfirm delayConfirm;
    private final DeviationCtrl deviationCtrl;

    public DecoratedGridCondition(BigDecimal basePrice,
            BinaryFactorType binaryFactorType,
            GridSubCondition rawBuyCondition,
            GridSubCondition rawSellCondition,
            boolean useGuaranteedPrice,
            DelayConfirm delayConfirm,
            DeviationCtrl deviationCtrl,
            int buyDelayConfirmedCount,
            int sellDelayConfirmedCount) {
        this.basePrice = basePrice;
        this.binaryFactorType = binaryFactorType;
        this.buyCondition = new DecoratedMarketCondition<>(rawBuyCondition,
                delayConfirm,
                deviationCtrl,
                buyDelayConfirmedCount);
        this.sellCondition = new DecoratedMarketCondition<>(rawSellCondition,
                delayConfirm,
                deviationCtrl,
                sellDelayConfirmedCount);
        this.useGuaranteedPrice = useGuaranteedPrice;
        this.delayConfirm = delayConfirm;
        this.deviationCtrl = deviationCtrl;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    void changeBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;

        GridSubCondition rawBuyCondition = buyCondition.getRawCondition().changeBasePrice(basePrice);
        this.buyCondition = new DecoratedMarketCondition<>(rawBuyCondition,
                delayConfirm, deviationCtrl, 0);

        GridSubCondition rawSellCondition = sellCondition.getRawCondition().changeBasePrice(basePrice);
        this.sellCondition = new DecoratedMarketCondition<>(rawSellCondition,
                delayConfirm, deviationCtrl, 0);
    }

    DecoratedMarketCondition<? extends GridSubCondition> getBuyCondition() {
        return buyCondition;
    }

    DecoratedMarketCondition<? extends GridSubCondition> getSellCondition() {
        return sellCondition;
    }

    public BinaryFactorType getBinaryFactorType() {
        return binaryFactorType;
    }

    public boolean isUseGuaranteedPrice() {
        return useGuaranteedPrice;
    }

    public DelayConfirm getDelayConfirm() {
        return delayConfirm;
    }

    public DeviationCtrl getDeviationCtrl() {
        return deviationCtrl;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        TradeSignal buySignal = buyCondition.onMarketTick(realTimeMarket);
        if (buySignal.isValid()) {
            return Signals.buy(buySignal.getDeviationExceeded());
        }

        TradeSignal sellSignal = sellCondition.onMarketTick(realTimeMarket);
        if (sellSignal.isValid()) {
            return Signals.sell(sellSignal.getDeviationExceeded());
        }

        return Signals.none();
    }

    @Override
    public boolean isDirty() {
        return buyCondition.isDirty() || sellCondition.isDirty();
    }

    @Override
    public void clearDirty() {
        buyCondition.clearDirty();
        sellCondition.clearDirty();
    }

    public void resetCounter() {
        buyCondition.resetCounter();
        sellCondition.resetCounter();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        DecoratedGridCondition that = (DecoratedGridCondition) o;
        return Objects.equals(buyCondition, that.buyCondition) &&
                Objects.equals(sellCondition, that.sellCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyCondition, sellCondition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DecoratedGridCondition.class).omitNullValues()
                .add("basePrice", basePrice)
                .add("buyCondition", buyCondition)
                .add("sellCondition", sellCondition)
                .toString();
    }
}
