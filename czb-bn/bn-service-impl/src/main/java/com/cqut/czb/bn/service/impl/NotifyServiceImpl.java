package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.NotifyMapper;
import com.cqut.czb.bn.entity.dto.CountDTO;
import com.cqut.czb.bn.entity.dto.NotificationInputDTO;
import com.cqut.czb.bn.entity.dto.TotalCountDTO;
import com.cqut.czb.bn.entity.entity.Notify;
import com.cqut.czb.bn.service.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyServiceImpl implements INotifyService {
    @Autowired
    NotifyMapper notifyMapper;
    @Override
    public boolean insert(Notify notify) {
        return notifyMapper.insert(notify);
    }

    @Override
    public List<Notify> getList(NotificationInputDTO notificationInputDTO) {
        return notifyMapper.getList(notificationInputDTO);
    }

    @Override
    public TotalCountDTO getCount() {
        TotalCountDTO totalCountDTO = new TotalCountDTO();
        CountDTO wx = notifyMapper.getCount("微信");
        CountDTO al = notifyMapper.getCount("支付宝");
        totalCountDTO.setTotalCount(wx.getTotal() + al.getTotal());
        totalCountDTO.setTotalSuccess(wx.getSuccess() + al.getSuccess());
        totalCountDTO.setTotalFailure(wx.getFailure() + al.getFailure());
        totalCountDTO.setALSuccess(al.getSuccess());
        totalCountDTO.setALFailure(al.getFailure());
        totalCountDTO.setALTotal(al.getTotal());
        totalCountDTO.setWXTotal(wx.getTotal());
        totalCountDTO.setWXFailure(wx.getFailure());
        totalCountDTO.setWXSuccess(wx.getSuccess());
        return totalCountDTO;
    }
}
