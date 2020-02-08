package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class weChatOrderDelivery {

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
    public static List<WeChatCommodityOrderDTO> readExcel(String filename,
                                                          InputStream inputStream) throws IOException {
        List<WeChatCommodityOrderDTO> list = null;// 存入解析的DTO
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
    public static List<WeChatCommodityOrderDTO> readXlsx(InputStream is) throws IOException {

        List<WeChatCommodityOrderDTO> list = new ArrayList<WeChatCommodityOrderDTO>();
        WeChatCommodityOrderDTO weChatDeliveryDTO = null;

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
                    weChatDeliveryDTO = resolveXlsx(xssfRow);
                    if (weChatDeliveryDTO != null) {
                        list.add(weChatDeliveryDTO);
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
    private static WeChatCommodityOrderDTO resolveXlsx(XSSFRow xssfRow)  {

        if (xssfRow.getCell(0) == null || xssfRow.getCell(0).toString() == "") {
            return null;
        }

        WeChatCommodityOrderDTO weChatDeliveryDTO = new WeChatCommodityOrderDTO();

        weChatDeliveryDTO.setUserName(getStringValue(xssfRow.getCell(0)));
        weChatDeliveryDTO.setUserAccount(getStringValue(xssfRow.getCell(1)));
        weChatDeliveryDTO.setOrderId(getStringValue(xssfRow.getCell(2)));
        weChatDeliveryDTO.setCommodityTitle(getStringValue(xssfRow.getCell(3)));
        weChatDeliveryDTO.setCommodityNum(Integer.parseInt(getStringValue(xssfRow.getCell(4))));
        weChatDeliveryDTO.setActualPrice(Double.valueOf(getStringValue(xssfRow.getCell(5))));
        if ("未支付".equals(getStringValue(xssfRow.getCell(6)))) {
            weChatDeliveryDTO.setPayStatus(0);
        }else if ("已支付".equals(getStringValue(xssfRow.getCell(6)))) {
            weChatDeliveryDTO.setPayStatus(1);
        }else {
            weChatDeliveryDTO.setPayStatus(-1);
        }
        try {
            weChatDeliveryDTO.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getStringValue(xssfRow.getCell(7))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        weChatDeliveryDTO.setDeliveryCompany(getStringValue(xssfRow.getCell(8)));
        weChatDeliveryDTO.setDeliveryNum(getStringValue(xssfRow.getCell(9)));
        if ("未邮寄".equals(getStringValue(xssfRow.getCell(10)))) {
            weChatDeliveryDTO.setDeliveryState(0);
        }else if ("寄送中".equals(getStringValue(xssfRow.getCell(10)))) {
            weChatDeliveryDTO.setDeliveryState(1);
        }else if ("已收货".equals(getStringValue(xssfRow.getCell(10)))) {
            weChatDeliveryDTO.setDeliveryState(2);
        }else {
            weChatDeliveryDTO.setDeliveryState(-1);
        }
        return weChatDeliveryDTO;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @return
     * @throws IOException
     */
    public static List<WeChatCommodityOrderDTO> readXls(InputStream is)
            throws IOException{
        List<WeChatCommodityOrderDTO> list = new ArrayList<WeChatCommodityOrderDTO>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            HSSFRow hssfRow = null;
            WeChatCommodityOrderDTO  obj = null;
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

    private static WeChatCommodityOrderDTO resolveXls(HSSFRow hssfRow) {
        WeChatCommodityOrderDTO weChatDeliveryDTO = new WeChatCommodityOrderDTO();
        if (hssfRow.getCell(0) == null || hssfRow.getCell(0).toString() == "") {
            return null;
        }
        weChatDeliveryDTO.setUserName(getStringValue(hssfRow.getCell(0)));
        weChatDeliveryDTO.setUserAccount(getStringValue(hssfRow.getCell(1)));
        weChatDeliveryDTO.setOrderId(getStringValue(hssfRow.getCell(2)));
        weChatDeliveryDTO.setCommodityTitle(getStringValue(hssfRow.getCell(3)));
        weChatDeliveryDTO.setCommodityNum(Integer.valueOf(getStringValue(hssfRow.getCell(4))));
        weChatDeliveryDTO.setActualPrice(Double.valueOf(getStringValue(hssfRow.getCell(5))));
        if ("未支付".equals(getStringValue(hssfRow.getCell(6)))) {
            weChatDeliveryDTO.setPayStatus(0);
        }else if ("已支付".equals(getStringValue(hssfRow.getCell(6)))) {
            weChatDeliveryDTO.setPayStatus(1);
        }else {
            weChatDeliveryDTO.setPayStatus(-1);
        }
        try {
            weChatDeliveryDTO.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getStringValue(hssfRow.getCell(7))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        weChatDeliveryDTO.setDeliveryCompany(getStringValue(hssfRow.getCell(8)));
        weChatDeliveryDTO.setDeliveryNum(getStringValue(hssfRow.getCell(9)));
        if ("未邮寄".equals(getStringValue(hssfRow.getCell(10)))) {
            weChatDeliveryDTO.setDeliveryState(0);
        }else if ("寄送中".equals(getStringValue(hssfRow.getCell(10)))) {
            weChatDeliveryDTO.setDeliveryState(1);
        }else if ("已收货".equals(getStringValue(hssfRow.getCell(10)))) {
            weChatDeliveryDTO.setDeliveryState(2);
        }else {
            weChatDeliveryDTO.setDeliveryState(-1);
        }
        return weChatDeliveryDTO;
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
        hssfCell.setCellType(CellType.STRING);
        return hssfCell.getStringCellValue();
    }

    private static String getStringValue(XSSFCell xssfCell) {
        if (xssfCell == null)
            return null;
//        xssfCell.setCellStyle(XSSFCell.CELL_TYPE_STRING);
//        xssfCell.setCellType(Cell.CELL_TYPE_STRING);
        xssfCell.setCellType(CellType.STRING);
        return xssfCell.getStringCellValue();
    }

}
