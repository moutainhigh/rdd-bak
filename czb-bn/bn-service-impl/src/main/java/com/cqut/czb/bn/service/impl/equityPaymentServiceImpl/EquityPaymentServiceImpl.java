package com.cqut.czb.bn.service.impl.equityPaymentServiceImpl;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentRecordMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralDeductionInfoDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.EquityPaymentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */

@Service
public class EquityPaymentServiceImpl implements EquityPaymentService {

    @Autowired
    EquityPaymentRecordMapperExtra equityPaymentRecordMapperExtra;

    @Override
    public PageInfo<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<EquityPaymentDTO> equityPaymentRecordList =  equityPaymentRecordMapperExtra.getEquityPaymentRecord(equityPaymentDTO);
        return new PageInfo<>(equityPaymentRecordList);
    }

    @Override
    public JSONResult getCategoryAndType() {
        return equityPaymentRecordMapperExtra.getCategoryAndType();
    }
}
