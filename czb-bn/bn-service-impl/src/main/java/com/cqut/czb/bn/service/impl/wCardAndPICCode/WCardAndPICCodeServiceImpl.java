package com.cqut.czb.bn.service.impl.wCardAndPICCode;

import com.cqut.czb.bn.dao.mapper.wCardAndPICCode.WCardAndPICCodeMapper;
import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.cqut.czb.bn.service.wCardAndPICCode.WCardAndPICCodeService;

import java.util.List;

public class WCardAndPICCodeServiceImpl implements WCardAndPICCodeService{
    @Autowired
    WCardAndPICCodeMapper wCardAndPICCodeMapper;

    @Override
    public List<WCardAndPICCodeDTO> getCommodityOrder(String userId) {
        return wCardAndPICCodeMapper.selectCommodityOrder(userId);
    }

}
