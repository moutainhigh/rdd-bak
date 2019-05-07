package com.cqut.czb.bn.service.impl.petrolRecharge;

import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.service.petrolRecharge.IPetrolRechargeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PetrolRechargeServiceImpl implements IPetrolRechargeService {

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Override
    public PageInfo<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(),true);
        List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolRechargeList(inputDTO);
        return new PageInfo<>(list);
    }
}
