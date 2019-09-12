package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticesDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface RemotePushNoticeMapperExtra {
    int deleteByPrimaryKey(String noticeId);

    int insert(RemotePushNotice record);

    int insertSelective(RemotePushNotice record);

    Page<RemotePushNoticesDTO> selectByPrimaryKey(RemotePushNotice record);

    RemotePushNotice selectById(String noticeId);

    int updateByPrimaryKeySelective(RemotePushNotice record);

    int updateByPrimaryKey(RemotePushNotice record);
}
