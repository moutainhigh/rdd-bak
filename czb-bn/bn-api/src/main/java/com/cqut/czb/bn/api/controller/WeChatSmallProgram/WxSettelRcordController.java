package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/WxSettelRcord")
public class WxSettelRcordController {

    @Autowired
    WxSettelRcordService wxSettelRcordService;

    @PermissionCheck(role = "管理员")
    @GetMapping(value ="/selectSettleRcord")
    public JSONResult selectSettleRcord(WxSettleRcordDTO pageDTO){
        return wxSettelRcordService.getSettleRcord(pageDTO);
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/settleRecord")
    public JSONResult settleRecord(@RequestBody WxSettleRcordDTO wxSettleRcordDTO){
        return  wxSettelRcordService.settleRecord(wxSettleRcordDTO);
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/deleteSettleRecord")
    public JSONResult deleteSettleRecord(@RequestBody WxSettleRcordDTO wxSettleRcordDTO){
        return  wxSettelRcordService.deleteSettleRecord(wxSettleRcordDTO);
    }

    /**
     * 下载excel表格
     */
    @PostMapping("/exportRecords")
    @PermissionCheck(role = "管理员")
    public JSONResult exportPertrolRecord(HttpServletResponse response, HttpServletRequest request, WxSettleRcordDTO pageDTO)
    {
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = wxSettelRcordService.exportOrderRecords(pageDTO);
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
            fileName = "订单结算记录.xlsx";
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
            message = "Excel数据量过大，请缩短导出文件的时间间隔";
        }
        result.put("message", message);
        return new JSONResult(result);
    }
}
