package me.caosh.condition.domain.model.strategy.factor;

import java.math.BigDecimal;

/**
 * 价格因子，判断给定的price是否满足this所代表的价格因子
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public interface PriceFactor extends Factor<BigDecimal> {
}
