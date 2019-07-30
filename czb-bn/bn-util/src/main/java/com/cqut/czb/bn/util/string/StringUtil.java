package com.cqut.czb.bn.util.string;

import com.cqut.czb.bn.util.constants.SystemConstants;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class StringUtil {

    private static final int DEFAULT_MAX_NUM = 100;
    private static Random random = new Random();

    // 根据系统时间生成的字符串
    private static String timesString = "";

    // 每次生成id时，生成相同id后，此序列号+1
    private static Integer orderNum = 1;

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


    public static String createNanoTimeTimestamp() {
        return String.valueOf(System.nanoTime());
    }

    public static String createNanoTimeId() {
        return createNanoTimeTimestamp() + "" + random.nextInt(10) + "" + random.nextInt(10);
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
        String returnTimesString = createNanoTimeTimestamp();
        // 如果时间字符串为空，则记录下来
        if(timesString.equals("")){
            timesString = returnTimesString;
        } else{
            // 如果返回的系统时间和上次的系统时间相同，则此时加上一个序列号，防止生成相同id
            if(timesString.equals(returnTimesString)){
                returnTimesString = returnTimesString + orderNum.toString();
                orderNum++;
            } else {
                // 不同则将序列号重置为1
                orderNum = 1;
            }
        }
        return returnTimesString + "" + random.nextInt(10) + "" + random.nextInt(10);
    }


    /**
     * 方法名称:transMapToString
     * 传入参数:map
     * 返回值:String 形如 username'chenziwen^password'1234
     */
    @SuppressWarnings("rawtypes")
    public static String transMapToStringOther(Map map){
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (java.util.Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

}
