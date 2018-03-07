package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.MappedClass;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;

import java.util.Date;

/**
 * Created by caosh on 2017/9/3.
 */
@MappedClass(NewStockPurchaseCondition.class)
public class NewStockPurchaseDynamicPropertiesDO implements DynamicPropertiesDO {
    private int purchasedCount;
    private Date lastTriggerDate;

    public NewStockPurchaseDynamicPropertiesDO() {
    }

    public NewStockPurchaseDynamicPropertiesDO(int purchasedCount, Date lastTriggerDate) {
        this.purchasedCount = purchasedCount;
        this.lastTriggerDate = lastTriggerDate;
    }

    public int getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(int purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    public Date getLastTriggerDate() {
        return lastTriggerDate;
    }

    public void setLastTriggerDate(Date lastTriggerDate) {
        this.lastTriggerDate = lastTriggerDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("purchasedCount", purchasedCount)
                .add("lastTriggerDate", lastTriggerDate)
                .toString();
    }
}
