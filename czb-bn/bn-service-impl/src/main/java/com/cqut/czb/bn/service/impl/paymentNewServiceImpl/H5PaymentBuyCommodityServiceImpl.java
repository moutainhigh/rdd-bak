package com.cqut.czb.bn.service.impl.paymentNewServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.H5PaymentBuyCommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.AttributeMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityAttrMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatStockMapperExtra;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.*;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPayService;
import com.cqut.czb.bn.service.impl.paymentNewServiceImpl.payBackImpl.PayBackServiceImpl;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyCommodityService;
import com.cqut.czb.bn.service.paymentNew.H5PaymentBuyIntegralService;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class H5PaymentBuyCommodityServiceImpl implements H5PaymentBuyCommodityService{

    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    WeChatStockMapperExtra weChatStockMapperExtra;

    @Autowired
    WeChatAppletPayService weChatAppletPayService;

    @Autowired
    AttributeMapper attributeMapper;

    @Autowired
    WeChatCommodityAttrMapperExtra weChatCommodityAttrMapperExtra;

    @Autowired
    VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    PayBackServiceImpl payBackService;

    @Autowired
    H5PaymentBuyCommodityMapperExtra h5PaymentBuyCommodityMapperExtra;

    private ReentrantLock lock = new ReentrantLock();

    private ScheduledExecutorService scheduledExecutorService= new ScheduledThreadPoolExecutor(2,
            new BasicThreadFactory.Builder().namingPattern("H5StockDTO-schedule-pool-%d").daemon(true).build());;

    public synchronized boolean judgeChangeSte(int flag,String stockId,String userId) {
        // 状态改变
        if (flag == 0) {
            String buyerId = h5PaymentBuyCommodityMapperExtra.getBuyerId(stockId);

            if (!buyerId.equals(userId)) {
                return false;
            }
            return true;
        } else if (flag == 1) {
            return !(h5PaymentBuyCommodityMapperExtra.updateTheStockState(stockId) > 0);
        }
        return false;
    }
    /**
     * 微信小程序库存商品支付
     * @param user
     * @param h5StockDTO
     * @return
     */
    @Override
    @Transactional
    public synchronized com.alibaba.fastjson.JSONObject WeChatAppletPaymentBuyCommodity(User user, H5StockDTO h5StockDTO) {

        //商品信息为空
        if (h5StockDTO == null){
            return null;
        }
        String stockId = h5PaymentBuyCommodityMapperExtra.getStockId(h5StockDTO);
        if (stockId != null) {
            h5StockDTO.setStockId(stockId);
        } else {
            System.out.println("未找到数据");
        }

        // 修改状态
        int modify = h5PaymentBuyCommodityMapperExtra.updateState(h5StockDTO);

        //计时器——5分钟之后执行
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!judgeChangeSte(1, stockId, null)){
                    scheduledExecutorService.shutdown();
                }
            }
        },0,5, TimeUnit.MINUTES);
        //计时器——5分钟之后执行
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
////                System.out.println("改变状态状态计时器");
////                int weChatStocks = weChatStockMapperExtra.selectStockState(ids);
//                if (!judgeChangeSte(1, stockId)){
//                    timer.cancel();
//                }
//            }
//        }, 300000);
        String nonceStrTemp = WeChatUtils.getRandomStr();

        SortedMap<String, Object> parameters = WeChatH5ParameterConfig.getParametersPaymentApplet(nonceStrTemp,h5StockDTO);

        return WeChatH5ParameterConfig.getSign(parameters, nonceStrTemp);
    }

    /**
     * 支付宝库存商品支付
     * @param user
     * @param h5StockDTO
     * @return
     */
    @Transactional
    @Override
    public synchronized String AliAppletPaymentBuyCommodity(User user, H5StockDTO h5StockDTO) {


        //商品信息为空
        if (h5StockDTO == null){
            return null;
        }

        //抓取数据
        String stockId = h5PaymentBuyCommodityMapperExtra.getStockId(h5StockDTO);
        if (stockId != null) {
            h5StockDTO.setStockId(stockId);
        } else {
            System.out.println("未找到数据");
        }

        // 修改状态
        int modify = h5PaymentBuyCommodityMapperExtra.updateState(h5StockDTO);

        //计时器——5分钟之后执行
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!judgeChangeSte(1, stockId,null)){
                    scheduledExecutorService.shutdown();
                }
            }
        },0,5, TimeUnit.MINUTES);

        double couponMoney = 0.0;
        //生成起调参数串
        //生成起吊参数
        //用于保存起调参数,
        String orderString = null;
        //"0"为购买积分
        AlipayNewClientConfig alipayClientConfig = AlipayNewClientConfig.getInstance("1");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        double actualPrice= BigDecimal.valueOf(h5StockDTO.getPrice()).subtract(BigDecimal.valueOf(couponMoney)).doubleValue();
//
//        购买者id
//        String ownerId = user.getUserId();
//                String userId = user.getUserId();
//        String ownerId = "703614235874580972";
        String ownerId = user.getUserId();
        System.out.println("积分userId" + ownerId);
        //支付订单
        request.setBizModel(AliParameterNewConfig.getBizModelICommodityCoupons(actualPrice,thirdOrder,user.getUserId(),h5StockDTO));
        request.setReturnUrl(AliPayH5Config.CommodityReturn_url);
        //支付回调接口
        request.setNotifyUrl(AliPayH5Config.CommodityRecharge_url);
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);;
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderString;
    }

    /**
     * 计算价格
     * @param
     * @param weChatCommodity
     * @param payInputDTO
     * @return
     */
    public Map getPayment(WeChatCommodity weChatCommodity, PayInputDTO payInputDTO){
        Map map=new HashMap();

        Double money= BigDecimal.valueOf(weChatCommodity.getSalePrice()).multiply(BigDecimal.valueOf(Integer.valueOf(payInputDTO.getCommodityNum()))).doubleValue();

        Double fyMoney=BigDecimal.valueOf(weChatCommodity.getFyMoney()).multiply(BigDecimal.valueOf(payInputDTO.getCommodityNum())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        if(payInputDTO.getCommodityAttrIds()==null||"".equals(payInputDTO.getCommodityAttrIds())){
            map.put("money",money);
            map.put("fyMoney",fyMoney);
            return map;
        }

        //获取属性值
        List<String> list = Arrays.asList(payInputDTO.getCommodityAttrIds().split(","));
        //存在属性值
        List<WeChatCommodityAttr> weChatCommodityAttrs= weChatCommodityAttrMapperExtra.selectByPrimaryKeys(list);

        if(weChatCommodityAttrs==null){
            map.put("money",money);
            map.put("fyMoney",fyMoney);
            return map;
        }

        for (int i=0;i<weChatCommodityAttrs.size();i++){
            fyMoney=BigDecimal.valueOf(fyMoney).add(BigDecimal.valueOf(weChatCommodityAttrs.get(i).getExtraFyMoney()).multiply(BigDecimal.valueOf(payInputDTO.getCommodityNum()))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            money=BigDecimal.valueOf(money).add(BigDecimal.valueOf(weChatCommodityAttrs.get(i).getExtraSaleMoney()).multiply(BigDecimal.valueOf(payInputDTO.getCommodityNum()))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        map.put("money",money);
        map.put("fyMoney",fyMoney);
        return map;
    }

    /**
     * 插入订单
     * @param
     * @param orgId
     * @param weChatCommodity
     * @param user
     * @param payInputDTO
     * @param fyMoney
     * @param money
     * @param ids
     * @param payMethod
     * @return
     */
    public boolean inputOrder(String orgId, WeChatCommodity weChatCommodity, User user, PayInputDTO payInputDTO, double fyMoney, double money, List<WeChatStock> ids,Integer payMethod){
        //插入预支付订单
        WeChatCommodityOrder weChatCommodityOrder=new WeChatCommodityOrder();
        weChatCommodityOrder.setOrderId(orgId);
        weChatCommodityOrder.setUserId(user.getUserId());
        weChatCommodityOrder.setCommodityId(weChatCommodity.getCommodityId());
        weChatCommodityOrder.setShopId(weChatCommodity.getShopId());

        //实际支付的价格
        weChatCommodityOrder.setActualPrice(money);

        weChatCommodityOrder.setPayStatus(0);
        weChatCommodityOrder.setPayMethod(payMethod);
        weChatCommodityOrder.setRemark(payInputDTO.getRemark());

        //插入电子码；
        Map mapCode=new HashMap();
        //获取电子码
        List<String> listCode = weChatStockMapperExtra.selectElectronicCode(ids);
        JSONArray jsonObjCode = (JSONArray) JSONArray.toJSON(listCode);// 数组转为JsonArray
        String jsonCode = "{";
        for (int i = 0; i < jsonObjCode.size(); i++){
            String newCode = jsonObjCode.getString(i).substring(1, jsonObjCode.getString(i).length() - 1) + ",";
            jsonCode += newCode;
        }
        if (jsonObjCode.size()!=0){
            jsonCode = jsonCode.substring(0, jsonCode.length() - 1);
        }
        jsonCode += "}";
        weChatCommodityOrder.setElectronicCode(jsonCode);

        //0：待支付  1：支付完成待处理 2：订单完成
        weChatCommodityOrder.setOrderState(0);

        //前台给地址id
        weChatCommodityOrder.setAddressId(payInputDTO.getAddressId());

        //插入二维码
//        weChatCommodityOrder.setQrcode("wx:shopId="+weChatCommodity.getShopId()+"&orderId="+orgId);

        weChatCommodityOrder.setPhone(payInputDTO.getUserPhone());
        //来源
        weChatCommodityOrder.setCommoditySource("本地商家");

        //返佣金额
        weChatCommodityOrder.setFyMoney(fyMoney);

        //成本价格
        double costMoney=BigDecimal.valueOf(weChatCommodity.getCostPrice()).multiply(BigDecimal.valueOf(Integer.valueOf(payInputDTO.getCommodityNum()))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        weChatCommodityOrder.setCostPrice(costMoney);

        //商品类型 3：库存
        weChatCommodityOrder.setCommodityType(3);

        //插入属性值；
        Map map=new HashMap();
        //获取属性值
        List<String> list = Arrays.asList(payInputDTO.getCommodityAttrIds().split(","));
        //存在属性值
        List<WeChatCommodityAttr> attrs= weChatCommodityAttrMapperExtra.selectByPrimaryKeys(list);
        for(int i=0;i<attrs.size();i++){
            Attribute attribute=attributeMapper.selectByPrimaryKey(attrs.get(i).getAttributeId());
            map.put(attribute.getName(),attribute.getContent());
        }
        //将map转化成json
        JSONObject jsonObject=new JSONObject(map);
        //json对象转换成json字符串
        String jsonStr=jsonObject.toString();
        System.out.println(jsonStr);
        weChatCommodityOrder.setAttrInfo(jsonStr);
        //商品类目id
        weChatCommodityOrder.setCommmodityTypeId(weChatCommodity.getCommmodityTypeId());
        //商品类型
        weChatCommodityOrder.setCommmodityTypeId(weChatCommodity.getCommmodityTypeId());
        weChatCommodityOrder.setCreateAt(new Date());
        weChatCommodityOrder.setCommodityNum(Integer.valueOf(payInputDTO.getCommodityNum()));
        return weChatStockMapperExtra.insertSelective(weChatCommodityOrder)>0;
    }

    /**
     *
     * @param
     * @return
     */
    public WeChatBackDTO getBackObject(JSONObject jsonObject){
        String string="appId="+jsonObject.get("appid")+"&nonceStr="+jsonObject.get("noncestr")+
                "&package=prepay_id="+jsonObject.get("prepayid")+"&signType=MD5&timeStamp="+jsonObject.get("timestamp")+
                "&key="+ WeChatPayConfig.skey;
        String paySignStr= MD5Util.MD5Encode(string,"UTF-8");
        WeChatBackDTO weChatBackDTO=new WeChatBackDTO();
        weChatBackDTO.setJsonObject(jsonObject);
        weChatBackDTO.setPaySignStr(paySignStr);
        return weChatBackDTO;
    }

}
