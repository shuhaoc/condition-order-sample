package hbec.intellitrade.conditionorder.domain.orders.newstock;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.condition.AbstractBasicTimeCondition;
import hbec.intellitrade.strategy.domain.factor.DailyTargetTimeFactor;
import hbec.intellitrade.strategy.domain.factor.TimeFactor;
import org.joda.time.LocalTime;

import java.util.Objects;


/**
 * 自动申购时间条件
 * <p>
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class NewStockPurchaseCondition extends AbstractBasicTimeCondition {
    private DailyTargetTimeFactor timeFactor;
    private int purchasedCount;

    public NewStockPurchaseCondition(LocalTime purchaseTime) {
        this.timeFactor = new DailyTargetTimeFactor(purchaseTime);
        this.purchasedCount = 0;
    }

    public NewStockPurchaseCondition(LocalTime purchaseTime, boolean todayTriggered, int purchasedCount) {
        this.timeFactor = new DailyTargetTimeFactor(purchaseTime, todayTriggered);
        this.purchasedCount = purchasedCount;
    }

    public LocalTime getPurchaseTime() {
        return timeFactor.getTargetTime();
    }

    public boolean isTodayTriggered() {
        return timeFactor.isTodayTriggered();
    }

    public int getPurchasedCount() {
        return purchasedCount;
    }

    void increasePurchasedCount() {
        this.purchasedCount++;
    }

    void setTriggeredToday() {
        timeFactor = timeFactor.setTodayTriggered();
    }

    @Override
    public TimeFactor getTimeFactor() {
        return timeFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        NewStockPurchaseCondition that = (NewStockPurchaseCondition) o;
        return purchasedCount == that.purchasedCount &&
                Objects.equals(timeFactor, that.timeFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeFactor, purchasedCount);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStockPurchaseCondition.class).omitNullValues()
                .add("timeFactor", timeFactor)
                .add("purchasedCount", purchasedCount)
                .toString();
    }
}
