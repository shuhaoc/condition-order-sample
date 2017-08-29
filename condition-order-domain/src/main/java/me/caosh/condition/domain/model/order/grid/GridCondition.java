package me.caosh.condition.domain.model.order.grid;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/26.
 */
public class GridCondition implements Condition, RealTimeMarketDriven {
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

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitGridCondition(this);
    }

    @Override
    public TradeSignal onRealTimeMarketUpdate(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        BigDecimal upwardTargetPrice = getBasePrice().add(gridLength);
        BigDecimal downwardTargetPrice = getBasePrice().subtract(gridLength);
        logger.info("Check grid condition, basePrice={}, upwardTargetPrice={}, downwardTargetPrice={}, currentPrice={}",
                basePrice, upwardTargetPrice, downwardTargetPrice, realTimeMarket.getCurrentPrice());
        if (currentPrice.compareTo(upwardTargetPrice) >= 0) {
            return SignalFactory.getInstance().sell();
        } else if (currentPrice.compareTo(downwardTargetPrice) <= 0) {
            return SignalFactory.getInstance().buy();
        }
        return SignalFactory.getInstance().none();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gridLength", gridLength)
                .add("basePrice", basePrice)
                .toString();
    }
}
