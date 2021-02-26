package com.cqut.czb.bn.service.equityPaymentService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.web.multipart.MultipartFile;

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

    JSONResult getEquityPaymentOrderDetails(String orderId);

    /**
     * 级联选择-类目-类别
     * @return
     */
    List<CategoryAndTypeDTO> getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO);

    List<CategoryAndTypeDTO> getAllType(CategoryAndTypeDTO categoryAndTypeDTO);

    /**
     * 新增商品
     * @return
     */
    JSONResult insertEquityPayment(String userId, EquityPaymentCommodityDTO equityPaymentCommodityDTO, MultipartFile file);

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
    JSONResult insertType(String userId, EquityPaymentTypeDTO equityPaymentTypeDTO, MultipartFile file);

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

    /**
     * 获取用户订单列表
     * @param userId
     * @return
     */
    JSONResult getUserEquityPaymentOrders(String userId);
}
