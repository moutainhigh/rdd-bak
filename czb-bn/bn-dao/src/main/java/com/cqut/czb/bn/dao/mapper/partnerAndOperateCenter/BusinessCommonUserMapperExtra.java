package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.BusinessCommonUserOutputDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface BusinessCommonUserMapperExtra {


    List<BusinessCommonUserOutputDTO> list(BusinessCommonUserVo vo);

    List<BusinessCommonUserOutputDTO> listPetrol(ArrayList<String> arrayList);

}
