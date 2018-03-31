package hbec.intellitrade.replay;

/**
 * 输入流对象
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/28
 */
public interface InputStreamObject {
    /**
     * 获取输入时间戳，决定其在输入流中的顺序
     *
     * @return 输入时间戳
     */
    long getInputTimestamp();
}
