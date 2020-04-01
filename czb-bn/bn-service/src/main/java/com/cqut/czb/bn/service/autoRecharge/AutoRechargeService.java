package com.cqut.czb.bn.service.autoRecharge;

import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.*;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;

import java.util.List;

public interface AutoRechargeService {
    byte[]  getCode();

    LoginResult login(LoginInput loginInput);

    ReadCardOutput readCard(ReadCardInput readCardInput);

    RechargeOutput recharge(RechargeInput rechargeInput);

    SearchCardOutput searchCardId(SearchCardInput searchCardInput);

    TemplateOutput getTemplateData(TemplateInput templateInput);

    SaveTemplateOutput saveTemplate(SaveTemplateInput saveTemplateInput);

    List<SearchCardUser> searchCardIds(SearchCardInput searchCardInput);

    List<PetrolRechargeOutputDTO> getRechargeList(PetrolRechargeInputDTO petrolRechargeInputDTO);
}
