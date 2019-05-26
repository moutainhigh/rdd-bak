package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.service.petrolRecharge.FanYongService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * author:陈德强
 * 作用：返佣操作
 */
@Service
public class FanYongServiceImpl implements FanYongService {

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    private UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

    @Autowired
    private IncomeLogMapperExtra incomeLogMapperExtra;

    @Autowired
    private DictMapperExtra dictMapperExtra;

    /**
     * 总体控制
     * 要给用户的userId,支付的金额
     */
    @Override
    public boolean beginFanYong(String userId,double money, double actualPayment, String orgId) {
        String userIdUp1 = userMapperExtra.selectUserId(userId);//上级用户id
        String userIdUp2;//上上级用户id
        double fangyong1 = 0;//一级返佣比例
        double fangyong2 = 0;//二级返佣比例
        double fangyongRate = 0;//占总金额的比例
        Dict dict1 = dictMapperExtra.selectDictByName("fangyong1");
        Dict dict2 = dictMapperExtra.selectDictByName("fangyong2");
        Dict dict3 = dictMapperExtra.selectDictByName("fangyong_rate");
        if (dict1 != null && dict1.getContent() != null) {
            fangyong1 = Double.valueOf(dict1.getContent());
            System.out.println("fangyong1：" + fangyong1);
        }
        if (dict2 != null && dict2.getContent() != null) {
            fangyong2 = Double.valueOf(dict2.getContent());
            System.out.println("fangyong2：" + fangyong2);
        }
        if (dict3 != null && dict3.getContent() != null) {
            fangyongRate = Double.valueOf(dict3.getContent());
            System.out.println("fangyongRate：" + fangyongRate);
        }

        if (userIdUp1 != null) {//可能存在没有上级用户
            //对上级用户的操作
            UserIncomeInfo oldUserIncomeInfoUp1 = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(userIdUp1);//查出原收益信息
            //对上级用户的(收益信息表，收益变更记录表)进行操作
            changeUserIncomeInfo(userId,userIdUp1, fangyongRate, oldUserIncomeInfoUp1, money, actualPayment, userIdUp1, 1, fangyong1, orgId);

            userIdUp2 = userMapperExtra.selectUserId(userIdUp1);
            if (userIdUp2 != null)//可能存在没有上上级用户
            {
                //对上上级用户的操作
                UserIncomeInfo oldUserIncomeInfoUp2 = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(userIdUp2);//查出原收益信息
                //对上上级用户的收益信息表，收益变更记录表进行操作
                changeUserIncomeInfo(userId,userIdUp2,fangyongRate,oldUserIncomeInfoUp2, money, actualPayment, userIdUp2, 2, fangyong2, orgId);
            }
        }
        return true;
    }

    /**
     * 共用方法——处理用户收益信息表
     * k为第几级：0为本人，1为上级，2为上上级
     */
    boolean changeUserIncomeInfo(String commissionSourceUser,String commissionGotUser, double fangyongRate, UserIncomeInfo userIncomeInfo, double money, double actualPayment, String userId, int k, double FYrate, String orgId) {
        String uuid = StringUtil.createId();//新生成的id号（可能要用）
        boolean ischangeUserIncomeInfo;
        //amount为变更金额
        double amount=(BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(fangyongRate)).multiply(BigDecimal.valueOf(FYrate))).doubleValue();
        if (userIncomeInfo == null) {//空则插入；不为空则修改
            //用户收益信息表——新增
            int type=0;//0为反用
            UserIncomeInfo userIncomeInfo1 = new UserIncomeInfo();
            userIncomeInfo1.setUserId(userId);
            userIncomeInfo1.setFanyongIncome(amount);
            userIncomeInfo1.setInfoId(uuid);
            userIncomeInfo1.setShareIncome(0.00);
            userIncomeInfo1.setWithdrawed(0.00);
            userIncomeInfo1.setGotTotalRent(0.00);
            userIncomeInfo1.setPayTotalRent(0.00);
            userIncomeInfo1.setOtherIncome(0.00);
            ischangeUserIncomeInfo = userIncomeInfoMapperExtra.insert(userIncomeInfo1) > 0;//进行新增
            System.out.println("新增用户收益信息表完毕" + k + ischangeUserIncomeInfo);
            boolean insertIncomeLog = insertIncomeLog(commissionSourceUser,commissionGotUser,uuid,amount,type,userIncomeInfo1, orgId);
            return insertIncomeLog;
        } else {
            //用户收益信息表——更改
                int type=0;
                double getMoney=(BigDecimal.valueOf(fangyongRate).multiply(BigDecimal.valueOf(FYrate)).multiply(BigDecimal.valueOf(money))).doubleValue();
                double fangyongIncome=(BigDecimal.valueOf(userIncomeInfo.getFanyongIncome()).add(BigDecimal.valueOf(getMoney))).doubleValue();
                userIncomeInfo.setFanyongIncome(fangyongIncome);
                if (userIncomeInfo.getShareIncome() == null)
                    userIncomeInfo.setShareIncome(0.00);
                ischangeUserIncomeInfo = userIncomeInfoMapperExtra.updateByPrimaryKeySelective(userIncomeInfo) > 0;//进行修改
                System.out.println("更改用户收益信息表完毕" + ischangeUserIncomeInfo);
                boolean insertIncomeLog = insertIncomeLog(commissionSourceUser,commissionGotUser,userIncomeInfo.getInfoId(), amount,type,userIncomeInfo, orgId);
                if (ischangeUserIncomeInfo == true && insertIncomeLog == true)
                    return true;
                else {
                    System.out.println("用户收益信息表有问题");
                    return false;
                }
            }
    }


    /**
     * 新增收益变更记录表
     * type: 0 返佣，1 提现，2 消费，3 推荐，4 其他
     */
    boolean insertIncomeLog(String commissionSourceUser,String commissionGotUser,String uuid, double amount, int type, UserIncomeInfo olduserIncomeInfo,String orgId) {
        //收益变更记录表——插入
        IncomeLog incomeLog = new IncomeLog();
        incomeLog.setCommissionGotUser(commissionGotUser);
        incomeLog.setCommissionSourceUser(commissionSourceUser);
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setType(type);//0为返佣
        if (olduserIncomeInfo == null) {
            incomeLog.setBeforeChangeIncome(0.00);
            incomeLog.setAmount(olduserIncomeInfo.getFanyongIncome());//只会有返佣收益
        } else {
            //处理可能没有数据的地方——变更前的收益
            double beforeIncome=0.00;//定义之前的余额
            if(olduserIncomeInfo.getFanyongIncome()!=null){
                beforeIncome=(BigDecimal.valueOf(beforeIncome).add(BigDecimal.valueOf(olduserIncomeInfo.getFanyongIncome()))).doubleValue();
            }
            if(olduserIncomeInfo.getShareIncome()!=null){
                beforeIncome=(BigDecimal.valueOf(beforeIncome).add(BigDecimal.valueOf(olduserIncomeInfo.getShareIncome()))).doubleValue();
            }
            if(olduserIncomeInfo.getOtherIncome()!=null){
                beforeIncome=(BigDecimal.valueOf(beforeIncome).add(BigDecimal.valueOf(olduserIncomeInfo.getOtherIncome()))).doubleValue();
            }
            if(olduserIncomeInfo.getWithdrawed()!=null){
                beforeIncome=(BigDecimal.valueOf(beforeIncome).subtract(BigDecimal.valueOf(olduserIncomeInfo.getWithdrawed()))).doubleValue();
            }
            beforeIncome=(BigDecimal.valueOf(beforeIncome).subtract(BigDecimal.valueOf(amount))).doubleValue();//减掉变更金额
            incomeLog.setBeforeChangeIncome(beforeIncome);
            System.out.println("变更前金额为："+beforeIncome);
            System.out.println("变更金额为："+amount);
            incomeLog.setAmount(amount);
        }
        incomeLog.setInfoId(uuid);
        incomeLog.setSouseId(orgId);//变更来源
        if(type==0)
            incomeLog.setRemark("返佣");
        else if(type==1)
            incomeLog.setRemark("提现");
        else if(type==2)
            incomeLog.setRemark("消费");
        else if(type==3)
            incomeLog.setRemark("推荐");
        else if(type==4)
            incomeLog.setRemark("其他");
        boolean insertIncomeLog = incomeLogMapperExtra.insert(incomeLog) > 0;
        System.out.println("新增收益变更记录表完毕" + insertIncomeLog);
        if (insertIncomeLog)
            return true;
        else {
            System.out.println("新增收益变更记录表有问题");
            return false;
        }
    }
}
