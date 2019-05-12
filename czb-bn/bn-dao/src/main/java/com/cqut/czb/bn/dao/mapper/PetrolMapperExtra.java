package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;

import java.util.List;

public interface PetrolMapperExtra {
    List<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO);
    int insertPetrolList(List<Petrol> list);

    int deleteByPrimaryKey(String petrolId);

    int insert(Petrol record);

    int insertSelective(Petrol record);

    Petrol selectByPrimaryKey(String petrolId);

    int updateByPrimaryKeySelective(Petrol record);

    int updateByPrimaryKey(Petrol record);

    /**
     * 获取未售出的油卡信息
     * @return
     */
    List<PetrolZoneDTO> selectPetrolZone();

    List<Petrol> selectPetrol();

    List<PetrolInfoDTO> selectPetrolInfoDTO();

    /**
     * 通过用户id获取油卡
     */
    Petrol selectMyPetrol(String userId);

    /**
     *查出不同类型的油卡
     * @param petrolInputDTO
     * @return
     */
    Petrol selectDifferentPetrol(PetrolInputDTO petrolInputDTO);

}