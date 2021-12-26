package com.cqut.czb.bn.dao.mapper.directCustomerManager;

import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectCustomerMapperExtra {

    List<DirectCustomerManagerDto> getCustomerList(DirectChargingOrderDto directChargingOrderDto);

    List<DirectCustomerManagerDto> getConsumptionList(DirectChargingOrderDto directChargingOrderDto);

    List<DirectCustomerManagerDto> getRechargeList(DirectChargingOrderDto directChargingOrderDto);

    List<DirectCustomerManagerDto> getRechargeAccountList(DirectChargingOrderDto directChargingOrderDto);

    double getTotalConsumption(DirectChargingOrderDto directChargingOrderDto);
    DirectCustomerManagerDto getTotalRecharge(DirectChargingOrderDto directChargingOrderDto);

    int addAgent(DirectCustomerManagerDto directCustomerManagerDto);

    int addAgentRole(DirectCustomerManagerDto directCustomerManagerDto);

    String getAgentRole();

    DirectCustomerManagerDto findAccount(DirectCustomerManagerDto directCustomerManagerDto);

    DirectCustomerManagerDto findUserId(DirectCustomerManagerDto directCustomerManagerDto);

    String getPassword();

    String getSpecialPassword();

    int recharge(DirectCustomerManagerDto directCustomerManagerDto);

    DirectCustomerManagerDto getAccountBalance(DirectCustomerManagerDto directCustomerManagerDto);

    DirectCustomerManagerDto getOneByAccount(DirectCustomerManagerDto directCustomerManagerDto);

    int changeBalance(DirectCustomerManagerDto directCustomerManagerDto);
    int changePassword(DirectCustomerManagerDto directCustomerManagerDto);
}
