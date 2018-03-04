package hbec.intellitrade.strategy.domain.factor;

import me.caosh.autoasm.ConvertibleEnum;
import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * 函数化的比较操作符
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
@SuppressWarnings("unchecked")
public enum CompareOperator implements BinaryFactor<Comparable>, ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    /**
     * 小于等于
     */
    LE(0, "<=") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) <= 0;
        }
    },

    /**
     * 大于等于
     */
    GE(1, ">=") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) >= 0;
        }
    },

    LT(3, "<") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) < 0;
        }
    },

    GT(4, ">") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) > 0;
        }
    };

    /**
     * int枚举值
     */
    int value;

    /**
     * 数学符合，用于日志打印
     */
    String mathSign;

    CompareOperator(int value, String mathSign) {
        this.value = value;
        this.mathSign = mathSign;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getMathSign() {
        return mathSign;
    }
}
