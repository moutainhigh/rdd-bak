package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityUserInfoCollectionDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.ShopSettledService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class ShopSettledServiceImpl implements ShopSettledService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    FileFunctionMapperExtra fileFunctionMapperExtra;

    @Autowired
    CommodityMapperExtra commodityMapperExtra;

    @Autowired
    CommodityUserInfoCollectionMapperExtra commodityUserInfoCollectionMapperExtra;

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Override
    public ShopDTO getShopInfo(ShopDTO shopDTO, User user) {
        if (shopDTO.getUserId()==null||"".equals(shopDTO.getUserId())){
            shopDTO.setUserId(user.getUserId());
        }
        ShopDTO shop = shopMapperExtra.selectShop(shopDTO);
        if (shop!=null) {
            List<FileFunctionDTO> file = fileFunctionMapperExtra.selectFile(shopDTO);
            shop.setFile(file);
        }
        return shop;

    }

    @Override
    public Boolean updateShopInfo(ShopDTO shopDTO) {
        return shopMapperExtra.updateShopInfo(shopDTO)>0;
    }

    @Override
    public PageInfo<CommodityDTO> getCommodity(CommodityDTO commodityDTO, PageDTO pageDTO, User user) {
        if (commodityDTO.getShopId()==null||"".equals(commodityDTO.getShopId())){
            CommodityDTO shopId = commodityMapperExtra.selectShopId(user.getUserId());
            if (shopId!=null||"".equals(shopId.getShopId())){
                commodityDTO.setShopId(shopId.getShopId());
            }else {
                return null;
            }
        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<CommodityDTO> commodityDTOS = commodityMapperExtra.selectCommodityByShop(commodityDTO);
        return new PageInfo<>(commodityDTOS);
    }

    @Override
    public Boolean insertInfo(CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO) {
        for (int i=0;i<commodityUserInfoCollectionDTO.length; i++) {
            commodityUserInfoCollectionDTO[i].setInfoId(StringUtil.createId());
        }
        List<CommodityUserInfoCollectionDTO> info = new ArrayList<>();
//        Collections.addAll(info,commodityUserInfoCollectionDTO);
        return commodityUserInfoCollectionMapperExtra.insertInfo(commodityUserInfoCollectionDTO)>0;
    }

    @Override
    public List<CommodityUserInfoCollectionDTO> selectInfo(CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO) {

            return commodityUserInfoCollectionMapperExtra.selectInfo(commodityUserInfoCollectionDTO);

    }

    @Override
    public List<CommodityUserInfoCollectionDTO> selectInfoByShop(CommodityUserInfoCollectionDTO commodityUserInfoCollectionDTO) {
        return commodityUserInfoCollectionMapperExtra.selectInfoByShop(commodityUserInfoCollectionDTO);
    }

    @Override
    public Boolean updateInfo(List<CommodityUserInfoCollectionDTO> commodityUserInfoCollectionDTO) {
        return commodityUserInfoCollectionMapperExtra.updateInfo(commodityUserInfoCollectionDTO)>0;
    }

    @Override
    public Boolean deleteInfo(String[] commodityUserInfoCollectionDTO) {
        return commodityUserInfoCollectionMapperExtra.deleteInfo(commodityUserInfoCollectionDTO)>0;
    }

    @Override
    public Boolean insertCommodity(CommodityDTO commodityDTO, MultipartFile file,User user) {
        if (commodityDTO.getCommodityId()==null||"".equals(commodityDTO.getCommodityId())){
            commodityDTO.setCommodityId(StringUtil.createId());
        }
        if (commodityDTO.getShopId()==null||"".equals(commodityDTO.getShopId())){
            CommodityDTO shopId = commodityMapperExtra.selectShopId(user.getUserId());
            if (shopId!=null||"".equals(shopId.getShopId())){
                commodityDTO.setShopId(shopId.getShopId());
            }else {
                return null;
            }
        }
        if (!"".equals(commodityDTO.getInfos())){
            String[] infos = commodityDTO.getInfos().split(";");
            String[][] mes =new String[infos.length][3];
            CommodityUserInfoCollectionDTO[] info = new CommodityUserInfoCollectionDTO[infos.length];
            for (int i=0;i<infos.length;i++){
                mes[i] = infos[i].split(",");
                CommodityUserInfoCollectionDTO one = new CommodityUserInfoCollectionDTO();
                one.setInfoId(StringUtil.createId());
                one.setCommodityId(commodityDTO.getCommodityId());
                one.setShopId(commodityDTO.getShopId());
                one.setInfoTitle(mes[i][0]);
                one.setInfoContent(mes[i][1]);
                one.setInfoCode(mes[i][2]);
                one.setCreateAt(new Date());
                info[i] = one;
            }
            commodityUserInfoCollectionMapperExtra.insertInfo(info);
        }
        String address="";
        try {
            if (file!=null||!file.isEmpty()) {
                address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        File file1 = setFile(file.getOriginalFilename(),address,user.getUserName(),new Date());
        fileMapperExtra.insert(file1);
        if (commodityDTO.getCreateAt()==null){
            commodityDTO.setCreateAt(new Date());
            commodityDTO.setUpdateAt(new Date());
        }
        commodityDTO.setCommodityImg(file1.getFileId());

        return commodityMapperExtra.insertCommodity(commodityDTO)>0;
    }

    @Override
    public Boolean updateCommodity(CommodityDTO commodityDTO, MultipartFile file) {
        return null;
    }

    @Override
    public Boolean updateCommodityNoFile(CommodityDTO commodityDTO) {
        int insertCount=0; //收集信息中需要插入的信息数
        int delecteCount=0;//收集信息中需要插入的信息数
        if (commodityDTO.getDeleteIds()!=null&&!"".equals(commodityDTO.getDeleteIds())){
            String[] deleted = commodityDTO.getDeleteIds().split(",");
            delecteCount = deleted.length;
            commodityUserInfoCollectionMapperExtra.deleteInfo(deleted);
        }
        if (commodityDTO.getInfoIds()!=null||"".equals(commodityDTO.getInfoIds())){
            String[] ids = commodityDTO.getInfoIds().split(",");
            if (commodityDTO.getInfos()!=null||"".equals(commodityDTO.getInfos())) {
                String[] infos = commodityDTO.getInfos().split(";");  //解析信息收集数组
                String[][] mes = new String[infos.length][3]; //解析的每组组信息收集数据
                List<CommodityUserInfoCollectionDTO> info = new ArrayList<>();   //准备将信息收集数组封装为对象数组
                List<CommodityUserInfoCollectionDTO> insert = new ArrayList<>();             //新增的数据
                for (int i = 0; i < infos.length; i++) {
                    mes[i] = infos[i].split(","); //解析每组信息收集数据
                    CommodityUserInfoCollectionDTO one = new CommodityUserInfoCollectionDTO();
                    if ("无".equals(ids[i])){  //如果没有id则表示需要新增
                        one.setInfoId(StringUtil.createId());
                        one.setInfoTitle(mes[i][0]);
                        one.setInfoContent(mes[i][1]);
                        one.setInfoCode(mes[i][2]);
                        one.setCreateAt(new Date());
                        one.setUpdateAt(new Date());
                        one.setCommodityId(commodityDTO.getCommodityId());
                        one.setShopId(commodityDTO.getShopId());
                        insert.add(one);
                    }else {
                        one.setInfoId(ids[i]);
                        one.setInfoTitle(mes[i][0]);
                        one.setInfoContent(mes[i][1]);
                        one.setInfoCode(mes[i][2]);
                        one.setUpdateAt(new Date());
                        info.add(one);
                    }

                }
                if (info!=null&&info.size()!=0) {
                    commodityUserInfoCollectionMapperExtra.updateInfo(info);
                }
                if (insert!=null&&insert.size()!=0){
                    CommodityUserInfoCollectionDTO[] insertArray = new CommodityUserInfoCollectionDTO[insert.size()];
                    commodityUserInfoCollectionMapperExtra.insertInfo(insert.toArray(insertArray));
                }
            }
        }
        return commodityMapperExtra.updateCommodity(commodityDTO)>0;
    }

    /**
     * 设置File对象内容
     */
    public File setFile(String name,String path,String user,Date create) {
        File file = new File();
        file.setFileId(StringUtil.createId());
        file.setFileName(name);
        file.setSavePath(path);
        file.setUploader(user);
        if (create == null) {
            file.setCreateAt(new Date());
        }
        else {
            file.setCreateAt(create);
        }
        file.setUpdateAt(new Date());
        return file;
    }

}
