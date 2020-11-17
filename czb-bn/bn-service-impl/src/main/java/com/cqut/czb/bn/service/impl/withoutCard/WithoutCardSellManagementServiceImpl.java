package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardSellManagementMapperExtra;
import com.cqut.czb.bn.service.withoutCard.WithoutCardSellManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithoutCardSellManagementServiceImpl implements WithoutCardSellManagementService {
    @Autowired
    WithoutCardSellManagementMapperExtra withoutCardSellManagementMapperExtra;
}
