package me.caosh.condition.domain.service;

import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;

/**
 * Created by caosh on 2017/8/2.
 */
public interface SecurityEntrustService {
    EntrustResult execute(EntrustCommand entrustCommand);
}
