package com.cqut.czb.bn.service.impl.ThirdBusiness;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.service.ThirdBusinessService.GetUnChargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUnChargeOrderServiceImpl implements GetUnChargeOrderService{

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Override
    public List<GetUnChargeOrderDTO> getUnChargeOrder() {
        return petrolSalesRecordsMapperExtra.getUnChargeOrders();
    }

    @Override
    public Boolean InputChargeOrders(String obj) {
        if(obj==null){
            return false;
        }
        List<GetUnChargeOrderDTO> GetUnChargeOrderDTOs = JSONObject.parseArray(obj,GetUnChargeOrderDTO.class);
        return petrolSalesRecordsMapperExtra.inputChargeOrders(GetUnChargeOrderDTOs)>0;
    }


}
