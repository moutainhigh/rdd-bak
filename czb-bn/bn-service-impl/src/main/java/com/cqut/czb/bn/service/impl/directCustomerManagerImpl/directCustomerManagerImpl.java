package com.cqut.czb.bn.service.impl.directCustomerManagerImpl;

import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.directCustomerManager.DirectCustomerMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomerManager.DirectCustomerService;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class directCustomerManagerImpl implements DirectCustomerService {

    @Autowired
    DirectCustomerMapperExtra directCustomerMapperExtra;

    private final UserMapperExtra userMapperExtra;

    private final RedisUtil redisUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public directCustomerManagerImpl(UserMapperExtra userMapperExtra, RedisUtil redisUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapperExtra = userMapperExtra;
        this.redisUtil = redisUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public JSONResult getCustomerData(DirectChargingOrderDto directChargingOrderDto) {
        System.out.println(directChargingOrderDto);
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getCustomerList(directChargingOrderDto);
        PageInfo<DirectCustomerManagerDto> pageInfo = new PageInfo<>(list);
        JSONResult result = new JSONResult("列表数据查询成功", 200, pageInfo);
        return  result;
    }

    @Override
    public JSONResult getConsumptionList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getConsumptionList(directChargingOrderDto);
        PageInfo<DirectCustomerManagerDto> pageInfo = new PageInfo<>(list);
        JSONResult result = new JSONResult("列表数据查询成功", 200, pageInfo);
        return result;
    }

    @Override
    public JSONResult addAgent(DirectCustomerManagerDto directCustomerManagerDto) {
        System.out.println(directCustomerManagerDto);
        DirectCustomerManagerDto directCustomerManagerDto1 = directCustomerMapperExtra.findAccount(directCustomerManagerDto);
        if (directCustomerManagerDto1 == null){
            return new JSONResult("插入失败，未知用户", 200);
        }
        DirectCustomerManagerDto directCustomerManagerDto2 = directCustomerMapperExtra.findUserId(directCustomerManagerDto1);
        if (directCustomerManagerDto2 != null) {
            return new JSONResult("插入失败，已经存在用户", 200);
        }
        directCustomerManagerDto.setUserId(directCustomerManagerDto1.getUserId());
        directCustomerManagerDto.setOrderId(StringUtil.createId());
        directCustomerManagerDto.setRole(directCustomerMapperExtra.getAgentRole());
        int result = directCustomerMapperExtra.addAgent(directCustomerManagerDto);
        int result2 = directCustomerMapperExtra.addAgentRole(directCustomerManagerDto);

        // 更新 reids 中 用户角色
        UserDTO user = userMapperExtra.findUserDTOById(directCustomerManagerDto.getUserId());
        if(redisUtil.hasKey(user.getUserAccount())) {
            redisUtil.remove(user.getUserAccount());
            redisUtil.put(user.getUserAccount(), user);
        }

        if (result > 0){
            return new JSONResult("插入成功", 200);
        }
        return new JSONResult("添加失败", 500);
    }

    @Override
    public JSONResult getTotalConsumption(DirectChargingOrderDto directChargingOrderDto) {
        System.out.println("进入统计消费总额");
        System.out.println(directChargingOrderDto);
        double result = directCustomerMapperExtra.getTotalConsumption(directChargingOrderDto);
        if (result > 0) {
            return new JSONResult("获取成功", 200, result);
        }
        return new JSONResult("添加失败", 500, result);
    }

    @Override
    public JSONResult getTotalRecharge(DirectChargingOrderDto directChargingOrderDto) {
        System.out.println("充值总数获取" + directChargingOrderDto);
        DirectCustomerManagerDto directCustomerManagerDto = directCustomerMapperExtra.getTotalRecharge(directChargingOrderDto);
        if (directChargingOrderDto != null){
            return new JSONResult("获取成功", 200, directCustomerManagerDto);
        }
        return new JSONResult("获取失败",500);
    }

    @Override
    public JSONResult getRechargeList(DirectChargingOrderDto directChargingOrderDto) {
        System.out.println("查询: "+directChargingOrderDto);
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getRechargeList(directChargingOrderDto);
        PageInfo<DirectCustomerManagerDto> pageInfo = new PageInfo<>(list);
        JSONResult result = new JSONResult("列表数据查询成功", 200, pageInfo);
        return result;
    }

    @Override
    public JSONResult getRechargeAccountList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getRechargeAccountList(directChargingOrderDto);
        JSONResult result = new JSONResult("列表数据查询成功", 200, list);
        return result;
    }

    @Override
    public JSONResult passwordVerification(String password, Integer isSpecial) {
        String pwd = directCustomerMapperExtra.getPassword();
        String sign = MD5Util.MD5Encode(password,"UTF-8").toLowerCase();
        if (pwd.equals(sign)) {
            return new JSONResult("校验成功", 200, true);
        }
        return new JSONResult("校验失败", 500, false);
    }

    @Override
    public JSONResult recharge(DirectCustomerManagerDto directCustomerManagerDto) {
        directCustomerManagerDto.setOrderId(StringUtil.createId());
        DirectCustomerManagerDto directCustomerManagerDto1 = directCustomerMapperExtra.getOneByAccount(directCustomerManagerDto);
        directCustomerManagerDto.setUserId(directCustomerManagerDto1.getUserId());
        directCustomerManagerDto.setBalance(directCustomerManagerDto1.getBalance()+directCustomerManagerDto.getRechargeAmount()-directCustomerManagerDto.getAmountRecovered());
        System.out.println("充值" + directCustomerManagerDto);
        int result = directCustomerMapperExtra.recharge(directCustomerManagerDto);
        directCustomerManagerDto.setAmountRecovered(directCustomerManagerDto1.getAmountRecovered()+directCustomerManagerDto.getAmountRecovered());
        directCustomerManagerDto.setRechargeAmount(directCustomerManagerDto.getRechargeAmount()+directCustomerManagerDto1.getRechargeAmount());
        int result2 = directCustomerMapperExtra.changeBalance(directCustomerManagerDto);
        if (result > 0 && result2 > 0) {
            return new JSONResult("获取成功", 200, result);
        }
        return new JSONResult("添加失败", 500, result);
    }

    @Override
    public JSONResult getAccountBalance(DirectCustomerManagerDto directCustomerManagerDto) {
        System.out.println(directCustomerManagerDto);
        DirectCustomerManagerDto directCustomerManagerDto1 = directCustomerMapperExtra.getAccountBalance(directCustomerManagerDto);
        if (directCustomerManagerDto1 !=null){
            return new JSONResult("获取成功", 200, directCustomerManagerDto1);
        }
        return new JSONResult("获取失败", 500);
    }

    @Override
    public JSONResult changePassword(DirectCustomerManagerDto directCustomerManagerDto) {
        System.out.println("修改密码" + directCustomerManagerDto.getOldPwd() + " : " + directCustomerManagerDto.getNewPwd());
        String oldPwd = directCustomerMapperExtra.getPassword();
        String oldSign = MD5Util.MD5Encode(directCustomerManagerDto.getOldPwd(),"UTF-8").toLowerCase();
        String newSign = MD5Util.MD5Encode(directCustomerManagerDto.getNewPwd(),"UTF-8").toLowerCase();
        directCustomerManagerDto.setNewPwd(newSign);
        if (oldSign.equals(oldPwd)){
            int result = directCustomerMapperExtra.changePassword(directCustomerManagerDto);
            return new JSONResult("修改成功", 200,result);
        }
        return new JSONResult("密码不正确", 500);
    }

    @Override
    public Workbook exportDirectAgent(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println(directChargingOrderDto);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getCustomerList(directChargingOrderDto);
        if(list==null||list.size()==0){
            return getDirectAgentWorkBook(null,directChargingOrderDto);
        }
        return getDirectAgentWorkBook(list, directChargingOrderDto);
    }

    @Override
    public Workbook exportDirectAgentConsumption(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("导出: " + directChargingOrderDto);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getConsumptionList(directChargingOrderDto);
        if(list==null||list.size()==0){
            return getDirectAgentConsumptionWorkBook(null,directChargingOrderDto);
        }
        return getDirectAgentConsumptionWorkBook(list, directChargingOrderDto);
    }

    @Override
    public Workbook exportDirectAgentRecharge(DirectChargingOrderDto directChargingOrderDto) throws Exception {
        System.out.println("导出: " + directChargingOrderDto);
        List<DirectCustomerManagerDto> list = directCustomerMapperExtra.getRechargeList(directChargingOrderDto);
        if(list==null||list.size()==0){
            return getDirectAgentRechargeWorkBook(null,directChargingOrderDto);
        }
        return getDirectAgentRechargeWorkBook(list, directChargingOrderDto);
    }

    private Workbook getDirectAgentRechargeWorkBook (List<DirectCustomerManagerDto> list,DirectChargingOrderDto directChargingOrderDto) throws Exception {
        String[] DirectAgentHead = SystemConstants.DIRECT_AGENT_RECHARGE_EXCEL_HEAD;
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
        for (int i = 0; i < DirectAgentHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(DirectAgentHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(list.get(i).getRechargeAmount());
            row.createCell(count++).setCellValue(list.get(i).getAmountRecovered());
            row.createCell(count++).setCellValue(list.get(i).getBalance());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRegisterTime()));
        }
        return workbook;
    }

    private Workbook getDirectAgentConsumptionWorkBook (List<DirectCustomerManagerDto> list,DirectChargingOrderDto directChargingOrderDto) throws Exception {
        String[] DirectAgentHead = SystemConstants.DIRECT_AGENT_CONSUMPTION_EXCEL_HEAD;
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
        for (int i = 0; i < DirectAgentHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(DirectAgentHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getRechargeAccount());
            row.createCell(count++).setCellValue(list.get(i).getRechargeAmount());
            if (1 == list.get(i).getState()){
                row.createCell(count++).setCellValue("待充值");
            }else if (0 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("未支付");
            }else if (2 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("已充值");
            }
            else if (3 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("支付失败");
            }
            else if (4 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("充值失败");
            }
            else if (5 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("充值中");
            }
            else if (6 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("已退款");
            }
            else if (7 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("当日超次数");
            }else if (8 == (list.get(i).getState())){
                row.createCell(count++).setCellValue("携号转网");
            }else {
                row.createCell(count++).setCellValue("携号转网");
            }
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRegisterTime()));
        }
        return workbook;
    }

    private Workbook getDirectAgentWorkBook(List<DirectCustomerManagerDto> list,DirectChargingOrderDto directChargingOrderDto) throws Exception {
        String[] DirectAgentHead = SystemConstants.DIRECT_AGENT_EXCEL_HEAD;
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
        for (int i = 0; i < DirectAgentHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(DirectAgentHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getAccount());
            row.createCell(count++).setCellValue(list.get(i).getBalance());
            row.createCell(count++).setCellValue(list.get(i).getConsumptionAmount());
            row.createCell(count++).setCellValue(list.get(i).getRechargeAmount());
            row.createCell(count++).setCellValue(list.get(i).getAmountRecovered());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getRegisterTime()));
        }
        return workbook;
    }
}
