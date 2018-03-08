package me.caosh.condition.domain.service;

import me.caosh.condition.domain.model.newstock.NewStock;

import java.util.List;

/**
 * Created by caosh on 2017/9/4.
 *
 * @author caoshuhao@touker.com
 */
public interface NewStocksSupplier {
    List<NewStock> getCurrentPurchasable();
}
