package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ShopManagementService;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingPartnerService;
import com.cqut.czb.bn.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectChargingPartnerServiceImpl implements DirectChargingPartnerService {

    @Autowired
    DirectChargingOrderMapperExtra directChargingOrderMapperExtra;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public PageInfo<DirectChargingOrderDto> getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize());
        List<DirectChargingOrderDto> directChargingOrderDtoList = directChargingOrderMapperExtra.getDirectChargingPartnerList(directChargingOrderDto);
        return new PageInfo<>(directChargingOrderDtoList);
    }

    @Override
    public PageInfo getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize());
        List<DirectChargingOrderDto> directChargingOrderDtoList = directChargingOrderMapperExtra.getDirectChargingPartnerOrder(directChargingOrderDto);
        return new PageInfo<>(directChargingOrderDtoList);
    }

    @Override
    public JSONResult getTotalRechargeAmount() {
        return new JSONResult(directChargingOrderMapperExtra.getTotalRechargeAmount());
    }

    @Override
    public JSONResult getUserTotalRechargeAmount(User user) {
        return new JSONResult(directChargingOrderMapperExtra.getUserTotalRechargeAmount(user.getUserId()));
    }

    @Override
    public JSONResult deleteDirectChargingPartnerOrder(User user) {
        if (directChargingOrderMapperExtra.deleteDirectChargingPartnerOrder(user) == 1) {
            // 更新 reids 中 用户角色
            UserDTO userDTO = userMapperExtra.findUserDTOById(user.getUserId());
            if(redisUtil.hasKey(userDTO.getUserAccount())) {
                redisUtil.remove(userDTO.getUserAccount());
                redisUtil.put(userDTO.getUserAccount(), userDTO);
            }
            return new JSONResult("删除成功");
        } else {
            return new JSONResult("删除失败");
        }
    }


}
