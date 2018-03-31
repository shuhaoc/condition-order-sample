package hbec.intellitrade.condorder.domain.trackindex;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.IndexInfo;
import hbec.intellitrade.common.market.MarketSource;

/**
 * 开启状态下的跟踪指数信息
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class TrackedIndexInfo extends IndexInfo implements TrackedIndex {
    public TrackedIndexInfo(MarketSource source, String code, String name) {
        super(source, code, name);
    }

    @Override
    public TrackIndexOption getOption() {
        return TrackIndexOption.ENABLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TrackedIndexInfo.class).omitNullValues()
                          .add("source", getSource())
                          .add("code", getCode())
                          .add("name", getName())
                          .toString();
    }
}
