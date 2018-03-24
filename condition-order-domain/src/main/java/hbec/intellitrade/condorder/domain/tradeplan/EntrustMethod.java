package hbec.intellitrade.condorder.domain.tradeplan;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 委托方式
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/18
 */
public enum EntrustMethod implements ConvertibleEnum<Integer> {
    /**
     * 数量下单
     */
    NUMBER(0),

    /**
     * 金额下单
     */
    AMOUNT(1);

    int value;

    EntrustMethod(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
