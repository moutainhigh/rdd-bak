package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.entity.PartnerVipIncome;
import com.cqut.czb.bn.entity.entity.UserIncomeInfo;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import com.cqut.czb.bn.util.config.partnerVipIncomeConfig.PartnerVipIncomeConfig;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class PartnerVipIncomeServiceImpl implements PartnerVipIncomeService {

    @Autowired
    PartnerVipIncomeMapperExtra partnerVipIncomeMapperExtra;
    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;
    @Autowired
    IncomeLogMapperExtra incomeLogMapperExtra;
    @Autowired
    PartnerMapperExtra partnerMapperExtra;
    @Autowired
    DictMapperExtra dictMapperExtra;
    @Autowired
    UserMapperExtra userMapperExtra;

    @Override
    public PageInfo<PartnerVipIncomeDTO> getVipIncomeList(PartnerVipIncomeDTO partnerVipIncomeDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(partnerVipIncomeMapperExtra.selectVipIncomeList(partnerVipIncomeDTO)) ;
    }

    @Override
    public Boolean settleVipIncome(PartnerVipIncomeDTO partnerVipIncomeDTO) {
        if (partnerVipIncomeDTO.getSettleIds() == null || "".equals(partnerVipIncomeDTO.getSettleIds())){
            return false;
        }
            String[] settleIds = partnerVipIncomeDTO.getSettleIds().split(",");
            Boolean isUpdate = partnerVipIncomeMapperExtra.settleVipIncomeRecords(settleIds)>0;

            if (isUpdate){
                //金额
                List<PartnerVipIncome> updatedIncome = partnerVipIncomeMapperExtra.selectVipIncomeByIds(settleIds);
                for (PartnerVipIncome partnerVipIncome: updatedIncome) {
                    UserIncomeInfoDTO userIncomeInfo = userIncomeInfoMapperExtra.selectUserIncomeInfo(partnerVipIncome.getPartnerId());
                    Boolean isRefund = false;
                    String id = StringUtil.createId();
                    if (userIncomeInfo != null && userIncomeInfo.getInfoId() != null) {  //判断用户收益表中是否有记录
                        userIncomeInfo.setUpdateAt(new Date());
                        userIncomeInfo.setRefundMoney(partnerVipIncome.getVipAddIncome());
                        isRefund = userIncomeInfoMapperExtra.updateOtherIncome(userIncomeInfo) > 0;   //有记录则直接进行余额返还
                    } else {
                        UserIncomeInfo userIncome = new UserIncomeInfo();
                        userIncome.setUserId(partnerVipIncome.getPartnerId());
                        userIncome.setInfoId(id);
                        userIncome.setOtherIncome(0.00);
                        Boolean insert = userIncomeInfoMapperExtra.insert(userIncome) > 0;  //无记录则插入
                        if (insert) {   //插入成功开始进行余额返还
                            userIncomeInfo.setInfoId(userIncome.getInfoId());
                            userIncomeInfo.setUpdateAt(new Date());
                            userIncomeInfo.setRefundMoney(partnerVipIncome.getVipAddIncome());
                            isRefund = userIncomeInfoMapperExtra.updateOtherIncome(userIncomeInfo) > 0;
                        }
                    }
                    if (isRefund) {  //退款成功后加入收入记录
                        IncomeLog incomeLog = new IncomeLog();
                        incomeLog.setType(4); //其他
                        incomeLog.setRemark("Vip返佣收益");
                        incomeLog.setRecordId(StringUtil.createId());
                        incomeLog.setSouseId(partnerVipIncome.getPartnerVipIncomeId());
                        incomeLog.setAmount(partnerVipIncome.getVipAddIncome());
                        if (userIncomeInfo != null && userIncomeInfo.getInfoId() != null) {
                            incomeLog.setInfoId(userIncomeInfo.getInfoId());
                            incomeLog.setBeforeChangeIncome(userIncomeInfo.getOtherIncome());
                        } else {
                            incomeLog.setInfoId(id);
                            incomeLog.setBeforeChangeIncome(0.0);
                        }
                        Boolean isLog = incomeLogMapperExtra.insert(incomeLog) > 0;  //插入收入记录
                        if (isLog) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    //生成新的收益记录
                    String newId = StringUtil.createId();
                    PartnerVipIncome newPartnerVipIncome = new PartnerVipIncome();
                    newPartnerVipIncome.setPartnerVipIncomeId(newId);
                    newPartnerVipIncome.setPartnerId(partnerVipIncome.getPartnerId());
                    newPartnerVipIncome.setIsSettle(0);
                    newPartnerVipIncome.setStartTime(new Date());
                    newPartnerVipIncome.setVipAddCount(0);
                    newPartnerVipIncome.setVipAddIncome(0.0);
                    newPartnerVipIncome.setPartnerType(partnerVipIncome.getPartnerType());
                    newPartnerVipIncome.setCreateAt(new Date());
                    boolean isInsert = partnerVipIncomeMapperExtra.insertIncome(newPartnerVipIncome)>0;
                    if (isInsert){
                        return true;
                    }else {
                        return false;
                    }
                }


            }else {
                return false;
        }
        return false;
    }

    public Boolean addVipIncome(String userId,Double addIncome){
        if (userId == null || addIncome==null){
            return false;
        }
        UserDTO userDTO = userMapperExtra.findUserDTOById(userId);
        PartnerVipIncome partnerVipIncome = new PartnerVipIncome();
        if (userDTO!=null && userDTO.getFirstLevelPartner()==null && userDTO.getSecondLevelPartner()!=null){  //如果本身是普通合伙人
            partnerVipIncome.setPartnerId(userDTO.getSecondLevelPartner());
            partnerVipIncome.setVipAddCount(1);  //下级vip数量加一
            Dict vipPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion());  //合伙人下级充值vip收益比例
            partnerVipIncome.setVipAddIncome(mul(addIncome,Double.parseDouble(vipPartnerProportion.getContent())));  //合伙人获得返佣
            return partnerVipIncomeMapperExtra.updateVipIncomeByAdd(partnerVipIncome)>0;
        }else if (userDTO!=null && userDTO.getFirstLevelPartner()!=null && userDTO.getSecondLevelPartner()==null){ //如果为事业合伙人下级且没有普通合伙人上级
            partnerVipIncome.setPartnerId(userDTO.getUserId());
            partnerVipIncome.setVipAddCount(1);
            Dict vipPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion());
            Dict vipFirstPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()); //事业合伙人专属下级充值vip收益比例
            Double proportion = add(Double.parseDouble(vipPartnerProportion.getContent()),Double.parseDouble(vipFirstPartnerProportion.getContent()));
            partnerVipIncome.setVipAddIncome(mul(addIncome,proportion));
            return partnerVipIncomeMapperExtra.updateVipIncomeByAdd(partnerVipIncome)>0;
        }else if (userDTO!=null && userDTO.getFirstLevelPartner()!=null && userDTO.getSecondLevelPartner()!=null){ //如果为事业合伙人下级且有普通合伙人上级
            partnerVipIncome.setPartnerId(userDTO.getUserId());
            partnerVipIncome.setVipAddCount(1);
            Dict vipFirstPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion());
            Double proportion = Double.parseDouble(vipFirstPartnerProportion.getContent());
            partnerVipIncome.setVipAddIncome(mul(addIncome,proportion));
            return partnerVipIncomeMapperExtra.updateVipIncomeByAdd(partnerVipIncome)>0;
        }
        return null;
    }

    @Override
    public Boolean initVipIncomeData() {
            //初始化数据库的合伙人vip收益，统计过去的数据（限用于数据初始化）
        List<PartnerDTO> allPartner = partnerMapperExtra.selectAllPartner();
        for (PartnerDTO partnerDTO : allPartner){
            if (partnerDTO.getPartner() == 1){

            }
        }



//
//            if (partner==null) {
//                partner = new PartnerDTO();
//            }
//
//            PartnerDTO partnerDTO1 = partnerMapperExtra.selectPartner(partnerDTO);
//
//            if (partnerDTO1!=null && partnerDTO1.getPartner()==1){  //如果本身是普通合伙人
//                PartnerDTO vipUser = partnerMapperExtra.selectSubVipSecond(partnerDTO);
//                if (vipUser!=null && vipUser.getVipAddCount()>0){
//                    partner.setVipAddCount(vipUser.getVipAddCount());
//                    partner.setVipMoney(mul(vipUser.getVipAddCount().doubleValue(),0.125));
//                    partner.setVipMoney(mul(partner.getVipMoney(),39.0));
//                }else {
//                    partner.setVipAddCount(0);
//                    partner.setVipMoney(0.00);
//                }
//            }else if (partnerDTO1!=null && partnerDTO1.getPartner()==2){
//                PartnerDTO firstSubVip = partnerMapperExtra.selectSubVipFirst(partnerDTO);  //事业合伙人下 第一子级不是普通合伙人的 自己中的Vip增量
//                PartnerDTO thirdSubVip = partnerMapperExtra.selectSubVipFirst(partnerDTO); //事业合伙人下 第一子级是普通合伙人的 自己中的Vip增量
//                Double firstMoney = 0.00;
//                Double secondMoney = 0.00;
//                if (firstSubVip != null && firstSubVip.getVipAddCount()>0) {
//                     firstMoney = mul(firstSubVip.getVipAddCount().doubleValue(),0.2);  //没有被普通合伙人分走的佣金12.5%+7.5%
//                     firstMoney = mul(firstMoney,39.0);
//                }
//                if (thirdSubVip != null && thirdSubVip.getVipAddCount()>0) {
//                    secondMoney = mul(thirdSubVip.getVipAddCount().doubleValue(),0.075);  //被普通合伙人分走后的固有佣金
//                    secondMoney = mul(secondMoney,39.0);
//                }
//                partner.setVipMoney(changeToBigDecimal(firstMoney+secondMoney));
//                if (firstSubVip != null && firstSubVip.getVipAddCount()>0 && thirdSubVip != null && thirdSubVip.getVipAddCount()>0) {
//                    partner.setVipAddCount(firstSubVip.getVipAddCount()+thirdSubVip.getVipAddCount());
//                } else {
//                    partner.setVipAddCount(0);
//                }
//            }
        return null;
    }

    public Double changeToBigDecimal(Double num){
        BigDecimal b = new BigDecimal(num);
        num = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue(); //直接去掉金额小数点两位后面的数
        return num;
    }

    //Double与Double进行乘法，如直接相乘容易精度缺失
    public Double mul (Double num1,Double num2){
        BigDecimal mul1 = new BigDecimal(Double.toString(num1));
        BigDecimal mul2 = new BigDecimal(Double.toString(num2));
        double mul = mul1.multiply(mul2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return mul;
    }

    public Double add (Double num1,Double num2){
        BigDecimal add1 = new BigDecimal(Double.toString(num1));
        BigDecimal add2 = new BigDecimal(Double.toString(num2));
        return new Double(add1.add(add2).doubleValue());
    }

}
