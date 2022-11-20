package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.dao.mapper.autoRecharge.UserRechargeMapper;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.IncomeIog;
import com.cqut.czb.bn.entity.dto.OfflineRecharge.UserRecharge;
import com.cqut.czb.bn.entity.dto.autoRecharge.UserRechargeDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.ThirdOrder;
import com.cqut.czb.bn.entity.dto.directChargingSystem.ThirdOrderCallback;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service("UserRechargeServiceImpl")
public class UserRechargeServiceImpl implements UserRechargeService {

    @Autowired
    UserRechargeMapper userRechargeMapper;

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    UserMapper userMapper;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 插入充值记录
     * @param user
     * @param userRechargeDTO
     * @return
     */
    @Override
    @Transactional
    public synchronized JSONResult insertBatchRecharge(User user, UserRechargeDTO userRechargeDTO) {
        String[] strs;
        String[] petrolNums;
        String[] phones;
        boolean petr = false;
        boolean isBalance =false;
        boolean info = false;

        User u = userMapper.selectByPrimaryKey(user.getUserId());
        System.out.println("大客户油卡" + user);

        if (u!=null){
            user = u;
        }

        if (userRechargeDTO.getPetrolNums() != null && userRechargeDTO.getPetrolNums() != " "){

            strs = userRechargeDTO.getPetrolNums().split(",");
            petrolNums = new String[strs.length];
            phones = new String[strs.length];
            for (int i=0;i<strs.length;i++) {
                System.out.println(strs[i]);
                petrolNums[i] =strs[i].split("/")[0];
                phones[i] =strs[i].split("/")[1];
            }
        }else {
            return new JSONResult("充值失败",200);
        }
        DecimalFormat dfDalance = new DecimalFormat("#.00");
        //本次总充值金额
        double total = userRechargeDTO.getTurnoverAmount() * petrolNums.length;
        BigDecimal bignum1 = new BigDecimal(String.valueOf(total));
        double formatBlance = bignum1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(userRechargeDTO.getTurnoverAmount() < 0) {
            return new JSONResult("充值金额不能为负数，充值失败",200);
        }
        else if(userRechargeDTO.getTurnoverAmount() * petrolNums.length > getBalance(user.getUserId())){
            return new JSONResult("充值失败，余额不足",200);
        }
        else if(formatBlance == 0.00){
            return new JSONResult("充值失败，金额不能小于0.01",200);
        }

        if((int)userRechargeDTO.getTurnoverAmount() != 500) {
            return new JSONResult("暂时只支持500元",500);
        }

        List<UserRecharge> userRecharge = new ArrayList<>();

        for (int i=0; i<petrolNums.length; i++){
            //订单标识
            UserRecharge petrol = new UserRecharge();
            String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
            petrol.setRecordId(orgId);
            petrol.setPetrolNum(petrolNums[i]);
            petrol.setBuyerId(user.getUserId());
            petrol.setPaymentMethod(2);
            petrol.setThirdOrderId(getOrderIdByUUId(user.getUserId()));
            petrol.setTurnoverAmount(userRechargeDTO.getTurnoverAmount());
            petrol.setState(1);
            petrol.setRecordType(3);
            petrol.setIsRecharged(0);
            petrol.setPetrolKind(1);
            petrol.setDenomination(userRechargeDTO.getTurnoverAmount());
            petrol.setCurrentPrice(userRechargeDTO.getTurnoverAmount());
            userRecharge.add(petrol);
        }
        petr = userRechargeMapper.insertBatchRecharge(userRecharge) > 0;

        // 同时插入油卡充值管理
        if (petr){
            for (int i = 0;i < userRecharge.size(); i++) {
                UserRecharge r = userRecharge.get(i);
                DirectChargingOrderDto o = new DirectChargingOrderDto();
                o.setOrderId(r.getRecordId());
                o.setThirdOrderId(user.getUserId());
                o.setCardholder(phones[i]);
                o.setRechargeAccount(r.getPetrolNum());
                o.setRechargeAmount(r.getTurnoverAmount());
                o.setState(9);
                o.setCustomerNumber(user.getUserAccount());
                System.out.println(o);
                oilCardRechargeService.insertOilCardOrder(o);
            }
        }

        if (petr){
            IncomeIog incomeInfo = new IncomeIog();
            incomeInfo.setRecordId(StringUtil.createId());
            incomeInfo.setAmount(userRechargeDTO.getTurnoverAmount() * petrolNums.length);
            incomeInfo.setInfoId(userRechargeMapper.getInfoId(user.getUserId()));
            incomeInfo.setBeforeChangeIncome(userRechargeMapper.getBalance(user.getUserId()));
            info = userRechargeMapper.insert(incomeInfo);

            BigDecimal beforeBalance = new BigDecimal(String.valueOf(incomeInfo.getBeforeChangeIncome()));
            BigDecimal afterBalance = null;
            afterBalance = beforeBalance.subtract(bignum1);
            System.out.println(afterBalance.doubleValue());
            //更新余额
            isBalance = userRechargeMapper.updateRecharge(user.getUserId(),afterBalance.doubleValue());
            if(isBalance && info)
                return new JSONResult("充值成功",200);
            else
                return new JSONResult("充值失败",500);

        }else {
            return new JSONResult("充值失败",200);
        }
    }

    @Override
    public synchronized ThirdOrderCallback thirdInsert(ThirdOrder order){
        System.out.println("第三方订单");
        System.out.println(order);
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", order.getUserId());
        map.put("orderId", order.getOrderId());
        map.put("phone", order.getPhone());
        map.put("card", order.getCard());
        map.put("amount", order.getAmount().intValue());
        map.put("notifyUrl", order.getNotifyUrl());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null){
                return new ThirdOrderCallback(400, "参数错误", order.getOrderId());
            }
        }
        String key = SignUtil.stringToMD5(order.getUserId());
        String sign = SignUtil.sign(key, map);
        if (!sign.equals(order.getSign())){
            return new ThirdOrderCallback(400, "签名错误", order.getOrderId());
        }
        if (userMapper.selectByPrimaryKey(order.getUserId()) == null){
            return new ThirdOrderCallback(400, "无用户信息", order.getOrderId());
        }
        Double amount = order.getAmount();
        if (amount == null) {
            return new ThirdOrderCallback(400, "参数错误", order.getOrderId());
        }
        else if(amount.intValue() != 500){
            return new ThirdOrderCallback(400, "仅支持500元", order.getOrderId());
        }
        else if(getBalance(order.getUserId()) < amount){
            return new ThirdOrderCallback(400, "余额不足", order.getOrderId());
        }

        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);

        boolean flag = insertUserRecharge(order, orderId);

        if (flag){
            IncomeIog incomeInfo = new IncomeIog();
            incomeInfo.setRecordId(StringUtil.createId());
            incomeInfo.setAmount(order.getAmount());
            incomeInfo.setInfoId(userRechargeMapper.getInfoId(order.getUserId()));
            incomeInfo.setBeforeChangeIncome(userRechargeMapper.getBalance(order.getUserId()));
            boolean info = userRechargeMapper.insert(incomeInfo);

            BigDecimal beforeBalance = new BigDecimal(String.valueOf(incomeInfo.getBeforeChangeIncome()));
            BigDecimal afterBalance = null;
            BigDecimal bignum1 = new BigDecimal(String.valueOf(amount));
            afterBalance = beforeBalance.subtract(bignum1);
            System.out.println(afterBalance.doubleValue());
            //更新余额
            boolean isBalance = userRechargeMapper.updateRecharge(order.getUserId(),afterBalance.doubleValue());
            if(isBalance && info){
                insertOilOrder(order, orderId);
                return new ThirdOrderCallback(200, "下单成功", order.getOrderId());
            }
        }
        return new ThirdOrderCallback(400, "参数错误", order.getOrderId());
    }

    private void insertOilOrder(ThirdOrder order, String id){
        DirectChargingOrderDto o = new DirectChargingOrderDto();
        o.setOrderId(id);
        o.setThirdOrderId(order.getOrderId());
        o.setCardholder(order.getPhone());
        o.setRechargeAccount(order.getCard());
        o.setRechargeAmount(order.getAmount());
        o.setState(1);
        String userAccount = userMapper.selectByPrimaryKey(order.getUserId()).getUserAccount();
        o.setCustomerNumber(userAccount);
        o.setCustomerOrderId(order.getNotifyUrl());
        oilCardRechargeService.insertOilCardOrder(o);
    }


    private boolean insertUserRecharge(ThirdOrder order, String orderId){
        UserRecharge petrol = new UserRecharge();
        petrol.setRecordId(orderId);
        petrol.setPetrolNum(order.getCard());
        petrol.setBuyerId(order.getUserId());
        petrol.setPaymentMethod(2);
        petrol.setThirdOrderId(order.getOrderId());
        petrol.setTurnoverAmount(order.getAmount());
        petrol.setState(1);
        petrol.setRecordType(3);
        petrol.setIsRecharged(0);
        petrol.setPetrolKind(1);
        petrol.setDenomination(order.getAmount());
        petrol.setCurrentPrice(order.getAmount());
        List<UserRecharge> list = new ArrayList<>();
        list.add(petrol);
        return userRechargeMapper.insertBatchRecharge(list) > 0;
    }


    @Override
    public boolean drawback(String recordId, boolean dropOrder){
        PetrolSalesRecords order = petrolSalesRecordsMapperExtra.selectInfoByOrgId(recordId);
        if (order != null){
            String userId = order.getBuyerId();
            BigDecimal beforeBalance = new BigDecimal(String.valueOf(userRechargeMapper.getBalance(userId)));
            BigDecimal afterBalance = null;
            afterBalance = beforeBalance.add(BigDecimal.valueOf(order.getCurrentPrice()));
            System.out.println(afterBalance.doubleValue());
            //更新余额
            boolean isBalance = userRechargeMapper.updateRecharge(userId,afterBalance.doubleValue());
            if (dropOrder){
                petrolSalesRecordsMapperExtra.dropRecordById(recordId);
            }
            System.out.println("大客户退款 用户：" + userId + "订单：" + order.getRecordId());
            return isBalance;
        }
        return false;
    }

    /**
     * 修改卡号
     * @param userRechargeDTO
     * @return
     */
    @Override
    public boolean updatePetrolNum(UserRechargeDTO userRechargeDTO) {
        return userRechargeMapper.updatePetrolNum(userRechargeDTO) > 0;
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
