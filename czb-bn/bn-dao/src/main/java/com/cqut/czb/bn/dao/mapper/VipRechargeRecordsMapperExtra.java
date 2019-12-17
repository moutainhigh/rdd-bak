package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.entity.VipRechargeRecords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipRechargeRecordsMapperExtra {

    int insertVipRechargeRecord(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    List<VipRechargeRecordListDTO> getVipRechargeRecord(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    List<VipRechargeRecordListDTO> getVipRechargeRecordTest(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    int insert(VipRechargeRecords vipRechargeRecords);

    String selectVipArea(@Param("ownerId") String ownerId);

    Double getVipRechargeTotalMoney(VipRechargeRecordListDTO vipRechargeRecordListDTO);

}