package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.alibaba.fastjson.JSON;
import com.cqut.czb.bn.dao.mapper.RentCarMapperExtra;
import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.rentCar.ContractLog;
import com.cqut.czb.bn.entity.dto.rentCar.PersonCar;
import com.cqut.czb.bn.entity.dto.rentCar.PersonSignedInputInfo;
import com.cqut.czb.bn.entity.dto.rentCar.SignerMap;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.*;
import com.cqut.czb.bn.entity.dto.rentCar.personContractSigned.CarNumAndRent;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.cqut.czb.bn.util.method.GetIdentifyCode;
import com.cqut.czb.bn.util.method.HttpClient4;
import com.cqut.czb.bn.dao.mapper.ContractMapperExtra;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ContractServiceImpl implements ContractService{
    private  ContractMapperExtra contractMapper;

    private RentCarMapperExtra rentCarMapper;

    private static String token =  null;

    private static Date isFailure = null;

    public static final String  STATE_CONTRACT_NULL = "100"; // 不存在符合的签约码
    public static final String STATE_CREATEYUNID_FAILED = "101"; // 用户没有注册云合同
    public static final String STATE_CREATE_CONTRACT_YUN_FAILED = "102"; // 生成合同模板出错
    public static final String STATE_INSERT_YUN_CONTRACTID_FAILED = "103"; // 插入云合同id到合同记录表中出错
    public static final String STATE_ADD_SIGNER_FAILED = "104"; // 为合同添加签署者失败
    public static final String STATE_COMPANY_SIGNED_INITIALIZE_FAILED = "105"; // 企业签订合同初始化失败
    public static final String STATE_TIME_FORMAT = "106"; // 时间格式可能出错
    public static final String STATE_TIME_SET = "107"; // 时间设置出错
    public static final String STATE_ADD_PERSONAL = "108"; // 企业签订合同添加个人服务失败
    public static final String STATE_FIND_TAO_CAN_RENT = "109"; // 根据taocCnId，查找套餐金额出错
    public static final String STATE_FIND_TIMES = "110"; // 查找父级合同开始和结束时间出错
    public static final String STATE_INSERT_PERSONAL_CONTRACT_FAILED = "111"; // 插入父级相应的子合同记录出错
    public static final short STATE_INSERT_CZID_FAILED = 118; // 合同存证失败

    @Autowired
    public ContractServiceImpl(ContractMapperExtra contractMapper, RentCarMapperExtra rentCarMapper) {
        this.contractMapper = contractMapper;
        this.rentCarMapper = rentCarMapper;
    }

    // type: 0 获得token 1 注册云id 2 注册印章 3 根据模块生成未签署合同

    /**
     * 获取云合同token
     * @return token
     */
    private String getToken(){
        // 如果token与isFailure为空，则为其初始化，
        if(token == null && isFailure == null){
            token = checkToken();
            isFailure = new Date();
            System.out.println(token);
            System.out.println("初始化");
        }

        // 如果token不为空，则看其是否已经使用了超过9分钟，如超过，则更新token和获得token的时间
        Date now = new Date();
        if(now.getTime() - isFailure.getTime() > 540000){
            token = checkToken();
            isFailure = now;
            System.out.println(token);
            System.out.println("更新");
        }

        return  token;
    }

    /**
     * 更新token的方法
     * @return String
     */
    private static String checkToken(){
        JSONObject request = new JSONObject();
        request.put("appId", "2019042516271800110");
        request.put("appKey", "uDCFes85C3OwDQ");
        String response = new String();
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/auth/login", request, 0);
        } catch (Exception e){
            e.printStackTrace();
        }
        int indexMax = response.length();
        response = response.substring(7, indexMax); // 删除token前的一些无关字符

        return response;
    }

    @Override
    public String getContractToken() {
        return getToken();
    }

    // TODO 谭深化——此接口需要删除（测试接口）
    /**
     *
     */
    @Override
    public String get( ){
        Integer fatherContractStatus = contractMapper.getFatherContractStatus("1");
        System.out.println(fatherContractStatus);
        if (fatherContractStatus == 0 || fatherContractStatus == 2){
            System.out.println("失败");
        }

        return "1";
    }

    /**
     * 个人输入认证码、身份证后，查找车牌号和租金
     * @param userId, inputInfo
     * @return JSONResult
     */
    @Override
    public JSONResult getCarNumAndPersonId(String userId, PersonSignedInputInfo inputInfo) {
        // 数据校验
        if (inputInfo.getIdentifyCode() == null)
            return new JSONResult("认证码不能为空", ResponseCodeConstants.FAILURE);

        // 先取身份证号码
        String personId = rentCarMapper.getPersonId(userId);
        if (personId == null)
            return new JSONResult("找不到用户身份证号码", ResponseCodeConstants.FAILURE);

        // 设置身份证号码，再去寻找车牌号和租金
        inputInfo.setPersonId(personId);
        CarNumAndRent info = contractMapper.getCarNumAndPersonId(inputInfo);
        if (info == null)
            return new JSONResult("找不到车牌号和认证码", ResponseCodeConstants.FAILURE);

        // 将身份证号码设置到返回信息里
        info.setPersonId(personId);

        return new JSONResult("获取数据成功",ResponseCodeConstants.SUCCESS, info);
    }

    /**
     * 为个人创建云合同
     * @param userId, token
     * @return message
     */
    private int registerPersonalContractAccount(String userId, String token){
        // 根据用户id，数据库查找用户已实名认证的信息，为其注册云合同
        PersonalRegisterDTO personalRegisterDTO = new PersonalRegisterDTO();
        try{
            personalRegisterDTO = contractMapper.getPersonInfo(userId);
            if (personalRegisterDTO.getCertifyNum() == null || personalRegisterDTO.getUserName() == null || personalRegisterDTO.getPhoneNo() == null)
                return 104;
        } catch(Exception e){
            e.printStackTrace();
            return 104;
        }

        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", personalRegisterDTO.getUserName()); //姓名
        requestJson.put("identityRegion", "0"); //身份地区大陆
        requestJson.put("certifyType", "a"); // 证件类型，身份证
        requestJson.put("certifyNum", personalRegisterDTO.getCertifyNum()); // 身份证号
        requestJson.put("phoneRegion", "0"); // 手机地区大陆
        requestJson.put("phoneNo", personalRegisterDTO.getPhoneNo()); // 手机号码
        requestJson.put("caType", "B2"); // 固定字段，证书类型
        requestJson.put("token", token); // 前端token

        // 这里注册个人云合同id
        String response = new String();
        String yunId;
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/user/person", requestJson, 1);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json = new JSONObject();
            json.putAll(map);
            yunId = json.getJSONObject("a").getJSONObject("data").getString("signerId");
            if (yunId == null)
                return 100;
        }catch (Exception e){
            return 100;
        }

//        // 这里注册个人云合同id的同时，进行个人印章注册,要先根据返回
//        if(yunId != null && !yunId.equals("")){
//            JSONObject sealRequestJson = new JSONObject();
//            sealRequestJson.put("signerId", yunId);
//            sealRequestJson.put("zoomCode", "0");
//            sealRequestJson.put("token", token);
//            try{
//                String responseSeal = HttpClient4.doPost("https://api.yunhetong.com/api/user/personMoulage", sealRequestJson, 1);
//            } catch (Exception e){
//                System.out.println("注册个人云合同印章失败");
//                return 101;
//            }
//
//        }

        // 向user表里插入云合同注册id
        try{
            int insertSuccess = contractMapper.insertUserContractYunId(userId, yunId);
            if (insertSuccess != 1)
                return 106;
        } catch(Exception e){
            e.printStackTrace();
            return 106;
        }

        return 1;
    }

    /**
     * 为企业用户创建云合同
     * @param userId token
     * @return int
     */
    public int registerEnterpriseContractAccount(String userId, String token){
        // 根据用户id，数据库查找用户已实名认证的信息，为其注册云合同
        EnterpriseRegisterDTO enterpriseRegisterDTO = new EnterpriseRegisterDTO();
        try{
            enterpriseRegisterDTO = contractMapper.getEnterpriseInfo(userId);
            if (enterpriseRegisterDTO.getCertifyNum() == null || enterpriseRegisterDTO.getUserName() == null || enterpriseRegisterDTO.getPhoneNo() == null)
                return 105;
        } catch(Exception e){
            e.printStackTrace();
            return 105;
        }

        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", enterpriseRegisterDTO.getUserName()); // 企业名称
        requestJson.put("certifyType", "1"); // 企业证件类型
        requestJson.put("certifyNum", enterpriseRegisterDTO.getCertifyNum()); // 企业证件号
        requestJson.put("phoneNo", enterpriseRegisterDTO.getPhoneNo()); // 企业用户手机号
        requestJson.put("caType", "B2");
        requestJson.put("token", token);

        String response = new String();
        String yunId;
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/user/company", requestJson, 1);
            System.out.println(response);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json = new JSONObject();
            json.putAll(map);
            yunId = json.getJSONObject("a").getJSONObject("data").getString("signerId");
        }catch (Exception e){
            e.printStackTrace();
            return 102;
        }

        // 这里注册企业云合同id的同时，进行企业印章注册,要先根据返回确定是否注册用户成功
        if(yunId != null && !yunId.equals("")){
            JSONObject sealRequestJson = new JSONObject();
            sealRequestJson.put("signerId", yunId);
            sealRequestJson.put("styleType", "1");
            sealRequestJson.put("token", token);
            try{
                String responseSeal = HttpClient4.doPost("https://api.yunhetong.com/api/user/companyMoulage", sealRequestJson, 1);
            } catch (Exception e){
                e.printStackTrace();
                return 103;
            }
        }

        // 向user表里插入云合同注册id
        try{
            int insertSuccess = contractMapper.insertUserContractYunId(userId, yunId);
            if (insertSuccess != 1)
                return 107;
        } catch(Exception e){
            e.printStackTrace();
            return 107;
        }

        return 1;
    }

    // TODO 检查
    // TODO 谭深化——现在公司那边没有定制合同，所以这边合同是一个测试用的，需一份专门的合同
    /**
     * 个人合成合同模板
     * @param token userId contractWriteId
     * @return message
     */
    private String createContract(String userId, String contractWriteId, String token){
        // 设置请求json数据
        JSONObject json = new JSONObject();
        json.put("contractTitle", "测试合同");
        json.put("templateId", "TEM1009230");

        // TODO 谭深化—— 因为还未做实名功能，这里以后需要根据是个人用户，还是企业用户，去获取相应的信息，还有选择相应的合同模板去生成
        // 根据用户id，数据库查找用户已实名认证的个人信息
        PersonalRegisterDTO personalRegisterDTO = new PersonalRegisterDTO();
        try{
            personalRegisterDTO = contractMapper.getPersonInfo(userId);
        } catch(Exception e){
            e.printStackTrace();
            return "108";
        }

//        // 根据用户id，数据库查找用户已实名认证的企业信息
//        EnterpriseRegisterDTO enterpriseRegisterDTO = new EnterpriseRegisterDTO();
//        try{
//            enterpriseRegisterDTO = contractMapper.getEnterpriseInfo(userId);
//        } catch(Exception e){
//            e.printStackTrace();
//            return "";
//        }

        JSONObject dataJson = new JSONObject();
        dataJson.put("${name}", personalRegisterDTO.getUserName());
        dataJson.put("${mobile}", personalRegisterDTO.getPhoneNo());
        dataJson.put("${id_no}", personalRegisterDTO.getCertifyNum());
        dataJson.put("${corporate_name}", "艾欧里亚");
        dataJson.put("${business_licence}", "渝A888888");
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
            return "109";
        }
//
//        // 将提取出的合同id，插入到前端传来的合同记录id中
//        if(contractId != null && !contractId.equals("")){
//            try{
//                contractMapper.insertContractId(contractWriteId, contractId);
//            } catch(Exception e){
//                return 110;
//            }
//        }

        return contractId;
    }

    /**
     * 企业合成合同模板
     * @param token userId
     * @return message
     */
    private String createContractCompany(String userId, String token){
        // 设置请求json数据
        JSONObject json = new JSONObject();
        json.put("contractTitle", "测试合同");
        json.put("templateId", "TEM1009230");

        // TODO 谭深化—— 因为还未做实名功能，这里以后需要根据是个人用户，还是企业用户，去获取相应的信息，还有选择相应的合同模板去生成
        // 根据用户id，数据库查找用户已实名认证的企业信息
        EnterpriseRegisterDTO enterpriseRegisterDTO = new EnterpriseRegisterDTO();
        try{
            enterpriseRegisterDTO = contractMapper.getEnterpriseInfo(userId);
        } catch(Exception e){
            e.printStackTrace();
            return "";
        }

        JSONObject dataJson = new JSONObject();
        dataJson.put("${name}", enterpriseRegisterDTO.getUserName());
        dataJson.put("${mobile}", enterpriseRegisterDTO.getPhoneNo());
        dataJson.put("${id_no}", enterpriseRegisterDTO.getCertifyNum());
        dataJson.put("${corporate_name}", "艾欧里亚");
        dataJson.put("${business_licence}", "渝A888888");
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
            return "109";
        }

//        // 将提取出的合同id，插入到前端传来的合同记录id中
//        if(contractId != null && !contractId.equals("")){
//            try{
//                contractMapper.insertContractId(contractWriteId, contractId);
//            } catch(Exception e){
//                return 110;
//            }
//        }

        return contractId;
    }

    /**
     * 为合同模板添加签署者（含平台添加）
     * @param token
     * @return message
     */
    public int addContractOwner(String userId,String contractId, String token, int type){
        JSONObject json = new JSONObject();
        //合同参数类型
        json.put("idType", "0");
        //合同参数
        json.put("idContent", contractId);

        // TODO 添加一个车租宝平台的签署者
        // 企业签署者
        JSONObject ownerCompany = new JSONObject();
        ownerCompany.put("signerId", "5293709"); // 云合同id
        ownerCompany.put("signPositionType", "1"); // 签名定位类型
        ownerCompany.put("positionContent", "04549"); // 签名定位参数
        ownerCompany.put("signValidateType", "0"); // 是否短信校验，否
        ownerCompany.put("signMode", "0"); // 是否定制印章，否
        ownerCompany.put("signForm", "0"); // js，h5签署。 js

        JSONObject ownerPerson = new JSONObject();
        //个人签署者
        String signerId = new String();
        // 获得个人签署者id
        try{
            signerId = contractMapper.getYunId(userId);
        } catch(Exception e){
            e.printStackTrace();
            return 111;
        }

        ownerPerson.put("signerId", signerId);
        ownerPerson.put("signPositionType", "1");
        ownerPerson.put("signValidateType", "0");
        ownerPerson.put("signMode", "0");
        ownerPerson.put("signForm", "0");

        if(type == 1){
            ownerPerson.put("positionContent", "08600");
        } else if(type ==2){
            ownerPerson.put("positionContent", "08600");
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(ownerCompany);
        jsonArray.add(ownerPerson);

        json.put("signers", jsonArray);

        json.put("token", token);

        String response = new String();
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/signer", json, 1);
        } catch (Exception e){
            return 112;
        }

        // TODO 车租宝自动签署
        signerContract("155780028358627",contractId, getToken());


         return 1;
    }

    /**
     * 为用户签署者签署合同（合同所有签署者完成签署，才算签署完成）
     * @param token
     * @return message
     */
    @Override
    public int signerContract(String userId, String contractId, String token){

        String signerId = new String();
        // 获得个人签署者id
        try{
            signerId = contractMapper.getYunId(userId);
        } catch(Exception e){
            e.printStackTrace();
            return 113;
        }

        JSONObject json = new JSONObject();
        json.put("idType", "0"); // 合同id类型
        json.put("idContent", contractId); // 合同id参数
        json.put("signerId",signerId); // 签署者id
        json.put("token", token);

        String response = new String();
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/sign", json, 1);
            System.out.println(response);
        } catch(Exception e){
            return 114;
        }
//        String responseStatus = new String();
//        try{
//            responseStatus = HttpClient4.doGet("https://api.yunhetong.com/api/contract/status/0/1904271759031848", token);
//            Map map = new HashMap();
//            map.put("a", response);
//            JSONObject jsonStatus = new JSONObject();
//            jsonStatus.putAll(map);
//            String status = jsonStatus.getJSONObject("a").getJSONObject("data").getJSONObject("contract").getString("status");
//            if(status.equals("已完成")){
//
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }

        return 1;
    }

    /**
     * 为已签署合同进行存证
     * @param token
     */
    private void czContract(String contractId, String token){
        JSONObject json = new JSONObject();
        json.put("idType", "0"); // 合同id类型
        json.put("idContent", contractId); // 合同id参数
        json.put("token", token);

        String response = new String();
        String czId = null;
        String msg = null;
        try{
            response = HttpClient4.doPost("https://api.yunhetong.com/api/contract/cz", json, 1);

            com.alibaba.fastjson.JSONObject jsonData = JSON.parseObject(response);
            com.alibaba.fastjson.JSONObject dataJo = jsonData.getJSONObject("data");
            if (jsonData.getInteger("code") == 200){
                czId = dataJo.getString("czId");
            }
            msg = dataJo.getString("msg");
        } catch(Exception e){
            System.out.println("存证信息：" + msg);
            System.out.println(response);
        }

        if(!StringUtil.isNullOrEmpty(czId)){
            int czSuccess = contractMapper.insertCZId(czId, contractId);
            if (czSuccess != 1) {
                System.out.println("插入合同存证id失败，云合同返回信息：" + msg + "czId: " + czId + "contractId: " + contractId);
            }
        }else {
            System.out.println("msg: " + msg);
        }

    }


    /**
     * 个人签约（不含签署）
     */
    @Override
    public JSONObject personSigned(String userId, PersonSignedInputInfo inputInfo){
        JSONObject json = new JSONObject(); // 此函数返回数据

        // 这里进行数据校验
        String identifyCode = inputInfo.getIdentifyCode();
        if ( identifyCode.length() != 8 || inputInfo.getIdentifyCode() == null){
            json.put("code", "114");
            return json;
        }

        // 查看是否存在未签约的认证码，如果存在，取出其个人合同id记录
        String personContractId = contractMapper.getIdentifyCodeAndPersonId(inputInfo);

        if(personContractId == null){
            json.put("code", STATE_CONTRACT_NULL); // 不存在未签约的认证码
            return json;
        }

        Integer fatherContractStatus = contractMapper.getFatherContractStatus(personContractId);
        if (fatherContractStatus == 0 || fatherContractStatus == 2){
            json.put("code", "112");
            return json;
        }

        // 查看用户是否注册云合同
        String yunId = contractMapper.getYunId(userId);

        if(yunId == null || yunId.equals("")){
            int success = registerPersonalContractAccount(userId, getToken());
            System.out.println(success);
            if(success != 1){
                json.put("code", STATE_CREATEYUNID_FAILED); // 不存在未签约的认证码
                return json;
            }
        }

        // 如果此用户已经点过个人签约，并生成第三方云合同id，这里直接取出返回就行
        String yunContractId = rentCarMapper.getYunContractId(personContractId);
        if (yunContractId != null && !"".equals(yunContractId)){
            json.put("contractId", yunContractId);
            json.put("signerId", yunId);
            json.put("token", getToken());
            json.put("code", "200");

            return json;
        }

        // 生成合同模板,并返回一个云合同id
        String createYunContract = createContract(userId, personContractId, getToken());
        if(createYunContract.equals("108") || createYunContract.equals("109") || createYunContract.equals("")){
            json.put("code", STATE_CREATE_CONTRACT_YUN_FAILED);
            return json;
        }

        // 把云合同id插入相应的合同记录表中去
        int insertYunContractId = contractMapper.insertContractId(personContractId, createYunContract);
        if(insertYunContractId != 1){
            json.put("code", STATE_INSERT_YUN_CONTRACTID_FAILED); //
            return json;
        }

        // 将个人userId，写入合同记录表
        try{
            int updateSuccess = contractMapper.updatePersonalContractUserId(userId, personContractId);
            if (!(updateSuccess > 0)){
                json.put("code", "113");
                return json;
            }
        } catch (Exception e){
            json.put("code", "113");
            return json;
        }

        // 个人添加签署者
        int addSigner = addContractOwner(userId, createYunContract, getToken(), 1);
        if(addSigner != 1){
            json.put("code", STATE_ADD_SIGNER_FAILED); // 不存在未签约的认证码
            return json;
        }

        json.put("contractId", createYunContract);
        json.put("signerId", yunId);
        json.put("token", getToken());
        json.put("code", "200");

        return json;
    }

    /**
     * 企业签订合同（此步后跳到引入js页面）
     */
    @Override
    public JSONObject companySigned(String userId, String contractId) {
        JSONObject json = new JSONObject();
        // 查看用户是否注册云合同
        String yunId = contractMapper.getYunId(userId);
        if(yunId == null){
            int success = registerEnterpriseContractAccount(userId, getToken());
            if(success != 1){
                json.put("code", STATE_CREATEYUNID_FAILED); // 不存在未签约的认证码
                return json;
            }
        }

        // 如果此合同是未提交合同，并已签已经生成第三方云合同id，这里直接取出返回就行
        String yunContractId = rentCarMapper.getYunContractId(contractId);
        if (yunContractId != null && !"".equals(yunContractId)){
            json.put("contractId", yunContractId);
            json.put("signerId", yunId);
            json.put("token", getToken());
            json.put("code", "200");
            return json;

        }

        // 生成合同模板,并返回一个云合同id
        String createYunContract = createContractCompany(userId, getToken());
        if(createYunContract.equals("108") || createYunContract.equals("109") || createYunContract.equals("")){
            json.put("code", STATE_CREATE_CONTRACT_YUN_FAILED); // 不存在未签约的认证码
            return json;
        }


        // 把云合同id插入相应的合同记录表中去
        int insertYunContractId = contractMapper.insertContractId(contractId, createYunContract);
        if(insertYunContractId != 1){
            json.put("code", STATE_INSERT_YUN_CONTRACTID_FAILED); //
            return json;
        }


        // 企业添加签署者
        int addSigner = addContractOwner(userId, createYunContract, getToken(), 2);
        if(addSigner != 1){
            json.put("code", STATE_ADD_SIGNER_FAILED); // 不存在未签约的认证码
            return json;
        }

        json.put("contractId", createYunContract);
        json.put("signerId", yunId);
        json.put("token", getToken());
        json.put("code", "200");

        return json;
    }

    /**
     * 企业签订合同正文初始化
     */
    @Override
    public String companySignedInitialize(String userId){
        ContractLog contractLog = new ContractLog();

        String contractId = StringUtil.createId();
        contractLog.setRecordId(contractId);
        contractLog.setUserId(userId);

        try{
            rentCarMapper.insertContractLog(contractLog);
        } catch (Exception e){
            e.printStackTrace();
            return STATE_COMPANY_SIGNED_INITIALIZE_FAILED;// 插入合同记录出错
        }

        return contractId;
    }

    /**
     * 企业签订正文时间设置
     */
    @Override
    public String setCompanySignedTime(String startTime, String contractId){
        SimpleDateFormat startTimeDate = new SimpleDateFormat(startTime);

        // 将符合格式的字符串时间转换为date类型，并以此设置结束时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(startTime);
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
            return STATE_TIME_FORMAT;
        }

        // 将开始时间加一年，设为结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, 1);
        Date endTime =calendar.getTime();

        // 设置合同记录时间
        try{
            contractMapper.setCompanySignedTime(startTime, simpleDateFormat.format(endTime), contractId);
        } catch(Exception e){
            return STATE_TIME_SET;
        }

        return "1";
    }

    /**
     * 获取套餐信息
     */
    @Override
    public List<TaoCanDTO> getTaoCanId(){
        List<TaoCanDTO> taos = contractMapper.getTaoCanId();
        for(TaoCanDTO data:taos){
            data.setTaoText(data.getTaoText() + "元/年");
        }

        return taos;
    }

    /**
     * 企业签订正文个人信息添加
     */
    @Override
    public JSONObject addCompanySignedPersonal(CompanySignedPersonal personal){
        JSONObject json = new JSONObject();
        // 同时，插入个人合同记录
        ContractLog contractLog = new ContractLog();

        // 根据taocCnId，查找套餐返租金额
        double rent = 0;
        try{
            rent = contractMapper.findRent(personal.getTaoCanId());
            if(rent == 0){
                json.put("code", STATE_FIND_TAO_CAN_RENT);
            }
        } catch(Exception e){
            json.put("code", STATE_FIND_TAO_CAN_RENT);
        }

        // 根据父级合同id，查找父级合同记录的开始和结束时间
        ContractLog times = null;
        try{
            times = contractMapper.getContractStartTimeAndEndTime(personal.getContractId());
            if(times == null){
                json.put("code", STATE_FIND_TIMES);
            }
        } catch(Exception e){
            json.put("code", STATE_FIND_TIMES);
        }

        String contractId = StringUtil.createId();
        contractLog.setRecordId(contractId);
        contractLog.setStartTime(times.getStartTime());
        contractLog.setEndTime(times.getEndTime());
        contractLog.setRent(rent);
        contractLog.setFatherRecordId(personal.getContractId());

        // 插入父级相应的子合同记录
        try{
            rentCarMapper.insertContractLogPerson(contractLog);
        } catch(Exception e){
            json.put("code", STATE_INSERT_PERSONAL_CONTRACT_FAILED);
        }

        // 插入车辆服务人员记录
        PersonCar personCar = new PersonCar();
        personCar.setPersonCarId(StringUtil.createId());
        personCar.setName(personal.getName());
        personCar.setPersonId(personal.getPersonId());
        personCar.setCarNum(personal.getCarNum());
        personCar.setContractId(contractId);
        personCar.setServiceId(personal.getTaoCanId());
        personCar.setPetrolType(personal.getPetrolType());
        // 生成认证码
        personCar.setIdentityCode(GetIdentifyCode.getIdentityCode(personal.getPersonId(), personal.getCarNum()));
        try {
            rentCarMapper.insertCompanyPerson(personCar);
        } catch(Exception e){
            json.put("code", STATE_ADD_PERSONAL);
        }

        // 返回生成的子级合同id
        json.put("sonContractId", contractId);
        json.put("code", "200");

        return json;
    }

    /**
     * 接收异步消息后，将状态改为已签署
     */
    @Override
    public void asynchronousInfo(SignerMap signerMap){
        if(signerMap != null && signerMap.getData() !=null){
            Long contractId = signerMap.getData().getId();
            // 将签署完成合同的id传入mapper，修改合同记录的状态
            if(contractId != null){
                // 改变合同状态
                contractMapper.updateContractStatus(contractId.toString(), ((Integer)(signerMap.getData().getStatusCode() - 1)).toString() );
                // 改变车辆服务表中的签约状态
                contractMapper.updateCarsPersonsStatus(contractId.toString());
                // 进行合同存证,并插入存证id
                czContract(contractId.toString(), getToken());
                // 查看印章个数，进行印章清除，只保留用户一个印章
                checkMoulages(contractId.toString());
            }
        }

    }

    /**
     * 查看合同状态
     */
    @Override
    public int getContractStatus(String contractId){
        return contractMapper.getContractStatus(contractId);
    }

    /**
     * 未提交企业合同个人信息列表获取
     * @param contractId
     * @return
     */
    @Override
    public JSONObject getWithoutCommitPersonInfo(String contractId) {
        // 根据企业合同记录id，查找其所有的企业合同子合同的服务人员车辆表记录
        List<CarsPersonsDTO> carsPersonsList = contractMapper.getWithoutCommitPersonInfo(contractId);

        List<CarsPersonsResultDTO> resultList = new ArrayList<>();

        // 根据套餐id和油卡类型设置文字
        for(CarsPersonsDTO data: carsPersonsList){
            CarsPersonsResultDTO result = new CarsPersonsResultDTO();
            result.setSonContractId(data.getContractId());
            result.setName(data.getName());
            result.setCarLicense(data.getCarLicense());
            result.setPersonId(data.getPersonId());
            // 根据油卡类型int设置油卡类型
            switch (data.getPetrolType()){
                case 0:
                    result.setPetrolType("大重庆油卡");
                    break;
                case 1:
                    result.setPetrolType("全国中石油");
                    break;
                case 2:
                    result.setPetrolType("全国中石化");
                    break;
            }
            Double rent = contractMapper.getTaoCan(data.getTaoCanId()) * 12d;
            String rentToString = rent.toString();
            rentToString = rentToString.replace(".0", "/年");
            result.setTaoCan(rentToString);
            resultList.add(result);
        }

        // 查找合同时间
//        ContractLog times = contractMapper.getContractStartTimeAndEndTime(contractId);

        JSONObject json = new JSONObject();
//        json.put("startTime", times.getStartTime());
        json.put("personList", resultList);

        return json;
    }

    /**
     * 删除企业合同个人信息
     * @param contractIdList
     * @return
     */
    @Override
    public boolean removePersonInfo(String contractIdList) {
        String[] contractIds = contractIdList.split(",");

        // 多选删除个人合同记录
        int removeCarsPerson = contractMapper.removeCarsPersonInfo(contractIds);

        // 多选删除个人人员车辆服务记录
        int removePersonInfo = contractMapper.removePersonInfo(contractIds);

        return removeCarsPerson >= 0 && removePersonInfo >= 0;
    }

    /**
     * 判断有无印章
     */
    @Override
    public JSONObject checkMoulage(String userId) {
        JSONObject json = new JSONObject();
        // 查看用户是否注册云合同
        String yunId = contractMapper.getYunId(userId);

        String response = new String();
        try{
            response = HttpClient4.doGet("https://api.yunhetong.com/api/user/moulageId/"+yunId+"/1/100", getToken());
            System.out.println(response);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json1 = new JSONObject();
            json1.putAll(map);
            String moulage = json1.getJSONObject("a").getJSONObject("data").getJSONArray("moulages").getJSONObject(0).getString("id");
            if (moulage == null || moulage.equals("")){
                json.put("code", 0);
                json.put("signerId", yunId);
                return json;
            }
        } catch(Exception e){
            json.put("code", 2);
            json.put("signerId", yunId);
            return json;
        }
        json.put("code", 1);
        json.put("signerId", yunId);
        return json;
    }

    /**
     * 查看印章个数，并进行印章清除,只保留用户一个印章
     */
    private void checkMoulages(String thirdContractId){
        // 根据第三方合同id找到userId，再根据userId找到yunId
        String userId = contractMapper.getUserId(thirdContractId);
        if (userId == null || userId.equals(""))
            System.out.println("删除印章出错,找不到userId");
        String yunId = contractMapper.getYunId(userId);
        if (yunId == null || "".equals(userId))
            System.out.println("删除印章出错，找不到yunId");

        String response = new String();
        try{
            response = HttpClient4.doGet("https://api.yunhetong.com/api/user/moulageId/"+yunId+"/1/100", getToken());
            System.out.println(response);
            Map map = new HashMap();
            map.put("a", response);
            JSONObject json1 = new JSONObject();
            json1.putAll(map);
            // 获得第一个印章的id
            String moulage = json1.getJSONObject("a").getJSONObject("data").getJSONArray("moulages").getJSONObject(0).getString("id");
            if (moulage == null || moulage.equals("")){
                System.out.println("删除印章出错,没有印章");
            }
            // 获得用户的印章的个数
            int moulageLenth = json1.getJSONObject("a").getJSONObject("data").getJSONArray("moulages").size();
            if(moulageLenth > 1){
                System.out.println(response);
                System.out.println(moulageLenth);
                System.out.println(moulage);
                String responseDelete = new String();
                try{
                    responseDelete = HttpClient4.doGet("https://api.yunhetong.com/api/user/moulage/" + moulage, getToken());
                } catch (Exception e){
                    System.out.println("删除印章请求失败");
                    System.out.println(responseDelete);
                    e.printStackTrace();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param contractId
     * @return
     */
    @Override
    public JSONResult deleteContract(String contractId) {
        int success = contractMapper.deleteContract(contractId);
        if (success != 1)
            return new JSONResult("删除合同记录失败", 500);

        return new JSONResult("删除合同记录成功", 200);
    }
}
