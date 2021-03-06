package org.seeker.format;

import java.text.DecimalFormat;

public class NumFormat {
	public static String getNumberh(long val) {
		String pattern = "00000000000000000";
		// String pattern="00000000";
		DecimalFormat fmt = new DecimalFormat(pattern);
		val = System.currentTimeMillis();
		return fmt.format(val);
	}


	
	/**
	 * @param val 原字符
	 * @param len 格式长度
	 * @param c 补尾字符
	 * @return
	 */
	private static String alterAddChar(String val,int len,int c){
		int temp=len-val.length();
		return val+String.format("%0"+temp+"d%n", c);
	}
	
	/**
	 * 补0
	 * @param val
	 * @param len
	 * @return
	 */
	public static String alterAddZero(String val,int len){
		return alterAddChar(val, len, 0);
	}
	
	public static void main(String[] args) {
		System.out.printf("最牛的编号是：%3s%n", 7);
//		System.out.printf("最牛的编号是：%d%n", 7);
		String str = "0050580";
		System.out.println(str.replaceFirst("^0*", ""));
		
		System.out.println(alterAddZero("3212223", 15));
	}
}
