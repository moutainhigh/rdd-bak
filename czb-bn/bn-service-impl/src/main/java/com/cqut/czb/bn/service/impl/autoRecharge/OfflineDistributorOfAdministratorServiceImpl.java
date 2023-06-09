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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        PageHelper.startPage(accountRechargeDTO.getCurrentPage(), accountRechargeDTO.getPageSize(), true);
        OfflineRecordsDTO rechargeRecordDTO = new OfflineRecordsDTO();
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        for (int i = 0; i < list.size(); i++) {
            BigDecimal a1 = new BigDecimal(String.valueOf(list.get(i).getRechargeAmount()));
            BigDecimal b1 = new BigDecimal(String.valueOf(list.get(i).getBeforeBalance()));
            list.get(i).setBalance(a1.add(b1).doubleValue());
            if (list.get(i).getRechargeAmount() < 0) {
                list.get(i).setTurnMoeny(-list.get(i).getRechargeAmount());
                list.get(i).setRechargeAmount(0);
                list.get(i).setType(1);
            } else {
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
        PageHelper.startPage(offlineConsumptionDTO.getCurrentPage(), offlineConsumptionDTO.getPageSize(), true);
        OfflineRecordsDTO consumptionRecordDTO = new OfflineRecordsDTO();
        consumptionRecordDTO.setOfflineRecordsListDTOList(new PageInfo<>(offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO)));
        consumptionRecordDTO.setTotalSale(offlineDistributorOfAdministratorMapperExtra.getTotalSale(offlineConsumptionDTO));
        return new JSONResult("列表数据查询成功", 200, consumptionRecordDTO);
    }

    @Override
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO) {
        PageHelper.startPage(offlineClientDTO.getCurrentPage(), offlineClientDTO.getPageSize(), true);
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTotalTurn(-list.get(i).getTotalTurn());
        }
        PageInfo<OfflineClientDTO> pageInfo = new PageInfo<>(list);
        JSONResult result = new JSONResult("列表数据查询成功", 200, pageInfo);
        return result;
    }

    @Override
    public JSONResult getRechargeAccountList(Integer isSpecial) {
        List<String> list = offlineDistributorOfAdministratorMapperExtra.getRechargeAccountList(isSpecial);
        return new JSONResult("列表查询成功", 200, list);
    }

    @Override
    public JSONResult getAccountBalance(String account) {
        if (account == null || account == "") {
            return new JSONResult("账户不能为空", 200);
        }
        Double balance = offlineDistributorOfAdministratorMapperExtra.getAccountBalance(account);
        return new JSONResult("余额查询成功", 200, balance);
    }

    @Override
    public JSONResult passwordVerification(String password, Integer isSpecial) {
        boolean isLike = false;
        System.out.println("123:" + isSpecial);
        if (isSpecial == 0) {
            String OldPwd = offlineDistributorOfAdministratorMapperExtra.getPassword();
            System.out.println("123:" + OldPwd);
            isLike = bCryptPasswordEncoder.matches(password, OldPwd);
        } else if (isSpecial == 1) {
            String OldPwd = offlineDistributorOfAdministratorMapperExtra.getSpecialPassword();
            isLike = bCryptPasswordEncoder.matches(password, OldPwd);
        } else {
            return new JSONResult("该账户类型有误！", 200, false);
        }
        if (!isLike) {
            return new JSONResult("密码错误！", 200, false);
        } else {
            return new JSONResult("密码正确！", 200, true);
        }
    }

    @Override
    public JSONResult passwordModification(NewOldPwdDTO newOldPwdDTO) {
        boolean isLike = false;
        String OldPwd = null;
        if (newOldPwdDTO.getIsSpecial() == 0) {
            OldPwd = offlineDistributorOfAdministratorMapperExtra.getPassword();
            isLike = bCryptPasswordEncoder.matches(newOldPwdDTO.getOldPWD(), OldPwd);
        } else if (newOldPwdDTO.getIsSpecial() == 1) {
            OldPwd = offlineDistributorOfAdministratorMapperExtra.getSpecialPassword();
            isLike = bCryptPasswordEncoder.matches(newOldPwdDTO.getOldPWD(), OldPwd);
        } else {
            return new JSONResult("该账户类型有误！", 200, false);
        }
        if (!isLike) {
            if (OldPwd == null) {
                String newPWD = bCryptPasswordEncoder.encode(newOldPwdDTO.getNewPWD());
                if (newOldPwdDTO.getIsSpecial() == 0) {
                    offlineDistributorOfAdministratorMapperExtra.insertPassword(newPWD);
                } else {
                    offlineDistributorOfAdministratorMapperExtra.insertSpecialPassword(newPWD);
                }
                return new JSONResult("密码创建成功", 200, true);
            }
            return new JSONResult("密码错误！", 200, false);
        } else {
            String newPWD = bCryptPasswordEncoder.encode(newOldPwdDTO.getNewPWD());
            boolean result = false;
            if (newOldPwdDTO.getIsSpecial() == 0) {
                result = offlineDistributorOfAdministratorMapperExtra.changePWD(newPWD) > 0;
            } else {
                result = offlineDistributorOfAdministratorMapperExtra.changeSpecialPWD(newPWD) > 0;
            }
            return new JSONResult("密码正确", 200, result);
        }
    }

    @Override
    public JSONResult rechargeAndTurn(RechargeDTO rechargeDTO) {
        if (rechargeDTO.getRechargeAmount() <= 0) {
            return new JSONResult("充值金额不能为负数，充值失败", 200, false);
        }
        if (offlineDistributorOfAdministratorMapperExtra.selectAccount(rechargeDTO.getAccount()) == 0) {
            return new JSONResult("该账户不是线下大客户，充值失败", 200, false);
        } else if (rechargeDTO.getAccount() != null && rechargeDTO.getAccount() != "") {
            RechargeDTO rechargeInfo = offlineDistributorOfAdministratorMapperExtra.getInfo(rechargeDTO);
            rechargeDTO.setUserId(rechargeInfo.getUserId());
            rechargeDTO.setInfoId(rechargeInfo.getInfoId());
            rechargeDTO.setRecordId(StringUtil.createId());
            rechargeDTO.setBalance(rechargeInfo.getBalance());
            if ("1".equals(rechargeDTO.getType()) && rechargeDTO.getRechargeAmount() <= rechargeDTO.getBalance()) {
                rechargeDTO.setRechargeAmount(-rechargeDTO.getRechargeAmount());
            } else if ("1".equals(rechargeDTO.getType()) && rechargeDTO.getRechargeAmount() > rechargeDTO.getBalance()) {
                return new JSONResult("圈回金额不能大于余额", 200, false);
            }
            offlineDistributorOfAdministratorMapperExtra.insertIncomeInfo(rechargeDTO);
            rechargeDTO.setRecordId(StringUtil.createId());
            offlineDistributorOfAdministratorMapperExtra.insertOfflineRecords(rechargeDTO);
            BigDecimal a1 = new BigDecimal(String.valueOf(rechargeDTO.getBalance()));
            BigDecimal b1 = new BigDecimal(String.valueOf(rechargeDTO.getRechargeAmount()));
            rechargeDTO.setRechargeAmount(a1.add(b1).doubleValue());
            offlineDistributorOfAdministratorMapperExtra.updateBalance(rechargeDTO);
            return new JSONResult("成功", 200, true);
        }
        return new JSONResult("充值失败", 200, false);
    }

    @Override
    public Workbook exportRechargeRecords(AccountRechargeDTO accountRechargeDTO) throws Exception {
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        if (list == null || list.size() == 0) {
            return getRechargeWorkBook(null, accountRechargeDTO);
        }
        for (int i = 0; i < list.size(); i++) {
            BigDecimal a1 = new BigDecimal(String.valueOf(list.get(i).getRechargeAmount()));
            BigDecimal b1 = new BigDecimal(String.valueOf(list.get(i).getBeforeBalance()));
            list.get(i).setBalance(a1.add(b1).doubleValue());
        }
        return getRechargeWorkBook(list, accountRechargeDTO);
    }

    private Workbook getRechargeWorkBook(List<AccountRechargeDTO> list, AccountRechargeDTO inputDTO) throws Exception {
        String[] rechargeHead = SystemConstants.RECHARGE_RECORDS_HEAD;
        double totalRecharge = offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(inputDTO);
        double totalTurn = offlineDistributorOfAdministratorMapperExtra.getTotalTurn(inputDTO);
        Workbook workbook = null;
        if (list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出线下大客户充值记录");//创建工作表
            Row row = sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无充值记录");
            return workbook;
        }
        try {
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出线下大客户充值记录");//创建工作表
        Row row = sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < rechargeHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rechargeHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 1);
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            if (list.get(i).getRechargeAmount() < 0) {
                row.createCell(count++).setCellValue(0.00);
                row.createCell(count++).setCellValue(formatNum(-list.get(i).getRechargeAmount()));
            } else if (list.get(i).getRechargeAmount() > 0) {
                row.createCell(count++).setCellValue(formatNum(list.get(i).getRechargeAmount()));
                row.createCell(count++).setCellValue(0.00);
            } else {
                row.createCell(count++).setCellValue(0.00);
                row.createCell(count++).setCellValue(0.00);
            }
            row.createCell(count++).setCellValue(formatNum(list.get(i).getBalance()));
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRechargeTime()));
        }
        int index = 0;
        row = sheet.createRow(list.size() + 1);
        row.createCell(index++).setCellValue("总充值金额：");
        row.createCell(index++).setCellValue(totalRecharge);
        row.createCell(index++).setCellValue("总圈回金额：");
        row.createCell(index++).setCellValue(formatNum(-totalTurn));
        return workbook;
    }


    @Override
    public Workbook exportClientRecords(OfflineClientDTO offlineClientDTO) throws Exception {
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        if (list == null || list.size() == 0) {
            return getClientWorkBook(null, offlineClientDTO);
        }
        return getClientWorkBook(list, offlineClientDTO);
    }


    private Workbook getClientWorkBook(List<OfflineClientDTO> list, OfflineClientDTO inputDTO) throws Exception {
        String[] ClientHead = SystemConstants.CLIENT_RECORDS_HEAD;
        AccountRechargeDTO accountRechargeDTO = new AccountRechargeDTO();
        accountRechargeDTO.setAccount(inputDTO.getAccount());
        accountRechargeDTO.setStartTime(inputDTO.getStartTime());
        accountRechargeDTO.setEndTime(inputDTO.getEndTime());
        OfflineConsumptionDTO offlineConsumptionDTO = new OfflineConsumptionDTO();
        offlineConsumptionDTO.setAccount(inputDTO.getAccount());
        offlineConsumptionDTO.setStartTime(inputDTO.getStartTime());
        offlineConsumptionDTO.setEndTime(inputDTO.getEndTime());
        Workbook workbook = null;
        if (list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出线下大客户详情记录");//创建工作表
            Row row = sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无记录");
            return workbook;
        }
        try {
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出线下大客户余额记录");//创建工作表
        Row row = sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));//起始行号，终止行号， 起始列号，终止列号
        Cell cell1 = row.createCell(0);
        cell1.setCellStyle(style);
        BigDecimal totalBalance = new BigDecimal(String.valueOf(0.00));
        BigDecimal todayBalance = new BigDecimal(String.valueOf(0.00));
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        cell1.setCellValue(sdf1.format(inputDTO.getStartTime()) + "至" + sdf1.format(inputDTO.getEndTime()) + "线下大客户余额记录");
        row = sheet.createRow(1);
        for (int i = 0; i < ClientHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(ClientHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 2);
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(formatNum(list.get(i).getTotalRecharge()));
            row.createCell(count++).setCellValue(formatNum(list.get(i).getTotalConsumption()));
            if (list.get(i).getTotalTurn() < 0) {
                row.createCell(count++).setCellValue(formatNum(-list.get(i).getTotalTurn()));
            } else {
                row.createCell(count++).setCellValue(formatNum(list.get(i).getTotalTurn()));
            }
            OfflineConsumptionDTO offlineConsumptionDTO1 = new OfflineConsumptionDTO();
            offlineConsumptionDTO1.setAccount(list.get(i).getAccount());
            offlineConsumptionDTO1.setStartTime(null);
            offlineConsumptionDTO1.setEndTime(inputDTO.getEndTime());
            BeforeBalanceDTO beforeBalanceDTO = offlineDistributorOfAdministratorMapperExtra.getTotalBalance(offlineConsumptionDTO1);
            BigDecimal balance = new BigDecimal(String.valueOf(0.00));
            if (beforeBalanceDTO == null) {
                row.createCell(count++).setCellValue(formatNum(0.00));
            } else {
                BigDecimal a1 = new BigDecimal(String.valueOf(beforeBalanceDTO.getAmount()));
                BigDecimal b1 = new BigDecimal(String.valueOf(beforeBalanceDTO.getBeforeBalance()));
                if (beforeBalanceDTO.getType() == 6) {
                    balance = a1.add(b1);
                } else {

                    balance = b1.subtract(a1);
                }
                row.createCell(count++).setCellValue(formatNum(balance.doubleValue()));
            }
            totalBalance = totalBalance.add(balance);
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRegisterTime()));
            todayBalance = todayBalance.add(new BigDecimal(String.valueOf(list.get(i).getBalance())));

        }
        int index = 0;
        row = sheet.createRow(list.size() + 2);
        row.createCell(index++).setCellValue("累计：");
        row = sheet.createRow(list.size() + 3);
        row.createCell(index++).setCellValue("总充值金额：");
        AccountRechargeDTO accountRechargeDTO1 = new AccountRechargeDTO();
        accountRechargeDTO1.setStartTime(inputDTO.getStartTime());
        accountRechargeDTO1.setEndTime(inputDTO.getEndTime());
        accountRechargeDTO1.setIsSpecial(inputDTO.getIsSpecial());
        row.createCell(index++).setCellValue(formatNum(offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(accountRechargeDTO1)));
        row.createCell(index++).setCellValue("总消费金额：");
        OfflineConsumptionDTO offlineConsumptionDTO1 = new OfflineConsumptionDTO();
        offlineConsumptionDTO1.setStartTime(inputDTO.getStartTime());
        offlineConsumptionDTO1.setEndTime(inputDTO.getEndTime());
        offlineConsumptionDTO1.setIsSpecial(inputDTO.getIsSpecial());
        row.createCell(index++).setCellValue(formatNum(offlineDistributorOfAdministratorMapperExtra.getTotalSale(offlineConsumptionDTO1)));
        row.createCell(index++).setCellValue("总圈回金额：");
        if (offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO1) == 0) {
            row.createCell(index++).setCellValue(formatNum(offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO1)));
        } else {
            row.createCell(index++).setCellValue(formatNum(-offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO1)));
        }

        row.createCell(index++).setCellValue("总余额：");
        row.createCell(index++).setCellValue(formatNum(totalBalance.doubleValue()));
        index = 0;
        row = sheet.createRow(list.size() + 4);
        row.createCell(index++).setCellValue("今日：");
        row = sheet.createRow(list.size() + 5);
        row.createCell(index++).setCellValue("总充值金额：");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(new Date());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = ft.parse(time);
        accountRechargeDTO1.setStartTime(date);
        accountRechargeDTO1.setEndTime(null);
        double totalRecharge = offlineDistributorOfAdministratorMapperExtra.getTotalRecharge(accountRechargeDTO1);
        row.createCell(index++).setCellValue(formatNum(totalRecharge));
        row.createCell(index++).setCellValue("总消费金额：");
        offlineConsumptionDTO1.setStartTime(date);
        offlineConsumptionDTO1.setEndTime(null);
        row.createCell(index++).setCellValue(formatNum(offlineDistributorOfAdministratorMapperExtra.getTotalSale(offlineConsumptionDTO1)));
        row.createCell(index++).setCellValue("总圈回金额：");
        if (offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO1) == 0) {
            row.createCell(index++).setCellValue(formatNum(offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO1)));
        } else {
            row.createCell(index++).setCellValue(formatNum(-offlineDistributorOfAdministratorMapperExtra.getTotalTurn(accountRechargeDTO1)));
        }
        row.createCell(index++).setCellValue("总余额：");
        row.createCell(index++).setCellValue(formatNum(todayBalance.doubleValue()));
        return workbook;
    }

    @Override
    public Workbook exportConsumptionRecords(OfflineConsumptionDTO offlineConsumptionDTO) throws Exception {
        List<OfflineConsumptionDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO);
        if (list == null || list.size() == 0) {
            return getConsumptionWorkBook(null, offlineConsumptionDTO);
        }
        return getConsumptionWorkBook(list, offlineConsumptionDTO);
    }


    private Workbook getConsumptionWorkBook(List<OfflineConsumptionDTO> list, OfflineConsumptionDTO inputDTO) throws Exception {
        String[] consumptionHead = SystemConstants.CONSUMPTION_RECORDS_HEAD;
        Double totalConsumption = offlineDistributorOfAdministratorMapperExtra.getTotalSale(inputDTO);
        Workbook workbook = null;
        if (list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出线下大客户消费记录");//创建工作表
            Row row = sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无消费记录");
            return workbook;
        }
        try {
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出线下大客户消费记录");//创建工作表
        Row row = sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < consumptionHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(consumptionHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 1);
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            row.createCell(count++).setCellValue(formatNum(list.get(i).getAmount()));
            if ("1".equals(list.get(i).getState())) {
                row.createCell(count++).setCellValue("充值成功");
            } else if ("0".equals(list.get(i).getState())) {
                row.createCell(count++).setCellValue("待充值");
            } else if ("2".equals(list.get(i).getState())) {
                row.createCell(count++).setCellValue("问题卡号");
            }
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRechargeTime()));
        }
        int index = 0;
        row = sheet.createRow(list.size() + 1);
        row.createCell(index++).setCellValue("总消费金额：");
        row.createCell(index++).setCellValue(totalConsumption);
        return workbook;
    }

    public String formatNum(double num) {
        String a = String.format("%.2f", num);
        return a;
    }

}
