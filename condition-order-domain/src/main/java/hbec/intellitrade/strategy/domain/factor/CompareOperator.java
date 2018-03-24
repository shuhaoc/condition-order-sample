package hbec.intellitrade.strategy.domain.factor;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 函数化的比较操作符
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
@SuppressWarnings("unchecked")
public enum CompareOperator implements BinaryFactor<Comparable>, ConvertibleEnum<Integer> {
    /**
     * 小于等于
     */
    LE(0, NumericalDirection.LESS, "<=") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) <= 0;
        }

        @Override
        public CompareOperator reverse() {
            return GE;
        }

        @Override
        public CompareOperator withoutEquals() {
            return LT;
        }
    },

    /**
     * 大于等于
     */
    GE(1, NumericalDirection.GREATER, ">=") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) >= 0;
        }

        @Override
        public CompareOperator reverse() {
            return LE;
        }

        @Override
        public CompareOperator withoutEquals() {
            return GT;
        }
    },

    LT(3, NumericalDirection.LESS, "<") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) < 0;
        }

        @Override
        public CompareOperator reverse() {
            return GT;
        }

        @Override
        public CompareOperator withoutEquals() {
            return this;
        }
    },

    GT(4, NumericalDirection.GREATER, ">") {
        @Override
        public boolean apply(Comparable a, Comparable b) {
            return a.compareTo(b) > 0;
        }

        @Override
        public CompareOperator reverse() {
            return LT;
        }

        @Override
        public CompareOperator withoutEquals() {
            return this;
        }
    };

    /**
     * int枚举值
     */
    int value;

    /**
     * 对应的数值变化方向
     */
    NumericalDirection numericalDirection;

    /**
     * 数学符合，用于日志打印
     */
    String mathSign;

    CompareOperator(int value, NumericalDirection numericalDirection, String mathSign) {
        this.value = value;
        this.numericalDirection = numericalDirection;
        this.mathSign = mathSign;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public NumericalDirection getNumericalDirection() {
        return numericalDirection;
    }

    public String getMathSign() {
        return mathSign;
    }

    /**
     * 求比较操作的反转操作符，例如“>=”的反转是“<=”
     *
     * @return 反转操作符
     */
    public abstract CompareOperator reverse();

    /**
     * 返回操作符的无等于条件的操作符，例如大于等于返回大于，大于仍然返回大于
     *
     * @return 无等于条件的操作符
     */
    public abstract CompareOperator withoutEquals();
}
