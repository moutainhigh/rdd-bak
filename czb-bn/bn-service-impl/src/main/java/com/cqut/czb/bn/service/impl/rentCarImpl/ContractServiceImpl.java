package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.dao.mapper.ContractMapper;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import com.cqut.czb.bn.util.string.StringUtil;
import io.swagger.models.auth.In;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService{
    @Autowired
    private ContractMapper contractMapper;

    // type: 0 获得token 1 注册云id 2 注册印章

    public String getContractToken(){
        JSONObject request = new JSONObject();
        request.put("appId", "2019042516271800110");
        request.put("appKey", "uDCFes85C3OwDQ");
        String response = HttpClient4.doPost("https://api.yunhetong.com/api/auth/login", request, 0);

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
        requestJson.put("caType", "B2");

        requestJson.put("token", token);
//        String response = HttpClient4.doPost("https://api.yunhetong.com/api/user/person", requestJson, 1);
        // TODO 这里注册个人云合同id的同时，进行个人印章注册,要先根据返回确定是否注册用户成功,这里先写死是成功的,同时需要yunId
        if(true){
            JSONObject sealRequestJson = new JSONObject();
            sealRequestJson.put("signerId", "yunId");
            sealRequestJson.put("zoomCode", "0");
            String response = HttpClient4.doPost("https://api.yunhetong.com/api/user/personMoulage", sealRequestJson, 2);
        }

        StringUtil.createId();
        // TODO 这里需要根据公共信息获取userId，再去将获得的云合同注册id插入到对应表去，这里先写死数据
        int userType = 1;
        // 向user表里插入云合同注册id
        contractMapper.insertUserContractYunId("1", "111");

        // TODO 返回信息需要修改
        return new String("1");
    }

    public String registerEnterpriseContractAccount(EnterpriseRegisterDTO enterpriseRegisterDTO, String token){
        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", enterpriseRegisterDTO.getUserName());
        requestJson.put("certifyType", enterpriseRegisterDTO.getCertifyType());
        requestJson.put("certifyNum", enterpriseRegisterDTO.getCertifyNum());
        requestJson.put("phoneNo", enterpriseRegisterDTO.getPhoneNo());
        requestJson.put("caType", "B2");
        requestJson.put("token", token);

//        String response = HttpClient4.doPost("https://api.yunhetong.com/api/user/person", requestJson, 1);

        // TODO 这里注册企业云合同id的同时，进行企业印章注册,要先根据返回确定是否注册用户成功,这里先写死是成功的,同时需要yunId
        if(true){
            JSONObject sealRequestJson = new JSONObject();
            sealRequestJson.put("signerId", "yunId");
            sealRequestJson.put("styleType", "1");
            String response = HttpClient4.doPost("https://api.yunhetong.com/api/user/personMoulage", sealRequestJson, 2);
        }

        StringUtil.createId();
        // TODO 这里需要根据公共信息获取userId，再去将获得的云合同注册id插入到对应表去，这里先写死数据
        int userType = 1;
        // 向enterprise表里插入云合同注册id
        contractMapper.insertEnterpriseContractYunId(contractMapper.getEnterpriseId("1"), "333");
        // TODO 返回信息需要修改
        return new String("1");
    }
}
