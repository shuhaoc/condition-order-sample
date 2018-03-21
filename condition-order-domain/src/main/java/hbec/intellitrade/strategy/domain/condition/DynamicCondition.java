package hbec.intellitrade.strategy.domain.condition;

import hbec.intellitrade.strategy.domain.shared.DirtyFlag;

/**
 * 动态属性条件，属于有状态条件的一种
 * <p>
 * 动态属性是指随着条件输入变化，变化频率可能比较高的属性，具有短暂不一致的特点
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public interface DynamicCondition extends Condition, DirtyFlag {
}
