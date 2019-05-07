package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.service.IRefuelingCard;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RefuelingCardService implements IRefuelingCard {

	@Autowired
	private  AppBuyPetrolService appBuyPetrolService;

	@Resource(name = "paymentRecordService")
	private PaymentRecordService paymentRecordService;

	@Autowired
	private PetrolMapper petrolMapper;

	@Autowired
	private PetrolSalesRecordsMapper petrolSalesRecordsMapper;

	@Autowired
	private DepositRecordsMapper depositRecordsMapper;

	@Autowired
	private UserIncomeInfoMapper userIncomeInfoMapper;

	@Autowired
	private UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

	@Autowired
	private LoginInfoMapper loginInfoMapper;

	@Autowired
	private IncomeLogMapper incomeLogMapper;

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
				System.out.println("充值金额:" + money);
			}
			if ("count".equals(temp[0])) {
				count = Integer.parseInt(temp[1]);
				System.out.println("购买数量:" + count);
			}
			if ("petrolKind".equals(temp[0])) {
				petrolKind = Integer.parseInt(temp[1]);
				System.out.println("油卡类型:" + petrolKind);
			}
			if ("petrolNum".equals(temp[0])) {
				petrolNum = temp[1];
				System.out.println("油卡号:" + petrolNum);
			}
			if ("ownerId".equals(temp[0])) {
				ownerId = temp[1];
				System.out.println("用户id:" + ownerId);
			}
		}

		//payType对应payType支付类型
		if ("0".equals(payType)) {
			System.out.println("this is 0——充值");
		} else if ("1".equals(payType)) {
			System.out.println("this is 1——购买油卡");
//			此处插入购油的相关信息，油卡购买记录
//			修改相应油卡的信息/***************************/死数据只修改了第一张
//			appBuyPetrolService.updatePetrol(AllPetrolDTO.getCurrentPetrol().get(0));
			boolean ischange=changeInfo( money, count, petrolKind, petrolNum, ownerId);

			//若插入失败则放回卡
			if(ischange!=true){
				Petrol petrol=AllPetrolDTO.currentPetrolMap.get(petrolNum);
				AllPetrolDTO.putBackPetrol(AllPetrolDTO.getAllpetrolMap(),petrol);//放回all中
				AllPetrolDTO.clearPetrol(AllPetrolDTO.getCurrentPetrolMap(),petrolNum);
				return 2;
			}

			//成功后移除对应的卡
			AllPetrolDTO.clearPetrol(AllPetrolDTO.getCurrentPetrolMap(),petrolNum);
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
	public boolean changeInfo(double money,int count,int petrolKind,String petrolNum,String ownerId){
		//油卡表——更改相应油卡的状态（用户的id，卡号）——更改
		//取出油卡
		Petrol petrol=AllPetrolDTO.currentPetrolMap.get(petrolNum);
		if(petrol==null)
		{
			System.out.println("油卡为空");
			return false;
		}
		petrol.setOwnerId(ownerId);
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
		boolean insertPetrolSalesRecords=insertPetrolSalesRecords(petrolSalesRecords);
		System.out.println("新增购买记录表完毕"+insertPetrolSalesRecords);

		//查找出用户之前的收益信息
		UserIncomeInfoDTO oldUserIncomeInfo=userIncomeInfoMapperExtra.selectOneUserIncomeInfo(petrol.getOwnerId());

		//用户收益信息表——更改
		UserIncomeInfo userIncomeInfo=new UserIncomeInfo();
		userIncomeInfo.setUserId(petrol.getOwnerId());
		userIncomeInfo.setFanyongIncome(oldUserIncomeInfo.getFanyongIncome()+petrol.getPetrolPrice()*0.01);//暂时设定为0.01****************
		userIncomeInfo.setInfoId(oldUserIncomeInfo.getInfoId());
		boolean updateUserIncomeInfo=updateUserIncomeInfo(userIncomeInfo);
		System.out.println("更改用户收益信息表完毕"+updateUserIncomeInfo);

		//收益变更记录表——插入
		IncomeLog incomeLog=new IncomeLog();
		incomeLog.setRecordId(StringUtil.createId());
		incomeLog.setAmount(petrol.getPetrolPrice()*0.01);//暂时设定为0.01
		incomeLog.setType(0);//0为返佣
		incomeLog.setBeforeChangeIncome(oldUserIncomeInfo.getFanyongIncome());
		incomeLog.setInfoId(oldUserIncomeInfo.getInfoId());
		boolean incomeLogMapper=insertIncomeLog(incomeLog);
		System.out.println("新增收益变更记录表完毕"+incomeLogMapper);

		if(updatePetrol==false&&incomeLogMapper==false&&updateUserIncomeInfo==false&&insertPetrolSalesRecords==false){
			return false;
		}
		return true;
	}


	//油卡表——更改相应油卡的状态（用户的id，卡号）——更改
	@Override
	public boolean updatePetrol(Petrol petrol) {
		return petrolMapper.updateByPrimaryKeySelective(petrol)>0;
	}

	//新增购买记录表——插入
	@Override
	public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
		return petrolSalesRecordsMapper.insert(petrolSalesRecords)>0;
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
