package hbec.intellitrade.conditionorder.domain.orders.newstock;

import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public class NewStockPurchaseConditionBuilder implements ConvertibleBuilder<NewStockPurchaseCondition> {
    private LocalTime purchaseTime;
    private boolean todayTriggered;
    private int purchasedCount;

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public boolean isTodayTriggered() {
        return todayTriggered;
    }

    public void setTodayTriggered(boolean todayTriggered) {
        this.todayTriggered = todayTriggered;
    }

    public int getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(int purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    @Override
    public NewStockPurchaseCondition build() {
        return new NewStockPurchaseCondition(purchaseTime, todayTriggered, purchasedCount);
    }
}
