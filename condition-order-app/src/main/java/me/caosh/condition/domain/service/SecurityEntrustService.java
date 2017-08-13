package me.caosh.condition.domain.service;

import me.caosh.condition.domain.model.trade.EntrustCommand;

/**
 * Created by caosh on 2017/8/2.
 */
public interface SecurityEntrustService {
    void execute(EntrustCommand entrustCommand);
}
