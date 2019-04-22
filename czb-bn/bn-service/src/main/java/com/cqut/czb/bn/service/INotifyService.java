package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.NotificationInputDTO;
import com.cqut.czb.bn.entity.dto.TotalCountDTO;
import com.cqut.czb.bn.entity.entity.Notify;

import java.util.List;

public interface INotifyService {
    boolean insert(Notify notify);
    List<Notify> getList(NotificationInputDTO notificationInputDTO);
    TotalCountDTO getCount();
}
