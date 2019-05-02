package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.entity.PetrolDeliveryRecords;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public interface PetrolDeliveryRecordsService {

    PageInfo<PetrolDeliveryDTO> selectPetrolDelivery(DeliveryInput deliveryInput, PageDTO pageDTO);

    Boolean updatePetrolDelivery(DeliveryInput deliveryInput);

    Object selectLogistics(DeliveryInput deliveryInput);

    Boolean receivePetrolDelivery(String ids);

    Workbook exportDeliveryRecords(DeliveryInput deliveryInput) throws Exception;
}
