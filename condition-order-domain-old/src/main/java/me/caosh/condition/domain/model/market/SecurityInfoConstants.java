package me.caosh.condition.domain.model.market;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;

/**
 * Created by caosh on 2017/9/3.
 */
public class SecurityInfoConstants {
    public static final SecurityInfo NEW_STOCK_PURCHASE = new SecurityInfo(SecurityType.STOCK, "999998", SecurityExchange.NV, "新股申购");
}
