package common.plugin.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kin207.zn.SpringKit;

import common.utils.ListUtil;

public class Db {

	/**************************
	 ********   查询   ******** 
	 **************************/
	public static <M extends Model<M>> M findById(Class<M> clazz, Object id) 
	{
		String table = getTable(clazz);
		String sql = SqlKit.findById(table);
		
		return findFirst(clazz, sql, id);
	}

	public static <M extends Model<M>> M findById(Class<M> clazz, Object id, String selectColumns) 
	{
		String table = getTable(clazz);
		String sql = SqlKit.findById(table, selectColumns);
		
		return findFirst(clazz, sql, id);
	}
	
	public static <M extends Model<M>> List<Map<String, Object>> findByIds(Class<M> clazz, String selectColumns, List<Integer> ids)
	{
		if (ids == null || ids.isEmpty()) 
		{
			return new ArrayList<Map<String,Object>>();
		}
		
		String sql = SqlKit.findByIds(getTable(clazz), selectColumns);
		
		String idsStr = ListUtil.joinToStr(ids, ",");
		List<Map<String, Object>> list = Db.find(sql, "ids", idsStr);
		
		return list;
	}

	public static <M extends Model<M>> M findFirstByColumns(Class<M> clazz, String whereColumns, Object...values) 
	{
		return findFirstByColumns(SqlKit.COLUMN_ALL, clazz, whereColumns, values);
	}
	
	public static <M extends Model<M>> M findFirstByColumns(String selectColumns, Class<M> clazz, String whereColumns, Object...values) 
	{
		String table = getTable(clazz);
		
		String[] whereColumnArr = adaptToArray(whereColumns);
		
		Map<String, Object> paramMap = adaptToParamMap(whereColumnArr, values);
		
		String sql = SqlKit.findFirstByColumns(table, selectColumns, whereColumnArr);
		
		return findFirst(clazz, sql, paramMap);
	}

	public static List<Map<String, Object>> find(String sql, String paramColumns, Object...values) 
	{
		String[] paramColumnArr = adaptToArray(paramColumns);
		Map<String, Object> paramMap = adaptToParamMap(paramColumnArr, values);
		
		return getSqlMapper().selectList(sql, paramMap);
	}
	
	public static Map<String, Object> findFirst(String sql, String paramColumns, Object...values) 
	{
		String[] paramColumnArr = adaptToArray(paramColumns);
		Map<String, Object> paramMap = adaptToParamMap(paramColumnArr, values);
		return getSqlMapper().selectOne(sql, paramMap);
	}
	
	public static Map<String, Object> findFirst(String sql) 
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return getSqlMapper().selectOne(sql, paramMap);
	}
	
	public static <M extends Model<M>> List<Map<String, Object>> findByColumns(Class<M> clazz, String whereColumns, Object...values) 
	{
		return findByColumns("*", clazz, whereColumns, values);
	}
	
	public static <M extends Model<M>> List<Map<String, Object>> findByColumns(String selectColumns, Class<M> clazz, String whereColumns, Object...values) 
	{
		String table = getTable(clazz);
		
		String[] whereColumnArr = adaptToArray(whereColumns);
		String sql = SqlKit.findByColumns(table, selectColumns, whereColumnArr);
		
		Map<String, Object> paramMap = adaptToParamMap(whereColumnArr, values);
		
		return getSqlMapper().selectList(sql, paramMap);
	}
	

	public static <M extends Model<M>> M findFirst(Class<M> clazz, String sql, String paramColumns, Object...values) 
	{
		String[] paramColumnArr = adaptToArray(paramColumns);
		Map<String, Object> paramMap = adaptToParamMap(paramColumnArr, values);
		return findFirst(clazz, sql, paramMap);
	}
	
	public static <M extends Model<M>> List<Map<String, Object>> findIn(String selectColumns, Class<M> clazz, String inColumn, List<Integer> inValues, String whereColumns, Object... values) 
	{
		
		String[] whereColumnArr = adaptToArray(whereColumns);
		Map<String, Object> paramMap = adaptToParamMap(whereColumnArr, values);
		
		String inValuesStr = ListUtil.joinToStr(inValues, ",");
		paramMap.put(inColumn, inValuesStr);
		
		String sql = SqlKit.findIn(getTable(clazz), selectColumns, inColumn, whereColumnArr);
		
		List<Map<String, Object>> list = getSqlMapper().selectList(sql, paramMap);
		
		return list;
	}
	
	private static <M extends Model<M>> M findFirst(Class<M> clazz, String sql, Object id) 
	{
		Map<String, Object> map = getSqlMapper().selectOne(sql, id);
		
		if (map == null) 
		{
			return null;
		}
		
		return Model.create(clazz, map);
	}
	
	
	/**************************
	 ****   保存／更新操作   **** 
	 **************************/
	
	
	@SuppressWarnings("unchecked")
	public static <M extends Model<M>> int save(M model) 
	{
		String table = getTable(model.getClass());
		String sql = SqlKit.save(table, model.attrSet());
		
		return getSqlMapper().insert(sql, Model.COLUMN_ID, model.getAttrs());
	}
	
	
	@SuppressWarnings("unchecked")
	public static <M extends Model<M>> int update(M model) 
	{
		String table = getTable(model.getClass());
		String sql = SqlKit.update(table, model.attrSet());
		
		return getSqlMapper().update(sql, model.getAttrs());
	}
	
	
	public static <M extends Model<M>> int updateBySql(String sql,String paramColumns, Object...values) 
	{
	//	String table = getTable(model.getClass());
	//	String sql = SqlKit.update(table, model.attrSet());
		String[] paramColumnArr = adaptToArray(paramColumns);
		Map<String, Object> paramMap = adaptToParamMap(paramColumnArr, values);
		return getSqlMapper().update(sql, paramMap);
	}
	
	public static <M extends Model<M>> int updateByColumns(Class<M> clazz, String updateColumns, String whereColumns, Object...values) 
	{
		String table = getTable(clazz);
		
		String[] updateColumnsArr = adaptToArray(updateColumns);
		String[] whereColumnArr = adaptToArray(whereColumns);
		
		String sql = SqlKit.update(table, updateColumnsArr, whereColumnArr);
		
		Map<String, Object> paramMap = adaptArraysToParamMap(updateColumnsArr, whereColumnArr, values);
		
		return getSqlMapper().update(sql, paramMap);
	}
	
	public static <M extends Model<M>> int deleteByColumn(Class<M> clazz, String whereColumns, Object... values) 
	{
		String table = getTable(clazz);
		String[] whereColumnArr = adaptToArray(whereColumns);
		
		String sql = SqlKit.delete(table, whereColumnArr);
		
		Map<String, Object> paramMap = adaptToParamMap(whereColumnArr, values);
		
		return getSqlMapper().delete(sql, paramMap);
	}

	public static <M extends Model<M>> int deleteById(Class<M> clazz, Object id) 
	{
		String table = getTable(clazz);
		String sql = SqlKit.deleteById(table);
		
		return getSqlMapper().delete(sql, id);
	}
	
	
	
	
	
	
	private static <M extends Model<M>> String getTable(Class<M> clazz) 
	{
		return clazz.getAnnotation(Table.class).value();
	}
	
	private static SqlMapper getSqlMapper() 
	{
		SqlSession sqlSession = SpringKit.getSqlSession();
		SqlMapper mapper = new SqlMapper(sqlSession);
		return mapper;
	}
	
	private static String[] adaptToArray(String whereColumns) 
	{
		if(whereColumns == null) //add by luke
			return null;
		String[] whereColumnArr = whereColumns.split(",");
		for (int i = 0; i < whereColumnArr.length; i++) 
		{
			whereColumnArr[i] = whereColumnArr[i].trim();;
		}
		return whereColumnArr;
	}
	
	private static Map<String, Object> adaptToParamMap(String[] paramColumnArr, Object... values) 
	{
		if(paramColumnArr == null || values == null) //add by Luke
			return new HashMap<String, Object>();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < paramColumnArr.length; i++) 
		{
			paramMap.put(paramColumnArr[i], values[i]);
		}
		return paramMap;
	}

	private static Map<String, Object> adaptArraysToParamMap(String[] updateColumnsArr, String[] whereColumnArr, Object[] values) 
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		int index = 0;
		
		for (int i = 0; i < updateColumnsArr.length; i++, index++) 
		{
			paramMap.put(updateColumnsArr[i], values[index]);
		}
		
		for (int i = 0; i < whereColumnArr.length; i++, index++) 
		{
			paramMap.put(whereColumnArr[i], values[index]);
		}
		
		return paramMap;
	}


	/*
	 * 理论代码，未调试成功，千万不要打开使用
	public static <M extends Model<M>> int saveList(List<M> modelList, Class<M> clazz) 
	{
		if (modelList == null || modelList.isEmpty()) 
		{
			return 0;
		}
		Set<String> columnSet = modelList.get(0).attrSet();
		String table = getTable(clazz);
		String sql = SqlKit.saveList(table, modelList.size(), columnSet);
		
		List<Map<String, Object>> paramList = new ArrayList<Map<String,Object>>();
		for (M m : modelList) 
		{
			paramList.add(m.getAttrs());
		}
		
		return getSqlMapper().insert(sql, Model.COLUMN_ID, paramList);
	}*/
	
	
}
