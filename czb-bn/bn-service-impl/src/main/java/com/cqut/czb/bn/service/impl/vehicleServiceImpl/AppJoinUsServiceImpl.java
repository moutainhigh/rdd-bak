package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapperExtra;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.ShopManagementDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.service.vehicleService.AppJoinUsService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class AppJoinUsServiceImpl implements AppJoinUsService {

    @Autowired
    CleanRiderMapperExtra cleanRiderMapperExtra;
    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Override
    public Boolean toBecomeRider(CleanRider rider, User user) {
        CleanRider isExist = cleanRiderMapperExtra.selectByPrimaryKey(user.getUserId());
        if (isExist!=null&&isExist.getRiderId()!=null&&!"".equals(isExist.getRiderId())){
            if (isExist.getAudit()==2){
                rider.setRiderId(isExist.getRiderId());
                rider.setAudit(0);
                rider.setUpdateAt(new Date());
                Boolean update = cleanRiderMapperExtra.updateByPrimaryKeySelective(rider)>0;
                return update;
            }
            return true;
        }else {
            rider.setAudit(0);
            rider.setCreateAt(new Date());
            rider.setUserId(user.getUserId());
            rider.setRiderId(StringUtil.createId());
            rider.setStatus((byte) 0);
            Boolean insert = cleanRiderMapperExtra.insert(rider);
            if (!insert){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean toBecomeShop(ShopDTO shopDTO,User user) {
        shopDTO.setShopId(user.getUserId());
        ShopDTO shop = shopMapperExtra.selectShop(shopDTO);
        if (shop!=null&&shop.getShopId()!=null&&shop.getShopId()!=""){
            if (shop.getAudit()==2){
                ShopManagementDTO updateShop = new ShopManagementDTO();
                updateShop.setAudit(0);
                updateShop.setShopId(shop.getShopId());
                Boolean update = shopMapperExtra.updateShopAudit(updateShop)>0;
                return update;
            }
            return true;
        }else {
            shopDTO.setShopId(StringUtil.createId());
            shopDTO.setUserId(user.getUserId());
            shopDTO.setAudit(0);
            int insert  = shopMapperExtra.insert(shopDTO);
            if (insert<=0){
                return false;
            }
        }
        return true;
    }
}
