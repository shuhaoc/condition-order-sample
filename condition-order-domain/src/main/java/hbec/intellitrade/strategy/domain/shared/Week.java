package hbec.intellitrade.strategy.domain.shared;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 星期
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public enum Week implements ConvertibleEnum<Integer> {
    /**
     * 星期一
     */
    MON(1),

    /**
     * 星期二
     */
    TUE(2),

    /**
     * 星期三
     */
    WED(3),

    /**
     * 星期四
     */
    THU(4),

    /**
     * 星期五
     */
    FRI(5),

    /**
     * 星期六
     */
    SAT(6),

    /**
     * 星期日
     */
    SUN(7);

    int value;

    Week(int value) {
        this.value = value;
    }

    public int getDayOfWeek() {
        return value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
