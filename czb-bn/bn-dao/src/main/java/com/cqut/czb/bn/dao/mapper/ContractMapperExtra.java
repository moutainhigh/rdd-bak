package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.dto.rentCar.ContractLog;
import com.cqut.czb.bn.entity.dto.rentCar.PersonSignedInputInfo;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.CarsPersonsDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.TaoCanDTO;
import com.cqut.czb.bn.entity.dto.rentCar.personContractSigned.CarNumAndRent;
import com.cqut.czb.bn.entity.dto.rentCar.personContractSigned.PersonBankInfo;
import com.cqut.czb.bn.entity.dto.rentCar.personContractSigned.SignerIdAndContractId;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractMapperExtra {
//    插入yunId
    int insertUserContractYunId(@Param("userId") String userId, @Param("userYunId") String userYunId);

    // 根据userId获得个人信息
    PersonalRegisterDTO getPersonInfo(@Param("userId") String userId);

//    根据userId获得企业信息
    EnterpriseRegisterDTO getEnterpriseInfo(@Param("userId") String userId);

//    将提取出的合同id，插入到前端传来的合同记录id中
    int insertContractId(@Param("contractWriteId") String contractWriteId, @Param("contractId") String contractId);

    // 插入银行卡三要素
    int insertBankInfo( PersonBankInfo inputInfo);

    // 获得用户云合同id
    String getYunId(@Param("userId") String userId);

    // 查找是否存在相应认证码和身份证的合同
    String getIdentifyCodeAndPersonId(PersonSignedInputInfo inputInfo);

    // 合同签署完成，根据回调消息改变合同状态
    int updateContractStatus(@Param("contractId") String contractId,@Param("statusCode") String statusCode);

    // 根据回调消息改变服务表签约状态
    int updateCarsPersonsStatus(@Param("contractId") String contractId);

    // 查看合同状态
    int getContractStatus(@Param("contractId") String contractId);

    // 设置企业合同开始和结束时间
    int setCompanySignedTime(@Param("startTime") String startTime,
                             @Param("endTime") String endTime,
                             @Param("contractId") String contractId,
                             @Param("bankDeposit")String bankDeposit,
                             @Param("bankAccount")String bankAccount,
                             @Param("bankName")String bankName);

    // 查找套餐id对应的租金
    double findRent(@Param("taoCanId") String taoCanId);

    // 获得合同的开始时间和结束时间
    ContractLog getContractStartTimeAndEndTime(@Param("contractId") String contractId);

    // 根据企业合同记录id，查找其所有的企业合同子合同的服务人员车辆表记录
    List<CarsPersonsDTO> getWithoutCommitPersonInfo(@Param("contractId") String contractId);

    // 根据套餐id获得套餐
    Double getTaoCan(@Param("taoCanId")String taoCanId);

    // 获取套餐信息
    List<TaoCanDTO> getTaoCanId();

    // 个人输入认证码、身份证后，查找车牌号和租金
    CarNumAndRent getCarNumAndPersonId(PersonSignedInputInfo inputInfo);

    // 个人签署时，查看其父级合同是否已经签署
    Integer getFatherContractStatus(@Param("contractId")String contractId);

    // 删除企业合同个人信息列表中的某人
    int removePersonInfo(@Param("array") String[] contractIds);

    // 删除企业合同个人信息列表中的某人
    int removeCarsPersonInfo(@Param("array") String[] contractIds);

    // 将个人userId，写入合同记录表
    int updatePersonalContractUserId(@Param("userId") String userId, @Param("contractId") String contractId);

    // 企业合同为添加个人信息，删除初始化的合同记录
    int deleteContract(@Param("contractId") String contractId);

    // 回调合同签署完成后，进行存证，保存存证id
    int insertCZId(@Param("czId") String czId, @Param("contractId") String contractId);

    String getUserId(@Param("thirdContractId") String thirdContractId);

    // 根据合同id，查找signerId和thirdContractId
    SignerIdAndContractId getSignerIdAndYunContractId(@Param("contractId") String contractId);

    // 根据子级合同id，查找父级合同id
    String getContractFatherId(@Param("contractId") String contractId);
}
