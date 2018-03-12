package hbec.intellitrade.strategy.domain;

import org.joda.time.LocalDateTime;

/**
 * 收盘事件监听者
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public interface MarketClosedEventListener {
    /**
     * 收盘事件通知
     *
     * @param localDateTime 收盘时间
     */
    void onMarketClosed(LocalDateTime localDateTime);
}
