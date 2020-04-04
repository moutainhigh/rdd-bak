package com.cqut.czb.bn.service.autoRecharge;

import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.*;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;

import java.util.List;

public interface AutoRechargeService {
    byte[]  getCode(String userId);

    LoginResult login(LoginInput loginInput, String userId);

    ReadCardOutput readCard(ReadCardInput readCardInput, String userId);

    RechargeOutput recharge(RechargeInput rechargeInput, String userId);

    SearchCardOutput searchCardId(SearchCardInput searchCardInput, String userId);

    TemplateOutput getTemplateData(TemplateInput templateInput, String userId);

    SaveTemplateOutput saveTemplate(SaveTemplateInput saveTemplateInput, String userId);

    List<SearchCardUser> searchCardIds(SearchCardInput searchCardInput, String userId);

    List<PetrolRechargeOutputDTO> getRechargeList(PetrolRechargeInputDTO petrolRechargeInputDTO, String userId);
}
