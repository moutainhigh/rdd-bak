package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WxSettelRcordMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class WxSettelRcordServiceImpl implements WxSettelRcordService{
    @Autowired
    WxSettelRcordMapperExtra wxSettelRcordMapperExtra;

    @Override
    public JSONResult getSettleRcord(WxSettleRcordDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<WxSettleRcordDTO> withdrawList = wxSettelRcordMapperExtra.selectSettleRcord(pageDTO);
        PageInfo<WxSettleRcordDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult settleRecord(WxSettleRcordDTO wxSettleRcordDTO) {
        if(wxSettelRcordMapperExtra.settleRecord(wxSettleRcordDTO))
            return new JSONResult("结算成功", 200, true);
        else
            return new JSONResult("结算失败", 500, false);
    }

    @Override
    public JSONResult deleteSettleRecord(WxSettleRcordDTO wxSettleRcordDTO) {
        if(wxSettelRcordMapperExtra.deleteSettleRecord(wxSettleRcordDTO) && wxSettelRcordMapperExtra.updateSettleRecord(wxSettleRcordDTO))
            return new JSONResult("删除成功", 200, true);
        else
            return new JSONResult("删除失败", 500, false);
    }

    @Override
    public Workbook exportOrderRecords(WxSettleRcordDTO pageDTO) throws Exception {
        List<WxOrderWithdrawDTO> wxOrderWithdrawDTOList = wxSettelRcordMapperExtra.selectByPrimaryKey(pageDTO);
        if(wxOrderWithdrawDTOList==null||wxOrderWithdrawDTOList.size()==0){
            return getWorkBook(null);
        }
        return getWorkBook(wxOrderWithdrawDTOList);
    }

    public Workbook getWorkBook(List<WxOrderWithdrawDTO> wxOrderWithdrawDTOS)throws Exception{
        String[] petrolDeliveryRecordHeader = SystemConstants.OREDER_SETTEL_EXCEL_HEAD;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(wxOrderWithdrawDTOS.size());
        } catch (Exception e) {
            e.printStackTrace();
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
        for (int i = 0 ; i<wxOrderWithdrawDTOS.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getShopName());

//            CellRangeAddress region1 = new CellRangeAddress(0, 1, (short) 0, (short) 12);
//            //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
//            sheet.addMergedRegion(region1);
//            SXSSFRow headTitle = (SXSSFRow) sheet.createRow(0);
//            headTitle.createCell(0).setCellValue("重点工程项目计划表");

            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getOrderId());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getUserName());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getPhone());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getCommodityName());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getCostPrice());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getThirdOrder());

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getOrderState()==0)
                row.createCell(count++).setCellValue("未使用");
            else if(wxOrderWithdrawDTOS.get(i).getOrderState()==1)
                row.createCell(count++).setCellValue("已使用");
            else
                row.createCell(count++).setCellValue("");
            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getIsHaveSettled()==0)
                row.createCell(count++).setCellValue("未结算");
            else if(wxOrderWithdrawDTOS.get(i).getIsHaveSettled()==1)
                row.createCell(count++).setCellValue("已结算");
            else
                row.createCell(count++).setCellValue("");

            row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getAttrInfo());

        }
        return workbook;
    }
}
