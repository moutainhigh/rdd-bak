package com.cqut.czb.bn.service.impl.ThirdBusiness;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetChargeOrderInputDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ThirdBusinessService.GetUnChargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GetUnChargeOrderServiceImpl implements GetUnChargeOrderService{

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Override
    public List<GetUnChargeOrderDTO> getUnChargeOrder() {

        List<GetUnChargeOrderDTO> list=petrolSalesRecordsMapperExtra.getUnChargeOrders();
        if(list==null){
            return null;
        }
        else {
            return getPetrolNum(list);
        }
    }

    @Override
    public Boolean InputChargeOrders(String obj) {
        if(obj==null){
            return false;
        }
        List<GetChargeOrderInputDTO> list= JSONObject.parseArray(obj,GetChargeOrderInputDTO.class);

        List<GetChargeOrderInputDTO> successfulOrders=new ArrayList<>();
        List<GetChargeOrderInputDTO> failureOrders=new ArrayList<>();
        //判断哪些没有充值成功
        for(int i=0;i<list.size();i++){
            if("succeed".equals(list.get(i).getState())){
                successfulOrders.add(list.get(i));
            }else if("fail".equals(list.get(i).getState())){
                failureOrders.add(list.get(i));
            }
        }

        return petrolSalesRecordsMapperExtra.inputChargeOrders(list)>0;
    }

    public void treatmentFail(List<GetChargeOrderInputDTO> list){
        System.out.println(list);
    }


    public List<GetUnChargeOrderDTO> getPetrolNum(List<GetUnChargeOrderDTO> list){

        for(int i=0;i<list.size();i++){
            list.get(i).setPetrolNum(regExNum(list.get(i).getPetrolNum()));
        }
        return list;
    }

    public String regExNum(String str){
        System.out.println(str);
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        System.out.println( m.replaceAll("").trim());
        return m.replaceAll("").trim();
    }

}
