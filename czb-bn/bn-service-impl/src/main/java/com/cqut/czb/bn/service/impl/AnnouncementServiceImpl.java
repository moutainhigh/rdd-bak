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

    @Override
    public PageInfo<Announcement> getAnnouncement(Announcement announcement) {
        PageHelper.startPage(announcement.getPageNum(),announcement.getPageSize());
        List<Announcement > announcements =announcementMapper.selectByPrimaryKey(announcement.getAnnouncementId(),announcement.getAnnouncementType());
        PageInfo<Announcement> pageInfo = new PageInfo<>(announcements);
        return  pageInfo;

    }

    @Override
    //User user,
    public Boolean addAnnouncement(Announcement announcement,  MultipartFile file) throws Exception {
            String address="";
        if (file!=null||!file.isEmpty()) {
                 address = FileUploadUtil.putObject(announcement.getAnnouncementTitle(), file.getInputStream());//返回图片储存路径
            }
            String id = StringUtil.createId();
            announcement.setAnnouncementId(id);
            File file1 = setFile(file.getOriginalFilename(),address,"wo",new Date());
            fileMapper.insertSelective(file1);
            announcement.setImgFileId(file1.getFileId());
            return announcementMapper.insertSelective(announcement);
    }

    @Override
    public Boolean updateAnnouncement(Announcement announcement, MultipartFile file) {
//        if (inputStream!=null){
//            String address= FileUploadUtil.putObject(announcement.getAnnouncementTitle(),inputStream);//返回图片储存路径
//            File file = setFile(announcement.getAnnouncementTitle(),address,user.getUserName(),announcement.getCreateAt());
//            fileMapper.updateByPrimaryKeySelective(file);
//        }
//        return announcementMapper.updateByPrimaryKeySelective(announcement);
        return null;
    }

    @Override
    public File getFileById(String id) {
        return fileMapper.selectByPrimaryKey(id);
    }

    public Boolean deleteAnnouncement(Announcement announcement){
        fileMapper.deleteByPrimaryKey(announcement.getImgFileId());
        return announcementMapper.deleteByPrimaryKey(announcement.getAnnouncementId());
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
