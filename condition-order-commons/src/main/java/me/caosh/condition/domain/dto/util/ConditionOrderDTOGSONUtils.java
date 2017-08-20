package me.caosh.condition.domain.dto.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.domain.dto.order.*;

/**
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderDTOGSONUtils {

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
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDTO.class)
                        .registerSubtype(PriceConditionDTO.class)
                        .registerSubtype(TurnUpConditionDTO.class)
                        .registerSubtype(SimpleTimeConditionDTO.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(TradeSignalDTO.class)
                        .registerSubtype(GeneralSignalDTO.class)
                        .registerSubtype(CacheSyncSignalDTO.class))
                .create();
    }

    private ConditionOrderDTOGSONUtils() {
    }
}
