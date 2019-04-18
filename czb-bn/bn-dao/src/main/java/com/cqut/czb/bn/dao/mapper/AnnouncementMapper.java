package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Announcement;

public interface AnnouncementMapper {
    int deleteByPrimaryKey(String announcementId);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    Announcement selectByPrimaryKey(String announcementId);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);
}