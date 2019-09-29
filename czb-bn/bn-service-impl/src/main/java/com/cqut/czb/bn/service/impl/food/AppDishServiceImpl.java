package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.DistanceMeter;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.DishShopDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.InputRecommendDishDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.SearchDishShopDTO;
import com.cqut.czb.bn.entity.dto.food.foodHomePage.SearchInputDTO;
import com.cqut.czb.bn.service.food.AppDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppDishServiceImpl implements AppDishService {
    @Autowired
    DishMapperExtra dishMapperExtra;
    @Autowired
    MyOrderServiceImpl myOrderService;


    @Override
    public List<SearchDishShopDTO> getRecommendDishList(InputRecommendDishDTO inputRecommendDishDTO) {
        List<SearchDishShopDTO> recommendShops = dishMapperExtra.selectRecommendDishShop(inputRecommendDishDTO);
        if (recommendShops!=null && recommendShops.size()!=0){
            for (SearchDishShopDTO recommendShop : recommendShops){
                if (inputRecommendDishDTO.getLatitude()!=null && inputRecommendDishDTO.getLongitude()!=null && recommendShop.getLatitude() != null && recommendShop.getLongitude() != null) {
                    recommendShop.setDistance(DistanceMeter.InputDistance(inputRecommendDishDTO.getLongitude(),inputRecommendDishDTO.getLatitude(),recommendShop.getLongitude(),recommendShop.getLatitude()));
                }else {
                    recommendShop.setDistance(null);
                }
                if(recommendShop.getDistance() != null) {
                    if (recommendShop.getDistance() < 1000) {
                        recommendShop.setDistanceWithUnit(recommendShop.getDistance().toString() + "m");
                    } else if(recommendShop.getDistance()<50000 &&recommendShop.getDistance()>1000){
                        recommendShop.setDistanceWithUnit(myOrderService.mul(recommendShop.getDistance(), 0.001) + "km");
                    }else if (recommendShop.getDistance()>50000) {
                        recommendShop.setDistanceWithUnit("50+km");
                    }
                }else {
                    recommendShop.setDistanceWithUnit(null);
                }
            }
        }

        return recommendShops;
    }

    @Override
    public List<DishShopDTO> getAllDishShop(DishShopDTO shop) {
        List<DishShopDTO> dishShopDTOS = dishMapperExtra.selectDishShop(shop);
        if (dishShopDTOS!=null && dishShopDTOS.size()!=0){
                for (DishShopDTO dishShopDTO : dishShopDTOS){
                    if (shop.getLatitude()!=null && shop.getLongitude()!=null && dishShopDTO.getLatitude() != null && dishShopDTO.getLongitude() != null) {
                        dishShopDTO.setDistance(DistanceMeter.InputDistance(shop.getLongitude(),shop.getLatitude(),dishShopDTO.getLongitude(),dishShopDTO.getLatitude()));
                    }else {
                        dishShopDTO.setDistance(null);
                    }
                    if (dishShopDTO.getSaleCount()!=null && dishShopDTO.getDistance()!=null){
                        //对排序值进行处理，目前为距离：销量 = 3：7
                        dishShopDTO.setSortValue(dishShopDTO.getDistance()*0.3+dishShopDTO.getSaleCount()*0.7);
                    }else {
                        dishShopDTO.setSortValue(0.0);
                    }
                    //换算距离单位
                    if(dishShopDTO.getDistance()!=null) {
                        if (dishShopDTO.getDistance() < 1000) {
                            dishShopDTO.setDistanceWithUnit(dishShopDTO.getDistance().toString() + "m");
                        } else if(dishShopDTO.getDistance()<50000 &&dishShopDTO.getDistance()>1000){
                            dishShopDTO.setDistanceWithUnit(myOrderService.mul(dishShopDTO.getDistance(), 0.001) + "km");
                        }else if (dishShopDTO.getDistance()>50000) {
                            dishShopDTO.setDistanceWithUnit("50+km");
                        }
                    }else {
                        dishShopDTO.setDistanceWithUnit(null);
                    }
                }
                if (shop.getSortType()==0) {
                    quickSortBySortValue(dishShopDTOS,0,dishShopDTOS.size()-1);  //对结果按排序值进行快速排序
                } else if (shop.getSortType()==1) {
                    if (shop.getLongitude()!=null && shop.getLatitude()!=null) {
                        quickSortByDistance(dishShopDTOS,0,dishShopDTOS.size()-1);
                    }
                }
        }

        return dishShopDTOS;
    }

    @Override
    public List<SearchDishShopDTO> searchDishShop(SearchInputDTO searchInputDTO) {
        List<SearchDishShopDTO> searchDishShopDTOS = dishMapperExtra.selectDishShopByName(searchInputDTO);
        for (SearchDishShopDTO searchDishShopDTO : searchDishShopDTOS){
            searchInputDTO.setShopId(searchDishShopDTO.getShopId());
            searchDishShopDTO.setSearchDishes(dishMapperExtra.selectDishByName(searchInputDTO));
        }
        return searchDishShopDTOS;
    }

    public List<DishShopDTO> quickSortBySortValue(List<DishShopDTO> dishShopDTOS,int left, int right) {
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
            quickSortBySortValue(dishShopDTOS,left, i-1);//继续处理左边的，这里是一个递归的过程
            quickSortBySortValue(dishShopDTOS,i+1, right);//继续处理右边的 ，这里是一个递归的过程
        }
       return dishShopDTOS;
    }

    public List<DishShopDTO> quickSortByDistance(List<DishShopDTO> dishShopDTOS,int left, int right) {
        int i, j;
        Double temp;
        DishShopDTO dishShopDTO=new DishShopDTO();
        if(left > right)
            return null;
        temp = dishShopDTOS.get(left).getDistance(); //temp中存的就是基准数
        i = left;
        j = right;
        while(i != j) { //顺序很重要，要先从右边开始找
            while(dishShopDTOS.get(j).getDistance() >= temp && i < j)
                j--;
            while(dishShopDTOS.get(i).getDistance() <= temp && i < j)//再找右边的
                i++;
            if(i < j)//交换两个数在数组中的位置
            {
                dishShopDTO = dishShopDTOS.get(i);
                dishShopDTOS.set(i,dishShopDTOS.get(j)) ;
                dishShopDTOS.set(j,dishShopDTO) ;
            }
        }
        //最终将基准数归位
        if (dishShopDTO.getDistance()!=null){  //如果上方进行了排序
            dishShopDTOS.set(left,dishShopDTOS.get(i)) ;
            dishShopDTOS.set(i,dishShopDTO) ;
            quickSortByDistance(dishShopDTOS,left, i-1);//继续处理左边的，这里是一个递归的过程
            quickSortByDistance(dishShopDTOS,i+1, right);//继续处理右边的 ，这里是一个递归的过程
        }
        return dishShopDTOS;
    }

}
