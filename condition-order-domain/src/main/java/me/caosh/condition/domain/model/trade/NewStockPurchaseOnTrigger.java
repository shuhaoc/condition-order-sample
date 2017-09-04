package me.caosh.condition.domain.model.trade;

import me.caosh.condition.domain.model.newstock.NewStock;

import java.util.List;

/**
 * Created by caosh on 2017/8/29.
 */
public interface NewStockPurchaseOnTrigger {
    List<EntrustCommand> createEntrustCommand(List<NewStock> currentPurchasable);
}
