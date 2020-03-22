package com.cqut.czb.bn.service.impl.petrolRecharge;

import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapper;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
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
       return new PageInfo<>(list);
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
        List<PetrolRechargeOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolRechargeList(inputDTO);
        return getWorkBook(list);
    }

    private Workbook getWorkBook(List<PetrolRechargeOutputDTO> list)throws Exception{
        String[] rechargeHead = SystemConstants.PETROL_RECHARGE_EXCEL_HEAD;
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
            sheet.setColumnWidth(i, (short) 5000); // 设置列宽
        }
        for (int i = 0 ; i<list.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count++).setCellValue(list.get(i).getPetrolNum());
            if ("1".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石油");
            }else if ("2".equals(list.get(i).getPetrolKind())){
                row.createCell(count++).setCellValue("中石化");
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
            row.createCell(count++).setCellValue(list.get(i).getUserPhone());
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getPurchaseTime()));
            if ("1".equals(list.get(i).getBuyWay())) {
                row.createCell(count++).setCellValue("支付宝");
            }else if ("2".equals(list.get(i).getBuyWay())){
                row.createCell(count++).setCellValue("微信");
            }
        }
        return workbook;
    }


    @Override
    public Workbook exportSaleRecords(GetPetrolSaleInfoInputDTO inputDTO) throws Exception {
        List<SaleInfoOutputDTO> list = petrolSalesRecordsMapperExtra.getPetrolSaleInfoList(inputDTO);
        return getSaleWorkBook(list);
    }

    private Workbook getSaleWorkBook(List<SaleInfoOutputDTO> list) throws Exception {
        String[] rechargeHead = SystemConstants.PETROL_SALE_EXCEL_HEAD;
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
            sheet.setColumnWidth(i, (short) 5000); // 设置列宽
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
        }
        return workbook;
    }
}
