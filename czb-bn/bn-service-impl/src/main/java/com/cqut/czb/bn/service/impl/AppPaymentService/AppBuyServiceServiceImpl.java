package com.cqut.czb.bn.service.impl.AppPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.CommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.CommodityUserInfoCollectionMapperExtra;
import com.cqut.czb.bn.dao.mapper.OrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserInfoCollectedMapperExtra;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityUserInfoCollectionDTO;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.appBuyService.BuyServiceDTO;
import com.cqut.czb.bn.entity.entity.Commodity;
import com.cqut.czb.bn.entity.entity.Order;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.UserInfoCollected;
import com.cqut.czb.bn.service.AppBuyServiceService;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONArray;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppBuyServiceServiceImpl implements AppBuyServiceService {

    @Autowired
    public CommodityMapperExtra commodityMapperExtra;

    @Autowired
    public OrderMapperExtra orderMapperExtra;


    @Autowired
    public CommodityUserInfoCollectionMapperExtra commodityUserInfoCollectionMapperExtra;

    @Autowired
    public UserInfoCollectedMapperExtra userInfoCollectedMapperExtra;

    @Override
    public String AliPayBuyService(User user,BuyServiceDTO buyServiceDTO) {

        if(user==null||buyServiceDTO==null){
            return null;
        }
        //查出商品
        CommodityDTO commodityDTO=commodityMapperExtra.selectOneCommodity(buyServiceDTO);
        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("3");//"3"为购买服务
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        Double actualPrice=commodityDTO.getCommodityPrice();
        //商品id
        String commodityId=commodityDTO.getCommodityId();
        //购买者id
        String ownerId = user.getUserId();
        request.setBizModel(AliParameterConfig.getBizModelBuyService(thirdOrder, actualPrice,commodityId ,ownerId));//支付订单
        request.setNotifyUrl(AliPayConfig.BuyService_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //插入必填信息及生成预支付订单
        insertInfo(thirdOrder,commodityDTO,user,buyServiceDTO);
        return orderString;
    }

    @Override
    public JSONObject WeChatBuyService(User user, BuyServiceDTO buyServiceDTO) {

        //查出商品
        CommodityDTO commodityDTO=commodityMapperExtra.selectOneCommodity(buyServiceDTO);

        buyServiceDTO.setUserId(user.getUserId());

        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String nonceStrTemp = WeChatUtils.getRandomStr();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersService(nonceStrTemp,orgId,user.getUserId(),commodityDTO);

        //插入必填信息及生成预支付订单
        insertInfo(orgId,commodityDTO,user,buyServiceDTO);

        return  WeChatParameterConfig.getSign( parameters, nonceStrTemp);

    }

    /**
     * 将必填信息插入数据库
     */
    public boolean insertInfo(String thirdOrder, CommodityDTO commodityDTO,User user, BuyServiceDTO buyServiceDTO){
        //插入订单
        Order order=new Order();
        order.setId(thirdOrder);
        order.setActualPrice(commodityDTO.getCommodityPrice());
        order.setCommodityId(commodityDTO.getCommodityId());
        order.setPayMethod(0);
        order.setShopId(commodityDTO.getShopId());
        order.setState(0);
        order.setComsumerId(user.getUserId());
        order.setTotalCount(commodityDTO.getUsageCount());
        order.setThirdOrder("");
        int insertOrder=orderMapperExtra.insert(order);
        System.out.println("插入订单"+(insertOrder>0));

        //插入必填信息
//      String str = "[{'infoTitle':'姓名','infoContent':'强爸爸'},{'infoTitle':'电话','infoContent':'147852963321'},{'infoTitle':'地址','infoContent':'卡蒂克'}]" ;  // 一个未转化的字符串
        String str=buyServiceDTO.getInputInfo();
        System.out.println(str);
        List<UserInfoCollected> userInfoCollectedList=  new ArrayList<UserInfoCollected>();
        //查出此产品的必填信息
        List<CommodityUserInfoCollectionDTO> commodityUserInfoCollectionDTOS= commodityUserInfoCollectionMapperExtra.selectInfoInput(commodityDTO.getCommodityId());
        if(commodityUserInfoCollectionDTOS.size()>0){
            JSONArray json = JSONArray.fromObject(str); // 首先把字符串转成 JSONArray  对象
            if(json.size()>0) {
                for (int j = 0; j < commodityUserInfoCollectionDTOS.size(); j++) {
                    UserInfoCollected userInfoCollected = new UserInfoCollected();
                    for (int i = 0; i < json.size(); i++) {
                        net.sf.json.JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        System.out.println(job.get("infoTitle"));  // 得到 每个对象中的属性值
                        System.out.println(job.get("infoContent"));
                        if (commodityUserInfoCollectionDTOS.get(j).getInfoTitle().equals((String) job.get("infoTitle"))) {
                            userInfoCollected.setContent((String) job.get("infoContent"));
                            userInfoCollected.setId(StringUtil.createId());
                            userInfoCollected.setOrderId(thirdOrder);
                            userInfoCollected.setInfoId(commodityUserInfoCollectionDTOS.get(j).getInfoId());
                            break;
                        }
                    }
                    userInfoCollectedList.add(userInfoCollected);
                }
                System.out.println("共有条"+userInfoCollectedList.size());
                boolean isInsert=userInfoCollectedMapperExtra.insert(userInfoCollectedList)>0;
                System.out.println("成功插入userInfoCollectedList"+isInsert);
            }
            return true;
        }
        return true;
    }

}
