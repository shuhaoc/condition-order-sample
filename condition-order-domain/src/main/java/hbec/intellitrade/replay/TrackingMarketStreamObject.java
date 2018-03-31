package hbec.intellitrade.replay;

import com.google.common.base.Optional;
import hbec.intellitrade.common.market.MarketXID;

/**
 * 跟踪行情的输入流对象
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/31
 */
public interface TrackingMarketStreamObject extends InputStreamObject {
    /**
     * 获取跟踪的行情XID
     *
     * @return 行情XID
     */
    Optional<MarketXID> getTrackMarketXID();
}
