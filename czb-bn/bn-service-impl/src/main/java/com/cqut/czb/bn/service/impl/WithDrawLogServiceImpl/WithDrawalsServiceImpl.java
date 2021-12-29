package com.cqut.czb.bn.service.impl.WithDrawLogServiceImpl;

import com.cqut.czb.bn.dao.mapper.withDrawals.WithDrawalsMapperExtra;
import com.cqut.czb.bn.entity.dto.withdrawals.WithdrawalsInsertDTO;
import com.cqut.czb.bn.entity.dto.withdrawals.WithdrawalsSelectDTO;
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
    public JSONResult getRecode(WithdrawalsSelectDTO withdrawalsSelectDTO) {
        PageHelper.startPage(withdrawalsSelectDTO.getCurrentPage(), withdrawalsSelectDTO.getPageSize(), true);
        List<WithdrawalsSelectDTO> withdrawList = withDrawalsMapperExtra.getRecode(withdrawalsSelectDTO);
        PageInfo<WithdrawalsSelectDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getTotal(WithdrawalsSelectDTO withdrawalsSelectDTO) {
        WithdrawalsSelectDTO total = new WithdrawalsSelectDTO();
        total.setTotalAmount(withDrawalsMapperExtra.getTotalAmount(withdrawalsSelectDTO));
        total.setTodayTotalAmount(withDrawalsMapperExtra.getTodayTotalAmount(withdrawalsSelectDTO));
        total.setMonthTotalAmount(withDrawalsMapperExtra.getMonthTotalAmount(withdrawalsSelectDTO));
        return new JSONResult("成功", 200, total);
    }

    @Override
    public Workbook export(WithdrawalsSelectDTO withdrawalsSelectDTO) throws Exception {
        List<WithdrawalsSelectDTO> list = withDrawalsMapperExtra.getRecode(withdrawalsSelectDTO);
        if (list == null || list.size() == 0) {
            return getWithDrawalsWorkBook(null);
        }
        return getWithDrawalsWorkBook(list);
    }


    private Workbook getWithDrawalsWorkBook(List<WithdrawalsSelectDTO> list) throws Exception {
        String[] CustomerHead = SystemConstants.WITHDRAWALS_EXCEL_HEAD;
        Workbook workbook;
        if (list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
            Row row = sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无订单记录");
            return workbook;
        }
        try {
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
        Row row = sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < CustomerHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(CustomerHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 1);
            row.createCell(count++).setCellValue(list.get(i).getUserAccount());
            row.createCell(count++).setCellValue(list.get(i).getUserName());
            row.createCell(count++).setCellValue(list.get(i).getAmount());
            row.createCell(count++).setCellValue(list.get(i).getRest());
            row.createCell(count++).setCellValue(list.get(i).getRemark());
            row.createCell(count).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateAt()));
        }
        return workbook;
    }


    @Override
    public JSONResult addMoney(WithdrawalsInsertDTO withdrawalsInsertDTO){
        if(withDrawalsMapperExtra.insertRecode(withdrawalsInsertDTO) == 0)
            return new JSONResult("插入记录失败", 500, "插入记录失败");
        if(withDrawalsMapperExtra.addMoney(withdrawalsInsertDTO) == 0)
            return new JSONResult("更新余额失败", 500, "更新余额失败");
        return new JSONResult("增加余额成功", 200);
    }

}
