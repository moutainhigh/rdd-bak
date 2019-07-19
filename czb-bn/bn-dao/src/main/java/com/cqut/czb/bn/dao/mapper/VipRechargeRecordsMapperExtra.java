package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;

import java.util.List;

public interface VipRechargeRecordsMapperExtra {

    int insertVipRechargeRecord(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    List<VipRechargeRecordListDTO> getVipRechargeRecord(VipRechargeRecordListDTO vipRechargeRecordListDTO);
}