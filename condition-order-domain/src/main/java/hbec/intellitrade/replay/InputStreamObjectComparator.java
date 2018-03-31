package hbec.intellitrade.replay;

import java.util.Comparator;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/31
 */
public class InputStreamObjectComparator implements Comparator<InputStreamObject> {
    @Override
    public int compare(InputStreamObject a, InputStreamObject b) {
        if (a.getInputTimestamp() > b.getInputTimestamp()) {
            return 1;
        } else if (a.getInputTimestamp() < b.getInputTimestamp()) {
            return -1;
        }
        return 0;
    }
}
