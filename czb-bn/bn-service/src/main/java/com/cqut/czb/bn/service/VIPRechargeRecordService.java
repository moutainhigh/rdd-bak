package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 22:24
 */
public interface VIPRechargeRecordService {
    VipRechargeRecordDTO getVipRechargeRecordList(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    Boolean deleteVIPRechargeByID(String vipRechargeRecordId);

    Boolean addVipMoney(VipRechargeRecordListDTO vipRechargeRecordListDTO);
}