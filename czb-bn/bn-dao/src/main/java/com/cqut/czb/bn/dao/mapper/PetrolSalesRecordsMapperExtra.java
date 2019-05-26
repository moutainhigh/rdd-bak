package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.AppPetrolSaleInfoOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolDeliveryRecords;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolSalesRecordsMapperExtra {
    List<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO);

    List<Petrol> getGTSoldPetrolForUser(String userId);

    List<AppPetrolSaleInfoOutputDTO> getPhysicalCardsForUser(@Param("userId") String userId, @Param("petrolKind")String petrolKind);

    int insert(PetrolSalesRecords record);

    List<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO);

    int recharge(@Param("recordId") String recordId);

    PetrolSalesRecords selectPetrolSalesRecords(PetrolInputDTO petrolInputDTO);

    /**
     * 通过合同id查出相应的卡
     * @param contractId
     * @return
     */
    PetrolSalesRecords selectPetrolByContractId(@Param("contractId") String contractId);

    /**
     * 通过订单号查询购买信息
     * @param orgId
     * @return
     */
    PetrolSalesRecords selectInfoByOrgId(@Param("orgId") String orgId);

    /**
     * 更改购买信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PetrolSalesRecords record);
}