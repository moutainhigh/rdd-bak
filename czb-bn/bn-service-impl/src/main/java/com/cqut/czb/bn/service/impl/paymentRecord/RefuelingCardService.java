package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.IRefuelingCard;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.service.petrolRecharge.FanYongService;
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
	private UserIncomeInfoMapper userIncomeInfoMapper;

	@Autowired
	private UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

	@Autowired
	private IncomeLogMapper incomeLogMapper;

	@Autowired
	FanYongService fanYongService;

	// 同一时间只允许一个线程访问购买油卡接口
	public synchronized Map payCallback(int flag, Object[] param) {
		//flag值暂时没用，为以后做拓展。
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

	/**
	 * 获取订单数据存入数据库(支付宝)
	 */
	public synchronized int getAddPaymentRecordData(Map<String, String> params) {
		String[] resDate = params.get("passback_params").split("\\^");
		String[] temp;
		// petrol_record主键
		String id = "";
		// 0代表充值，1代表购油——对应payType
		String payType = "";
		double money = 0;
		int count = 0;
		int petrolKind=0;
		String petrolNum= "";
		String ownerId="";
		double actualPayment=0;
		for (String data : resDate) {
			temp = data.split("\'");
			if ("orgId".equals(temp[0])) {
				id = temp[1];
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
                actualPayment =Double.valueOf(temp[1]);
                System.out.println("实际支付actualPayment:" + actualPayment);
            }
		}

		//payType对应payType支付类型
		if ("0".equals(payType)) {
			System.out.println("this is 0——充值");
		} else if ("1".equals(payType)) {
			System.out.println("this is 1——购买油卡");
//			此处插入购油的相关信息，油卡购买记录
			boolean ischange=changeInfo( money, count, petrolKind, petrolNum, ownerId,actualPayment);

			//若插入失败则放回卡
			if(ischange!=true){
				Petrol petrol= PetrolCache.currentPetrolMap.get(petrolNum);
				petrol.setOwnerId("");
				petrol.setEndTime(0);
				PetrolCache.putBackPetrol("AllpetrolMap",petrol);//放回all中
				PetrolCache.clearPetrol("currentPetrolMap",petrolNum);
				return 2;
			}

			//成功后移除对应的卡
			System.out.println("支付成功——删除油卡"+PetrolCache.currentPetrolMap.size());
			PetrolCache.clearPetrol("currentPetrolMap",petrolNum);
			return 1;

		} else if ("2".equals(payType)) {
			System.out.println("优惠卷");
//			modifyAndInsertCoupons(count, id, money);
		}
		return 1;
	}

	/**
	 * 进行所有的操作——相关表的增删改查（油卡表，新增购买记录表，收益变更记录表，用户收益信息表）
	 * @return
	 */
	public boolean changeInfo(double money,int count,int petrolKind,String petrolNum,String ownerId,double actualPayment){
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
		petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());//油卡种类
		petrolSalesRecords.setPetrolNum(petrol.getPetrolNum());//卡号
		petrolSalesRecords.setRecordId(StringUtil.createId());
		petrolSalesRecords.setState(1);//1为已支付
		petrolSalesRecords.setTurnoverAmount(petrol.getPetrolPrice());
		petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());
		boolean insertPetrolSalesRecords=insertPetrolSalesRecords(petrolSalesRecords);
		System.out.println("新增购买记录表完毕"+insertPetrolSalesRecords);

		boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,actualPayment);

		if(beginFanYong==true&&insertPetrolSalesRecords==true)
			return true;
		else {
			System.out.println("新增购买记录表有问题或beginFanYong");
			return false;
		}
	}


	//油卡表——更改相应油卡的状态（用户的id，卡号）——更改
	@Override
	public boolean updatePetrol(Petrol petrol) {
		return petrolMapperExtra.updateByPrimaryKeySelective(petrol)>0;
	}

	//新增购买记录表——插入
	@Override
	public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
		return petrolSalesRecordsMapperExtra.insert(petrolSalesRecords)>0;
	}

	//收益变更记录表——插入
	@Override
	public boolean insertIncomeLog(IncomeLog incomeLog) {
		return incomeLogMapper.insert(incomeLog)>0;
	}

	//用户收益信息表——更改
	@Override
	public boolean updateUserIncomeInfo(UserIncomeInfo userIncomeInfo) {
		return userIncomeInfoMapper.updateByPrimaryKeySelective(userIncomeInfo)>0;
	}
}
