package common.plugin.mybatis;

import java.util.Set;

import org.apache.log4j.Logger;



public class SqlKit {
	
	private static final Logger log = Logger.getLogger(SqlKit.class);
	
	public static final String COLUMN_ALL = "*";
	
	
	public static String findById(String table) 
	{
		return findById(table, COLUMN_ALL);
	}
	
	public static String findById(String table, String selectColumns) 
	{
		String sql = "select %s from %s where id = #{id}";
		
		if (selectColumns == null) 
		{
			selectColumns = COLUMN_ALL;
		}
		
		sql = String.format(sql, selectColumns, table);
		log.info(sql);
		return sql;
	}
	
	public static String findByIds(String table, String selectColumns) 
	{
		String sql = "select %s from %s where id in (${ids})";
		
		if (selectColumns == null) 
		{
			selectColumns = COLUMN_ALL;
		}
		
		sql = String.format(sql, selectColumns, table);
		log.info(sql);
		
		return sql;
	}
	
	public static String findFirstByColumns(String table, String selectColumns, String[] whereColumns) 
	{
		String sql = "select %s from %s where %s limit 1";
		
		String where = buildWhereBlock(whereColumns);
		
		sql = String.format(sql, selectColumns, table, where);
		log.info(sql);
		return sql;
	}
	
	public static String findByColumns(String table, String selectColumns, String[] whereColumns) 
	{
		String sql = "select %s from %s where %s";
		
		String where = buildWhereBlock(whereColumns);
		
		sql = String.format(sql, selectColumns, table, where);
		log.info(sql);
		return sql;
	}
	
	public static String findIn(String table, String selectColumns, String inColumn, String[] whereColumns) 
	{
		String sql = "select %s from %s where %s and %s in (${%s})";
		
		String where = buildWhereBlock(whereColumns);
		
		sql = String.format(sql, selectColumns, table, where, inColumn, inColumn);
		log.info(sql);
		return sql;
	}

	
	public static String save(String table, Set<String> columns)
	{
		String sql = "insert ignore into %s %s values %s";
		
		StringBuilder insertColumns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		
		for (String column : columns) 
		{
			if (insertColumns.length() > 1) 
			{
				insertColumns.append(",");
				values.append(",");
			}
			insertColumns.append(column);
			values.append("#{").append(column).append("} ");
		}
		
		insertColumns.append(")");
		values.append(")");
		
		sql = String.format(sql, table, insertColumns.toString(), values.toString());
		log.info(sql);
		return sql;
	}
	
	public static String saveList(String table, int size, Set<String> columns) 
	{
		String sql = "insert into %s %s values %s";
		String columnBlock = buildInsertColumnBlock(columns);
		
		StringBuilder valuesBuilder = null;
		for (int i = 0; i < size; i++) 
		{
			if (valuesBuilder == null) 
			{
				valuesBuilder = buildInsertValueBlock(columns);
			}else 
			{
				valuesBuilder.append(",")
							 .append(buildInsertValueBlock(columns));
			}
		}
		sql = String.format(sql, table, columnBlock, valuesBuilder.toString());
		log.info(sql);
		return sql;
	}

	

	
	
	public static String update(String table, Set<String> updateColumnSet)
	{
		String sql = "update %s set %s where id = #{id}";
		
		StringBuilder updateColumns = new StringBuilder();
		for (String column : updateColumnSet) 
		{
			if (updateColumns.length() > 0) 
			{
				updateColumns.append(" , ");
			}
			updateColumns.append(column).append(" = #{").append(column).append("}");
		}
		
		sql = String.format(sql, table, updateColumns.toString());
		log.info(sql);
		return sql;
	}
	
	public static String update(String table, String[] updateColumnsArr, String[] whereColumnArr) 
	{
		String sql = "update %s set %s where %s";
		
		StringBuilder updateColumnBlock = new StringBuilder();
		for (String column : updateColumnsArr) 
		{
			if (updateColumnBlock.length() > 0) 
			{
				updateColumnBlock.append(" , ");
			}
			updateColumnBlock.append(column).append(" = #{").append(column).append("}");
		}
		
		String whereBlock = buildWhereBlock(whereColumnArr);
		
		sql = String.format(sql, table, updateColumnBlock.toString(), whereBlock);
		log.info(sql);
		return sql;
	}
	
	public static String delete(String table, String[] whereColumnArr) 
	{
		String sql = "delete from %s where %s";
		
		String whereBlock = buildWhereBlock(whereColumnArr);
		
		sql = String.format(sql, table, whereBlock);
		log.info(sql);
		return sql;
	}
	
	public static String deleteById(String table)
	{
		String sql = "delete from %s where id = #{id}";
		
		sql = String.format(sql, table);
		log.info(sql);
		return sql;
	}
	
	
	
	private static String buildWhereBlock(String[] whereColumns) 
	{
		StringBuilder whereBuilder = new StringBuilder();
		for (int i = 0; i < whereColumns.length; i++) 
		{
			String column = whereColumns[i];
			if (whereBuilder.length() > 0) 
			{
				whereBuilder.append(" and ");
			}
			whereBuilder.append(column).append(" = #{").append(column).append("} ");
		}
		return whereBuilder.toString();
	}

	private static StringBuilder buildInsertValueBlock(Set<String> columns) 
	{
		StringBuilder values = new StringBuilder("(");
		for (String column : columns) 
		{
			if (values.length() > 1) 
			{
				values.append(",");
			}
			values.append("#{").append(column).append("} ");
		}
		values.append(")");
		return values;
	}
	
	private static String buildInsertColumnBlock(Set<String> columns) 
	{
		StringBuilder insertColumns = new StringBuilder("(");
		for (String column : columns) 
		{
			if (insertColumns.length() > 1) 
			{
				insertColumns.append(",");
			}
			insertColumns.append(column);
		}
		insertColumns.append(")");
		
		return insertColumns.toString();
	}

}