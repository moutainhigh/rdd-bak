package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

/**
 * @ClassName: OrdinaryManagement
 * @Author: Iriya720
 * @Date: 2019/12/2
 * @Description:
 * @version: v1.0
 */
public interface OrdinaryManagement {

    /**
     *
     * @param pageDTO
     */
    JSONResult getTableData(GeneralPartnerUserPageDTO pageDTO);
}
