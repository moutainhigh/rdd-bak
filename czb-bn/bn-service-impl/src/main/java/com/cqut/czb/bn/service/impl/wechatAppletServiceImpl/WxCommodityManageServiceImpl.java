package com.cqut.czb.bn.service.impl.wechatAppletServiceImpl;

import com.cqut.czb.bn.dao.mapper.FileFunctionMapper;
import com.cqut.czb.bn.dao.mapper.FileMapper;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.CategoryMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.FileFunction;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Category;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.service.wechatAppletService.WxCommodityManageService;
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

@Service
public class WxCommodityManageServiceImpl implements WxCommodityManageService {

    @Autowired
    WeChatCommodityMapperExtra weChatCommodityMapperExtra;

    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    FileMapper fileMapper;

    @Autowired
    FileFunctionMapper fileFunctionMapper;

    @Autowired
    CategoryMapperExtra categoryMapperExtra;

    @Override
    public PageInfo<WxCommodityDTO> getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(weChatCommodityMapperExtra.selectAllCommodity(wxCommodityDTO));
    }

    @Override
    public Boolean deletedWxCommodity(String commodityId) {
        return weChatCommodityMapperExtra.deleteByPrimaryKey(commodityId)>0;
    }

    @Override
    public Boolean addWxCommodity(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException {
        wxCommodityDTO.setCommodityId(StringUtil.createId());
        wxCommodityDTO.setCommodityImgId(StringUtil.createId());
        wxCommodityDTO.setCreateAt(new Date());
        wxCommodityDTO.setUpdateAt(new Date());
        String address = "";
        if (file != null && !file.isEmpty()) {
            //插入图片
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            File file1 = new File();
            file1.setFileId(StringUtil.createId());
            file1.setFileName(file.getOriginalFilename());
            file1.setSavePath(address);
            file1.setUploader(user.getUserId());
            file1.setCreateAt(new Date());
            file1.setUpdateAt(new Date());
            fileMapper.insertSelective(file1);

            FileFunction fileFunction = new FileFunction();
            fileFunction.setId(StringUtil.createId());
            fileFunction.setFileId(file1.getFileId());
            fileFunction.setLocalId(wxCommodityDTO.getCommodityId());
            fileFunction.setCreateAt(new Date());
            fileFunction.setUpdateAt(new Date());
            fileFunction.setGroupCode("WCCommodity");
            fileFunctionMapper.insertSelective(fileFunction);
        }
        return weChatCommodityMapperExtra.insertCommodity(wxCommodityDTO) > 0;
    }

    @Override
    public Boolean addWxCommodityImg(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException {
        if(file != null && !file.isEmpty() && wxCommodityDTO.getCommodityImgId() != null && wxCommodityDTO.getCommodityImgId() != ""){
            String address = "";
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            File file1 = new File();
            file1.setFileId(StringUtil.createId());
            file1.setFileName(file.getOriginalFilename());
            file1.setSavePath(address);
            file1.setUploader(user.getUserId());
            file1.setCreateAt(new Date());
            file1.setUpdateAt(new Date());

            FileFunction fileFunction = new FileFunction();
            fileFunction.setId(StringUtil.createId());
            fileFunction.setFileId(file1.getFileId());
            fileFunction.setLocalId(wxCommodityDTO.getCommodityId());
            fileFunction.setCreateAt(new Date());
            fileFunction.setUpdateAt(new Date());
            fileFunction.setGroupCode("WCCommodity");
            return fileFunctionMapper.insertSelective(fileFunction) > 0 && fileMapper.insertSelective(file1) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateCommodity(WxCommodityDTO wxCommodityDTO) {
        if(wxCommodityDTO.getCommodityId() == null || wxCommodityDTO.getCommodityImgId() == "")
            return false;
        return weChatCommodityMapperExtra.updateCommodity(wxCommodityDTO) > 0;
    }

    @Override
    public Boolean deleteWxCommodityImg(String fileId, String id) {
        return fileMapper.deleteByPrimaryKey(fileId) > 0 && fileFunctionMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<Category> selectAllCategory() {
        return categoryMapperExtra.selectAllCategory();
    }

    @Override
    public Boolean haltOrOnSales(String ids, Integer type) {
        return type == 1 ? weChatCommodityMapperExtra.updateIsSale(ids, 1) > 0 : type == 2 ? weChatCommodityMapperExtra.updateIsSale(ids, 2) > 0 : false;
    }

    @Override
    public Boolean editWeChatCommodityWithImg(String userId, WxCommodityDTO wxCommodityDTO, MultipartFile file) throws IOException {
        Boolean insertImg = true;
        if(file != null && !file.isEmpty() && wxCommodityDTO.getCommodityImgId() != null && wxCommodityDTO.getCommodityImgId() != ""){
            String address = "";
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            File file1 = new File();
            file1.setFileId(StringUtil.createId());
            file1.setFileName(file.getOriginalFilename());
            file1.setSavePath(address);
            file1.setUploader(userId);
            file1.setCreateAt(new Date());
            file1.setUpdateAt(new Date());

            FileFunction fileFunction = new FileFunction();
            fileFunction.setId(StringUtil.createId());
            fileFunction.setFileId(file1.getFileId());
            fileFunction.setLocalId(wxCommodityDTO.getCommodityId());
            fileFunction.setCreateAt(new Date());
            fileFunction.setUpdateAt(new Date());
            fileFunction.setGroupCode("WCCommodity");
            insertImg = fileFunctionMapper.insertSelective(fileFunction) > 0 && fileMapper.insertSelective(file1) > 0;
        }
        Boolean deleteImgs = true;
        if(wxCommodityDTO.getCommodityImgId() != null && wxCommodityDTO.getDeleteIds() != ""){
            deleteImgs = fileMapperExtra.deleteByPrimaryKey(wxCommodityDTO.getDeleteIds()) > 0 && fileFunctionMapper.deleteByPrimaryKey(wxCommodityDTO.getDeleteIds()) > 0;
        }
        return weChatCommodityMapperExtra.updateCommodity(wxCommodityDTO) > 0 && insertImg && deleteImgs;
    }
}
