package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapperExtra;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapper;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.global.DateDealWith;
import com.cqut.czb.bn.service.VIPRechargeRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}