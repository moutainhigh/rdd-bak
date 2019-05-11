package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.dto.rentCar.PersonSignedInputInfo;
import com.cqut.czb.bn.entity.dto.rentCar.SignerMap;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.CompanySignedPersonal;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdListDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.TaoCanDTO;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface ContractService {
    String getContractToken();

//    int registerPersonalContractAccount(String userId, String token);

    // 注册企业合同（暂时弃用）
    int registerEnterpriseContractAccount(String userId, String token);

//    String createContract(String userId,String contractWriteId, String token);

//    int addContractOwner(String userId, String contractId, String token);

    // 签署合同
    int signerContract(String userId, String contractId, String token);

    // 合同存证
    int czContract(String contractId, String token );

    // 测试
    String get( );

    // 个人签约
    JSONObject personSigned(String userId, PersonSignedInputInfo inputInfo);

    // 企业签订合同正文初始化
    String companySignedInitialize(String userId);

    // 企业签订合同正文时间确认
    String setCompanySignedTime(String startTime, String contractId);

    // 企业签订合同正文个人服务添加
    String addCompanySignedPersonal(CompanySignedPersonal personal);

    // 异步回调，存入数据库
    void asynchronousInfo(SignerMap signerMap);

    // 查看合同状态
    int getContractStatus(String contractId);

    // 未提交企业合同个人信息列表获取
    JSONObject getWithoutCommitPersonInfo(String contractId);

    // 获取套餐信息
    List<TaoCanDTO> getTaoCanId();

    // 删除企业合同个人信息列表中的某人
    boolean removePersonInfo(ContractIdListDTO contractiIdList);

    // 企业签订合同
    JSONObject companySigned(String userId, String contractId);

}
