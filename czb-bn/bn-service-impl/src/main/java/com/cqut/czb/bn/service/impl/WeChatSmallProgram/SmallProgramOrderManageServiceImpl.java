package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.AddressMapper;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserRoleMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserRoleDTO;

import com.cqut.czb.bn.entity.dto.myTeam.RecommenderDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.Address;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.entity.global.JSONResult;

import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPNoticeService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.InputStream;
import java.util.*;

@Service
public class SmallProgramOrderManageServiceImpl implements SmallProgramOrderManageService {

    @Autowired
    WeChatCommodityOrderMapperExtra weChatCommodityOrderMapperExtra;
    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;
    @Autowired
    UserMapperExtra userMapperExtra;
    @Autowired
    WCPNoticeService wcpNoticeService;
    @Autowired
    FanYongService fanYongService;
    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

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
            // 获取地址
            Address address = addressMapper.selectByPrimaryKey(result.getAddressId());
            // 设置地址与联系电话
            if (address != null) {
                result.setAddressInfo(address.getProvince() + address.getCity() + address.getArea() + address.getDetail());
                result.setContactNumber(address.getContactNumber());
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
        System.out.println(input.getRemark());

        JSONResult<Boolean> jsonResult = new JSONResult<>();
//        try {
//            if (input.getDeliveryState() != null && input.getDeliveryState() == 2 || input.getOrderState() != null && input.getOrderState() == 2){
//                //订单完成， 返佣
//                System.out.println("小程序返佣"+input.getOrderId());
//                WeChatCommodityOrder o = weChatCommodityOrderMapper.selectByPrimaryKey(input.getOrderId());
//                WeChatCommodity weChatCommodity = weChatCommodityMapper.selectByPrimaryKey(o.getCommodityId());
//                String title="";
//                if(weChatCommodity.getCommodityTitle().length()>10){
//                    title=weChatCommodity.getCommodityTitle().substring(0,8)+"…";
//                }else {
//                    title=weChatCommodity.getCommodityTitle();
//                }
//                Boolean isSucceed=fanYongService.AppletBeginFanYong(o.getUserId(),o.getActualPrice(),o.getOrderId(),o.getFyMoney(),o.getCommodityId());
//                System.out.println("返佣"+isSucceed);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//

        boolean result;
        if (input.getTakeWay() == 1) { // 寄送
            result = weChatCommodityOrderMapperExtra.dealOrderSend(input) > 0;
            // 如果寄送状态为已收货，订单处理完成，修改订单状态
            WeChatCommodityOrderProcess newOrderState = new WeChatCommodityOrderProcess();
            newOrderState.setOrderId(input.getOrderId());
            // 订单已完成
            newOrderState.setOrderState(2);
            UserDTO user = weChatCommodityOrderMapperExtra.getOrderUserAccount(input.getOrderId());
            System.out.println(user);
            WeChatCommodityOrder order = weChatCommodityOrderMapper.selectByPrimaryKey(input.getOrderId());
            if (user!=null && !StringUtil.isNullOrEmpty(user.getUserAccount()) && user.getUserType() == 2){
                String[] values = new String[]{input.getOrderId(), user.getUserName(), order.getActualPrice()+"元", order.getCreateAt().toString()};
                System.out.println(wcpNoticeService.pushOneUser(user.getUserAccount(), WCProgramConfig.order_finish, values, "订单处理通知", "您购买的产品已交付"));
            }
            newOrderState.setHandler(input.getHandler());
            newOrderState.setRemark(input.getRemark());
            result = result && weChatCommodityOrderMapperExtra.dealOrderEl(newOrderState) > 0;
        } else { // 核销
            result = weChatCommodityOrderMapperExtra.dealOrderEl(input) > 0;
        }
//        } else {
//            jsonResult.setData(false);
//            jsonResult.setCode(200);
//            jsonResult.setMessage("商品取件方式不明确！");
//            return jsonResult;
//        }
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
     * 导入excel
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public int ImportDeliveryRecords(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        List<WeChatCommodityOrderDTO> deliveryList = null;
        Map<String,WeChatCommodityOrderDTO> deliveryMap = new HashMap<>();
        deliveryList = weChatOrderDelivery.readExcel(file.getOriginalFilename(), inputStream);
        /**
         * 按照订单号区分重复
         */
        if(deliveryList != null){
            for (WeChatCommodityOrderDTO p : deliveryList){
                deliveryMap.put(p.getOrderId(),p);
            }
        }
        List<WeChatCommodityOrderDTO> deliveryListNoRepeat = new ArrayList<>();
        for (WeChatCommodityOrderDTO p : deliveryMap.values()){
            deliveryListNoRepeat.add(p);
        }
        int countForInsert = weChatCommodityOrderMapperExtra.updateImportRecords(deliveryListNoRepeat);
        return countForInsert;
    }

    /**
     * 处理用户权限问题
     * (微信小程序商家只能看到自己的订单，管理员可以看到所有订单)
     *
     * 逻辑：管理员设置managerId为null，普通商家的managerId就是userId做操作，使之成为SQL筛选条件
     *
     * @param input
     */
    private void dealUserPermission(WeChatCommodityOrderDTO input) {
        // 判断用户是否是管理员
        UserRoleDTO user = new UserRoleDTO();
        user.setUserId(input.getManagerId());
        List<UserRoleDTO> roleList = userRoleMapperExtra.selectUserRoleName(user);

        boolean flag = false; // 使用标识是为了处理多角色情况
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

    /**
     * 导出excel
     * @param pageDTO
     * @return
     * @throws Exception
     */
    @Override
    public Workbook exportOrderRecords(WeChatCommodityOrderDTO pageDTO) throws Exception {
        List<WeChatCommodityOrderDTO> wxOrderDTOList = weChatCommodityOrderMapperExtra.selectOrder(pageDTO);
        if(wxOrderDTOList==null||wxOrderDTOList.size()==0){
            return getWorkBook(null);
        }
        return getWorkBook(wxOrderDTOList);
    }

    public Workbook getWorkBook(List<WeChatCommodityOrderDTO> wxOrderWithdrawDTOS)throws Exception{
        String[] petrolDeliveryRecordHeader = SystemConstants.OREDER_MANAGE_EXCEL_HEAD;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(wxOrderWithdrawDTOS.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出订单记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = 0; i < petrolDeliveryRecordHeader.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(petrolDeliveryRecordHeader[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        sheet.setColumnWidth(1,(short)4000);
        sheet.setColumnWidth(4,(short)8000);
        sheet.setColumnWidth(5,(short)8000);
        sheet.setColumnWidth(6,(short)4000);
        sheet.setColumnWidth(7,(short)4000);
        int startRow=1;
        int startRow1=1;
        int addRow=0;
        int addRow1=0;
        String flag = "";
        String flagShop = "";
        String subName = "";
        String subShop = "";
        if (wxOrderWithdrawDTOS.get(0).getUserName() != null) {
            flag = wxOrderWithdrawDTOS.get(0).getUserName();
        } else {
            flag = "";
        }
        if (wxOrderWithdrawDTOS.get(0).getShopName() != null) {
            flagShop = wxOrderWithdrawDTOS.get(0).getShopName();
        } else {
            flagShop = "";
        }

        for (int i = 0 ; i<wxOrderWithdrawDTOS.size(); i++){
            int count = 0;
            String checkFlag = "";
            String checkFlag2 = "";
            row = sheet.createRow(i+1);
            Cell cell = row.createCell(count);
            cell.setCellStyle(style);
            cell.setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getShopName() != null) {
                cell.setCellValue(wxOrderWithdrawDTOS.get(i).getShopName());
                checkFlag2 = wxOrderWithdrawDTOS.get(i).getShopName();
            } else {
                cell.setCellValue("");
                checkFlag2 = "";
            }
            count++;

            if (checkFlag2.equals(flagShop)){
                addRow++;
            }
            else if (addRow==startRow){
                flagShop=checkFlag2;
                startRow=startRow+1;
                addRow=addRow+1;
            }
            else{
                flagShop=checkFlag2;
                sheet.addMergedRegion(new CellRangeAddress(startRow, addRow, (short) 0, (short) 0));
                startRow=addRow+1;
                addRow=addRow+1;
            }
           if (i==wxOrderWithdrawDTOS.size()-1 && wxOrderWithdrawDTOS.size() > 1 && startRow != addRow) {
                sheet.addMergedRegion(new CellRangeAddress(startRow, addRow, (short) 0, (short) 0));
               row.createCell(count).setCellType(CellType.STRING);
               row.createCell(count).setCellValue(wxOrderWithdrawDTOS.get(i).getShopName());

            } else if (i==wxOrderWithdrawDTOS.size()-1 && startRow == addRow) {
               row.createCell(count).setCellType(CellType.STRING);
               row.createCell(count).setCellValue(wxOrderWithdrawDTOS.get(i).getShopName());
           }
           else {
               row.createCell(count).setCellType(CellType.STRING);
               row.createCell(count).setCellValue(wxOrderWithdrawDTOS.get(i).getShopName());
           }

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getUserName() != null) {
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getUserName());
                checkFlag = wxOrderWithdrawDTOS.get(i).getUserName();
            } else {
                row.createCell(count++).setCellValue("");
                checkFlag = "";
            }
            if (checkFlag.equals(flag)){
                addRow1++;
            }
            else if (addRow1==startRow1){
                flag=checkFlag;
                startRow1=startRow1+1;
                addRow1=addRow1+1;
            }
            else{
                flag=checkFlag;
                sheet.addMergedRegion(new CellRangeAddress(startRow1, addRow1, (short) 1, (short) 1));
                sheet.addMergedRegion(new CellRangeAddress(startRow1, addRow1, (short) 2, (short) 2));
                startRow1=addRow1+1;
                addRow1=addRow1+1;
            }

            if (i < wxOrderWithdrawDTOS.size()-1) {
                if (wxOrderWithdrawDTOS.get(i+1).getUserName() != null) {
                    subName = wxOrderWithdrawDTOS.get(i+1).getUserName();
                } else {
                    subName = "";
                }
            }
            if (i==wxOrderWithdrawDTOS.size()-1 && wxOrderWithdrawDTOS.size() > 1 && startRow != addRow) {
//                sheet.addMergedRegion(new CellRangeAddress(startRow1, addRow1, (short) 1, (short) 1));
//                sheet.addMergedRegion(new CellRangeAddress(startRow1, addRow1, (short) 2, (short) 2));
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count).setCellValue(wxOrderWithdrawDTOS.get(i).getUserName());

            } else if (i==wxOrderWithdrawDTOS.size()-1 && startRow == addRow) {
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count).setCellValue(wxOrderWithdrawDTOS.get(i).getUserName());
            }
            else {
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count).setCellValue(wxOrderWithdrawDTOS.get(i).getUserName());
            }

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getPhone() != null) {
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getPhone());
            } else {
                row.createCell(count++).setCellValue("");

            }

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getOrderId() != null)
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getOrderId());
            else
                row.createCell(count++).setCellValue("");

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getAddressId() != null)
                row.createCell(count++).setCellValue(getAddress(wxOrderWithdrawDTOS.get(i).getAddressId()));
            else
                row.createCell(count++).setCellValue("");

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getCommodityTitle() != null)
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getCommodityTitle());
            else
                row.createCell(count++).setCellValue("");
            if (wxOrderWithdrawDTOS.get(i).getCommodityNum() != null)
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getCommodityNum());
            else
                row.createCell(count++).setCellValue("");

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getActualPrice() != null)
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getActualPrice());
            else
                row.createCell(count++).setCellValue("");

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getPayStatus()==0)
                row.createCell(count++).setCellValue("未支付");
            else if(wxOrderWithdrawDTOS.get(i).getPayStatus()==1)
                row.createCell(count++).setCellValue("已支付");
            else
                row.createCell(count++).setCellValue("");


            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(formateDate(wxOrderWithdrawDTOS.get(i).getCreateAt()));
            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getDeliveryCompany() != null)
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getDeliveryCompany());
            else
                row.createCell(count++).setCellValue("暂无");
            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getDeliveryNum() != null)
                row.createCell(count++).setCellValue(wxOrderWithdrawDTOS.get(i).getDeliveryNum());
            else
                row.createCell(count++).setCellValue("暂无");

            row.createCell(count).setCellType(CellType.STRING);
            if (wxOrderWithdrawDTOS.get(i).getDeliveryState() != null) {
                if (wxOrderWithdrawDTOS.get(i).getDeliveryState()==0)
                    row.createCell(count++).setCellValue("未寄送");
                else if(wxOrderWithdrawDTOS.get(i).getDeliveryState()==1)
                    row.createCell(count++).setCellValue("寄送中");
                else if(wxOrderWithdrawDTOS.get(i).getDeliveryState()==2)
                    row.createCell(count++).setCellValue("已收货");
            } else {
                row.createCell(count++).setCellValue("暂无");
            }
            String userId = wxOrderWithdrawDTOS.get(i).getUserId();
            RecommenderDTO up1 = userMapperExtra.selectRecommender(userId);
            if (up1 != null) {
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count++).setCellValue(up1.getUserName());
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count++).setCellValue(up1.getBindingPhone());

                RecommenderDTO up2 = userMapperExtra.selectRecommender(up1.getUserId());
                if (up2 != null) {
                    row.createCell(count).setCellType(CellType.STRING);
                    row.createCell(count++).setCellValue(up2.getUserName());
                    row.createCell(count).setCellType(CellType.STRING);
                    row.createCell(count++).setCellValue(up2.getBindingPhone());
                }
            }

        }
        return workbook;
    }

    public String formateDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String theDate = sdf.format(date);
        return theDate;
    }

//    获取订单地址信息
    public String getAddress(String addressId) {
        WeChatCommodityOrderDTO address = weChatCommodityOrderMapperExtra.getOrderAddress(addressId);
        if (address != null) {
            return address.getProvince() + address.getCity() + address.getArea() + address.getDetail();
        } else {
            return "";
        }
    }

}
