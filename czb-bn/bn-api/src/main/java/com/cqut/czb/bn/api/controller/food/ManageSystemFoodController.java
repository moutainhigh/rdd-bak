package com.cqut.czb.bn.api.controller.food;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.ManageSystemFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author 谭深化
 * @Module 美食服务
 * @date 2019-09-23
 */

@RestController
@RequestMapping("/api/ManageSystemFood")
public class ManageSystemFoodController {
    @Autowired
    ManageSystemFoodService service;

    @Autowired
    RedisUtils redisUtils;

    @PostMapping("/add")
    public JSONResult add(Food food, Principal principal, @RequestParam("file")MultipartFile file) {
        User user = (User)redisUtils.get(principal.getName());
        return service.add(food, file, user);
    }

    @PostMapping("/delete")
    public JSONResult delete(Food food) {
        return service.delete(food);
    }

    @PostMapping("/change")
    public JSONResult change(Food food, Principal principal, @RequestParam("file")MultipartFile file) {
        User user = (User)redisUtils.get(principal.getName());
        return service.change(food, file, user);
    }

    @PostMapping("/changeWithoutImage")
    public JSONResult changeWithoutImage(@RequestBody Food food) {
        return service.changeWithoutImage(food);
    }

    @GetMapping("/search")
    public JSONResult search(Food food, PageDTO pageDTO, Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        if( null == user) {
            return new JSONResult("没有权限");
        }
        return service.search(food, pageDTO, user);
    }

    @GetMapping("/getSetInfo")
    public JSONResult getSetInfo(Food food) {
        return service.getSetInfo(food);
    }

    @GetMapping("/getShops")
    public JSONResult getShops(PageDTO pageDTO) {
        return service.getShops(pageDTO);
    }
}
