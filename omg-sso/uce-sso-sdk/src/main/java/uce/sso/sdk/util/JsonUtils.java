/** 
 * @项目名称: FSP
 * @文件名称: JsonUtils 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
//package uce.sso.sdk.util;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//
///**
// * 
// * @author zhangd
// *
// */
//public class JsonUtils {
//
//	/**
//     * 转换成json
//     * 默认将class标记序列化进去
//     */
//    public static String toJsonString(Object obj) {
//        return toJsonString(obj,true);
//    }
//    
//    /**
//     * 转换成json
//     */
//    public static String toJsonString(Object obj,boolean seralizerClass) {
//        if(obj != null) {
//            if(obj instanceof String && StringUtils.isBlank((String) obj)) {
//                return "";
//            }
//            try {
//                if(seralizerClass) {
//                    return JSON.toJSONString(obj, SerializerFeature.WriteClassName);
//                } else {
//                    return JSON.toJSONString(obj);
//                }
//            } catch(Exception e) {
//                return "null";
//            }
//        }
//        return "null";
//    }
//    
//    /**
//     * json转javaBean对象
//     */
//    public static Object jsonParseObject(String json) {
//        if(StringUtils.isBlank(json)) {
//            return "";
//        } else if(StringUtils.equalsIgnoreCase(json, "null")) {
//            return null;
//        }
//        return JSON.parse(json);
//    }
//    
//    /**
//     * json转javaBean对象
//     * @param json
//     * @param clazz
//     * @return 如果为null、空字符串或者'null'字符串，将返回null
//     */
//    public static <T> T toObject(String json, Class<T> clazz) {
//    	if (StringUtils.isBlank(json)) {
//    		return null;
//    	} else if (json.equalsIgnoreCase("null")) {
//    		return null;
//    	}
//    	
//    	return JSON.parseObject(json, clazz);
//    }
//    
//}
