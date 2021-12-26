package com.cqut.czb.bn.service.impl.WithDrawLogServiceImpl;

import com.cqut.czb.bn.dao.mapper.withDrawals.WithDrawalsMapperExtra;
import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;
import com.cqut.czb.bn.entity.dto.fanyong.FanyongTotalDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WithDrawLog.WithDrawalsService;
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
public class WithDrawalsServiceImpl implements WithDrawalsService {

    @Autowired
    WithDrawalsMapperExtra withDrawalsMapperExtra;

    @Override
    public JSONResult getRecode(FanyongLogDto fanyongLogDto) {
        System.out.println(fanyongLogDto);
        PageHelper.startPage(fanyongLogDto.getCurrentPage(), fanyongLogDto.getPageSize(),true);
        List<FanyongLogDto> withdrawList = withDrawalsMapperExtra.getRecode(fanyongLogDto);
        PageInfo<FanyongLogDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getTotal(FanyongLogDto fanyongLogDto) {
        Double total = withDrawalsMapperExtra.getTotal(fanyongLogDto);
        List<FanyongLogDto> withdrawList = withDrawalsMapperExtra.getRecode(fanyongLogDto);
        int length = withdrawList.size();
        FanyongTotalDto fanyongTotalDto = new FanyongTotalDto();
        fanyongTotalDto.setLength(length);
        fanyongTotalDto.setTotal(total);
        return new JSONResult("成功",200,fanyongTotalDto);
    }

    @Override
    public Workbook export(FanyongLogDto fanyongLogDto) throws Exception {
        System.out.println(fanyongLogDto);
        List<FanyongLogDto> list = withDrawalsMapperExtra.getRecode(fanyongLogDto);
        if(list==null||list.size()==0){
            return getWithDrawalsWorkBook(null,fanyongLogDto);
        }
        return getWithDrawalsWorkBook(list, fanyongLogDto);
    }

    private Workbook getWithDrawalsWorkBook(List<FanyongLogDto> list, FanyongLogDto fanyongLogDto) throws Exception {
        String[] CustomerHead = SystemConstants.WITHDRAWALS_EXCEL_HEAD;
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
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getGotUser());
            row.createCell(count++).setCellValue(list.get(i).getAmount());
            row.createCell(count++).setCellValue(Double.valueOf(list.get(i).getBeforeAmount()) - Double.parseDouble(list.get(i).getAmount()));
            row.createCell(count++).setCellValue(list.get(i).getRemark());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateAt()));
        }
        return workbook;
    }
}
