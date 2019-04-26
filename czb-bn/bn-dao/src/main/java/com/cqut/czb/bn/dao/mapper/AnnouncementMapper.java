package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Announcement;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AnnouncementMapper {
    Boolean deleteByPrimaryKey(String announcementId);

    int insert(Announcement record);

    Boolean insertSelective(Announcement record);

    List<Announcement> selectByPrimaryKey(@Param("announcementId") String announcementId, @Param("announcementType") Integer announcementType);

    Boolean updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);
}