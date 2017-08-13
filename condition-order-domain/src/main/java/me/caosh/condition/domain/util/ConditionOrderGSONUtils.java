package me.caosh.condition.domain.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.signal.General;
import me.caosh.condition.domain.model.signal.None;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderGSONUtils {

    private static final Gson GSON = createSpecialGson();
    private static final Gson MARKET_GSON = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();

    public static Gson getGSON() {
        return GSON;
    }

    public static Gson getMarketGSON() {
        return MARKET_GSON;
    }

    private static Gson createSpecialGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Condition.class)
                        .registerSubtype(PriceCondition.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDTO.class)
                        .registerSubtype(PriceConditionDTO.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(TradeSignal.class)
                        .registerSubtype(None.class)
                        .registerSubtype(General.class))
                .create();
    }

    private ConditionOrderGSONUtils() {
    }
}
