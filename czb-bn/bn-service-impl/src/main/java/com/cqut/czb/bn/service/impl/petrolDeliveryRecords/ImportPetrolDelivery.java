package com.cqut.czb.bn.service.impl.petrolDeliveryRecords;

import com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.PetrolDeliveryDTO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ImportPetrolDelivery {
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
    public static List<PetrolDeliveryDTO> readExcel(String filename,
                                                    InputStream inputStream) throws IOException {
        List<PetrolDeliveryDTO> list = null;// 存入解析的DTO
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
    public static List<PetrolDeliveryDTO> readXlsx(InputStream is) throws IOException {
        List<PetrolDeliveryDTO> list = new ArrayList<PetrolDeliveryDTO>();
        PetrolDeliveryDTO petrolDeliveryDTO = null;

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
                    petrolDeliveryDTO = resolveXlsx(xssfRow);
                    if (petrolDeliveryDTO != null) {
                        list.add(petrolDeliveryDTO);
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
    private static PetrolDeliveryDTO resolveXlsx(XSSFRow xssfRow)  {

        System.out.println(xssfRow.toString());
        Iterator iterator =  xssfRow.cellIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());

        }
        if (xssfRow.getCell(0) == null || xssfRow.getCell(0).toString() == "") {
            return null;
        }
        PetrolDeliveryDTO petrolDeliveryDTO = new PetrolDeliveryDTO();
        petrolDeliveryDTO.setRecordId(StringUtil.createId());
        XSSFCell cell = xssfRow.getCell(5);
        petrolDeliveryDTO.setPetrolNum(getStringValue(xssfRow.getCell(0)));
//        if (xssfRow.getCell(1) == null || xssfRow.getCell(1).toString() == "") {
//            petrolDeliveryDTO.setPetrolKind(null);
//        } else {
//            petrolDeliveryDTO.setPetrolKind(Integer.parseInt(getStringValue(xssfRow.getCell(1))));
//        }
        if ("未寄送".equals(getStringValue(xssfRow.getCell(2)))) {
            petrolDeliveryDTO.setDeliveryState(1);
        }
        petrolDeliveryDTO.setReceiver((getStringValue(xssfRow.getCell(3))));
//        petrolDeliveryDTO.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getStringValue(xssfRow.getCell(4))));
        petrolDeliveryDTO.setContactNumber(getStringValue(xssfRow.getCell(5)));
        petrolDeliveryDTO.setProvince((getStringValue(xssfRow.getCell(6))));
        petrolDeliveryDTO.setDetail((getStringValue(xssfRow.getCell(7))));
        petrolDeliveryDTO.setDeliveryNum(getStringValue(xssfRow.getCell(8)));
        petrolDeliveryDTO.setDeliveryCompany(getStringValue(xssfRow.getCell(9)));
        return petrolDeliveryDTO;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws IOException
     */
    public static List<PetrolDeliveryDTO> readXls(InputStream is)
            throws IOException{
        List<PetrolDeliveryDTO> list = new ArrayList<PetrolDeliveryDTO>();
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
            PetrolDeliveryDTO obj = null;
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

    private static PetrolDeliveryDTO resolveXls(HSSFRow hssfRow) {

        PetrolDeliveryDTO petrolDeliveryDTO = new PetrolDeliveryDTO();
        if (hssfRow.getCell(0) == null || hssfRow.getCell(0).toString() == "") {
            return null;
        }
        petrolDeliveryDTO.setRecordId(StringUtil.createId());
        petrolDeliveryDTO.setPetrolNum(getStringValue(hssfRow.getCell(0)));
//        if (hssfRow.getCell(1) == null || hssfRow.getCell(1).toString() == "") {
//            petrolDeliveryDTO.setPetrolKind(null);
//        } else {
//            petrolDeliveryDTO.setPetrolKind(Integer.parseInt(getStringValue(hssfRow.getCell(1))));
//        }
        petrolDeliveryDTO.setReceiver(getStringValue(hssfRow.getCell(3)));
        if (getStringValue(hssfRow.getCell(2)).equals("未寄送")) {
            petrolDeliveryDTO.setDeliveryState(1);
        }
//        petrolDeliveryDTO.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getStringValue(hssfRow.getCell(4))));
        petrolDeliveryDTO.setContactNumber(getStringValue(hssfRow.getCell(5)));
        petrolDeliveryDTO.setProvince((getStringValue(hssfRow.getCell(6))));
        petrolDeliveryDTO.setDetail(getStringValue(hssfRow.getCell(7)));
        petrolDeliveryDTO.setDeliveryNum(getStringValue(hssfRow.getCell(8)));
        petrolDeliveryDTO.setDeliveryCompany(getStringValue(hssfRow.getCell(9)));
        return petrolDeliveryDTO;
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
        hssfCell.setCellType(Cell.CELL_TYPE_STRING);
        return hssfCell.getStringCellValue();
    }

    private static String getStringValue(XSSFCell xssfCell) {
        if (xssfCell == null)
            return null;
//        xssfCell.setCellStyle(XSSFCell.CELL_TYPE_STRING);
        xssfCell.setCellType(Cell.CELL_TYPE_STRING);
        return xssfCell.getStringCellValue();
    }
}
