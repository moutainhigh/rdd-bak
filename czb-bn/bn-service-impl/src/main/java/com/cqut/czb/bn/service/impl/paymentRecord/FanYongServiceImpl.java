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
    public boolean beginFanYong(String userId, double money, double actualPayment, String orgId) {
        String userIdUp1 = userMapperExtra.selectUserId(userId);//上级用户id
        String userIdUp2;//上上级用户id
        double fangyong1 = 0;//一级返佣
        double fangyong2 = 0;//二级返佣
        Dict dict1 = dictMapperExtra.selectDictByName("fangyong1");
        Dict dict2 = dictMapperExtra.selectDictByName("fangyong2");
        if (dict1 != null || dict1.getContent() != null) {
            fangyong1 = Double.valueOf(dict1.getContent());
            System.out.println("fangyong1：" + fangyong1);
        }
        if (dict2 != null || dict2.getContent() != null) {
            fangyong2 = Double.valueOf(dict2.getContent());
            System.out.println("fangyong2：" + fangyong2);
        }
        UserIncomeInfo oldUserIncomeInfoUp1;//上级用户的用户收益信息表
        UserIncomeInfo oldUserIncomeInfoUp2;//上上级用户的用户收益信息表
        if (userIdUp1 != null) {//可能存在没有上级用户
            //对上级用户的操作
            oldUserIncomeInfoUp1 = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(userIdUp1);//查出原收益信息
            //对上级用户的收益信息表，收益变更记录表进行操作
            changeUserIncomeInfo(oldUserIncomeInfoUp1, money, actualPayment, userIdUp1, 1, fangyong1,orgId);

            userIdUp2 = userMapperExtra.selectUserId(userIdUp1);
            if (userIdUp2 != null)//可能存在没有上上级用户
            {
                //对上上级用户的操作
                oldUserIncomeInfoUp2 = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(userIdUp2);//查出原收益信息
                //对上上级用户的收益信息表，收益变更记录表进行操作
                changeUserIncomeInfo(oldUserIncomeInfoUp2, money, actualPayment, userIdUp2, 2, fangyong2,orgId);
            }
        }

        //对本人的操作
        UserIncomeInfo oldUserIncomeInfo = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(userId);//查出原收益信息
        //更改个人收益信息和新增收益变更记录表
        boolean changeUserIncomeInfo = changeUserIncomeInfo(oldUserIncomeInfo, money, actualPayment, userId, 0, 0,orgId);

        if (changeUserIncomeInfo)
            return true;
        else
            return false;
    }

    /**
     * 共用方法——处理用户收益信息表
     * k为第几级：0为本人，1为上级，2为上上级
     */
    boolean changeUserIncomeInfo(UserIncomeInfo userIncomeInfo, double money, double actualPayment, String userId, int k, double FYrate,String orgId) {
        UserIncomeInfo oldUserIncomeInfo = userIncomeInfo;//先保存用于后面添加记录
        String uuid = StringUtil.createId();//新生成的id号（可能要用）
        boolean ischangeUserIncomeInfo;
        if (userIncomeInfo == null) {//1、为空则插入；2、不为空则修改
            //用户收益信息表——新增
            UserIncomeInfo userIncomeInfo1 = new UserIncomeInfo();
            userIncomeInfo1.setUserId(userId);
            if (k == 1 || k == 2) {//本人不返佣
                userIncomeInfo1.setFanyongIncome(money * FYrate);
            }
            userIncomeInfo1.setInfoId(uuid);
            userIncomeInfo1.setFanyongIncome(0.00);
            userIncomeInfo1.setShareIncome(0.00);
            userIncomeInfo1.setCreateAt(new Date());
            userIncomeInfo1.setUpdateAt(new Date());
            ischangeUserIncomeInfo = userIncomeInfoMapperExtra.insert(userIncomeInfo1) > 0;//进行新增
            System.out.println("新增用户收益信息表完毕" + k + ischangeUserIncomeInfo);
            boolean insertIncomeLog = insertIncomeLog(uuid, money, 0, oldUserIncomeInfo, userIncomeInfo1,orgId);
            return insertIncomeLog;
        } else {
            //用户收益信息表——更改
            if (k == 1 || k == 2) {//本人不返佣
                userIncomeInfo.setFanyongIncome(userIncomeInfo.getFanyongIncome() + money * FYrate);
                if (userIncomeInfo.getShareIncome() == null)
                    userIncomeInfo.setShareIncome(0.00);
            }
            if (k == 0) {
                if (actualPayment > 0) {
                    userIncomeInfo.setFanyongIncome(0.00);
                    userIncomeInfo.setShareIncome(0.00);
                } else if (actualPayment == 0) {
                    if (userIncomeInfo.getShareIncome() == null)
                        userIncomeInfo.setShareIncome(0.00);
                    double fanyong = userIncomeInfo.getFanyongIncome();
                    if (fanyong - money >= 0)
                        userIncomeInfo.setFanyongIncome(fanyong - money);
                    else if (fanyong - money < 0) {
                        double shareIncome = userIncomeInfo.getShareIncome();
                        userIncomeInfo.setFanyongIncome(0.00);
                        userIncomeInfo.setShareIncome(shareIncome - (money - fanyong));
                    }
                }
            }
            userIncomeInfo.setUpdateAt(new Date());//修改时间
            ischangeUserIncomeInfo = userIncomeInfoMapperExtra.updateByPrimaryKeySelective(userIncomeInfo) > 0;//进行修改
            System.out.println("更改用户收益信息表完毕" + ischangeUserIncomeInfo);
            boolean insertIncomeLog = insertIncomeLog(userIncomeInfo.getInfoId(), money, 0, oldUserIncomeInfo, userIncomeInfo,orgId);
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
    boolean insertIncomeLog(String uuid, double money, int type, UserIncomeInfo olduserIncomeInfo, UserIncomeInfo newuserIncomeInfo,String orgId) {
        //收益变更记录表——插入
        IncomeLog incomeLog = new IncomeLog();
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setType(type);//0为返佣
        if (olduserIncomeInfo == null) {
            incomeLog.setBeforeChangeIncome(0.00);
            incomeLog.setAmount(newuserIncomeInfo.getFanyongIncome() + newuserIncomeInfo.getShareIncome());
        } else {
            //处理可能没有数据的地方
            if (olduserIncomeInfo.getFanyongIncome() == null && olduserIncomeInfo.getShareIncome() == null)
                incomeLog.setBeforeChangeIncome(0.00);
            else if (olduserIncomeInfo.getFanyongIncome() == null && olduserIncomeInfo.getShareIncome() != null)
                incomeLog.setBeforeChangeIncome(olduserIncomeInfo.getShareIncome());
            else if (olduserIncomeInfo.getFanyongIncome() != null && olduserIncomeInfo.getShareIncome() == null)
                incomeLog.setBeforeChangeIncome(olduserIncomeInfo.getFanyongIncome());
            else
                incomeLog.setBeforeChangeIncome(olduserIncomeInfo.getFanyongIncome() + olduserIncomeInfo.getShareIncome());

            Double newIncome= newuserIncomeInfo.getFanyongIncome() + newuserIncomeInfo.getShareIncome();
            Double oldIncome=olduserIncomeInfo.getFanyongIncome() + olduserIncomeInfo.getShareIncome();
            BigDecimal bnewIncome = new BigDecimal(newIncome);
            BigDecimal boldIncome = new BigDecimal(oldIncome);

            double i = bnewIncome.subtract(boldIncome).doubleValue();

            incomeLog.setAmount(Double.parseDouble(String.format("%.2f", i)));
            incomeLog.setCreateAt(new Date());
            incomeLog.setUpdateAt(new Date());
        }
        incomeLog.setInfoId(uuid);
        incomeLog.setSouseId(orgId);//变更来源
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
