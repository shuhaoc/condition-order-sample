package hbec.intellitrade.trade.domain;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class EntrustResult {
    public static final int SUCCESS = 1;

    private final Integer entrustState;
    private final String entrustMessage;
    private final Integer entrustCode;

    public static EntrustResult ofSuccess(String entrustMessage, Integer entrustCode) {
        return new EntrustResult(SUCCESS, entrustMessage, entrustCode);
    }

    public static EntrustResult ofFail(Integer entrustState, String entrustMessage) {
        return new EntrustResult(entrustState, entrustMessage, null);
    }

    public EntrustResult(Integer entrustState, String entrustMessage, Integer entrustCode) {
        this.entrustState = entrustState;
        this.entrustMessage = entrustMessage;
        this.entrustCode = entrustCode;
    }

    public Integer getEntrustState() {
        return entrustState;
    }

    public String getEntrustMessage() {
        return entrustMessage;
    }

    public Integer getEntrustCode() {
        return entrustCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustResult.class).omitNullValues()
                .addValue(EntrustResult.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("entrustState", entrustState)
                .add("entrustMessage", entrustMessage)
                .add("entrustCode", entrustCode)
                .toString();
    }
}
