package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.AnnouncementDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public interface AnnouncementService {
    PageInfo<Announcement> getAnnouncement(Announcement announcement);

    Boolean addAnnouncement(Announcement announcement,  MultipartFile file) throws Exception;

    Boolean deleteAnnouncement(String id);
//User user,
    Boolean updateAnnouncement(Announcement announcement,  MultipartFile file);

    File getFileById(String id);
}
