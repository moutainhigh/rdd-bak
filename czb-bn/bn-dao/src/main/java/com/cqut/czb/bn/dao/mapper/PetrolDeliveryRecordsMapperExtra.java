package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryMessageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.entity.PetrolDeliveryRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolDeliveryRecordsMapperExtra {

    int deleteByPrimaryKey(String recordId);

    int insert(PetrolDeliveryRecords record);

    int insertSelective(PetrolDeliveryRecords record);

    List<PetrolDeliveryDTO> selectByPrimaryKey(DeliveryInput deliveryInput);

    int updateByPrimaryKeySelective(PetrolDeliveryRecords record);

    DeliveryMessageDTO selectDeliveryMessage(String recordId);

    int updateByPrimaryKey(DeliveryInput deliveryInput);

    int updateReceivePetrolDeliveryState( String[] array);

    int updateImportRecords(List<PetrolDeliveryDTO> list);

    PetrolDeliveryDTO getDeliveryInfo(@Param("userId")String userId,@Param("petrolKind")String petrolKind);

    DeliveryMessageDTO selectDeliveryMessageByPetrolNum(String petrolNum);
}
