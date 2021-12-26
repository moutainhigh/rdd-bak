package com.cqut.czb.bn.api.controller.electricityRecharge;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/electricityRecharge")
public class ElectricityRechargeController {
    @Autowired
    ElectricityRechargeService electricityRechargeService;

    @Autowired
    RedisUtils redisUtils;

    //表单获取
    @PostMapping("/getCustomers")
    public JSONResult getCustomers(ElectricityRechargeDto electricityRechargeDto){
        System.out.println(electricityRechargeDto);
        return electricityRechargeService.getCustomers(electricityRechargeDto);
    }

    //统计获取
    @PostMapping("/getTotal")
    public JSONResult getTotal(ElectricityRechargeDto electricityRechargeDto){
        System.out.println(electricityRechargeDto);
        return electricityRechargeService.getTotal(electricityRechargeDto);
    }

    //修改状态
    @PostMapping("/editOrder")
    public JSONResult editOrder(ElectricityRechargeDto electricityRechargeDto){
        System.out.println(electricityRechargeDto);
        return electricityRechargeService.editOrder(electricityRechargeDto);
    }

    //批量修改状态
    @PostMapping("/updateList")
    public JSONResult updateList(ElectricityRechargeDto electricityRechargeDto){
        System.out.println(electricityRechargeDto);
        return electricityRechargeService.updateList(electricityRechargeDto);
    }

    //下载文件
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/downloadData",method = RequestMethod.POST)
    public JSONResult downloadData(HttpServletResponse response, HttpServletRequest request,ElectricityRechargeDto electricityRechargeDto){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = electricityRechargeService.exportData(electricityRechargeDto);
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
            String fileName = "水电费记录.xlsx";
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

    /*
     *导入文件
     */
    @PostMapping("/importData")
    public JSONResult importData(MultipartFile file, Integer recordType) {
        try {
            electricityRechargeService.importData(file,recordType);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(500, "failed");
        }
        return new JSONResult(200, "success");
    }
}
