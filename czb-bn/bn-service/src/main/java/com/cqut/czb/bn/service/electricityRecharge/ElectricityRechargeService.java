package com.cqut.czb.bn.service.electricityRecharge;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface ElectricityRechargeService {
    JSONResult getCustomers(ElectricityRechargeDto electricityRechargeDto);

    JSONResult editOrder(ElectricityRechargeDto electricityRechargeDto);

    Workbook exportData(ElectricityRechargeDto electricityRechargeDto) throws Exception;

    int importData(MultipartFile file, Integer recordType) throws Exception;

    /**
     * 微信支付
     * 充值
     */
    JSONObject WeChatRechargeDirect(DirectChargingOrderDto directChargingOrderDto);

    String findRegional(String orgId);

    int changeState(String orgId);

    int changeFinishTime(ElectricityRechargeDto electricityRechargeDto);

    String AlipayRechargeDirect(DirectChargingOrderDto directChargingOrderDto);

    JSONResult updateList(ElectricityRechargeDto electricityRechargeDto);

    JSONResult elcOrder(ElectricityDto electricityDto);

    JSONResult getTotal(ElectricityRechargeDto electricityRechargeDto);
}
