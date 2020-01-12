package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolPriceDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolStock;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PetrolInfoDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.PetrolManagementInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import org.apache.ibatis.annotations.Param;

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
    List<PetrolZoneDTO> selectPetrolZone(@Param(value = "area") String area);

    List<Petrol> selectPetrol();

    List<PetrolInfoDTO> selectPetrolInfoDTO();

    /**
     * 通过用户id获取油卡
     */
    Petrol selectMyPetrol(Petrol petrol);

    /**
     *查出不同类型的油卡
     * @param petrolInputDTO
     * @return
     */
    Petrol selectDifferentPetrol(PetrolInputDTO petrolInputDTO);

    /**
     * 判断是否买过中石油
     * @param userId
     * @return
     */
    Integer isBuyPetrol(@Param("userId") String userId);

    int changePetrolState(@Param("ids")String[] id,@Param("state")String state);

    int saleAllPetrol();

    int saleSomePetrol(PetrolManagementInputDTO inputDTO);

    int notSaleAllPetrol();

    int notSaleSomePetrols(PetrolManagementInputDTO inputDTO);

    String sumOfPetrolMoney(GetPetrolListInputDTO getPetrolListInputDTO);

    List<PetrolPriceDTO> selectUserPetrol(@Param("userId") String userId, @Param("area") String area);

    Petrol selectPetrolByNum(String petrolNum);

    List<PetrolStock> selectPetrolStock(String area);

    Petrol selectPetrolByDeliveryRecordId(@Param("recordId")String recordId);
}