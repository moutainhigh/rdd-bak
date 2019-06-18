package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.IndicatorRecordMapperExtra;
import com.cqut.czb.bn.dao.mapper.PartnerMapperExtra;
import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PartnerAssessmentServiceImpl implements com.cqut.czb.bn.service.IndicatorRecordService {

    @Autowired
    IndicatorRecordMapperExtra indicatorRecordMapperExtra;
    @Autowired
    PartnerMapperExtra partnerMapperExtra;
    @Autowired
    InfoSpreadServiceImpl infoSpreadService;
    @Autowired
    DictMapperExtra dictMapperExtra;
    @Override
    public PageInfo<IndicatorRecordDTO> getIndicatorRecordList(IndicatorRecordDTO indicatorRecordDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        return new PageInfo<>(indicatorRecordMapperExtra.getIndicatorRecordList(indicatorRecordDTO));
    }

    @Override
    public Boolean ConfirmComplianceByState(String recordIds) {
        if(recordIds != null && recordIds != ""){
            IndicatorRecordDTO temp = new IndicatorRecordDTO();
            temp.setRecordIds(recordIds);
            List<IndicatorRecordDTO> list = indicatorRecordMapperExtra.getIndicatorRecordList(temp);

            //迭代器遍历，删除不需要新增记录的元素
            Iterator iterator = list.iterator();
            Calendar c = Calendar.getInstance();
            while (iterator.hasNext()){
                IndicatorRecordDTO indicatorRecordDTO = (IndicatorRecordDTO) iterator.next();
                if(indicatorRecordDTO.getState() == 2 || indicatorRecordDTO.getState() == 3 ){
                    iterator.remove();
                }else{
                    //将指标开始时间和结束时间增加一个月
                    c.setTime(indicatorRecordDTO.getIndicatorBeginTime());
                    c.add(Calendar.MONTH, +1);
                    indicatorRecordDTO.setIndicatorBeginTime(c.getTime());

                    c.setTime(indicatorRecordDTO.getIndicatorEndTime());
                    c.add(Calendar.MONTH, +1);
                    indicatorRecordDTO.setIndicatorEndTime(c.getTime());

//                    indicatorRecordDTO.setRecordId(StringUtil.createId());

                    if(indicatorRecordDTO.getTargetNewConsumer() <= indicatorRecordDTO.getActualNewConsumer()
                            &&indicatorRecordDTO.getTargetPromotionNumber() <= indicatorRecordDTO.getActualPromotionNumber()){
                        indicatorRecordDTO.setState(2);
                    }else{
                        indicatorRecordDTO.setState(3);
                    }
                }
            }
            int i = 0;
            Dict businessNumIndicators = dictMapperExtra.selectDictByName("businessNumIndicators");
            Dict businessConNumIndicators = dictMapperExtra.selectDictByName("businessConNumIndicators");

            Dict ordinaryNumIndicators = dictMapperExtra.selectDictByName("ordinaryNumIndicators");
            Dict ordinaryConNumIndicators = dictMapperExtra.selectDictByName("ordinaryConNumIndicators");

            if(list.size() > 0){
                i = indicatorRecordMapperExtra.ConfirmComplianceByState(list);
            }
            iterator = list.iterator();
            while (iterator.hasNext()) {
                IndicatorRecordDTO indicatorRecordDTO = (IndicatorRecordDTO) iterator.next();
                if(indicatorRecordDTO.getMissionEndTime().getTime() < new Date().getTime()){
                    iterator.remove();
                }
                if(indicatorRecordDTO.getPartner() == 0){
                    iterator.remove();
                }else if (indicatorRecordDTO.getPartner() == 2){
                    indicatorRecordDTO.setActualPromotionNumber(Integer.valueOf(businessNumIndicators.getContent()));
                    indicatorRecordDTO.setActualNewConsumer(Integer.valueOf(businessConNumIndicators.getContent()));
                }else if (indicatorRecordDTO.getPartner() == 1){
                    indicatorRecordDTO.setActualPromotionNumber(Integer.valueOf(ordinaryNumIndicators.getContent()));
                    indicatorRecordDTO.setActualNewConsumer(Integer.valueOf(ordinaryConNumIndicators.getContent()));
                }
            }
            for(IndicatorRecordDTO indicatorRecordDTO : list){
                indicatorRecordDTO.setRecordId(StringUtil.createId());
            }
            indicatorRecordMapperExtra.insertIndicatorRecordList(list);
            return i > 0;
        }else{
            return false;
        }
    }

    @Override
    public String exportExaminationRecords(HttpServletResponse response, HttpServletRequest request,IndicatorRecordDTO input) {
        List<IndicatorRecordDTO> list = indicatorRecordMapperExtra.getIndicatorRecordList(input);
        if(list.size() == 0 || list == null){
            return "所选时间没有记录";
        }else{
            try{
                Workbook workbook = getPartnerAssessmentRecordWorkBook(list);
                //设置对客户端请求的编码格式
                request.setCharacterEncoding("utf-8");
                //指定服务器返回给浏览器的编码格式
                response.setCharacterEncoding("utf-8");
                //点击下载之后出现下载对话框
                response.setContentType("application/x-download");
                String fileName = null;
                fileName = "合伙人考核记录.xlsx";
                //System.out.println(fileName);
                //将中文转换为16进制
                fileName = URLEncoder.encode(fileName,"utf-8");
                //确保浏览器弹出对应文件的对话框
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                //System.out.println("====="+out.toString());
                out.close();
                return "导出成功";
            }catch (IOException e){
                return "导出Excel数据失败，请稍后再试";
            }catch (Exception e){
                return "Excel数据量过大，请缩短导出文件的时间间隔";
            }
        }
    }

    @Override
    public Boolean statisticsPeople(PartnerDTO partnerDTO) {
        PartnerDTO partner = partnerMapperExtra.selectPartnerInfo(partnerDTO);
        List<PartnerDTO> children = partnerMapperExtra.selectPartnerChildInfo(partnerDTO);
        List<PartnerDTO> ids = new ArrayList<>();
        infoSpreadService.getChildIds(ids,children);
        List<PartnerDTO> child = getPartnerChildInfoWithTime(ids,partnerDTO);
        if (child!=null&&child.size()!=0) {
            partner.setChildPartner(child);            //获取指定月份中注册的子级用户
            partner.setActualPromotionNumber(getChildCount(partner.getChildPartner()));
            partner.setActualNewConsumer(getChildCount(getPartnerChildInfoWithMoney(ids,partnerDTO)));
        }else{
            partner.setActualNewConsumer(0);
            partner.setActualPromotionNumber(0);
        }
        if(indicatorRecordMapperExtra.statisticsPeople(partner) > 0){
            return true;
        }
        return false;
    }

    public List<PartnerDTO> getPartnerChildInfoWithTime(List<PartnerDTO> list, PartnerDTO partnerDTO){
        if(list.size()==0 || list==null){
            return new ArrayList<>();
        }
        return partnerMapperExtra.selectPartnerChildInfoWithTime(list,partnerDTO);
    }

    public int getChildCount(List<PartnerDTO> partnerDTOS){
        int count = 0;          //合伙人下级数量
        if (partnerDTOS!=null&&partnerDTOS.size()!=0){
            count = count + partnerDTOS.size();       //如果有子级就加
            for (int i = 0; i<partnerDTOS.size(); i++){
                getChildCount(partnerDTOS.get(i).getChildPartner());
            }
        }
        return count;
    }

    public List<PartnerDTO> getPartnerChildInfoWithMoney(List<PartnerDTO>list, PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfoWithMoney(list,partnerDTO);
    }

    public Workbook getPartnerAssessmentRecordWorkBook(List<IndicatorRecordDTO> list){
        String[] partnerAssessmentExcelHead = SystemConstants.PARTNER_ASSESSMENT_EXCEL_HEAD;
        Workbook workbook = new SXSSFWorkbook(list.size());
        Sheet sheet = workbook.createSheet("导出合伙人考核记录");
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < partnerAssessmentExcelHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(partnerAssessmentExcelHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);

            row.createCell(count++).setCellValue(list.get(i).getUserAccount());

            if(list.get(i).getPartner() == 1){
                row.createCell(count++).setCellValue("普通合伙人");
            }else if (list.get(i).getPartner() == 2){
                row.createCell(count++).setCellValue("事业合伙人");
            }else{
                count++;
            }

            if(list.get(i).getTargetPromotionNumber() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getTargetPromotionNumber().toString());
            }
            if(list.get(i).getActualPromotionNumber() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getActualPromotionNumber().toString());
            }
            if(list.get(i).getTargetNewConsumer() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getTargetNewConsumer().toString());
            }
            if(list.get(i).getActualNewConsumer() == null){
                count++;
            }else{
                row.createCell(count++).setCellValue(list.get(i).getActualNewConsumer().toString());
            }

            if(list.get(i).getState() == 0){
                row.createCell(count++).setCellValue("未达标");
            }else if (list.get(i).getState() == 1){
                row.createCell(count++).setCellValue("已达标");
            }else{
                count++;
            }

            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getIndicatorBeginTime()));
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getIndicatorEndTime()));
        }
        return workbook;
    }
}
