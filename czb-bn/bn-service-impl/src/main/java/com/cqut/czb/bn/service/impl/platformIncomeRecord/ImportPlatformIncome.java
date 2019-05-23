package com.cqut.czb.bn.service.impl.platformIncomeRecord;

import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.util.string.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportPlatformIncome {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String LIB_PATH = "lib";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";
    public static Integer processNum=0;

    /**
     * read the Excel file
     *
     * @param filename
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static List<PlatformIncomeRecordsDTO> readExcel(String filename,
                                                           InputStream inputStream) throws Exception {
        List<PlatformIncomeRecordsDTO> list = null;// 存入解析的DTO
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
    public static List<PlatformIncomeRecordsDTO> readXlsx(InputStream is) throws Exception {
        List<PlatformIncomeRecordsDTO> list = new ArrayList<PlatformIncomeRecordsDTO>();
        PlatformIncomeRecordsDTO platformIncomeRecordsDTO = null;

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
                    platformIncomeRecordsDTO = resolveXlsx(xssfRow);
                    processNum++;
                    System.out.println("异步调用"+processNum);
                    if (platformIncomeRecordsDTO != null) {
                        list.add(platformIncomeRecordsDTO);
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
    private static PlatformIncomeRecordsDTO resolveXlsx(XSSFRow xssfRow) throws Exception {
        if (xssfRow.getCell(0) == null || xssfRow.getCell(0).toString() == "") {
            return null;
        }
        PlatformIncomeRecordsDTO platformIncomeRecordsDTO = new PlatformIncomeRecordsDTO();
        platformIncomeRecordsDTO.setRecordId(StringUtil.createId());
        platformIncomeRecordsDTO.setContractRecordId(getStringValue(xssfRow.getCell(0)));
        platformIncomeRecordsDTO.setUserName((getStringValue(xssfRow.getCell(1))));
        System.out.println(Double.parseDouble(getStringValue(xssfRow.getCell(2))));
        platformIncomeRecordsDTO.setReceivableMoney(Double.parseDouble(getStringValue(xssfRow.getCell(2))));
        platformIncomeRecordsDTO.setActualReceiptsMoney(Double.parseDouble(getStringValue(xssfRow.getCell(3))));
        platformIncomeRecordsDTO.setTargetYearMonth(new SimpleDateFormat("yyyy-MM").parse(getStringValue(xssfRow.getCell(4))));
        platformIncomeRecordsDTO.setEnterprisePayTime(new SimpleDateFormat("yyyy-MM-dd").parse(getStringValue(xssfRow.getCell(5))));
        if (!platformIncomeRecordsDTO.getReceivableMoney().equals(platformIncomeRecordsDTO.getActualReceiptsMoney())){
            platformIncomeRecordsDTO.setState(2);
            platformIncomeRecordsDTO.setIsDistributed(0);
        }else {
            platformIncomeRecordsDTO.setState(1);
            platformIncomeRecordsDTO.setIsDistributed(1);
        }
        platformIncomeRecordsDTO.setIsDeleted(0);
        return platformIncomeRecordsDTO;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws Exception
     */
    public static List<PlatformIncomeRecordsDTO> readXls(InputStream is)
            throws Exception{
        List<PlatformIncomeRecordsDTO> list = new ArrayList<PlatformIncomeRecordsDTO>();
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
            PlatformIncomeRecordsDTO obj = null;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                hssfRow = hssfSheet.getRow(rowNum);
                // 解析文档
                // System.out.println(rowNum);
                if (hssfRow != null && hssfRow.getLastCellNum() >= 7 && hssfRow.getCell(0) != null) {
                    obj = resolveXls(hssfRow);
                    processNum++;
                    if (obj != null) {
                        list.add(obj);
                    }
                }
            }
        }
        hssfWorkbook.close();
        return list;
    }

    private static PlatformIncomeRecordsDTO resolveXls(HSSFRow hssfRow) throws Exception{
        PlatformIncomeRecordsDTO platformIncomeRecordsDTO = new PlatformIncomeRecordsDTO();
        if (hssfRow.getCell(0) == null || hssfRow.getCell(0).toString() == "") {
            return null;
        }
        platformIncomeRecordsDTO.setRecordId(StringUtil.createId());
        platformIncomeRecordsDTO.setContractRecordId(getStringValue(hssfRow.getCell(0)));
        platformIncomeRecordsDTO.setUserName((getStringValue(hssfRow.getCell(1))));
        System.out.println(getStringValue(hssfRow.getCell(2)));
        platformIncomeRecordsDTO.setReceivableMoney(Double.parseDouble(getStringValue(hssfRow.getCell(2))));
        platformIncomeRecordsDTO.setActualReceiptsMoney(Double.parseDouble(getStringValue(hssfRow.getCell(3))));
        platformIncomeRecordsDTO.setTargetYearMonth(new SimpleDateFormat("yyyy-MM").parse(getStringValue(hssfRow.getCell(4))));
        platformIncomeRecordsDTO.setEnterprisePayTime(new SimpleDateFormat("yyyy-MM-dd").parse(getStringValue(hssfRow.getCell(5))));
        if (platformIncomeRecordsDTO.getReceivableMoney()==platformIncomeRecordsDTO.getActualReceiptsMoney()){
            platformIncomeRecordsDTO.setState(2);
        }else if (platformIncomeRecordsDTO.getReceivableMoney()==platformIncomeRecordsDTO.getActualReceiptsMoney()){
            platformIncomeRecordsDTO.setState(1);
        }
        platformIncomeRecordsDTO.setIsDeleted(0);
        return platformIncomeRecordsDTO;
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
