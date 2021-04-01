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
    @GetMapping(value = "/statisticsMoney")
    public JSONResult statisticsMoney() {
        return mallPartnerManageService.statisticsMoney();
    }

    @GetMapping(value = "/getMallPartnerList")
    public JSONResult getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO) {
        return new JSONResult(mallPartnerManageService.getMallPartnerList(mallPartnerDTO, pageDTO));
    }

    @GetMapping(value = "/getMallPartnerConsumptionDetails")
    public JSONResult getMallPartnerConsumptionDetails(User user) {
        return new JSONResult(mallPartnerManageService.getMallPartnerConsumptionDetails(user));
    }

    @GetMapping(value = "/getEveryOrderDetails")
    public JSONResult getEveryOrderDetails(OrderDetails orderDetails, PageDTO pageDTO) {
        return new JSONResult(mallPartnerManageService.getEveryOrderDetails(orderDetails, pageDTO));
    }

    /**
     * 商城合伙人总消费和总收益excel下载
     * @param mallPartnerDTO
     * @param response
     */
    @GetMapping(value = "/downloadMallPartnerListExcel")
    public void downloadMallPartnerListExcel(MallPartnerDTO mallPartnerDTO, HttpServletResponse response) {
        List<MallPartnerDTO> mallPartnerDTOList = mallPartnerManageService.getMallPartnerList(mallPartnerDTO, new PageDTO()).getList();
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
     * @param user
     * @param response
     */
    @GetMapping(value = "/downloadMallPartnerConsumptionExcel")
    public void downloadMallPartnerConsumptionExcel(User user, HttpServletResponse response) {
        List<MallPartnerDTO> mallPartnerDTOList = mallPartnerManageService.getMallPartnerConsumptionDetails(user);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String title = simpleDateFormat.format(new Date()) + "  " + userInfoService.getUserAccount(user.getUserId()) + "  各类消费收益表";
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
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String title = simpleDateFormat.format(new Date()) + "  " + userInfoService.getUserAccount(orderDetails.getUserId()) + "  订单表";
            ExcelUtil.exportExcel(everyOrderDetailsList, title, "sheet1", MallPartnerDTO.class, title + ".xlsx", response);
        } catch (NormalException e) {
            return;
        }
    }
}
