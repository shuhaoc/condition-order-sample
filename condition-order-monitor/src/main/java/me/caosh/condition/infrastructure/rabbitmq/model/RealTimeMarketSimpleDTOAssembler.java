package me.caosh.condition.infrastructure.rabbitmq.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityType;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketSimpleDTOAssembler {
    public static RealTimeMarket fromDTO(SecurityType securityType, RealTimeMarketSimpleDTO realTimeMarketSimpleDTO) {
        MarketID marketID = new MarketID(securityType, realTimeMarketSimpleDTO.getC());
        return new RealTimeMarket(marketID, realTimeMarketSimpleDTO.getP(), Lists.newArrayList(
                realTimeMarketSimpleDTO.getSp5(),
                realTimeMarketSimpleDTO.getSp4(),
                realTimeMarketSimpleDTO.getSp3(),
                realTimeMarketSimpleDTO.getSp2(),
                realTimeMarketSimpleDTO.getSp1(),
                realTimeMarketSimpleDTO.getBp1(),
                realTimeMarketSimpleDTO.getBp2(),
                realTimeMarketSimpleDTO.getBp3(),
                realTimeMarketSimpleDTO.getBp4(),
                realTimeMarketSimpleDTO.getBp5()
        ));
    }

    public static Map<String, RealTimeMarket> transformMap(final SecurityType securityType, Map<String, RealTimeMarketSimpleDTO> realTimeMarketDTOMap) {
        return Maps.transformValues(realTimeMarketDTOMap, new Function<RealTimeMarketSimpleDTO, RealTimeMarket>() {
            @Override
            public RealTimeMarket apply(RealTimeMarketSimpleDTO realTimeMarketSimpleDTO) {
                return fromDTO(securityType, realTimeMarketSimpleDTO);
            }
        });
    }
}
