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
        return null;
    }
}
