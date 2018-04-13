package hbec.intellitrade.condorder.domain.trackindex;

import hbec.intellitrade.common.market.index.IndexInfo;
import hbec.intellitrade.common.market.index.IndexSource;

/**
 * 开启状态下的跟踪指数信息
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class TrackedIndexInfo extends IndexInfo implements TrackedIndex {
    public TrackedIndexInfo(IndexSource source, String code, String name) {
        super(source, code, name);
    }

    @Override
    public TrackIndexOption getOption() {
        return TrackIndexOption.ENABLED;
    }
}
