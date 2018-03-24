package hbec.intellitrade.trade.domain;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 订单类别
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/14
 */
public enum OrderType implements ConvertibleEnum<Integer> {

    /**
     * 限价单
     */
    LIMITED(0),

    /**
     * （上海）五档即时成交剩余撤消
     */
    SH_WDJSCJSYCX(1),

    /**
     * （上海）五档即时成交剩余转限
     */
    SH_WDJSCJSYZX(2),

    /**
     * （深圳）对手方最优
     */
    SZ_DSFZY(101),

    /**
     * （深圳）本方最优
     */
    SZ_BFZY(102),

    /**
     * （深圳）即时成交剩余撤消
     */
    SZ_JSCJSYCX(103),

    /**
     * （深圳）五档即时成交撤消
     */
    SZ_WDJSCJSYCX(104),

    /**
     * （深圳）全额成交或撤消
     */
    SZ_QECJHCX(105);

    int value;

    OrderType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
