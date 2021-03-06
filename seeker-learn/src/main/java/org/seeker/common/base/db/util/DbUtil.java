package org.seeker.common.base.db.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.seeker.common.base.db.DBConnection;
import org.seeker.common.base.db.DBConnectionHandler;
import org.seeker.common.base.db.entity.TableColumns;

public class DbUtil {

	public static String getDriverName() throws SQLException {
		String sql = "select * from dual";
		Connection con = DBConnectionHandler.getConnection();
		DatabaseMetaData dbMeta = con.getMetaData();
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		return dbMeta.getDriverName();
	}
	
	public static String getPrimaryKey(String intsert) throws SQLException {
		Connection con = DBConnectionHandler.getConnection();
		PreparedStatement ps=con.prepareStatement(intsert,Statement.RETURN_GENERATED_KEYS );
//		//此方法只针对添加语句 查询语句不可以
		ResultSet rs=ps.getGeneratedKeys();
		rs.next();
		return rs.getString(1);
	}

	public static Map<String, TableColumns> toResultSetMetaDataTableDesc(String tableName) throws Exception {
		Map<String, TableColumns> map = new HashMap<String, TableColumns>();
		TableColumns colPos = null;

		String sql = "select * from " + tableName;
		tableName = tableName.toUpperCase();
		Connection con = DBConnectionHandler.getConnection();
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		ResultSetMetaData rsm = ps.getMetaData();// 表结构
		int cols = rsm.getColumnCount();
		for (int i = 0; i < cols; i++) {
			colPos = new TableColumns();
			// String content = "ColumnName>>" + rsm.getColumnName(i + 1) + "\n"
			// + "ColumnName>>" + CodeUtil.wordCaseConvert(rsm.getColumnName(i +
			// 1), false) + "\n" + "ColumnLabel>>" + rsm.getColumnLabel(i + 1) +
			// "\n" + "ColumnDisplaySize>>" + rsm.getColumnDisplaySize(i + 1) +
			// "\n" + "ColumnTypeName>>" + rsm.getColumnTypeName(i + 1) + "\n" +
			// "isReadOnly>>" + rsm.isReadOnly(i + 1) + "\n" + "isNullable>>" +
			// rsm.isNullable(i + 1) + "\n" + "SchemaName>>" +
			// rsm.getSchemaName(i + 1) + "\n" + "Precision>>" +
			// rsm.getPrecision(i + 1) + "\n" + "Scale>>" + rsm.getScale(i + 1)
			// + "\n" + "Searchable>>" + rsm.isSearchable(i + 1) + "\n" + "";
			colPos.setColName(rsm.getColumnName(i + 1));// ColumnName
			colPos.setColLabel(rsm.getColumnLabel(i + 1));// ColumnLabel
			colPos.setColDisplaySize(String.valueOf(rsm.getColumnDisplaySize(i + 1)));// ColumnDisplaySize
			colPos.setTypeName(rsm.getColumnTypeName(i + 1));// ColumnTypeName

			colPos.setIsReadOnly(rsm.isReadOnly(i + 1));// isReadOnly
			colPos.setIsNull(rsm.isNullable(i + 1));// isNullable

			colPos.setSchemaName(rsm.getSchemaName(i + 1));
			colPos.setPrecision(rsm.getPrecision(i + 1));
			colPos.setScale(rsm.getScale(i + 1));
			colPos.setSearchable(rsm.isSearchable(i + 1));
			map.put(rsm.getColumnName(i + 1), colPos);
			// UtilTools.sys(content);
		}
		return map;
	}

	public static Map<String, TableColumns> toDatabaseMetaDataTableDesc(String tableName) throws Exception {
		Map<String, TableColumns> map = new HashMap<String, TableColumns>();
		TableColumns colPos = null;

		tableName = tableName.toUpperCase();
		Connection con = DBConnection.getConnection();
		DatabaseMetaData dbMeta = con.getMetaData();
		// 4. 提取表内的字段的名字和类型
		ResultSet colRet = dbMeta.getColumns(null, "%", tableName, "%");
		while (colRet.next()) {
			colPos = new TableColumns();
			String colName = colRet.getString("COLUMN_NAME");
			colPos.setColName(colName);
			colPos.setTypeName(colRet.getString("TYPE_NAME"));
			colPos.setColDisplaySize(colRet.getString("COLUMN_SIZE"));
			colPos.setIsNull(colRet.getInt("NULLABLE"));
			map.put(colName, colPos);
		}

		return map;
	}
	

}
