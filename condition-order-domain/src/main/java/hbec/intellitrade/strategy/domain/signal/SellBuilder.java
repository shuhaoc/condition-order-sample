package hbec.intellitrade.strategy.domain.signal;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
public class SellBuilder implements ConvertibleBuilder<BS> {
    private Boolean deviationExceeded;

    public Boolean getDeviationExceeded() {
        return deviationExceeded;
    }

    public void setDeviationExceeded(Boolean deviationExceeded) {
        this.deviationExceeded = deviationExceeded;
    }

    @Override
    public BS build() {
        if (Boolean.TRUE.equals(deviationExceeded)) {
            return new Sell(deviationExceeded);
        }
        return Signals.sell();
    }
}
