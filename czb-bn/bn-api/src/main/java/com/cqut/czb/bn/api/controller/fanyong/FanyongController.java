package com.cqut.czb.bn.api.controller.fanyong;

import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityDto;
import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.fanyong.FanyongLogService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fanyongLog")
public class FanyongController {

    @Autowired
    FanyongLogService fanyongLogService;

    @GetMapping("/getLogData")
    public JSONResult getLogData(FanyongLogDto fanyongLogDto){
        System.out.println(fanyongLogDto);
        return fanyongLogService.getLogData(fanyongLogDto);
    }

    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/exportFanyongLog",method = RequestMethod.POST)
    public JSONResult exportFanyongLog(HttpServletResponse response, HttpServletRequest request, FanyongLogDto fanyongLogDto){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = fanyongLogService.exportFanyongLog(fanyongLogDto);
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

    @GetMapping("/getFanyongTotalAmount")
    public JSONResult getFanyongTotalAmount(FanyongLogDto fanyongLogDto){
        return fanyongLogService.getFanyongTotalAmount(fanyongLogDto);
    }
}
