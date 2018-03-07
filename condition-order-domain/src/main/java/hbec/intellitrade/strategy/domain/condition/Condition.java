package hbec.intellitrade.strategy.domain.condition;

import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.time.TimeCondition;

/**
 * 策略条件
 * <p>
 * 条件接受行情和时间条件作为输入，根据具体的条件返回交易信号
 * <p>
 * 条件分为行情条件{@link MarketCondition}和时间条件{@link TimeCondition}，可以进行组合
 * <p>
 * 条件可以是有状态的或是无状态的，比如价格条件是无状态的，基于基准价的条件是有状态的
 * <p>
 * 有状态条件还有一种是动态属性条件{@link DynamicCondition}，比如带有拐点结构的条件，其拐点极值是动态属性，具有变化快、短暂不一致的特点
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public interface Condition {
}
