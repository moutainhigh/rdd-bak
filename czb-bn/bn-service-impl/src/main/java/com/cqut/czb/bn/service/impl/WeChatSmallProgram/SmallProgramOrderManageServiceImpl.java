package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.AddressMapper;
import com.cqut.czb.bn.dao.mapper.UserRoleMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserRoleDTO;
import com.cqut.czb.bn.entity.entity.Address;
import com.cqut.czb.bn.entity.entity.UserRole;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmallProgramOrderManageServiceImpl implements SmallProgramOrderManageService {

    @Autowired
    WeChatCommodityOrderMapperExtra weChatCommodityOrderMapperExtra;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;

    @Override
    public JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(WeChatCommodityOrderDTO input, PageDTO page) {
        JSONResult<PageInfo<WeChatCommodityOrderDTO>> jsonResult = new JSONResult<>();

        // 处理用户权限问题
        dealUserPermission(input);

        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<WeChatCommodityOrderDTO> result = weChatCommodityOrderMapperExtra.getTableList(input);
        PageInfo<WeChatCommodityOrderDTO> pageInfo = new PageInfo<>(result);

        jsonResult.setData(pageInfo);

        jsonResult.setCode(200);
        jsonResult.setMessage("列表数据获取成功");
        return jsonResult;
    }

    @Override
    public JSONResult<Boolean> obsoleteOrder(String orderId) {
        JSONResult<Boolean> jsonResult = new JSONResult<>();
        boolean result = weChatCommodityOrderMapperExtra.obsoleteOrder(orderId) > 0;
        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("成功作废该订单");
        return jsonResult;
    }

    @Override
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(String userId, String orderId) {
        // 获取订单通用信息
        JSONResult<WeChatCommodityOrderDetail> jsonResult = new JSONResult<>();
        WeChatCommodityOrderDetail result = weChatCommodityOrderMapperExtra.getOrderDetail(orderId);
        // 查询商品图片
        if (result.getCommodityImgId() != null) {
            WCPCommodityOrderDTO data = weChatCommodityOrderMapperExtra.selectOneCommodityOrderById(userId, orderId);
            if (data != null && data.getCommodityImgList() != null) {
                result.setCommodityImgList(data.getCommodityImgList());
            }
        }
        if (result == null) {
            jsonResult.setData(null);
            jsonResult.setCode(200);
            jsonResult.setMessage("查无此订单");
        }
        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("数据获取成功");
        return jsonResult;
    }

    @Override
    public JSONResult<WeChatCommodityOrderProcess> getOrderProcessInfo(String orderId) {
        JSONResult<WeChatCommodityOrderProcess> jsonResult = new JSONResult<>();
        WeChatCommodityOrderProcess result = weChatCommodityOrderMapperExtra.getOrderProcessInfo(orderId);

        if (result == null) {
            jsonResult.setData(null);
            jsonResult.setCode(200);
            return jsonResult;
        }
        // 如果为寄送(takeWay == 1)，获取addressInfo
        if (result.getTakeWay() == 1 && result.getAddressId() != null) {
            Address address = addressMapper.selectByPrimaryKey(result.getAddressId());
            if (address != null) {
                result.setAddressInfo(address.getProvince() + address.getCity() + address.getArea() + address.getDetail());
            }
        }

        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("数据获取成功");
        return jsonResult;
    }

    @Override
    public JSONResult<Boolean> dealOrder(String userAccount, WeChatCommodityOrderProcess input) {
        // 设置处理时间与处理人
        input.setProcessingTime(new Date().toString());
        input.setHandler(userAccount);

        JSONResult<Boolean> jsonResult = new JSONResult<>();
        boolean result;
        if (input.getTakeWay() == 1) { // 寄送
            result = weChatCommodityOrderMapperExtra.dealOrderSend(input) > 0;
        } else if (input.getTakeWay() == 2) { // 核销
            result = weChatCommodityOrderMapperExtra.dealOrderEl(input) > 0;
        } else {
            jsonResult.setData(false);
            jsonResult.setCode(200);
            jsonResult.setMessage("商品取件方式不明确！");
            return jsonResult;
        }
        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("成功处理该订单");
        return jsonResult;
    }

    @Override
    public JSONResult<Double> getTotalSale(WeChatCommodityOrderDTO input) {
        JSONResult<Double> jsonResult = new JSONResult<>();

        // 处理用户权限问题
        dealUserPermission(input);

        Double totalSaleNumber = weChatCommodityOrderMapperExtra.getTotalSale(input);

        jsonResult.setCode(200);
        jsonResult.setMessage("销售总额获取成功");
        jsonResult.setData(totalSaleNumber);
        return jsonResult;
    }

    /**
     * 处理用户权限问题
     * (微信小程序商家只能看到自己的订单，管理员可以看到所有订单)
     *
     * 逻辑：管理员设置managerId为null，普通商家不对managerId做操作，使之成为SQL筛选条件
     *
     * @param input
     */
    private void dealUserPermission(WeChatCommodityOrderDTO input) {
        // 判断用户是否是管理员
        UserRoleDTO user = new UserRoleDTO();
        user.setUserId(input.getManagerId());
        List<UserRoleDTO> roleList = userRoleMapperExtra.selectUserRoleName(user);

        boolean flag = false;
        for (UserRoleDTO userRoleDTO : roleList) {
            if ("管理员".equals(userRoleDTO.getRoleName())) {
                // 如果该用户拥有管理员权限，进行标识
                flag = true;
                break;
            }
        }
        // 如果该用户有个身份是管理员
        if (flag) {
            // 设置userId(managerId)为空，去除SQL中的筛选条件
            input.setManagerId(null);
        }
    }
}
