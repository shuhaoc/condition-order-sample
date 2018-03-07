package me.caosh.condition.domain.model.order.newstock;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.ConditionVisitor;
import hbec.intellitrade.strategy.domain.condition.AbstractBasicTimeCondition;
import hbec.intellitrade.strategy.domain.factor.DailyTargetTimeFactor;
import hbec.intellitrade.strategy.domain.factor.TimeFactor;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockPurchaseCondition extends AbstractBasicTimeCondition {
    private int purchasedCount;
    private DailyTargetTimeFactor timeFactor;

    public NewStockPurchaseCondition(LocalTime purchaseTime) {
        this.purchasedCount = 0;
        this.timeFactor = new DailyTargetTimeFactor(purchaseTime);
    }

    public NewStockPurchaseCondition(LocalTime purchaseTime, Integer purchasedCount, LocalDate lastTriggerDate) {
        this.purchasedCount = MoreObjects.firstNonNull(purchasedCount, 0);
        this.timeFactor = new DailyTargetTimeFactor(purchaseTime, lastTriggerDate);
    }

    public LocalTime getPurchaseTime() {
        return timeFactor.getTargetTime();
    }

    public int getPurchasedCount() {
        return purchasedCount;
    }

    void increasePurchasedCount() {
        this.purchasedCount++;
    }

    public Optional<LocalDate> getLastTriggerDate() {
        return timeFactor.getLastTriggerDate();
    }

    void setTriggeredToday() {
        timeFactor = timeFactor.setTodayTriggered();
    }

    @Override
    public TimeFactor getTimeFactor() {
        return timeFactor;
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitNewStockPurchaseCondition(this);
    }
}
