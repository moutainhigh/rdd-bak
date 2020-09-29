package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.TroubleshootingDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.User;
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
import java.security.Principal;

@RestController
@RequestMapping("/api/petrolSaleInfo")


public class PetrolSaleInfoController {
    @Autowired
    IPetrolManagementService petrolManagementService;

    @Autowired
    IPetrolRechargeService petrolRechargeService;

    @Autowired
    RedisUtils redisUtils;

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getSaleInfoList",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(GetPetrolSaleInfoInputDTO inputDTO, Principal principal){
        User user = (User) redisUtils.get(principal.getName());
        inputDTO.setIsSpecial(user.getIsSpecial());
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
    public JSONResult getPetrolRechargeList(PetrolRechargeInputDTO inputDTO, Principal principal){
        User user = (User) redisUtils.get(principal.getName());
        inputDTO.setIsSpecial(user.getIsSpecial());
        return new JSONResult(petrolRechargeService.getPetrolRechargeList(inputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value ="/recharge",method = RequestMethod.POST)
    public JSONResult recharge(@RequestBody PetrolRechargeInputDTO inputDTO ){
        return new JSONResult(petrolRechargeService.recharge(inputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value ="/troubleshooting",method = RequestMethod.POST)
    public JSONResult troubleshooting(@RequestBody TroubleshootingDTO inputDTO ){
        return new JSONResult(petrolRechargeService.troubleshooting(inputDTO));
    }

    @PostMapping("/exportRecords")
    public JSONResult exportRechargeRecord(HttpServletResponse response,
                                           PetrolRechargeInputDTO inputDTO, Principal principal) {

        User user = (User) redisUtils.get(principal.getName());
        inputDTO.setIsSpecial(user.getIsSpecial());

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

    @PostMapping("/exportSaleRecords")
    public JSONResult exportSaleRecords(HttpServletResponse response, GetPetrolSaleInfoInputDTO inputDTO, Principal principal) {

        User user = (User) redisUtils.get(principal.getName());
        inputDTO.setIsSpecial(user.getIsSpecial());

        String message = null;
        Workbook workbook = null;

        try {
            workbook = petrolRechargeService.exportSaleRecords(inputDTO);
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
