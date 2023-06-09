package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.petrolSaleConfig.PetrolSaleConfigOutputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolSaleConfigMapperExtra {
    int deleteByPrimaryKey(String petrolConfigId);

    int insert(PetrolSaleConfig record);

    int insertSelective(PetrolSaleConfig record);

    PetrolSaleConfig selectByPrimaryKey(String petrolConfigId);

    int updateByPrimaryKeySelective(PetrolSaleConfig record);

    int updateByPrimaryKey(PetrolSaleConfig record);

    List<PetrolSaleConfig> selectPetrolSaleConfig();

    List<PetrolSaleConfigOutputDTO> getPetrolSaleConfigs(@Param("area") String area);

    List<PetrolSaleConfig>  getAreaConfigByArea(@Param("area") String area);

    int updateAreaConfig(@Param("list") List<PetrolSaleConfig>  list);

    List<String> getAllArea();

}