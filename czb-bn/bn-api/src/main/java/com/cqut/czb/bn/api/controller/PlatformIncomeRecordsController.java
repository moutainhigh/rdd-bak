package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PlatformIncomeRecordsService;
import org.apache.http.HttpResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * 收款记录
 */
@RestController
@RequestMapping("/api/platform")
public class PlatformIncomeRecordsController {

    @Autowired
    PlatformIncomeRecordsService platformIncomeRecordsService;
    /**
     * 获取列表/查询
     */
    @GetMapping("/getPlatformRecordList")
    public JSONResult getPlatformRecordList(PlatformIncomeRecordsDTO platformIncomeRecordsDTO, PageDTO pageDTO){
        return new JSONResult(platformIncomeRecordsService.getReceiveRecords(platformIncomeRecordsDTO,pageDTO));
    }

    /**
     * 导出excel表
     * @param request
     * @param response
     * @param platformIncomeRecordsDTO
     * @return
     */
    @GetMapping("/exportPlatformRecords")
    public JSONResult exportPlatformRecords(HttpServletRequest request, HttpServletResponse response, PlatformIncomeRecordsDTO platformIncomeRecordsDTO){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = platformIncomeRecordsService.exportRecords(platformIncomeRecordsDTO);
            if(workbook == null) {
       //         workbook = new SXSSFWorkbook();
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
            fileName = "企业打款记录.xlsx";
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
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }

    /**
     * 确认已打款
     */
    @GetMapping("/confirmReceipt")
    public JSONResult confirmReceipt(PlatformIncomeRecordsDTO platformIncomeRecordsDTO){
        return new JSONResult(platformIncomeRecordsService.ConfirmReceipt(platformIncomeRecordsDTO));
    }


    @PostMapping("impoertIncomeRecords")
    public JSONResult impoertIncomeRecords(@Param("file")MultipartFile file) throws Exception{
        return new JSONResult(platformIncomeRecordsService.importRecords(file));
    }
}
