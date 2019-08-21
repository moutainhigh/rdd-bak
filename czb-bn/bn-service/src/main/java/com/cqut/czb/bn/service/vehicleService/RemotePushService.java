package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface RemotePushService {
    Boolean insertToken(String DeviceToken, User user,Integer type);
}
