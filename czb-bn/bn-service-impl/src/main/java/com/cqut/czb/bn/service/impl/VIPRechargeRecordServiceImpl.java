package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapperExtra;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapper;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
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
        List<VipRechargeRecordListDTO> vipRechargeRecordListDTOS = vipRechargeRecordsMapperExtra.getVipRechargeRecord(vipRechargeRecordListDTO);
        vipRechargeRecordDTO.setVipRechargeRecordListDTOList(new PageInfo<>(vipRechargeRecordListDTOS));
        double totalAmount = 0;
        for(VipRechargeRecordListDTO vipRechargeRecordListDTO1 : vipRechargeRecordListDTOS){
            totalAmount += vipRechargeRecordListDTO1.getAmount();
        }
        vipRechargeRecordDTO.setTotalAmount(totalAmount);
        return vipRechargeRecordDTO;
    }

    @Override
    public Boolean deleteVIPRechargeByID(String vipRechargeRecordId) {
        return vipRechargeRecordsMapper.deleteByPrimaryKey(vipRechargeRecordId) > 0;
    }


}