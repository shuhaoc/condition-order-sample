package hbec.intellitrade.strategy.domain.factor;

/**
 * 二元条件因子
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/28
 */
public interface BinaryFactor<T> {
    /**
     * 判断参数是否符合条件
     *
     * @param a 参数1
     * @param b 参数2
     * @return 是否符合条件
     */
    boolean apply(T a, T b);
}
