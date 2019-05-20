package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PlatformIncomeRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import com.cqut.czb.bn.service.PlatformIncomeRecordsService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;

public class PlatformIncomeRecordServiceImpl implements PlatformIncomeRecordsService{

    @Autowired
    private PlatformIncomeRecordsMapperExtra platformIncomeRecordsMapperExtra;

    @Override
    public PageInfo<PlatformIncomeRecordsDTO> getReceiveRecords(PlatformIncomeRecordsDTO records, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = platformIncomeRecordsMapperExtra.selectByPrimaryKey(records);
        return new PageInfo<>(platformIncomeRecordsDTOS);
    }

    @Override
    public Workbook exportRecords(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) {
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = platformIncomeRecordsMapperExtra.selectByPrimaryKey(platformIncomeRecordsDTO);

        return null;
    }
        //导出生成execl表
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
//                row.createCell(count++).setCellType(CellType.STRING);
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getReceiver());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(petrolDeliveryDTOS.get(i).getCreateAt()));
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getContactNumber());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getProvince()+petrolDeliveryDTOS.get(i).getCity()+petrolDeliveryDTOS.get(i).getArea());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDetail());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(""+petrolDeliveryDTOS.get(i).getDeliveryNum());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDeliveryCompany());
        }
        return workbook;
    }

}
