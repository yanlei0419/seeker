package org.seeker.common.base.db.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.seeker.common.util.BaseUtils;

/**
 * 反射机制组装实体bean
 * @author yanlei
 *
 */
public class ResultToEnitiy {
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 将数据库查询的结果转化成实体bean
	 * 
	 * @param rs
	 *            查询出来的结果集
	 * @param clazz
	 *            实体bean
	 * @return
	 */
	public static Object toEntity(ResultSet rs, Class clazz) {
		// List<Object> list=new ArrayList<Object>();
		try {
			// 循环结果集
			// while (rs.next()) {
			// 实例化一个实体
			Object obj = clazz.newInstance();
			// 获取该类的全部属性
			Field[] fields = clazz.getDeclaredFields();
			// Map<String, String> map = new HashMap<String, String>();
			// ResultSetMetaData rsmd = rs.getMetaData();
			// int count = rsmd.getColumnCount();
			// for (int i = 1; i <= count; i++) {
			// String colName = rsmd.getColumnName(i).toLowerCase();
			// map.put(colName,
			// rs.getString(colName) == null ? "" : rs
			// .getString(colName));
			// }
			// }
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName(); // 获取节点的名字
				String type = fields[i].getGenericType().toString();// 获取字段的类别
				// 字符串类型
				if ("class java.lang.String".equals(type)) {
					String value = rs.getString(name);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// System.out.println(value);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}
				// 数字整形
				if ("class java.lang.Byte".equals(type) || "byte".equals(type)) {
					byte value = rs.getByte(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}

				if ("class java.lang.Integer".equals(type) || "int".equals(type)) {
					int value = rs.getInt(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					// Method m =clazz.getMethod("set"+name,Integer.class);
					m.invoke(obj, value);
				}

				if ("class java.lang.Short".equals(type)) {
					short value = rs.getShort(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}

				if ("class java.lang.Long".equals(type)) {
					long value = rs.getLong(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}
				// 浮点型
				if ("class java.lang.Float".equals(type)) {
					float value = rs.getFloat(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}

				if ("class java.lang.Double".equals(type)) {
					double value = rs.getDouble(name);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}
				// 布尔型
				if ("class java.lang.Boolean".equals(type)) {
					boolean value = rs.getBoolean(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}
				// 时间类型
				if ("class java.sql.Date".equals(type)) {
					Date value = rs.getDate(name);
					// System.out.println(value);
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 将属性的首字符大写，方便构造get，set方法
					Method m = clazz.getMethod("set" + name, fields[i].getType());
					m.invoke(obj, value);
				}
				// TODO 目前使用这些数据类型作为实体类的, 以后在用再添加 ...
			}
			// 将整理好的实体放入集合传出去
			// list.add(obj);
			// }
			return obj;
		} catch (InstantiationException e) {
			BaseUtils.print("创建实体出问题");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			BaseUtils.print("创建实体出问题");
			e.printStackTrace();
		} catch (SQLException e) {
			BaseUtils.print("访问结果集字段出问题");
			e.printStackTrace();
		} catch (SecurityException e) {
			BaseUtils.print("获取方法出问题");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			BaseUtils.print("获取方法出问题");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			BaseUtils.print("执行方法出问题");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			BaseUtils.print("执行方法出问题");
			e.printStackTrace();
		}
		return null;
	}

	public static List toListEntity(ResultSet rs, Class clazz) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int cols = meta.getColumnCount();
		List<Object> list = new ArrayList<Object>(cols);
		try {
			while (rs.next()) {
				Object obj = toEntity(rs, clazz);
				list.add(obj);
			}
		} catch (SQLException e) {
			BaseUtils.print("访问结果集字段出问题");
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 判断是什么类型的属性 8种数据类型
	 * 
	 * @param value
	 *            数据库的值
	 * @param type
	 *            累的类别
	 * @return
	 */
	private boolean isCompatibleType(Object value, Class<?> type) {
		if ((value == null) || (type.isInstance(value))) {
			return true;
		}
		if ((type.equals(Integer.TYPE)) && (Integer.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Long.TYPE)) && (Long.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Double.TYPE)) && (Double.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Float.TYPE)) && (Float.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Short.TYPE)) && (Short.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Byte.TYPE)) && (Byte.class.isInstance(value))) {
			return true;
		}
		if ((type.equals(Character.TYPE)) && (Character.class.isInstance(value))) {
			return true;
		}
		return (type.equals(Boolean.TYPE)) && (Boolean.class.isInstance(value));
	}

	/**
	 * 将数据库查询的结果转化成实体bean
	 * 
	 * @param rs
	 *            查询出来的结果集
	 * @param clazz
	 *            实体bean
	 * @return
	 * @throws SQLException 
	 * @throws IntrospectionException 
	 */
	public static <T> T createEntity(ResultSet rs, Class<T> type) throws SQLException, IntrospectionException {
		Object entity = newInstance(type);
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < props.length; i++) {
			if (rs.next()) {
			}

		}

		return null;
	}

	/**
	 * 
	 * @param target
	 *            目标类
	 * @param prop
	 *            属性方法
	 * @param value
	 *            数据库的值
	 * @throws SQLException
	 */
	private void callSetter(Object target, PropertyDescriptor prop, Object value) throws SQLException {
		Method setter = prop.getWriteMethod();
		if (setter == null) {
			return;
		}

		Class[] params = setter.getParameterTypes();
		try {
			if ((value instanceof java.util.Date)) {
				String targetType = params[0].getName();
				if ("java.sql.Date".equals(targetType)) {
					value = new java.sql.Date(((java.util.Date) value).getTime());
				} else if ("java.sql.Time".equals(targetType)) {
					value = new Time(((java.util.Date) value).getTime());
				} else if ("java.sql.Timestamp".equals(targetType)) {
					value = new Timestamp(((java.util.Date) value).getTime());
				}

			}

			if (isCompatibleType(value, params[0]))
				setter.invoke(target, new Object[] { value });
			else {
				throw new SQLException("Cannot set " + prop.getName() + ": incompatible types, cannot convert " + value.getClass().getName() + " to " + params[0].getName());
			}

		} catch (IllegalArgumentException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 实例化传入的类
	 * 
	 * @param <T>
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	protected static <T> T newInstance(Class<T> c) throws SQLException {
		try {
			return c.newInstance();
		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());
		}
	}

}