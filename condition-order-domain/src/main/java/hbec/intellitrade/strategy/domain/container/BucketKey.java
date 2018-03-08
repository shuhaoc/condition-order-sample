package hbec.intellitrade.strategy.domain.container;

/**
 * 策略bucket key
 * <p>
 * bucket key是用在同一个container中hash key
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public interface BucketKey {
    /**
     * 要求子类实现equals方法
     *
     * @param that 其他对象
     * @return 是否equals
     */
    @Override
    boolean equals(Object that);

    /**
     * 要求子类实现hashCode方法，返回的hash code用于container的hash key
     *
     * @return hash code
     */
    @Override
    int hashCode();
}
