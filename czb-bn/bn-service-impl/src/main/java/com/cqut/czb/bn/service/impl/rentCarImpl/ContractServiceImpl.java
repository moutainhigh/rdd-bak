package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.dao.mapper.ContractMapper;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService{
    @Autowired
    private ContractMapper contractMapper;

    public String getContractToken(){
        JSONObject request = new JSONObject();
        request.put("appId", "2019042516271800110");
        request.put("appKey", "uDCFes85C3OwDQ");
        String response = HttpClient4.doPost("https://api.yunhetong.com/api/auth/login", request);

        return response;
    }

    public String registerPersonalContractAccount(PersonalRegisterDTO personalRegisterDTO, String token){
        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", personalRegisterDTO.getUserName());
        requestJson.put("identityRegion", personalRegisterDTO.getIdentityRegion());
        requestJson.put("certifyType", personalRegisterDTO.getCertifyType());
        requestJson.put("certifyNum", personalRegisterDTO.getCertifyNum());
        requestJson.put("phoneRegion", personalRegisterDTO.getPhoneRegion());
        requestJson.put("phoneNo", personalRegisterDTO.getPhoneNo());
        requestJson.put("caType", personalRegisterDTO.getCaType());
        requestJson.put("token", token);
//        String response = HttpClient4.doPost("https://api.yunhetong.com/api", requestJson);

        return new String();
    }
}
