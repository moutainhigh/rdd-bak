package com.cqut.czb.bn.service.impl.platformIncomeRecord;

import com.cqut.czb.bn.dao.mapper.PayToPersonMapperExtra;
import com.cqut.czb.bn.dao.mapper.PlatformIncomeRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import com.cqut.czb.bn.service.PlatformIncomeRecordsService;
import com.cqut.czb.bn.service.impl.payToPerson.ImportPayToPerson;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformIncomeRecordServiceImpl implements PlatformIncomeRecordsService{

    @Autowired
    private PlatformIncomeRecordsMapperExtra platformIncomeRecordsMapperExtra;

    @Autowired
    private PayToPersonMapperExtra payToPersonMapperExtra;

    @Override
    public PageInfo<PlatformIncomeRecordsDTO> getReceiveRecords(PlatformIncomeRecordsDTO records, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = platformIncomeRecordsMapperExtra.selectByPrimaryKey(records);
        return new PageInfo<>(platformIncomeRecordsDTOS);
    }

    @Override
    public Workbook exportRecords(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) throws Exception {
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = platformIncomeRecordsMapperExtra.selectIncomeList(platformIncomeRecordsDTO); //查询选择月份中有哪些合同
        if (platformIncomeRecordsDTOS==null||platformIncomeRecordsDTOS.size()==0){
            return null;
        }
        List<PlatformIncomeRecordsDTO> incomeMoney = platformIncomeRecordsMapperExtra.selectIncome(platformIncomeRecordsDTOS);//查询这些合同企业的打款总数
        for(int i=0;i<platformIncomeRecordsDTOS.size();i++){ //将合同与应打款合并
            for (int j=0;j<incomeMoney.size();i++){
                if(platformIncomeRecordsDTOS.get(i).getContractRecordId().equals(incomeMoney.get(i).getContractRecordId())){
                    platformIncomeRecordsDTOS.get(i).setRecordId(StringUtil.createId());
                    platformIncomeRecordsDTOS.get(i).setReceivableMoney(incomeMoney.get(i).getReceivableMoney());
                    platformIncomeRecordsDTOS.get(i).setTargetYearMonth(new SimpleDateFormat("yyyy-MM").parse(platformIncomeRecordsDTO.getExportTime()));  //将有合同数据的目标年月数写为选择的年月数
                    platformIncomeRecordsDTOS.get(i).setState(0); //将平台对该合同的收款状态设为未收款
                    platformIncomeRecordsDTOS.get(i).setIsDeleted(0);
                }
            }
        }
        int isInert = platformIncomeRecordsMapperExtra.insert(platformIncomeRecordsDTOS);
        Workbook workbook = null;
        if (isInert>0){
         workbook = getWorkBook(platformIncomeRecordsDTOS);}
        return workbook;
    }

    @Override
    public boolean ConfirmReceipt(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) {
        PayToPerson payToPerson=new PayToPerson();
        payToPerson.setState(1);//已打款
        if(platformIncomeRecordsDTO.getRemark()!=null)
        payToPerson.setRemark(platformIncomeRecordsDTO.getRemark());
        return payToPersonMapperExtra.updateByPrimaryKey(payToPerson)>0;
    }

    //导出生成execl表
    @Async
    @Override
    public int importRecords(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = null;
        Map<String, PayToPersonDTO> personDTOMap = new HashMap<>();
        platformIncomeRecordsDTOS = ImportPlatformIncome.readExcel(file.getOriginalFilename(), inputStream);
        System.out.println("99999999"+platformIncomeRecordsDTOS.get(0).getContractRecordId());
        int countForInsert = platformIncomeRecordsMapperExtra.updateImportData(platformIncomeRecordsDTOS);
//        System.out.println("countForInsert " + countForInsert);
        return countForInsert;
    }

    //导出生成execl表
    public Workbook getWorkBook(List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS)throws Exception{
        String[] platformIncomeRecordsHeader = SystemConstants.PLATFORM_INCOME_RECORDS;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(platformIncomeRecordsDTOS.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出平台收款记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < platformIncomeRecordsHeader.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(platformIncomeRecordsHeader[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        for (int i = 0 ; i<platformIncomeRecordsDTOS.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(platformIncomeRecordsDTOS.get(i).getContractRecordId());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(platformIncomeRecordsDTOS.get(i).getUserName());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(platformIncomeRecordsDTOS.get(i).getReceivableMoney());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("");
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM").format(platformIncomeRecordsDTOS.get(i).getTargetYearMonth()));
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("");
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("未打款");
        }
        return workbook;
    }

}
