package com.cqut.czb.bn.service.impl.petrolDeliveryRecords;

import com.cqut.czb.bn.dao.mapper.PetrolDeliveryRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.service.PetrolDeliveryRecordsService;
import com.cqut.czb.bn.service.impl.petrolManagement.ImportPetrol;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/** 油卡邮寄功能
 *
 */
@Service
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
        return (petrolDeliveryRecordsMapperExtra.updateReceivePetrolDeliveryState(id)>0);
    }

    @Override
    public Workbook exportDeliveryRecords(DeliveryInput deliveryInput) throws Exception {
        List<PetrolDeliveryDTO> petrolDeliveryDTOList = petrolDeliveryRecordsMapperExtra.selectByPrimaryKey(deliveryInput);
        System.out.println("yyyyyyyy"+petrolDeliveryDTOList.size());
        if(petrolDeliveryDTOList==null||petrolDeliveryDTOList.size()==0){
            return getWorkBook(null);
        }
        return getWorkBook(petrolDeliveryDTOList);
    }

    public Workbook getWorkBook(List<PetrolDeliveryDTO> petrolDeliveryDTOS)throws Exception{
        String[] petrolDeliveryRecordHeader = SystemConstants.PETROL_DELIVERY_RECORD_HEAD;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(petrolDeliveryDTOS.size());
        } catch (Exception e) {
               throw new Exception("Excel数据量过大，请缩短时间间隔");
           }
           Sheet sheet = workbook.createSheet("导出寄送记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
            for (int i = 0; i < petrolDeliveryRecordHeader.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(petrolDeliveryRecordHeader[i]);
                cell.setCellStyle(style);
                sheet.setColumnWidth(i, (short) 6000); // 设置列宽
            }
            for (int i = 0 ; i<petrolDeliveryDTOS.size(); i++){
                int count = 0;
                row = sheet.createRow(i+1);
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getPetrolNum());
                if (petrolDeliveryDTOS.get(i).getDeliveryState()==0)
                    row.createCell(count++).setCellValue("国通");
                else if(petrolDeliveryDTOS.get(i).getDeliveryState()==1)
                    row.createCell(count++).setCellValue("中石油");
                else if (petrolDeliveryDTOS.get(i).getDeliveryState()==2)
                    row.createCell(count++).setCellValue("中石化");
                if (petrolDeliveryDTOS.get(i).getDeliveryState()==0)
                row.createCell(count++).setCellValue("未寄送");
                else if(petrolDeliveryDTOS.get(i).getDeliveryState()==1)
                    row.createCell(count++).setCellValue("寄送中");
                else if (petrolDeliveryDTOS.get(i).getDeliveryState()==2)
                    row.createCell(count++).setCellValue("已收货");
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getReceiver());
                row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(petrolDeliveryDTOS.get(i).getCreateAt()));
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getContactNumber());
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getProvince()+petrolDeliveryDTOS.get(i).getCity()+petrolDeliveryDTOS.get(i).getArea());
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDetail());
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDeliveryNum());
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDeliveryCompany());
            }
            return workbook;
    }

    @Override
    public int ImportDeliveryRecords(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        List<PetrolDeliveryDTO> petrolDeliveryDTOList = null;
        Map<String, PetrolDeliveryDTO> petrolMap = new HashMap<>();
        petrolDeliveryDTOList = ImportPetrolDelivery.readExcel(file.getOriginalFilename(), inputStream);
        System.out.println("99999999"+petrolDeliveryDTOList.get(0).getDeliveryCompany());
        /**
         * 按petrolNum为key 做到去重复的效果
         */
        if (petrolDeliveryDTOList != null) {
            for (PetrolDeliveryDTO p : petrolDeliveryDTOList) {
                petrolMap.put(p.getPetrolNum(), p);
            }
        }
        List<PetrolDeliveryDTO> petrolListNoRepeat = new ArrayList<>();
        for(PetrolDeliveryDTO p:petrolMap.values()){
            petrolListNoRepeat.add(p);
        }
        int countForInsert = petrolDeliveryRecordsMapperExtra.updateImportRecords(petrolListNoRepeat);
//        System.out.println("countForInsert " + countForInsert);
        return countForInsert;
    }
}
