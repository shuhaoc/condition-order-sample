package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * Created by caosh on 2017/9/3.
 */
public class NewStockPurchaseConditionDTO implements ConditionDTO {
    private String purchaseTime; // HH:mm:ss
    private int purchasedCount;
    private Date lastTriggerDate;

    public NewStockPurchaseConditionDTO() {
    }

    public NewStockPurchaseConditionDTO(String purchaseTime, int purchasedCount, Date lastTriggerDate) {
        this.purchaseTime = purchaseTime;
        this.purchasedCount = purchasedCount;
        this.lastTriggerDate = lastTriggerDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
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
                .add("purchaseTime", purchaseTime)
                .add("purchasedCount", purchasedCount)
                .add("lastTriggerDate", lastTriggerDate)
                .toString();
    }
}
