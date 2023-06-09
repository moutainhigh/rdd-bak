package com.cqut.czb.bn.service.impl.WxOilCodeSale;

import com.cqut.czb.bn.dao.mapper.WxOilCodeSaleMapperExtra;
import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.dto.ImportWxStockDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import com.cqut.czb.bn.entity.dto.WxStockNumDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatOilCodeSale.WxOilCodeSaleService;
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
import java.util.*;

@Service
public class WxOilCodeSaleServiceImpl implements WxOilCodeSaleService {

    @Autowired
    WxOilCodeSaleMapperExtra wxOilCodeSaleMapperExtra;

    @Override
    public JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO) {
        PageHelper.startPage(commodityStockDTO.getCurrentPage(), commodityStockDTO.getPageSize(),true);
        PageInfo pageInfo = new PageInfo();
        List<CommodityStockDTO> list = wxOilCodeSaleMapperExtra.getWxOilCodeSaleList(commodityStockDTO);
        int total = wxOilCodeSaleMapperExtra.getWxOilCodeSaleTotal();
        pageInfo.setList(list);
        pageInfo.setTotal(total);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO) {
        List<WxStockDetailsDTO> list = wxOilCodeSaleMapperExtra.getWxStockDetailsList(wxStockDetailsDTO);
        for (int i = 0; i<list.size();i++){
            for (int j=i+1;j<list.size();j++){
                if (list.get(i).getStockID().equals(list.get(j).getStockID())){
                    list.get(i).setAttribute(list.get(i).getAttribute()+","+list.get(j).getAttribute());
                    list.get(i).setSalePrice(list.get(i).getSalePrice()+list.get(j).getExtSalePrice());
                    list.remove(j);
                    j--;
                }
            }
        }
        return new JSONResult("列表数据查询成功", 200, list);
    }

    @Override
    public JSONResult editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO) {
        if (wxOilCodeSaleMapperExtra.check(wxStockDetailsDTO)!=0){
            return new JSONResult("输入的电子码重复", 200, false);
        }else{
            boolean result = wxOilCodeSaleMapperExtra.editWxStockDetails(wxStockDetailsDTO)>0;
            return new JSONResult("修改", 200, result);
        }
    }

    @Override
    public JSONResult deleteWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO) {
        boolean result = wxOilCodeSaleMapperExtra.deleteWxStock(wxStockDetailsDTO)>0
                && wxOilCodeSaleMapperExtra.deleteWxStockAttr(wxStockDetailsDTO)>0
                && wxOilCodeSaleMapperExtra.updateWxCommodityNum(wxStockDetailsDTO)>0;
        return new JSONResult("删除", 200, result);
    }

    @Override
    public JSONResult importDate(List<ImportWxStockDTO> WxStockList){
        if (WxStockList == null){
            return new JSONResult("表格为空",200,false);
        }
        Map<String, ImportWxStockDTO> stockMap = new HashMap<>();
        /**
         * 按二维码或者电子码为key 做到去重复的效果
         */
        for (ImportWxStockDTO p : WxStockList) {
            stockMap.put(p.getContent(), p);
        }
        List<ImportWxStockDTO> list = new ArrayList<>();
        for (ImportWxStockDTO p:stockMap.values()) {
            list.add(p);
        }
        list = changeTo(list);
        /**
         * 分离属性
         */
        for (int i = 0; i<list.size(); i++){
            if (list.get(i).getAttribute().indexOf(",")!=-1){
                ImportWxStockDTO importWxStockDTO1 = new ImportWxStockDTO();
                ImportWxStockDTO importWxStockDTO2 = new ImportWxStockDTO();
                importWxStockDTO1.setStockID(list.get(i).getStockID());
                importWxStockDTO1.setCommodityNo(list.get(i).getCommodityNo());
                importWxStockDTO1.setContent(list.get(i).getContent());
                importWxStockDTO1.setAttribute(list.get(i).getAttribute().substring(0,list.get(i).getAttribute().indexOf(",")));
                list.add(importWxStockDTO1);
                importWxStockDTO2.setStockID(list.get(i).getStockID());
                importWxStockDTO2.setCommodityNo(list.get(i).getCommodityNo());
                importWxStockDTO2.setContent(list.get(i).getContent());
                importWxStockDTO2.setAttribute(list.get(i).getAttribute().substring(list.get(i).getAttribute().indexOf(",")+1,list.get(i).getAttribute().length()));
                list.add(importWxStockDTO2);
                list.remove(i);
                i--;
                continue;
            }
            WxStockDetailsDTO wxStockDetailsDTO = new WxStockDetailsDTO();
            wxStockDetailsDTO.setContent(list.get(i).getContent());
            ImportWxStockDTO importWxStockDTO = wxOilCodeSaleMapperExtra.checkCommodityAtrr(list.get(i));
            if (wxOilCodeSaleMapperExtra.check(wxStockDetailsDTO)!=0 || importWxStockDTO==null){
                for (int j = 0; j<list.size(); j++){
                    if (list.get(i).getStockID().equals(list.get(j).getStockID())){
                        list.remove(j);
                        j--;
                    }
                }
                list.remove(i);
                i--;
            } else {
                list.get(i).setAtrrID(importWxStockDTO.getAtrrID());
                list.get(i).setCommodityID(importWxStockDTO.getCommodityID());
                list.get(i).setStockAtrrID(StringUtil.createId());
            }

        }
        boolean result1 = wxOilCodeSaleMapperExtra.importWxStockAttr(list)>0;
        Map<String, ImportWxStockDTO> stockAtrrMap = new HashMap<>();
        for (ImportWxStockDTO p : list) {
            stockAtrrMap.put(p.getStockID(), p);
        }
        List<ImportWxStockDTO> list2 = new ArrayList<>();
        for (ImportWxStockDTO p:stockAtrrMap.values()) {
            list2.add(p);
        }
        boolean result2 = wxOilCodeSaleMapperExtra.importWxStock(list2)>0;
        List<WxStockNumDTO> wxStockNumDTOS = wxOilCodeSaleMapperExtra.getCommdityTotal();
        boolean result3 = wxOilCodeSaleMapperExtra.updateWxCommodityTotalNum(wxStockNumDTOS)>0;
        String result = "成功"+list2.size()+"条，失败"+(WxStockList.size()-list2.size())+"条";
        return new JSONResult(result,200,result1&&result2&&result3);
    }

    @Override
    public JSONResult importDate(MultipartFile file) throws Exception {
        List<ImportWxStockDTO> WxStockList = ImportWxStock.readExcel(file.getOriginalFilename(),file.getInputStream());
        return importDate(WxStockList);
    }

    @Override
    public Workbook exportDate(WxStockDetailsDTO wxStockDetailsDTO) throws Exception {
        List<WxStockDetailsDTO> list = wxOilCodeSaleMapperExtra.getWxStockDetailsList(wxStockDetailsDTO);
        if(list==null||list.size()==0){
            return getWxStockWorkBook(null,wxStockDetailsDTO);
        }
        for (int i = 0; i<list.size();i++){
            for (int j=i+1;j<list.size();j++){
                if (list.get(i).getStockID().equals(list.get(j).getStockID())){
                    list.get(i).setAttribute(list.get(i).getAttribute()+","+list.get(j).getAttribute());
                    list.get(i).setSalePrice(list.get(i).getSalePrice()+list.get(j).getExtSalePrice());
                    list.remove(j);
                    j--;
                }
            }
        }
        return getWxStockWorkBook(list, wxStockDetailsDTO);
    }

    List<ImportWxStockDTO> changeTo(List<ImportWxStockDTO> list){
        for (int i = 0;i<list.size();i++){
            List<String> contentList = new ArrayList<>();
            String str = null;
            contentList.add(list.get(i).getContent());
            for (int j = 0;j<contentList.size();j++){
                if (contentList.get(j).indexOf(",")!=-1){
                    String content1 = contentList.get(j).substring(0,contentList.get(j).indexOf(","));
                    String content2 = contentList.get(j).substring(contentList.get(j).indexOf(",")+1,contentList.get(j).length());
                    contentList.add(content1);
                    contentList.add(content2);
                    contentList.remove(j);
                    j--;
                }
            }
            if (contentList.size() == 1) {
                str = "{\"" + contentList.get(0).substring(0, contentList.get(0).indexOf(":")) + "\":\"" + contentList.get(0).substring(contentList.get(0).indexOf(":")+1) + "\"}";
            } else {
                for (int m = 0; m < contentList.size(); m++) {

                    if (m == 0) {
                        str = "{\"" + contentList.get(0).substring(0, contentList.get(0).indexOf(":")) + "\":\"" + contentList.get(0).substring(contentList.get(0).indexOf(":")+1) + "\",";
                    } else if (m == contentList.size() - 1) {
                        str = str + "\"" + contentList.get(contentList.size()-1).substring(0, contentList.get(contentList.size()-1).indexOf(":")) + "\":\"" + contentList.get(contentList.size()-1).substring(contentList.get(contentList.size()-1).indexOf(":")+1) + "\"}";
                    } else {
                        str = str + "\"" + contentList.get(m).substring(0, contentList.get(m).indexOf(":")) + "\":\"" + contentList.get(m).substring(contentList.get(m).indexOf(":")+1) + "\",";
                    }
                }
            }
            list.get(i).setContent(str);
        }
        return list;
    }
    private Workbook getWxStockWorkBook(List<WxStockDetailsDTO> list, WxStockDetailsDTO inputDTO) throws Exception {
        String[] WxStockHead = SystemConstants.WXSTOCK_DETAILS_HEAD;
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出微信商品库存详情记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无记录");
            return workbook;
        }
        try {
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出微信商品库存详情记录");//创建工作表
        Row row = sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));//起始行号，终止行号， 起始列号，终止列号
        Cell cell1 = row.createCell(0);
        cell1.setCellStyle(style);
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
        cell1.setCellValue(sdf1.format(inputDTO.getStartTime())+"至"+sdf1.format(inputDTO.getEndTime())+"微信商品库存详情记录");
        row = sheet.createRow( 1);
        for (int i = 0; i < WxStockHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(WxStockHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            row = sheet.createRow(i + 2);
            row.setRowStyle(style);
            row.createCell(count++).setCellValue(list.get(i).getItemNo());
            row.createCell(count++).setCellValue(list.get(i).getName());
            row.createCell(count++).setCellValue(list.get(i).getAttribute());
            row.createCell(count++).setCellValue(list.get(i).getContent());
            row.createCell(count++).setCellValue(list.get(i).getSalePrice());
            if (list.get(i).getState().equals("0")){
                row.createCell(count++).setCellValue("未购买");
            }else if (list.get(i).getState().equals("1")){
                row.createCell(count++).setCellValue("支付中");
            }else if (list.get(i).getState().equals("2")){
                row.createCell(count++).setCellValue("已购买");
            }
            row.createCell(count++).setCellValue(sdf1.format(list.get(i).getCreateTime()));
        }
        return workbook;
    }
}
