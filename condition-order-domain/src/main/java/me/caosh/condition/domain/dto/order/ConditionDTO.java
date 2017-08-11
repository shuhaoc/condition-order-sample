package me.caosh.condition.domain.dto.order;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionDTO extends Serializable {
    void accept(ConditionDTOVisitor visitor);
}
