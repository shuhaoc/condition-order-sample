package hbec.commons.domain.intellitrade.condition;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockPurchaseCondition;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockPurchaseConditionBuilder;
import me.caosh.autoasm.MappedClass;

/**
 * Created by caosh on 2017/9/3.
 */
@MappedClass(value = NewStockPurchaseCondition.class, builderClass = NewStockPurchaseConditionBuilder.class)
public class NewStockPurchaseConditionDTO implements ConditionDTO {
    /**
     * HH:mm:ss
     */
    private String purchaseTime;
    private Boolean todayTriggered;
    private Integer purchasedCount;

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Boolean getTodayTriggered() {
        return todayTriggered;
    }

    public void setTodayTriggered(Boolean todayTriggered) {
        this.todayTriggered = todayTriggered;
    }

    public Integer getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(Integer purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStockPurchaseConditionDTO.class).omitNullValues()
                .add("purchaseTime", purchaseTime)
                .add("todayTriggered", todayTriggered)
                .add("purchasedCount", purchasedCount)
                .toString();
    }
}
