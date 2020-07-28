/** 
 * @项目名称: FSP
 * @文件名称: ObjectConvertUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
//package uce.sso.sdk.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.BeanUtils;
//
//public class ObjectConvertUtil {
//
//	public static <T> List<T> convertList(List<?> sourceList, Class<T> t) {
//		if (sourceList == null) {
//			return null;
//		} else if (sourceList.isEmpty()) {
//			return new ArrayList<T>();
//		}
//		List<T> targetList = new ArrayList<T>();
//		T target = null;
//		Object sourceDto = null;
//		try {
//			for (int i = 0; i < sourceList.size(); i++) {
//				sourceDto = sourceList.get(i);
//				target = t.newInstance();
//				BeanUtils.copyProperties(sourceDto, target);
//				targetList.add(target);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return targetList;
//	}
//	
//	public static <T> T convertObject(Object source, Class<T> t) {
//		T target = null;
//		try {
//			target = t.newInstance();
//			BeanUtils.copyProperties(source, target);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return target;
//	}
//}
