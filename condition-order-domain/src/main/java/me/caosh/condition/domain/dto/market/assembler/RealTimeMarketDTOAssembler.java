package me.caosh.condition.domain.dto.market.assembler;

import me.caosh.condition.domain.dto.market.RealTimeMarketDTO;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketDTOAssembler {
    public static RealTimeMarketDTO toDTO(RealTimeMarket realTimeMarket) {
        RealTimeMarketDTO dto = new RealTimeMarketDTO();
        dto.setType(realTimeMarket.getMarketID().getType().getValue());
        dto.setCode(realTimeMarket.getMarketID().getCode());
        dto.setCurrentPrice(realTimeMarket.getCurrentPrice());
        dto.setOfferedPrices(realTimeMarket.getOfferedPrices());
        return dto;
    }

    public static RealTimeMarket fromDTO(RealTimeMarketDTO realTimeMarketDTO) {
        SecurityType securityType = ValuedEnumUtil.valueOf(realTimeMarketDTO.getType(), SecurityType.class);
        MarketID marketID = new MarketID(securityType, realTimeMarketDTO.getCode());
        return new RealTimeMarket(marketID, realTimeMarketDTO.getCurrentPrice(), realTimeMarketDTO.getOfferedPrices());
    }

    private RealTimeMarketDTOAssembler() {
    }
}
