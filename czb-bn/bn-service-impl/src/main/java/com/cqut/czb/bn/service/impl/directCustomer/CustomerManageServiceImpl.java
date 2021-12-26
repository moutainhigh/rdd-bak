package com.cqut.czb.bn.service.impl.directCustomer;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.directCustomer.CustomerManageMapperExtra;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerLoginDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomer.CustomerManageService;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerManageServiceImpl implements CustomerManageService {

    @Autowired
    CustomerManageMapperExtra customerManageMapperExtra;

    @Autowired
    private DictMapperExtra dictMapperExtra;

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
        if (customerManageDto1.getBalance() >= customerManageDto.getAmountRecovered()) {
            customerManageDto1.setBalance(customerManageDto1.getBalance() - customerManageDto.getAmountRecovered());
            customerManageDto1.setAmountRecovered(customerManageDto.getAmountRecovered() + customerManageDto1.getAmountRecovered());

            int update = customerManageMapperExtra.recovered(customerManageDto1);
            if (update > 0){
                return new JSONResult("更新成功", 200);
            }
        }
        return new JSONResult("更新失败", 500);
    }

    @Override
    public JSONResult checkPassword(CustomerLoginDto customerLoginDto) {
        CustomerLoginDto customerLoginDto1 = customerManageMapperExtra.checkPassword(customerLoginDto);
        String sign = MD5Util.MD5Encode(customerLoginDto.getContent(),"UTF-8").toLowerCase();
        if (customerLoginDto1.getContent().equals(sign)) {
            return new JSONResult("校验成功", 200, true);
        }
        return new JSONResult("校验失败", 500, false);
    }

    @Override
    public JSONResult addCustomer(CustomerManageDto customerManageDto) {
        customerManageDto.setCustomerId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        customerManageDto.setCustomerNumber((System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", "")).substring(8,11));
        customerManageDto.setAppId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        customerManageDto.setAppSecret(System.currentTimeMillis() + UUID.randomUUID().toString().substring(12, 17).replace("-", ""));
        customerManageDto.setConsumptionAmount(0);
        customerManageDto.setBalance(0);
        customerManageDto.setAmountRecovered(0);
        customerManageDto.setRechargeAmount(0);

        if (customerManageMapperExtra.addCustomer(customerManageDto) > 0) {
            return new JSONResult("新增成功", 200);
        }
        return new JSONResult("新增失败", 500);
    }

    @Override
    public JSONResult changeRecharge(CustomerManageDto customerManageDto) {
        System.out.println("isRecharge为" + customerManageDto.getIsShutRecharge());
        int isRecharge = customerManageMapperExtra.changeRecharge(customerManageDto);
        if (isRecharge > 0){
            return new JSONResult("更新成功", 200);
        }
        return new JSONResult("更新失败", 500);
    }

}
