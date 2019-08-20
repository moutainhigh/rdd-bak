package com.cqut.czb.bn.dao.mapper.vehicleService;


import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;

public interface RemotePushNoticeMapper {
    int deleteByPrimaryKey(String noticeId);

    int insert(RemotePushNotice record);

    int insertSelective(RemotePushNotice record);

    RemotePushNotice selectByPrimaryKey(String noticeId);

    int updateByPrimaryKeySelective(RemotePushNotice record);

    int updateByPrimaryKey(RemotePushNotice record);
}