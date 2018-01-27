package me.caosh.condition.domain.model.strategy.shared;


import com.google.common.base.Function;

/**
 * {@link Range}工具类
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/29
 */
public class Ranges {
    /**
     * 转换{@link Range}
     *
     * @param source   源Range
     * @param function 转换函数
     * @param <S>      源类型
     * @param <T>      目标类型
     * @return 目标Range
     */
    public static <S extends Comparable<? super S>, T extends Comparable<? super T>>
    Range<T> transform(Range<S> source, Function<S, T> function) {
        T t1 = function.apply(source.getLeft());
        T t2 = function.apply(source.getRight());
        return new Range<>(t1, t2, source.getLeftOperator(), source.getRightOperator());
    }

    private Ranges() {
    }
}
