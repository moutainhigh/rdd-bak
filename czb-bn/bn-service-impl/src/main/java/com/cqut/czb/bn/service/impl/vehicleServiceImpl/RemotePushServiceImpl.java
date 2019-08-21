package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapperExtra;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import com.cqut.czb.bn.service.vehicleService.RemotePushService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemotePushServiceImpl implements RemotePushService {
    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;

    @Override
    public Boolean insertToken(String DeviceToken, User user,Integer type) {
        RemotePush remotePush = new RemotePush();
        remotePush.setDeviceId(StringUtil.createId());
        remotePush.setDeviceToken(DeviceToken);
        remotePush.setUserId(user.getUserId());
        remotePush.setDeviceType(type);
        return remotePushMapperExtra.insert(remotePush)>0;
    }
}
