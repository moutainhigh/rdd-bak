package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.UserRechargeMapper;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.IncomeInfo;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("UserRechargeServiceImpl")
public class UserRechargeServiceImpl implements UserRechargeService {

    @Autowired
    UserRechargeMapper userRechargeMapper;

    /**
     * 插入充值记录
     * @param userId
     * @param userRecharge
     * @return
     */
    @Override
    public JSONResult insertRecharge(String userId, UserRecharge userRecharge) {
        if(userRecharge.getTurnoverAmount() < 0) {
            return new JSONResult("充值金额不能为负数，充值失败",200);
        }
        else if(userRecharge.getTurnoverAmount() > getBalance(userId)){
            return new JSONResult("充值失败，余额不足",200);
        }
        UserRecharge petrol = new UserRecharge();
        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        petrol.setRecordId(orgId);
        petrol.setPetrolNum(userRecharge.getPetrolNum());
        petrol.setBuyerId(userId);
        petrol.setPaymentMethod(2);
        petrol.setThirdOrderId(getOrderIdByUUId(userId));
        petrol.setTurnoverAmount(userRecharge.getTurnoverAmount());
        petrol.setTransactionTime(userRecharge.getTransactionTime());
        petrol.setState(1);
        petrol.setRecordType(3);
        petrol.setIsRecharged(0);
        petrol.setPetrolKind(1);
        petrol.setDenomination(userRecharge.getTurnoverAmount());
        petrol.setCurrentPrice(userRecharge.getTurnoverAmount());
        boolean petr = userRechargeMapper.insertRecharge(petrol);
        boolean isBalance = userRechargeMapper.updateRecharge(userId,userRecharge.getTurnoverAmount());

        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.setInfoId(StringUtil.createId());
        incomeInfo.setUserId(userId);
        incomeInfo.setOfflineRechargeBalance(userRechargeMapper.getBalance(userId));
        boolean info = userRechargeMapper.insert(incomeInfo);
        if(petr && isBalance && info)
            return new JSONResult("充值成功",500);
        else
            return new JSONResult("充值失败",200);
    }

//    /**
//     * 插入批量充值记录
//     * @param user
//     * @param userRechargeDTO
//     * @return
//     */
//    @Override
//    public JSONResult insertBatchRecharge(User user, UserRechargeDTO userRechargeDTO) {
//        String[] petrolNums;
//        boolean petr = false;
//        boolean isBalance =false;
//        boolean info = false;
//        if (userRechargeDTO.getPetrolNums() != null && userRechargeDTO.getPetrolNums() != " "){
//            petrolNums = userRechargeDTO.getPetrolNums().split(",");
//        }else {
//            return new JSONResult("充值失败",500);
//        }
//
//        if(userRechargeDTO.getTurnoverAmount() < 0) {
//            return new JSONResult("充值金额不能为负数，充值失败",500);
//        }
//        else if(userRechargeDTO.getTurnoverAmount() * petrolNums.length > getBalance(user.getUserId())){
//            return new JSONResult("充值失败，余额不足",500);
//        }
//        List<UserRecharge> petrol = new ArrayList<>();
////        UserRecharge petrol = new UserRecharge();
//        for (int i=0; i<petrolNums.length; i++){
//            //订单标识
//            String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
//            petrol.get(i).setRecordId(orgId);
//            petrol.get(i).setPetrolNum(petrolNums[i]);
//            petrol.get(i).setBuyerId(user.getUserId());
//            petrol.get(i).setPaymentMethod(2);
//            petrol.get(i).setThirdOrderId(getOrderIdByUUId(user.getUserId()));
//            petrol.get(i).setTurnoverAmount(userRechargeDTO.getTurnoverAmount());
//            petrol.get(i).setTransactionTime(userRechargeDTO.getTransactionTime());
//            petrol.get(i).setState(1);
//            petrol.get(i).setRecordType(3);
//            petrol.get(i).setIsRecharged(0);
//            petrol.get(i).setPetrolKind(1);
//            petrol.get(i).setDenomination(userRechargeDTO.getTurnoverAmount());
//            petrol.get(i).setCurrentPrice(userRechargeDTO.getTurnoverAmount());
//            petr = userRechargeMapper.insertRecharge(petrol.get(i));
//            isBalance = userRechargeMapper.updateRecharge(user.getUserId(),userRechargeDTO.getTurnoverAmount());
//
//            IncomeInfo incomeInfo = new IncomeInfo();
//            incomeInfo.setInfoId(StringUtil.createId());
//            incomeInfo.setUserId(user.getUserId());
//            incomeInfo.setOfflineRechargeBalance(userRechargeMapper.getBalance(user.getUserId()));
//            info = userRechargeMapper.insert(incomeInfo);
//        }
//
//        if(petr && isBalance && info)
//            return new JSONResult("充值成功",200);
//        else
//            return new JSONResult("充值失败",500);
//    }
    /**
     * 插入批量充值记录
     * @param user
     * @param userRechargeDTO
     * @return
     */
    @Override
    public JSONResult insertBatchRecharge(User user, UserRechargeDTO userRechargeDTO) {
        String[] petrolNums;
        boolean petr = false;
        boolean isBalance =false;
        boolean info = false;

        if (userRechargeDTO.getPetrolNums() != null && userRechargeDTO.getPetrolNums() != " "){
            petrolNums = userRechargeDTO.getPetrolNums().split(",");
        }else {
            return new JSONResult("充值失败",500);
        }
        //本次总充值金额
        double total = userRechargeDTO.getTurnoverAmount() * petrolNums.length;

        if(userRechargeDTO.getTurnoverAmount() < 0) {
            return new JSONResult("充值金额不能为负数，充值失败",500);
        }
        else if(userRechargeDTO.getTurnoverAmount() * petrolNums.length > getBalance(user.getUserId())){
            return new JSONResult("充值失败，余额不足",500);
        }

        List<UserRecharge> userRecharge = new ArrayList<>();

        for (int i=0; i<petrolNums.length; i++){
            //订单标识
            String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
            userRecharge.get(i).setRecordId(orgId);
            userRecharge.get(i).setPetrolNum(petrolNums[i]);
            userRecharge.get(i).setBuyerId(user.getUserId());
            userRecharge.get(i).setPaymentMethod(2);
            userRecharge.get(i).setThirdOrderId(getOrderIdByUUId(user.getUserId()));
            userRecharge.get(i).setState(1);
            userRecharge.get(i).setRecordType(3);
            userRecharge.get(i).setIsRecharged(0);
            userRecharge.get(i).setPetrolKind(1);
            userRecharge.get(i).setDenomination(userRechargeDTO.getTurnoverAmount());
            userRecharge.get(i).setCurrentPrice(userRechargeDTO.getTurnoverAmount());
        }
        petr = userRechargeMapper.insertBatchRecharge(userRecharge);

        //更新余额
        isBalance = userRechargeMapper.updateRecharge(user.getUserId(),total);

        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.setInfoId(StringUtil.createId());
        incomeInfo.setUserId(user.getUserId());
        incomeInfo.setOfflineRechargeBalance(userRechargeMapper.getBalance(user.getUserId()));
        info = userRechargeMapper.insert(incomeInfo);
        if(petr && isBalance && info)
            return new JSONResult("充值成功",200);
        else
            return new JSONResult("充值失败",500);
    }
    /**
     * 获取余额
     * @param userId
     * @return
     */
    @Override
    public double getBalance(String userId) {
        double balance = userRechargeMapper.getBalance(userId);
        return balance;
    }

    /**
     * 获取详情
     * @param userId
     * @param pageDTO
     * @return
     */
    @Override
    public JSONResult getRechargeDetails(String userId, UserRechargeDTO pageDTO) {
        pageDTO.setBuyerId(userId);
        List<UserRecharge> userRechargeList = userRechargeMapper.getRechargeDetails(pageDTO);
        for (int i=0; i<userRechargeList.size();i++){
            userRechargeList.get(i).setDate(formateDate(userRechargeList.get(i).getTransactionTime()));
        }
        PageInfo<UserRecharge> pageInfo = new PageInfo<>(userRechargeList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    /**
     * 获取总充值金额
     * @param user
     * @return
     */
    @Override
    public JSONResult getTotalRechargeAmount(User user) {
        double total = userRechargeMapper.getTotalRechargeAmount(user.getUserId());
        return new JSONResult("总充值金额查询成功",200,total);

    }



    /**
     * 生成支付订单
     * @param userId
     * @return
     */
    public String getOrderIdByUUId(String userId) {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
//         0 代表前面补充0     
//         4 代表长度为4     
//         d 代表参数为正数型
         return machineId + String.format("%015d", hashCodeV) + userId;
    }

    public String formateDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String theDate = sdf.format(date);
        return theDate;
    }
}
