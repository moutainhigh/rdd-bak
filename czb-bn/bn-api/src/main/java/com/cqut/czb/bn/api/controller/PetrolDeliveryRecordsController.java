package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PetrolDeliveryRecordsService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**   PetrolDeliveryRecordsController  油卡快递模块
 *
 */
@RequestMapping("/petrolDelivery")
@RestController
public class PetrolDeliveryRecordsController {
    @Autowired
    PetrolDeliveryRecordsService petrolDeliveryRecordsService;

    @GetMapping("/selectRecords")
    public JSONResult selectRecords(DeliveryInput deliveryInput, PageDTO pageDTO){
        return new JSONResult(petrolDeliveryRecordsService.selectPetrolDelivery(deliveryInput,pageDTO));
    }
    @PostMapping("/receivePetrolDelivery")
    public JSONResult receivePetrolDelivery(@RequestBody DeliveryInput deliveryInput){
        return new JSONResult(petrolDeliveryRecordsService.receivePetrolDelivery(deliveryInput.getIds()));
    }

    @PostMapping("/updateRecords")
    public JSONResult updateRecords(@RequestBody DeliveryInput deliveryInput){
        return new JSONResult(petrolDeliveryRecordsService.updatePetrolDelivery(deliveryInput));
    }

    @GetMapping("/exportRecords")
    public JSONResult exportPertrolRecord(HttpServletResponse response, HttpServletRequest request,
                                                       DeliveryInput deliveryInput) {
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = petrolDeliveryRecordsService.exportDeliveryRecords(deliveryInput);
            if(workbook == null) {
                workbook = new SXSSFWorkbook();
            }
            //设置对客户端请求的编码格式
            request.setCharacterEncoding("utf-8");
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = null;
            fileName = "油卡寄送记录.xlsx";
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

    @PostMapping("importRecords")
    public JSONResult importRecords(MultipartFile file){
        try {
            petrolDeliveryRecordsService.ImportDeliveryRecords(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONResult("success");
    }
}
