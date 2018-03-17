package hbec.intellitrade.condorder.domain.trackindex;

/**
 * 未开启情况下的跟踪指数参数
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public enum NoneTrackedIndex implements TrackedIndex {
    /**
     * 单例
     */
    NONE;


    @Override
    public TrackIndexOption getOption() {
        return TrackIndexOption.DISABLED;
    }
}
