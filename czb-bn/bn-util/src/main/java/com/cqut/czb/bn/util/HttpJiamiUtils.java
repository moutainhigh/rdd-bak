package com.cqut.czb.bn.util;

import com.cqut.czb.bn.util.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HttpJiamiUtils {
    public static String CZBSign(Map<String,String> map){
        List<String> list = new ArrayList<>();
        String time = map.get("timestamp");
        map.remove("timestamp");
        String F = map.get("F");
        map.remove("F");
        //等号赋值 key=value 赋值到list中
        for (String s : map.keySet()){
            String r = s+"="+map.get(s);
            list.add(r);
        }

        //list排序 按首字母从小到大排序
        Collections.sort(list,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1 == null || o2 == null){
                    return -1;
                }
                if (o1.charAt(0)>o2.charAt(0)){
                    return 1;
                }
                if (o1.charAt(0)<o2.charAt(0)){
                    return -1;
                }
                if(o1.compareTo(o2) > 0){
                    return 1;
                }
                if(o1.compareTo(o2) < 0){
                    return -1;
                }
                if(o1.compareTo(o2) == 0){
                    return 0;
                }
                return 0;
            }
        });

        //字符串拼接 拼接"&"
        String r = "";
        for (String s : list){
            r+=s+"&";
        }
        if("ios".equals(F))  {
            r = r+"clientKey="+ Config.IOSKEY+"&"+"timestamp="+time;
        }
        if("android".equals(F)) {
            r = r+"clientKey="+ Config.ANDROIDKEY+"&"+"timestamp="+time;
        }
        r = MD5Utils.MD5Encode(r,"utf-8");
        return r;
    }

}
