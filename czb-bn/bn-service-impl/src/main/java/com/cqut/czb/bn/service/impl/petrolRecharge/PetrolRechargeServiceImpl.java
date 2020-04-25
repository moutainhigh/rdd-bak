package com.cqut.czb.bn.service.impl.petrolRecharge;

import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleTotal;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.MessageManagementService;
import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
import com.cqut.czb.bn.service.petrolRecharge.IPetrolRechargeService;
import com.cqut.czb.bn.util.config.SendMesConfig.MesInfo;
import com.cqut.czb.bn.util.config.SendMesConfig.MessageModelInfo;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetrolRechargeServiceImpl implements IPetrolRechargeService {

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    ServerOrderServiceImpl serverOrderService;

    @Autowired
    PetrolSalesRecordsMapper petrolSalesRecordsMapper;

    @Autowired
    MessageManagementService messageManagementService;

    @Override
    public PageInfo<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(),true);
        List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolRechargeList(inputDTO);
        if (list.size()>0){ //在第一项中加入总的金额统计
            list.get(0).setTotalMoney(getPetrolRechargeListTotalMoney(inputDTO));
        }
        return new PageInfo<>(list);
    }

    public Integer getPetrolRechargeListTotalMoney(PetrolRechargeInputDTO inputDTO){
        Integer totalMoney = petrolSalesRecordsMapperExtra.getPetrolRechargeListTotalMoney(inputDTO);
        return totalMoney;
    }


    @Override
    public boolean recharge(PetrolRechargeInputDTO record) {
        boolean isRecharge = petrolSalesRecordsMapperExtra.recharge(record.getRecordId()) > 0;
        //充值油卡更新卡号操作
        if(isRecharge && record.getUpdatePetrolNum() != null && "".equals(record.getUpdatePetrolNum())){
            // 判断要更新的油卡卡号是否跟数据库中的卡号重复
            List<Petrol> repeatPetrol = petrolSalesRecordsMapperExtra.judgePetrolNumRepeat(record.getUpdatePetrolNum());
            if (repeatPetrol != null && repeatPetrol.size() > 0) {
                return false;
            }
            // 管理员修改卡号，加前缀S
            if(!record.getUpdatePetrolNum().startsWith("S")){
                record.setUpdatePetrolNum("S" + record.getUpdatePetrolNum());
            }
            petrolSalesRecordsMapperExtra.updatePetrolNum(record);
        }
//        PhoneCode phoneCode = new PhoneCode();
//        if("true".equals(phoneCode.getRechargeMessage(record.getUserAccount(), record.getPetrolNum(), record.getPetrolDenomination()))){
//            System.out.println("充值油卡发送成功");
//        }
        //发送APP内部消息 和 推送
        PetrolSalesRecords petrolSalesRecords = petrolSalesRecordsMapper.selectByPrimaryKey(record.getRecordId());
        Map<String,String> content = new HashMap<>();
        content.put("petrolKind", record.getPetrolKind());
        content.put("petrolPrice", String.valueOf(record.getPetrolDenomination()));
        serverOrderService.sendMessage(petrolSalesRecords.getBuyerId(), MesInfo.noticeId.RECHARGE_PETROL_USER.getNoticeId(),content);
        content.put("msgModelId", MessageModelInfo.RECHARGE_SUCCESS_MESSAGE_USER.getMessageModelInfo());
        content.put("receiverId", petrolSalesRecords.getBuyerId());
        content.put("userAccount", record.getUserAccount());
        content.put("petrolNum", (record.getUpdatePetrolNum() != null && record.getUpdatePetrolNum() != "") ? record.getUpdatePetrolNum() : record.getPetrolNum());
        content.put("petrolDenomination", String.valueOf(record.getPetrolDenomination()));
        messageManagementService.sendMessageToOne(content, petrolSalesRecords.getBuyerId());
        return isRecharge;
    }

    @Override
    public Workbook exportRechargeRecords(PetrolRechargeInputDTO inputDTO) throws Exception {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(),true);
        List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.exportRechargeRecords(inputDTO);
        return getWorkBook(list);
    }

    private Workbook getWorkBook(List<PetrolRechargeOutputDTO> list)throws Exception{
        String[] rechargeHead = SystemConstants.PETROL_RECHARGE_EXCEL_HEAD;
        SaleTotal saleTotal = new SaleTotal();
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出充值记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < rechargeHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rechargeHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i,  (short) 7500); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            if (list.get(i).getRecordType().equals("0")) {
                saleTotal.setFristTotalNumber(saleTotal.getFristTotalNumber()+1);
                if(list.get(i).getIsRecharged().equals("0")){
                    saleTotal.setNoFristTotalNumber(saleTotal.getNoFristTotalNumber()+1);
                    saleTotal.setNoFristTotal(saleTotal.getNoFristTotal() + list.get(i).getPetrolPrice());
                }
                else if(list.get(i).getIsRecharged().equals("1")){
                    saleTotal.setyFristTotalNumber(saleTotal.getyFristTotalNumber()+1);
                    saleTotal.setyFristTotal(saleTotal.getyFristTotal() + list.get(i).getPetrolPrice());
                }
                saleTotal.setFristTotal(saleTotal.getNoFristTotal() + saleTotal.getyFristTotal());
            } else if (list.get(i).getRecordType().equals("1")) {
                saleTotal.setContinueTotalNumber(saleTotal.getContinueTotalNumber()+1);
                if(list.get(i).getIsRecharged().equals("0")){
                    saleTotal.setNoContinueTotalNumber(saleTotal.getNoContinueTotalNumber()+1);
                    saleTotal.setNoContinueTotal(saleTotal.getNoContinueTotal() + list.get(i).getPetrolPrice());
                }
                else if(list.get(i).getIsRecharged().equals("1")){
                    saleTotal.setyContinueTotalNumber(saleTotal.getyContinueTotalNumber()+1);
                    saleTotal.setyContinueTotal(saleTotal.getyContinueTotal() + list.get(i).getPetrolPrice());
                }
                saleTotal.setContinueTotal(saleTotal.getNoContinueTotal() + saleTotal.getyContinueTotal());
            }
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            if ("1".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石油");
            }else if ("2".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石化");
            }else if("0".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("国通");
            }else{
                row.createCell(count++).setCellValue("其他");
            }
            row.createCell(count++).setCellValue(list.get(i).getPetrolDenomination());
            row.createCell(count++).setCellValue(list.get(i).getPetrolPrice());
            if ("0".equals(list.get(i).getIsRecharged())){
                row.createCell(count++).setCellValue("未充值");
            }else if ("1".equals(list.get(i).getIsRecharged())){
                row.createCell(count++).setCellValue("已充值");
            }
            if ("0".equals(list.get(i).getRecordType())){
                row.createCell(count++).setCellValue("首充");
            }else if ("1".equals(list.get(i).getRecordType())){
                row.createCell(count++).setCellValue("续充");
            }
            row.createCell(count++).setCellValue(list.get(i).getUserPhone());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getPurchaseTime()));
            if ("1".equals(list.get(i).getBuyWay())) {
                row.createCell(count++).setCellValue("支付宝");
            }else if ("2".equals(list.get(i).getBuyWay())){
                row.createCell(count++).setCellValue("微信");
            }
        }
        int index = 0;
        System.out.println(saleTotal.getFristTotalNumber());
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("首充合计");
        row.createCell(index++).setCellValue("首充人数");
        row.createCell(index++).setCellValue("续充合计");
        row.createCell(index++).setCellValue("续充人数");
        row.createCell(index++).setCellValue("首充已充合计");
        row.createCell(index++).setCellValue("首充已充人数");
        row.createCell(index++).setCellValue("续充已充合计");
        row.createCell(index++).setCellValue("续充已充人数");
        row.createCell(index++).setCellValue("首充未充合计");
        row.createCell(index++).setCellValue("首充未充人数");
        row.createCell(index++).setCellValue("续充未充合计");
        row.createCell(index++).setCellValue("续充未充人数");
        row = sheet.createRow(list.size()+2);
        index = 0;
        row.createCell(index++).setCellValue(formatNum(saleTotal.getFristTotal()) + "元");
        row.createCell(index++).setCellValue(saleTotal.getFristTotalNumber() + "人");
        row.createCell(index++).setCellValue(formatNum(saleTotal.getContinueTotal()) + "元");
        row.createCell(index++).setCellValue(saleTotal.getContinueTotalNumber() + "人");
        row.createCell(index++).setCellValue(formatNum(saleTotal.getyFristTotal()) + "元");
        row.createCell(index++).setCellValue(saleTotal.getyFristTotalNumber() + "人");
        row.createCell(index++).setCellValue(formatNum(saleTotal.getyContinueTotal()) + "元");
        row.createCell(index++).setCellValue(saleTotal.getyContinueTotalNumber() + "人");
        row.createCell(index++).setCellValue(formatNum(saleTotal.getNoFristTotal()) + "元");
        row.createCell(index++).setCellValue(saleTotal.getNoFristTotalNumber() + "人");
        row.createCell(index++).setCellValue(formatNum(saleTotal.getNoContinueTotal()) + "元");
        row.createCell(index++).setCellValue(saleTotal.getNoContinueTotalNumber() + "人");


        return workbook;
    }


    @Override
    public Workbook exportSaleRecords(GetPetrolSaleInfoInputDTO inputDTO) throws Exception {
        List<SaleInfoOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolSaleInfoList(inputDTO);
        return getSaleWorkBook(list, inputDTO);
    }

    private Workbook getSaleWorkBook(List<SaleInfoOutputDTO> list, GetPetrolSaleInfoInputDTO inputDTO) throws Exception {
        String[] rechargeHead = SystemConstants.PETROL_SALE_EXCEL_HEAD;
        SaleTotal saleTotal = petrolSalesRecordsMapperExtra.getTotal(inputDTO);
        Workbook workbook = null;
        if(list == null) {
            workbook = new SXSSFWorkbook(1);
            Sheet sheet = workbook.createSheet("导出充值记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            Cell cell = row.createCell(0);
            cell.setCellValue("该时间段无销售记录");
            return workbook;
        }
        try{
            workbook = new SXSSFWorkbook(list.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出充值记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < rechargeHead.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(rechargeHead[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 7500); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            row.createCell(count++).setCellValue(list.get(i).getThirdOrderId());
            if ("1".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石油");
            }else if ("2".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石化");
            }else if("0".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("国通");
            }else{
                row.createCell(count++).setCellValue("其他");
            }
            row.createCell(count++).setCellValue(list.get(i).getPetrolDenomination());
            row.createCell(count++).setCellValue(list.get(i).getPetrolPrice());
            row.createCell(count++).setCellValue(list.get(i).getOwner());
            if ("1".equals(list.get(i).getPaymentMethod())) {
                row.createCell(count++).setCellValue("支付宝");
            }else if ("2".equals(list.get(i).getPaymentMethod())){
                row.createCell(count++).setCellValue("微信");
            }
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getTransactionTime()));
            row.createCell(count++).setCellValue(list.get(i).getArea());

            BigDecimal bg = new BigDecimal(Float.parseFloat(list.get(i).getPetrolPrice()));
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if(list.get(i).getPetrolNum().length() < 15){
                row.createCell(count++).setCellValue("首充");
                saleTotal.setFristTotalNumber(saleTotal.getFristTotalNumber()+1);
                saleTotal.setFristTotal(saleTotal.getFristTotal() + f1);
            }else {
                row.createCell(count++).setCellValue("续充");
                saleTotal.setContinueTotalNumber(saleTotal.getContinueTotalNumber()+1);
                saleTotal.setContinueTotal(saleTotal.getContinueTotal() + f1);
            }
        }
        saleTotal.setTotal(saleTotal.getFristTotal() + saleTotal.getContinueTotal() + saleTotal.getVipRecordTotal());
        int index = 0;
        row = sheet.createRow(list.size()+1);
        row.createCell(index++).setCellValue("会员人数");
        row.createCell(index++).setCellValue("会员费合计");
        row.createCell(index++).setCellValue("首充人数");
        row.createCell(index++).setCellValue("首充合计");
        row.createCell(index++).setCellValue("续充人数");
        row.createCell(index++).setCellValue("续充合计");
        row.createCell(index++).setCellValue("总计");

        index = 0;
        row = sheet.createRow(list.size()+2);
        row.createCell(index++).setCellValue(saleTotal.getVipRecordTotalNumber() + "人");
        row.createCell(index++).setCellValue(saleTotal.getVipRecordTotal() + "元");
        row.createCell(index++).setCellValue(saleTotal.getFristTotalNumber() + "人");
        row.createCell(index++).setCellValue(saleTotal.getFristTotal() + "元");
        row.createCell(index++).setCellValue(saleTotal.getContinueTotalNumber() + "人");
        row.createCell(index++).setCellValue(saleTotal.getContinueTotal() + "元");
        row.createCell(index++).setCellValue(saleTotal.getTotal() + "元");

        return workbook;
    }

    public String formatNum(double num) {
        String a = String.format("%.2f",num);
        return a;
    }
}
