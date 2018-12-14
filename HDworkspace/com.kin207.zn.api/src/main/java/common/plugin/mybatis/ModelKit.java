package common.plugin.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelKit {

	
	public static <M extends Model<M>> void put(List<M> models, String columns, Object...values) 
	{
		if (models == null || models.isEmpty()) 
		{
			return;
		}
		String[] columnArr = columns.split(",");
		for (int i = 0; i < columnArr.length; i++) 
		{
			String column = columnArr[i].trim();
			Object value = values[i];
			for (M m : models) 
			{
				m.set(column, value);
			}
		}
	}

	public static <M extends Model<M>> List<Map<String, Object>> getAttrMaps(List<M> models) 
	{
		if (models == null || models.isEmpty()) 
		{
			return new ArrayList<Map<String,Object>>();
		}
		
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		for (M m : models) 
		{
			maps.add(m.getAttrs());
		}
		return maps;
	}

}
