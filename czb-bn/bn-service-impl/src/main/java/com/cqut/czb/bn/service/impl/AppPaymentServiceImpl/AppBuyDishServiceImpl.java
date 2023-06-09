package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.ShopMapper;
import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishOrderMapper;
import com.cqut.czb.bn.dao.mapper.food.OrderDishesMapper;
import com.cqut.czb.bn.dao.mapper.food.OrderDishesMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.InputDishDTO;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.OrderNum;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.ParseJSON;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.food.DishOrder;
import com.cqut.czb.bn.entity.entity.food.OrderDishes;
import com.cqut.czb.bn.service.appPaymentService.AppBuyDishService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AppBuyDishServiceImpl implements AppBuyDishService {

    @Autowired
    DishMapperExtra dishMapperExtra;

    @Autowired
    DishOrderMapper dishOrderMapper;

    @Autowired
    OrderDishesMapperExtra orderDishesMapperExtra;

    @Autowired
    ShopMapper shopMapper;


    @Override
    public Map AliBuyDish(User user, InputDishDTO inputDishDTO) {
        Map dataMap= processingParameters(inputDishDTO);

        List<OrderNum> list= (List<OrderNum>) dataMap.get("list");

        List<DishDTO> dishDTOS= (List<DishDTO>) dataMap.get("dishDTOS");

        //支付金额
        double totalPrice= (double) dataMap.get("totalPrice");

        if(dataMap==null&&list==null&&dishDTOS==null&&totalPrice<=0){
            return null;
        }

        //生成起吊参数
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("5",user.getIsSpecial());//"5"为点餐
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //购买者id
        String ownerId = user.getUserId();
        request.setBizModel(AliParameterConfig.getBizModelBuyDish(thirdOrder, totalPrice ,ownerId,user.getIsSpecial()));//支付订单
        request.setNotifyUrl(AliPayConfig.BuyDish_url);
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //    插入订单
        insertOrder(thirdOrder,totalPrice,user,inputDishDTO,dishDTOS,list,1);

        Map map=new HashMap();
        map.put("orderString",orderString);
        map.put("orderId",thirdOrder);
        return map;
    }

    @Override
    public Map<String, Object> WeChatBuyDish(User user, InputDishDTO inputDishDTO) {

        Map dataMap= processingParameters(inputDishDTO);

        List<OrderNum> list= (List<OrderNum>) dataMap.get("list");

        List<DishDTO> dishDTOS= (List<DishDTO>) dataMap.get("dishDTOS");

        //支付金额
        double totalPrice= (double) dataMap.get("totalPrice");

        if(dataMap==null&&list==null&&dishDTOS==null&&totalPrice<=0){
            return null;
        }

        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String nonceStrTemp = WeChatUtils.getRandomStr();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersBuyDish(nonceStrTemp,orgId,user.getUserId(),totalPrice);
        //    插入订单
        insertOrder(orgId,totalPrice,user,inputDishDTO,dishDTOS,list,2);
        Map map=new HashMap();
        map.put("orderString",WeChatParameterConfig.getSign( parameters, nonceStrTemp));
        map.put("orderId",orgId);
        return  map;
    }


    //    插入订单
    public  void insertOrder(String orgId,double totalPrice,User user,InputDishDTO inputDishDTO, List<DishDTO> dishDTOS, List<OrderNum> list,int payMethod){
        //插入订单

        //插叙是否需要核销
        Shop shop=shopMapper.selectByPrimaryKey(inputDishDTO.getShopId());
        int diningStatus=0;
        if(shop!=null){
            if(shop.getOrderWriteOff()==1){
                diningStatus=1;
            }
        }


        //菜品订单表：插入
        DishOrder dishOrder=new DishOrder();
        dishOrder.setOrderId(orgId);
        dishOrder.setActualPrice(totalPrice) ;
        dishOrder.setUserId(user.getUserId());
        dishOrder.setShopId(inputDishDTO.getShopId());
        dishOrder.setDiningStatus(diningStatus);//0:不核销 1:未确认 2:已完成
        dishOrder.setRemark(inputDishDTO.getRemark());
        dishOrder.setPayStatus(0);
        dishOrder.setPeyMethod(payMethod);
        dishOrder.setCreateAt(new Date());
        int order=dishOrderMapper.insertSelective(dishOrder);
        System.out.println("菜单订单表插入"+(order>0));

        //插入关系表
        List<OrderDishes> orderDisheslist=new ArrayList<>();
        for(int i=0;i<dishDTOS.size();i++){
            OrderDishes orderDishes=new OrderDishes();
            orderDishes.setCreateAt(new Date());
            orderDishes.setUpdateAt(new Date());
            orderDishes.setDishId(dishDTOS.get(i).getDishId());
            orderDishes.setDishType(dishDTOS.get(i).getDishType());
            orderDishes.setOrderId(orgId);
            for(int j=0;j<list.size();j++){
                if(list.get(j).getDishId().equals(dishDTOS.get(i).getDishId())){
                    orderDishes.setDishCount(list.get(i).getNum());
                }
            }
            orderDishes.setOrderDishesId(StringUtil.createId());
            orderDisheslist.add(orderDishes);
        }
        int OrderDishes=orderDishesMapperExtra.insertList(orderDisheslist);
        System.out.println("插入关系表："+OrderDishes);
    }


    //    支付前数据处理
    public Map processingParameters(InputDishDTO inputDishDTO){
        //解析订单
        List<OrderNum> list= ParseJSON.parseJSONWithJSONObject(inputDishDTO.getDishInfo());
        if(list==null){
            return null;
        }
        //取出所有商品的信息
        List<DishDTO> dishDTOS=dishMapperExtra.selectOrderDish(list);

        //价格计算
        double  totalPrice=0.0;
        if(dishDTOS==null){
            return null;
        }

        for(int i=0;i<dishDTOS.size();i++){
            for(int j=0;j<list.size();j++){
                if(dishDTOS.get(i).getDishId().equals(list.get(j).getDishId())){
                    totalPrice+=(BigDecimal.valueOf(dishDTOS.get(i).getCurrentPrice()).multiply(BigDecimal.valueOf(list.get(j).getNum()))).doubleValue();
                }
            }
        }
        //四舍五入
        totalPrice=BigDecimal.valueOf(totalPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("价格："+totalPrice);

        Map dataMap=new HashMap();
        dataMap.put("totalPrice",totalPrice);
        dataMap.put("dishDTOS",dishDTOS);
        dataMap.put("list",list);
        return dataMap;
    }

}



