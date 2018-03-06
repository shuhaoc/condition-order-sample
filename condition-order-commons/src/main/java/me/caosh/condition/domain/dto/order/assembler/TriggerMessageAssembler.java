package me.caosh.condition.domain.dto.order.assembler;

import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.dto.market.RealTimeMarketDTO;
import me.caosh.condition.domain.dto.market.assembler.RealTimeMarketDTOAssembler;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTO;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.TriggerMessage;
import hbec.intellitrade.strategy.domain.signal.Signal;

/**
 * Created by caosh on 2017/8/30.
 */
public class TriggerMessageAssembler {
    public static TriggerMessageDTO toDTO(TriggerMessage triggerMessage) {
        TradeSignalDTO tradeSignalDTO = new SignalDTOBuilder(triggerMessage.getSignal()).build();
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(triggerMessage.getConditionOrder());
        RealTimeMarketDTO realTimeMarketDTO = null;
        if (triggerMessage.getRealTimeMarket().isPresent()) {
            realTimeMarketDTO = RealTimeMarketDTOAssembler.toDTO(triggerMessage.getRealTimeMarket().get());
        }
        return new TriggerMessageDTO(tradeSignalDTO, conditionOrderDTO, realTimeMarketDTO);
    }

    public static TriggerMessage fromDTO(TriggerMessageDTO triggerMessageDTO) {
        Signal signal = new TradeSignalBuilder(triggerMessageDTO.getTradeSignalDTO()).build();
        ConditionOrder conditionOrder = ConditionOrderDTOAssembler.fromDTO(triggerMessageDTO.getConditionOrderDTO());
        RealTimeMarket realTimeMarket = null;
        if (triggerMessageDTO.getRealTimeMarketDTO() != null) {
            realTimeMarket = RealTimeMarketDTOAssembler.fromDTO(triggerMessageDTO.getRealTimeMarketDTO());
        }
        return new TriggerMessage(signal, conditionOrder, realTimeMarket);
    }

    private TriggerMessageAssembler() {
    }
}
