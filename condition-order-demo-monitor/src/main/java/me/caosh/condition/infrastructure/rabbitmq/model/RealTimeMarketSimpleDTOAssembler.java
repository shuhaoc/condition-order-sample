package me.caosh.condition.infrastructure.rabbitmq.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.MarketType;
import hbec.intellitrade.common.market.RealTimeMarket;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketSimpleDTOAssembler {
    public static RealTimeMarket fromDTO(MarketType marketType, RealTimeMarketSimpleDTO realTimeMarketSimpleDTO) {
        MarketID marketID = new MarketID(marketType, realTimeMarketSimpleDTO.getC());
        ArrayList<BigDecimal> offeredPrices = null;
        if (realTimeMarketSimpleDTO.getSp5() != null) {
            offeredPrices = Lists.newArrayList(
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
            );
        }
        return new RealTimeMarket(marketID,
                                  realTimeMarketSimpleDTO.getP(),
                                  realTimeMarketSimpleDTO.getPp(),
                                  offeredPrices,
                                  LocalDateTime.fromDateFields(realTimeMarketSimpleDTO.getDt()));
    }

    public static Map<String, RealTimeMarket> transformMap(final MarketType marketType,
                                                           Map<String, RealTimeMarketSimpleDTO> realTimeMarketDTOMap) {
        return Maps.transformValues(realTimeMarketDTOMap, new Function<RealTimeMarketSimpleDTO, RealTimeMarket>() {
            @Override
            public RealTimeMarket apply(RealTimeMarketSimpleDTO realTimeMarketSimpleDTO) {
                return fromDTO(marketType, realTimeMarketSimpleDTO);
            }
        });
    }
}
