package hbec.intellitrade.strategy.domain.timerange;

import hbec.intellitrade.strategy.domain.shared.Week;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/14
 */
public class WeekRangeBuilder implements ConvertibleBuilder<WeekRange> {
    private Week beginWeek;
    private Week endWeek;

    public void setBeginWeek(Week beginWeek) {
        this.beginWeek = beginWeek;
    }

    public void setEndWeek(Week endWeek) {
        this.endWeek = endWeek;
    }

    @Override
    public WeekRange build() {
        return new WeekRange(beginWeek, endWeek);
    }
}
