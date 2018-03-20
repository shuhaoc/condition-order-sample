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
public interface DynamicCondition extends DirtyFlag {
    /**
     * 判断this条件在覆盖更新origin条件之前，是否需要与origin对象交换动态属性
     *
     * @param origin 被更新对象
     * @return 是否需要交换
     */
//    boolean isNeedSwap(DynamicCondition origin);

    /**
     * 覆盖更新条件参数时交换动态属性
     *
     * @param origin 被更新对象
     */
//    void swap(DynamicCondition origin);
}
