package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardPetrolMapperExtra;
import com.cqut.czb.bn.service.withoutCard.WithoutCardPetrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithoutCardPetrolServiceImpl implements WithoutCardPetrolService {
    @Autowired
    WithoutCardPetrolMapperExtra withoutCardPetrolMapperExtra;
}
