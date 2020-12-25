package com.cqut.czb.bn.service.impl.appWithoutCardServiceImpl;

import com.cqut.czb.bn.dao.mapper.appWithoutCard.AppWithoutCardMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.Commodity.UserCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.petrolInfoDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.withoutCard.CommodityWithoutCard;
import com.cqut.czb.bn.entity.entity.withoutCard.WithoutCardAreaConfig;
import com.cqut.czb.bn.service.appWithoutCard.AppWithoutCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppWithoutCardServiceImpl implements AppWithoutCardService {

    @Autowired
    AppWithoutCardMapperExtra appWithoutCardMapperExtra;

    @Override
    public List<PetrolZoneDTO> getGoods(User user,String area) {
        List<WithoutCardAreaConfig> withoutCardAreaConfigs = appWithoutCardMapperExtra.selectConfig(area);
        List<CommodityWithoutCard> list = appWithoutCardMapperExtra.getGoods();
        List<petrolInfoDTO> petrolInfoDTOS=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            petrolInfoDTO petrolInfoDTO = new petrolInfoDTO();
            petrolInfoDTO.setPetrolDenomination(list.get(i).getCommodityDenomination());
            petrolInfoDTO.setPetrolPrice(list.get(i).getCommodityPrice());
            petrolInfoDTO.setVipPrice(list.get(i).getVipDiscount());
            petrolInfoDTO.setDiscount(list.get(i).getDiscount());
            petrolInfoDTOS.add(petrolInfoDTO);
        }
        List<PetrolZoneDTO> petrolZoneDTOList =new ArrayList<>();
        for (int i=0;i<withoutCardAreaConfigs.size();i++){
            PetrolZoneDTO petrolZoneDTO = new PetrolZoneDTO();
            petrolZoneDTO.setPetrolKind(withoutCardAreaConfigs.get(i).getPetrolType());
            if (withoutCardAreaConfigs.get(i).getPetrolType()==1){
                petrolZoneDTO.setPetrolName("中石油");
            }
            petrolZoneDTO.setPetrolPriceInfo(petrolInfoDTOS);
            petrolZoneDTO.setArea(withoutCardAreaConfigs.get(i).getArea());
            petrolZoneDTOList.add(petrolZoneDTO);
        }
        return petrolZoneDTOList;
    }

    public List<UserCommodityOrderDTO> getCommodityOrderList(String userId, Integer state) {
        List<UserCommodityOrderDTO> a = appWithoutCardMapperExtra.getCommodityOrderList(userId,state);
        return a;
    }
}
