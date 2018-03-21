package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

/**
 * 行情类条件门面（Facade），内部集成了延迟确认、偏差控制等装饰条件，对外开放动态条件、延迟确认次数查询与重置等功能
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public interface MarketConditionFacade extends MarketCondition, DynamicCondition, DelayConfirmCondition {
}
