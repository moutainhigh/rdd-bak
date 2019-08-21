package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import com.github.pagehelper.Page;

public interface RemotePushNoticeMapperExtra {
    int deleteByPrimaryKey(String noticeId);

    int insert(RemotePushNotice record);

    int insertSelective(RemotePushNotice record);

    Page<RemotePushNotice> selectByPrimaryKey(RemotePushNotice record);

    RemotePushNotice selectById(String noticeId);

    int updateByPrimaryKeySelective(RemotePushNotice record);

    int updateByPrimaryKey(RemotePushNotice record);
}
