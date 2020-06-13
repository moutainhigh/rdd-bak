package com.cqut.czb.bn.service.impl.WxOilCodeSale;

import com.cqut.czb.bn.entity.dto.ImportWxStockDTO;
import com.cqut.czb.bn.util.string.StringUtil;
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
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.STRING;

public class ImportWxStock {
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
    public static List<ImportWxStockDTO> readExcel(String filename,
                                                   InputStream inputStream) throws IOException {
        List<ImportWxStockDTO> list = null;// 存入解析的DTO
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
    public static List<ImportWxStockDTO> readXlsx(InputStream is) throws IOException {
        List<ImportWxStockDTO> list = new ArrayList<ImportWxStockDTO>();
        ImportWxStockDTO importWxStockDTO = null;

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
                if (xssfRow != null && xssfRow.getLastCellNum() >= 3 && xssfRow.getCell(0) != null) {
                    importWxStockDTO = resolveXlsx(xssfRow);
                    if (importWxStockDTO != null) {
                        list.add(importWxStockDTO);
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
    private static ImportWxStockDTO resolveXlsx(XSSFRow xssfRow)  {
        if (xssfRow.getCell(0) == null || xssfRow.getCell(0).toString() == "") {
            return null;
        }
        ImportWxStockDTO importWxStockDTO = new ImportWxStockDTO();
//        importWxStockDTO.setRecordId(StringUtil.createId());
        XSSFCell numCell = xssfRow.getCell(0);
        if(numCell != null){
            numCell.setCellType(STRING);
        }
        importWxStockDTO.setCommodityNo(getStringValue(numCell));
        importWxStockDTO.setStockID(StringUtil.createId());
        importWxStockDTO.setAttribute(getStringValue(xssfRow.getCell(1)));
        importWxStockDTO.setContent(getStringValue(xssfRow.getCell(2)));
        return importWxStockDTO;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws IOException
     */
    public static List<ImportWxStockDTO> readXls(InputStream is)
            throws IOException{
        List<ImportWxStockDTO> list = new ArrayList<ImportWxStockDTO>();
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
            ImportWxStockDTO obj = null;
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                hssfRow = hssfSheet.getRow(rowNum);
                // 解析文档
                // System.out.println(rowNum);
                if (hssfRow != null && hssfRow.getLastCellNum() >= 3 && hssfRow.getCell(0) != null) {
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

    private static ImportWxStockDTO resolveXls(HSSFRow hssfRow) {
        ImportWxStockDTO importWxStockDTO = new ImportWxStockDTO();
        if (hssfRow.getCell(0) == null || hssfRow.getCell(0).toString() == "") {
            return null;
        }
//        importWxStockDTO.setRecordId(StringUtil.createId());
        HSSFCell numCell = hssfRow.getCell(0);
        if(numCell != null){
            numCell.setCellType(STRING);
        }
        importWxStockDTO.setCommodityNo(getStringValue(numCell));
        importWxStockDTO.setStockID(StringUtil.createId());
        importWxStockDTO.setAttribute(getStringValue(hssfRow.getCell(1)));
        importWxStockDTO.setContent(getStringValue(hssfRow.getCell(2)));
        return importWxStockDTO;
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
