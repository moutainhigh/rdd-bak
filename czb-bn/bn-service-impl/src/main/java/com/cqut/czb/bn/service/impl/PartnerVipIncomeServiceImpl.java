package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserIncomeInfoDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.*;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import com.cqut.czb.bn.service.impl.payBack.FanYongServiceImpl;
import com.cqut.czb.bn.util.config.partnerVipIncomeConfig.PartnerVipIncomeConfig;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
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
    @Autowired
    FanYongServiceImpl fanYongService;
    @Autowired
    UserMapper userMapper;

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
                List<PartnerVipIncomeDTO> updatedIncome = partnerVipIncomeMapperExtra.selectVipIncomeByIds(settleIds);
                for (PartnerVipIncomeDTO partnerVipIncome: updatedIncome) {
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
                            userIncomeInfo = userIncomeInfoMapperExtra.selectUserIncomeInfo(partnerVipIncome.getPartnerId());
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

                            }else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }

                }
            }else {
                return false;
        }
        return true;
    }
    @Override
    public Boolean addVipIncome(String userId,Double addIncome,Integer type){
        if (userId == null || addIncome==null){
            return false;
        }
        UserDTO userDTO = userMapperExtra.findUserDTOById(userId);
        PartnerVipIncome partnerVipIncome = new PartnerVipIncome();
        PartnerVipIncome secondPartnerVipIncome = new PartnerVipIncome();
        Dict firstPetrol = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getFirstPetrolProportion());
        Dict secondPetrol = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getSecondPetrolProportion());
        Double firstPetrolProportion = Double.parseDouble(firstPetrol.getContent());
        Double secondPetrolProportion = Double.parseDouble(secondPetrol.getContent());
        Double petrolProportion = add(firstPetrolProportion,secondPetrolProportion);
        if (userDTO!=null && (userDTO.getFirstLevelPartner()==null || "".equals(userDTO.getFirstLevelPartner()) ) && userDTO.getSecondLevelPartner()!=null && !"".equals(userDTO.getSecondLevelPartner())){  //如果本身是普通合伙人
            partnerVipIncome.setPartnerId(userDTO.getSecondLevelPartner());
            if (type == 1){  //vip充值
            partnerVipIncome.setVipAddCount(1);  //下级vip数量加一
            Dict vipPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion());  //合伙人下级充值vip收益比例
            Double income = mul(addIncome,Double.parseDouble(vipPartnerProportion.getContent()));
            partnerVipIncome.setVipAddIncome(income);  //合伙人获得返佣
            partnerVipIncome.setFirstVipIncome(income);
            }
            else if (type == 2){  //油卡充值
            Double firstPetrolIncome = mul(addIncome,firstPetrolProportion);
            partnerVipIncome.setFirstPetrolIncome(firstPetrolIncome);
            partnerVipIncome.setVipAddIncome(firstPetrolIncome);
            }
            return partnerVipIncomeMapperExtra.updateVipIncomeByAdd(partnerVipIncome)>0;
        }else if (userDTO!=null && userDTO.getFirstLevelPartner()!=null && !"".equals(userDTO.getFirstLevelPartner()) && (userDTO.getSecondLevelPartner()==null || "".equals(userDTO.getSecondLevelPartner()))){ //如果为事业合伙人下级且没有普通合伙人上级
            partnerVipIncome.setPartnerId(userDTO.getFirstLevelPartner());
            if (type == 1) {
                partnerVipIncome.setVipAddCount(1);
                Dict vipPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion());
                Dict vipFirstPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()); //事业合伙人专属下级充值vip收益比例
                Double proportion = add(Double.parseDouble(vipPartnerProportion.getContent()), Double.parseDouble(vipFirstPartnerProportion.getContent()));
//            Double firstProportion = Double.parseDouble(vipPartnerProportion.getContent()); //12.5%
//            Double secondProportion = Double.parseDouble(vipFirstPartnerProportion.getContent()); //7.5%
                Double income = mul(addIncome, proportion);
//            Double first = mul(firstProportion,addIncome);
//            Double second = mul(secondProportion,secondProportion);
                partnerVipIncome.setVipAddIncome(income);
                partnerVipIncome.setFirstVipIncome(income);   //事业合伙人的直属下级中的%7.5也是算在了一级返佣收益中
            }else if (type == 2){
                Double petrolIncome = mul(addIncome,petrolProportion);
                partnerVipIncome.setVipAddIncome(petrolIncome);
                partnerVipIncome.setFirstPetrolIncome(petrolIncome);
            }
//            partnerVipIncome.setSecondVipIncome(second);
            return partnerVipIncomeMapperExtra.updateVipIncomeByAdd(partnerVipIncome)>0;
        }else if (userDTO!=null && userDTO.getFirstLevelPartner()!=null && !"".equals(userDTO.getFirstLevelPartner()) && !"".equals(userDTO.getSecondLevelPartner()) && userDTO.getSecondLevelPartner()!=null){ //如果为事业合伙人下级且有普通合伙人上级
            partnerVipIncome.setPartnerId(userDTO.getFirstLevelPartner());
            secondPartnerVipIncome.setPartnerId(userDTO.getSecondLevelPartner());
            if (type == 1) {
                partnerVipIncome.setVipAddCount(1);
                secondPartnerVipIncome.setVipAddCount(1);
                Dict vipFirstPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion());
                Dict vipPartnerProportion = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion());
                Double proportion = Double.parseDouble(vipFirstPartnerProportion.getContent());
                Double otherProportion = Double.parseDouble(vipPartnerProportion.getContent());
                Double firstIncome = mul(addIncome, proportion);  //一级（事业）合伙人收益，
                Double secondIncome = mul(addIncome, otherProportion);  //二级（普通）合伙人收益
                partnerVipIncome.setVipAddIncome(firstIncome);   //事业合伙人得%7.5,二级返利
                partnerVipIncome.setSecondVipIncome(firstIncome);
                secondPartnerVipIncome.setVipAddIncome(secondIncome);    //普通合伙人得12.5%
                secondPartnerVipIncome.setFirstVipIncome(secondIncome);
            }else if (type == 2){
                Double firstPetrolIncome = mul(addIncome,firstPetrolProportion);
                Double secondPetrolIncome = mul(addIncome,secondPetrolProportion);
                partnerVipIncome.setVipAddIncome(secondPetrolIncome);
                partnerVipIncome.setSecondPetrolIncome(secondPetrolIncome);
                secondPartnerVipIncome.setVipAddIncome(firstPetrolIncome);
                secondPartnerVipIncome.setFirstPetrolIncome(firstPetrolIncome);
            }
            Boolean firstPartner = partnerVipIncomeMapperExtra.updateVipIncomeByAdd(partnerVipIncome)>0;
            Boolean secondPartner = partnerVipIncomeMapperExtra.updateVipIncomeByAdd(secondPartnerVipIncome)>0;
            return firstPartner && secondPartner;
        }
        return null;
    }

    @Override
    public PartnerVipIncomeDTO getVipIncomeByUser(User user) {
        if (user==null || user.getUserId()==null){
            return null;
        }
        PartnerVipIncomeDTO partnerVipIncomeDTO =  partnerVipIncomeMapperExtra.selectVipIncomeByPartnerId(user.getUserId());
        if (partnerVipIncomeDTO!=null){
            partnerVipIncomeDTO.setVipTotalMoney(add(partnerVipIncomeDTO.getFirstVipIncome(),partnerVipIncomeDTO.getSecondVipIncome()));
            partnerVipIncomeDTO.setPetrolTotalMoney(add(partnerVipIncomeDTO.getFirstPetrolIncome(),partnerVipIncomeDTO.getSecondPetrolIncome()));
        }
        return partnerVipIncomeDTO;
    }

    public Boolean initVipIncomeData() {
            //初始化数据库的合伙人vip收益，统计过去的数据（限用于数据初始化）
        List<PartnerBecomeTimeDTO> allPartner = partnerMapperExtra.selectPartnerBecomeTime();
        System.out.println("合伙人数量："+allPartner.size());
        for (PartnerBecomeTimeDTO partnerDTO : allPartner){
            Double totalMoney = 0.0;  //vip应得返佣
            Double firstIncome = 0.0;   //一级vip收益
            Double secondIncome = 0.0;  //二级vip收益
            Double firstPetrolIncome = 0.0; //一级油卡收益
            Double secondPetrolIncome = 0.0; //二级油卡收益
            Dict petrol1 = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getFirstPetrolProportion());
            Dict petrol2 = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getSecondPetrolProportion());
            Double petrolProportion1 = Double.parseDouble(petrol1.getContent());
            Double petrolProportion2 = Double.parseDouble(petrol2.getContent());
            Double petrolProportion = add(petrolProportion1,petrolProportion2);
            Integer totalCount = 0;   //下级新增的Vip数
            if (partnerDTO.getPartner() == 2){
                Double proportion = add(Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion()).getContent()),Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()).getContent()));
                PartnerVipMoney firstPartnerSubMoneyVip = partnerMapperExtra.selectFirstPartnerSubVip(partnerDTO);  //先算出事业合伙人直属下级的消费，利息12.5%+7.5%
                PartnerVipMoney firstPartnerSubMoneyPetrol = partnerMapperExtra.selectFirstPartnerSubPetrol(partnerDTO);
                if (firstPartnerSubMoneyVip!=null && firstPartnerSubMoneyVip.getVipConsumption()!=null) {
                    totalMoney = add(totalMoney, mul(firstPartnerSubMoneyVip.getVipConsumption(), proportion));  //乘以倍率算出vip第一部分
                    firstIncome = add(firstIncome, mul(firstPartnerSubMoneyVip.getVipConsumption(), proportion));

                }
                if (firstPartnerSubMoneyPetrol!=null && firstPartnerSubMoneyPetrol.getPetrolMoney()!=null) {
                    totalMoney = add(totalMoney, mul(firstPartnerSubMoneyPetrol.getPetrolMoney(), petrolProportion)); //乘以倍率算出油卡第一部分
                    firstPetrolIncome = add(firstPetrolIncome, mul(firstPartnerSubMoneyPetrol.getPetrolMoney(), petrolProportion));
                }
                List<SubPartnerDTO> subPartnerList = partnerMapperExtra.selectSubPartner(partnerDTO.getUserId());  //找到该事业合伙人的全部下级合伙人
                if (subPartnerList!=null && subPartnerList.size()>0) {
                    for (SubPartnerDTO subPartnerDTO : subPartnerList) {  //依次算出事业合伙人下级的每个普通合伙人分走的钱
                        PartnerVipMoney notBySecondVip = partnerMapperExtra.selectFirstNotBySecondPartnerSubVip(subPartnerDTO);   //下级成为合伙人之前，利息12.5%+7.5%
                        if (notBySecondVip != null && notBySecondVip.getVipConsumption() != null) {
                            totalMoney = add(totalMoney, mul(notBySecondVip.getVipConsumption(), proportion));
//                            totalMoney = add(totalMoney, mul(notBySecondVip.getVipConsumption(), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()).getContent())));
                            firstIncome = add(firstIncome, mul(notBySecondVip.getVipConsumption(), proportion));
                        }
                        PartnerVipMoney notBySecondPetrol = partnerMapperExtra.selectFirstNotBySecondPartnerSubPetrol(subPartnerDTO);
                        if (notBySecondPetrol != null && notBySecondPetrol.getPetrolMoney() != null){
                            totalMoney = add(totalMoney, mul(notBySecondPetrol.getPetrolMoney(), petrolProportion));
//                            totalMoney = add(totalMoney, mul(notBySecondPetrol.getPetrolMoney(), petrolProportion2));
                            firstPetrolIncome = add(firstPetrolIncome, mul(notBySecondPetrol.getPetrolMoney(), petrolProportion));
                        }
                        PartnerVipMoney BySecondVip = partnerMapperExtra.selectFirstBySecondPartnerSubVip(subPartnerDTO);  //下级成为合伙人之后，利息7.5%
                        if (BySecondVip != null && BySecondVip.getVipConsumption() != null) {
                            totalMoney = add(totalMoney, mul(BySecondVip.getVipConsumption(), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()).getContent())));
                            secondIncome = add(secondIncome, mul(BySecondVip.getVipConsumption(), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()).getContent())));
                        }
                        PartnerVipMoney BySecondPetrol = partnerMapperExtra.selectFirstBySecondPartnerSubPetrol(subPartnerDTO);
                        if (BySecondPetrol != null && BySecondPetrol.getPetrolMoney() != null){
                            totalMoney = add(totalMoney, mul(BySecondPetrol.getPetrolMoney(), petrolProportion2));
                            secondPetrolIncome = add(secondPetrolIncome, mul(BySecondPetrol.getPetrolMoney(),petrolProportion2));
                        }
                    }
                }
                VipCountDTO countDTO = partnerMapperExtra.selectFirstPartnerSubVipCount(partnerDTO.getUserId());  //算出Vip增长数
                if (countDTO!=null && countDTO.getVipCount()!=null)
                    totalCount += countDTO.getVipCount();
            } else if (partnerDTO.getPartner() == 1){    //只有一种利息12.5%，直接算
                PartnerVipMoney partnerVipMoneyVip = partnerMapperExtra.selectSecondPartnerSubVip(partnerDTO);
                if (partnerVipMoneyVip!=null && partnerVipMoneyVip.getVipConsumption()!=null) {
                    totalMoney = add(totalMoney,mul(partnerVipMoneyVip.getVipConsumption(), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion()).getContent())));
                    firstIncome = add(firstIncome, mul(partnerVipMoneyVip.getVipConsumption(), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion()).getContent())));
                }
                PartnerVipMoney partnerVipMoneyPetrol = partnerMapperExtra.selectSecondPartnerSubPetrol(partnerDTO);
                if (partnerVipMoneyPetrol!=null && partnerVipMoneyPetrol.getPetrolMoney()!=null) {
                    totalMoney = add(totalMoney,mul(partnerVipMoneyPetrol.getPetrolMoney(),petrolProportion1));
                    firstPetrolIncome = add(firstPetrolIncome,mul(partnerVipMoneyPetrol.getPetrolMoney(),petrolProportion1));
                }
                VipCountDTO countDTO = partnerMapperExtra.selectSecondPartnerSubVipCount(partnerDTO);
                if (countDTO!=null && countDTO.getVipCount()!=null)
                totalCount += countDTO.getVipCount();
            } else {
                break;
            }
//            PartnerVipIncome partnerVipIncome = new PartnerVipIncome();
//            partnerVipIncome.setPartnerId(partnerDTO.getUserId());
//            partnerVipIncome.setPartnerType(partnerDTO.getPartner());
//            partnerVipIncome.setIsSettle(0);
//            partnerVipIncome.setPartnerVipIncomeId(StringUtil.createId());
//            partnerVipIncome.setStartTime(new Date(119,9,11));
//            partnerVipIncome.setCreateAt(new Date());
//            partnerVipIncome.setVipAddIncome(totalMoney);
//            partnerVipIncome.setVipAddCount(totalCount);
//            partnerVipIncome.setFirstPetrolIncome(firstPetrolIncome);
//            partnerVipIncome.setSecondPetrolIncome(secondPetrolIncome);
//            partnerVipIncome.setFirstVipIncome(firstIncome);
//            partnerVipIncome.setSecondVipIncome(secondIncome);
//            partnerVipIncomeMapperExtra.updateByPrimaryKeySelective(partnerVipIncome);
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
        return true;
    }

    public Boolean initFyIncomeLogDataTTT() {
        //初始化数据库的合伙人vip收益，统计过去的数据（限用于数据初始化）
        List<PartnerBecomeTimeDTO> allPartner = partnerMapperExtra.selectPartnerBecomeTime();
        System.out.println("合伙人数量：" + allPartner.size());
        for (PartnerBecomeTimeDTO partnerDTO : allPartner) {
            Double totalMoney = 0.0;  //vip应得返佣
            Double firstIncome = 0.0;   //一级vip收益
            Double secondIncome = 0.0;  //二级vip收益
            Double firstPetrolIncome = 0.0; //一级油卡收益
            Double secondPetrolIncome = 0.0; //二级油卡收益
            Dict petrol1 = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getFirstPetrolProportion());
            Dict petrol2 = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getSecondPetrolProportion());
            Double petrolProportion1 = Double.parseDouble(petrol1.getContent());
            Double petrolProportion2 = Double.parseDouble(petrol2.getContent());
            Double petrolProportion = add(petrolProportion1, petrolProportion2);
            Integer totalCount = 0;   //下级新增的Vip数
            if (partnerDTO.getPartner() == 2) {

                Double proportion = add(Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion()).getContent()), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()).getContent()));
                List<PartnerVipMoney> firstPartnerSubMoneyVip = partnerMapperExtra.selectAllFirstPartnerSubVip(partnerDTO);  //先算出事业合伙人直属下级的消费，利息12.5%+7.5%
                List<PartnerVipMoney> firstPartnerSubMoneyPetrol = partnerMapperExtra.selectAllFirstPartnerSubPetrol(partnerDTO);
                for (PartnerVipMoney partnerVipMoney : firstPartnerSubMoneyVip) {
                    if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                        User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "充值vip返佣";
                        fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),1);
                    }
                }
                for (PartnerVipMoney partnerVipMoney: firstPartnerSubMoneyPetrol) {
                    if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                        User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "购油返佣";
                        fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),1);
                    }
                }
                List<SubPartnerDTO> subPartnerList = partnerMapperExtra.selectSubPartner(partnerDTO.getUserId());  //找到该事业合伙人的全部下级合伙人
                if (subPartnerList != null && subPartnerList.size() > 0) {
                    for (SubPartnerDTO subPartnerDTO : subPartnerList) {  //依次算出事业合伙人下级的每个普通合伙人分走的钱
                        List<PartnerVipMoney> notBySecondVip = partnerMapperExtra.selectAllFirstNotBySecondPartnerSubVip(subPartnerDTO);   //下级成为合伙人之前，利息12.5%+7.5%
                        for (PartnerVipMoney partnerVipMoney: notBySecondVip) {
                            if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "充值vip返佣";
                                PartnerBecomeTimeDTO oldSuper = partnerMapperExtra.selectOldPartnerBecomeTimeOne(partnerVipMoney.getUserId());
                                if (oldSuper!=null&&oldSuper.getCreateAt()!=null&&oldSuper.getCreateAt().getTime()<partnerVipMoney.getCreateAt().getTime()){
                                    fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),2);
                                    continue;
                                }
                                fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),1);
                            }
                        }
                        List<PartnerVipMoney> notBySecondPetrol = partnerMapperExtra.selectAllFirstNotBySecondPartnerSubPetrol(subPartnerDTO);
                        for (PartnerVipMoney partnerVipMoney : notBySecondPetrol) {
                            if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "购油返佣";
                                PartnerBecomeTimeDTO oldSuper = partnerMapperExtra.selectOldPartnerBecomeTimeOne(partnerVipMoney.getUserId());
                                if (oldSuper!=null&&oldSuper.getCreateAt()!=null&&oldSuper.getCreateAt().getTime()<partnerVipMoney.getCreateAt().getTime()){
                                    fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),2);
                                    continue;
                                }
                                fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),1);
                            }
                        }
                        List<PartnerVipMoney> BySecondVip = partnerMapperExtra.selectAllFirstBySecondPartnerSubVip(subPartnerDTO);  //下级成为合伙人之后，利息7.5%
                        for (PartnerVipMoney partnerVipMoney : BySecondVip) {
                            if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "充值vip返佣";
                                fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),2);
                            }
                        }
                        List<PartnerVipMoney> BySecondPetrol = partnerMapperExtra.selectAllFirstBySecondPartnerSubPetrol(subPartnerDTO);
                        for (PartnerVipMoney partnerVipMoney : BySecondPetrol) {
                            if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "购油返佣";
                                fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),2);
                            }
                        }
                    }
                }
            } else if (partnerDTO.getPartner() == 1) {    //只有一种利息12.5%，直接算
                List<PartnerVipMoney> partnerVipMoneyVip = partnerMapperExtra.selectAllSecondPartnerSubVip(partnerDTO);
                for (PartnerVipMoney partnerVipMoney : partnerVipMoneyVip) {
                    if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                        User user = userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "充值vip返佣";
                        fanYongService.FyIncomeLogTest(2,user, FyRemark, partnerVipMoney.getPartnerId(), partnerVipMoney.getVipConsumption(), partnerVipMoney.getRecordId(),1);
                    }
                }
                List<PartnerVipMoney> partnerVipMoneyPetrol = partnerMapperExtra.selectAllSecondPartnerSubPetrol(partnerDTO);
                for (PartnerVipMoney partnerVipMoney : partnerVipMoneyPetrol) {
                    if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                        User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "购油返佣";
                        fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),1);
                    }
                }
            } else {
            }
        }
        return true;
    }


    @Override
    public Boolean initFyIncomeLogData() throws Exception {
        File file = new File("/log.txt");
        if (!file.exists()){
             file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        FileWriter fileWritter = new FileWriter(file.getName(),true);

        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


        //初始化数据库的合伙人vip收益，统计过去的数据（限用于数据初始化）155962733891547
            PartnerBecomeTimeDTO partnerDTO = partnerMapperExtra.selectPartnerBecomeTimeOne("155980397456674");
            Double totalMoney = 0.0;  //vip应得返佣
            Double firstIncome = 0.0;   //一级vip收益
            Double secondIncome = 0.0;  //二级vip收益
            Double firstPetrolIncome = 0.0; //一级油卡收益
            Double secondPetrolIncome = 0.0; //二级油卡收益
            Dict petrol1 = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getFirstPetrolProportion());
            Dict petrol2 = dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getSecondPetrolProportion());
            Double petrolProportion1 = Double.parseDouble(petrol1.getContent());
            Double petrolProportion2 = Double.parseDouble(petrol2.getContent());
            Double petrolProportion = add(petrolProportion1, petrolProportion2);
            Integer totalCount = 0;   //下级新增的Vip数
            if (partnerDTO.getPartner() == 2) {
                Double proportion = add(Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipPartnerProportion()).getContent()), Double.parseDouble(dictMapperExtra.selectDictByName(PartnerVipIncomeConfig.getVipFirstPartnerProportion()).getContent()));
                List<PartnerVipMoney> firstPartnerSubMoneyVip = partnerMapperExtra.selectAllFirstPartnerSubVip(partnerDTO);  //先算出事业合伙人直属下级的消费，利息12.5%+7.5%
                bufferWritter.write("纯一级Vip返佣:"+firstPartnerSubMoneyVip.size()+"\n");
                List<PartnerVipMoney> firstPartnerSubMoneyPetrol = partnerMapperExtra.selectAllFirstPartnerSubPetrol(partnerDTO);
                bufferWritter.write("纯一级购油返佣:"+firstPartnerSubMoneyPetrol.size()+"\n");
                bufferWritter.write("纯一级Vip返佣:recordId+userId+amonut"+"\n");
                for (PartnerVipMoney partnerVipMoney : firstPartnerSubMoneyVip) {
                    if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                        User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "充值vip返佣";
                        bufferWritter.write(partnerVipMoney.getRecordId()+user.getUserId()+partnerVipMoney.getVipConsumption()+"\n");
                        fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),1);
                    }
                }
                bufferWritter.write("纯一级购油返佣:recordId+userId+amonut"+"\n");
                for (PartnerVipMoney partnerVipMoney: firstPartnerSubMoneyPetrol) {
                    if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                        User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "购油返佣";
                        bufferWritter.write(partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getPetrolMoney()+"\n");
                        fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),1);
                    }
                }
                List<SubPartnerDTO> subPartnerList = partnerMapperExtra.selectSubPartner(partnerDTO.getUserId());  //找到该事业合伙人的全部下级合伙人
                bufferWritter.write("全部下级合伙人"+subPartnerList.size()+"\n");
                if (subPartnerList != null && subPartnerList.size() > 0) {
                    for (SubPartnerDTO subPartnerDTO : subPartnerList) {  //依次算出事业合伙人下级的每个普通合伙人分走的钱
                        List<PartnerVipMoney> notBySecondVip = partnerMapperExtra.selectAllFirstNotBySecondPartnerSubVip(subPartnerDTO);   //下级成为合伙人之前，利息12.5%+7.5%
                        bufferWritter.write("成为合伙人之前消费的："+notBySecondVip.size()+"\n");
                        bufferWritter.write("非纯一级Vip返佣：recordId+userId+amonut"+"\n");
                        for (PartnerVipMoney partnerVipMoney: notBySecondVip) {
                            if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "充值vip返佣";
                                PartnerBecomeTimeDTO oldSuper = partnerMapperExtra.selectOldPartnerBecomeTimeOne(partnerVipMoney.getUserId());
                                if (oldSuper!=null&&oldSuper.getCreateAt()!=null&&oldSuper.getCreateAt().getTime()<partnerVipMoney.getCreateAt().getTime()){
                                    bufferWritter.write("这是二级返佣："+partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getVipConsumption()+"\n");
                                    fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),2);
                                    continue;
                                }
                                bufferWritter.write(partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getVipConsumption()+"\n");
                                fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),1);
                            }
                        }
                        List<PartnerVipMoney> notBySecondPetrol = partnerMapperExtra.selectAllFirstNotBySecondPartnerSubPetrol(subPartnerDTO);
                        bufferWritter.write("非纯一级购油返佣：recordId+userId+amonut"+"\n");
                        for (PartnerVipMoney partnerVipMoney : notBySecondPetrol) {
                            if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "购油返佣";
                                PartnerBecomeTimeDTO oldSuper = partnerMapperExtra.selectOldPartnerBecomeTimeOne(partnerVipMoney.getUserId());
                                if (oldSuper!=null&&oldSuper.getCreateAt()!=null&&oldSuper.getCreateAt().getTime()<partnerVipMoney.getCreateAt().getTime()){
                                    bufferWritter.write("这是二级返佣："+partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getPetrolMoney()+"\n");
                                    fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),2);
                                    continue;
                                }
                                bufferWritter.write(partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getPetrolMoney()+"\n");
                                fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),1);
                            }
                        }
                        List<PartnerVipMoney> BySecondVip = partnerMapperExtra.selectAllFirstBySecondPartnerSubVip(subPartnerDTO);  //下级成为合伙人之后，利息7.5%
                        bufferWritter.write("二级vip返佣：recordId+userId+amonut"+"\n");
                        for (PartnerVipMoney partnerVipMoney : BySecondVip) {
                            if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "充值vip返佣";
                                bufferWritter.write(partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getVipConsumption()+"\n");
                                fanYongService.FyIncomeLogTest(2,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getRecordId(),2);
                            }
                        }
                        List<PartnerVipMoney> BySecondPetrol = partnerMapperExtra.selectAllFirstBySecondPartnerSubPetrol(subPartnerDTO);
                        bufferWritter.write("二级购油返佣：recordId+userId+amonut"+"\n");
                        for (PartnerVipMoney partnerVipMoney : BySecondPetrol) {
                            if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                                User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                                String FyRemark = "购油返佣";
                                bufferWritter.write(partnerVipMoney.getRecordId()+"\\\\"+user.getUserId()+"\\\\"+partnerVipMoney.getPetrolMoney()+"\n");
                                fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),2);
                            }
                        }
                    }
                }
            } else if (partnerDTO.getPartner() == 1) {    //只有一种利息12.5%，直接算
                List<PartnerVipMoney> partnerVipMoneyVip = partnerMapperExtra.selectAllSecondPartnerSubVip(partnerDTO);
                for (PartnerVipMoney partnerVipMoney : partnerVipMoneyVip) {
                    if (partnerVipMoney != null && partnerVipMoney.getVipConsumption() != null) {
                        User user = userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "充值vip返佣";
                        fanYongService.FyIncomeLogTest(2,user, FyRemark, partnerVipMoney.getPartnerId(), partnerVipMoney.getVipConsumption(), partnerVipMoney.getRecordId(),1);
                    }
                }
                //如果下级有人升级为普通合伙人导致漏算
                List<PartnerBecomeTimeDTO> beforeBecomeVip = partnerMapperExtra.selectBeforeBecome(partnerDTO.getUserId());
                if (beforeBecomeVip!=null && beforeBecomeVip.get(0)!=null) {
                    for (PartnerBecomeTimeDTO before : beforeBecomeVip) {
                        List<PartnerVipMoney> beforeVip = partnerMapperExtra.selectByOldSuperVip(before);
                        if (beforeVip!=null && beforeVip.size()>0){
                            for (PartnerVipMoney vip: beforeVip){
                                User user = userMapper.selectByPrimaryKey(vip.getUserId());
                                String FyRemark = "充值vip返佣";
                                fanYongService.FyIncomeLogTest(2,user, FyRemark, vip.getPartnerId(), vip.getVipConsumption(), vip.getRecordId(),1);
                            }
                        }
                    }
                }

                List<PartnerVipMoney> partnerVipMoneyPetrol = partnerMapperExtra.selectAllSecondPartnerSubPetrol(partnerDTO);
                for (PartnerVipMoney partnerVipMoney : partnerVipMoneyPetrol) {
                    if (partnerVipMoney != null && partnerVipMoney.getPetrolMoney() != null) {
                        User user=userMapper.selectByPrimaryKey(partnerVipMoney.getUserId());
                        String FyRemark = "购油返佣";
                        fanYongService.FyIncomeLogTest(1,user,FyRemark,partnerVipMoney.getPartnerId(),partnerVipMoney.getPetrolMoney(),partnerVipMoney.getRecordId(),1);
                    }
                }

                //如果下级有人升级为普通合伙人导致漏算
                List<PartnerBecomeTimeDTO> beforeBecomePetrol = partnerMapperExtra.selectBeforeBecome(partnerDTO.getUserId());
                if (beforeBecomePetrol!=null && beforeBecomePetrol.get(0)!=null) {
                    for (PartnerBecomeTimeDTO before : beforeBecomePetrol) {
                        List<PartnerVipMoney> beforePetrol = partnerMapperExtra.selectByOldSuperPetrol(before);
                        if (beforePetrol!=null && beforePetrol.size()>0){
                            for (PartnerVipMoney vip: beforePetrol){
                                User user = userMapper.selectByPrimaryKey(vip.getUserId());
                                String FyRemark = "购油返佣";
                                fanYongService.FyIncomeLogTest(1,user, FyRemark, vip.getPartnerId(), vip.getPetrolMoney(), vip.getRecordId(),1);
                            }
                        }
                    }
                }


            } else {
            }
        bufferWritter.close();
        return true;
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
        double mul = mul1.multiply(mul2).setScale(2,BigDecimal.ROUND_DOWN).doubleValue(); //直接去掉金额小数点两位后面的数
//        double mul = mul1.multiply(mul2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); //四舍五入
        return mul;
    }

    public Double add (Double num1,Double num2){
        BigDecimal add1 = new BigDecimal(Double.toString(num1));
        BigDecimal add2 = new BigDecimal(Double.toString(num2));
        return new Double(add1.add(add2).doubleValue());
    }

}
