package common.plugin.mybatis;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *  来自 jfinal Model 类
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Model<M extends Model> {
	private static Logger log = Logger.getLogger(Model.class);
	public static final String COLUMN_ID = "id";

	private static final String EMPTY_STRING = "";
	
	/**
	 * Attributes of this model
	 */
	private Map<String, Object> attrs = new HashMap<String, Object>();
	
	/**
	 * Put key value pair to the model
	 */
	public M set(String attr, Object value) {
		attrs.put(attr, value);
		return (M)this;
	}
	
	
	/**
	 * Get attribute of any mysql type
	 */
	public <T> T get(String attr) {
		return (T)(attrs.get(attr));
	}
	
	/**
	 * 保存插入数据库时，返回的自增ID为 long 类型
	 */
	public Integer getId(){
		Object id = get(COLUMN_ID);
		
		if (id != null && id instanceof Long) 
		{
			return ((Long)id).intValue();
		}
		
		return getInt(COLUMN_ID);
	}
	
	/**
	 * Get attribute of any mysql type. Returns defaultValue if null.
	 */
	public <T> T get(String attr, Object defaultValue) {
		Object result = attrs.get(attr);
		return (T)(result != null ? result : defaultValue);
	}
	
	/**
	 * Get attribute of mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
	 */
	public String getStr(String attr) {
		return (String)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
	 */
	public Integer getInt(String attr) {
		Object obj = attrs.get(attr);
		if(obj != null && obj instanceof Long){
			return ((Long)obj).intValue();
		}
		return (Integer)obj;
		//return (Integer)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: bigint, unsign int
	 */
	public Long getLong(String attr) {
		Object obj = attrs.get(attr);
		
		if (obj != null && obj instanceof Integer){
			return ((Integer)obj).longValue();
		}
		
		return (Long)obj;
		//return (Long)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String attr) {
		return (java.math.BigInteger)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: date, year
	 */
	public java.util.Date getDate(String attr) {
		return (java.util.Date)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: time
	 */
	public java.sql.Time getTime(String attr) {
		return (java.sql.Time)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: timestamp, datetime
	 */
	public java.sql.Timestamp getTimestamp(String attr) {
		return (java.sql.Timestamp)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: real, double
	 */
	public Double getDouble(String attr) {
		return (Double)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: float
	 */
	public Float getFloat(String attr) {
		return (Float)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String attr) {
		return (Boolean)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String attr) {
		return (java.math.BigDecimal)attrs.get(attr);
	}
	
	/**
	 * Get attribute of mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob
	 */
	public byte[] getBytes(String attr) {
		return (byte[])attrs.get(attr);
	}
	
	/**
	 * Get attribute of any type that extends from Number
	 */
	public Number getNumber(String attr) {
		return (Number)attrs.get(attr);
	}
	
	public List<Map<String, Object>> getList(String attr) 
	{
		return (List<Map<String, Object>>)attrs.get(attr);
	}
	
	/**
	 * Return attribute Map.
	 */
	public Map<String, Object> getAttrs() {
		return attrs;
	}
	
	public Set<String> attrSet() {
		return attrs.keySet();
	}
	
	/**
	 * Return attribute Set.
	 */
	public Set<Entry<String, Object>> getAttrsEntrySet() {
		return attrs.entrySet();
	}
	
	public M setId(int id)
	{
		set(COLUMN_ID, id);
		return getMe();
	}
	
	public M setAttrs(Map<String, Object> attrsMap, String...mergeColumns) 
	{
		for (String column : mergeColumns) 
		{
			set(column, attrsMap.get(column));
		}
		return (M)this;
	}
	
	/**
	 * Set attributes with other model.
	 * @param model the Model
	 * @return this Model
	 */
	public M setAttrs(M model) {
		return setAttrs(model.getAttrs());
	}
	
	/**
	 * Set attributes with Map.
	 * @param attrs attributes of this model
	 * @return this Model
	 */
	public M setAttrs(Map<String, Object> attrs) {
		for (Entry<String, Object> e : attrs.entrySet())
			set(e.getKey(), e.getValue());
		return (M)this;
	}
	
	/**
	 * Remove attribute of this model.
	 * @param attr the attribute name of the model
	 * @return this model
	 */
	public M remove(String attr) {
		attrs.remove(attr);
		return (M)this;
	}
	
	/**
	 * Remove attributes of this model.
	 * @param attrs the attribute names of the model
	 * @return this model
	 */
	public M remove(String... attrs) {
		if (attrs != null)
			for (String a : attrs) {
				this.attrs.remove(a);
			}
		return (M)this;
	}
	
	/**
	 * Remove attributes if it is null.
	 * @return this model
	 */
	public M removeNullValueAttrs() {
		for (Iterator<Entry<String, Object>> it = attrs.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() == null) {
				it.remove();
			}
		}
		return (M)this;
	}
	
	/**
	 * Remove all attributes of this model.
	 * @return this model
	 */
	public M clear() {
		attrs.clear();
		return (M)this;
	}

	public M getMe()
	{
		return (M)this;
	}

	
	
	/***************************
	 ******   格式化属性   ********
	 ***************************/
	public M setNullToEmptyStr() 
	{
		for (String key : attrs.keySet()) 
		{
			if (attrs.get(key) == null) 
			{
				attrs.put(key, EMPTY_STRING);
			}
		}
		return (M)this;
	}
	
	public M fillNullValue(String columns) 
	{
		String[] columnArr = columns.split(",");
		for (String column : columnArr) 
		{
			String trueColumn = column.trim();
			if (get(trueColumn) == null) 
			{
				set(trueColumn, "");
			}
		}
		return (M)this;
	}
	
	public M formatDate(String...attrKeys) 
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format(format, attrKeys);
	}
	
	public M formatTime(String...attrKeys) 
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format(format, attrKeys);
	}
	
	private M format(DateFormat format, String...attrKeys) 
	{
		for (String attrKey : attrKeys) 
		{
			Date date = getDate(attrKey);
			if (date != null) 
			{
				attrs.put(attrKey, format.format(date));
			}
		}
		return (M)this;
	}
	
	/***************************
	 ******   创建实体   ********
	 ***************************/
	public static <M extends Model<M>> M create(Class<M> clazz) 
	{
		try {
			M m = clazz.newInstance();
			return m;
		} catch (Exception e) {
			log.error("Model 创建实体 " +e.getMessage());
		}
		return null;
	}

	public static <M extends Model<M>> M create(Class<M> clazz, Map<String, Object> map) 
	{
		M m = Model.create(clazz);
		if (m != null) 
		{
			m.setAttrs(map);
		}
		return m;
	}
	
	/**************************
	 *****   数据库操作   ******
	 **************************/
	
	public void save() 
	{
		Db.save(getMe());
	}
	
	public void update() 
	{
		Db.update(getMe());
	}
}
