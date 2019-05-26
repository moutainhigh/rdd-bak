package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.announcement.AnnouncementDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AnnouncementService {
    //公告获取
    PageInfo<Announcement> getAnnouncement(AnnouncementDTO announcementDTO);
    //公告新增
    Boolean addAnnouncement(Announcement announcement,  MultipartFile file,User user) throws Exception;
    //公告删除
    Boolean deleteAnnouncement(String id);
//User user,带文件更新
    Boolean updateAnnouncementFile (Announcement announcement,  MultipartFile file,User user)throws Exception;
    //不带文件更新
    Boolean updateAnnouncement(Announcement announcement);
    //获取文件路径
    String getFileById(String id);
}
