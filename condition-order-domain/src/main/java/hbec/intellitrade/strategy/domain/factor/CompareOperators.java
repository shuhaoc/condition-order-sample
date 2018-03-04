package hbec.intellitrade.strategy.domain.factor;

import static hbec.intellitrade.strategy.domain.factor.CompareOperator.*;

/**
 * {@link CompareOperator}相关工具类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class CompareOperators {
    /**
     * 求比较操作的反转操作符，例如“>=”的反转是“<=”
     *
     * @param op 操作符
     * @return 反转操作符
     */
    public static CompareOperator reverse(CompareOperator op) {
        switch (op) {
            case GE:
                return LE;
            case LE:
                return GE;
            case GT:
                return LT;
            case LT:
                return GT;
            default:
                throw new IllegalArgumentException("Illegal compare operator: " + op);
        }
    }

    /**
     * 返回操作符的无等于条件的操作符，例如大于等于返回大于，大于仍然返回大于
     *
     * @param op 操作符
     * @return 无等于条件的操作符
     */
    public static CompareOperator nonEquals(CompareOperator op) {
        switch (op) {
            case GE:
                return GT;
            case LE:
                return LT;
            default:
                return op;
        }
    }

    private CompareOperators() {
    }
}
