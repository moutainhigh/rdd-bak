package com.cqut.czb.bn.service.IssueCouponsService;

import com.cqut.czb.bn.entity.dto.IssueCoupons.IssueCouponsDTO;

import java.util.List;

public interface IssueCouponsService {

    /**
     * 获取所有优惠劵
     */
    List<IssueCouponsDTO> selectCoupons(IssueCouponsDTO issueCouponsDTO);
}
