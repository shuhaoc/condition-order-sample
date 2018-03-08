package hbec.intellitrade.trade.domain.exception;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class InsufficientPositionException extends TradeException {
    public InsufficientPositionException(String message) {
        super(message);
    }
}
