package com.cqut.czb.bn.service.impl.equityPaymentServiceImpl;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.*;
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
import org.springframework.dao.DuplicateKeyException;
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

    @Autowired
    EquityPaymentAreaClothingMapperExtra equityPaymentAreaClothingMapperExtra;

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
        EquityPaymentCommodityDTO equityPaymentCommodityDTO = equityPaymentCommodityMapperExtra.selectCommodityByGoodsId(goodsId);
        int column1 = equityPaymentCommodityMapperExtra.deleteCommodity(goodsId, true);
        if (column1 == 0) {
            return new JSONResult("删除商品失败");
        }
        if (equityPaymentCommodityDTO.getGoodsPic() != null || !equityPaymentCommodityDTO.getGoodsPic().equals("")) {
            int column2 = fileMapperExtra.deleteByPrimaryKey(equityPaymentCommodityDTO.getGoodsPic());
            if (column2 == 0) {
                return new JSONResult("删除商品失败");
            }
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
    public JSONResult insertEquityPayment(String userId, EquityPaymentCommodityDTO equityPaymentCommodityDTO, MultipartFile[] files) {
        String addressOne = "";
        String addressTwo = "";
        try {
            if (files!=null) {
                if (files[0] != null || !files[0].isEmpty()) {
                    addressOne = FileUploadUtil.putObject(files[0].getOriginalFilename(), files[0].getInputStream());//返回图片储存路径
                }
                if (files[1] != null || !files[1].isEmpty()) {
                    addressTwo = FileUploadUtil.putObject(files[1].getOriginalFilename(), files[1].getInputStream());//返回图片储存路径
                }
            }
        } catch (IOException ioException) {
            return new JSONResult("文件读取错误", 500);
        }

        File fileOne = announcementServiceImpl.setFile(files[0].getOriginalFilename(),addressOne, userId,new Date());
        File fileTwo = announcementServiceImpl.setFile(files[1].getOriginalFilename(),addressTwo, userId,new Date());
        fileMapperExtra.insertSelective(fileOne);
        fileMapperExtra.insertSelective(fileTwo);
        equityPaymentCommodityDTO.setGoodsId(equityPaymentCommodityDTO.getProductCode());
        equityPaymentCommodityDTO.setCreateAt(new Date());
        equityPaymentCommodityDTO.setUpdateAt(new Date());
        equityPaymentCommodityDTO.setSoldNumber(0);
        equityPaymentCommodityDTO.setIsDelete(0);
        equityPaymentCommodityDTO.setGoodsPic(fileOne.getFileId());
        equityPaymentCommodityDTO.setProductDetails(fileTwo.getFileId());
        try {
            return new JSONResult(equityPaymentCommodityMapperExtra.insertEquityPayment(equityPaymentCommodityDTO) > 0);
        } catch (DuplicateKeyException e) {
            return new JSONResult("商品编码重复");
        }
    }

    @Override
    public JSONResult insertCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        equityPaymentCategoryDTO.setCategoryId(StringUtil.createId());
        equityPaymentCategoryDTO.setIsDelete(0);
        equityPaymentCategoryDTO.setOrder(equityPaymentCategoryDTO.getOrder());
        equityPaymentCategoryDTO.setCreateAt(new Date());
        equityPaymentCategoryDTO.setUpdateAt(new Date());
        return new JSONResult(equityPaymentCategoryMapperExtra.insertCategory(equityPaymentCategoryDTO) > 0);
    }

    @Override
    public JSONResult updateCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        equityPaymentCategoryDTO.setUpdateAt(new Date());
        return new JSONResult(equityPaymentCategoryMapperExtra.updateCategory(equityPaymentCategoryDTO) > 0);
    }

    @Override
    public JSONResult deleteCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO) {
        int count = equityPaymentTypeMapperExtra.getCountOfType(equityPaymentCategoryDTO);
        if (count != 0) {
            return new JSONResult("此类目下存在类别不能删除");
        }
        if (equityPaymentCategoryMapperExtra.deleteCategory(equityPaymentCategoryDTO) > 0) {
            return new JSONResult("删除成功");
        } else {
            return new JSONResult("删除失败");
        }
    }

    @Override
    public JSONResult insertType(String userId, EquityPaymentTypeDTO equityPaymentTypeDTO, MultipartFile files) {
        String address = "";
        try {
            if (files!=null) {
                address = FileUploadUtil.putObject(files.getOriginalFilename(), files.getInputStream());//返回图片储存路径
            }
        } catch (IOException ioException) {
            return new JSONResult("文件读取错误", 500);
        }

        File file = announcementServiceImpl.setFile(files.getOriginalFilename(),address, userId,new Date());
        fileMapperExtra.insertSelective(file);
        equityPaymentTypeDTO.setTypeId(StringUtil.createId());
        equityPaymentTypeDTO.setCategoryId(equityPaymentTypeDTO.getCategoryId());
        equityPaymentTypeDTO.setPic(file.getFileId());
        equityPaymentTypeDTO.setIsDelete(0);
        equityPaymentTypeDTO.setOrder(equityPaymentTypeDTO.getOrder());
        equityPaymentTypeDTO.setCreateAt(new Date());
        equityPaymentTypeDTO.setUpdateAt(new Date());
        return new JSONResult(equityPaymentTypeMapperExtra.insertType(equityPaymentTypeDTO) > 0);
    }

    @Override
    public JSONResult updateType(String userId, EquityPaymentTypeDTO equityPaymentTypeDTO, MultipartFile files) throws IOException {
        if (files != null) {
            File file1 = fileMapperExtra.selectByPrimaryKey(equityPaymentTypeDTO.getPic());
            file1.setSavePath(FileUploadUtil.putObject(files.getOriginalFilename(),files.getInputStream()));
            file1.setFileName(files.getOriginalFilename());
            file1.setUploader(userId);
            file1.setUpdateAt(new Date());
            fileMapperExtra.updateByPrimaryKeySelective(file1);
        }
        equityPaymentTypeDTO.setUpdateAt(new Date());
        return new JSONResult(equityPaymentTypeMapperExtra.updateType(equityPaymentTypeDTO) > 0);
    }

    @Override
    public JSONResult deleteType(EquityPaymentTypeDTO equityPaymentTypeDTO) {
        int count = equityPaymentCommodityMapperExtra.getCountOfCommodityByType(equityPaymentTypeDTO);
        fileMapperExtra.deleteByPrimaryKey(equityPaymentTypeDTO.getPic());
        if (count != 0) {
            return new JSONResult("此类别下存在商品不能删除");
        }
        if(equityPaymentTypeMapperExtra.deleteType(equityPaymentTypeDTO) > 0) {
            return new JSONResult("删除成功");
        } else {
            return new JSONResult("删除失败");
        }
    }

    @Override
    public JSONResult getUserEquityPaymentOrders(String userId) {
        List<EquityPaymentDTO> userEquityPaymentOrders = equityPaymentRecordMapperExtra.getUserEquityPaymentOrders(userId);
        return new JSONResult(userEquityPaymentOrders);
    }

    @Override
    public JSONResult updateEquityPayment(String userId, EquityPaymentCommodityDTO equityPaymentCommodityDTO, MultipartFile commodityPic, MultipartFile detailsPic) throws IOException {
        equityPaymentCommodityDTO.setUpdateAt(new Date());
//            for (MultipartFile file: files) {
//                File file1 = fileMapperExtra.selectByPrimaryKey(equityPaymentCommodityDTO.getGoodsPic());
//                file1.setSavePath(FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream()));
//                file1.setFileName(file.getOriginalFilename());
//                file1.setUploader(userId);
//                file1.setUpdateAt(new Date());
//                fileMapperExtra.updateByPrimaryKeySelective(file1);
//            }
            if (commodityPic != null && !commodityPic.isEmpty()) {
                updatePic(commodityPic, equityPaymentCommodityDTO.getGoodsPic(), userId);
            }

            if (detailsPic != null && !detailsPic.isEmpty()) {
                updatePic(detailsPic, equityPaymentCommodityDTO.getProductDetails(), userId);
            }

            try {
                return new JSONResult((equityPaymentCommodityMapperExtra.updateEquityPayment(equityPaymentCommodityDTO) > 0));
            } catch (DuplicateKeyException e) {
                return new JSONResult("商品编码重复");
            }
    }

    @Override
    public JSONResult getDistrictServiceInformation(String productCode) {
        List<EquityPaymentAreaClothingDTO> equityPaymentAreaClothingDTOList = equityPaymentAreaClothingMapperExtra.getDistrictServiceInformation(productCode);
        if (equityPaymentAreaClothingDTOList == null) {
            return new JSONResult("没有区服信息");
        }

        return new JSONResult(equityPaymentAreaClothingDTOList);
    }

    public boolean updatePic(MultipartFile file, String pic, String userId) throws IOException {
        File file1 = fileMapperExtra.selectByPrimaryKey(pic);
        file1.setSavePath(FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream()));
        file1.setFileName(file.getOriginalFilename());
        file1.setUploader(userId);
        file1.setUpdateAt(new Date());
        int num = fileMapperExtra.updateByPrimaryKeySelective(file1);
        if (num == 1) {
            return true;
        } else {
            return false;
        }
    }
}
