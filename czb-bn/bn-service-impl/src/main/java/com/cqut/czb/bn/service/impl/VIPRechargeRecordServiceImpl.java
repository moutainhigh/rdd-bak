package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.entity.VipRechargeRecords;
import com.cqut.czb.bn.entity.global.DateDealWith;
import com.cqut.czb.bn.service.VIPRechargeRecordService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 22:25
 */
@Service
public class VIPRechargeRecordServiceImpl implements VIPRechargeRecordService {

    @Autowired
    VipRechargeRecordsMapperExtra vipRechargeRecordsMapperExtra;

    @Autowired
    VipRechargeRecordsMapper vipRechargeRecordsMapper;

    @Autowired
    VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

    @Autowired
    IncomeLogMapperExtra incomeLogMapperExtra;

    @Override
    public VipRechargeRecordDTO getVipRechargeRecordList(VipRechargeRecordListDTO vipRechargeRecordListDTO) {
        PageHelper.startPage(vipRechargeRecordListDTO.getCurrentPage(), vipRechargeRecordListDTO.getPageSize());

        VipRechargeRecordDTO vipRechargeRecordDTO = new VipRechargeRecordDTO();

        //插入充值数据data
        vipRechargeRecordDTO.setVipRechargeRecordListDTOList(new PageInfo<>(vipRechargeRecordsMapperExtra.getVipRechargeRecord(vipRechargeRecordListDTO)));

        double totalAmount = 0;
        double todayTotalAmount=0.0;
        int todayTotalNum=0;
//        List<VipRechargeRecordListDTO> vipRechargeRecordListDTOS = vipRechargeRecordsMapperExtra.getVipRechargeRecord(vipRechargeRecordListDTO);
//        for(VipRechargeRecordListDTO vipRechargeRecordListDTO1 : vipRechargeRecordListDTOS){
//            totalAmount += vipRechargeRecordListDTO1.getAmount();
//        }
//        totalAmount = Math.round(totalAmount * 100) / 100.0;
//        System.out.println(totalAmount);

        Double totalMoney=vipRechargeRecordsMapperExtra.getVipRechargeTotalMoney(vipRechargeRecordListDTO);
        if(totalMoney!=null)
        {
            totalAmount=totalMoney;
        }
        //插入总额
        vipRechargeRecordDTO.setTotalAmount(totalAmount);
        VipRechargeRecordListDTO listDTO=new VipRechargeRecordListDTO();
        listDTO.setStartTime(DateDealWith.backStartTime());
        listDTO.setEndTime(DateDealWith.backEndTime());

        Double money=vipRechargeRecordsMapperExtra.getVipRechargeTotalMoney(listDTO);
        if(money!=null){
            todayTotalAmount=money;
        }

        List<VipRechargeRecordListDTO> listDTOS=vipRechargeRecordsMapperExtra.getVipRechargeRecord(listDTO);
        if(listDTOS!=null) {
            todayTotalNum = listDTOS.size();
        }
        //插入今日总额
        vipRechargeRecordDTO.setTodayTotalAmount(todayTotalAmount);
        //插入今日单数
        vipRechargeRecordDTO.setTodayTotalNum(todayTotalNum);
        return vipRechargeRecordDTO;
    }

    @Override
    public Boolean deleteVIPRechargeByID(String vipRechargeRecordId) {
        return vipRechargeRecordsMapper.deleteByPrimaryKey(vipRechargeRecordId) > 0;
    }


    public Boolean addVipMoney(VipRechargeRecordListDTO vipRechargeRecordListDTO) {
        List<VipRechargeRecordListDTO> vipRechargeRecordDTOS = vipRechargeRecordsMapperExtra.getVipRechargeRecordTest(vipRechargeRecordListDTO);
        if (vipRechargeRecordDTOS!=null && vipRechargeRecordDTOS.size()>0){
            for (VipRechargeRecordListDTO vipRechargeRecordListDTO1: vipRechargeRecordDTOS){
                if (vipRechargeRecordListDTO1.getSuperiorUser()==null){
                    continue;
                }
                UserIncomeInfoDTO userIncomeInfo = userIncomeInfoMapperExtra.selectUserIncomeInfo(vipRechargeRecordListDTO1.getSuperiorUser());
                userIncomeInfo.setRefundMoney(mul(vipRechargeRecordListDTO1.getAmount(),0.45));
                userIncomeInfo.setUpdateAt(new Date());
                Boolean update = userIncomeInfoMapperExtra.updateFanYongIncome(userIncomeInfo)>0;
                if (update){
                    IncomeLog incomeLog = new IncomeLog();
                    incomeLog.setType(0); //返佣
                    incomeLog.setRemark("充值Vip返佣");
                    incomeLog.setRecordId(StringUtil.createId());
                    incomeLog.setCommissionLevel(1);
                    double beforeIncome=0.00;//定义之前的余额
                    if(userIncomeInfo.getFanyongIncome()!=null){
                        beforeIncome=(BigDecimal.valueOf(beforeIncome).add(BigDecimal.valueOf(userIncomeInfo.getFanyongIncome()))).doubleValue();
                    }
                    if(userIncomeInfo.getShareIncome()!=null){
                        beforeIncome=(BigDecimal.valueOf(beforeIncome).add(BigDecimal.valueOf(userIncomeInfo.getShareIncome()))).doubleValue();
                    }
                    if(userIncomeInfo.getOtherIncome()!=null){
                        beforeIncome=(BigDecimal.valueOf(beforeIncome).add(BigDecimal.valueOf(userIncomeInfo.getOtherIncome()))).doubleValue();
                    }
                    if(userIncomeInfo.getWithdrawed()!=null){
                        beforeIncome=(BigDecimal.valueOf(beforeIncome).subtract(BigDecimal.valueOf(userIncomeInfo.getWithdrawed()))).doubleValue();
                    }
                    beforeIncome=(BigDecimal.valueOf(beforeIncome).subtract(BigDecimal.valueOf(userIncomeInfo.getWithdrawed()))).doubleValue();//减掉变更金额
                    incomeLog.setBeforeChangeIncome(beforeIncome);
                    System.out.println("变更前金额为："+beforeIncome);
                    incomeLog.setCommissionGotUser(vipRechargeRecordListDTO1.getSuperiorUser());
                    incomeLog.setCommissionSourceUser(vipRechargeRecordListDTO1.getUserId());
                    incomeLog.setSouseId(vipRechargeRecordListDTO1.getRecordId());
                    incomeLog.setAmount(mul(vipRechargeRecordListDTO1.getAmount(),0.45));
                    if (userIncomeInfo != null && userIncomeInfo.getInfoId() != null) {
                        incomeLog.setInfoId(userIncomeInfo.getInfoId());
                        incomeLog.setBeforeChangeIncome(userIncomeInfo.getOtherIncome());
                    }
                    Boolean isLog = incomeLogMapperExtra.insert(incomeLog) > 0;  //插入收入记录
                    if (!isLog){
                        return false;
                    }
                }

            }
        }
        return true;
    }

    //Double与Double进行乘法，如直接相乘容易精度缺失
    public Double mul (Double num1,Double num2){
        BigDecimal mul1 = new BigDecimal(Double.toString(num1));
        BigDecimal mul2 = new BigDecimal(Double.toString(num2));
        double mul = mul1.multiply(mul2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return mul;
    }


}