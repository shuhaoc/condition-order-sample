package hbec.intellitrade.conditionorder.domain.trackindex;

/**
 * 跟踪指数参数
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public interface TrackedIndex {
    /**
     * 获取跟踪指数选项
     *
     * @return 跟踪指数选项
     */
    TrackIndexOption getOption();
}
