package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.AppRouterMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.service.food.FindTodayFoodService;
import com.cqut.czb.bn.service.impl.AppHomePageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class FindTodayFoodServiceImpl implements FindTodayFoodService{
    @Autowired
    AppRouterMapperExtra appRouterMapperExtra;
    @Autowired
    AppHomePageServiceImpl appHomePageService;
    @Autowired
    DishMapperExtra dishMapperExtra;
    @Override
    public AppRouterDTO getPic(AppRouterDTO appRouterDTO) {
        List<AppRouterDTO> appRouterDTOS = appRouterMapperExtra.selectAppRouterBycode(appRouterDTO);
        if (appRouterDTOS!=null && appRouterDTOS.size()!=0){
            List<DishDTO> dishDTOS = dishMapperExtra.findTodayFood();
            if (dishDTOS!=null && dishDTOS.size()>0){
                appRouterDTOS.get(0).setSavePath(dishDTOS.get(new Random().nextInt(dishDTOS.size())).getSavePath());
                return appRouterDTOS.get(0);
            } else {
               return new AppRouterDTO();
            }

        }else {
            return new AppRouterDTO();
        }

    }
}
