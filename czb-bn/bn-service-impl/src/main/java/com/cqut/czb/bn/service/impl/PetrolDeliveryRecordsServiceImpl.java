package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PetrolDeliveryRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.entity.PetrolDeliveryRecords;
import com.cqut.czb.bn.service.PetrolDeliveryRecordsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 油卡邮寄功能
 *
 */
@Transactional
public class PetrolDeliveryRecordsServiceImpl implements PetrolDeliveryRecordsService {


    @Autowired
    PetrolDeliveryRecordsMapperExtra petrolDeliveryRecordsMapperExtra;

    @Override
    public PageInfo<PetrolDeliveryDTO> selectPetrolDelivery(DeliveryInput deliveryInput, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PetrolDeliveryDTO > petrolDeliveryDTOList = petrolDeliveryRecordsMapperExtra.selectByPrimaryKey(deliveryInput);
        return new PageInfo<>(petrolDeliveryDTOList);
    }

    @Override
    public Boolean updatePetrolDelivery(DeliveryInput deliveryInput) {
        return (petrolDeliveryRecordsMapperExtra.updateByPrimaryKey(deliveryInput)>0);
    }

    @Override
    public Object selectLogistics(DeliveryInput deliveryInput) {
        return null;
    }

    @Override   //批量确认收货
    public Boolean receivePetrolDelivery(String ids) {
        String[] id = ids.split(",");
        return (petrolDeliveryRecordsMapperExtra.updatereceivePetrolDeliveryState(id)>0);
    }

    @Override
    public Workbook exportDeliveryRecords(DeliveryInput deliveryInput) throws Exception {
        List<PetrolDeliveryDTO> petrolDeliveryDTOList = petrolDeliveryRecordsMapperExtra.selectByPrimaryKey(deliveryInput);
        if(petrolDeliveryDTOList==null||petrolDeliveryDTOList.size()==0){
            return getWorkBook(null);
        }
        return getWorkBook(petrolDeliveryDTOList);
    }

    public Workbook getWorkBook(List<PetrolDeliveryDTO> petrolDeliveryDTOS)throws Exception{
        return null;
    }
}
