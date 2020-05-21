package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.OfflineDistributorOfAdministratorMapperExtra;
import com.cqut.czb.bn.entity.dto.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class OfflineDistributorOfAdministratorServiceImpl implements OfflineDistributorOfAdministratorService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    OfflineDistributorOfAdministratorMapperExtra offlineDistributorOfAdministratorMapperExtra;

    public OfflineDistributorOfAdministratorServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO) {
        PageHelper.startPage(accountRechargeDTO.getCurrentPage(), accountRechargeDTO.getPageSize(),true);
        OfflineRecordsDTO rechargeRecordDTO = new OfflineRecordsDTO();
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        for (int i = 0; i < list.size(); i++) {
                BigDecimal a1 = new BigDecimal(String.valueOf(list.get(i).getRechargeAmount()));
                BigDecimal b1 = new BigDecimal(String.valueOf(list.get(i).getBeforeBalance()));
                list.get(i).setBalance(a1.add(b1).doubleValue());
            if (list.get(i).getRechargeAmount()<0){
                list.get(i).setTurnMoeny(-list.get(i).getRechargeAmount());
                list.get(i).setRechargeAmount(0);
                list.get(i).setType(1);
            }
            else {
                list.get(i).setType(0);
            }
        }
        rechargeRecordDTO.setOfflineRecordsListDTOList(new PageInfo<>(list));
        rechargeRecordDTO.setTotalRecharge(offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(accountRechargeDTO));
        rechargeRecordDTO.setTotalTurn(-(offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO)));
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
        for (int i = 0; i < list.size(); i++){
            list.get(i).setTotalTurn(-list.get(i).getTotalTurn());
        }
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
    public JSONResult passwordVerification(String password) {
        String OldPwd = offlineDistributorOfAdministratorMapperExtra.getPassword();
        boolean isLike=bCryptPasswordEncoder.matches(password, OldPwd);
        System.out.println(isLike);
        if (!isLike){
            return new JSONResult("密码错误！",200,false);
        }else{
            return new JSONResult("密码正确！",200,true);
        }
    }

    @Override
    public JSONResult passwordModification(String OldPWD, String NewPWD) {
        String OldPwd = offlineDistributorOfAdministratorMapperExtra.getPassword();
        boolean isLike=bCryptPasswordEncoder.matches(OldPWD, OldPwd);
        if (!isLike){
            if (OldPwd == null){
                String newPWD = bCryptPasswordEncoder.encode(NewPWD);
                offlineDistributorOfAdministratorMapperExtra.insertPassword(newPWD);
                return new JSONResult("密码创建成功",200,true);
            }
            return new JSONResult("密码错误！",200,false);
        }else{
            String newPWD = bCryptPasswordEncoder.encode(NewPWD);
            boolean result = offlineDistributorOfAdministratorMapperExtra.changePWD(newPWD) > 0;
            return new JSONResult("密码正确",200,result);
        }
    }

    @Override
    public JSONResult rechargeAndTurn(RechargeDTO rechargeDTO) {
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
            if ("1".equals(rechargeDTO.getType()) && rechargeDTO.getRechargeAmount()<rechargeDTO.getBalance()){
                    rechargeDTO.setRechargeAmount(-rechargeDTO.getRechargeAmount());
            }else if("1".equals(rechargeDTO.getType()) && rechargeDTO.getRechargeAmount()>rechargeDTO.getBalance()){
                return new JSONResult("圈回金额不能大于余额",200,false);
            }
            offlineDistributorOfAdministratorMapperExtra.insertIncomeInfo(rechargeDTO);
            rechargeDTO.setRecordId(StringUtil.createId());
            offlineDistributorOfAdministratorMapperExtra.insertOfflineRecords(rechargeDTO);
            BigDecimal a1 = new BigDecimal(String.valueOf(rechargeDTO.getBalance()));
            BigDecimal b1 = new BigDecimal(String.valueOf(rechargeDTO.getRechargeAmount()));
            rechargeDTO.setRechargeAmount(a1.add(b1).doubleValue());
            offlineDistributorOfAdministratorMapperExtra.updateBalance(rechargeDTO);
            return new JSONResult("成功",200,true);
        }
        return new JSONResult("充值失败",200,false);
    }

    @Override
    public Workbook exportRechargeRecords(AccountRechargeDTO accountRechargeDTO) throws Exception {
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        for (int i = 0; i < list.size(); i++){
            BigDecimal a1 = new BigDecimal(String.valueOf(list.get(i).getRechargeAmount()));
            BigDecimal b1 = new BigDecimal(String.valueOf(list.get(i).getBeforeBalance()));
            list.get(i).setBalance(a1.add(b1).doubleValue());
        }
        return getRechargeWorkBook(list, accountRechargeDTO);
    }



    private Workbook getRechargeWorkBook(List<AccountRechargeDTO> list, AccountRechargeDTO inputDTO) throws Exception {
        String[] rechargeHead = SystemConstants.RECHARGE_RECORDS_HEAD;
        Double totalRecharge = offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(inputDTO);
        double totalTurn = offlineDistributorOfAdministratorMapperExtra.getTotalTurn(inputDTO);
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
            if (list.get(i).getRechargeAmount()<0){
                row.createCell(count++).setCellValue(0.00);
                row.createCell(count++).setCellValue(formatNum(-list.get(i).getRechargeAmount()));
            }else if (list.get(i).getRechargeAmount()>0){
                row.createCell(count++).setCellValue(formatNum(list.get(i).getRechargeAmount()));
                row.createCell(count++).setCellValue(0.00);
            }else {
                row.createCell(count++).setCellValue(0.00);
                row.createCell(count++).setCellValue(0.00);
            }
            row.createCell(count++).setCellValue(formatNum(list.get(i).getBalance()));
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRechargeTime()));
    }
        int index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("总充值金额：");
        row.createCell(index++).setCellValue(totalRecharge);
        row.createCell(index++).setCellValue("总圈回金额：");
        row.createCell(index++).setCellValue(formatNum(-totalTurn));
        return workbook;
    }


    @Override
    public Workbook exportClientRecords(OfflineClientDTO offlineClientDTO) throws Exception {
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        return getClientWorkBook(list, offlineClientDTO);
    }


    private Workbook getClientWorkBook(List<OfflineClientDTO> list, OfflineClientDTO inputDTO) throws Exception {
        String[] ClientHead = SystemConstants.CLIENT_RECORDS_HEAD;
        double totalBalance = offlineDistributorOfAdministratorMapperExtra.getTotalBalance(inputDTO);
        AccountRechargeDTO accountRechargeDTO = new AccountRechargeDTO();
        accountRechargeDTO.setAccount(inputDTO.getAccount());
        accountRechargeDTO.setStartTime(inputDTO.getStartTime());
        accountRechargeDTO.setEndTime(inputDTO.getEndTime());
        double totalRecharge = offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(accountRechargeDTO);
        OfflineConsumptionDTO offlineConsumptionDTO = new OfflineConsumptionDTO();
        offlineConsumptionDTO.setAccount(inputDTO.getAccount());
        offlineConsumptionDTO.setStartTime(inputDTO.getStartTime());
        offlineConsumptionDTO.setEndTime(inputDTO.getEndTime());
        double totalSale = offlineDistributorOfAdministratorMapperExtra.getTotalSale(offlineConsumptionDTO);
        double totalTurn = offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO);
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出线下大客户余额记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出线下大客户余额记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < ClientHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(ClientHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 1);
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(formatNum(list.get(i).getBalance()));
            row.createCell(count++).setCellValue(formatNum(list.get(i).getTotalConsumption()));
            row.createCell(count++).setCellValue(formatNum(list.get(i).getTotalRecharge()));
            if (list.get(i).getTotalTurn()<0){
                row.createCell(count++).setCellValue(formatNum(-list.get(i).getTotalTurn()));
            }else{
                row.createCell(count++).setCellValue(formatNum(list.get(i).getTotalTurn()));
            }
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRegisterTime()));
        }
        int index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("总充值金额：");
        row.createCell(index++).setCellValue(formatNum(totalRecharge));
        row.createCell(index++).setCellValue("总消费金额：");
        row.createCell(index++).setCellValue(formatNum(totalSale));
        row.createCell(index++).setCellValue("总圈回金额：");
        row.createCell(index++).setCellValue(formatNum(-totalTurn));
        row.createCell(index++).setCellValue("总余额：");
        row.createCell(index++).setCellValue(formatNum(totalBalance));

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
        row.createCell(index++).setCellValue("总消费金额：");
        row.createCell(index++).setCellValue(totalConsumption);
        return workbook;
    }
    public String formatNum(double num) {
        String a = String.format("%.2f",num);
        return a;
    }

}
