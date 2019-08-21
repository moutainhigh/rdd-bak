package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushNoticeMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushNoticeMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RemotePushNoticeDTO;
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

    @Override
    public JSONResult search(RemotePushNotice notice, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<RemotePushNotice> notices = mapper.selectByPrimaryKey(notice);
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
}
