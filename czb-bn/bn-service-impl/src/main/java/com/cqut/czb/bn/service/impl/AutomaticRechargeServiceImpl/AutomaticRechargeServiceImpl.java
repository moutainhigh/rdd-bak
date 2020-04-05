package com.cqut.czb.bn.service.impl.AutomaticRechargeServiceImpl;

import com.cqut.czb.bn.dao.mapper.AutomaticRechargeMapperExtra;
import com.cqut.czb.bn.dao.mapper.autoRecharge.AutoRechargeRecordMapper;
import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.AutoRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.dto.automaticRecharge.SumAutoRecharge;
import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.automaticRechargeService.AutomaticRechargeService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("AutomaticRechargeService")
public class AutomaticRechargeServiceImpl implements AutomaticRechargeService {

    @Autowired
    AutomaticRechargeMapperExtra automaticRechargeMapperExtra;

    @Autowired
    AutoRechargeRecordMapper autoRechargeRecordMapper;

    @Override
    public JSONResult getAutoList(AutomaticRechargeDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<AutomaticRechargeDTO> withdrawList = automaticRechargeMapperExtra.getAutoList(pageDTO);
        PageInfo<AutomaticRechargeDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult deleteRecorder(String id) {
        int deleNum = automaticRechargeMapperExtra.deleteRecorder(id);
        if (deleNum != 0) {
            return new JSONResult("删除成功", 200);
        }
        return new JSONResult("删除失败", 400);
    }

    @Override
    public JSONResult showRecorder(String id) {
        AutomaticRechargeDTO automaticRechargeDTO = automaticRechargeMapperExtra.showRecorder(id);
        return new JSONResult("列表数据查询成功", 200, automaticRechargeDTO);
    }

    @Override
    public Workbook exportOrderRecords(AutomaticRechargeDTO pageDTO) throws Exception {
        List<AutomaticRechargeDTO> automaticRechargeDTOList = automaticRechargeMapperExtra.getAutoList(pageDTO);
        if (automaticRechargeDTOList==null || automaticRechargeDTOList.size()==0){
            return getWorkBook(null,null);
        }
        return getWorkBook(automaticRechargeDTOList,pageDTO);
    }

    private Workbook getWorkBook(List<AutomaticRechargeDTO> list,AutomaticRechargeDTO pageDTO) throws Exception {
        String[] automaticRechargeHeader = SystemConstants.AUTOMATIC_RECHARGR_EXCEL_HEAD;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出自动充值记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont() ;
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        style.setFont(font);
        for (int i = 0; i < automaticRechargeHeader.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(automaticRechargeHeader[i]);
            cell.setCellStyle(style);
            if (i == automaticRechargeHeader.length-1 || i == 6){// 设置列宽
                int index = i;
                sheet.setColumnWidth(index,(short)7500);
            }else {
                sheet.setColumnWidth(i,  (short)5000);
            }
        }
        for (int i = 0; i<list.size();i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getId());
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            row.createCell(count++).setCellValue(list.get(i).getUserAccount());
            if("平台用户".equals(list.get(i).getUserName())){
                row.createCell(count++).setCellValue(list.get(i).getUserName());
            }else {
                row.createCell(count++).setCellValue("非平台用户");
            }
            row.createCell(count++).setCellValue(list.get(i).getRechargeAmount());
            row.createCell(count++).setCellValue(list.get(i).getPrice());
            if (list.get(i).getOrderTime() == null){
                row.createCell(count++).setCellValue("");
            }else {
                row.createCell(count++).setCellValue(formateDate(list.get(i).getOrderTime()));
            }
            if (list.get(i).getRechargeTime() == null){
                row.createCell(count++).setCellValue("");
            }else {
                row.createCell(count++).setCellValue(formateDate(list.get(i).getRechargeTime()));
            }
            if (list.get(i).getStatus()==1){
                row.createCell(count++).setCellValue("执行成功");
            }else if (list.get(i).getStatus()==0){
                row.createCell(count++).setCellValue("执行失败");
            }else{
                row.createCell(count++).setCellValue(" ");
            }
            row.createCell(count++).setCellValue(list.get(i).getMessage());
        }

        SumAutoRecharge sumAutoRecharge1 = automaticRechargeMapperExtra.getSumData(pageDTO);
        SumAutoRecharge sumAutoRecharge2 = automaticRechargeMapperExtra.getSuccessPeople(pageDTO);
        Row row2 = sheet.createRow(list.size()+3);
        sheet.addMergedRegion(new CellRangeAddress(list.size()+3,list.size()+3,0,6));
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("今日明细");
        cell2.setCellStyle(style);
        Row row3 = sheet.createRow(list.size()+4);
        String[] sumAutoRechargeHeader = SystemConstants.SUM_AUTO_RECHARGE_EXCEL_HEAD;
        for (int i = 0; i < sumAutoRechargeHeader.length; i++) {
            Cell cell3 = row3.createCell(i);
            cell3.setCellValue(sumAutoRechargeHeader[i]);
            cell3.setCellStyle(style);
        }
        row3 = sheet.createRow(list.size()+5);
        sheet.addMergedRegion(new CellRangeAddress(list.size()+4,list.size()+5,0,0));
        row3.createCell(1).setCellValue(sumAutoRecharge2.getSuccessPeople());
        row3.createCell(2).setCellValue(sumAutoRecharge1.getSumPeople()-sumAutoRecharge2.getSuccessPeople());
        row3.createCell(3).setCellValue(sumAutoRecharge1.getSumPeople());
        row3.createCell(4).setCellValue(sumAutoRecharge1.getSumRechargeAmount());
        row3.createCell(5).setCellValue(sumAutoRecharge1.getSumPrice());
        row3.createCell(6).setCellValue(pageDTO.getStartTime()+"——"+pageDTO.getEndTime());

        return workbook;
    }

    @Override
    public Boolean insertRecord(AutoRechargeRecordDTO autoRechargeRecord) {
        autoRechargeRecord.setAutoRechargeRecordId(StringUtil.createId());
        autoRechargeRecord.setRechargeTime(new Date());
        autoRechargeRecord.setCreateAt(new Date());
        autoRechargeRecord.setUpdateAt(new Date());
        return autoRechargeRecordMapper.insertSelective(autoRechargeRecord) > 0;
    }

    @Override
    public JSONResult editRecorder(AutomaticRechargeDTO automaticRechargeDTO) {
        int updata = automaticRechargeMapperExtra.editRecorder(automaticRechargeDTO);
        if (updata != 0) {
            return new JSONResult("修改成功", 200);
        }
        return new JSONResult("修改失败", 200);
    }

    public String formateDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String theDate = sdf.format(date);
        return theDate;
    }
}
