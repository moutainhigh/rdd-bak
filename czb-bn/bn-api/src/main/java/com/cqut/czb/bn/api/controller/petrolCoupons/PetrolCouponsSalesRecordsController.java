package com.cqut.czb.bn.api.controller.petrolCoupons;

import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.global.DateDealWith;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolCoupons.PetrolCouponsSalesRecordsService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 作者： 陈爽
 * 模块：中石化优惠券管理
 * 创建时间： 2020/4/26
 */
@CrossOrigin
@RestController
@RequestMapping("/api/PetrolCoupons")
public class PetrolCouponsSalesRecordsController {

    @Autowired
    PetrolCouponsSalesRecordsService petrolCouponsSalesRecordsService;

    /**
     * 初始化数据，查询
     * @param input
     * @return
     */
    @GetMapping("/selectPetrolCouponsSalesRecords")
    public JSONResult selectVipApply(GetPetrolSaleInfoInputDTO input) {
        DataWithCountOutputDTO dataWithCountOutputDTO = new DataWithCountOutputDTO();
        dataWithCountOutputDTO.setData(petrolCouponsSalesRecordsService.selectPetrolCouponsSalesRecords(input));
        dataWithCountOutputDTO.setCount(petrolCouponsSalesRecordsService.getPetrolCouponsSaleMoneyCount(input));
        //获取今日销售数据
        GetPetrolSaleInfoInputDTO inputDTO2=new GetPetrolSaleInfoInputDTO();
        inputDTO2.setStartTime(DateDealWith.backStartTime());
        inputDTO2.setEndTime(DateDealWith.backEndTime());
        dataWithCountOutputDTO.setTodayCount(petrolCouponsSalesRecordsService.getPetrolCouponsSaleMoneyCount(inputDTO2));
        dataWithCountOutputDTO.setTodayNum(petrolCouponsSalesRecordsService.selectPetrolCouponsSalesRecords(inputDTO2).getSize()+"");
        return new JSONResult(dataWithCountOutputDTO);
    }


    @PostMapping("/exportCouponsRecords")
    public JSONResult exportCouponsRecords(HttpServletResponse response, GetPetrolSaleInfoInputDTO inputDTO) {
        String message = null;
        Workbook workbook = null;
        try{
            workbook = petrolCouponsSalesRecordsService.exportCouponsRecords(inputDTO);
            if(workbook == null){
                workbook = new SXSSFWorkbook();
            }
            // 指定服务器返回浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            // 点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = "中石化优惠券销售记录";
            // 将中文转换成16进制
            fileName = URLEncoder.encode(fileName,"utf-8");
            // 确保浏览器弹出对应文件的对话框
            response.addHeader("Content-Disposition","attachment;filename=" + fileName);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            message = "导出Excel数据失败，请稍后再试";
        }
        return new JSONResult(message);
    }
}
