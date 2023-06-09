package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.PushDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticeDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticesDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.Page;

public interface RemotePushNoticeService {
    JSONResult search(RemotePushNotice notice, PageDTO pageDTO);

    JSONResult add(RemotePushNotice notice);

    JSONResult edit(RemotePushNotice notice);

    JSONResult delete(RemotePushNoticeDTO notice);

    Page<RemotePushNoticesDTO> getRemotePushNoticeType(RemotePushNotice notice);

    boolean addPush(PushDTO pushDTO);
}
