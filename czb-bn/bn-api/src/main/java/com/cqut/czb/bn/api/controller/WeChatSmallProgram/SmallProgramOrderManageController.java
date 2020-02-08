package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.DealCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序订单管理
 *
 * @author 袁才明
 */
@CrossOrigin
@RestController
@RequestMapping("/api/smallProgramOrderManage")
public class SmallProgramOrderManageController {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    SmallProgramOrderManageService orderManageService;


    /**
     * 小程序订单管理页面数据获取
     * （根据用户角色，筛选获取的订单）
     *
     * @param weChatCommodityOrderDTO
     * @param page
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getTableList")
    public JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(Principal principal, @RequestBody WeChatCommodityOrderDTO weChatCommodityOrderDTO, PageDTO page) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        weChatCommodityOrderDTO.setManagerId(user.getUserId());
        return orderManageService.getTableList(weChatCommodityOrderDTO, page);
    }

    /**
     * 作废订单
     *
     * @param orderId
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/obsoleteOrder")
    public JSONResult<Boolean> obsoleteOrder(@RequestBody String orderId) {
        return orderManageService.obsoleteOrder(orderId);
    }

    /**
     * 通过orderId获取订单详情的数据
     *
     * @param principal
     * @param orderId
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getOrderDetail")
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(Principal principal, String orderId) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        return orderManageService.getOrderDetail(user.getUserId(), orderId);
    }

    /**
     * 获取订单处理数据
     *
     * @param orderId
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getOrderProcessInfo")
    public JSONResult<WeChatCommodityOrderProcess> getOrderProcessInfo(String orderId) {
        return orderManageService.getOrderProcessInfo(orderId);
    }

    /**
     * 处理订单
     *
     * @param principal
     * @param input
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/dealOrder")
    public JSONResult<Boolean> dealOrder(Principal principal, WeChatCommodityOrderProcess input) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        return orderManageService.dealOrder(user.getUserAccount(), input);
    }

    /**
     * 获取销售额
     *
     * @param principal
     * @param weChatCommodityOrderDTO
     * @param page
     * @return
     */
    @PermissionCheck(role = "管理员,微信商家")
    @PostMapping("/getTotalSale")
    public JSONResult<Double> getTotalSale(Principal principal, @RequestBody WeChatCommodityOrderDTO weChatCommodityOrderDTO, PageDTO page) {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        if (user == null || user.getUserId() == null) {
            return null;
        }
        weChatCommodityOrderDTO.setManagerId(user.getUserId());
        return orderManageService.getTotalSale(weChatCommodityOrderDTO);
    }

    /**
     * 下载excel表格
     */
    @PostMapping("/exportRecords")
    @PermissionCheck(role = "管理员")
    public JSONResult exportOrderRecord(HttpServletResponse response, HttpServletRequest request, WeChatCommodityOrderDTO pageDTO)
    {
        Map<String, Object> result = new HashMap<>();
        String message = null;
        Workbook workbook = null;
        try {
            workbook = orderManageService.exportOrderRecords(pageDTO);
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
    /**
     * 导入excel表（更新快递单号/公司 状态）
     * @param file
     * @return
     */
    @PostMapping("importDeliveryRecords")
//    @PermissionCheck(role = "管理员")
    public JSONResult importDeliveryRecords(MultipartFile file){
        try {
            orderManageService.ImportDeliveryRecords(file);
        }catch (Exception e){
            e.printStackTrace();
            return new JSONResult(500,"failed");
        }
        return new JSONResult(200,"success");
    }

}
