package common.plugin.mybatis;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kin207.zn.support.MobileParam;

import common.utils.DateUtil;


public class MapKit {

	public static void formatDate(List<Map<String, Object>> maps, String keys) 
	{
		formatDate(maps, keys, "yyyy-MM-dd");
	}
	
	public static void formatTime(List<Map<String, Object>> maps, String keys) 
	{
		formatDate(maps, keys, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static void formatDate(List<Map<String, Object>> maps, String keys, String formatPatterm) 
	{
		if (maps == null || maps.isEmpty()) 
		{
			return;
		}
		
		DateFormat format = new SimpleDateFormat(formatPatterm);
		
		String[] keyArr = keys.split(",");
		for (String keyStr : keyArr) 
		{
			String key = keyStr.trim();
			for (Map<String, Object> map: maps)
			{
				String value = format.format(map.get(key));
				map.put(key, value);
			}
		}
	}

	public static void fill(Map<String, Object> result, String keys, Object...values) 
	{
		if (result == null) 
		{
			return;
		}
		
		String[] keyArr = keys.split(",");
		for (int i = 0; i < keyArr.length; i++) 
		{
			String key = keyArr[i].trim();
			result.put(key, values[i]);
		}
	}

	public static void fillNull(Map<String, Object> result, String keys, Object...defaultValues) 
	{
		if (result == null) 
		{
			return;
		}
		
		String[] keyArr = keys.split(",");
		for (int i = 0; i < keyArr.length; i++) 
		{
			String key = keyArr[i].trim();
			if (!result.containsKey(key) || result.get(key) == null) 
			{
				result.put(key, defaultValues[i]); 
			}
		}
	}

	public static void bigDecimalToInt(Map<String, Object> result, String keys) 
	{
		if (result == null) 
		{
			return;
		}
		
		String[] keyArr = keys.split(",");
		for (int i = 0; i < keyArr.length; i++) 
		{
			String key = keyArr[i].trim();
			Object value = result.get(key);
			if (value instanceof BigDecimal) 
			{
				Integer intVal = ((BigDecimal) value).intValue();
				result.put(key, intVal);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getToUniqueList(List<Map<String, Object>> rs, String column, Class<T> clazz) 
	{
		if(rs == null || rs.isEmpty())
		{
			return new ArrayList<T>();
		}
		
		List<T> list = new ArrayList<T>();
		for (Map<String, Object> r : rs) 
		{
			Object value = r.get(column);
			if (value != null && !list.contains(value)) 
			{
				list.add((T)value);
			}
		}
		return list;
	}
	
	
	public static List<Map<String, Object>> mergeColumnsTo(List<Map<String, Object>> toRecords, List<Map<String, Object>> fromRecords, String eqToColumn, String eqFromColumn, String mergeColumns) 
	{
		if (toRecords == null || toRecords.isEmpty() || fromRecords == null || fromRecords.isEmpty()) 
		{
			return toRecords;
		}
		
		Map<Object, Map<String, Object>> map = listToMap(fromRecords, eqFromColumn, Object.class);
		
		String[] mergeColumnArr = mergeColumns.split(",");
		
		for (Map<String, Object> to : toRecords) 
		{
			Map<String, Object> from = map.get(to.get(eqToColumn));
			if(from != null)
			{
				for (String column : mergeColumnArr) 
				{
					String trimColumn = column.trim();
					to.put(trimColumn, from.get(trimColumn));
				}
			}
		}
		return toRecords;
	}
	
	
	@SuppressWarnings("unchecked")
	private static <K> Map<K, Map<String, Object>> listToMap(List<Map<String, Object>> list, String columnKey, Class<K> keyType)
	{
		if(list == null || list.isEmpty())
		{
			return new HashMap<K, Map<String, Object>>();
		}
		
		Map<K, Map<String, Object>> map = new HashMap<K, Map<String, Object>>();
		for (Map<String, Object> t : list)
		{
			Object val = t.get(columnKey);
			if(val != null)
			{
				map.put((K)val, t);
			}
		}
		return map;
	}

	public static void mergeColumnsTo(Map<String, Object> toMap, Map<String, Object> fromMap, String columns) 
	{
		if (toMap == null || fromMap == null) 
		{
			return;
		}
		String[] mergeColumnArr = columnsStrToArr(columns);
		for (String column : mergeColumnArr) 
		{
			toMap.put(column, fromMap.get(column));
		}
	}

	private static String[] columnsStrToArr(String columns) 
	{
		String[] columnArr = columns.split(",");
		for (int i = 0; i < columnArr.length; i++) 
		{
			columnArr[i] = columnArr[i].trim();
		}
		return columnArr;
	}

	public static void mergeStatusColumnTo(List<Map<String, Object>> toList, List<Map<String, Object>> fromList, String eqToColumn, String eqFromColumn, String statusColumn, int statusYes, int statusNo) 
	{
		if (toList == null || toList.isEmpty()) 
		{
			return;
		}
		
		addColumn(toList, statusColumn, statusNo);
		
		if (fromList == null || fromList.isEmpty()) 
		{
			return;
		}
		
		for (Map<String, Object> fromMap : fromList) 
		{
			Object fromEqVal = fromMap.get(eqFromColumn);
			for (Map<String, Object> toMap : toList)
			{
				Object toEqVal = toMap.get(eqToColumn);
				if (fromEqVal.equals(toEqVal)) 
				{
					toMap.put(statusColumn, statusYes);
					break;
				}
			}
		}
		
	}
	
	public static void addColumn(List<Map<String, Object>> list, String statusColumn, Object value) 
	{
		if (list == null || list.isEmpty()) 
		{
			return;
		}
		for (Map<String, Object> record : list) 
		{
			record.put(statusColumn, value);
		}
	}

	public static void putFriendlyTimeT(List<Map<String, Object>> list, String column, String newColunm,MobileParam param) 
	{
		if(list == null || list.isEmpty())
		{
			return;
		}
		for (Map<String, Object> record : list) 
		{
			if(record.get(column) != null)
			{
				Date date = (Date)record.get(column);
				record.put(newColunm, DateUtil.friendlyTime(date,param));
			}
		}
	}
	public static void putFriendlyTime(List<Map<String, Object>> list, String column, String newColunm ) 
	{
		if(list == null || list.isEmpty())
		{
			return;
		}
		for (Map<String, Object> record : list) 
		{
			if(record.get(column) != null)
			{
				Date date = (Date)record.get(column);
				record.put(newColunm, DateUtil.friendlyTime(date));
			}
		}
	}
	public static <M extends Model<M>> List<Map<String, Object>> getMapList(List<M> modelList) 
	{
		if (modelList == null || modelList.isEmpty()) 
		{
			return new ArrayList<Map<String,Object>>();
		}
		
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		for (M m : modelList) 
		{
			maps.add(m.getAttrs());
		}
		return maps;
	}
}
