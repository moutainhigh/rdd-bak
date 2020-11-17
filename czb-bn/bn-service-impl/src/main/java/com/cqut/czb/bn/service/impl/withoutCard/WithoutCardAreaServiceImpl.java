package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardAreaMapperExtra;
import com.cqut.czb.bn.service.withoutCard.WithoutCardAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithoutCardAreaServiceImpl implements WithoutCardAreaService {

    @Autowired
    WithoutCardAreaMapperExtra withoutCardAreaMapperExtra;


}
