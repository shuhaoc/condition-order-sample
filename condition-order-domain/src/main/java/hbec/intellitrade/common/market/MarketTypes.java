package hbec.intellitrade.common.market;

import hbec.intellitrade.common.security.SecurityType;

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

    private MarketTypes() {
    }

    private static final MarketTypes _CODE_COVERAGE = new MarketTypes();
}
