package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.AnnouncementMapperExtra;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.entity.dto.announcement.AnnouncementDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.AnnouncementService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementMapperExtra announcementMapperExtra;
    @Autowired
    private FileMapperExtra fileMapperExtra;

    @Override //获取公告数据
    public PageInfo<Announcement> getAnnouncement(AnnouncementDTO announcementDTO) {
        PageHelper.startPage(announcementDTO.getPageNum(),announcementDTO.getPageSize());
        List<Announcement > announcements =announcementMapperExtra.selectByPrimaryKey(announcementDTO.getAnnouncementId(),announcementDTO.getAnnouncementType());
        PageInfo<Announcement> pageInfo = new PageInfo<>(announcements);
        return  pageInfo;

    }

    @Override
    //User user,  添加公告
    public Boolean addAnnouncement(Announcement announcement,  MultipartFile file,User user) throws Exception {
            String address="";
        if (file!=null||!file.isEmpty()) {
                 address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            }
            String id = StringUtil.createId();
            announcement.setAnnouncementId(id);
            announcement.setIsShow(0);   //添加时默认不展示
//            announcement.setUpdateAt(new Date());
            File file1 = setFile(file.getOriginalFilename(),address,user.getUserName(),new Date());
            fileMapperExtra.insertSelective(file1);
            announcement.setImgFileId(file1.getFileId()); //更新文件存储后的id
            if (announcement.getUpdateAt()==null)
            announcement.setUpdateAt(announcement.getCreateAt());
            return (announcementMapperExtra.insertSelective(announcement)>0);
    }

    @Override //带文件更新
    public Boolean updateAnnouncementFile(Announcement announcement, MultipartFile file,User user) throws Exception{
        String address="";
        if (file!=null||!file.isEmpty()) {
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
        }
        File file1 = setFile(file.getOriginalFilename(),address,user.getUserName(),new Date());
        file1.setFileId(announcement.getImgFileId());
        fileMapperExtra.updateByPrimaryKeySelective(file1);
        return (announcementMapperExtra.updateByPrimaryKeySelective(announcement)>0);
    }

    @Override //无文件更新
    public Boolean updateAnnouncement(Announcement announcement) {
        return (announcementMapperExtra.updateByPrimaryKeySelective(announcement)>0);  }

    @Override //根据文件id得到图片路径
    public String getFileById(String id) {
        File file = fileMapperExtra.selectByPrimaryKey(id);
        if (file!=null)
        return file.getSavePath();
        else
            return "";
    }
    @Override //根据id删除公告
    public Boolean deleteAnnouncement(String id){
        List<Announcement> announcements = announcementMapperExtra.selectByPrimaryKey(id,null);
        fileMapperExtra.deleteByPrimaryKey(announcements.get(0).getImgFileId());
        return (announcementMapperExtra.deleteByPrimaryKey(id)>0);
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
