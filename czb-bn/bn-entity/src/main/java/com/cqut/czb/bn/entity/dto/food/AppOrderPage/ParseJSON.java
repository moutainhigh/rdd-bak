package com.cqut.czb.bn.entity.dto.food.AppOrderPage;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 解析json
 */
public class ParseJSON {

    public static List<OrderNum> parseJSONWithJSONObject(String jsonData) {
            try
             {
                List<OrderNum> list=new ArrayList();
                JSONArray jsonArray = JSON.parseArray(jsonData);
                for (int i = 0; i<jsonArray.size(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String dishId = jsonObject.getString("dishId");
                        String num = jsonObject.getString("num");
                        System.out.println("dishId：" + dishId + "num：" + num);
                        OrderNum orderNum=new OrderNum();
                        orderNum.setDishId(dishId);
                        orderNum.setNum(Integer.parseInt(num));
                        list.add(orderNum);
                }
                return list;
             }
             catch (Exception e)
             {
                 e.printStackTrace();
                 return null;
             }
            }
}
