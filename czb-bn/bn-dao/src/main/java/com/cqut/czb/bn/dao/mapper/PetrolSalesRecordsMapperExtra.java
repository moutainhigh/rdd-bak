package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetChargeOrderInputDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.AppPetrolSaleInfoOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleTotal;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolSalesRecordsMapperExtra {
    List<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO);

    SaleTotal getTotal(String userId);

    List<Petrol> getGTSoldPetrolForUser(String userId);

    List<AppPetrolSaleInfoOutputDTO> getPhysicalCardsForUser(@Param("userId") String userId, @Param("petrolKind") String petrolKind);

    int insert(PetrolSalesRecords record);

    List<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO);

    int recharge(@Param("recordId") String recordId);

    PetrolSalesRecords selectPetrolSalesRecords(PetrolInputDTO petrolInputDTO);

    /**
     * 通过合同id查出相应的卡
     *
     * @param contractId
     * @return
     */
    PetrolSalesRecords selectPetrolByContractId(@Param("contractId") String contractId);

    /**
     * 通过订单号查询购买信息
     *
     * @param orgId
     * @return
     */
    PetrolSalesRecords selectInfoByOrgId(@Param("orgId") String orgId);

    /**
     * 更改购买信息
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PetrolSalesRecords record);

    String sumOfPetrolSaleMoney(GetPetrolSaleInfoInputDTO infoInputDTO);

    /**
     * 后台管理系统修改油卡卡号
     *
     * @param inputDTO
     * @return
     */
    int updatePetrolNum(PetrolRechargeInputDTO inputDTO);

    /**
     * app修改油卡卡号，要对userId进行验证
     *
     * @param inputDTO
     * @return
     */
    int appUpdatePetrolNum(PetrolRechargeInputDTO inputDTO);

    /**
     * 通过userId与petrolNum，获取receiver姓名
     *
     * @param inputDTO
     * @return
     */
    List<String> getReceiver(PetrolRechargeInputDTO inputDTO);

    /**
     * 判断油卡卡号是否重复
     *
     * @param petrolNum
     * @return
     */
    List<Petrol> judgePetrolNumRepeat(@Param("petrolNum") String petrolNum);

    List<GetUnChargeOrderDTO> getUnChargeOrders();

    int inputChargeOrders(@Param("list") List<GetChargeOrderInputDTO> list);

    int inputChargeOrder(GetChargeOrderInputDTO getChargeOrderInputDTO);
}