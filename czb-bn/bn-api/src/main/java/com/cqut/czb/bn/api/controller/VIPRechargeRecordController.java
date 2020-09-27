package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.VIPRechargeRecordService;
import com.cqut.czb.bn.util.RedisUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description VIP充值记录管理
 * @auther nihao
 * @create 2019-07-18 22:23
 */

@RestController
@RequestMapping("/api/VIPRechargeRecord")
public class VIPRechargeRecordController {

    @Autowired
    VIPRechargeRecordService vipRechargeRecordService;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/getVIPRechargeRecordList")
    public JSONResult getVipRechargeRecordList(VipRechargeRecordListDTO vipRechargeRecordListDTO, Principal principal){
        User user = (User)redisUtil.get(principal.getName());
        vipRechargeRecordListDTO.setIsSpecial(user.getIsSpecial());
        return new JSONResult(vipRechargeRecordService.getVipRechargeRecordList(vipRechargeRecordListDTO));
    }

    @PostMapping("/deleteVIPRechargeByID")
    public JSONResult deleteVIPRechargeByID(@Param("vipRechargeRecordId")String vipRechargeRecordId){
        return new JSONResult(vipRechargeRecordService.deleteVIPRechargeByID(vipRechargeRecordId));
    }

    @PostMapping("addVipMoney")
    public JSONResult addVipMoney(@RequestBody VipRechargeRecordListDTO vipRechargeRecordListDTO){
        return new JSONResult(vipRechargeRecordService.addVipMoney(vipRechargeRecordListDTO));
    }

    /**
     * 下载Excel表
     * @param response
     * @param request
     * @param pageDTO
     * @return
     */
    @PostMapping("/exportData")
    public JSONResult ExportDate(HttpServletResponse response, HttpServletRequest request, VipRechargeRecordListDTO pageDTO){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = vipRechargeRecordService.exportOrderRecords(pageDTO);
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
            fileName = "VIP充值记录.xlsx";
            //System.out.println(fileName);
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            //System.out.println("====="+out.toString());
            out.close();
        } catch (IOException e) {
            message = "导出Excel数据失败，请稍后再试";
        } catch (Exception e1) {
            e1.printStackTrace();
            message = "Excel数据量过大，请调整导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }
}
