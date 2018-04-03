package hbec.intellitrade.replay;

import java.io.Serializable;

/**
 * 输入流对象
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/28
 */
public interface InputStreamObject extends Serializable {
    /**
     * 获取输入时间戳，决定其在输入流中的顺序
     *
     * @return 输入时间戳
     */
    long getInputTimestamp();
}
