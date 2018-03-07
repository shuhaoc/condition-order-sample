package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/9/3.
 */
public class NewStockPurchaseConditionDO implements ConditionDO {
    private String purchaseTime; // HH:mm:ss

    public NewStockPurchaseConditionDO() {
    }

    public NewStockPurchaseConditionDO(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("purchaseTime", purchaseTime)
                .toString();
    }
}
