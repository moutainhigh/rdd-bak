package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.rentCar.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface RentCarMapperExtra {
    PersonIncome getPersonIncome(@Param("userId") String userId);

    OneContractInfoDTO getOneContractInfo(@Param("userId")String userId, @Param("contractId") String contractId);

    List<PersonalContractDTO> getPersonalContractList(@Param("userId") String userId);

    List<CompanyContractInfoDTO> getCompanyContractList(@Param("userId") String userId);

    List<CompanyContractInfoDTO> getCompanyContractListAll(@Param("userId") String userId);

    ContractAndSignedNumDTO getSignedNumList(@Param("fatherId") String fatherId);

    List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(@Param("fatherId") String fatherId);

    // 插入一条企业合同记录
    int insertContractLog(ContractLog contractLog);

    // 插入合同记录后，插入相应的所有租车者到车辆人员服务表中
    int insertCompanyPerson(PersonCar personCar);

    // 个人签订租赁前确认是否已签过
    int getIsSigned(@Param("personId") String personId, @Param("identifyCode") String identifyCode);

    // 修改车辆人员服务表
    int updateCarPerson(@Param("personId") String personId, @Param("identifyCode") String identifyCode);

    // 插入一条个人合同记录
    int insertContractLogPerson(ContractLog contractLog);

    // 根据父级合同时间修改自己合同时间
    int updateChildContractTimes(ContractLog contractLog);

    // 获取个人收益信息列表
    List<Income> getPersonalIncome(@Param("userId") String userId);

    // 根据套餐id获得某一个套餐
    String getTaoCanById(@Param("planId")String planId);

    // 个人签约时，获得用户身份证
    String getPersonId(@Param("userId") String userId);

    // 如果此用户已经点过个人签约，并生成第三方云合同id，这里直接取出返回就行
    String getYunContractId(@Param("contractId")String contractId);

}
