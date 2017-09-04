package me.caosh.condition.domain.model.market;

import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;

/**
 * Created by caosh on 2017/9/3.
 */
public class SecurityInfoConstants {
    public static final SecurityInfo NEW_STOCK_PURCHASE = new SecurityInfo(SecurityType.STOCK, "999998", SecurityExchange.NA, "新股申购");
}
