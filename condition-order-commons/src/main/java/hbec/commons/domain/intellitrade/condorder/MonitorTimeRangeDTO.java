package hbec.commons.domain.intellitrade.condorder;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.FieldMapping;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/14
 */
@Convertible
public class MonitorTimeRangeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Range(min = 0, max = 1)
    private Integer option;
    @Range(min = 1, max = 5)
    @FieldMapping(mappedProperty = "weekRange.beginWeek")
    private Integer beginWeek;
    @Range(min = 1, max = 5)
    @FieldMapping(mappedProperty = "weekRange.endWeek")
    private Integer endWeek;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @FieldMapping(mappedProperty = "localTimeRange.beginTime")
    private String beginTime;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @FieldMapping(mappedProperty = "localTimeRange.endTime")
    private String endTime;

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public Integer getBeginWeek() {
        return beginWeek;
    }

    public void setBeginWeek(Integer beginWeek) {
        this.beginWeek = beginWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(MonitorTimeRangeDTO.class).omitNullValues()
                          .add("option", option)
                          .add("beginWeek", beginWeek)
                          .add("endWeek", endWeek)
                          .add("beginTime", beginTime)
                          .add("endTime", endTime)
                          .toString();
    }
}
