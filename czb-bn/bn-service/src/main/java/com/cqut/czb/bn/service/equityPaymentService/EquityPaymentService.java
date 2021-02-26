package com.cqut.czb.bn.service.equityPaymentService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */
public interface EquityPaymentService {

    /**
     * 获取商品列表
     * @param equityPaymentCommodityDTO
     * @param pageDTO
     * @return
     */
    PageInfo<EquityPaymentCommodityDTO> getCommodityList(EquityPaymentCommodityDTO equityPaymentCommodityDTO, PageDTO pageDTO);

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    JSONResult getCommodityDetails(String goodsId);


    /**
     * 删除商品
     * @param goodsId
     * @return
     */
    JSONResult deleteCommodity(String goodsId);
    /**
     * 后台管理系统
     * 获取商品订单详情分页
     * @param equityPaymentDTO
     * @param pageDTO
     * @return
     */
    PageInfo<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO);

    /**
     * 级联选择-类目-类别
     * @return
     */
    List<CategoryAndTypeDTO> getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO);

    /**
     * 新增商品
     * @return
     */
    JSONResult insertEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO);

    /**
     * 新增类目
     * @return
     */
    JSONResult insertCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO);

    /**
     * 修改类目
     * @return
     */
    JSONResult updateCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO);

    /**
     * 删除类目
     * @return
     */
    JSONResult deleteCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO);

    /**
     * 新增类别
     * @return
     */
    JSONResult insertType(EquityPaymentTypeDTO equityPaymentTypeDTO);

    /**
     * 修改类别
     * @return
     */
    JSONResult updateType(EquityPaymentTypeDTO equityPaymentTypeDTO);

    /**
     * 删除类别
     * @return
     */
    JSONResult deleteType(EquityPaymentTypeDTO equityPaymentTypeDTO);
}
