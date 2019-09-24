package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.InputRecommendDishDTO;
import com.cqut.czb.bn.service.food.AppDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppDishServiceImpl implements AppDishService {
    @Autowired
    DishMapperExtra dishMapperExtra;


    @Override
    public List<DishDTO> getRecommendDishList(InputRecommendDishDTO inputRecommendDishDTO) {
        return dishMapperExtra.selectRecommendDish(inputRecommendDishDTO);
    }

    @Override
    public List<DishShopDTO> getAllDishShop(DishShopDTO shop) {
        List<DishShopDTO> dishShopDTOS = dishMapperExtra.selectDishShop(shop);
        if (dishShopDTOS!=null && dishShopDTOS.size()!=0){
            if (shop.getSortType()==0){
                for (DishShopDTO dishShopDTO : dishShopDTOS){
                    if (dishShopDTO.getSaleCount()!=null&&dishShopDTO.getDistance()!=null){
                        //对排序值进行处理，目前为距离：销量 = 3：7
                        dishShopDTO.setSortValue(dishShopDTO.getDistance()*0.3+dishShopDTO.getSaleCount()*0.7);
                    }else {
                        dishShopDTO.setSortValue(0.0);
                    }

                }
                quickSort(dishShopDTOS,0,dishShopDTOS.size()-1);
            }
        }
        return dishShopDTOS;
    }

   public List<DishShopDTO> quickSort(List<DishShopDTO> dishShopDTOS,int left, int right) {
        int i, j;
        Double temp;
        DishShopDTO dishShopDTO=new DishShopDTO();
        if(left > right)
            return null;
        temp = dishShopDTOS.get(left).getSortValue(); //temp中存的就是基准数
        i = left;
        j = right;
        while(i != j) { //顺序很重要，要先从右边开始找
            while(dishShopDTOS.get(j).getSortValue() >= temp && i < j)
                j--;
            while(dishShopDTOS.get(i).getSortValue() <= temp && i < j)//再找右边的
                i++;
            if(i < j)//交换两个数在数组中的位置
            {
                dishShopDTO = dishShopDTOS.get(i);
                dishShopDTOS.set(i,dishShopDTOS.get(j)) ;
                dishShopDTOS.set(j,dishShopDTO) ;
            }
        }
        //最终将基准数归位
        if (dishShopDTO.getSortValue()!=null){  //如果上方进行了排序
            dishShopDTOS.set(left,dishShopDTOS.get(i)) ;
            dishShopDTOS.set(i,dishShopDTO) ;
            quickSort(dishShopDTOS,left, i-1);//继续处理左边的，这里是一个递归的过程
            quickSort(dishShopDTOS,i+1, right);//继续处理右边的 ，这里是一个递归的过程
        }
       return dishShopDTOS;
    }

}
