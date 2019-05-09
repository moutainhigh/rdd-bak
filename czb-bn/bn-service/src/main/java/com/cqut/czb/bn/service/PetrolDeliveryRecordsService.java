package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.entity.PetrolDeliveryRecords;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public interface PetrolDeliveryRecordsService {

    PageInfo<PetrolDeliveryDTO> selectPetrolDelivery(DeliveryInput deliveryInput, PageDTO pageDTO);

    Boolean updatePetrolDelivery(DeliveryInput deliveryInput);

    String selectLogistics(DeliveryInput deliveryInput);

    Boolean receivePetrolDelivery(String ids);

    Workbook exportDeliveryRecords(DeliveryInput deliveryInput) throws Exception;

    int ImportDeliveryRecords(MultipartFile file) throws Exception;

    String selectLogistics();
}
