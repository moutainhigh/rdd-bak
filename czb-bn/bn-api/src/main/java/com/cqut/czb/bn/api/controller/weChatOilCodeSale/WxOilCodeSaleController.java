package com.cqut.czb.bn.api.controller.weChatOilCodeSale;


import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.bn.entity.dto.CommodityStockDTO;

import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatOilCodeSale.WxOilCodeSaleService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/WxOilCodeSale")
public class WxOilCodeSaleController {
    @Autowired
    WxOilCodeSaleService wxOilCodeSaleService;
    /**
     * 获取表格数据
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getWxOilCodeSaleList",method = RequestMethod.POST)
    public JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO){
        return wxOilCodeSaleService.getWxOilCodeSaleList(commodityStockDTO);
    }
    /**
     * 获取详情页面数据
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getWxStockDetailsList",method = RequestMethod.POST)
    public JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO){
        return wxOilCodeSaleService.getWxStockDetailsList(wxStockDetailsDTO);
    }
    /**
     * 修改
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/editWxStockDetails",method = RequestMethod.POST)
    public JSONResult editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO){
        return wxOilCodeSaleService.editWxStockDetails(wxStockDetailsDTO);
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/deleteWxStockDetails",method = RequestMethod.POST)
    public JSONResult deleteWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO){
        return wxOilCodeSaleService.deleteWxStockDetails(wxStockDetailsDTO);
    }
    /**
     * 导入
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/importDate",method = RequestMethod.POST)
    public JSONResult importDate(MultipartFile file){
        boolean result = false;
        try {
             result = wxOilCodeSaleService.importDate(file)>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONResult("连通成功",200,result);
    }
    /**
     * 导出
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/exportDate",method = RequestMethod.POST)
    public JSONResult exportDate(HttpServletResponse response, HttpServletRequest request, WxStockDetailsDTO wxStockDetailsDTO ){
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = wxOilCodeSaleService.exportDate(wxStockDetailsDTO);
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
            String fileName = "线下大客户余额记录.xlsx";
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
}
