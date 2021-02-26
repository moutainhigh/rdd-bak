package com.cqut.czb.bn.api.controller.equityPayment;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.CategoryAndTypeDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCommodityDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.EquityPaymentService;
import com.cqut.czb.bn.service.impl.AnnouncementServiceImpl;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */

@RestController
@RequestMapping("/api/equityPayment")
public class EquityPaymentController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    EquityPaymentService equityPaymentService;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping(value = "/getCommodityList", method = RequestMethod.GET)
    public JSONResult getCommodityList(EquityPaymentCommodityDTO equityPaymentCommodityDTO, PageDTO pageDTO) {
        return new JSONResult(equityPaymentService.getCommodityList(equityPaymentCommodityDTO, pageDTO));
    }

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/getCommodityDetails", method = RequestMethod.GET)
    public JSONResult getCommodityDetails(String goodsId) {
        return equityPaymentService.getCommodityDetails(goodsId);
    }

    /**
     * 后台管理系统
     * 获取商品订单详情分页
     * @param equityPaymentDTO
     * @param pageDTO
     * @return
     */
    @RequestMapping(value = "/getEquityPaymentRecord", method = RequestMethod.GET)
    public JSONResult getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO) {
        return new JSONResult(equityPaymentService.getEquityPaymentRecord(equityPaymentDTO, pageDTO));
    }

    /**
     * 级联选择-类目-类别
     * @return
     */
    @RequestMapping(value = "/getCategoryAndType", method = RequestMethod.GET)
    public JSONResult getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO) {
        return new JSONResult(equityPaymentService.getCategoryAndType(categoryAndTypeDTO));
    }

    /**
     * 新增商品
     * @return
     */
    @RequestMapping(value = "/insertEquityPayment", method = RequestMethod.POST)
    public JSONResult insertEquityPayment(Principal principal, EquityPaymentCommodityDTO equityPaymentCommodityDTO, @RequestParam("files")MultipartFile files) {
        User user = (User) redisUtils.get(principal.getName());
        String address = "";
        try {
            if (files!=null||!files.isEmpty()) {
                address = FileUploadUtil.putObject(files.getOriginalFilename(), files.getInputStream());//返回图片储存路径
            }
        } catch (IOException ioException) {
            return new JSONResult("文件读取错误");
        }

        File file = announcementServiceImpl.setFile(files.getOriginalFilename(),address, user.getUserId(),new Date());
        fileMapperExtra.insertSelective(file);
        equityPaymentCommodityDTO.setGoodsPic(file.getFileId());
        return new JSONResult(equityPaymentService.insertEquityPayment(equityPaymentCommodityDTO));
    }

//    /**
//     * 修改商品
//     * @return
//     */
//    @RequestMapping(value = "/updateEquityPayment", method = RequestMethod.GET)
//    public JSONResult updateEquityPayment( ) {
//        return new JSONResult(equityPaymentService.updateEquityPayment());
//    }
}
