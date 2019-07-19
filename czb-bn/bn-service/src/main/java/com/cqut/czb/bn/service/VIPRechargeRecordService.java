package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.github.pagehelper.PageInfo;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 22:24
 */
public interface VIPRechargeRecordService {
    PageInfo getVipRechargeRecordList(VipRechargeRecordListDTO vipRechargeRecordListDTO);

    Boolean deleteVIPRechargeByID(String vipRechargeRecordId);
}