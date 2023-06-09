package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RechargeOrderUpdateImportHelper {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String LIB_PATH = "lib";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";

    /**
     * read the Excel file
     *
     * @param filename
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static List<DirectChargingOrderDto> readExcel(String filename,
                                                         InputStream inputStream) throws IOException, ParseException {
        List<DirectChargingOrderDto> list = null;// 存入解析的DTO
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
     * @throws IOException
     */
    public static List<DirectChargingOrderDto> readXlsx(InputStream is) throws IOException, ParseException {

        List<DirectChargingOrderDto> list = new ArrayList<>();
        DirectChargingOrderDto directChargingOrderDto = null;

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            String head = xssfSheet.getRow(0).getCell(0).getStringCellValue();
            boolean isID = false;
            if (head != null) {
                if (head.trim().equals("orderId")) {
                    isID = true;
                }
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                System.out.println(getValue(xssfRow.getCell(0)));
                // 解析文档
                if (xssfRow != null && xssfRow.getLastCellNum() >= 2 && xssfRow.getCell(0) != null) {
                    directChargingOrderDto = resolveXlsx(xssfRow, isID ? 1 : 2);
                    if (directChargingOrderDto != null) {
                        list.add(directChargingOrderDto);
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
    private static DirectChargingOrderDto resolveXlsx(XSSFRow xssfRow, int type) throws ParseException {

        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();

        if (getValue(xssfRow.getCell(0)) == null || getValue(xssfRow.getCell(0)).equals("")) {
            return null;
        }

        if (type == 1) {
            directChargingOrderDto.setOrderId(getStringValue(xssfRow.getCell(0)));
            directChargingOrderDto.setState(getState(getStringValue(xssfRow.getCell(1))));
        } else {
            directChargingOrderDto.setRechargeAccount(getStringValue(xssfRow.getCell(0)));
            directChargingOrderDto.setState(getState(getStringValue(xssfRow.getCell(1))));
        }

        return directChargingOrderDto;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws IOException
     */
    public static List<DirectChargingOrderDto> readXls(InputStream is)
            throws IOException, ParseException {
        List<DirectChargingOrderDto> list = new ArrayList<>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            String head = hssfSheet.getRow(0).getCell(0).getStringCellValue();
            boolean isID = false;
            if (head != null) {
                if (head.trim().equals("orderId")) {
                    isID = true;
                }
            }
            // Read the Row
            HSSFRow hssfRow = null;
            DirectChargingOrderDto  obj = null;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                hssfRow = hssfSheet.getRow(rowNum);
                // 解析文档
                // System.out.println(rowNum);
                if (hssfRow != null && hssfRow.getLastCellNum() >= 7 && hssfRow.getCell(0) != null) {
                    obj = resolveXls(hssfRow, isID ? 1 : 2);
                    if (obj != null) {
                        list.add(obj);
                    }
                }
            }
        }
        hssfWorkbook.close();
        return list;
    }

    private static DirectChargingOrderDto resolveXls(HSSFRow hssfRow, int type) throws ParseException {
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();

        if (getValue(hssfRow.getCell(0)) == null || getValue(hssfRow.getCell(0)) == "") {
            return null;
        }

        if (type == 1) {
            directChargingOrderDto.setOrderId(getStringValue(hssfRow.getCell(0)));
            directChargingOrderDto.setState(getState(getStringValue(hssfRow.getCell(1))));
        } else {
            directChargingOrderDto.setRechargeAccount(getStringValue(hssfRow.getCell(0)));
            directChargingOrderDto.setState(getState(getStringValue(hssfRow.getCell(1))));
        }

        return directChargingOrderDto;
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
        hssfCell.setCellType(CellType.STRING);
        return hssfCell.getStringCellValue();
    }

    private static String getStringValue(XSSFCell xssfCell) {
        if (xssfCell == null)
            return null;
        xssfCell.setCellType(CellType.STRING);
        return xssfCell.getStringCellValue();
    }
    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell == null)
            return null;
//        hssfCell.setCellStyle();
//        hssfCell.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell.setCellType(CellType.STRING);
        return hssfCell.getStringCellValue();
    }

    private static String getValue(XSSFCell xssfCell) {
        if (xssfCell == null)
            return null;
//        xssfCell.setCellStyle(XSSFCell.CELL_TYPE_STRING);
//        xssfCell.setCellType(Cell.CELL_TYPE_STRING);
        xssfCell.setCellType(CellType.STRING);
        return xssfCell.getStringCellValue();
    }

    private static int getState(String val){
        if (val.equals("待充值")){
            return 1;
        }else if (val.equals("未支付")){
            return 0;
        }else if (val.equals("已充值")){
            return 2;
        }else if (val.equals("支付失败")){
            return 3;
        }else if (val.equals("充值失败")){
            return 4;
        }else if (val.equals("充值中")){
            return 5;
        }else if (val.equals("已退款")){
            return 6;
        }else if (val.equals("当日超次数")){
            return 7;
        }else if (val.equals("携号转网")){
            return 8;
        }else if (val.equals("排队中")){
            return 9;
        }else if (val.equals("待充值（快充）") || val.equals("待充值(快充)")){
            return 10;
        }else {
            return 1;
        }
    }

    private static int getType(String val) throws ParseException {
        if (val.equals("油卡")){
            return 2;
        }else if (val.equals("话费")){
            return 8;
        } else {
            throw new ParseException("不支持该类型", 0);
        }
    }
}
