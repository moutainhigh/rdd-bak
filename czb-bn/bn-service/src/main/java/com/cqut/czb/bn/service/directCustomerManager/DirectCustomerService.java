package com.cqut.czb.bn.service.directCustomerManager;

import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface DirectCustomerService {

    JSONResult getCustomerData(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getConsumptionList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult addAgent(DirectCustomerManagerDto directCustomerManagerDto);

    JSONResult getTotalConsumption(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getTotalRecharge(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getRechargeList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getRechargeAccountList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult passwordVerification(String password,Integer isSpecial);

    JSONResult recharge(DirectCustomerManagerDto directCustomerManagerDto);

    JSONResult getAccountBalance(DirectCustomerManagerDto directCustomerManagerDto);

    JSONResult changePassword(DirectCustomerManagerDto directCustomerManagerDto);

    Workbook exportDirectAgent(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    Workbook exportDirectAgentConsumption(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    Workbook exportDirectAgentRecharge(DirectChargingOrderDto directChargingOrderDto) throws Exception;
}
