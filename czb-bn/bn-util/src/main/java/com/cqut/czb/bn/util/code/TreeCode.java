package com.cqut.czb.bn.util.code;

import java.text.DecimalFormat;

/**
 * 树形code生成器，用于resource模块和organization模块
 * 作者：谭勇
 * 时间：2018-04-06
 * */
public class TreeCode {

    /**
     * PATTERN
     * */
    private static final String PATTERN = "00000000";

    /**
     * 初始化code长度
     * */
    public static final int INIT_LENGTH;

    /**
     * 初始化code值
     * */
    private static final String INIT_CODE;

    private static final DecimalFormat FORMAT = new DecimalFormat(PATTERN);

    static {
        INIT_LENGTH = PATTERN.length();
        INIT_CODE = nextCode(PATTERN);
    }

    /**
     * @param parentCode 父级code值
     * @param code 父节点下最大的code值
     * */
    public static String getTreeCode(String parentCode, String code) {
        if (parentCode == null && code == null) {
            return INIT_CODE;
        } else if (code == null) {
            return parentCode + INIT_CODE;
        } else {
            return nextCode(code);
        }
    }

    /**
     * 获取比该code值大1的code
     * */
    private static String nextCode(String code) {
        int lastCodeValue = Integer.valueOf(code.substring(code.length() - INIT_LENGTH, code.length()));
        return code.substring(0, code.length() - INIT_LENGTH) + FORMAT.format(++lastCodeValue);
    }
}
