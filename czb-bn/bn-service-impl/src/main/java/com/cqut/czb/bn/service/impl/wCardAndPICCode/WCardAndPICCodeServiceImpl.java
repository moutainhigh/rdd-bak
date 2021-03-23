package com.cqut.czb.bn.service.impl.wCardAndPICCode;

import com.cqut.czb.bn.dao.mapper.wCardAndPICCode.WCardAndPICCodeMapper;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.cqut.czb.bn.service.wCardAndPICCode.WCardAndPICCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WCardAndPICCodeServiceImpl implements WCardAndPICCodeService{
    @Autowired
    WCardAndPICCodeMapper wCardAndPICCodeMapper;

    @Override
    public List<H5StockDTO> getCommodityOrder(String userId, Integer type) {
        return wCardAndPICCodeMapper.selectCommodityOrder(userId, type);
    }

}
