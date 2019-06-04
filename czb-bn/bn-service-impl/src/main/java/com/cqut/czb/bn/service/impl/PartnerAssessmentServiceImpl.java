package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.IndicatorRecordMapperExtra;
import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.IndicatorRecord;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
@Service
public class PartnerAssessmentServiceImpl implements com.cqut.czb.bn.service.IndicatorRecordService {
    @Autowired
    IndicatorRecordMapperExtra indicatorRecordMapperExtra;

    @Override
    public PageInfo<IndicatorRecordDTO> getIndicatorRecordList(IndicatorRecordDTO indicatorRecordDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IndicatorRecordDTO> list = indicatorRecordMapperExtra.getIndicatorRecordList(indicatorRecordDTO);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean ConfirmComplianceByState(String recordIds) {
        if(recordIds != null && recordIds != ""){
            return indicatorRecordMapperExtra.ConfirmComplianceByState(recordIds) > 0;
        }else{
            return false;
        }
    }

    @Override
    public String exportExaminationRecords(HttpServletResponse response, HttpServletRequest request,IndicatorRecordDTO input) {
        List<IndicatorRecordDTO> list = indicatorRecordMapperExtra.getIndicatorRecordList(input);
        if(list.size() == 0 || list == null){
            return "所选时间没有记录";
        }else{
            try{
                Workbook workbook = getPartnerAssessmentRecordWorkBook(list);
                //设置对客户端请求的编码格式
                request.setCharacterEncoding("utf-8");
                //指定服务器返回给浏览器的编码格式
                response.setCharacterEncoding("utf-8");
                //点击下载之后出现下载对话框
                response.setContentType("application/x-download");
                String fileName = null;
                fileName = "合伙人考核记录.xlsx";
                //System.out.println(fileName);
                //将中文转换为16进制
                fileName = URLEncoder.encode(fileName,"utf-8");
                //确保浏览器弹出对应文件的对话框
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                //System.out.println("====="+out.toString());
                out.close();
                return "导出成功";
            }catch (IOException e){
                return "导出Excel数据失败，请稍后再试";
            }catch (Exception e){
                return "Excel数据量过大，请缩短导出文件的时间间隔";
            }
        }
    }

    public Workbook getPartnerAssessmentRecordWorkBook(List<IndicatorRecordDTO> list){
        String[] partnerAssessmentExcelHead = SystemConstants.PARTNER_ASSESSMENT_EXCEL_HEAD;
        Workbook workbook = new SXSSFWorkbook(list.size());
        Sheet sheet = workbook.createSheet("导出合伙人考核记录");
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < partnerAssessmentExcelHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(partnerAssessmentExcelHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);

            row.createCell(count++).setCellValue(list.get(i).getUserAccount());

            if(list.get(i).getPartner() == 2){
                row.createCell(count++).setCellValue("普通合伙人");
            }else if (list.get(i).getPartner() == 1){
                row.createCell(count++).setCellValue("事业合伙人");
            }else{
                count++;
            }

            if(list.get(i).getTargetPromotionNumber() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getTargetPromotionNumber().toString());
            }
            if(list.get(i).getActualPromotionNumber() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getActualPromotionNumber().toString());
            }
            if(list.get(i).getTargetNewConsumer() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getTargetNewConsumer().toString());
            }
            if(list.get(i).getActualNewConsumer() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getActualNewConsumer().toString());
            }

            if(list.get(i).getState() == 0){
                row.createCell(count++).setCellValue("未达标");
            }else if (list.get(i).getState() == 1){
                row.createCell(count++).setCellValue("已达标");
            }else{
                count++;
            }

            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getIndicatorBeginTime()));
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getIndicatorEndTime()));
        }
        return workbook;
    }
}
