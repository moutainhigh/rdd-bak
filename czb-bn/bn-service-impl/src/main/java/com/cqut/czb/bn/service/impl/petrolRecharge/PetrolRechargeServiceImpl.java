package com.cqut.czb.bn.service.impl.petrolRecharge;

import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.service.petrolRecharge.IPetrolRechargeService;
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
public class PetrolRechargeServiceImpl implements IPetrolRechargeService {

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Override
    public PageInfo<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(),true);
      List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolRechargeList(inputDTO);
       return new PageInfo<>(list);
    }

    @Override
    public boolean recharge(PetrolRechargeInputDTO record) {
        boolean isRecharge = petrolSalesRecordsMapperExtra.recharge(record.getRecordId())>0;
        if(isRecharge && record.getUpdatePetrolNum() != null && record.getUpdatePetrolNum() != ""){
            petrolSalesRecordsMapperExtra.updatePetrolNum(record);
        }

        return isRecharge;
    }

    @Override
    public Workbook exportRechargeRecords(PetrolRechargeInputDTO inputDTO) throws Exception {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(),true);
        List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolRechargeList(inputDTO);
        return getWorkBook(list);
    }

    private Workbook getWorkBook(List<PetrolRechargeOutputDTO> list)throws Exception{
        String[] rechargeHead = SystemConstants.PETROL_RECHARGE_EXCEL_HEAD;
        Workbook workbook = null;
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
            sheet.setColumnWidth(i, (short) 5000); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getUserName());
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            if ("1".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石油");
            }else if ("2".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石化");
            }else{
                row.createCell(count++).setCellValue("其他");
            }
            row.createCell(count++).setCellValue(list.get(i).getPetrolDenomination());
            row.createCell(count++).setCellValue(list.get(i).getPetrolPrice());
            row.createCell(count++).setCellValue(list.get(i).getUserPhone());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getPurchaseTime()));
            if ("0".equals(list.get(i).getBuyWay()))
            row.createCell(count++).setCellValue("支付宝");
        }
        return workbook;
    }
}
