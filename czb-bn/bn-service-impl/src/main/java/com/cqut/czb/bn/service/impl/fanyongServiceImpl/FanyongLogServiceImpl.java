package com.cqut.czb.bn.service.impl.fanyongServiceImpl;

import com.cqut.czb.bn.dao.mapper.fanyong.FanyongMapperExtra;
import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.fanyong.FanyongLogService;
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
public class FanyongLogServiceImpl implements FanyongLogService {

    @Autowired
    FanyongMapperExtra fanyongMapperExtra;

    @Override
    public boolean isContainFanyongLog(String orgId){
        return fanyongMapperExtra.isContainFanyongLog(orgId);
    }

    @Override
    public JSONResult getLogData(FanyongLogDto fanyongLogDto) {
        System.out.println(fanyongLogDto);
        PageHelper.startPage(fanyongLogDto.getCurrentPage(), fanyongLogDto.getPageSize(),true);
        List<FanyongLogDto> withdrawList = fanyongMapperExtra.getLogData(fanyongLogDto);
        PageInfo<FanyongLogDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public Workbook exportFanyongLog(FanyongLogDto fanyongLogDto) throws Exception {
        System.out.println(fanyongLogDto);
        List<FanyongLogDto> list = fanyongMapperExtra.getLogData(fanyongLogDto);
        if(list==null||list.size()==0){
            return getLogWorkBook(null,fanyongLogDto);
        }
        return getLogWorkBook(list, fanyongLogDto);
    }

    @Override
    public JSONResult getFanyongTotalAmount(FanyongLogDto fanyongLogDto) {
        System.out.println(fanyongLogDto);
        System.out.println(fanyongMapperExtra.getTotal(fanyongLogDto));
        return new JSONResult("查询成功",200,fanyongMapperExtra.getTotal(fanyongLogDto));
    }

    private Workbook getLogWorkBook(List<FanyongLogDto> list, FanyongLogDto fanyongLogDto) throws Exception {
        String[] CustomerHead = SystemConstants.FANYONG_EXCEL_HEAD;
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无订单记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < CustomerHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(CustomerHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        System.out.println("数量" + list.size());
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getSourceUser());
            row.createCell(count++).setCellValue(list.get(i).getAmount());
            row.createCell(count++).setCellValue(list.get(i).getRemark());
            row.createCell(count++).setCellValue(list.get(i).getSouseId());
            row.createCell(count++).setCellValue(list.get(i).getGotUser());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateAt()));
        }
        return workbook;
    }
}
