package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.DynamicCondition;
import me.caosh.condition.domain.model.order.SimpleMarketCondition;
import me.caosh.condition.domain.model.order.shared.DynamicProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpCondition implements SimpleMarketCondition, DynamicCondition {
    private static final Logger logger = LoggerFactory.getLogger(TurnUpCondition.class);

    private final BigDecimal breakPrice;
    private final BigDecimal turnUpPercent;

    private DynamicProperty<Boolean> broken;
    private DynamicProperty<BigDecimal> lowestPrice;

    public TurnUpCondition(BigDecimal breakPrice, BigDecimal turnUpPercent) {
        this.breakPrice = breakPrice;
        this.turnUpPercent = turnUpPercent;
        this.broken = new DynamicProperty<>(false);
        this.lowestPrice = new DynamicProperty<>();
    }

    public TurnUpCondition(BigDecimal breakPrice, BigDecimal turnUpPercent, Boolean broken, BigDecimal lowestPrice) {
        this.breakPrice = breakPrice;
        this.turnUpPercent = turnUpPercent;
        this.broken = new DynamicProperty<>(broken);
        this.lowestPrice = new DynamicProperty<>(lowestPrice);
    }

    public BigDecimal getBreakPrice() {
        return breakPrice;
    }

    public BigDecimal getTurnUpPercent() {
        return turnUpPercent;
    }

    public Boolean getBroken() {
        return broken.get();
    }

    public Optional<BigDecimal> getLowestPrice() {
        return Optional.ofNullable(lowestPrice.get());
    }

    @Override
    public boolean isDirty() {
        return Iterables.any(Lists.newArrayList(broken, lowestPrice), DynamicProperty::isDirty);
    }

    @Override
    public void clearDirty() {
        Lists.newArrayList(broken, lowestPrice).forEach(DynamicProperty::clearDirty);
    }

    @Override
    public boolean isSatisfiedBy(BigDecimal price) {
        if (!broken.get()) {
            if (price.compareTo(breakPrice) <= 0) {
                broken.set(true);
                lowestPrice.set(price);
                logger.info("Price falls down and breaks the break-price, breakPrice={}, price={}", breakPrice, price);
            }
        } else {
            if (price.compareTo(lowestPrice.get()) < 0) {
                logger.info("Update the lowest price, lowestPrice={}, price={}", lowestPrice, price);
                lowestPrice.set(price);
            } else {
                BigDecimal turnUpTargetPrice = lowestPrice.get().multiply(
                        BigDecimal.ONE.add(
                                turnUpPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.UP)));
                logger.info("Check turn up condition, turnUpTargetPrice={}, price={}", turnUpTargetPrice, price);
                return price.compareTo(turnUpTargetPrice) >= 0;
            }
        }
        return false;
    }

    @Override
    public void swap(DynamicCondition another) {
        TurnUpCondition that = (TurnUpCondition) another;
        this.broken = new DynamicProperty<>(that.getBroken());
        this.lowestPrice = new DynamicProperty<>(that.getLowestPrice().orElse(null));
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitTurnUpCondition(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnUpCondition)) return false;

        TurnUpCondition that = (TurnUpCondition) o;

        if (breakPrice != null ? !breakPrice.equals(that.breakPrice) : that.breakPrice != null) return false;
        if (turnUpPercent != null ? !turnUpPercent.equals(that.turnUpPercent) : that.turnUpPercent != null)
            return false;
        if (broken != null ? !broken.equals(that.broken) : that.broken != null) return false;
        return !(lowestPrice != null ? !lowestPrice.equals(that.lowestPrice) : that.lowestPrice != null);

    }

    @Override
    public int hashCode() {
        int result = breakPrice != null ? breakPrice.hashCode() : 0;
        result = 31 * result + (turnUpPercent != null ? turnUpPercent.hashCode() : 0);
        result = 31 * result + (broken != null ? broken.hashCode() : 0);
        result = 31 * result + (lowestPrice != null ? lowestPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("breakPrice", breakPrice)
                .add("turnUpPercent", turnUpPercent)
                .add("broken", broken)
                .add("lowestPrice", lowestPrice)
                .toString();
    }
}
