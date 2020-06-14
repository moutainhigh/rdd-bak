package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.AddressMapperExtra;
import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.ShopMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.*;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatBackDTO;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatPayConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Attribute;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityAttr;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPayService;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WeChatAppletPayServiceImpl implements WeChatAppletPayService {


    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    AddressMapperExtra addressMapperExtra;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    CategoryMapperExtra categoryMapperExtra;

    @Autowired
    WeChatCommodityAttrMapperExtra weChatCommodityAttrMapperExtra;

    @Autowired
    AttributeMapper attributeMapper;




    @Override
    public WeChatBackDTO WeChatAppletBuyCommodity(User user, PayInputDTO payInputDTO) {

        if (payInputDTO==null){
            return null;
        }

        //查出商品信息
        WeChatCommodity weChatCommodity=weChatCommodityMapper.selectByPrimaryKey(payInputDTO.getCommodityId());
        if(weChatCommodity==null){
            return null;
        }
        //计算价格
        Map map=getPayMoney(weChatCommodity,payInputDTO);
        if(map==null)
            return null;

        /**
         * 生成起调参数串——返回给app（微信的支付订单）
         */
        String orgId = String.valueOf(System.currentTimeMillis()+(int)(1+Math.random()*(10000-1+1)));
        String nonceStrTemp = WeChatUtils.getRandomStr();
        double money= (double) map.get("money");
        double fyMoney= (double) map.get("fyMoney");
        //插入订单
        inputOrder( orgId, weChatCommodity, user, payInputDTO,fyMoney,money);
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersApplet(user.getUserAccount(),money,nonceStrTemp,orgId,user.getUserId(),weChatCommodity);
        JSONObject jsonObject= WeChatParameterConfig.getSign( parameters, nonceStrTemp);
        return   getBackObject(jsonObject);
    }


    public Boolean inputOrder(String orgId,WeChatCommodity weChatCommodity,User user, PayInputDTO payInputDTO,double fyMoney,double money){
        //插入预支付订单
        WeChatCommodityOrder weChatCommodityOrder=new WeChatCommodityOrder();
        weChatCommodityOrder.setOrderId(orgId);
        weChatCommodityOrder.setUserId(user.getUserId());
        weChatCommodityOrder.setCommodityId(weChatCommodity.getCommodityId());
        weChatCommodityOrder.setShopId(weChatCommodity.getShopId());

        //实际支付的价格
        weChatCommodityOrder.setActualPrice(money);

        weChatCommodityOrder.setPayStatus(0);
        weChatCommodityOrder.setPayMethod(2);
        weChatCommodityOrder.setRemark(payInputDTO.getRemark());

        //需要生成电子码
        weChatCommodityOrder.setElectronicCode(orgId);

        //0：待支付  1：支付完成待处理 2：订单完成
        weChatCommodityOrder.setOrderState(0);

        //前台给地址id
        weChatCommodityOrder.setAddressId(payInputDTO.getAddressId());

        //插入二维码
        weChatCommodityOrder.setQrcode("wx:shopId="+weChatCommodity.getShopId()+"&orderId="+orgId);

        weChatCommodityOrder.setPhone(payInputDTO.getUserPhone());
        //来源
        weChatCommodityOrder.setCommoditySource("本地商家");

        //返佣金额
        weChatCommodityOrder.setFyMoney(fyMoney);

        //成本价格
        double costMoney=BigDecimal.valueOf(weChatCommodity.getCostPrice()).multiply(BigDecimal.valueOf(Integer.valueOf(payInputDTO.getCommodityNum()))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        weChatCommodityOrder.setCostPrice(costMoney);

        //商品类型 1:寄送 2：核销
        if(weChatCommodity.getTakeWay()==1){
            weChatCommodityOrder.setCommodityType(1);
        }else if(weChatCommodity.getTakeWay()==2){
            weChatCommodityOrder.setCommodityType(2);
        }

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
        return weChatCommodityOrderMapper.insertSelective(weChatCommodityOrder)>0;
    }

    public WeChatBackDTO getBackObject(JSONObject jsonObject){
        String string="appId="+jsonObject.get("appid")+"&nonceStr="+jsonObject.get("noncestr")+
                "&package=prepay_id="+jsonObject.get("prepayid")+"&signType=MD5&timeStamp="+
                jsonObject.get("timestamp")+ "&key="+ WeChatPayConfig.skey;
        String paySignStr= MD5Util.MD5Encode(string,"UTF-8");
        WeChatBackDTO weChatBackDTO=new WeChatBackDTO();
        weChatBackDTO.setJsonObject(jsonObject);
        weChatBackDTO.setPaySignStr(paySignStr);
        return weChatBackDTO;
    }

    //计算价格
    public Map getPayMoney(WeChatCommodity weChatCommodity,PayInputDTO payInputDTO){

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

}
