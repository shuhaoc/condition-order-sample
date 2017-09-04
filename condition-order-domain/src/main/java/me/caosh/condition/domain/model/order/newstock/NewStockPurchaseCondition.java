package me.caosh.condition.domain.model.order.newstock;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.TimeCondition;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockPurchaseCondition implements TimeCondition {

    private final LocalTime purchaseTime;
    private int purchasedCount;
    private LocalDate lastTriggerDate;

    public NewStockPurchaseCondition(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
        this.purchasedCount = 0;
    }

    public NewStockPurchaseCondition(LocalTime purchaseTime, Integer purchasedCount, LocalDate lastTriggerDate) {
        this.purchaseTime = purchaseTime;
        this.purchasedCount = MoreObjects.firstNonNull(purchasedCount, 0);
        this.lastTriggerDate = lastTriggerDate;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public int getPurchasedCount() {
        return purchasedCount;
    }

    void increasePurchasedCount() {
        this.purchasedCount++;
    }

    public Optional<LocalDate> getLastTriggerDate() {
        return Optional.ofNullable(lastTriggerDate);
    }

    private boolean isTriggeredToday() {
        return lastTriggerDate != null && LocalDate.now().equals(lastTriggerDate);
    }

    void setTriggeredToday() {
        this.lastTriggerDate = LocalDate.now();
    }

    @Override
    public boolean isSatisfiedNow() {
        if (isTriggeredToday()) {
            return false;
        }
        LocalTime now = LocalTime.now();
        return now.equals(purchaseTime) || now.isAfter(purchaseTime);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitNewStockPurchaseCondition(this);
    }
}
