package com.cqut.czb.bn.service.impl.platformIncomeRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.PlatformIncomeRecordsService;
import com.cqut.czb.bn.service.impl.payToPerson.ImportPayToPerson;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformIncomeRecordServiceImpl implements PlatformIncomeRecordsService{

    @Autowired
    private PlatformIncomeRecordsMapperExtra platformIncomeRecordsMapperExtra;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    private ContractRecordsMapperExtra contractRecordsMapperExtra;

    @Autowired
    private PetrolDeliveryRecordsMapperExtra petrolDeliveryRecordsMapperExtra;

    @Autowired
    private AddressMapperExtra addressMapperExtra;
    @Override
    public PageInfo<PlatformIncomeRecordsDTO> getReceiveRecords(PlatformIncomeRecordsDTO records, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = platformIncomeRecordsMapperExtra.selectByPrimaryKey(records);
        return new PageInfo<>(platformIncomeRecordsDTOS);
    }

    @Override
    public Workbook exportRecords(PlatformIncomeRecordsDTO platformIncomeRecordsDTO) throws Exception {
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = platformIncomeRecordsMapperExtra.selectIncomeList(platformIncomeRecordsDTO); //查询选择月份中有哪些合同
        if (platformIncomeRecordsDTOS==null||platformIncomeRecordsDTOS.size()==0){
            return null;
        }
        List<PlatformIncomeRecordsDTO> incomeMoney = platformIncomeRecordsMapperExtra.selectIncome(platformIncomeRecordsDTOS);//查询这些合同企业的打款总数
        for(int i=0;i<platformIncomeRecordsDTOS.size();i++){ //将合同与应打款合并
            for (int j=0;j<incomeMoney.size();j++){
                if(platformIncomeRecordsDTOS.get(i).getContractRecordId().equals(incomeMoney.get(i).getContractRecordId())){
                    platformIncomeRecordsDTOS.get(i).setRecordId(StringUtil.createId());
                    platformIncomeRecordsDTOS.get(i).setReceivableMoney(incomeMoney.get(i).getReceivableMoney());
                    platformIncomeRecordsDTOS.get(i).setTargetYearMonth(new SimpleDateFormat("yyyy-MM-dd").parse(platformIncomeRecordsDTO.getExportTime()));  //将有合同数据的目标年月数写为选择的年月数
                    platformIncomeRecordsDTOS.get(i).setState(0); //将平台对该合同的收款状态设为未收款
                    platformIncomeRecordsDTOS.get(i).setIsDeleted(0);
                }
            }
        }
        int isInert = platformIncomeRecordsMapperExtra.insert(platformIncomeRecordsDTOS);
        Workbook workbook = null;
        if (isInert>0){
         workbook = getWorkBook(platformIncomeRecordsDTOS);}
        return workbook;
    }

    @Override
    public boolean handleManyPlatFormIncomeRecords(String contractRecordIds) {
        String[] contractRecordId=contractRecordIds.split(",");
        for(int i=0;i<contractRecordId.length;i++){
            handleOnePlatFormIncomeRecord(contractRecordId[i]);
        }
        return true;
    }


    @Override
    public boolean handleOnePlatFormIncomeRecord(String contractRecordId) {
        if(contractRecordId==null)//数据为空
            return false;
        PetrolSalesRecords petrolSalesRecords=isHaveDistributionPetrol(contractRecordId);//查出购买记录
        double petrolPrice=100;//无法确定（暂时死的数据）

        if(petrolSalesRecords==null){//为空则————分配油卡
            System.out.println("以前没有分配过油卡，合同id为："+contractRecordId);
            //开始查询相关信息（petrolKind,petrolPrice,owerId）
            PetrolInputDTO petrolInputDTO=contractRecordsMapperExtra.selectOwnerId(contractRecordId);
            if(petrolInputDTO==null){
                System.out.println("数据为空");
                return false;
            }
            petrolInputDTO.setPetrolKind(petrolInputDTO.getPetrolKind());
            petrolInputDTO.setPetrolPrice(petrolInputDTO.getPetrolPrice());
            petrolInputDTO.setOwnerId(petrolInputDTO.getOwnerId());
            Petrol petrol= PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
            if(petrol==null) {
                System.out.println("无对应的油卡");
                return false;
            }
            else {//有相应的油卡
                //更改卡的状态
                petrol.setState(2);
                boolean statePetrol= petrolMapperExtra.updateByPrimaryKeySelective(petrol) > 0;
                System.out.println("油卡状态更改"+statePetrol+":"+petrol.getPetrolNum());
                //新增油卡的购买销售信息
                petrolSalesRecords=new PetrolSalesRecords();
                petrolSalesRecords.setPetrolId(petrol.getPetrolId());
                petrolSalesRecords.setBuyerId(petrolInputDTO.getOwnerId());
                petrolSalesRecords.setPaymentMethod(4);//4为合同打款
                petrolSalesRecords.setPetrolKind(petrolInputDTO.getPetrolKind());//油卡种类
                petrolSalesRecords.setPetrolNum(petrol.getPetrolNum());//卡号
                petrolSalesRecords.setRecordId(StringUtil.createId());
                petrolSalesRecords.setState(1);//1为已支付
                petrolSalesRecords.setTurnoverAmount(petrol.getPetrolPrice());
                petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());
                petrolSalesRecords.setRecordType(0);
                petrolSalesRecords.setIsRecharged(0);
                petrolSalesRecords.setContractId(contractRecordId);
                boolean insertPetrolSalesRecords = petrolSalesRecordsMapperExtra.insert(petrolSalesRecords) > 0;
                System.out.println("新增购买记录表完毕" + insertPetrolSalesRecords);

                //新增油卡邮寄记录——插入
                PetrolDeliveryRecords petrolDeliveryRecords = new PetrolDeliveryRecords();
                String addressId=addressMapperExtra.selectAddressId(contractRecordId);
                petrolDeliveryRecords.setAddressId(addressId);
                petrolDeliveryRecords.setPetrolNum(petrol.getPetrolNum());
                petrolDeliveryRecords.setDeliveryState(0);
                petrolDeliveryRecords.setRecordId(StringUtil.createId());
                boolean isInsert = petrolDeliveryRecordsMapperExtra.insert(petrolDeliveryRecords) > 0;
                System.out.println("新增油卡邮寄记录" + isInsert);
                //成功后移除对应的卡
                PetrolCache.currentPetrolMap.remove(petrol.getPetrolNum());
                return true;
            }
        }else {//不为空充值
            System.out.println("以前分配过油卡，将充值，合同id为："+contractRecordId);
            //新增购买记录表——插入（充值）;
            petrolSalesRecords.setPaymentMethod(4);
            petrolSalesRecords.setRecordId(StringUtil.createId());
            petrolSalesRecords.setState(1);//1为已支付
            petrolSalesRecords.setTurnoverAmount(petrolSalesRecords.getTurnoverAmount());///价格有问题**********
            petrolSalesRecords.setIsRecharged(1);
            petrolSalesRecords.setContractId(contractRecordId);
            boolean insertPetrolSalesRecords=petrolSalesRecordsMapperExtra.insert(petrolSalesRecords)>0;
            System.out.println("新增油卡充值记录完毕"+insertPetrolSalesRecords);
            return true;
        }
    }

    @Override
    public PetrolSalesRecords isHaveDistributionPetrol(String contractRecordId ) {
        /**
         * 通过个人合同id查到相应的油卡（油卡购买记录表）
         */
        if(contractRecordId==null)
            return null;
        else{
            PetrolSalesRecords petrolSalesRecords=petrolSalesRecordsMapperExtra.selectPetrolByContractId(contractRecordId);
            return petrolSalesRecords;
        }
    }

    //导入生成execl表
  //  @Async
    @Override
    public int importRecords(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS = null;
        Map<String, PayToPersonDTO> personDTOMap = new HashMap<>();
        platformIncomeRecordsDTOS = ImportPlatformIncome.readExcel(file.getOriginalFilename(), inputStream);
        System.out.println("99999999"+platformIncomeRecordsDTOS.get(0).getContractRecordId());
        int countForInsert = platformIncomeRecordsMapperExtra.updateImportData(platformIncomeRecordsDTOS);
//        System.out.println("countForInsert " + countForInsert);
        return countForInsert;
    }

    //导出生成execl表
    public Workbook getWorkBook(List<PlatformIncomeRecordsDTO> platformIncomeRecordsDTOS)throws Exception{
        String[] platformIncomeRecordsHeader = SystemConstants.PLATFORM_INCOME_RECORDS;
        Workbook workbook = null;
        try{
            workbook = new SXSSFWorkbook(platformIncomeRecordsDTOS.size());
        } catch (Exception e) {
            throw new Exception("Excel数据量过大，请缩短时间间隔");
        }
        Sheet sheet = workbook.createSheet("导出平台收款记录");//创建工作表
        Row row =sheet.createRow(0);//创建行从第0行开始
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //对齐方式
        for (int i = 0; i < platformIncomeRecordsHeader.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(platformIncomeRecordsHeader[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short) 6000); // 设置列宽
        }
        for (int i = 0 ; i<platformIncomeRecordsDTOS.size(); i++){
            int count = 0;
            row = sheet.createRow(i+1);
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(platformIncomeRecordsDTOS.get(i).getContractRecordId());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(platformIncomeRecordsDTOS.get(i).getUserName());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(platformIncomeRecordsDTOS.get(i).getReceivableMoney());
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("");
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue(new SimpleDateFormat("yyyy-MM").format(platformIncomeRecordsDTOS.get(i).getTargetYearMonth()));
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("");
            row.createCell(count).setCellType(CellType.STRING);
            row.createCell(count++).setCellValue("未打款");
        }
        return workbook;
    }

}
