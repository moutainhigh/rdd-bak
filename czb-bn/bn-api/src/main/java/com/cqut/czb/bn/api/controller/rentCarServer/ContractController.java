package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.dto.rentCar.AsynchronousInfo;
import com.cqut.czb.bn.entity.dto.rentCar.PersonSignedInputInfo;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.CompanySignedPersonal;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.CompanySignedTime;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdListDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.rentCarImpl.ContractServiceImpl;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import com.cqut.czb.bn.util.method.HttpClient4;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 作者：谭深哈
 * 模块：租车服务-合同
 * 业务：个人租车合同、平台企业套餐合同签署
 */

@RestController
@RequestMapping("/signContract")
public class ContractController {
    @Autowired
    private  ContractService contractService;

    @Autowired
    RedisUtils redisUtils;


    /**
     * 获得平台在云合同长效令牌token，客户端需要定时更新，后续操作都需要传入token
     * @return token字符串
     */
    @RequestMapping(value = "/getContractToken", method = RequestMethod.GET)
    public String getContractToken(){
        return contractService.getContractToken();
    }

//    /**
//     * 给用户（个人）注册云合同唯一id，此id需存入数据库维护
//     * @param principal,token
//     * @return 云合同返回的用户（个人或企业）id
//     */
//    @RequestMapping(value = "/registerPersonalContractAccount", method = RequestMethod.POST)
//    public JSONResult registerPersonalContractAccount(@RequestBody Principal principal, String yunToken){
//        User user = (User)redisUtils.get(principal.getName());
//        JSONResult json =  new JSONResult();
//        int code = contractService.registerPersonalContractAccount(user.getUserId(), yunToken);
//        switch(code){
//            case 100:
//                json.setMessage("注册个人云合同失败");
//                json.setCode(code);
//                break;
//            case 101:
//                json.setMessage("注册个人云合同成功，但注册个人印章失败");
//                json.setCode(code);
//                break;
//            case 104:
//                json.setMessage("注册个人云合同时，查找实名信息失败");
//                json.setCode(code);
//                break;
//            case 106:
//                json.setMessage("注册个人云合同时，插入yunId出错");
//                json.setCode(code);
//                break;
//            case 1:
//                json.setCode(200);
//                json.setMessage("个人注册云合同成功");
//                break;
//
//        }
//        return json;
//    }

    /**
     * 企业注册云合同
     * 给用户（个人、或企业）注册云合同唯一id，此id需存入数据库维护
     * @param principal,token
     * @return 云合同返回的用户（个人或企业）id
     */
    @RequestMapping(value = "/registerEnterpriseContractAccount", method = RequestMethod.POST)
    public JSONResult registerEnterpriseContractAccount(@RequestBody Principal principal, String token){
        User user = (User)redisUtils.get(principal.getName());
        JSONResult json =  new JSONResult();
        int code = contractService.registerEnterpriseContractAccount(user.getUserId(), token);
        switch(code){
            case 102:
                json.setMessage("注册企业云合同失败");
                json.setCode(code);
                break;
            case 103:
                json.setMessage("注册企业云合同成功，但注册企业印章失败");
                json.setCode(code);
                break;
            case 105:
                json.setMessage("注册企业云合同时，获取实名信息失败");
                json.setCode(code);
                break;
            case 107:
                json.setMessage("注册企业云合同时，插入yunId失败");
                json.setCode(code);
                break;
            case 1:
                json.setCode(200);
                json.setMessage("企业注册云合同成功");
                break;

        }
        return json;
    }

//    /**
//     * 根据合同模板生成合同（此时还未签署合同）
//     */
//    @RequestMapping(value = "/createContract", method = RequestMethod.POST)
//    public JSONResult createContract(@RequestBody Principal principal,String contractWriteId, String token){
//        User user = (User)redisUtils.get(principal.getName());
//        JSONResult json = new JSONResult();
//        String code = contractService.createContract(user.getUserId(), contractWriteId, token);
//        switch(code){
//            case "108":
//                json.setMessage("生成合同模板时，获取用户信息失败");
//                json.setCode(Integer.parseInt(code));
//                break;
//            case "109":
//                json.setMessage("创建合同模板出错");
//                json.setCode(Integer.parseInt(code));
//                break;
//            case "110":
//                json.setMessage("插入第三方合同id失败");
//                json.setCode(Integer.parseInt(code));
//                break;
//            default:
//                JSONObject contractIdJson = new JSONObject();
//                contractIdJson.put("thirdContractId", code);
//                json.setData(contractIdJson);
//                json.setMessage("成功，返回第三方合同id，用于接下来的合同操作");
//                break;
//        }
//        return json;
//    }

//    /**
//     * 添加合同签署者
//     */
//    @RequestMapping(value = "/addContractOwner", method = RequestMethod.POST)
//    public JSONResult downloadContract(@RequestBody Principal principal,String contractId, String token){
//        User user = (User)redisUtils.get(principal.getName());
//        JSONResult json = new JSONResult();
//        int code = contractService.addContractOwner(user.getUserId(),contractId, token);
//        switch(code){
//            case 111:
//                json.setMessage("查找用户yunId失败");
//                json.setCode(code);
//                break;
//            case 112:
//                json.setMessage("为指定合同添加签署者失败");
//                json.setCode(code);
//                break;
//            default:
//                json.setMessage("添加签署者成功");
//                json.setCode(200);
//                break;
//        }
//        return json;
//    }

    /**
     * 合同签署
     * 在生成模板、添加签署者之后，可以进行合同签署了
     */
    @RequestMapping(value = "/signerContract", method = RequestMethod.POST)
    public JSONResult signerContract(@RequestBody Principal principal,String contractId, String token){
        User user = (User)redisUtils.get(principal.getName());
        JSONResult json = new JSONResult();
        int code = contractService.signerContract(user.getUserId(), contractId, token);
        switch(code){
            case 113:
                json.setMessage("查找用户yunId失败");
                json.setCode(code);
                break;
            case 114:
                json.setMessage("签署合同失败");
                json.setCode(code);
                break;
            case 1:
                json.setMessage("此签署者成功");
                json.setCode(200);
                break;
        }
        return json;
    }

    /**
     * 合同存证
     * 签署合同后要进行存证，出事儿可进行公证
     */
    @RequestMapping(value = "/czContract", method= RequestMethod.POST)
    public JSONResult czContract(@RequestBody Principal principal, String contractId, String token){
        User user = (User)redisUtils.get(principal.getName());
        JSONResult json = new JSONResult();
        int code = contractService.czContract(contractId, token);
        switch(code){
            case 115:
                json.setMessage("合同存证失败");
                json.setCode(code);
                break;
            case 1:
                json.setMessage("存证成功");
                json.setCode(200);
                break;
        }
        return json;
    }

    /**
     * 测试接口
     */
    @RequestMapping(value = "/get", method= RequestMethod.POST)
    public JSONResult get(){

        return new JSONResult(contractService.get());
    }

    /**
     * 个人签约
     */
    @RequestMapping(value = "/personSigned", method = RequestMethod.POST)
    public JSONResult personSigned(Principal principal, @RequestBody  PersonSignedInputInfo inputInfo){
        User user = (User)redisUtils.get(principal.getName());
        JSONObject json = contractService.personSigned(user.getUserId(), inputInfo);
        String code = json.getString("code");
        switch(code){
            case ContractServiceImpl.STATE_CONTRACT_NULL:
                json.put("code", code);
                json.put("message", "不存在符合的签约码" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_CREATEYUNID_FAILED:
                json.put("code", code);
                json.put("message", "用户没有注册云合同" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_CREATE_CONTRACT_YUN_FAILED:
                json.put("code", code);
                json.put("message", "生成合同模板出错" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_INSERT_YUN_CONTRACTID_FAILED:
                json.put("code", code);
                json.put("message", "插入云合同id到合同记录表中出错" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_ADD_SIGNER_FAILED:
                json.put("code", code);
                json.put("message", "为合同添加签署者失败" + "(" + code + ")");
                break;
            default:
                json.put("code", "200");
                json.put("message", "个人签约初始化成功（签署中状态）");
        }
        return new JSONResult(json);
    }

    /**
     * 企业签订合同正文初始化
     */
    @RequestMapping(value = "/companySignedInitialize", method = RequestMethod.GET)
    public JSONResult companySigned(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        String code = contractService.companySignedInitialize(user.getUserId());
        JSONObject json = new JSONObject();
        switch(code){
            case ContractServiceImpl.STATE_COMPANY_SIGNED_INITIALIZE_FAILED:
                json.put("code", Integer.parseInt(code));
                json.put("message", "插入合同记录出错" + "(" + code + ")");
                break;
            default:
                json.put("code", 200);
                json.put("message", "合同插入成功");
                json.put("contractLogId", code);
        }
        return new JSONResult(json);
    }

    // TODO 谭深化——这里的合同开始结束时间，以后可能会修改
    /**
     * 企业签订正文时间设置
     */
    @RequestMapping(value = "/setCompanySignedTime", method = RequestMethod.POST)
    public JSONResult setCompanySignedTime(@RequestBody CompanySignedTime startTime){
        String code = contractService.setCompanySignedTime(startTime.getStartTime(), startTime.getContractId());
        JSONObject json = new JSONObject();
        switch(code){
            case ContractServiceImpl.STATE_TIME_FORMAT:
                json.put("code", Integer.parseInt(code));
                json.put("message", "时间格式可能出错" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_TIME_SET:
                json.put("code", Integer.parseInt(code));
                json.put("message", "设置时间出错" + "(" + code + ")");
                break;
            default:
                json.put("code", 200);
                json.put("message", "设置时间成功");
        }

        return new JSONResult(json);
    }

    /**
     * 企业签订正文个人信息添加
     */
    @RequestMapping(value = "/addCompanySignedPersonal", method = RequestMethod.POST)
    public JSONResult addCompanySignedPersonal(@RequestBody CompanySignedPersonal personal){
        String code = contractService.addCompanySignedPersonal(personal);
        JSONObject json = new JSONObject();
        switch(code){
            case ContractServiceImpl.STATE_ADD_PERSONAL:
                json.put("code", Integer.parseInt(code));
                json.put("message", "企业签订合同添加个人服务失败" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_FIND_TAO_CAN_RENT:
                json.put("code", Integer.parseInt(code));
                json.put("message", "查找套餐金额失败" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_FIND_TIMES:
                json.put("code", Integer.parseInt(code));
                json.put("message", "查找父级合同记录的开始和结束时间失败" + "(" + code + ")");
                break;
            case ContractServiceImpl.STATE_INSERT_PERSONAL_CONTRACT_FAILED:
                json.put("code", Integer.parseInt(code));
                json.put("message", "插入父级相应的子合同记录失败" + "(" + code + ")");
                break;
            default:
                json.put("code", 200);
                json.put("message", "企业签订正文添加个人信息成功");
        }

        return new JSONResult(json);
    }

    /**
     * 获得套餐
     */
    @RequestMapping(value = "/getTaoCanId", method = RequestMethod.GET)
    public JSONResult getTaoCanId(){
        return new JSONResult(contractService.getTaoCanId());
    }

    /**
     * 未提交企业合同个人信息列表获取
     */
    @RequestMapping(value = "/getWithoutCommitPersonInfo", method = RequestMethod.POST)
    public JSONResult getWithoutCommitPersonInfo(@RequestBody ContractIdInfo idInfo){
        return new JSONResult(contractService.getWithoutCommitPersonInfo(idInfo.getContractId()));
    }

    /**
     * 删除企业合同个人信息列表中的某人
     */
    @RequestMapping(value = "/removePersonInfo", method = RequestMethod.POST)
    public JSONResult removePersonInfo(@RequestBody ContractIdListDTO contractIdList){
        boolean success= contractService.removePersonInfo(contractIdList);

        return new JSONResult(success);
    }

    /**
     * 云合同异步消息回调地址
     */
    @RequestMapping(value = "/getAsynchronousInfo", method = RequestMethod.POST)
    public void getAsynchronousInfo(@RequestBody AsynchronousInfo info){
        System.out.println(info.toString());
//         noticeType为2时，此合同为签署完成状态
        if(info.getNoticeType() == 2)
            contractService.asynchronousInfo(info.getMap());

    }

    /**
     * 查看合同状态
     */
    @RequestMapping(value = "/getContractStatus", method = RequestMethod.POST)
    public JSONResult getContractStatus(@RequestBody ContractIdInfo info){
        JSONObject json = new JSONObject();
        json.put("contractStatus", contractService.getContractStatus(info.getContractId()));

        return new JSONResult(json);
    }

    /**
     * 企业签订合同
     */
    @RequestMapping(value = "/companySigned", method = RequestMethod.POST)
    public JSONResult personSigned(Principal principal, @RequestBody ContractIdInfo info){
        User user = (User)redisUtils.get(principal.getName());
        JSONObject json = contractService.companySigned(user.getUserId(), info.getContractId());
        String code = json.getString("code");
        switch(code){
            case ContractServiceImpl.STATE_CONTRACT_NULL:
                json.put("code", code);
                json.put("message", "不存在符合的签约码");
                break;
            case ContractServiceImpl.STATE_CREATEYUNID_FAILED:
                json.put("code", code);
                json.put("message", "企业用户没有注册云合同");
                break;
            case ContractServiceImpl.STATE_CREATE_CONTRACT_YUN_FAILED:
                json.put("code", code);
                json.put("message", "生成合同模板出错");
                break;
            case ContractServiceImpl.STATE_INSERT_YUN_CONTRACTID_FAILED:
                json.put("code", code);
                json.put("message", "插入云合同id到合同记录表中出错");
                break;
            case ContractServiceImpl.STATE_ADD_SIGNER_FAILED:
                json.put("code", code);
                json.put("message", "为合同添加签署者失败");
                break;
            default:
                json.put("code", "200");
                json.put("message", "企业合同初始化成功（签署中状态）");
        }
        return new JSONResult(json);
    }
}
