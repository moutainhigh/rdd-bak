package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.web.multipart.MultipartFile;

public interface ManageSystemFoodService {
    // 新增
    JSONResult add(Food food, MultipartFile file, User user);

    // 删除
    JSONResult delete(Food food);

    // 修改
    JSONResult change(Food food, MultipartFile file, User user);

    JSONResult changeWithoutImage(Food food);

    // 查询
    JSONResult search(Food food, PageDTO pageDTO);
}
