package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.ServerStandardMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.CleanServerStandardService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CleanServerStandardServiceImpl implements CleanServerStandardService {
    @Autowired
    ServerStandardMapper serverStandardMapper;

    @Override
    public JSONResult add(ServerStandard serverStandard) {
        if(serverStandardMapper.insertSelective(serverStandard) > 0) {
            return new JSONResult("新增洗车服务类型成功",200);
        } else {
            return new JSONResult("新增洗车服务类型失败", 500);
        }
    }

    @Override
    public JSONResult delete(String id) {
        if(serverStandardMapper.deleteByPrimaryKey(id) > 0) {
            return new JSONResult("删除洗车服务类型成功",200);
        } else {
            return new JSONResult("删除洗车服务类型失败", 500);
        }
    }

    @Override
    public JSONResult change(ServerStandard serverStandard) {
        if(serverStandardMapper.updateByPrimaryKeySelective(serverStandard) > 0) {
            return new JSONResult("修改洗车服务类型成功",200);
        } else {
            return new JSONResult("修改洗车服务类型失败", 500);
        }
    }

    @Override
    public JSONResult search(ServerStandard serverStandard, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<ServerStandard> standardPageInfo = serverStandardMapper.search(serverStandard);

        return new JSONResult("查询洗车服务类型成功", 200, new PageInfo<>(standardPageInfo));
    }
}
