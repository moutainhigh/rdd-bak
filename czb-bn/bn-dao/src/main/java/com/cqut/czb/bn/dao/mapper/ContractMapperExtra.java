package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.dto.rentCar.ContractLog;
import com.cqut.czb.bn.entity.dto.rentCar.PersonSignedInputInfo;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.CompanySignedPersonal;
import com.cqut.czb.bn.entity.entity.EnterpriseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

public interface ContractMapperExtra {
//    插入yunId
    int insertUserContractYunId(@Param("userId") String userId, @Param("userYunId") String userYunId);

    // 根据userId获得个人信息
    PersonalRegisterDTO getPersonInfo(@Param("userId") String userId);

//    根据userId获得企业信息
    EnterpriseRegisterDTO getEnterpriseInfo(@Param("userId") String userId);

//    将提取出的合同id，插入到前端传来的合同记录id中
    int insertContractId(@Param("contractWriteId") String contractWriteId, @Param("contractId") String contractId);

    // 获得用户云合同id
    String getYunId(@Param("userId") String userId);

    // 查找是否存在相应认证码和身份证的合同
    String getIdentifyCodeAndPersonId(PersonSignedInputInfo inputInfo);

    // 合同签署完成，修改合同记录状态
    int updateContractStatus(@Param("contractId") String contractId,@Param("statusCode") String statusCode);

    // 查看合同状态
    int getContractStatus(@Param("contractId") String contractId);

    // 设置企业合同开始和结束时间
    int setCompanySignedTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("contractId") String contractId);

    // 查找套餐id对应的租金
    double findRent(@Param("taoCanId") String taoCanId);

    //
    ContractLog getContractStartTimeAndEndTime(@Param("contractId") String contractId);
}
