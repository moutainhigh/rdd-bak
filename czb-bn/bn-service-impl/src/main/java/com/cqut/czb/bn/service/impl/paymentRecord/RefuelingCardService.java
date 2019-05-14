package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.IRefuelingCard;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.service.petrolRecharge.FanYongService;
import com.cqut.czb.bn.service.petrolRecharge.PetrolRecharge;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RefuelingCardService implements IRefuelingCard {

	@Autowired
	private PetrolMapperExtra petrolMapperExtra;

	@Autowired
	private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

	@Autowired
	FanYongService fanYongService;

	@Autowired
	PetrolRecharge petrolRecharge;

	@Autowired
	PetrolDeliveryRecordsMapper petrolDeliveryRecordsMapper;

    // 同一时间只允许一个线程访问购买油卡接口
    public synchronized Map payCallback(Object[] param) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if (getAddPaymentRecordData(params) == 1) {//获取订单数据存入数据库(支付宝)
            result.put("success", AlipayConfig.response_success);
            return result;
        } else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
    }

    @Override
    public void purchaseFailed(Object[] param) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = getOrderdata(params);
        System.out.println("购买失败删除前" + PetrolCache.AllpetrolMap + ":" + PetrolCache.currentPetrolMap);
        if (petrolSalesRecordsDTO == null)
            return;
        Petrol petrol = PetrolCache.currentPetrolMap.get(petrolSalesRecordsDTO.getPetrolNum());
        petrol.setOwnerId("");
        petrol.setEndTime(0);
        PetrolCache.currentPetrolMap.remove(petrolSalesRecordsDTO.getPetrolNum());//移除
        PetrolCache.AllpetrolMap.put(petrol.getPetrolNum(), petrol);//放入
        System.out.println("购买失败删除后" + PetrolCache.AllpetrolMap + ":" + PetrolCache.currentPetrolMap);
    }


    /**
     * 解析订单数据用于处理（成功此块有点冗余）
     */
    public static PetrolSalesRecordsDTO getOrderdata(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        // petrol_record主键
        String id = "";
        // 0代表充值，1代表购油——对应payType
        String payType = "";
        double money = 0;
        int count = 0;
        int petrolKind = 0;
        String petrolNum = "";
        String ownerId = "";
        double actualPayment = 0;
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = new PetrolSalesRecordsDTO();
        petrolSalesRecordsDTO.setPaymentMethod(0);
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            if ("orgId".equals(temp[0])) {
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setThirdOrderId(temp[1]);
            }
            if ("payType".equals(temp[0])) {
                System.out.println(temp[0] + ":" + temp[1]);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPayType(Integer.parseInt(temp[1]));
            }
            if ("money".equals(temp[0])) {
                System.out.println("充值金额:money" + money);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPetrolPrice(Double.valueOf(temp[1]));
            }
            if ("count".equals(temp[0])) {
                System.out.println("购买数量count:" + count);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setCount(Integer.parseInt(temp[1]));
            }
            if ("petrolKind".equals(temp[0])) {
                System.out.println("油卡类型petrolKind:" + petrolKind);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPetrolKind(Integer.parseInt(temp[1]));
            }
            if ("petrolNum".equals(temp[0])) {
                System.out.println("油卡号petrolNum:" + petrolNum);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPetrolNum(temp[1]);
            }
            if ("ownerId".equals(temp[0])) {
                System.out.println("用户id:" + ownerId);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setBuyerId(temp[1]);
            }
            if ("actualPayment".equals(temp[0])) {
                System.out.println("实际支付actualPayment:" + actualPayment);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setActualPayment(Double.valueOf(temp[1]));
            }
        }
        return petrolSalesRecordsDTO;
    }


    /**
     * 获取订单数据存入数据库(支付宝)
     */
    public synchronized int getAddPaymentRecordData(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        // petrol_record主键
        String orgId = "";//第三方订单
        // payType"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
        String payType = "";
        double money = 0;
        int count = 0;
        int petrolKind = 0;
        String petrolNum = "";
        String ownerId = "";
        String addressId = "";
        double actualPayment = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
            }
            if ("payType".equals(temp[0])) {
                System.out.println(temp[0] + ":" + temp[1]);
                payType = temp[1];
            }
            if ("money".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
                System.out.println("充值金额:money" + money);
            }
            if ("count".equals(temp[0])) {
                count = Integer.parseInt(temp[1]);
                System.out.println("购买数量count:" + count);
            }
            if ("petrolKind".equals(temp[0])) {
                petrolKind = Integer.parseInt(temp[1]);
                System.out.println("油卡类型petrolKind:" + petrolKind);
            }
            if ("petrolNum".equals(temp[0])) {
                petrolNum = temp[1];
                System.out.println("油卡号petrolNum:" + petrolNum);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("actualPayment".equals(temp[0])) {
                actualPayment = Double.valueOf(temp[1]);
                System.out.println("实际支付actualPayment:" + actualPayment);
            }
            if ("addressId".equals(temp[0])) {
                addressId = temp[1];
                System.out.println("用户id:" + addressId);
            }
        }

        //payType对应"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
        if ("2".equals(payType)) {
            System.out.println("开始充值0");
            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(money, count,
                    petrolKind, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true)
                return 1;
            else
                return 2;
        } else if ("0".equals(payType)) {
//			此处插入购油的相关信息，油卡购买记录
			boolean ischange=changeInfo( money, count, petrolKind, petrolNum, ownerId,actualPayment,addressId, orgId);

			//若插入失败则放回卡
			if(ischange!=true){
				Petrol petrol= PetrolCache.currentPetrolMap.get(petrolNum);
				if(petrol == null){
					return 2;
				}
				petrol.setOwnerId("");
				petrol.setEndTime(0);
				PetrolCache.AllpetrolMap.put(petrolNum,petrol);//放回all中
				PetrolCache.currentPetrolMap.remove(petrolNum);
				return 2;
			}

			//成功后移除对应的卡
			PetrolCache.currentPetrolMap.remove(petrolNum);
			return 1;

		} else if ("1".equals(payType)) {
			System.out.println("优惠卷");
//			modifyAndInsertCoupons(count, id, money);
		}
		return 1;
	}

	/**
	 * 进行所有的操作——相关表的增删改查（油卡表，新增购买记录表，收益变更记录表，用户收益信息表）
	 * @return
	 */
	public boolean changeInfo(double money,int count,int petrolKind,String petrolNum,String ownerId,double actualPayment,String addressId,String orgId){
		//油卡表——更改相应油卡的状态（用户的id，卡号）——更改
		//取出油卡
		Petrol petrol=PetrolCache.currentPetrolMap.get(petrolNum);
		if(petrol==null)
		{
			System.out.println("油卡为空");
			return false;
		}
		petrol.setOwnerId(ownerId);
		petrol.setState(2);
		boolean updatePetrol=updatePetrol(petrol);
		System.out.println("更改油卡状态完毕"+updatePetrol);

		//新增购买记录表——插入
		PetrolSalesRecords petrolSalesRecords=new PetrolSalesRecords();
		petrolSalesRecords.setPetrolId(petrol.getPetrolId());
		petrolSalesRecords.setBuyerId(ownerId);
		petrolSalesRecords.setPaymentMethod(1);//1为支付宝支付
		petrolSalesRecords.setPetrolKind(petrolKind);//油卡种类
		petrolSalesRecords.setPetrolNum(petrolNum);//卡号
		petrolSalesRecords.setRecordId(StringUtil.createId());
		petrolSalesRecords.setState(1);//1为已支付
		petrolSalesRecords.setTurnoverAmount(petrol.getPetrolPrice());
		petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());
		petrolSalesRecords.setThirdOrderId(orgId);
		petrolSalesRecords.setRecordType(1);
		petrolSalesRecords.setIsRecharged(1);
		boolean insertPetrolSalesRecords=insertPetrolSalesRecords(petrolSalesRecords);
		System.out.println("新增购买记录表完毕"+insertPetrolSalesRecords);

		//新增油卡邮寄记录——插入
		PetrolDeliveryRecords petrolDeliveryRecords=new PetrolDeliveryRecords();
		petrolDeliveryRecords.setAddressId(addressId);
		petrolDeliveryRecords.setPetrolNum(petrolNum);
		petrolDeliveryRecords.setDeliveryState(0);
		petrolDeliveryRecords.setRecordId(StringUtil.createId());
		boolean isInsert=petrolDeliveryRecordsMapper.insert(petrolDeliveryRecords)>0;
		System.out.println("新增油卡邮寄记录"+isInsert);

        boolean beginFanYong = fanYongService.beginFanYong(ownerId, money, actualPayment, orgId);

        if (beginFanYong == true && insertPetrolSalesRecords == true)
            return true;
        else {
            System.out.println("新增购买记录表有问题或beginFanYong");
            return false;
        }
    }

    //油卡表——更改相应油卡的状态（用户的id，卡号）——更改
    @Override
    public boolean updatePetrol(Petrol petrol) {
        return petrolMapperExtra.updateByPrimaryKeySelective(petrol) > 0;
    }

    //新增购买记录表——插入
    @Override
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return petrolSalesRecordsMapperExtra.insert(petrolSalesRecords) > 0;
    }

}
