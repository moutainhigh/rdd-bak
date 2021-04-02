package com.cqut.czb.bn.service.impl.h5Stock;

import com.cqut.czb.bn.entity.dto.H5StockDTO;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class H5StockDelivery {

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
    public static List<H5StockDTO> readExcel(String filename,
                                                          InputStream inputStream) throws IOException {
        List<H5StockDTO> list = null;// 存入解析的DTO
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
    public static List<H5StockDTO> readXlsx(InputStream is) throws IOException {

        List<H5StockDTO> list = new ArrayList<H5StockDTO>();
        H5StockDTO h5StockDTO = null;

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
                if (xssfRow != null && xssfRow.getLastCellNum() >= 2 && xssfRow.getCell(0) != null) {
                    h5StockDTO = resolveXlsx(xssfRow);
                    if (h5StockDTO != null) {
                        list.add(h5StockDTO);
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
    private static H5StockDTO resolveXlsx(XSSFRow xssfRow)  {
        if (getValue(xssfRow.getCell(0)) == null || getValue(xssfRow.getCell(0)) == "" ||
            getValue(xssfRow.getCell(1)) == null || getValue(xssfRow.getCell(1)) == "" ||
            getValue(xssfRow.getCell(2)) == null || getValue(xssfRow.getCell(2)) == "") {
            return null;
        }

        H5StockDTO h5StockDTO = new H5StockDTO();

        h5StockDTO.setCommodityId(getStringValue(xssfRow.getCell(0)));
        h5StockDTO.setPrice(Double.parseDouble(getValue(xssfRow.getCell(1))));
        h5StockDTO.setContent(getStringValue(xssfRow.getCell(2)));
        return h5StockDTO;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws IOException
     */
    public static List<H5StockDTO> readXls(InputStream is)
            throws IOException{
        List<H5StockDTO> list = new ArrayList<H5StockDTO>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            HSSFRow hssfRow = null;
            H5StockDTO  obj = null;
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

    private static H5StockDTO resolveXls(HSSFRow hssfRow) {
        H5StockDTO h5StockDTO = new H5StockDTO();
        if (getValue(hssfRow.getCell(0)) == null || getValue(hssfRow.getCell(0)) == "" ||
            getValue(hssfRow.getCell(1)) == null || getValue(hssfRow.getCell(1)) == "" ||
            getValue(hssfRow.getCell(2)) == null || getValue(hssfRow.getCell(2)) == "") {
            return null;
        }
        h5StockDTO.setCommodityId(getStringValue(hssfRow.getCell(1)));
        h5StockDTO.setPrice(Double.parseDouble(getStringValue(hssfRow.getCell(2))));
        h5StockDTO.setContent(getStringValue(hssfRow.getCell(3)));

        return h5StockDTO;
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

}

