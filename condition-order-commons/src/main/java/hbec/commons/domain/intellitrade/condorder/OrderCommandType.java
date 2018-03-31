package hbec.commons.domain.intellitrade.condorder;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/13
 */
public enum OrderCommandType implements ConvertibleEnum<Integer> {
    /**
     * 保存或更新
     * <p>
     * 新建、修改条件单后状态为监控中状态的，发送此类型指令
     */
    SAVE(1),

    /**
     * 删除
     * <p>
     * 条件单状态变更为终止状态，包括正常终止异常终止，不再为监控中状态的，发送此类型指令
     */
    REMOVE(2);

    int value;

    OrderCommandType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
