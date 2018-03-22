package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

/**
 * 行情类条件包装类（Decorator模式），内部集成了延迟确认、偏差控制等装饰条件，对外开放动态条件、延迟确认次数查询与重置等功能
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/22
 */
public abstract class DecoratedMarketCondition<T extends MarketCondition>
        implements MarketCondition, DelayConfirmCondition, DynamicCondition {
}
