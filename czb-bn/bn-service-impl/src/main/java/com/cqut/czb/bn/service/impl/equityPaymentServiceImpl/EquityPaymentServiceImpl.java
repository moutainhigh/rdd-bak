package com.cqut.czb.bn.service.impl.equityPaymentServiceImpl;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentCategoryMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentCommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentRecordMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentTypeMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.*;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.EquityPaymentService;
import com.cqut.czb.bn.service.impl.AnnouncementServiceImpl;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */

@Service
public class EquityPaymentServiceImpl implements EquityPaymentService {

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    @Autowired
    EquityPaymentCategoryMapperExtra equityPaymentCategoryMapperExtra;

    @Autowired
    EquityPaymentTypeMapperExtra equityPaymentTypeMapperExtra;

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

    public JSONResult deleteCommodity(String goodsId) {
        int column = equityPaymentCommodityMapperExtra.deleteCommodity(goodsId, true);
        if (column == 0) {
            return new JSONResult("删除商品失败");
        }

        return new JSONResult("删除商品成功");
    }

    @Override
    public PageInfo<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<EquityPaymentDTO> equityPaymentRecordList =  equityPaymentRecordMapperExtra.getEquityPaymentRecord(equityPaymentDTO);
        return new PageInfo<>(equityPaymentRecordList);
    }

    @Override
    public JSONResult getEquityPaymentOrderDetails(String orderId) {
        EquityPaymentDTO equityPaymentDTO = equityPaymentRecordMapperExtra.getEquityPaymentOrderDetails(orderId);
        if (equityPaymentDTO == null) {
            return new JSONResult("此订单已不存在");
        }

        return new JSONResult(equityPaymentDTO);
    }

    @Override
    public List<CategoryAndTypeDTO> getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO) {
        return equityPaymentRecordMapperExtra.getCategoryAndType(categoryAndTypeDTO);
    }

    @Override
    public List<CategoryAndTypeDTO> getAllType(CategoryAndTypeDTO categoryAndTypeDTO) {
        return equityPaymentRecordMapperExtra.getAllType(categoryAndTypeDTO);
    }

    @Override
    public JSONResult insertEquityPayment(String userId, EquityPaymentCommodityDTO equityPaymentCommodityDTO, MultipartFile files) {
        String address = "";
        try {
            if (files!=null||!files.isEmpty()) {
                address = FileUploadUtil.putObject(files.getOriginalFilename(), files.getInputStream());//返回图片储存路径
            }
        } catch (IOException ioException) {
            return new JSONResult("文件读取错误");
        }

        File file = announcementServiceImpl.setFile(files.getOriginalFilename(),address, userId,new Date());
        fileMapperExtra.insertSelective(file);
        equityPaymentCommodityDTO.setGoodsPic(file.getFileId());
        return new JSONResult(equityPaymentCommodityMapperExtra.insertEquityPayment(equityPaymentCommodityDTO) > 0);
    }

    @Override
    public JSONResult insertCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        equityPaymentCategoryDTO.setCategoryId(StringUtil.createId());
        return new JSONResult(equityPaymentCategoryMapperExtra.insertCategory(equityPaymentCategoryDTO) > 0);
    }

    @Override
    public JSONResult updateCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return new JSONResult(equityPaymentCategoryMapperExtra.updateCategory(equityPaymentCategoryDTO) > 0);
    }

    @Override
    public JSONResult deleteCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        return new JSONResult(equityPaymentCategoryMapperExtra.deleteCategory(equityPaymentCategoryDTO) > 0);
    }

    @Override
    public JSONResult insertType(String userId, EquityPaymentTypeDTO equityPaymentTypeDTO, MultipartFile files) {
        String address = "";
        try {
            if (files!=null||!files.isEmpty()) {
                address = FileUploadUtil.putObject(files.getOriginalFilename(), files.getInputStream());//返回图片储存路径
            }
        } catch (IOException ioException) {
            return new JSONResult("文件读取错误");
        }

        File file = announcementServiceImpl.setFile(files.getOriginalFilename(),address, userId,new Date());
        fileMapperExtra.insertSelective(file);
        equityPaymentTypeDTO.setPic(file.getFileId());
        return new JSONResult(equityPaymentTypeMapperExtra.insertType(equityPaymentTypeDTO) > 0);
    }

    @Override
    public JSONResult updateType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        return new JSONResult(equityPaymentTypeMapperExtra.updateType(equityPaymentTypeDTO) > 0);
    }

    @Override
    public JSONResult deleteType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        return new JSONResult(equityPaymentTypeMapperExtra.deleteType(equityPaymentTypeDTO) > 0);
    }

    @Override
    public JSONResult getUserEquityPaymentOrders(String userId) {
        List<EquityPaymentDTO> userEquityPaymentOrders = equityPaymentRecordMapperExtra.getUserEquityPaymentOrders(userId);
        return new JSONResult(userEquityPaymentOrders);
    }

}
