package com.cqut.czb.bn.api.controller.mallPartnerManage;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.OrderDetails;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.UserInfoService;
import com.cqut.czb.bn.service.mallPartnerManageService.MallPartnerManageService;
import com.cqut.czb.bn.util.excel.ExcelUtil;
import com.cqut.czb.bn.util.exception.NormalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/mallPartnerManage")
public class MallPartnerManageController {
    @Autowired
    MallPartnerManageService mallPartnerManageService;

    @Autowired
    UserInfoService userInfoService;

    /**
     * 统计总金额
     * @return
     */
    @GetMapping(value = "/statisticsOrder")
    public JSONResult statisticsOrder() {
        return mallPartnerManageService.statisticsOrder();
    }

    @GetMapping(value = "/getMallPartnerList")
    public JSONResult getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO) {
        return new JSONResult(mallPartnerManageService.getMallPartnerList(mallPartnerDTO, pageDTO));
    }

    @GetMapping(value = "/getMallPartnerConsumptionDetails")
    public JSONResult getMallPartnerConsumptionDetails(MallPartnerDTO mallPartnerDTO) {
        return new JSONResult(mallPartnerManageService.getMallPartnerConsumptionDetails(mallPartnerDTO));
    }

    @GetMapping(value = "/getEveryOrderDetails")
    public JSONResult getEveryOrderDetails(OrderDetails orderDetails, PageDTO pageDTO) {
        return new JSONResult(mallPartnerManageService.getEveryOrderDetails(orderDetails, pageDTO));
    }

    /**
     * 统计不同类型的消费总额
     */
    @GetMapping(value = "/getEveryTotalMoney")
    public JSONResult getEveryTotalMoney(MallPartnerDTO mallPartnerDTO) {
        return mallPartnerManageService.getEveryTotalMoney(mallPartnerDTO);
    }

    /**
     * 商城合伙人总消费和总收益excel下载
     * @param mallPartnerDTO
     * @param response
     */
    @GetMapping(value = "/downloadMallPartnerListExcel")
    public void downloadMallPartnerListExcel(MallPartnerDTO mallPartnerDTO, HttpServletResponse response) {
        List<MallPartnerDTO> mallPartnerDTOList = mallPartnerManageService.getMallPartnerList(mallPartnerDTO, new PageDTO()).getList();
        // 设置响应输出的头类型(设置响应类型)
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称(设置下载文件的默认名称)
        response.setHeader("Content-Disposition", "attachment;filename=address.xls");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String title = simpleDateFormat.format(new Date()) + "  商城合伙人总收益表";
            ExcelUtil.exportExcel(mallPartnerDTOList, title, "sheet1", MallPartnerDTO.class, title + ".xlsx", response);
        } catch (NormalException e) {
            return;
        }
    }

    /**
     * 商城合伙人各类消费excel下载
     * @param mallPartnerDTO
     * @param response
     */
    @GetMapping(value = "/downloadMallPartnerConsumptionExcel")
    public void downloadMallPartnerConsumptionExcel(MallPartnerDTO mallPartnerDTO, HttpServletResponse response) {
        List<MallPartnerDTO> mallPartnerDTOList = mallPartnerManageService.getMallPartnerConsumptionDetails(mallPartnerDTO);
        // 设置响应输出的头类型(设置响应类型)
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称(设置下载文件的默认名称)
        response.setHeader("Content-Disposition", "attachment;filename=address.xls");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String title = simpleDateFormat.format(new Date()) + "  " + userInfoService.getUserAccount(mallPartnerDTO.getUserId()) + "  各类消费收益表";
            ExcelUtil.exportExcel(mallPartnerDTOList, title, "sheet1", MallPartnerDTO.class, title + ".xlsx", response);
        } catch (NormalException e) {
            return;
        }

    }

    /**
     * 商城合伙人各类消费excel下载
     * @param orderDetails
     * @param response
     */
    @GetMapping(value = "/downloadEveryOrderDetailsExcel")
    public void downloadEveryOrderDetailsExcel(OrderDetails orderDetails, HttpServletResponse response) {
        List<OrderDetails> everyOrderDetailsList = mallPartnerManageService.getEveryOrderDetails(orderDetails, new PageDTO()).getList();
        // 设置响应输出的头类型(设置响应类型)
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称(设置下载文件的默认名称)
        response.setHeader("Content-Disposition", "attachment;filename=address.xls");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String title = simpleDateFormat.format(new Date()) + "  " + userInfoService.getUserAccount(orderDetails.getUserId()) + "  订单表";
            ExcelUtil.exportExcel(everyOrderDetailsList, title, "sheet1", MallPartnerDTO.class, title + ".xlsx", response);
        } catch (NormalException e) {
            return;
        }
    }
}
