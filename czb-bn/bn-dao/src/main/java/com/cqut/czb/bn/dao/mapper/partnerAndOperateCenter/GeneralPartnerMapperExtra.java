package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GetGeneralPartnerListDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GetNumberOfDevelopmentDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GeneralPartnerDevelopmentNumbers;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GetGeneralPartnerListVo;

import java.util.List;

public interface GeneralPartnerMapperExtra {
    /**
     *
     * @param
     * @return
     */
    List<GetGeneralPartnerListDTO> selectGeneralPartnerList(GeneralPartnerUserPageDTO pageDTO);

    /**
     *
     * @param
     * @return
     */
    GetNumberOfDevelopmentDTO selectDevelopmentNumbers(GeneralPartnerDevelopmentNumbers vo);
}
