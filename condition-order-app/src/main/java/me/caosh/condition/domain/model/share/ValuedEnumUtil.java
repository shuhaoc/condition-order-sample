package me.caosh.condition.domain.model.share;

/**
 * Created by caosh on 2017/8/3.
 */
public class ValuedEnumUtil {
    public static <ValueType, EnumType extends ValuedEnum<ValueType>> EnumType valueOf(ValueType value, Class<EnumType> enumClass) {
        for (EnumType enumObj : enumClass.getEnumConstants()) {
            if (enumObj.getValue().equals(value)) {
                return enumObj;
            }
        }
        throw new IllegalArgumentException("value=" + value + ", enumClass=" + enumClass);
    }

    private ValuedEnumUtil() {
    }
}
