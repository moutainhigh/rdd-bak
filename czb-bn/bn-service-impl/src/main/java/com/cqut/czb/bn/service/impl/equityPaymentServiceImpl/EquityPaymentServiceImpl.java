package com.cqut.czb.bn.service.impl.equityPaymentServiceImpl;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentCategoryMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentCommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentRecordMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.*;
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
    EquityPaymentCategoryMapperExtra equityPaymentCategoryMapperExtra;

    @Autowired
    EquityPaymentRecordMapperExtra equityPaymentRecordMapperExtra;

    @Autowired
    EquityPaymentCommodityMapperExtra equityPaymentCommodityMapperExtra;

    public PageInfo<EquityPaymentCommodityDTO> getCommodityList(EquityPaymentCommodityDTO equityPaymentCommodityDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<EquityPaymentCommodityDTO> equityPaymentCommodityList = equityPaymentCommodityMapperExtra.getCommodityList(equityPaymentCommodityDTO);
        return new PageInfo<>(equityPaymentCommodityList);
    }

    public JSONResult getCommodityDetails(String goodsId) {
        EquityPaymentCommodityDTO equityPaymentCommodityDTO = equityPaymentCommodityMapperExtra.selectCommodityByGoodsId(goodsId);
        if (equityPaymentCommodityDTO == null) {
            return new JSONResult("查询失败，不存在该商品");
        }
        return new JSONResult(equityPaymentCommodityDTO);
    }

    @Override
    public PageInfo<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<EquityPaymentDTO> equityPaymentRecordList =  equityPaymentRecordMapperExtra.getEquityPaymentRecord(equityPaymentDTO);
        return new PageInfo<>(equityPaymentRecordList);
    }

    @Override
    public List<CategoryAndTypeDTO> getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO) {
        return equityPaymentRecordMapperExtra.getCategoryAndType(categoryAndTypeDTO);
    }

    @Override
    public JSONResult insertEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO) {
        return new JSONResult(equityPaymentCommodityMapperExtra.insertEquityPayment(equityPaymentCommodityDTO) > 0);
    }

    @Override
    public JSONResult insertCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return new JSONResult(equityPaymentCategoryMapperExtra.insertCategory(equityPaymentCategoryDTO) > 0);
    }

    @Override
    public JSONResult updateCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return null;
    }

    @Override
    public JSONResult deleteCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return null;
    }

    @Override
    public JSONResult insertType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        return null;
    }

    @Override
    public JSONResult updateType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        return null;
    }

    @Override
    public JSONResult deleteType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        return null;
    }

}
