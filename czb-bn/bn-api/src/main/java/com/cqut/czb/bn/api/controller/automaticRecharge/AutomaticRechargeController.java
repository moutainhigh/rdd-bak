package com.cqut.czb.bn.api.controller.automaticRecharge;

import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.automaticRechargeService.AutomaticRechargeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/automaticRecharge")
public class AutomaticRechargeController {

    @Autowired
    AutomaticRechargeService automaticRechargeService;

    /**
     * 获取信息
     * @return
     */
    @RequestMapping(value = "/getTableList",method = RequestMethod.POST)
    public JSONResult getTableList(AutomaticRechargeDTO automaticRechargeDTO){
        return automaticRechargeService.getAutoList(automaticRechargeDTO);
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping(value = "/deleteRecorder",method = RequestMethod.POST)
    public JSONResult deleteRecorder(String id){
        return automaticRechargeService.deleteRecorder(id);
    }

    /**
     * 修改信息
     * @return
     */
    @RequestMapping(value = "/editRecorder",method = RequestMethod.POST)
    public JSONResult editRecorder(AutomaticRechargeDTO automaticRechargeDTO){
        return automaticRechargeService.editRecorder(automaticRechargeDTO);
    }

    /**
     * 查看信息
     * @return
     */
    @RequestMapping(value = "/showRecorder",method = RequestMethod.POST)
    public JSONResult showRecorder(String id){
        return automaticRechargeService.showRecorder(id);
    }

    /**
     * 插入记录
     * @param autoRechargeRecord
     * @return
     */
    @RequestMapping(value = "/insertRecord",method = RequestMethod.POST)
    public JSONResult insertRecord(AutoRechargeRecord autoRechargeRecord){
        return new JSONResult(automaticRechargeService.insertRecord(autoRechargeRecord));
    }

    /**
     * 下载Excel表
     * @param response
     * @param request
     * @param pageDTO
     * @return
     */
    @PostMapping("/exportData")
    public JSONResult ExportDate(HttpServletResponse response, HttpServletRequest request,AutomaticRechargeDTO pageDTO){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = automaticRechargeService.exportOrderRecords(pageDTO);
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
            fileName = "自动充值记录.xlsx";
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
