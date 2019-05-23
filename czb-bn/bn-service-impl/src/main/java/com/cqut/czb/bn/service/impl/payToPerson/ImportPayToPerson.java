package com.cqut.czb.bn.service.impl.payToPerson;

import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.util.string.StringUtil;
import io.swagger.models.auth.In;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportPayToPerson {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String LIB_PATH = "lib";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";
    public static Integer processing = 0;

    /**
     * read the Excel file
     *
     * @param filename
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static List<PayToPersonDTO> readExcel(String filename,
                                                 InputStream inputStream) throws Exception {
        List<PayToPersonDTO> list = null;// 存入解析的DTO
        if (filename == null || EMPTY.equals(filename)) {
            return null;
        } else {
            String postfix = getPostfix(filename);
            if (!EMPTY.equals(postfix)) {
                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    list = readXls(inputStream);
                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    list = readXlsx(inputStream);
                }
            } else {
            }
        }
        return list;
    }

    /**
     * Read the Excel 2010
     *
     * @return
     * @throws Exception
     */
    public static List<PayToPersonDTO> readXlsx(InputStream is) throws Exception {
        List<PayToPersonDTO> list = new ArrayList<PayToPersonDTO>();
        PayToPersonDTO PayToPersonDTO = null;

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                // 解析文档
                if (xssfRow != null && xssfRow.getLastCellNum() >= 7 && xssfRow.getCell(0) != null) {
                    PayToPersonDTO = resolveXlsx(xssfRow);
                    if (PayToPersonDTO != null) {
                        list.add(PayToPersonDTO);
                    }

                }
            }
        }
        xssfWorkbook.close();
        return list;
    }

    /**
     * @param xssfRow
     * @return
     */
    private static PayToPersonDTO resolveXlsx(XSSFRow xssfRow) throws Exception {
        if (xssfRow.getCell(0) == null || xssfRow.getCell(0).toString() == "") {
            return null;
        }
        PayToPersonDTO payToPersonDTO = new PayToPersonDTO();
        payToPersonDTO.setRecordId(StringUtil.createId());
        payToPersonDTO.setContractRecordId(getStringValue(xssfRow.getCell(0)));
        payToPersonDTO.setPayeeName((getStringValue(xssfRow.getCell(1))));
        System.out.println(getStringValue(xssfRow.getCell(2)));
        payToPersonDTO.setPayeeIdCard(getStringValue(xssfRow.getCell(2)));
        payToPersonDTO.setBankOfDeposit((getStringValue(xssfRow.getCell(3))));
        payToPersonDTO.setBankAccountNum((getStringValue(xssfRow.getCell(4))));
        payToPersonDTO.setPayableMoney(Double.parseDouble(getStringValue(xssfRow.getCell(5))));
        payToPersonDTO.setActualPayMoney(Double.parseDouble(getStringValue(xssfRow.getCell(6))));
        if (payToPersonDTO.getPayableMoney().equals(payToPersonDTO.getActualPayMoney())){
            payToPersonDTO.setState(2);
        }else if (!payToPersonDTO.getPayableMoney().equals(payToPersonDTO.getActualPayMoney())){
            payToPersonDTO.setState(1);
        }
        try {
            payToPersonDTO.setPlatformPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getStringValue(xssfRow.getCell(8))));
        }catch (Exception e){
            throw new Exception("时间为空");
        }
        payToPersonDTO.setIsDeleted(0);

        return payToPersonDTO;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws Exception
     */
    public static List<PayToPersonDTO> readXls(InputStream is)
            throws Exception{
        List<PayToPersonDTO> list = new ArrayList<PayToPersonDTO>();
//        Petrol petrol = null;

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            HSSFRow hssfRow = null;
            PayToPersonDTO obj = null;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                hssfRow = hssfSheet.getRow(rowNum);
                // 解析文档
                // System.out.println(rowNum);
                if (hssfRow != null && hssfRow.getLastCellNum() >= 7 && hssfRow.getCell(0) != null) {
                    obj = resolveXls(hssfRow);
                    processing++;
                    if (obj != null) {
                        list.add(obj);
                    }
                }
            }
        }
        hssfWorkbook.close();
        return list;
    }

    private static PayToPersonDTO resolveXls(HSSFRow hssfRow) throws Exception{
        PayToPersonDTO payToPersonDTO = new PayToPersonDTO();
        if (hssfRow.getCell(0) == null || hssfRow.getCell(0).toString() == "") {
            return null;
        }
        payToPersonDTO.setRecordId(StringUtil.createId());
        payToPersonDTO.setContractRecordId(getStringValue(hssfRow.getCell(0)));
        payToPersonDTO.setPayeeName((getStringValue(hssfRow.getCell(1))));
        System.out.println(getStringValue(hssfRow.getCell(2)));
        payToPersonDTO.setPayeeIdCard(getStringValue(hssfRow.getCell(2)));
        payToPersonDTO.setBankOfDeposit((getStringValue(hssfRow.getCell(3))));
        payToPersonDTO.setBankAccountNum((getStringValue(hssfRow.getCell(4))));
        payToPersonDTO.setPayableMoney(Double.parseDouble(getStringValue(hssfRow.getCell(5))));
        payToPersonDTO.setActualPayMoney(Double.parseDouble(getStringValue(hssfRow.getCell(6))));
        if (payToPersonDTO.getPayableMoney().equals(payToPersonDTO.getActualPayMoney())){
            payToPersonDTO.setState(2);
        }else if (payToPersonDTO.getPayableMoney().equals(payToPersonDTO.getActualPayMoney())){
            payToPersonDTO.setState(1);
        }
        try {
            payToPersonDTO.setPlatformPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getStringValue(hssfRow.getCell(7))));
        }catch (Exception e){
            throw new Exception("时间为空");
        }
        return payToPersonDTO;
    }

    // 表格数据转字符串
    private static String getValue(XSSFCell xssfCell) {
        if (xssfCell == null)
            return null;

        String data = xssfCell.toString();

        try {
            DecimalFormat df = new DecimalFormat("0");
            data = Double.parseDouble(df.format(data)) + "";
        } catch (Exception e) {
            data = xssfCell.toString();
        }

        return data;
    }

    private static BigDecimal getBigDecimal(String str) {
        Double.parseDouble(str);
        return new BigDecimal(str);
    }

    // 表格数据转字符串
    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell == null)
            return null;
        String data = hssfCell.toString();
        try {
            DecimalFormat df = new DecimalFormat("0");
            data = Double.parseDouble(df.format(data)) + "";
        } catch (Exception e) {
            data = hssfCell.toString();
        }
        return data;
    }

    // 判断文档类型是xls还是xlsx
    public static String getPostfix(String filename) {
        if (filename == null || EMPTY.equals(filename.trim())) {
            return EMPTY;
        }
        if (filename.contains(POINT)) {
            return filename.substring(filename.lastIndexOf(POINT) + 1,
                    filename.length());
        }
        return EMPTY;
    }

    private static String getStringValue(HSSFCell hssfCell) {
        if (hssfCell == null)
            return null;
//        hssfCell.setCellStyle();
//        hssfCell.setCellType(Cell.CELL_TYPE_STRING);
//        hssfCell.setCellType(CellType.STRING);
        return hssfCell.getStringCellValue();
    }

    private static String getStringValue(XSSFCell xssfCell) {
        if (xssfCell == null)
            return null;
//        xssfCell.setCellStyle(XSSFCell.CELL_TYPE_STRING);
//        xssfCell.setCellType(Cell.CELL_TYPE_STRING);
//        xssfCell.setCellType(CellType.STRING);
        return xssfCell.getStringCellValue();
    }
}
