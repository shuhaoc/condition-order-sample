package hbec.intellitrade.condorder.domain.trackindex;

import hbec.intellitrade.common.market.index.IndexSource;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class TrackedIndexInfoBuilder implements ConvertibleBuilder<TrackedIndex> {
    private TrackIndexOption option = TrackIndexOption.DISABLED;
    private IndexSource source;
    private String code;
    private String name;

    public TrackedIndexInfoBuilder setOption(TrackIndexOption option) {
        this.option = option;
        return this;
    }

    public TrackedIndexInfoBuilder setSource(IndexSource source) {
        this.source = source;
        return this;
    }

    public TrackedIndexInfoBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public TrackedIndexInfoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TrackedIndex build() {
        if (option == TrackIndexOption.DISABLED) {
            return NoneTrackedIndex.NONE;
        }
        return new TrackedIndexInfo(source, code, name);
    }
}
