package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.entity.VipRechargeRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VipRechargeRecordsMapperExtra {

    int insertVipRechargeRecord(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    List<VipRechargeRecordListDTO> getVipRechargeRecord(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    int insert(VipRechargeRecords vipRechargeRecords);

    String selectVipArea(@Param("ownerId") String ownerId);

}