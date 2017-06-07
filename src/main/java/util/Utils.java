package util;

import java.util.List;

/**
 * Created by ywcrm on 2017/6/7.
 */
public class Utils {

    // 把list转换为string，用,分割
    public String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public List<String> convertMedalListToIdlist(List<String> medalList) {
        return null;
    }

}
