package me.caosh.condition.domain.dto.order;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionDTOVisitor {
    void visitPriceConditionDTO(PriceConditionDTO priceConditionDTO);

    void visitTurnUpConditionDTO(TurnUpConditionDTO turnUpConditionDTO);

    void visitSimpleTimeConditionDTO(SimpleTimeConditionDTO simpleTimeConditionDTO);

    void visitGridConditionDTO(GridConditionDTO gridConditionDTO);
}
