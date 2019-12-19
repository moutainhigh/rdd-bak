package com.cqut.czb.bn.service.impl.wechatAppletServiceImpl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.CategoryMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.ShopInfoDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserRoleDTO;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    FileFunctionMapperExtra fileFunctionMapperExtra;

    @Autowired
    FileFunctionMapper fileFunctionMapper;

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    CategoryMapperExtra categoryMapperExtra;

    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;

    @Override
    public PageInfo<WxCommodityDTO> getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO, String userId) {
//        UserRoleDTO userRole = new UserRoleDTO();
//        userRole.setUserId(userId);
//        List<UserRoleDTO> userRoles = userRoleMapperExtra.selectUserRoleName(userRole);
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
//        for(UserRoleDTO userRoleDTO : userRoles){
//            if("管理员".equals(userRoleDTO.getRoleName())){
//                return new PageInfo<>(weChatCommodityMapperExtra.selectAllCommodity(wxCommodityDTO));
//            }else if("微信商家".equals(userRoleDTO.getRoleName())){
//                wxCommodityDTO.setUserId(userId);
//                return
//            }
//        }
        return new PageInfo<>(weChatCommodityMapperExtra.selectAllCommodity(wxCommodityDTO));
    }

    @Override
    public Boolean deletedWxCommodity(String commodityId) {
        return weChatCommodityMapperExtra.deleteByPrimaryKey(commodityId)>0;
    }

    @Override
    public Boolean addWxCommodity(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException, InterruptedException {
        wxCommodityDTO.setCommodityId(StringUtil.createWCId());
        wxCommodityDTO.setCommodityImgId(StringUtil.createId());
        wxCommodityDTO.setCreateAt(new Date());
        wxCommodityDTO.setUpdateAt(new Date());
        //将富文本编辑器中的图片url改为http请求
        if (wxCommodityDTO.getCommodityIntroduce()!=null)
        wxCommodityDTO.setCommodityIntroduce(wxCommodityDTO.getCommodityIntroduce().replace("https","http"));
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
            fileFunction.setLocalId(wxCommodityDTO.getCommodityImgId());
            fileFunction.setCreateAt(new Date());
            fileFunction.setUpdateAt(new Date());
            fileFunction.setGroupCode("WCCommodity");
            fileFunctionMapper.insertSelective(fileFunction);
        }
        return weChatCommodityMapperExtra.insertCommodity(wxCommodityDTO) > 0;
    }

    @Override
    public Boolean addWxCommodityImg(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException {
        wxCommodityDTO.setCommodityImgId(StringUtil.createId());
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
            fileFunction.setLocalId(wxCommodityDTO.getCommodityImgId());
            fileFunction.setCreateAt(new Date());
            fileFunction.setUpdateAt(new Date());
            fileFunction.setGroupCode("WCCommodity");
            return fileFunctionMapper.insertSelective(fileFunction) > 0 && fileMapper.insertSelective(file1) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateCommodity(WxCommodityDTO wxCommodityDTO) {
        if(wxCommodityDTO.getCommodityId() == null ||   "".equals(wxCommodityDTO.getCommodityImgId()) )
            return false;
        Boolean deleteImgs = true;
        if (wxCommodityDTO.getCommodityIntroduce()!=null){
            //将富文本编辑器中的图片url改为http请求
            wxCommodityDTO.setCommodityIntroduce(wxCommodityDTO.getCommodityIntroduce().replace("https","http"));
            String pattern = "(<img)(.*?)(/>)";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            // 创建 matcher 对象
            Matcher m = r.matcher(wxCommodityDTO.getCommodityIntroduce());
            //将所有img标签没有style的都加上宽度
            while(m.find())
            {
                System.out.println(m.group());
                String exp = m.group();
                if (exp.indexOf("style=\"")<0) {
                    StringBuffer str = new StringBuffer(exp);
                    str.insert(4," style=\"width:100%\" ");
                    wxCommodityDTO.setCommodityIntroduce(wxCommodityDTO.getCommodityIntroduce().replace(exp,str));
                }


            }
        }

        if(wxCommodityDTO.getDeleteIds() != null && !"".equals(wxCommodityDTO.getDeleteIds())){
            deleteImgs = fileMapperExtra.deleteByDeleteIds(wxCommodityDTO.getDeleteIds()) > 0 && fileFunctionMapperExtra.deleteByDeleteIds(wxCommodityDTO.getDeleteIds()) > 0;
        }
        return weChatCommodityMapperExtra.updateCommodity(wxCommodityDTO) >
                0 && deleteImgs;
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
            fileFunction.setLocalId(wxCommodityDTO.getCommodityImgId());
            fileFunction.setCreateAt(new Date());
            fileFunction.setUpdateAt(new Date());
            //将富文本编辑器中的图片url改为http请求
            if (wxCommodityDTO.getCommodityIntroduce()!=null)
                wxCommodityDTO.setCommodityIntroduce(wxCommodityDTO.getCommodityIntroduce().replace("https","http"));
            if(wxCommodityDTO.getInsertType() == 1){
                fileFunction.setGroupCode("WCCommodity");
                insertImg = fileFunctionMapper.insertSelective(fileFunction) > 0 && fileMapper.insertSelective(file1) > 0;
            }else if(wxCommodityDTO.getInsertType() == 2){
                fileFunction.setGroupCode("WCCommodity");
                insertImg = fileFunctionMapper.insertSelective(fileFunction) > 0 && fileMapper.insertSelective(file1) > 0;
            }else if(wxCommodityDTO.getInsertType() == 3){
                //如果有海报图片
                if(weChatCommodityMapperExtra.selectPosterImgCount(wxCommodityDTO.getCommodityImgId()) != null && weChatCommodityMapperExtra.selectPosterImgCount(wxCommodityDTO.getCommodityImgId()) != 0){
                    insertImg = weChatCommodityMapperExtra.updatePoster(wxCommodityDTO.getCommodityImgId(), address) > 0;
                }else{ //如果没有海报图片
                    fileFunction.setGroupCode("PosterImg");
                    insertImg = fileFunctionMapper.insertSelective(fileFunction) > 0 && fileMapper.insertSelective(file1) > 0;
                }
            }else{
                insertImg = false;
            }
        }
        Boolean deleteImgs = true;
        if(wxCommodityDTO.getDeleteIds() != null && !"".equals(wxCommodityDTO.getDeleteIds()) ){
            deleteImgs = fileMapperExtra.deleteByDeleteIds(wxCommodityDTO.getDeleteIds()) > 0 && fileFunctionMapperExtra.deleteByDeleteIds(wxCommodityDTO.getDeleteIds()) > 0;
        }
        if (wxCommodityDTO.getCommodityIntroduce()!=null){
            //将富文本编辑器中的图片url改为http请求
            wxCommodityDTO.setCommodityIntroduce(wxCommodityDTO.getCommodityIntroduce().replace("https","http"));
            String pattern = "(<img)(.*?)(/>)";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            // 创建 matcher 对象
            Matcher m = r.matcher(wxCommodityDTO.getCommodityIntroduce());
            //将所有img标签没有style的都加上宽度
            while(m.find())
            {
                System.out.println(m.group());
                String exp = m.group();
                if (exp.indexOf("style=\"")<0) {
                    StringBuffer str = new StringBuffer(exp);
                    str.insert(4," style=\"width:100%\" ");
                    wxCommodityDTO.setCommodityIntroduce(wxCommodityDTO.getCommodityIntroduce().replace(exp,str));
                }


            }
        }
        return weChatCommodityMapperExtra.updateCommodity(wxCommodityDTO) > 0 && insertImg && deleteImgs;
    }

    @Override
    public List<ShopInfoDTO> getAllShopInfo() {
        return shopMapperExtra.selectAllShopInfo();
    }
}
