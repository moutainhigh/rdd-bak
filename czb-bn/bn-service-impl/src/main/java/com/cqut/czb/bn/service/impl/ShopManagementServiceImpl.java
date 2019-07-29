package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ShopMapper;
import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.entity.dto.shopManagement.SettlementDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.SettlementPageDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.ShopManagementDTO;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.service.ShopManagementService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-28 14:49
 */
@Service
public class ShopManagementServiceImpl implements ShopManagementService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    ShopMapper shopMapper;

    @Override
    public PageInfo getShopList(ShopManagementDTO shopManagementDTO) {
        PageHelper.startPage(shopManagementDTO.getCurrentPage(), shopManagementDTO.getPageSize());
        return new PageInfo<>(shopMapperExtra.selectShopManageDTO(shopManagementDTO));
    }

    @Override
    public SettlementPageDTO getSettlement(SettlementDTO settlementDTO) {
        PageHelper.startPage(settlementDTO.getCurrentPage(), settlementDTO.getPageSize());
        SettlementPageDTO settlementPageDTO = new SettlementPageDTO();
        List<SettlementDTO> settlementDTOList = shopMapperExtra.selectSettlementDTO(settlementDTO);
        settlementPageDTO.setTotalUnsettledAmount(getTotalUnsettledAmount(settlementDTOList));
        settlementPageDTO.setSettlementDTOList(new PageInfo<>(settlementDTOList));
        return settlementPageDTO;
    }

    public static double getTotalUnsettledAmount(List<SettlementDTO> settlementDTOList){
        double totalUnsettledAmount = 0.0;
        for(SettlementDTO item : settlementDTOList){
            totalUnsettledAmount += item.getActualPrice();
        }
        BigDecimal bigDecimal = new BigDecimal(totalUnsettledAmount);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public String settleOrder(HttpServletResponse response, HttpServletRequest request, SettlementDTO settlementDTO) {
        try{
            List<SettlementDTO> list = shopMapperExtra.selectSettlementDTO(settlementDTO);
            Shop shop = shopMapper.selectByPrimaryKey(settlementDTO.getShopId());
            if(list.size() == 0 || list == null){
                return "暂无数据不能导出";
            }
            if(shopMapperExtra.updateSettlementOrder(settlementDTO) > 0){
                Workbook workbook = getSettlementDTOListWorkBook(list,getTotalUnsettledAmount(list));
                //设置对客户端请求的编码格式
                request.setCharacterEncoding("utf-8");
                //指定服务器返回给浏览器的编码格式
                response.setCharacterEncoding("utf-8");
                //点击下载之后出现下载对话框
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                response.setContentType("application/x-download");
                String fileName = null;
                fileName = shop.getShopName() + df.format(new Date()) + "结算记录.xlsx";
                //System.out.println(fileName);
                //将中文转换为16进制
                fileName = URLEncoder.encode(fileName,"utf-8");
                //确保浏览器弹出对应文件的对话框
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.close();
                return "导出成功";
            }else{
                return "更新失败不能导出";
            }
        }catch (Exception e){
            return "导出失败";
        }
    }

    public static Workbook getSettlementDTOListWorkBook(List<SettlementDTO> settlementDTOS, double totalUnsettledAmoun){
        String[] settlementDTOSExcelHead = SystemConstants.SETTLE_ORDER_EXCEL_HEAD;
        Workbook workbook = new SXSSFWorkbook(settlementDTOS.size());
        Sheet sheet = workbook.createSheet("导出记录");
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        CellStyle styleRight = workbook.createCellStyle();
        styleRight.setAlignment(HorizontalAlignment.RIGHT); //对齐方式
        row.createCell(0).setCellValue("总金额：");
        row.createCell(1).setCellValue(totalUnsettledAmoun + "元");

        row = sheet.createRow(1);
        for (int i = 0; i < settlementDTOSExcelHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(settlementDTOSExcelHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        for (int i = 0; i < settlementDTOS.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 2);

            row.createCell(count).setCellValue(settlementDTOS.get(i).getCommodityTitle());
            row.getCell(count++).setCellStyle(style);
            row.createCell(count).setCellValue(settlementDTOS.get(i).getOrderId());
            row.getCell(count++).setCellStyle(style);
            row.createCell(count).setCellValue(settlementDTOS.get(i).getActualPrice() + "元");
            row.getCell(count++).setCellStyle(styleRight);
            if (settlementDTOS.get(i).getPayMethod() == 0) {
                row.createCell(count).setCellValue("佣金");
            } else if (settlementDTOS.get(i).getPayMethod() == 1) {
                row.createCell(count).setCellValue("支付宝");
            } else if (settlementDTOS.get(i).getPayMethod() == 2) {
                row.createCell(count).setCellValue("微信");
            }
            row.getCell(count++).setCellStyle(style);
            row.createCell(count).setCellValue(settlementDTOS.get(i).getThirdOrder());
            row.getCell(count++).setCellStyle(style);
            row.createCell(count).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(settlementDTOS.get(i).getCreateAt()));
            row.getCell(count++).setCellStyle(style);
        }

            return workbook;

    }
}