package com.cqut.czb.bn.api.controller.directCustomerManager;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directCustomerManager.DirectCustomerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/directCustomerManager")
public class DirectCustomerManagerController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    DirectCustomerService directCustomerService;

    /**
     * 获取信息
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getDirectCustomer",method = RequestMethod.POST)
    public JSONResult getDirectCustomer(DirectChargingOrderDto directChargingOrderDto, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return directCustomerService.getCustomerData(directChargingOrderDto);
    }

    /**
     * 获取线下消费信息
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getConsumptionList",method = RequestMethod.POST)
    public JSONResult getConsumptionList(DirectChargingOrderDto directChargingOrderDto, Principal principal){
        System.out.println("传入" + directChargingOrderDto);
        User user = (User)redisUtils.get(principal.getName());
        return directCustomerService.getConsumptionList(directChargingOrderDto);
    }

    /**
     * 添加代理人
     * @return
     */
    @PostMapping("/addAgent")
    public JSONResult getAllOnceOrderInfoList(DirectCustomerManagerDto directCustomerManagerDto){
        return new JSONResult(directCustomerService.addAgent(directCustomerManagerDto));
    }

    /**
     * 返回消费总数
     * @return
     */
    @PostMapping("/getTotalConsumption")
    public JSONResult getTotalConsumption(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(directCustomerService.getTotalConsumption(directChargingOrderDto));
    }

    /**
     * 返回充值总数
     * @return
     */
    @PostMapping("/getTotalRecharge")
    public JSONResult getTotalRecharge(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(directCustomerService.getTotalRecharge(directChargingOrderDto));
    }

    /**
     * 获取充值信息
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getRechargeList",method = RequestMethod.POST)
    public JSONResult getRechargeList(DirectChargingOrderDto directChargingOrderDto, Principal principal){
        System.out.println("传入" + directChargingOrderDto);
        User user = (User)redisUtils.get(principal.getName());
        return directCustomerService.getRechargeList(directChargingOrderDto);
    }

    /**
     * 获取充值信息
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getRechargeAccountList",method = RequestMethod.POST)
    public JSONResult getRechargeAccountList(DirectChargingOrderDto directChargingOrderDto, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return directCustomerService.getRechargeAccountList(directChargingOrderDto);
    }

    /**
     * 验证财务密码
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/passwordVerification",method = RequestMethod.POST)
    public JSONResult passwordVerification(String password,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return directCustomerService.passwordVerification(password,user.getIsSpecial());
    }

    /**
     * 充值
     * @return
     */
    @PostMapping("/recharge")
    public JSONResult recharge(DirectCustomerManagerDto directCustomerManagerDto){
        return new JSONResult(directCustomerService.recharge(directCustomerManagerDto));
    }

    /**
     * 获取余额
     * @return
     */
    @PostMapping("/getAccountBalance")
    public JSONResult getAccountBalance(DirectCustomerManagerDto directCustomerManagerDto){
        return new JSONResult(directCustomerService.getAccountBalance(directCustomerManagerDto));
    }

    /**
     * 修改密码
     * @return
     */
    @PostMapping("/changePassword")
    public JSONResult changePassword(DirectCustomerManagerDto directCustomerManagerDto){
        return new JSONResult(directCustomerService.changePassword(directCustomerManagerDto));
    }

    /**
     * 导出直充代理人详情
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportDirectAgent",method = RequestMethod.POST)
    public JSONResult exportDirectAgent(HttpServletResponse response, HttpServletRequest request, DirectChargingOrderDto directChargingOrderDto, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = directCustomerService.exportDirectAgent(directChargingOrderDto);
            if(workbook == null) {
                message = "当前没有未导出的数据啦";
                result.put("message", message);
                return new JSONResult(result);
            }
            //设置对客户端请求的编码格式
            request.setCharacterEncoding("utf-8");
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
        }  catch (IOException e) {
            message = "导出Excel数据失败，请稍后再试";
        } catch (Exception e1) {
            e1.printStackTrace();
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }

    /**
     * 导出线下大客户详情
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportDirectAgentConsumption",method = RequestMethod.POST)
    public JSONResult exportDirectAgentConsumption(HttpServletResponse response, HttpServletRequest request, DirectChargingOrderDto directChargingOrderDto, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = directCustomerService.exportDirectAgentConsumption(directChargingOrderDto);
            if(workbook == null) {
                message = "当前没有未导出的数据啦";
                result.put("message", message);
                return new JSONResult(result);
            }
            //设置对客户端请求的编码格式
            request.setCharacterEncoding("utf-8");
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
        }  catch (IOException e) {
            message = "导出Excel数据失败，请稍后再试";
        } catch (Exception e1) {
            e1.printStackTrace();
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }

    /**
     * 导出线下大客户详情
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportDirectAgentRecharge",method = RequestMethod.POST)
    public JSONResult exportDirectAgentRecharge(HttpServletResponse response, HttpServletRequest request, DirectChargingOrderDto directChargingOrderDto, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = directCustomerService.exportDirectAgentRecharge(directChargingOrderDto);
            if(workbook == null) {
                message = "当前没有未导出的数据啦";
                result.put("message", message);
                return new JSONResult(result);
            }
            //设置对客户端请求的编码格式
            request.setCharacterEncoding("utf-8");
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
        }  catch (IOException e) {
            message = "导出Excel数据失败，请稍后再试";
        } catch (Exception e1) {
            e1.printStackTrace();
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }

}
