package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.global.DateDealWith;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import com.cqut.czb.bn.service.petrolRecharge.IPetrolRechargeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/petrolSaleInfo")


public class PetrolSaleInfoController {
    @Autowired
    IPetrolManagementService petrolManagementService;

    @Autowired
    IPetrolRechargeService petrolRechargeService;
    @RequestMapping(value = "/getSaleInfoList",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(GetPetrolSaleInfoInputDTO inputDTO){
        DataWithCountOutputDTO dataWithCountOutputDTO = new DataWithCountOutputDTO();
        dataWithCountOutputDTO.setData(petrolManagementService.getPetrolSaleInfoList(inputDTO));
        dataWithCountOutputDTO.setCount(petrolManagementService.getPetrolSaleMoneyCount(inputDTO));
        //获取今日销售数据
        GetPetrolSaleInfoInputDTO inputDTO2=new GetPetrolSaleInfoInputDTO();
        inputDTO2.setStartTime(DateDealWith.backStartTime());
        inputDTO2.setEndTime(DateDealWith.backEndTime());
        dataWithCountOutputDTO.setTodayCount(petrolManagementService.getPetrolSaleMoneyCount(inputDTO2));
        dataWithCountOutputDTO.setTodayNum(petrolManagementService.getPetrolSaleInfoList(inputDTO2).getSize()+"");
        return new JSONResult(dataWithCountOutputDTO);
    }

    @RequestMapping(value = "/changePetrolNum",method = RequestMethod.GET)
    public JSONResult changePetrolNum(PetrolRechargeInputDTO inputDTO){
        return petrolManagementService.changePetrolNum(inputDTO);
    }

    @RequestMapping(value = "/getPetrolRechargeList",method = RequestMethod.GET)
    public JSONResult getPetrolRechargeList(PetrolRechargeInputDTO inputDTO){
        return new JSONResult(petrolRechargeService.getPetrolRechargeList(inputDTO));
    }

    @RequestMapping(value ="/recharge",method = RequestMethod.POST)
    public JSONResult recharge(@RequestBody PetrolRechargeInputDTO inputDTO ){
        return new JSONResult(petrolRechargeService.recharge(inputDTO));
    }

    @PostMapping("/exportRecords")
    public JSONResult exportRechargeRecord(HttpServletResponse response,
                                           PetrolRechargeInputDTO inputDTO) {

        String message = null;
        Workbook workbook = null;
        try {
            workbook = petrolRechargeService.exportRechargeRecords(inputDTO);
            if(workbook == null) {
                workbook = new SXSSFWorkbook();
            }
            //指定服务器返回给浏览器的编码格式
            response.setCharacterEncoding("utf-8");
            //点击下载之后出现下载对话框
            response.setContentType("application/x-download");
            String fileName = "油卡充值记录.xlsx";
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
