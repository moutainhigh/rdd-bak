package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.dao.mapper.RentCarMapper;
import com.cqut.czb.bn.entity.dto.rentCar.personIncome;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class rentCarServiceImpl implements RentCarService {
    @Autowired
    private RentCarMapper rentCarMapper;

    @Override
    public personIncome getPersonIncome(){
        // TODO 用户id需要从redis获取
        return rentCarMapper.getPersonIncome("1");
    }
}
