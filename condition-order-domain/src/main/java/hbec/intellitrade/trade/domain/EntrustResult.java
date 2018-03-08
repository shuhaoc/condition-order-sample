package hbec.intellitrade.trade.domain;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class EntrustResult {
    public static final int SUCCESS = 1;
    public static final int FAIL = 1;

    private final int entrustState;
    private final String entrustMessage;

    public EntrustResult(int entrustState, String entrustMessage) {
        this.entrustState = entrustState;
        this.entrustMessage = entrustMessage;
    }

    public int getEntrustState() {
        return entrustState;
    }

    public String getEntrustMessage() {
        return entrustMessage;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustResult.class).omitNullValues()
                .add("entrustState", entrustState)
                .add("entrustMessage", entrustMessage)
                .toString();
    }
}
