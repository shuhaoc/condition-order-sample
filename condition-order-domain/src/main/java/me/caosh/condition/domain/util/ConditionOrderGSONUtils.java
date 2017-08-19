package me.caosh.condition.domain.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.domain.dto.order.*;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.signal.General;
import me.caosh.condition.domain.model.signal.None;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * TODO: rename this
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderGSONUtils {

    private static final Gson CONDITION_GSON = createConditionGSON();
    private static final Gson MARKET_GSON = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();

    public static Gson getConditionGSON() {
        return CONDITION_GSON;
    }

    public static Gson getMarketGSON() {
        return MARKET_GSON;
    }

    private static Gson createConditionGSON() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDTO.class)
                        .registerSubtype(PriceConditionDTO.class)
                        .registerSubtype(TurnUpConditionDTO.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(TradeSignalDTO.class)
                        .registerSubtype(GeneralSignalDTO.class)
                        .registerSubtype(CacheSyncSignalDTO.class))
                .create();
    }

    private ConditionOrderGSONUtils() {
    }
}
