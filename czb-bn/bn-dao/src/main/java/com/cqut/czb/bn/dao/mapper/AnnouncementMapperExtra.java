package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnnouncementMapperExtra {
    Boolean deleteByPrimaryKey(String announcementId);

    int insert(Announcement record);

    Boolean insertSelective(Announcement record);

    List<Announcement> selectByPrimaryKey(@Param("announcementId") String announcementId, @Param("announcementType") Integer announcementType);

    Boolean updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);

    List<appAnnouncementDTO> selectAnnouncement();
}