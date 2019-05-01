package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.dao.mapper.ContractMapper;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService{
    private final ContractMapper contractMapper;

    @Autowired
    public ContractServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    // type: 0 获得token 1 注册云id 2 注册印章 3 根据模块生成未签署合同

    /**
     * 获取云合同token
     * @return token
     */
    @Override
    public String getContractToken(){
        JSONObject request = new JSONObject();
        request.put("appId", "2019042516271800110");
        request.put("appKey", "uDCFes85C3OwDQ");
        String response = HttpClient4.doPost("https://api.yunhetong.com/api/auth/login", request, 0);
        int indexMax = response.length();
        response = response.substring(7, indexMax);

        return response;
    }

    /**
     * 获取合同id
     */
    @Override
    public String getContractId(){
        String response = new String("1904271759031848");
        JSONObject json = new JSONObject();
        json.put("contractId", response);

        return json.toString();
    }
    /**
     * 为个人创建云合同
     * @param personalRegisterDTO
     * @param token
     * @return message
     */
    @Override
    public String registerPersonalContractAccount(PersonalRegisterDTO personalRegisterDTO, String token){
        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", personalRegisterDTO.getUserName()); //姓名
        requestJson.put("identityRegion", personalRegisterDTO.getIdentityRegion()); //身份
        requestJson.put("certifyType", personalRegisterDTO.getCertifyType());
        requestJson.put("certifyNum", personalRegisterDTO.getCertifyNum());
        requestJson.put("phoneRegion", personalRegisterDTO.getPhoneRegion());
        requestJson.put("phoneNo", personalRegisterDTO.getPhoneNo());
        requestJson.put("caType", "B2");
        requestJson.put("token", token);

        String response = new String();
        String yunId;
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/user/person", requestJson, 1);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json = new JSONObject();
            json.putAll(map);
            yunId = json.getJSONObject("a").getJSONObject("data").getString("signerId");
        }catch (Exception e){
            return "注册个人云合同失败";
        }

        // 这里注册个人云合同id的同时，进行个人印章注册,要先根据返回
        if(yunId != null && !yunId.equals("")){
            JSONObject sealRequestJson = new JSONObject();
            sealRequestJson.put("signerId", yunId);
            sealRequestJson.put("zoomCode", "0");
            sealRequestJson.put("token", token);
            try{
                String responseSeal = HttpClient4.doPost("https://api.yunhetong.com/api/user/personMoulage", sealRequestJson, 1);
            } catch (Exception e){
                return "注册个人云合同成功，但注册个人印章失败";
            }

        }

        // TODO 这里需要根据公共信息获取userId，再去将获得的云合同注册id插入到对应表去，这里先写死数据
        int userType = 1;
        // 向user表里插入云合同注册id
        contractMapper.insertUserContractYunId("1", yunId);

        return "1";
    }

    /**
     * 为企业用户创建云合同
     * @param enterpriseRegisterDTO
     * @param token
     * @return message
     */
    @Override
    public String registerEnterpriseContractAccount(EnterpriseRegisterDTO enterpriseRegisterDTO, String token){
        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", enterpriseRegisterDTO.getUserName());
        requestJson.put("certifyType", enterpriseRegisterDTO.getCertifyType());
        requestJson.put("certifyNum", enterpriseRegisterDTO.getCertifyNum());
        requestJson.put("phoneNo", enterpriseRegisterDTO.getPhoneNo());
        requestJson.put("caType", "B2");
        requestJson.put("token", token);

        String response = new String();
        String yunId;
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/user/company", requestJson, 1);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json = new JSONObject();
            json.putAll(map);
            yunId = json.getJSONObject("a").getJSONObject("data").getString("signerId");
            System.out.println("yunId11111111111111111");
            System.out.println(yunId);
        }catch (Exception e){
            return "注册企业云合同失败";
        }

        // 这里注册企业云合同id的同时，进行企业印章注册,要先根据返回确定是否注册用户成功
        if(yunId != null && !yunId.equals("")){
            JSONObject sealRequestJson = new JSONObject();
            sealRequestJson.put("signerId", yunId);
            sealRequestJson.put("styleType", "1");
            sealRequestJson.put("token", token);
            try{
                String responseSeal = HttpClient4.doPost("https://api.yunhetong.com/api/user/companyMoulage", sealRequestJson, 1);
                System.out.println("responseSeal");
                System.out.println(responseSeal);
            } catch (Exception e){
                return "注册企业云合同成功，但注册企业印章失败";
            }
        }

        // TODO 这里需要根据公共信息获取userId，再去将获得的云合同注册id插入到对应表去，这里先写死数据
        int userType = 1;
        // 向enterprise表里插入云合同注册id
        contractMapper.insertEnterpriseContractYunId(contractMapper.getEnterpriseId("1"), yunId);

        return "1";
    }

    // TODO 现在公司那边没有定制合同，所以这边合同是一个测试用的，需一份专门的合同
    /**
     * 合成合同模板
     * @param token
     * @return message
     */
    @Override
    public String createContract(String token){
        // 设置请求json数据
        JSONObject json = new JSONObject();
        json.put("contractTitle", "测试合同");
        json.put("templateId", "TEM1009230");

        JSONObject dataJson = new JSONObject();
        dataJson.put("${name}", "谭深化");
        dataJson.put("${mobile}", "15826045613");
        dataJson.put("${id_no}", "50022519971001773X");
        dataJson.put("${corporate_name}", "艾欧里亚");
        dataJson.put("${business_licence}", "1997");
        dataJson.put("${contract_date}", (new Date()).toString());

        json.put("contractData", dataJson);

        json.put("token", token);

        String response = new String(); // 设置返回字符串
        String contractId; // 合同id字符串

        // 进行网络请求，并提取返回数据中的合同id，进行记录，便于维护
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/templateContract", json, 1);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json1 = new JSONObject();
            json1.putAll(map);
            contractId = json1.getJSONObject("a").getJSONObject("data").getString("contractId");
        } catch(Exception e){
            return "创建合同模板出错";
        }

        // TODO 此接口需待完善,userId是写死的
        // 将提取出的合同id，插入到数据库中
        if(contractId != null && !contractId.equals("")){
            contractMapper.insertContractId("1", contractId);
        }


        return "1";
    }

    /**
     * 为合同模板添加签署者
     * @param token
     * @return message
     */
    // TODO 此接口中的签署者来源，待完善
    @Override
    public String addContractOwner(String token){
        JSONObject json = new JSONObject();
        //合同参数类型
        json.put("idType", "0");
        //合同参数
        json.put("idContent", "1904271759031848");

        // 企业签署者
        JSONObject ownerCompany = new JSONObject();
        ownerCompany.put("signerId", "5160494"); // 云合同id
        ownerCompany.put("signPositionType", "1"); // 签名定位类型
        ownerCompany.put("positionContent", "04549"); // 签名定位参数
        ownerCompany.put("signValidateType", "0"); // 是否短信校验，否
        ownerCompany.put("signMode", "0"); // 是否定制印章，否
        ownerCompany.put("signForm", "0"); // js，h5签署。 js

        //个人签署者
        JSONObject ownerPerson = new JSONObject();
        ownerPerson.put("signerId", "5159797");
        ownerPerson.put("signPositionType", "1");
        ownerPerson.put("positionContent", "08600");
        ownerPerson.put("signValidateType", "0");
        ownerPerson.put("signMode", "0");
        ownerPerson.put("signForm", "0");


        JSONArray jsonArray = new JSONArray();
        jsonArray.add(ownerCompany);
        jsonArray.add(ownerPerson);

        json.put("signers", jsonArray);

        json.put("token", token);

        String response = new String();
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/signer", json, 1);
        } catch (Exception e){
            return "为指定合同添加签署者失败";
        }

         return "1";
    }

    /**
     * 为某个签署者签署合同（合同所有签署者完成签署，才算签署完成）
     * @param token
     * @return message
     */
    // TODO 此接口中，合同id，签署者id来源需完善
    @Override
    public String signerContract(String token){
        JSONObject json = new JSONObject();
        json.put("idType", "0"); // 合同id类型
        json.put("idContent", "1904271759031848"); // 合同id参数
        json.put("signerId","5160494"); // 签署者id
        json.put("token", token);

        String response = new String();
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/sign", json, 1);
        } catch(Exception e){
            return "签署合同失败";
        }

        return "1";
    }

    /**
     * 为已签署合同进行存证
     * @param token
     * @return message
     */
    // TODO 合同id来源，存证id需插入数据库，便于维护
    @Override
    public String czContract(String token){
        JSONObject json = new JSONObject();
        json.put("idType", "0"); // 合同id类型
        json.put("idContent", "1904271759031848"); // 合同id参数
        json.put("token", token);

        String response = new String();
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/cz", json, 1);
        } catch(Exception e){
            return "合同存证失败";
        }

        return "1";
    }
}
