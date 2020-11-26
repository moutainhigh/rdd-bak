package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardSellManagementMapperExtra;
import com.cqut.czb.bn.entity.dto.withoutCard.PetrolSalesWithoutDto;
import com.cqut.czb.bn.service.withoutCard.WithoutCardSellManagementService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class WithoutCardSellManagementServiceImpl implements WithoutCardSellManagementService {
    @Autowired
    WithoutCardSellManagementMapperExtra withoutCardSellManagementMapperExtra;

    @Override
    public PageInfo<PetrolSalesWithoutDto> listPetrolSellManagement(PetrolSalesWithoutDto petrolSalesWithoutDto) {
        PageHelper.startPage(petrolSalesWithoutDto.getCurrentPage(),petrolSalesWithoutDto.getPageSize());
        return new PageInfo<>(withoutCardSellManagementMapperExtra.listPetrolSellManagement(petrolSalesWithoutDto));
    }

    @Override
    public String getPetrolSellManagementTotal(PetrolSalesWithoutDto petrolSalesWithoutDto) {
        return withoutCardSellManagementMapperExtra.getPetrolSellManagementTotal(petrolSalesWithoutDto);
    }

    @Override
    public Workbook exportSaleRecords(PetrolSalesWithoutDto petrolSalesWithoutDto) throws Exception {
        List<PetrolSalesWithoutDto> list = withoutCardSellManagementMapperExtra.listPetrolSellManagement(petrolSalesWithoutDto);
        return getSaleWorkBook(list, petrolSalesWithoutDto);
    }

    private Workbook getSaleWorkBook(List<PetrolSalesWithoutDto> list,PetrolSalesWithoutDto petrolSalesWithoutDto) throws Exception {
        String[] rechargeHead = SystemConstants.WITHOUT_CARD_PETROL_SALE_EXCEL_HEAD;
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出消费记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无消费记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出消费记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < rechargeHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rechargeHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            row.createCell(count++).setCellValue(list.get(i).getThirdOrderId());
            row.createCell(count++).setCellValue(list.get(i).getUserAccount());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getTransactionTime()));
            row.createCell(count++).setCellValue(list.get(i).getCommodityTitle());
            BigDecimal bg = new BigDecimal(list.get(i).getTurnoverAmount());
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            row.createCell(count++).setCellValue(f1);
            row.createCell(count++).setCellValue(list.get(i).getTransactionArea());
        }
        return workbook;
    }
}
