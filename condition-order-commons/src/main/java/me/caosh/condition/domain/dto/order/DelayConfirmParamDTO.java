package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
@Convertible
public class DelayConfirmParamDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Range(min = 0, max = 2)
    private Integer option;

    @Range(min = 0, max = 20)
    private Integer confirmTimes;

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public Integer getConfirmTimes() {
        return confirmTimes;
    }

    public void setConfirmTimes(Integer confirmTimes) {
        this.confirmTimes = confirmTimes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DelayConfirmParamDTO.class).omitNullValues()
                .add("option", option)
                .add("confirmTimes", confirmTimes)
                .toString();
    }
}
