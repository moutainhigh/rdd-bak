package com.cqut.czb.bn.api.controller.equityPayment;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.*;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.EquityPaymentService;
import com.cqut.czb.bn.service.impl.AnnouncementServiceImpl;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */

@RestController
@RequestMapping("/api/equityPayment")
public class EquityPaymentController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    EquityPaymentService equityPaymentService;


    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping(value = "/getCommodityList", method = RequestMethod.GET)
    public JSONResult getCommodityList(EquityPaymentCommodityDTO equityPaymentCommodityDTO, PageDTO pageDTO) {
        return new JSONResult(equityPaymentService.getCommodityList(equityPaymentCommodityDTO, pageDTO));
    }

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/getCommodityDetails", method = RequestMethod.GET)
    public JSONResult getCommodityDetails(String goodsId) {
        return equityPaymentService.getCommodityDetails(goodsId);
    }

    /**
     * 通过商品id删除商品
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/deleteCommodity", method = RequestMethod.GET)
    public JSONResult deleteCommodity(String goodsId) {
        return equityPaymentService.deleteCommodity(goodsId);
    }

    /**
     * 后台管理系统
     * 获取商品订单详情分页
     * @param equityPaymentDTO
     * @param pageDTO
     * @return
     */
    @RequestMapping(value = "/getEquityPaymentRecord", method = RequestMethod.GET)
    public JSONResult getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO) {
        return new JSONResult(equityPaymentService.getEquityPaymentRecord(equityPaymentDTO, pageDTO));
    }

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getEquityPaymentOrderDetails", method = RequestMethod.GET)
    public JSONResult getEquityPaymentOrderDetails(String orderId) {
        return new JSONResult(equityPaymentService.getEquityPaymentOrderDetails(orderId));
    }

    /**
     * 获取用户订单列表
     * @param principal
     * @return
     */
    @RequestMapping(value = "/getUserEquityPaymentOrders", method = RequestMethod.GET)
    public JSONResult getUserEquityPaymentOrders(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(equityPaymentService.getUserEquityPaymentOrders(user.getUserId()));
    }

    /**
     * 级联选择-类目-类别
     * @return
     */
    @RequestMapping(value = "/getCategoryAndType", method = RequestMethod.GET)
    public JSONResult getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO) {
        return new JSONResult(equityPaymentService.getCategoryAndType(categoryAndTypeDTO));
    }

    /**
     * 获取所有的类型
     * @param categoryAndTypeDTO
     * @return
     */
    @RequestMapping(value = "/getAllType", method = RequestMethod.GET)
    public JSONResult getAllType(CategoryAndTypeDTO categoryAndTypeDTO) {
        return new JSONResult(equityPaymentService.getAllType(categoryAndTypeDTO));
    }

    /**
     * 新增商品
     * @return
     */
    @RequestMapping(value = "/insertEquityPayment", method = RequestMethod.POST)
    public JSONResult insertEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO, Principal principal, @RequestParam("file")MultipartFile[] files) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(equityPaymentService.insertEquityPayment(user.getUserId(), equityPaymentCommodityDTO, files));
    }

    /**
     * 新增类目
     * @return
     */
    @RequestMapping(value = "/insertCategory", method = RequestMethod.POST)
    public JSONResult insertCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return new JSONResult(equityPaymentService.insertCategory(equityPaymentCategoryDTO));
    }

    /**
     * 修改类目
     * @return
     */
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public JSONResult updateCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return new JSONResult(equityPaymentService.updateCategory(equityPaymentCategoryDTO));
    }

    /**
     * 删除类目
     * @return
     */
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    public JSONResult deleteCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return new JSONResult(equityPaymentService.deleteCategory(equityPaymentCategoryDTO));
    }

    /**
     * 新增类别
     * @return
     */
    @RequestMapping(value = "/insertType", method = RequestMethod.POST)
    public JSONResult insertType(Principal principal, EquityPaymentTypeDTO equityPaymentTypeDTO, @RequestParam("file")MultipartFile files) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(equityPaymentService.insertType(user.getUserId(), equityPaymentTypeDTO, files));
    }

    /**
     * 修改类别
     * @return
     */
    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    public JSONResult updateType(EquityPaymentTypeDTO equityPaymentTypeDTO, Principal principal, @RequestParam("file")MultipartFile files) {
        User user = (User) redisUtils.get(principal.getName());
        try {
            return new JSONResult(equityPaymentService.updateType(user.getUserId(), equityPaymentTypeDTO, files));
        } catch (IOException e) {
            return new JSONResult("图片上传错误");
        }
    }

    /**
     * 修改类别
     * @return
     */
    @RequestMapping(value = "/updateTypeNoPic", method = RequestMethod.POST)
    public JSONResult updateTypeNoPic(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        try {
            return new JSONResult(equityPaymentService.updateType(null, equityPaymentTypeDTO, null));
        } catch (IOException e) {
            return new JSONResult("图片上传错误");
        }
    }

    /**
     * 删除类别
     * @return
     */
    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    public JSONResult deleteType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        return new JSONResult(equityPaymentService.deleteType(equityPaymentTypeDTO));

    }
    /**
     * 修改商品+图片
     * @return
     */
    @RequestMapping(value = "/updateEquityPayment", method = RequestMethod.POST)
    public JSONResult updateEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO,@RequestParam(value = "commodityPic", required=false) MultipartFile commodityPic, @RequestParam(value = "detailsPic", required = false) MultipartFile detailsPic, Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        try {
            return equityPaymentService.updateEquityPayment(user.getUserId(), equityPaymentCommodityDTO, commodityPic, detailsPic);
        } catch (IOException exception) {
            return new JSONResult("图片上传错误");
        }
    }

    /**
     * 修改商品+无图片
     * @return
     */
    @RequestMapping(value = "/updateEquityPaymentNoPic", method = RequestMethod.POST)
    public JSONResult updateEquityPaymentNoPic(EquityPaymentCommodityDTO equityPaymentCommodityDTO, Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        try {
            return equityPaymentService.updateEquityPayment(user.getUserId(), equityPaymentCommodityDTO, null, null);
        } catch (IOException exception) {
            return new JSONResult("图片上传错误");
        }
    }

    /**
     * 获取区服信息
     * @param productCode
     * @return
     */
    @RequestMapping(value = "/getDistrictServiceInformation", method = RequestMethod.GET)
    public JSONResult getDistrictServiceInformation(String productCode) {
        return equityPaymentService.getDistrictServiceInformation(productCode);
    }
}
