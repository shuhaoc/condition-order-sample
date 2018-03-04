package hbec.intellitrade.strategy.domain.factor;

/**
 * 条件因子
 * <p>
 * 因子是一元函数，给定一个入参，返回布尔值
 * <p>
 * 因子的实现一般会带有参数绑定，可以是有状态或无状态的实现
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public interface Factor<T> {
    /**
     * 判断给定的入参是否满足this所代表的条件因子
     *
     * @param input 输入参数
     * @return 是否满足条件因子
     */
    boolean apply(T input);
}
