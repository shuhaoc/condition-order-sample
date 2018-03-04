package hbec.intellitrade.strategy.domain.factor;

import org.joda.time.LocalDateTime;

/**
 * 时间因子，判断给定的某个时间点是否满足this所代表的时间因子
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public interface TimeFactor extends Factor<LocalDateTime> {
}
