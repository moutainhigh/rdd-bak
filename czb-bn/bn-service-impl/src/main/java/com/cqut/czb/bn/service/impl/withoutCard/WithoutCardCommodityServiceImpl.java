package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardCommodityMapperExtra;
import com.cqut.czb.bn.service.withoutCard.WithoutCardCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithoutCardCommodityServiceImpl implements WithoutCardCommodityService {
    @Autowired
    WithoutCardCommodityMapperExtra withoutCardCommodityMapperExtra;
}
