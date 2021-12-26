package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.AutoDirectDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.dto.directChargingSystem.SelectOrderDto;
import com.cqut.czb.bn.entity.dto.until.WXSign;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cqut.czb.bn.entity.dto.weChatAppletPushNotification.sendNotification;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.cqut.czb.bn.entity.dto.until.WXSign.httpRequest;

@RestController
@RequestMapping("/oilCardRecharge")
public class OilCardRechargeController {
    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取当前用户所有订单
     * @param principal
     * @return
     */
    @PostMapping("/getOrderInfoList")
    public JSONResult getInfoNum(Principal principal, DirectChargingOrderDto directChargingOrderDto){
        String userId = "";
        if (directChargingOrderDto.getIsNeedLogin() == 1) {
            userId = ((User)redisUtils.get(principal.getName())).getUserId();
            return new JSONResult(oilCardRechargeService.getOrderInfoList(userId, directChargingOrderDto.getRecordType()));
        } else {
            userId = directChargingOrderDto.getUserId();
            return new JSONResult(oilCardRechargeService.getOnceOrderInfoList(userId, directChargingOrderDto.getRecordType()));
        }
    }

    /**
     * 获取所有订单
     * @return
     */
    @PostMapping ("/getAllOrderInfoList")
    public JSONResult getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getAllOrderInfoList(directChargingOrderDto));
    }

    /**
     * 获取所有订单
     * @return
     */
    @PostMapping ("/getAllOnceOrderInfoList")
    public JSONResult getAllOnceOrderInfoList(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getAllOnceOrderInfoList(directChargingOrderDto));
    }

    /**
     * 绑定油卡
     * @param principal
     * @param oilCardBinging
     * @return
     */
    @PostMapping("/bindingOilCard")
    public JSONResult bindingOilCard(Principal principal, OilCardBinging oilCardBinging){
        User user = (User)redisUtils.get(principal.getName());
        return oilCardRechargeService.bindingOilCard(user.getUserId(), oilCardBinging);
    }

    @PostMapping("/getAllUserCard")
    public JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto){
        return oilCardRechargeService.getAllUserCard(directChargingOrderDto);
    }


    /**
     *
     * @return
     */
    @PostMapping("/updateOrderState")
    @ResponseBody
    public JSONResult updateOrderState(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.updateOrderState(directChargingOrderDto));
    }

    /**
     * 油卡充值状态
     * @return
     */
    @PostMapping("/getOilOrderState")
    @ResponseBody
    public JSONResult getOilOrderState(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getOilOrderState(directChargingOrderDto));
    }

    /**
     * 话费充值状态
     * @return
     */
    @PostMapping("/getPhoneOrderState")
    @ResponseBody
    public JSONResult getPhoneOrderState(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getPhoneOrderState(directChargingOrderDto));
    }

    /**
     * 话费充值状态
     * @return
     */
    @PostMapping("/getSelectPhoneOrderState")
    @ResponseBody
    public JSONResult getSelectPhoneOrderState(SelectOrderDto selectOrderDto){
        System.out.println(selectOrderDto);
        return oilCardRechargeService.getSelectPhoneOrderState(selectOrderDto);
    }

    /**
     * 话费充值状态
     * @return
     */
    @PostMapping("/getSelectOilOrderState")
    @ResponseBody
    public JSONResult getSelectOilOrderState(SelectOrderDto selectOrderDto){
        System.out.println(selectOrderDto);
        return oilCardRechargeService.getSelectOilOrderState(selectOrderDto);
    }

    /**
     * 是否可以充值
     * @return
     */
    @PostMapping("/isPhoneRecharge")
    @ResponseBody
    public JSONResult isPhoneRecharge(DirectChargingOrderDto directChargingOrderDto){
        return oilCardRechargeService.isPhoneRecharge(directChargingOrderDto);
    }

    /**
     * 是否需要登录
     * @return
     */
    @GetMapping("/isNeedLogin")
    @ResponseBody
    public JSONResult isNeedLogin(){
        return oilCardRechargeService.isNeedLogin();
    }

    /**
     * 话费重新提交
     * @return
     */
    @PostMapping("/phoneRechargeSubmission")
    @ResponseBody
    public JSONResult phoneRechargeSubmission(DirectChargingOrderDto directChargingOrderDto){
        oilCardRechargeService.phoneRechargeSubmission(directChargingOrderDto);
        return new JSONResult("重新提交成功", 200);
    }

    /**
     * 油卡重新提交
     * @return
     */
    @PostMapping("/onlineorderSubmission")
    @ResponseBody
    public JSONResult onlineorderSubmission(DirectChargingOrderDto directChargingOrderDto){
        oilCardRechargeService.onlineorderSubmission(directChargingOrderDto);
        return new JSONResult("重新提交成功", 200);
    }

    /**
     * 导出油卡、话费数据
     */
    @PostMapping("/exportOilCardRecord")
    @PermissionCheck(role = "管理员")
    public JSONResult exportOilCardRecord(HttpServletResponse response, HttpServletRequest request, DirectChargingOrderDto directChargingOrderDto){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = oilCardRechargeService.exportOilCardRecord(directChargingOrderDto);
            System.out.println("wwww"+workbook);
            if(workbook == null) {
                //           workbook = new SXSSFWorkbook();
                message = "当前月没有未导出的数据啦";
                result.put("message", message);
                return new JSONResult(result);
            }
            //设置对客户端请求的编码格式
            request.setCharacterEncoding("utf-8");
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = null;
            fileName = "油卡订单记录.xlsx";
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            message = "导出Excel数据失败，请稍后再试";
        } catch (Exception e1) {
            e1.printStackTrace();
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }

    /**
     * 话费自动提交订单
     */
    @PostMapping("/automaticSubmitPhone")
    @ResponseBody
    public JSONResult automaticSubmitPhone(AutoDirectDto autoDirectDto){
        return oilCardRechargeService.automaticSubmitPhone(autoDirectDto);
    }

    /**
     * 油卡充值状态
     * @return
     */
    @PostMapping("/automaticSubmitOilCard")
    @ResponseBody
    public JSONResult automaticSubmitOilCard(DictInputDTO dictInputDTO){
        return oilCardRechargeService.automaticSubmitOilCard(dictInputDTO);
    }

    /*
     *导入文件
     */
    @PostMapping("/importData")
    public JSONResult importData(MultipartFile file, Integer recordType) {
        try {
            oilCardRechargeService.importData(file,recordType);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(500, "failed");
        }
        return new JSONResult(200, "success");
    }

    /**
     * 话费充值状态
     * @return
     */
    @PostMapping("/submitSelectState")
    @ResponseBody
    public JSONResult submitSelectState(SelectOrderDto selectOrderDto){
        System.out.println(selectOrderDto);
        return oilCardRechargeService.submitSelectState(selectOrderDto);
    }
}


