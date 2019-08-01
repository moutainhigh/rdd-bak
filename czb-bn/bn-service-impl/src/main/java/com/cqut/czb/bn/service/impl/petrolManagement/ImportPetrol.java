package com.cqut.czb.bn.service.impl.petrolManagement;

import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.util.string.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportPetrol {
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
    public static List<Petrol> readExcel(String filename,
                                         InputStream inputStream) throws IOException {
        List<Petrol> list = null;// 存入解析的DTO
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
    public static List<Petrol> readXlsx(InputStream is) throws IOException {
        List<Petrol> list = new ArrayList<Petrol>();
        Petrol petrol = null;

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
                    petrol = resolveXlsx(xssfRow);
                    if (petrol != null) {
                        list.add(petrol);
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
    private static Petrol resolveXlsx(XSSFRow xssfRow) {

        if (xssfRow.getCell(0) == null || xssfRow.getCell(0).toString() == "") {
            return null;
        }
        Petrol petrol = new Petrol();
        petrol.setPetrolId(StringUtil.createId());
        try{
            Thread.sleep(1L);
        }catch (Exception e){
            e.printStackTrace();
        }
        petrol.setPetrolNum(getStringValue(xssfRow.getCell(0)));
        if (xssfRow.getCell(1) == null || xssfRow.getCell(1).toString() == "") {
            petrol.setPetrolPsw(null);
        } else {
            petrol.setPetrolPsw(getStringValue(xssfRow.getCell(1)));
        }
        petrol.setPetrolDenomination(Double.parseDouble(getStringValue(xssfRow.getCell(2))));
        petrol.setPetrolPrice(Double.parseDouble(getStringValue(xssfRow.getCell(3))));
        petrol.setPetrolKind(Integer.parseInt(getStringValue(xssfRow.getCell(4))));
        petrol.setArea(getStringValue(xssfRow.getCell(5)));
        petrol.setPetrolType(Integer.parseInt(getStringValue(xssfRow.getCell(6))));
        petrol.setDiscount(Double.parseDouble(getStringValue(xssfRow.getCell(7))));
        petrol.setState(0);
        return petrol;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws IOException
     */
    public static List<Petrol> readXls(InputStream is)
            throws IOException {
        List<Petrol> list = new ArrayList<Petrol>();
        Petrol petrol = null;

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            HSSFRow hssfRow = null;
            Petrol obj = null;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                hssfRow = hssfSheet.getRow(rowNum);
                // 解析文档
                // System.out.println(rowNum);
                if (hssfRow != null && hssfRow.getLastCellNum() >= 7 && hssfRow.getCell(0) != null) {
                    obj = resolveXls(hssfRow);
                    if (obj != null) {
                        list.add(obj);
                    }
                }
            }
        }
        hssfWorkbook.close();
        return list;
    }

    private static Petrol resolveXls(HSSFRow hssfRow) {

        Petrol petrol = new Petrol();
        if (hssfRow.getCell(0) == null || hssfRow.getCell(0).toString() == "") {
            return null;
        }
        petrol.setPetrolId(StringUtil.createId());
        try{
            Thread.sleep(1L);
        }catch (Exception e){
            e.printStackTrace();
        }
        petrol.setPetrolNum(getStringValue(hssfRow.getCell(0)));
        if (hssfRow.getCell(1) == null || hssfRow.getCell(1).toString() == "") {
            petrol.setPetrolPsw(null);
        } else {
            petrol.setPetrolPsw(getStringValue(hssfRow.getCell(1)));
        }
        petrol.setPetrolDenomination(Double.parseDouble(getStringValue(hssfRow.getCell(2))));
        petrol.setPetrolPrice(Double.parseDouble(getStringValue(hssfRow.getCell(3))));
        petrol.setPetrolKind(Integer.parseInt(getStringValue(hssfRow.getCell(4))));
        petrol.setArea(getStringValue(hssfRow.getCell(5)));
        petrol.setPetrolType(Integer.parseInt(getStringValue(hssfRow.getCell(6))));
        petrol.setDiscount(Double.parseDouble(getStringValue(hssfRow.getCell(7))));
        petrol.setState(0);
        return petrol;
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

    private static String getStringValue(HSSFCell hssfCell){
        if (hssfCell == null)
            return null;
//        hssfCell.setCellStyle();
        hssfCell.setCellType(Cell.CELL_TYPE_STRING);
        return hssfCell.getStringCellValue();
    }
    private static String getStringValue(XSSFCell xssfCell){
        if (xssfCell == null)
            return null;
//        xssfCell.setCellStyle(XSSFCell.CELL_TYPE_STRING);
        xssfCell.setCellType(Cell.CELL_TYPE_STRING);
        return xssfCell.getStringCellValue();
    }

}
