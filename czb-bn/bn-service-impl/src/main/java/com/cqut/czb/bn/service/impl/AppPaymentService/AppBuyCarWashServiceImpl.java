package com.cqut.czb.bn.service.impl.AppPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanServerVehicleMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppCleanServerVehicleDTO;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.CleanServerVehicleDTO;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppVehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.conpons;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanServerVehicle;
import com.cqut.czb.bn.service.AppBuyCarWashService;
import com.cqut.czb.bn.service.AppCarWashService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;
import java.util.UUID;

@Service("AppBuyCarWashService")
public class AppBuyCarWashServiceImpl implements AppBuyCarWashService {

    @Autowired
    VehicleCleanOrderMapperExtra vehicleCleanOrderMapperExtra;

    @Autowired
    CleanServerVehicleMapperExtra cleanServerVehicleMapperExtra;

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;

    @Autowired
    AppCarWashService appCarWashService;

    @Override
    public String AliBuyCarWash(User user, CleanServerVehicleDTO cleanServerVehicleDTO) {
        //判空
        if(user==null&&cleanServerVehicleDTO==null){
            System.out.println("用户信息不全");
            return null;
        }
        Double couponMoney=0.0;

        if(cleanServerVehicleDTO.getCouponId()!=null&&!cleanServerVehicleDTO.getCouponId().equals("")){
            List<conpons> k=serverCouponMapperExtra.selectCoupons( user.getUserId(), cleanServerVehicleDTO.getCouponId());
            if(k.size()>0){
                couponMoney=k.get(0).getStandardValue();
            }
        }

        //查出产品的信息
        ServiceCommodityDTO commodity= appCarWashService.selectOneService(cleanServerVehicleDTO.getServerId());

        if(commodity==null){
            System.out.println("没有此类商品");
            return null;
        }

     //生成起吊参数
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("4");//"4"为购买洗车服务
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //订单标识
        String thirdOrder = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付金额
        Double actualPrice=BigDecimal.valueOf(commodity.getCurrentPrice()).subtract(BigDecimal.valueOf(couponMoney)).doubleValue();
        //商品id
        String serviceId= cleanServerVehicleDTO.getServerId();
        //优惠劵id
        String couponId=cleanServerVehicleDTO.getCouponId();
        //购买者id
        String ownerId = user.getUserId();
        request.setBizModel(AliParameterConfig.getBizModelBuyCarWash(couponId,thirdOrder, actualPrice,serviceId ,ownerId));//支付订单
        request.setNotifyUrl(AliPayConfig.BuyCarWash_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        InsertBusinessInfo(1,thirdOrder,user,cleanServerVehicleDTO,commodity);//payMethod 1为支付宝，2为微信

        return orderString;
    }

    @Override
    public JSONObject WeChatBuyCarWash(User user, CleanServerVehicleDTO cleanServerVehicleDTO) {
        //判空
        if(user==null&&cleanServerVehicleDTO==null){
            System.out.println("用户信息不全");
            return null;
        }
        Double couponMoney=0.0;

        if(cleanServerVehicleDTO.getCouponId()!=null&&!cleanServerVehicleDTO.getCouponId().equals("")){
            List<conpons> k=serverCouponMapperExtra.selectCoupons( user.getUserId(), cleanServerVehicleDTO.getCouponId());
            if(k.size()!=0){
                couponMoney=k.get(0).getStandardValue();
            }
        }

        //查出产品的信息
        ServiceCommodityDTO commodity= appCarWashService.selectOneService(cleanServerVehicleDTO.getServerId());

        if(commodity==null){
            System.out.println("没有此类商品");
            return null;
        }

        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String nonceStrTemp = WeChatUtils.getRandomStr();
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersBuyCarWash(cleanServerVehicleDTO.getCouponId(),couponMoney,nonceStrTemp,orgId,user.getUserId(),commodity.getCurrentPrice(),cleanServerVehicleDTO.getServerId());

        InsertBusinessInfo(2,orgId,user,cleanServerVehicleDTO,commodity);
        return  WeChatParameterConfig.getSign( parameters, nonceStrTemp);
    }

    /**
     * 插入业务信息
     */
    public void InsertBusinessInfo(int payMethod,String thirdOrder,User user,CleanServerVehicleDTO cleanServerVehicleDTO,ServiceCommodityDTO serviceCommodityDTO){

        //服务车辆id
        String vehicleId=StringUtil.createId();

        //查出是否有信息
        CleanServerVehicle v=cleanServerVehicleMapperExtra.selectCleanServerVehicle(user.getUserId());
        //洗车服务车辆对象表	czb_clean_server_vehicle  录入备份信息
        AppCleanServerVehicleDTO cleanSV=new AppCleanServerVehicleDTO();
        if(v!=null){
            vehicleId=v.getVehicleId();
            cleanSV.setUserId(user.getUserId());
            cleanSV.setUserName(cleanServerVehicleDTO.getUserName());
            cleanSV.setLicenseNumber(cleanServerVehicleDTO.getLicenseNumber());
            cleanSV.setVehicleColor(cleanServerVehicleDTO.getVehicleColor());
            cleanSV.setVehicleSeries(cleanServerVehicleDTO.getVehicleSeries());
            cleanSV.setServiceLocation(cleanServerVehicleDTO.getServiceLocation());
            cleanSV.setPhone(cleanServerVehicleDTO.getPhone());
            boolean j=cleanServerVehicleMapperExtra.updateByUserId(cleanSV)>0;
            System.out.println("更改车辆信息"+j);
        }else {
            cleanSV.setVehicleId(vehicleId);
            cleanSV.setUserId(user.getUserId());
            cleanSV.setUserName(cleanServerVehicleDTO.getUserName());
            cleanSV.setLicenseNumber(cleanServerVehicleDTO.getLicenseNumber());
            cleanSV.setVehicleColor(cleanServerVehicleDTO.getVehicleColor());
            cleanSV.setVehicleSeries(cleanServerVehicleDTO.getVehicleSeries());
            cleanSV.setServiceLocation(cleanServerVehicleDTO.getServiceLocation());
            cleanSV.setPhone(cleanServerVehicleDTO.getPhone());
            boolean j=cleanServerVehicleMapperExtra.insert(cleanSV)>0;
            System.out.println("插入车辆信息"+j);
        }


        //洗车服务订单记录	czb_vehicle_clean_order 插入预支付订单
        AppVehicleCleanOrderDTO vehicleCO=new AppVehicleCleanOrderDTO();
        vehicleCO.setServerOrderId(thirdOrder);
        vehicleCO.setUserId(user.getUserId());
        //骑手没有放入
        vehicleCO.setPayStatus((byte)0);
        //前要注意下
        vehicleCO.setVehicleId(vehicleId);
        vehicleCO.setProcessStatus((byte)0);

        vehicleCO.setServerId(cleanServerVehicleDTO.getServerId());
        vehicleCO.setPayMethod(payMethod);
        vehicleCO.setPhone(cleanSV.getPhone());
        vehicleCO.setUserName(cleanSV.getUserName());
        vehicleCO.setLicenseNumber(cleanSV.getLicenseNumber());
        vehicleCO.setVehicleColor(cleanSV.getVehicleColor());
        vehicleCO.setServiceLocation(cleanSV.getServiceLocation());
        vehicleCO.setVehicleSeries(cleanSV.getVehicleSeries());
        boolean K=vehicleCleanOrderMapperExtra.insert(vehicleCO)>0;
        System.out.println("插入洗车服务预支付订单"+K);
    }

}


