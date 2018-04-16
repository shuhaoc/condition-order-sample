package hbec.commons.domain.intellitrade.conditionorder;

import com.google.common.base.Optional;
import hbec.commons.domain.intellitrade.market.TrackedIndexDTO;
import hbec.commons.domain.intellitrade.security.SecurityInfoDTO;
import hbec.intellitrade.common.market.MarketSource;
import hbec.intellitrade.common.market.MarketType;
import hbec.intellitrade.common.market.MarketXID;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/31
 */
public class ConditionOrderDtoTest {
    @Test
    public void testGetTrackMarketXidAbsent() throws Exception {
        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        assertEquals(conditionOrderDTO.getTrackMarketXID(), Optional.<MarketXID>absent());
    }

    @Test
    public void testGetTrackMarketXidTrackIndex() throws Exception {
        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        TrackedIndexDTO trackedIndex = new TrackedIndexDTO();
        trackedIndex.setOption(1);
        trackedIndex.setSource("SH");
        trackedIndex.setCode("000016");
        conditionOrderDTO.setTrackedIndex(trackedIndex);

        assertEquals(conditionOrderDTO.getTrackMarketXID().orNull(),
                     new MarketXID(MarketType.INDEX, MarketSource.SH, "000016"));
    }

    @Test
    public void testGetTrackMarketXidNormal() throws Exception {
        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setExchange("SH");
        securityInfo.setCode("600000");
        conditionOrderDTO.setSecurityInfo(securityInfo);

        assertEquals(conditionOrderDTO.getTrackMarketXID().orNull(),
                     new MarketXID(MarketType.STOCK, MarketSource.SH, "600000"));
    }
}
