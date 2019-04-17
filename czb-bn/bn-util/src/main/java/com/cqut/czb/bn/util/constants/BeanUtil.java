package com.cqut.czb.bn.util.constants;

import java.text.DecimalFormat;

public class BeanUtil {
	private static final DecimalFormat treeCodeFormat = new DecimalFormat("00000000");
	
	/**
	 * 初始code
	 * */
	public static final String INIT_TREE_CODE = "00000001";
	
	/**
	 * 树形code初始默认长度
	 * */
	public static final int INIT_CODE_LEN = 8;
	
	/**
	 * 根据当前code生成比他稍大的code值，如果为null直接生成初始code
	 * */
	public static String getTreeCode(String curCode){

		return getTreeCode(curCode, INIT_CODE_LEN);
	}
	
	public static String getTreeCode(String curCode, int len) {
		if(null == curCode)
			return INIT_TREE_CODE;
		int code = Integer.parseInt(curCode.substring(curCode.length() - len, curCode.length()));
		code++;
		return curCode.substring(0,curCode.length() - len)+treeCodeFormat.format(code);
	}
	
	
	/**
	 * 根据父级code生成该code值下的第一个子级code
	 * */
	public static String getTreeCodeForInit(String parentCode) {
		return parentCode == null ? INIT_TREE_CODE : parentCode.concat(INIT_TREE_CODE);
	}
}
