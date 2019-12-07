package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.AddressMapperExtra;
import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.ShopMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.CategoryMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedMap;
import java.util.UUID;

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

    public Boolean inputOrder(String orgId,WeChatCommodity weChatCommodity,User user, PayInputDTO payInputDTO){
        //插入预支付订单
        WeChatCommodityOrder weChatCommodityOrder=new WeChatCommodityOrder();
        weChatCommodityOrder.setOrderId(orgId);
        weChatCommodityOrder.setUserId(user.getUserId());
        weChatCommodityOrder.setCommodityId(weChatCommodity.getCommodityId());
        weChatCommodityOrder.setShopId(weChatCommodity.getShopId());

        weChatCommodityOrder.setActualPrice(BigDecimal.valueOf(weChatCommodity.getSalePrice()).multiply(BigDecimal.valueOf(payInputDTO.getCommodityNum())).doubleValue());

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
        Dict dict1=dictMapperExtra.selectDictByName("sp_fy1");
        Dict dict2=dictMapperExtra.selectDictByName("sp_fy2");
        double money=BigDecimal.valueOf(weChatCommodity.getFyMoney()).multiply(BigDecimal.valueOf(payInputDTO.getCommodityNum())).doubleValue();
        weChatCommodityOrder.setFyMoney(money);

        //成本价格
        weChatCommodityOrder.setCostPrice(weChatCommodity.getCostPrice()*Integer.valueOf(payInputDTO.getCommodityNum()));

        //商品类型 1:寄送 2：核销
        if(weChatCommodity.getTakeWay()==1){
            weChatCommodityOrder.setCommodityType(1);
        }else if(weChatCommodity.getTakeWay()==2){
            weChatCommodityOrder.setCommodityType(2);
        }

        //商品类目id
        weChatCommodityOrder.setCommmodityTypeId(weChatCommodity.getCommmodityTypeId());
        //商品类型
        weChatCommodityOrder.setCommmodityTypeId(weChatCommodity.getCommmodityTypeId());
        weChatCommodityOrder.setCreateAt(new Date());
        weChatCommodityOrder.setCommodityNum(Integer.valueOf(payInputDTO.getCommodityNum()));
        return weChatCommodityOrderMapper.insertSelective(weChatCommodityOrder)>0;
    }

    @Override
    public JSONObject WeChatAppletBuyCommodity(User user, PayInputDTO payInputDTO) {
        //查出商品信息
        WeChatCommodity weChatCommodity=weChatCommodityMapper.selectByPrimaryKey(payInputDTO.getCommodityId());
        if(weChatCommodity==null){
            return null;
        }
        /**
         * 生成起调参数串——返回给app（微信的支付订单）
         */
        String orgId = String.valueOf(System.currentTimeMillis()+(int)(1+Math.random()*(10000-1+1)));
        String nonceStrTemp = WeChatUtils.getRandomStr();
        double money= BigDecimal.valueOf(weChatCommodity.getSalePrice()).multiply(BigDecimal.valueOf(Integer.valueOf(payInputDTO.getCommodityNum()))).doubleValue();

        //插入订单
        inputOrder( orgId, weChatCommodity, user,  payInputDTO);
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersApplet(user.getUserAccount(),money,nonceStrTemp,orgId,user.getUserId(),weChatCommodity);
        return WeChatParameterConfig.getSign( parameters, nonceStrTemp);
    }
}
