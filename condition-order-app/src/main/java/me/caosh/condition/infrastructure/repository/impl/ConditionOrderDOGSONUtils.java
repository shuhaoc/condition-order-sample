package me.caosh.condition.infrastructure.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.infrastructure.repository.model.ConditionDO;
import me.caosh.condition.infrastructure.repository.model.DynamicPropertiesDO;
import me.caosh.condition.infrastructure.repository.model.GridConditionDO;
import me.caosh.condition.infrastructure.repository.model.GridDynamicPropertiesDO;
import me.caosh.condition.infrastructure.repository.model.NewStockPurchaseConditionDO;
import me.caosh.condition.infrastructure.repository.model.NewStockPurchaseDynamicPropertiesDO;
import me.caosh.condition.infrastructure.repository.model.PriceConditionDO;
import me.caosh.condition.infrastructure.repository.model.SimpleTimeConditionDO;
import me.caosh.condition.infrastructure.repository.model.TurnUpConditionDO;
import me.caosh.condition.infrastructure.repository.model.TurnUpDynamicPropertiesDO;

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
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDO.class)
                        .registerSubtype(PriceConditionDO.class)
                        .registerSubtype(TurnUpConditionDO.class)
                        .registerSubtype(SimpleTimeConditionDO.class)
                        .registerSubtype(GridConditionDO.class)
                        .registerSubtype(NewStockPurchaseConditionDO.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(DynamicPropertiesDO.class)
                        .registerSubtype(TurnUpDynamicPropertiesDO.class)
                        .registerSubtype(GridDynamicPropertiesDO.class)
                        .registerSubtype(NewStockPurchaseDynamicPropertiesDO.class))
                .create();
    }

    private ConditionOrderDOGSONUtils() {
    }
}
