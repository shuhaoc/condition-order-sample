package me.caosh.condition.infrastructure.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.infrastructure.repository.model.*;

/**
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderDOGSONUtils {

    private static final Gson GSON = createConditionGSON();

    public static Gson getGSON() {
        return GSON;
    }

    private static Gson createConditionGSON() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDO.class)
                        .registerSubtype(PriceConditionDO.class)
                        .registerSubtype(TurnUpConditionDO.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(DynamicPropertiesDO.class)
                        .registerSubtype(TurnUpDynamicPropertiesDO.class))
                .create();
    }

    private ConditionOrderDOGSONUtils() {
    }
}
