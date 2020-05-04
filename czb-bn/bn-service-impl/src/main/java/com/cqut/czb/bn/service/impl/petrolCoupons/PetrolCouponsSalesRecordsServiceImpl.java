package com.cqut.czb.bn.service.impl.petrolCoupons;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesMapper;
import com.cqut.czb.bn.entity.dto.petrolCoupons.PetrolCouponsSales;
import com.cqut.czb.bn.service.petrolCoupons.PetrolCouponsSalesRecordsService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PetrolCouponsSalesRecordsServiceImpl implements PetrolCouponsSalesRecordsService {

    @Autowired
    PetrolCouponsSalesMapper petrolCouponsSalesRecordsMapper;

    @Override
    public PageInfo<PetrolCouponsSales> selectPetrolCouponsSalesRecords(PetrolCouponsSales inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize());
        List<PetrolCouponsSales> list = petrolCouponsSalesRecordsMapper.selectPetrolCouponsSalesRecords(inputDTO);
        return new PageInfo<>(list);
    }

    @Override
    public String getPetrolCouponsSaleMoneyCount(PetrolCouponsSales infoInputDTO) {
        return petrolCouponsSalesRecordsMapper.getPetrolCouponsSaleMoneyCount(infoInputDTO);
    }

    @Override
    public Workbook exportCouponsRecords(PetrolCouponsSales inputDTO) throws Exception {
        List<PetrolCouponsSales> list = petrolCouponsSalesRecordsMapper.selectPetrolCouponsSalesRecords(inputDTO);
        return getSaleWorkBook(list, inputDTO);
    }

    private Workbook getSaleWorkBook(List<PetrolCouponsSales> list, PetrolCouponsSales inputDTO) throws Exception {
        String[] rechargeHead = SystemConstants.PETROL_COUPONS_SALE_EXCEL_HEAD;
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出充值记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无销售记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出充值记录");//创建工作表
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
            row.createCell(count++).setCellValue(list.get(i).getPetrolId());
            row.createCell(count++).setCellValue(list.get(i).getOrderId());
            row.createCell(count++).setCellValue(list.get(i).getToRddThirdOrderId());
            row.createCell(count++).setCellValue(list.get(i).getUserAccount());
            if ("1".equals(list.get(i).getPaymentMethod())) {
                row.createCell(count++).setCellValue("支付宝");
            }else if ("2".equals(list.get(i).getPaymentMethod())) {
                row.createCell(count++).setCellValue("微信");
            }
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getToRddTransactionTime()));
            if(list.get(i).getUnitPrice() == null){
                row.createCell(count++).setCellValue("0.00");
            }
            else {
                row.createCell(count++).setCellValue(list.get(i).getUnitPrice());
            }
            row.createCell(count++).setCellValue(list.get(i).getReturnOrderId());
            row.createCell(count++).setCellValue(list.get(i).getTradingId());
            row.createCell(count++).setCellValue(list.get(i).getArea());
        }
        int index = 0;
        row = sheet.createRow(list.size());
        row.createCell(index++).setCellValue("销售总单数");
        row.createCell(index++).setCellValue("销售总金额");
        index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue(list.size()-1);
        row.createCell(index++).setCellValue(petrolCouponsSalesRecordsMapper.getPetrolCouponsSaleMoneyCount(inputDTO) + "元");
        return workbook;
    }
}
