package com.cqut.czb.bn.service.impl.petrolSaleConfigService;

import com.cqut.czb.bn.dao.mapper.PetrolSaleConfigMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSaleConfigMapperExtra;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.petrolSaleConfig.PetrolSaleConfigOutputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import com.cqut.czb.bn.service.petrolSaleConfigService.IPetrolSaleConfigService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PetrolSaleConfigServiceImpl implements IPetrolSaleConfigService {

    @Autowired
    PetrolSaleConfigMapperExtra petrolSaleConfigMapperExtra;

    @Autowired
    PetrolSaleConfigMapper petrolSaleConfigMapper;
    @Override
    public boolean updatePetrolSaleConfig(PetrolSaleConfig petrolSaleConfig) {
        petrolSaleConfig.setUpdateAt(new Date());
        return petrolSaleConfigMapper.updateByPrimaryKeySelective(petrolSaleConfig) > 0;
    }

    @Override
    public List<PetrolSaleConfigOutputDTO> getPetrolSaleConfigs() {
        List<PetrolSaleConfigOutputDTO> list = petrolSaleConfigMapperExtra.getPetrolSaleConfigs();
        return list;
    }
}
