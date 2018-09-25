package com.kin207.zn.gh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kin207.zn.gh.model.GoodsEntity;

@Transactional
@Service
public class GoodsService {

	/**
	 * 菜单数据
	 * @return
	 */
	public List<Map<String,Object>> getAllGoods(){
		String typeCode = "";
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> good = new ArrayList<Map<String,Object>>() ;
		Map<String,Object> typeMap = new HashMap<String,Object>();
		List<Map<String,Object>> goods = GoodsEntity.findAllGoods();
		if(goods!=null&&!"".equals(goods)){
		for(Map<String,Object> map : goods){
			if(!typeCode.equals(map.get("typeCode"))){
				typeCode = map.get("typeCode").toString();
				typeMap.put("typeName", map.get("typeName"));
				typeMap.put("typeCode",typeCode);
				good = getGoods(goods,typeCode);
				typeMap.put("good", good);
				datas.add(typeMap);
			}
		}
		}
		return datas;
	}
	/**
	 * 获取相同类型的商品
	 * @param goods
	 * @param code
	 * @return
	 */
	public static List<Map<String,Object>> getGoods(List<Map<String,Object>> goods,String code){
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : goods){
			if(map.get("typeCode").equals(code)){
				map.remove("typeCode");
				map.remove("typeName");
				datas.add(map);
			}
		}
		return datas;
	}
}
