package com.cqut.czb.bn.service.impl.petrolDeliveryRecords;

import com.cqut.czb.bn.dao.mapper.PetrolDeliveryRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryInput;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.DeliveryMessageDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.service.PetrolDeliveryRecordsService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 油卡邮寄功能
 *
 */
@Service
@Transactional
public class PetrolDeliveryRecordsServiceImpl implements PetrolDeliveryRecordsService {



    @Autowired
    PetrolDeliveryRecordsMapperExtra petrolDeliveryRecordsMapperExtra;

    @Override
    public PageInfo<PetrolDeliveryDTO> selectPetrolDelivery(DeliveryInput deliveryInput, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PetrolDeliveryDTO > petrolDeliveryDTOList = petrolDeliveryRecordsMapperExtra.selectByPrimaryKey(deliveryInput);
        return new PageInfo<>(petrolDeliveryDTOList);
    }

    @Override
    public Boolean deleteByPrimaryKey(String recordId) {
        return petrolDeliveryRecordsMapperExtra.deleteByPrimaryKey(recordId)>0;
    }

    @Override
    public Boolean updatePetrolDelivery(DeliveryInput deliveryInput) {
        if (deliveryInput.getDeliveryNum()!=null&&deliveryInput.getDeliveryNum()!=""&&deliveryInput.getDeliveryCompany()!=null&&deliveryInput.getDeliveryCompany()!=""){
            deliveryInput.setDeliveryState(1);
        }else {
            deliveryInput.setDeliveryState(0);
        }
        Boolean updateDeliveryRecord = petrolDeliveryRecordsMapperExtra.updateByPrimaryKey(deliveryInput)>0;
        if(updateDeliveryRecord){
            PhoneCode phoneCode = new PhoneCode();
            DeliveryMessageDTO messageDTO  = petrolDeliveryRecordsMapperExtra.selectDeliveryMessage(deliveryInput.getRecordId());
            phoneCode.getDeliveryMessage(messageDTO.getUserAccount(), messageDTO.getPetrolNum(), messageDTO.getDeliveryNum());
        }
        //发送APP内部消息 和 推送
//        PetrolSalesRecords petrolSalesRecords = petrolSalesRecordsMapper.selectByPrimaryKey(record.getRecordId());
//        Map<String,String> content = new HashMap<>();
//        content.put("petrolKind", record.getPetrolKind());
//        content.put("petrolPrice", String.valueOf(record.getPetrolDenomination()));
//        serverOrderService.sendMessage(petrolSalesRecords.getBuyerId(), MesInfo.noticeId.RECHARGE_PETROL_USER.getNoticeId(),content);
//        content.put("msgModelId", MessageModelInfo.RECHARGE_SUCCESS_MESSAGE_USER.getMessageModelInfo());
//        content.put("receiverId", petrolSalesRecords.getBuyerId());
//        content.put("userAccount", record.getUserAccount());
//        content.put("petrolNum", (record.getUpdatePetrolNum() != null && record.getUpdatePetrolNum() != "") ? record.getUpdatePetrolNum() : record.getPetrolNum());
//        content.put("petrolDenomination", String.valueOf(record.getPetrolDenomination()));
//        messageManagementService.sendMessageToOne(content, petrolSalesRecords.getBuyerId());
        return updateDeliveryRecord;
    }

    @Override
    public String selectLogistics(DeliveryInput deliveryInput) {
        String search = "";
            if (getLogisticCode(deliveryInput.getDeliveryCompany())==null){
                System.out.println("公司为空");
                return "";
            }
            if (deliveryInput.getDeliveryNum()==null||deliveryInput.getDeliveryNum().equals("")){
                return "";
            }
           String ShipperCode = getLogisticCode(deliveryInput.getDeliveryCompany());
        try {
             search = KdniaoTrackQueryAPI.getOrderTraces(ShipperCode,
                    deliveryInput.getDeliveryNum());
        } catch (Exception e) {
            System.out.println("错误！！！");
            return null;
        }
        System.out.println("ss"+search);
        return search;
    }

    //通过快递公司名称获取快递公司编号
    public String getLogisticCode(String deliveryCompany){
            if (deliveryCompany.equals("顺丰速运")){
                return "SF";
            }else if (deliveryCompany.equals("中通快递")){
                return "ZTO";
            }else if (deliveryCompany.equals("圆通速递")){
                return "YTO";
            }else if (deliveryCompany.equals("韵达速递")){
                return "YD";
            }
            else if (deliveryCompany.equals("邮政快递包裹")){
                return "YZPY";
            }
            else if(deliveryCompany.equals("EMS")){
                return "EMS";
            }else if (deliveryCompany.equals("天天快递")){
                return "HHTT";
            } else {
                return null;
            }
    }

    @Override   //批量确认收货
    public Boolean receivePetrolDelivery(String ids) {
        String[] id = ids.split(",");
        return (petrolDeliveryRecordsMapperExtra.updateReceivePetrolDeliveryState(id)>0);
    }

    @Override
    public Workbook exportDeliveryRecords(DeliveryInput deliveryInput) throws Exception {
        List<PetrolDeliveryDTO> petrolDeliveryDTOList = petrolDeliveryRecordsMapperExtra.selectByPrimaryKey(deliveryInput);
        System.out.println("yyyyyyyy"+petrolDeliveryDTOList.size());
        if(petrolDeliveryDTOList==null||petrolDeliveryDTOList.size()==0){
            return getWorkBook(null);
        }
        return getWorkBook(petrolDeliveryDTOList);
    }

    public Workbook getWorkBook(List<PetrolDeliveryDTO> petrolDeliveryDTOS)throws Exception{
        String[] petrolDeliveryRecordHeader = SystemConstants.PETROL_DELIVERY_RECORD_HEAD;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(petrolDeliveryDTOS.size());
        } catch (Exception e) {
               throw new Exception("Excel数据量过大，请缩短时间间隔");
           }
           Sheet sheet = workbook.createSheet("导出寄送记录");//创建工作表
            Row row =sheet.createRow(0);//创建行从第0行开始
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
            for (int i = 0; i < petrolDeliveryRecordHeader.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(petrolDeliveryRecordHeader[i]);
                cell.setCellStyle(style);
                sheet.setColumnWidth(i, (short) 6000); // 设置列宽
            }
            for (int i = 0 ; i<petrolDeliveryDTOS.size(); i++){
                int count = 0;
                row = sheet.createRow(i+1);

                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getPetrolNum());
                if (petrolDeliveryDTOS.get(i).getPetrolKind()==0)
                    row.createCell(count++).setCellValue("国通");
                else if(petrolDeliveryDTOS.get(i).getPetrolKind()==1)
                    row.createCell(count++).setCellValue("中石油");
                else if (petrolDeliveryDTOS.get(i).getPetrolKind()==2)
                    row.createCell(count++).setCellValue("中石化");

                if (petrolDeliveryDTOS.get(i).getDeliveryState()==0)
                row.createCell(count++).setCellValue("未邮寄");
                else if(petrolDeliveryDTOS.get(i).getDeliveryState()==1)
                    row.createCell(count++).setCellValue("邮寄中");
                else if (petrolDeliveryDTOS.get(i).getDeliveryState()==2)
                    row.createCell(count++).setCellValue("已收货");
//                row.createCell(count++).setCellType(CellType.STRING);
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getReceiver());
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(petrolDeliveryDTOS.get(i).getCreateAt()));
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getContactNumber());
                row.createCell(count).setCellType(CellType.STRING);
                if (petrolDeliveryDTOS.get(i).getProvince()==null){
                    petrolDeliveryDTOS.get(i).setProvince("");
                }
                if (petrolDeliveryDTOS.get(i).getCity()==null){
                    petrolDeliveryDTOS.get(i).setCity("");
                }
                if(petrolDeliveryDTOS.get(i).getArea()==null){
                    petrolDeliveryDTOS.get(i).setArea("");
                }
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getProvince()+petrolDeliveryDTOS.get(i).getCity()+petrolDeliveryDTOS.get(i).getArea());
                row.createCell(count).setCellType(CellType.STRING);
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDetail());
                row.createCell(count).setCellType(CellType.STRING);
                if (petrolDeliveryDTOS.get(i).getDeliveryNum()==null){
                    petrolDeliveryDTOS.get(i).setDeliveryNum("");
                }
                row.createCell(count++).setCellValue(""+petrolDeliveryDTOS.get(i).getDeliveryNum());
                row.createCell(count).setCellType(CellType.STRING);
                if (petrolDeliveryDTOS.get(i).getDeliveryCompany()==null){
                    petrolDeliveryDTOS.get(i).setDeliveryCompany("");
                }
                row.createCell(count++).setCellValue(petrolDeliveryDTOS.get(i).getDeliveryCompany());
            }
            return workbook;
    }

    @Override
    public int ImportDeliveryRecords(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        List<PetrolDeliveryDTO> petrolDeliveryDTOList = null;
        Map<String, PetrolDeliveryDTO> petrolMap = new HashMap<>();
        petrolDeliveryDTOList = ImportPetrolDelivery.readExcel(file.getOriginalFilename(), inputStream);

        /**
         * 按petrolNum为key 做到去重复的效果
         */
        if (petrolDeliveryDTOList != null) {
            for (PetrolDeliveryDTO p : petrolDeliveryDTOList) {
                petrolMap.put(p.getPetrolNum(), p);
            }
        }
        List<PetrolDeliveryDTO> petrolListNoRepeat = new ArrayList<>();
        for(PetrolDeliveryDTO p:petrolMap.values()){
            PhoneCode code = new PhoneCode();
            DeliveryMessageDTO messageDTO  = petrolDeliveryRecordsMapperExtra.selectDeliveryMessageByPetrolNum(p.getPetrolNum());
            code.getDeliveryMessage(messageDTO.getUserAccount(), messageDTO.getPetrolNum(), p.getDeliveryNum());
            petrolListNoRepeat.add(p);
        }
        int countForInsert = petrolDeliveryRecordsMapperExtra.updateImportRecords(petrolListNoRepeat);
        return countForInsert;
    }

    @Override
    public String selectLogistics() {
        return null;
    }

    @Override
    public PetrolDeliveryDTO getDeliveryInfo(String userId, String petrolKind) {

        return  petrolDeliveryRecordsMapperExtra.getDeliveryInfo(userId,petrolKind);
    }
}
