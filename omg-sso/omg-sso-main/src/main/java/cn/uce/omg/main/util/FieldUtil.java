/** 
 * @项目名称: FSP
 * @文件名称: FieldUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字段util类
 * @author huangting
 * @date 2017年6月9日 下午12:17:48
 */
public class FieldUtil {
	/**
	 * 根据字段名给对象的字段赋值
	 *
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param o
	 *            对象
	 * @throws Exception
	 *             异常
	 */
	public static void setFieldValueByName(String fieldName,
			Object fieldValue, Object o) throws Exception {

		Field field = getFieldByName(fieldName, o.getClass());
		if (field != null) {
			field.setAccessible(true);
			// 获取字段类型
			Class<?> fieldType = field.getType();

			// 根据字段类型给字段赋值
			if (String.class == fieldType) {
				field.set(o, String.valueOf(fieldValue));
			} else if ((Integer.TYPE == fieldType)
					|| (Integer.class == fieldType)) {
				field.set(o, Integer.parseInt(fieldValue.toString()));
			} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
				field.set(o, Long.valueOf(fieldValue.toString()));
			} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
				field.set(o, Float.valueOf(fieldValue.toString()));
			} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
				field.set(o, Short.valueOf(fieldValue.toString()));
			} else if ((Double.TYPE == fieldType)
					|| (Double.class == fieldType)) {
				field.set(o, Double.valueOf(fieldValue.toString()));
			} else if (Character.TYPE == fieldType) {
				if ((fieldValue != null)
						&& (fieldValue.toString().length() > 0)) {
					field.set(o,
							Character.valueOf(fieldValue.toString().charAt(0)));
				}
			} else if (Date.class == fieldType) {
				field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(fieldValue.toString()));
			}
			else if (Boolean.class == fieldType) {
				boolean f=false;
				if(fieldValue.equals("T")||fieldValue.equals("1")||fieldValue.equals("true")){
					f=true;
				}
				else{
					f=false;
				}
				field.set(o, Boolean.valueOf(f));
			} 
			else {
				field.set(o, fieldValue);
			}
		} else {
			throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "
					+ fieldName);
		}
	}
	/**
	 * 根据字段名获取字段
	 *
	 * @param fieldName
	 *            字段名
	 * @param clazz
	 *            包含该字段的类
	 * @return 字段
	 */
	public static Field getFieldByName(String fieldName, Class<?> clazz) {
		// 拿到本类的所有字段
		Field[] selfFields = clazz.getDeclaredFields();

		// 如果本类中存在该字段，则返回
		for (Field field : selfFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null && superClazz != Object.class) {
			return getFieldByName(fieldName, superClazz);
		}

		// 如果本类和父类都没有，则返回空
		return null;
	}
}
