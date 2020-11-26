package com.cqut.czb.bn.api.controller.withoutCard;

import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.withoutCard.PetrolSalesWithoutDto;
import com.cqut.czb.bn.entity.global.DateDealWith;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardSellManagementService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 作者： 陈爽
 * 模块： 无卡加油消费记录管理
 * 创建时间： 2020/11/17
 */

@RestController
@RequestMapping("/api/withoutCardSellManagement")
public class WithoutCardSellManagementController {

    @Autowired
    WithoutCardSellManagementService withoutCardSellManagementService;

    @PostMapping("/listPetrolSellManagement")
    public JSONResult listPetrolSellManagement(PetrolSalesWithoutDto petrolSalesWithoutDto){
        DataWithCountOutputDTO dataWithCountOutputDTO = new DataWithCountOutputDTO();
        dataWithCountOutputDTO.setData(withoutCardSellManagementService.listPetrolSellManagement(petrolSalesWithoutDto));
        dataWithCountOutputDTO.setCount(withoutCardSellManagementService.getPetrolSellManagementTotal(petrolSalesWithoutDto));
        petrolSalesWithoutDto.setStartTime(DateDealWith.backStartTime());
        petrolSalesWithoutDto.setEndTime(DateDealWith.backEndTime());
        // 今日金额跟数量
        dataWithCountOutputDTO.setTodayCount(withoutCardSellManagementService.getPetrolSellManagementTotal(petrolSalesWithoutDto));
        dataWithCountOutputDTO.setTodayNum(withoutCardSellManagementService.listPetrolSellManagement(petrolSalesWithoutDto).getSize() + "");
        return new JSONResult(dataWithCountOutputDTO);
    }


    @PostMapping("/exportSaleRecords")
    public JSONResult exportSaleRecords(HttpServletResponse response,PetrolSalesWithoutDto petrolSalesWithoutDto){

        String message = null;
        Workbook workbook = null;
        try {
            workbook = withoutCardSellManagementService.exportSaleRecords(petrolSalesWithoutDto);
            if(workbook == null) {
                workbook = new SXSSFWorkbook();
            }
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = "油卡销售记录.xlsx";
            //System.out.println(fileName);
            //将中文转换为16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            //确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            message = "导出Excel数据失败，请稍后再试";
        }

        return new JSONResult(message);
    }
}
