package com.cqut.czb.bn.util.string;

import com.cqut.czb.bn.util.constants.SystemConstants;
import java.util.Random;

public class StringUtil {

    private static final int DEFAULT_MAX_NUM = 100;
    private static Random random = new Random();
    /**
     * 将空的字符串转成空字符串，不是空字符串就返回原来值
     * 作者：谭勇
     * 时间：2018-04-07
     * */
    public static String emptyStr2NullStr(String str) {
        if (isNullOrEmpty(str.trim())) {
            return null;
        }

        return str;
    }

    /**
     * 判断一个字符串是否为空
     * 作者：谭勇
     * 时间：2018-04-07
     * */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals(SystemConstants.EMPTY_STR);
    }


    /**
     * 生成当前纳秒值的字符串
     * */
    public static String createTimestamp() {
        return String.valueOf(System.nanoTime())
                .concat(String.valueOf(random.nextInt(DEFAULT_MAX_NUM)));
    }

    /**
     * 生成毫秒时间戳字符串
     * */
    public static String createMillisTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
    /**
     * 根据当前时间戳生成id
     * */
    public static String createId() {
        return createMillisTimestamp() + "" + random.nextInt(10) + "" + random.nextInt(10);
    }

}
