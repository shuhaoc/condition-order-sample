package hbec.intellitrade.strategy.domain.signal;

/**
 * 交易信号，包括普通买卖信号或无信号
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/28
 */
public interface TradeSignal extends Signal {

    /**
     * 是否超出偏差限制
     *
     * @return 是否超出偏差限制
     */
    boolean getDeviationExceeded();

    /**
     * prototype模式
     * 基于this对象复制出deviationExceeded属性为true的对象
     *
     * @return 交易信号
     */
    TradeSignal withDeviationExceeded();
}
