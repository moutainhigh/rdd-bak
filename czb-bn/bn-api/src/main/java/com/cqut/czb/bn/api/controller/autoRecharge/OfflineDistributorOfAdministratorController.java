package com.cqut.czb.bn.api.controller.autoRecharge;

import com.cqut.czb.auth.interceptor.PermissionCheck;

import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.RechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/OfflineDistributorRecords")
public class OfflineDistributorOfAdministratorController {
    @Autowired
    OfflineDistributorOfAdministratorService offlineDistributorOfAdministratorService;

    /**
     * 获取账户充值记录
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getRechargeTableList",method = RequestMethod.POST)
    public JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO){
        return offlineDistributorOfAdministratorService.getRechargeTableList(accountRechargeDTO);
    }

    /**
     * 获取所有线下消费记录
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getOfflineConsumptionList",method = RequestMethod.POST)
    public JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO){
        return offlineDistributorOfAdministratorService.getOfflineConsumptionList(offlineConsumptionDTO);
    }

    /**
     * 获取代理商信息
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getOfflineClientList",method = RequestMethod.POST)
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO){
        return offlineDistributorOfAdministratorService.getOfflineClientList(offlineClientDTO);
    }

    /**
     * 账户联动
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getRechargeAccountList",method = RequestMethod.POST)
    public JSONResult getRechargeAccountList(){
        return offlineDistributorOfAdministratorService.getRechargeAccountList();
    }

    /**
     * 获取余额
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getAccountBalance",method = RequestMethod.POST)
    public JSONResult getAccountBalance(String account){
        return offlineDistributorOfAdministratorService.getAccountBalance(account);
    }

    /**
     * 管理员充值圈回
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/rechargeAndTurn",method = RequestMethod.POST)
    public JSONResult rechargeAndTurn(RechargeDTO rechargeDTO){
        return offlineDistributorOfAdministratorService.rechargeAndTurn(rechargeDTO);
    }

    /**
     * 充值记录导出
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportRechargeRecords",method = RequestMethod.POST)
    public JSONResult exportRechargeRecords(HttpServletResponse response, AccountRechargeDTO accountRechargeDTO){
        String message = null;
        Workbook workbook = null;
        try {
            workbook = offlineDistributorOfAdministratorService.exportRechargeRecords(accountRechargeDTO);
            if(workbook == null) {
                workbook = new SXSSFWorkbook();
            }
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = "线下大客户充值记录.xlsx";
            //System.out.println(fileName);
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            message = "导出Excel数据失败，请稍后再试";
        }

        return new JSONResult(message);
    }

    /**
     * 线下消费记录导出
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportConsumptionRecords",method = RequestMethod.POST)
    public JSONResult exportConsumptionRecords(HttpServletResponse response, OfflineConsumptionDTO offlineConsumptionDTO){
        String message = null;
        Workbook workbook = null;
        try {
            workbook = offlineDistributorOfAdministratorService.exportConsumptionRecords(offlineConsumptionDTO);
            if(workbook == null) {
                workbook = new SXSSFWorkbook();
            }
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = "线下大客户消费记录.xlsx";
            //System.out.println(fileName);
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            message = "导出Excel数据失败，请稍后再试";
        }
        return new JSONResult(message);
    }

    /**
     * 导出线下大客户余额
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportClientRecords",method = RequestMethod.POST)
    public JSONResult exportClientRecords(HttpServletResponse response, OfflineClientDTO offlineClientDTO){
        String message = null;
        Workbook workbook = null;
        try {
            workbook = offlineDistributorOfAdministratorService.exportClientRecords(offlineClientDTO);
            if(workbook == null) {
                workbook = new SXSSFWorkbook();
            }
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = "线下大客户余额记录.xlsx";
            //System.out.println(fileName);
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            message = "导出Excel数据失败，请稍后再试";
        }
        return new JSONResult(message);
    }
}
