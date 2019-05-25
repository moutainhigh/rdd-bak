package com.cqut.czb.bn.service.rentCarService;

import com.cqut.czb.bn.entity.dto.rentCar.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import net.sf.json.JSONObject;

import java.util.List;

public interface RentCarService {
    // 获取个人收益记录
    PersonIncome getPersonIncome(String userId);

    // 获取合同概要信息
    OneContractInfoDTO getOneContractInfo(String userId, String contractId);

    // 个人租车服务，合同列表获取
    List<PersonalContractDTO> getPersonalContractList(String userId);

    // 企业合同服务，合同列表获取
    List<CompanyContractInfoDTO> getCompanyContractList(String userId);

    // 企业合同概要信息列表获取
    List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(String fatherId);

    // 获取个人收益
    JSONObject getCompanyContractListAllgetPersonalIncome(String userId);

    // 合同获取电话号码
    JSONResult getContact(String userId);
}
