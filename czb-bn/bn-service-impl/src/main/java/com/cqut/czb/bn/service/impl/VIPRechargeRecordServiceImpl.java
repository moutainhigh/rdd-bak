package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapper;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.service.VIPRechargeRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo getVipRechargeRecordList(VipRechargeRecordListDTO vipRechargeRecordListDTO) {
        PageHelper.startPage(vipRechargeRecordListDTO.getCurrentPage(), vipRechargeRecordListDTO.getPageSize());
        return new PageInfo<>(vipRechargeRecordsMapperExtra.getVipRechargeRecord(vipRechargeRecordListDTO));
    }

    @Override
    public Boolean deleteVIPRechargeByID(String vipRechargeRecordId) {
        return vipRechargeRecordsMapper.deleteByPrimaryKey(vipRechargeRecordId) > 0;
    }
}