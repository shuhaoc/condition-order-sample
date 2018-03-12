package hbec.intellitrade.strategy.domain.condition.delayconfirm;

/**
 * 延迟确认参数
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public interface DelayConfirmParam {
    /**
     * 延迟确认选项
     *
     * @return 延迟确认选项
     */
    DelayConfirmOption getOption();

    /**
     * 延迟确认（目标）次数
     *
     * @return 延迟确认（目标）次数
     */
    int getConfirmTimes();
}
