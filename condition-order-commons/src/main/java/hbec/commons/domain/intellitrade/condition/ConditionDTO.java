package hbec.commons.domain.intellitrade.condition;

import me.caosh.autoasm.RuntimeType;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@RuntimeType({
        PriceConditionDTO.class,
        TimeReachedConditionDTO.class,
        TurnPointConditionDTO.class,
        NewStockPurchaseConditionDTO.class,
        GridConditionDTO.class
})
public interface ConditionDTO extends Serializable {
}
