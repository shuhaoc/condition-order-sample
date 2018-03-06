package me.caosh.condition.domain.model.order.grid;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.market.MarketCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/26.
 */
public class GridCondition implements MarketCondition {
    private static final Logger logger = LoggerFactory.getLogger(GridCondition.class);

    private final BigDecimal gridLength;
    private BigDecimal basePrice;

    public GridCondition(BigDecimal gridLength) {
        this.gridLength = gridLength;
    }

    public GridCondition(BigDecimal gridLength, BigDecimal basePrice) {
        this.gridLength = gridLength;
        this.basePrice = basePrice;
    }

    public BigDecimal getGridLength() {
        return gridLength;
    }

    public BigDecimal getBasePrice() {
        return Preconditions.checkNotNull(basePrice);
    }

    void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitGridCondition(this);
    }

    @Override
    public TradeSignal onMarketUpdate(RealTimeMarket realTimeMarket) {
        Preconditions.checkNotNull(basePrice);

        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        BigDecimal upwardTargetPrice = basePrice.add(gridLength);
        BigDecimal downwardTargetPrice = basePrice.subtract(gridLength);
        logger.info("Check grid condition, basePrice={}, upwardTargetPrice={}, downwardTargetPrice={}, currentPrice={}",
                basePrice, upwardTargetPrice, downwardTargetPrice, realTimeMarket.getCurrentPrice());
        if (currentPrice.compareTo(upwardTargetPrice) >= 0) {
            return Signals.sell();
        } else if (currentPrice.compareTo(downwardTargetPrice) <= 0) {
            return Signals.buy();
        }
        return Signals.none();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gridLength", gridLength)
                .add("basePrice", basePrice)
                .toString();
    }
}
