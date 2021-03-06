package org.seeker.common.base.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;
import org.seeker.common.base.db.util.PageSqlUtil;
import org.seeker.common.base.entity.Page;
import org.seeker.common.util.config.SysConstant;

public class BaseDao {
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Connection conn = null;

	protected List executeQuery(String strSql, Object[] param, ResultSetHandler rsh) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();// tlw 2013.6.4
		QueryRunner runner = BeanQueryRunner.getRuner();
		return (List) runner.query(this.conn, strSql, rsh, param);
	}

	protected int executeCount(String strSql, Object[] param) throws SQLException {
		int num = 0;
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = new QueryRunner();

		num = ((Integer) runner.query(this.conn, strSql, new ResultSetHandler() {
			public Integer handle(ResultSet rs) throws SQLException {
				rs.next();
				return Integer.valueOf(rs.getInt(1));
			}
		}, param)).intValue();

		return num;
	}

	/**
	 * 查询List
	 * @param strSql
	 * @param rsh
	 * @return
	 * @throws SQLException
	 */
	protected List executeQuery(String strSql, ResultSetHandler rsh) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		return (List) runner.query(this.conn, strSql, rsh);
	}

	/**
	 * 查询数据List的总个数
	 * @param strSql
	 * @return
	 * @throws SQLException
	 */
	protected int executeCount(String strSql) throws SQLException {
		int num = 0;
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		num = ((Integer) runner.query(this.conn, strSql, new ResultSetHandler() {
			public Integer handle(ResultSet rs) throws SQLException {
				rs.next();
				return Integer.valueOf(rs.getInt(1));
			}
		})).intValue();

		return num;
	}

	/**
	 * 查询单个bean
	 * @param strSql
	 * @param param
	 * @param rsh
	 * @return
	 * @throws SQLException
	 */
	protected Object executeQueryObject(String strSql, Object[] param, ResultSetHandler rsh) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		return runner.query(this.conn, strSql, rsh, param);
	}

	/**
	 * 更新方法
	 * @param strSql
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	protected int executeUpdate(String strSql, Object[] param) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		return runner.update(this.conn, strSql, param);
	}

	/**
	 * 更新方法
	 * @param strSql
	 * @return
	 * @throws SQLException
	 */
	protected int executeUpdate(String strSql) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		return runner.update(this.conn, strSql);
	}

	public Object executeQueryByObject(String sql, Object[] param, ResultSetHandler rsh) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		return (Object) runner.query(this.conn, sql, rsh, param);
	}

	/**
	 * 查询分页List
	 * @param strSql
	 * @param po
	 * @param paramList
	 * @param rsh
	 * @return
	 * @throws SQLException
	 */
	protected List executePageQuery(String strSql,List paramList, ResultSetHandler rsh, Page po) throws SQLException {
		this.conn = DBConnectionHandler.getConnection();
		QueryRunner runner = BeanQueryRunner.getRuner();
		strSql= PageSqlUtil.toPageUtil(strSql, po, paramList, SysConstant.jdbcType);
		return (List) runner.query(this.conn, strSql, rsh, paramList.toArray());
	}
}
