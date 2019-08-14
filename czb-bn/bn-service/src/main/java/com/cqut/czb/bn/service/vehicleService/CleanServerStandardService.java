package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.web.multipart.MultipartFile;

/*
auth: tsh
time: 2019.8.13
version: 1.0
 */
public interface CleanServerStandardService {
    // 新增
    JSONResult add(ServerStandard serverStandard, MultipartFile file, User user);

    // 删除
    JSONResult delete(String id);

    // 修改
    JSONResult change(ServerStandard serverStandard, MultipartFile file, User user);

    JSONResult changeWithoutImage(ServerStandard serverStandard);

    // 查询
    JSONResult search(ServerStandard serverStandard, PageDTO pageDTO);
}
