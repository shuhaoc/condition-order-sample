package me.caosh.condition.domain.model.order.newstock;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.TimeCondition;
import me.caosh.condition.domain.model.order.shared.DynamicProperty;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public NewStockPurchaseCondition(LocalTime purchaseTime, Integer purchasedCount) {
        this.purchaseTime = purchaseTime;
        this.purchasedCount =MoreObjects.firstNonNull(purchasedCount, 0);
    }



    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitNewStockPurchaseCondition(this);
    }
}
