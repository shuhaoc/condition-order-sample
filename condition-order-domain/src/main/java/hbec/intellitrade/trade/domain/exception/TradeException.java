package hbec.intellitrade.trade.domain.exception;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class TradeException extends Exception {
    public TradeException(String message) {
        super(message);
    }

    public TradeException(String message, Throwable cause) {
        super(message, cause);
    }
}
