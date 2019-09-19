package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushNoticeMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.PushDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticeDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticesDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePushNotice;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.RemotePushNoticeService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemotePushNoticeServiceImpl implements RemotePushNoticeService {
    @Autowired
    RemotePushNoticeMapperExtra mapper;
    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;
    @Autowired
    AppRouterMapper appRouterMapper;

    @Override
    public JSONResult search(RemotePushNotice notice, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<RemotePushNoticesDTO> notices = mapper.selectByPrimaryKey(notice);
        return new JSONResult("查询成功", 200, new PageInfo(notices));
    }

    @Override
    public JSONResult add(RemotePushNotice notice) {
        notice.setNoticeId(StringUtil.createId());
        if (mapper.insert(notice) > 0) {
            return new JSONResult("新增成功", 200);
        } else {
            return new JSONResult("新增失败", 500);
        }
    }

    @Override
    public JSONResult edit(RemotePushNotice notice) {
        if (mapper.updateByPrimaryKey(notice) > 0) {
            return new JSONResult("修改成功", 200);
        } else {
            return new JSONResult("修改失败", 500);
        }
    }

    @Override
    public JSONResult delete(RemotePushNoticeDTO notice) {
        if (mapper.deleteByPrimaryKey(notice.getNoticeId()) > 0) {
            return new JSONResult("删除成功", 200);
        } else {
            return new JSONResult("删除失败", 500);
        }
    }

    @Override
    public Page<RemotePushNoticesDTO> getRemotePushNoticeType(RemotePushNotice notice) {
        Page<RemotePushNoticesDTO> remotePushNotices = mapper.selectByPrimaryKey(notice);
        return remotePushNotices;
    }

    @Override
    public boolean addPush(PushDTO pushDTO) {
        if (pushDTO.getPathType()==null){
            return false;
        } else if (pushDTO.getPathType()==1){
            try {
                JGPush jgPush = new JGPush();
                jgPush.setType(3);
                jgPush.setNoticeId(pushDTO.getTitle());
                jgPush.setAppRouterMapper(appRouterMapper);
                jgPush.setRemotePushMapperExtra(remotePushMapperExtra);
                jgPush.setRemotePushNoticeMapperExtra(mapper);
                Thread thread = new Thread(jgPush);
                thread.start();
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (pushDTO.getPathType()==2) {
            MessageThread messageThread = new MessageThread();
            messageThread.setType(3);
            messageThread.setNoticeId(pushDTO.getTitle());
            messageThread.setAppRouterMapper(appRouterMapper);
            messageThread.setRemotePushMapperExtra(remotePushMapperExtra);
            messageThread.setRemotePushNoticeMapperExtra(mapper);
            Thread thread = new Thread(messageThread);
            thread.start();
            return true;
        }
        return false;
    }
}
