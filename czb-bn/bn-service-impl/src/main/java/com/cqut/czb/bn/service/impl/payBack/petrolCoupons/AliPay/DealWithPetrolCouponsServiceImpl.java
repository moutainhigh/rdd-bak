package com.cqut.czb.bn.service.impl.payBack.petrolCoupons.AliPay;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.paymentCallBack.AliPetrolCouponsDTO;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import com.cqut.czb.bn.service.PaymentProcess.DataProcessService;
import com.cqut.czb.bn.service.PaymentProcess.DealWithPetrolCouponsService;
import com.cqut.czb.bn.service.PaymentProcess.RequestLuPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class DealWithPetrolCouponsServiceImpl implements DealWithPetrolCouponsService {

    @Autowired
    PetrolCouponsSalesRecordsMapperExtra extra;

    @Autowired
    private DataProcessService dataProcessService;

    @Autowired
    RequestLuPayService requestLuPayService;

    @Override
    public int getAddBuyPetrolCouponsAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
//        解析订单信息
        AliPetrolCouponsDTO petrolCouponsDTO=new AliPetrolCouponsDTO();
        //存入第三方订单号
        if(params.get("trade_no")==null){
            return 0;
        }
         petrolCouponsDTO.setThirdOrderId(params.get("trade_no"));
        //存入其他业务信息
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            if ("orgId".equals(temp[0])) {
                petrolCouponsDTO.setOrgId(temp[1]);
                System.out.println("商家订单orgId:" +temp[1]);
            }else if ("money".equals(temp[0])) {
                petrolCouponsDTO.setPetrolPrice(Double.valueOf(temp[1]));
                System.out.println("支付金额:money" + temp[1]);
            }else if ("ownerId".equals(temp[0])) {
                petrolCouponsDTO.setUserId(temp[1]);
                System.out.println("用户id:" + temp[1]);
            }else  if ("petrolNum".equals(temp[0])) {
                petrolCouponsDTO.setPetrolNum(temp[1]);
                System.out.println("中石化编码:" + temp[1]);
            }else  if ("userAccount".equals(temp[0])) {
                petrolCouponsDTO.setUserAccount(temp[1]);
                System.out.println("用户电话:" + temp[1]);
            }else  if ("area".equals(temp[0])) {
                petrolCouponsDTO.setArea(temp[1]);
                System.out.println("地区:" + temp[1]);
            }

        }

        //修改用户订单
        updatePetrolSaleRecords(petrolCouponsDTO);

        //申请璐付订单
        requestLuPayService.httpRequestGETLuPay(petrolCouponsDTO);
//        //查询是否为首次消费
//        dataProcessService.isHaveConsumption(petrolCouponsDTO.getUserId());
//        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务,5为点餐,6为购买优惠券
//        //插入消费记录
//        dataProcessService.insertConsumptionRecord(petrolCouponsDTO.getOrgId(),petrolCouponsDTO.getThirdOrderId(),
//                petrolCouponsDTO.getPetrolPrice(), petrolCouponsDTO.getUserId(), "5", 1);

        return 1;
    }

    //插入消费记录
    public void updatePetrolSaleRecords(AliPetrolCouponsDTO dto){
        PetrolCouponsSalesRecords records=new PetrolCouponsSalesRecords();
        records.setRecordId(dto.getOrgId());
        records.setBuyerId(dto.getUserId());
        records.setToRddOutTradeNo(dto.getOrgId());
        records.setToRddThirdOrderId(dto.getThirdOrderId());
        records.setToRddTurnoverAmount(dto.getPetrolPrice());
        records.setToRddTransactionTime(new Date());
        records.setToRddState(1);
        records.setCreateAt(new Date());
        records.setUpdateAt(new Date());
        int is= extra.updateByPrimaryKeySelective(records);
        System.out.println("插入支付宝起吊信息"+(is>0));
    }

}
