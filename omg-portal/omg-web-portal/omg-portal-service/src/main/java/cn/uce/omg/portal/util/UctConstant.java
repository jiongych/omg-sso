package cn.uce.omg.portal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UctConstant {

	//总部
	private static final List<String> HEADQUARTERS = new ArrayList<>();
	//省区
	private static final List<String> PROVINCIAL = new ArrayList<>();
	//网点
	private static final List<String> DOT = new ArrayList<>();
	//快递员
	private static final List<String> COURIER = new ArrayList<>();
	
	private static final Map<String, List<String>> viewScope = new HashMap<>();
	
	static {
		HEADQUARTERS.add("总部");
		HEADQUARTERS.add("省区");
		HEADQUARTERS.add("网点");
		HEADQUARTERS.add("快递员");
		viewScope.put("总部", HEADQUARTERS);
		
		PROVINCIAL.add("省区");
		PROVINCIAL.add("网点");
		PROVINCIAL.add("快递员");
		viewScope.put("省区", PROVINCIAL);
		
		DOT.add("网点");
		DOT.add("快递员");
		viewScope.put("网点", DOT);
		
		COURIER.add("快递员");
		viewScope.put("快递员", COURIER);
	}
	
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据受众群体获取其及以下
	 * </pre>
	 * @param identity
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午5:03:46
	 */
	public static List<String> findViewScope(String identity) {
		if (null == identity || identity.length() == 0) {
			return null;
		}
		return viewScope.get(identity);
	}
}
