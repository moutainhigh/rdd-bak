package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.OfflineDistributorOfAdministratorMapperExtra;
import com.cqut.czb.bn.entity.dto.*;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class OfflineDistributorOfAdministratorServiceImpl implements OfflineDistributorOfAdministratorService {

    @Autowired
    OfflineDistributorOfAdministratorMapperExtra offlineDistributorOfAdministratorMapperExtra;

    @Override
    public JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO) {
        PageHelper.startPage(accountRechargeDTO.getCurrentPage(), accountRechargeDTO.getPageSize(),true);
        OfflineRecordsDTO rechargeRecordDTO = new OfflineRecordsDTO();
        rechargeRecordDTO.setOfflineRecordsListDTOList(new PageInfo<>(offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO)));
        rechargeRecordDTO.setTotalRecharge(offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(accountRechargeDTO));
        return new JSONResult("列表数据查询成功", 200, rechargeRecordDTO);
    }

    @Override
    public JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO) {
        PageHelper.startPage(offlineConsumptionDTO.getCurrentPage(), offlineConsumptionDTO.getPageSize(),true);
        OfflineRecordsDTO consumptionRecordDTO = new OfflineRecordsDTO();
        consumptionRecordDTO.setOfflineRecordsListDTOList(new PageInfo<>(offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO)));
        consumptionRecordDTO.setTotalSale(offlineDistributorOfAdministratorMapperExtra.getTotalSale(offlineConsumptionDTO));
        return new JSONResult("列表数据查询成功", 200, consumptionRecordDTO);
    }

    @Override
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO) {
        PageHelper.startPage(offlineClientDTO.getCurrentPage(), offlineClientDTO.getPageSize(),true);
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        PageInfo<OfflineClientDTO> pageInfo = new PageInfo<>(list);
        JSONResult result = new JSONResult("列表数据查询成功", 200, pageInfo);
        return  result;
    }

    @Override
    public JSONResult getRechargeAccountList() {
        List<String> list = offlineDistributorOfAdministratorMapperExtra.getRechargeAccountList();
        return new JSONResult("列表查询成功", 200, list);
    }

    @Override
    public JSONResult getAccountBalance(String account) {
        if (account == null || account == ""){
            return new JSONResult("账户不能为空",200);
        }
        Double balance = offlineDistributorOfAdministratorMapperExtra.getAccountBalance(account);
        return new JSONResult("余额查询成功", 200, balance);
    }

    @Override
    public JSONResult recharge(RechargeDTO rechargeDTO) {
        if (rechargeDTO.getRechargeAmount()<=0){
            return new JSONResult("充值金额不能为负数，充值失败",200,false);
        }
        if (offlineDistributorOfAdministratorMapperExtra.selectAccount(rechargeDTO.getAccount())==0){
            return new JSONResult("该账户不是线下大客户，充值失败",200,false);
        }
        else if (rechargeDTO.getAccount()!=null && rechargeDTO.getAccount() != ""){
            RechargeDTO rechargeInfo = offlineDistributorOfAdministratorMapperExtra.getInfo(rechargeDTO);
            rechargeDTO.setUserId(rechargeInfo.getUserId());
            rechargeDTO.setInfoId(rechargeInfo.getInfoId());
            rechargeDTO.setRecordId(StringUtil.createId());
            rechargeDTO.setBalance(rechargeInfo.getBalance());
            offlineDistributorOfAdministratorMapperExtra.insertIncomeInfo(rechargeDTO);
            rechargeDTO.setRecordId(StringUtil.createId());
            offlineDistributorOfAdministratorMapperExtra.insertOfflineRecords(rechargeDTO);
            offlineDistributorOfAdministratorMapperExtra.updateBalance(rechargeDTO);
            return new JSONResult("充值成功",200,true);
        }
        return new JSONResult("充值失败",200,false);
    }

    @Override
    public Workbook exportRechargeRecords(AccountRechargeDTO accountRechargeDTO) throws Exception {
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        return getRechargeWorkBook(list, accountRechargeDTO);
    }



    private Workbook getRechargeWorkBook(List<AccountRechargeDTO> list, AccountRechargeDTO inputDTO) throws Exception {
        String[] rechargeHead = SystemConstants.RECHARGE_RECORDS_HEAD;
        Double totalRecharge = offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(inputDTO);
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出线下大客户充值记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无充值记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出线下大客户充值记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < rechargeHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rechargeHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(formatNum(list.get(i).getRechargeAmount()));
            row.createCell(count++).setCellValue(formatNum(list.get(i).getBalance()));
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRechargeTime()));
    }
        int index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("总充值金额：");
        row.createCell(index++).setCellValue(totalRecharge);

        return workbook;
    }

    @Override
    public Workbook exportConsumptionRecords(OfflineConsumptionDTO offlineConsumptionDTO) throws Exception {
        List<OfflineConsumptionDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO);
        return getConsumptionWorkBook(list, offlineConsumptionDTO);
    }

    private Workbook getConsumptionWorkBook(List<OfflineConsumptionDTO> list, OfflineConsumptionDTO inputDTO) throws Exception {
        String[] consumptionHead = SystemConstants.CONSUMPTION_RECORDS_HEAD;
        Double totalConsumption = offlineDistributorOfAdministratorMapperExtra.getTotalSale(inputDTO);
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出线下大客户消费记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无消费记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出线下大客户消费记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < consumptionHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(consumptionHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            row.createCell(count++).setCellValue(formatNum(list.get(i).getAmount()));
            if ("1".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("充值成功");
            }else if ("0".equals(list.get(i).getState())){
                row.createCell(count++).setCellValue("待充值");
            }else {
                row.createCell(count++).setCellValue(" ");
            }
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRechargeTime()));
        }
        int index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("总充值金额：");
        row.createCell(index++).setCellValue(totalConsumption);
        return workbook;
    }
    public String formatNum(double num) {
        String a = String.format("%.2f",num);
        return a;
    }

}
