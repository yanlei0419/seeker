package org.seeker.common.excel;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.seeker.common.excel.util.ExcelUtil;
import org.seeker.common.util.BaseUtils;
import org.seeker.common.util.BeanFactoryUtil;

public class ImportExcel {
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 导入 excel
	 * 
	 * @param inputstream
	 *            : 文件输入流
	 * @param pojoClass
	 *            : 对应的导入对象 (每行记录)
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	public static List importExcelTool(InputStream inputstream, Class pojoClass) throws Exception {
//		HSSFWorkbook book = new HSSFWorkbook(inputstream);
//		return toListEntity(book, pojoClass);
//	}

	/**
	 * 导入 excel
	 * 
	 * @param inputstream
	 *            : 文件输入流
	 * @param pojoClass
	 *            : 对应的导入对象 (每行记录)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> importExcelByIs(InputStream inputstream, Class pojoClass) {
		List<Object> dist = new ArrayList<Object>();
		try {
			Map map= ExcelUtil.getMapByClazz(pojoClass);
//			Map<String, Method> fieldSetMap = map.get("fieldSetMap");//获得excelPo里面添加属性值的方法
//			Map<String, Method> fieldSetConvertMap = map.get("fieldSetConvertMap");//是否需要转化 应该没有用
			
			// // 得到工作表
			HSSFWorkbook book = new HSSFWorkbook(inputstream);
			// // 得到第一页
			HSSFSheet sheet = book.getSheetAt(0);
			// // 得到第一面的所有行
			Iterator<Row> rows = sheet.rowIterator();
			
			// 得到第一行，也就是标题行
			Map<Integer,String> titlemap = ExcelUtil.getTitleByRow(rows.next());
			map.put("titlemap", titlemap);
			while (rows.hasNext()) {
				Row rown = rows.next();
				// 得到传入类的实例
				Object tObject = pojoClass.newInstance();
				ExcelUtil.getEntityByRow(tObject, map, rown);
				dist.add(tObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}

	/**
	 * 导入 excel
	 * 
	 * @param file
	 *            : Excel文件
	 * @param pojoClass
	 *            : 对应的导入对象 (每行记录)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List importExcel(File file, Class pojoClass) {
		try {
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			return importExcelByIs(in, pojoClass);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转换为Date类型数据（限定格式 YYYY-MM-DD hh:mm:ss）或（YYYY/MM/DD hh:mm:ss）
	 * 
	 * @param cellValue
	 *            : 字符串类型的日期数据
	 * @return
	 */
	private static Date stringToDate(String cellValue) {
		if (cellValue.length() > 19)
			cellValue = cellValue.substring(0, 19);
		Calendar calendar = Calendar.getInstance();
		String[] dateStr = cellValue.split(" ");
		String[] dateInfo = dateStr[0].split("-");
		if (dateInfo.length != 3) {
			dateInfo = dateStr[0].split("/"); // 让 yyyy/mm/dd 的格式也支持
		}
		if (dateInfo.length == 3) {
			int year = Integer.parseInt(dateInfo[0]);
			int month = Integer.parseInt(dateInfo[1]) - 1; // 0~11
			int day = Integer.parseInt(dateInfo[2]);
			calendar.set(year, month, day);
		} else {
			return null; // 格式不正确
		}
		if (dateStr.length > 1) {// 有时间（限定格式 hh:mm:ss）
			String[] timeStr = dateStr[1].split(":");
			if (timeStr.length == 3) {
				int hour = Integer.parseInt(timeStr[0]);
				int minute = Integer.parseInt(timeStr[1]);
				int second = Integer.parseInt(timeStr[2]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, second);
			} else {
				return null; // 格式不正确
			}
		}
		return calendar.getTime();
	}
	
//	public static <T> List<T> toListEntity(HSSFWorkbook book, Class<T> type) throws Exception {
//		List results = new ArrayList();
//		HSSFSheet sheet = book.getSheetAt(0);
//		// 得到第一面的所有行
//		Iterator<Row> row = sheet.rowIterator();
//
//		// 得到第一行，也就是标题行
//		Row title = row.next();
//		if (title == null) {
//			return results;
//		}
//		// 得到第一行的所有列
//		Iterator<Cell> cellTitle = title.cellIterator();
//		// 将标题的文字内容放入到一个map中。
//		Map<Integer,String> titlemap = new HashMap<Integer, String>();
//		// 从标题第一列开始
//		int i = 0;
//		// 循环标题所有的列
//		while (cellTitle.hasNext()) {
//			Cell cell = cellTitle.next();
//			String value = cell.getStringCellValue();
//			titlemap.put(i,value);
//			i++;
//		}
//		
//		
//		//VE 第二行开始
//		
//		while (row.hasNext()) {
//			Object tObject=type.newInstance();
//			Map<String, Method> fieldSetConvertMap=toExcelBeanWriteMethodMap(tObject);
//			// 标题下的第一行
//			Row rown = row.next();
//			// 行的所有列
//			Iterator<Cell> cellbody = rown.cellIterator();
//			// 得到传入类的实例
//			int k = 0;
//			// 遍历一行的列
//			while (cellbody.hasNext()) {
//				Cell cell = cellbody.next();
//				// 这里得到此列的对应的标题
//				String titleString = (String) titlemap.get(k);
//				Object value=cell.getStringCellValue();
////				Method m=map.get(titleString);
//				if (Cell.CELL_TYPE_STRING == cell.getCellType() && map.containsKey(titleString)) {
//					// 目前只支持从String转换
//					fieldSetConvertMap.get(titleString).invoke(tObject, cell.getStringCellValue());
//				} else {
//					if (xclass.equals("class java.lang.String")) {
//						String cellValue = "";
//						// 如果第一列是數字類型，則轉為整型
//						// if (Cell.CELL_TYPE_NUMERIC == cell
//						// .getCellType()
//						// && k == 0) {
//						// DecimalFormat df = new DecimalFormat("0");
//						// cellValue = df.format(cell
//						// .getNumericCellValue());
//						// } else {
//						cell.setCellType(Cell.CELL_TYPE_STRING);
//						cellValue = cell.getStringCellValue();
//						// }
//						// 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
//						// cell.setCellType(Cell.CELL_TYPE_STRING);
//						setMethod.invoke(tObject, cellValue);
//					} else if (xclass.equals("class java.util.Date")) {
//						// update-start--Author:Quainty Date:20130523
//						// for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
//						Date cellDate = null;
//						if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//							// 日期格式
//							cellDate = cell.getDateCellValue();
//						} else { // 全认为是 Cell.CELL_TYPE_STRING: 如果不是
//							// yyyy-mm-dd hh:mm:ss 的格式就不对(wait to
//							// do:有局限性)
//							cellDate = stringToDate(cell.getStringCellValue());
//						}
//						setMethod.invoke(tObject, cellDate);
//					} else if (xclass.equals("class java.lang.Boolean")) {
//						boolean valBool;
//						if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
//							valBool = cell.getBooleanCellValue();
//						} else {// 全认为是 Cell.CELL_TYPE_STRING
//							valBool = cell.getStringCellValue().equalsIgnoreCase("true") || (!cell.getStringCellValue().equals("0"));
//						}
//						setMethod.invoke(tObject, valBool);
//					} else if (xclass.equals("class java.lang.Integer")) {
//						Integer valInt;
//						if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//							valInt = (new Double(cell.getNumericCellValue())).intValue();
//						}
//						// lijin======================
//						else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
//							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//							valInt = (new Double(cell.getNumericCellValue())).intValue();
//						} else {// 全认为是 Cell.CELL_TYPE_STRING
//							valInt = new Integer(cell.getStringCellValue());
//						}
//						setMethod.invoke(tObject, valInt);
//					} else if (xclass.equals("class java.lang.Long")) {
//						Long valLong;
//						if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//							valLong = (new Double(cell.getNumericCellValue())).longValue();
//						} else {// 全认为是 Cell.CELL_TYPE_STRING
//							valLong = new Long(cell.getStringCellValue());
//						}
//						setMethod.invoke(tObject, valLong);
//					} else if (xclass.equals("class java.lang.Double")) {
//						Double valDouble;
//						if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//							valDouble = (new Double(cell.getNumericCellValue())).doubleValue();
//						} else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
//							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//							valDouble = (new Double(cell.getNumericCellValue())).doubleValue();
//						} else {// 全认为是 Cell.CELL_TYPE_STRING
//							valDouble = new Double(cell.getStringCellValue());
//						}
//						setMethod.invoke(tObject, valDouble);
//					}
//
//					else if (xclass.equals("class java.math.BigDecimal")) {
//						BigDecimal valDecimal;
//						if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//							valDecimal = new BigDecimal(cell.getNumericCellValue());
//						} else {// 全认为是 Cell.CELL_TYPE_STRING
//							valDecimal = new BigDecimal(cell.getStringCellValue());
//						}
//						setMethod.invoke(tObject, valDecimal);
//						// //
//						// ----------------------------------------------------------------
//						// // update-begin--Author:sky Date:20130422
//						// for：取值类型调整cell.getNumberCellValue-->>getStringCellValue
//						// setMethod.invoke(tObject, new
//						// BigDecimal(cell.getStringCellValue()));
//						// // update-end--Author:sky Date:20130422
//						// for：取值类型调整
//						// //
//						// ----------------------------------------------------------------
//						// update-end--Author:Quainty Date:20130523
//						// for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
//				}
//			}
//				
//				
//			
//				BeanFactoryUtil.callSetter(bean, m, value);
//				k++;
//			}
//			results.add(bean);
//		}
//
//		return results;
//	}
	
	/**
	 * 通过传入的类获取该属性的set方法和注解的名称
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Method> toExcelBeanWriteMethodMap(Class type) throws Exception {
		Map<String, Method> beanMap = new HashMap<String, Method>();
		String fieldName = "";
		PropertyDescriptor[] props = BeanFactoryUtil.propertyDescriptors(type);
		Excel excel=null;
		for (int i = 0; i < props.length; i++) {
			Method m = props[i].getWriteMethod();
			fieldName = props[i].getName();// 属性名称
			System.out.println(fieldName);
			if (!"".equals(fieldName)) {
				try{
					excel=type.getDeclaredField(fieldName).getAnnotation(Excel.class);
				}catch(NoSuchFieldException e){
					BaseUtils.print(fieldName);
					continue;
				}
				if(excel!=null){
					String excelName=excel.exportName();
					beanMap.put(excelName, m);
				}
			}
		}
		return beanMap;
	}
	/**
	 * 通过传入的类获取该属性的set方法和注解的名称
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Method> toExcelBeanWriteMethodMap(Object obj) throws Exception {
		Class type=obj.getClass();
		return toExcelBeanWriteMethodMap(type);
	}
	
}
