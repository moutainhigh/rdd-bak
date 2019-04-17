package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.CountDTO;
import com.cqut.czb.bn.entity.dto.NotificationInputDTO;
import com.cqut.czb.bn.entity.entity.Notify;

import java.util.List;

public interface NotifyMapper {
    boolean insert(Notify notify);
    List<Notify> getList(NotificationInputDTO notificationInputDTO);
    CountDTO getCount(String type);
}
