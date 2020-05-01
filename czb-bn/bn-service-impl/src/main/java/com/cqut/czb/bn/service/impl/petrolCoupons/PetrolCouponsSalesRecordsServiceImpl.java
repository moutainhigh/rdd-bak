package com.cqut.czb.bn.service.impl.petrolCoupons;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapper;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import com.cqut.czb.bn.service.petrolCoupons.PetrolCouponsSalesRecordsService;
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
public class PetrolCouponsSalesRecordsServiceImpl implements PetrolCouponsSalesRecordsService {

    @Autowired
    PetrolCouponsSalesRecordsMapper petrolCouponsSalesRecordsMapper;

    @Override
    public PageInfo<PetrolCouponsSalesRecords> selectPetrolCouponsSalesRecords(GetPetrolSaleInfoInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize());
        List<PetrolCouponsSalesRecords> list = petrolCouponsSalesRecordsMapper.selectPetrolCouponsSalesRecords(inputDTO);
        return new PageInfo<>(list);
    }

    @Override
    public String getPetrolCouponsSaleMoneyCount(GetPetrolSaleInfoInputDTO infoInputDTO) {
        return petrolCouponsSalesRecordsMapper.getPetrolCouponsSaleMoneyCount(infoInputDTO);
    }

    @Override
    public Workbook exportCouponsRecords(GetPetrolSaleInfoInputDTO inputDTO) throws Exception {
        List<PetrolCouponsSalesRecords> list = petrolCouponsSalesRecordsMapper.selectPetrolCouponsSalesRecords(inputDTO);
        return getSaleWorkBook(list, inputDTO);
    }

    private Workbook getSaleWorkBook(List<PetrolCouponsSalesRecords> list, GetPetrolSaleInfoInputDTO inputDTO) throws Exception {
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
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            row.createCell(count++).setCellValue(list.get(i).getOrderId());
            row.createCell(count++).setCellValue(list.get(i).getThirdOrderId());
            row.createCell(count++).setCellValue("中石化加油卡");
            row.createCell(count++).setCellValue(list.get(i).getPetrolDenomination());
            row.createCell(count++).setCellValue(list.get(i).getPetrolPrice());
            row.createCell(count++).setCellValue(list.get(i).getOwner());
            if ("1".equals(list.get(i).getPaymentMethod())) {
                row.createCell(count++).setCellValue("支付宝");
            }else if ("2".equals(list.get(i).getPaymentMethod())){
                row.createCell(count++).setCellValue("微信");
            }
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getTransactionTime()));
            row.createCell(count++).setCellValue(list.get(i).getArea());
            if(list.get(i).getPetrolNum().length() < 15){
                row.createCell(count++).setCellValue("首充");
            }else {
                row.createCell(count++).setCellValue("续充");
            }
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
