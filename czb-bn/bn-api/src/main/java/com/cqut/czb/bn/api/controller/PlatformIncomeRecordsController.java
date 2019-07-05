package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PlatformIncomeRecordsService;
import com.cqut.czb.bn.service.impl.platformIncomeRecord.ImportPlatformIncome;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
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

/**
 * 收款记录
 */
@EnableAsync
@RestController
@RequestMapping("/api/platform")
public class PlatformIncomeRecordsController {

    @Autowired
    PlatformIncomeRecordsService platformIncomeRecordsService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取列表/查询
     */
    @GetMapping("/getPlatformRecordList")
    public JSONResult getPlatformRecordList(PlatformIncomeRecordsDTO platformIncomeRecordsDTO, PageDTO pageDTO) {
        return new JSONResult(platformIncomeRecordsService.getReceiveRecords(platformIncomeRecordsDTO, pageDTO));
    }

    /**
     * 导出excel表
     * @param request
     * @param response
     * @param platformIncomeRecordsDTO
     * @return
     */
    @PostMapping("/exportPlatformRecords")
    public JSONResult exportPlatformRecords(HttpServletRequest request, HttpServletResponse response, PlatformIncomeRecordsDTO platformIncomeRecordsDTO) {
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = platformIncomeRecordsService.exportRecords(platformIncomeRecordsDTO);
            if (workbook == null) {
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
            fileName = URLEncoder.encode(fileName, "utf-8");
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
     * 为多个公司分配油卡
     */
    @GetMapping("/distributionManyPetrols")
    public JSONResult distributionManyPetrols(@Param("contractRecordIds") String contractRecordIds,@Param("recordId") String recordId) {
        return new JSONResult(platformIncomeRecordsService.handleManyPlatFormIncomeRecords(contractRecordIds,recordId));
    }

    /**
     * 为一个公司分配油卡
     * @return
     */
    @GetMapping("/distributionOnePetrols")
    public JSONResult distributionOnePetrols(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) {
        return new JSONResult(platformIncomeRecordsService.selectSonContractId(platformIncomeRecordsDTO));
    }


    @PostMapping("/importIncomeRecords")
    public JSONResult impoertIncomeRecords(MultipartFile file) throws Exception {
        try{
            return new JSONResult(platformIncomeRecordsService.importRecords(file));

        } catch (Exception e){
            ImportPlatformIncome.processing=0.0;
            ImportPlatformIncome.processNum=0.0;
            return new JSONResult("上传文件出现错误",110);
        }
    }

    /**
     *查看平台收款记录导入进度
     * @return
     */
    @GetMapping("/searchProcess")
    public JSONResult searchProcess() {
        if( ImportPlatformIncome.processing.intValue() ==100){  //如果进度为100则当前台调用时归零
            ImportPlatformIncome.processing=0.0;
            ImportPlatformIncome.processNum=0.0;
            return new JSONResult(100);
        }
        return new JSONResult(ImportPlatformIncome.processing);
    }

    /**
     * 查看油卡
     * @param platformIncomeRecordsDTO
     * @return
     */
    @PostMapping("/selectPetrol")
    public JSONResult selectPetrol(PlatformIncomeRecordsDTO platformIncomeRecordsDTO, PageDTO pageDTO) {
        return new JSONResult(platformIncomeRecordsService.selectPetrol(platformIncomeRecordsDTO, pageDTO));
    }

    @GetMapping("/selectState")
    public JSONResult selectState(Principal principal){
        System.out.println(principal==null);
        User user = (User) redisUtils.get(principal.getName());
        System.out.println(user==null);
        return new JSONResult(platformIncomeRecordsService.selectPayState(user));
    }
}
