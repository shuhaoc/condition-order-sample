package hbec.intellitrade.common.market;

import hbec.intellitrade.common.security.SecurityType;
import me.caosh.autoasm.AutoAssemblers;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class MarketTypes {
    public static MarketType fromSecurityType(SecurityType securityType) {
        switch (securityType) {
            case STOCK:
                return MarketType.STOCK;
            case FUND:
                return MarketType.FUND;
            default:
                throw new IllegalArgumentException("securityType=" + securityType);
        }
    }

    public static MarketType valueOf(int marketType) {
        return AutoAssemblers.getDefault().disassemble(marketType, MarketType.class);
    }

    private MarketTypes() {
    }

    private static final MarketTypes _CODE_COVERAGE = new MarketTypes();
}
