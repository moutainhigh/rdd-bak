package com.cqut.czb.bn.dao.mapper.directCustomerManager;

import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectAgentMapperExtra {
    DirectCustomerManagerDto getBalance(String userId);

    void changeBalance(DirectCustomerManagerDto directCustomerManagerDto);

    void changeConsumption(DirectCustomerManagerDto directCustomerManagerDto);

    void insertRecharge(DirectCustomerManagerDto directCustomerManagerDto);

    double findBalanceById(String userId);

    double findConsumptionById(String userId);

    String findAccountById(String petrolNum);

    double findTotalRecharge(String userId);

    List<DirectCustomerManagerDto> getRechargeDetails(DirectCustomerManagerDto directCustomerManagerDto);
}
