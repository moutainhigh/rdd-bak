package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.AnnouncementMapper;
import com.cqut.czb.bn.dao.mapper.FileMapper;
import com.cqut.czb.bn.entity.dto.AnnouncementDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.AnnouncementService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private FileMapper fileMapper;

    @Override //获取公告数据
    public PageInfo<Announcement> getAnnouncement(AnnouncementDTO announcementDTO) {
        PageHelper.startPage(announcementDTO.getPageNum(),announcementDTO.getPageSize());
        List<Announcement > announcements =announcementMapper.selectByPrimaryKey(announcementDTO.getAnnouncementId(),announcementDTO.getAnnouncementType());
        PageInfo<Announcement> pageInfo = new PageInfo<>(announcements);
        return  pageInfo;

    }

    @Override
    //User user,  添加公告
    public Boolean addAnnouncement(Announcement announcement,  MultipartFile file) throws Exception {
            String address="";
        if (file!=null||!file.isEmpty()) {
                 address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            }
            String id = StringUtil.createId();
            announcement.setAnnouncementId(id);
            announcement.setIsShow(0);   //添加时默认不展示
            File file1 = setFile(file.getOriginalFilename(),address,"wo",new Date());
            fileMapper.insertSelective(file1);
            announcement.setImgFileId(file1.getFileId()); //更新文件存储后的id
            return announcementMapper.insertSelective(announcement);
    }

    @Override //带文件更新
    public Boolean updateAnnouncementFile(Announcement announcement, MultipartFile file) throws Exception{
        String address="";
        if (file!=null||!file.isEmpty()) {
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
        }
        File file1 = setFile(file.getOriginalFilename(),address,"wo",new Date());
        file1.setFileId(announcement.getImgFileId());
        fileMapper.updateByPrimaryKeySelective(file1);
        return announcementMapper.updateByPrimaryKeySelective(announcement);
    }

    @Override //无文件更新
    public Boolean updateAnnouncement(Announcement announcement) {
        return announcementMapper.updateByPrimaryKeySelective(announcement);
    }

    @Override //根据文件id得到图片路径
    public String getFileById(String id) {
        File file = fileMapper.selectByPrimaryKey(id);
        if (file!=null)
        return file.getSavePath();
        else
            return "";
    }
    @Override //根据id删除公告
    public Boolean deleteAnnouncement(String id){
        List<Announcement> announcements = announcementMapper.selectByPrimaryKey(id,null);
        fileMapper.deleteByPrimaryKey(announcements.get(0).getImgFileId());
        return announcementMapper.deleteByPrimaryKey(id);
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
