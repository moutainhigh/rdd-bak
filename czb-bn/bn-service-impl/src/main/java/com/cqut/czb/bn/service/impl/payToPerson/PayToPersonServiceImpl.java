package com.cqut.czb.bn.service.impl.payToPerson;

import com.cqut.czb.bn.dao.mapper.PayToPersonMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;
import com.cqut.czb.bn.service.PayToPersonService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PayToPersonServiceImpl implements PayToPersonService{

    @Autowired
    PayToPersonMapperExtra payToPersonMapperExtra;

    //列表查询
    @Override
    public PageInfo<PayToPersonDTO> getPayList(PayToPersonDTO payToPersonDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(payToPersonMapperExtra.selectByPrimaryKey(payToPersonDTO));
    }

    //导出execl表
    @Override
    public Workbook exportPayList(PayToPersonDTO payToPersonDTO) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //精确到日
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");//精确到月
        Calendar c = Calendar.getInstance();
        Date reExportTime = monthFormat.parse(payToPersonDTO.getExportTime());
        try {
            Date exportTime = monthFormat.parse(payToPersonDTO.getExportTime());
            int compareTo = exportTime.compareTo(monthFormat.parse(format.format(new Date()))); //是否导出当前月
            if(compareTo==0){
                payToPersonDTO.setExportTime(format.format(new Date())); //导出当前月则根据现在时间筛选是否开始与结束
            }else{
                c.setTime(exportTime);
                c.add(Calendar.MONTH,1);
                exportTime = c.getTime();
                payToPersonDTO.setExportTime(format.format(exportTime));   //不是则导出那个月全部
            }
            System.out.println(compareTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<PayToPersonDTO> payToPersonDTOS = payToPersonMapperExtra.selectPayInfo(payToPersonDTO); //查询选择月中是否有合同打款信息
        if(payToPersonDTOS==null||payToPersonDTOS.size()==0){
                return null;
        }
        payToPersonDTO.setTargetYearMonth(reExportTime);
        List<PayToPersonDTO> selectPayRecord = payToPersonMapperExtra.selectByPrimaryKey(payToPersonDTO);//查看当月是否导出过
        if (selectPayRecord!=null&&selectPayRecord.size()>0){ //如果查到了对应数据则表示已经导出过了
            if (payToPersonDTOS.size()==selectPayRecord.size()){//如果合同数与打款记录数相等则无新合同
                payToPersonDTO.setState(0); //导出当月为企业未打款的记录
                return getWorkBook(payToPersonMapperExtra.selectByPrimaryKey(payToPersonDTO));
            }else {
                List<PayToPersonDTO> newContract = payToPersonMapperExtra.selectNewContract(selectPayRecord,payToPersonDTO);
                for (int i =0;i<newContract.size();i++){
                    newContract.get(i).setRecordId(StringUtil.createId());
                    newContract.get(i).setState(0);
                    newContract.get(i).setIsDeleted(0);
                    newContract.get(i).setTargetYearMonth(reExportTime);
                }
                int isAdd = payToPersonMapperExtra.insert(newContract);   //将新数据插入数据库后开始导出
                    payToPersonDTO.setState(0);
                    return getWorkBook(payToPersonMapperExtra.selectByPrimaryKey(payToPersonDTO)); //导出当月未导出的数据
                }
            }
            for (int i =0;i<payToPersonDTOS.size();i++){
            payToPersonDTOS.get(i).setRecordId(StringUtil.createId());
            payToPersonDTOS.get(i).setState(0);
            payToPersonDTOS.get(i).setIsDeleted(0);
            payToPersonDTOS.get(i).setTargetYearMonth(reExportTime);
        }

        int isAdd = payToPersonMapperExtra.insert(payToPersonDTOS);   //将数据插入数据库后开始导出
        Workbook workbook =null;
        if (isAdd>0){
         workbook = getWorkBook(payToPersonDTOS);}
        return workbook;
    }
    @Override
    public int importPayList(MultipartFile file) throws Exception{
            InputStream inputStream = file.getInputStream();
            List<PayToPersonDTO> payToPersonDTOS = null;
            Map<String, PayToPersonDTO> personDTOMap = new HashMap<>();
            payToPersonDTOS = ImportPayToPerson.readExcel(file.getOriginalFilename(), inputStream);
            int countForInsert = payToPersonMapperExtra.updateImportData(payToPersonDTOS);
                return countForInsert;
    }


//    //生成只有表头的空表
//    public Workbook getNullWorkBook(){
//        String[] payToPersonHeader = SystemConstants.PAY_TO_PERSON_RECORDS;
//        Workbook workbook = null;
//        workbook = new SXSSFWorkbook();
//        Sheet sheet = workbook.createSheet("导出企业打款记录");//创建工作表
//        Row row =sheet.createRow(0);//创建行从第0行开始
//        CellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
//        for (int i = 0; i < payToPersonHeader.length; i++) {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(payToPersonHeader[i]);
//            cell.setCellStyle(style);
//            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
//        }
//        return workbook;
//    }

    @Override
    public boolean ConfirmReceipt(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) {
        PayToPerson payToPerson=new PayToPerson();
        payToPerson.setState(1);//已打款
        if(platformIncomeRecordsDTO.getRemark()!=null)
            payToPerson.setRemark(platformIncomeRecordsDTO.getRemark());
        if(platformIncomeRecordsDTO.getRecordId()!=null)
            payToPerson.setRecordId(platformIncomeRecordsDTO.getRecordId());
        return payToPersonMapperExtra.updateByPrimaryKeySelective(payToPerson)>0;
    }

    //生成execl表
    public Workbook getWorkBook(List<PayToPersonDTO> payToPersonDTOS)throws Exception{
        String[] payToPersonHeader = SystemConstants.PAY_TO_PERSON_RECORDS;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(payToPersonDTOS.size());
        } catch (Exception e) {
            throw new Exception("Excel导出不了啦，请换个时间段试试");
        }
        Sheet sheet = workbook.createSheet("导出企业打款记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < payToPersonHeader.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(payToPersonHeader[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        for (int i = 0 ; i<payToPersonDTOS.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getContractRecordId());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getPayeeName());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getPayeeIdCard());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getBankOfDeposit());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getBankAccountNum());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getPayableMoney());
            row.createCell(count).setCellType(CellType.STRING);
            if (payToPersonDTOS.get(i).getActualPayMoney()!=null){
            row.createCell(count++).setCellValue(payToPersonDTOS.get(i).getActualPayMoney());}
            else {
                row.createCell(count++).setCellValue("");
            }
            row.createCell(count).setCellType(CellType.STRING);
            if (payToPersonDTOS.get(i).getState()==0){
            row.createCell(count++).setCellValue("未打款");}
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("");
        }
        return workbook;
    }

}
