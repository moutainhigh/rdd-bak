package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GeneralPartnerUser;

import java.util.List;

/**
 * @ClassName: OrdinaryManagementMapperExtra
 * @Author: Iriya720
 * @Date: 2019/12/2
 * @Description:
 * @version: v1.0
 */
public interface OrdinaryManagementMapperExtra {

    /**
     * 获取表格数据
     * @param pageDTO
     * @return
     */
    List<GeneralPartnerUser> getAllUser(GeneralPartnerUserPageDTO pageDTO);

    /**
     * 获取总数
     * @return
     * @param pageDTO
     */
    int getTotalNumber(GeneralPartnerUserPageDTO pageDTO);
}
