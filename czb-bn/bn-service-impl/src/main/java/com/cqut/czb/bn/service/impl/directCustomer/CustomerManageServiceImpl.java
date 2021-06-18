package com.cqut.czb.bn.service.impl.directCustomer;

import com.cqut.czb.bn.dao.mapper.directCustomer.CustomerManageMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomer.CustomerManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManageServiceImpl implements CustomerManageService {

    @Autowired
    CustomerManageMapperExtra customerManageMapperExtra;

    @Override
    public JSONResult getCustomers(CustomerManageDto customerManageDto) {
        PageHelper.startPage(customerManageDto.getCurrentPage(), customerManageDto.getPageSize(),true);
        List<CustomerManageDto> withdrawList = customerManageMapperExtra.getCustomers(customerManageDto);
        PageInfo<CustomerManageDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult recharge(CustomerManageDto customerManageDto) {
        CustomerManageDto customerManageDto1 = customerManageMapperExtra.findCustomer(customerManageDto);
        customerManageDto1.setBalance(customerManageDto.getRechargeAmount()+customerManageDto1.getBalance());
        customerManageDto1.setRechargeAmount(customerManageDto.getRechargeAmount() + customerManageDto1.getRechargeAmount());

        int update = customerManageMapperExtra.recharge(customerManageDto1);
        if (update > 0){
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult recovered(CustomerManageDto customerManageDto) {
        CustomerManageDto customerManageDto1 = customerManageMapperExtra.findCustomer(customerManageDto);
        customerManageDto1.setBalance(customerManageDto1.getBalance() - customerManageDto.getAmountRecovered());
        customerManageDto1.setAmountRecovered(customerManageDto.getAmountRecovered() + customerManageDto1.getAmountRecovered());

        int update = customerManageMapperExtra.recovered(customerManageDto1);
        if (update > 0){
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }
}
