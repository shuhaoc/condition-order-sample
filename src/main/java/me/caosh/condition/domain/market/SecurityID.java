package me.caosh.condition.domain.market;

/**
 * Created by caosh on 2017/8/1.
 */
public class SecurityID {
    private final SecurityType type;
    private final String code;

    public SecurityType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public SecurityID(SecurityType type, String code) {

        this.type = type;
        this.code = code;
    }

    @Override
    public String toString() {
        return type.name() + "/" + code;
    }
}
