package me.caosh.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/3.
 */
public class GsonExtrasTest {
    private static final Logger logger = LoggerFactory.getLogger(GsonExtrasTest.class);

    @Test
    public void test() throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(
                RuntimeTypeAdapterFactory.of(ConditionOrder.class)
                        .registerSubtype(PriceOrder.class)
        ).create();

        PriceOrder priceOrder = new PriceOrder(123L, OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH"),
                new PriceCondition(CompareCondition.LESS_THAN_OR_EQUALS, new BigDecimal("13.00")),
                new TradePlan(ExchangeType.BUY, EntrustStrategy.CURRENT_PRICE, 100)
        );
        String json = gson.toJson(priceOrder);
        logger.info(json);
        PriceOrder recovered = (PriceOrder) gson.fromJson(json, ConditionOrder.class);
        assertEquals(priceOrder.getOrderId(), recovered.getOrderId());
        assertEquals(priceOrder.getSecurityInfo(), recovered.getSecurityInfo());
        assertEquals(priceOrder.getCondition(), recovered.getCondition());
        assertEquals(priceOrder.getTradePlan(), recovered.getTradePlan());
    }
}
