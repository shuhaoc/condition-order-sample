package hbec.intellitrade.conditionorder.domain.delayconfirm.count;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/13
 */
public class SingleDelayConfirmCountBuilder implements ConvertibleBuilder<SingleDelayConfirmCount> {
    private int confirmedCount;

    public SingleDelayConfirmCountBuilder setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
        return this;
    }

    @Override
    public SingleDelayConfirmCount build() {
        return new SingleDelayConfirmCount(confirmedCount);
    }
}
