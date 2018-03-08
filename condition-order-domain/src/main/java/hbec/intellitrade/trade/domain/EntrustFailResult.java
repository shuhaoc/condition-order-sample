package hbec.intellitrade.trade.domain;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class EntrustFailResult extends EntrustResult {
    public EntrustFailResult(String entrustMessage) {
        super(FAIL, entrustMessage);
    }
}
