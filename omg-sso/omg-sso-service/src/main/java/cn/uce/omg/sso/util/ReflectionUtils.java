/** 
 * @项目名称: FSP
 * @文件名称: ReflectionUtils 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 反射、泛型工具类
 * @author 肖佳
 */
public class ReflectionUtils {
	private static Log log = LogFactory.getLog(ReflectionUtils.class);
	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("不可能抛出的异常" +  e.getMessage());
		}
		return result;
	}
	
	/**
	 * 循环向上转型, 获取对象的DeclaredField,	 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	/**  
     * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>  
     *  
     * @param clazz clazz 需要反射的类,该类必须继承范型父类
     * @param index 泛型参数所在索引,从0开始.  
     * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */  
    @SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {    
        Type genType = clazz.getGenericSuperclass();//得到泛型父类  
        //如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class   
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;   
        }   
        //返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends DaoSupport<Buyer,Contact>就返回Buyer和Contact类型   
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();                   
        if (index >= params.length || index < 0) { 
        	 throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
        }      
        if (!(params[index] instanceof Class)) {
            return Object.class;   
        }   
        return (Class) params[index];
    }
    
    /**  
     * 通过反射,获得指定类的接口的泛型参数的实际类型. 如BoundListRedisSupport<T> implements BoundListKey<T>
     *  
     * @param clazz clazz 需要反射的类,该类必须实现范型接口
     * @param interfacerIndex 接口所在索引,从0开始.  
     * @param index 泛型参数所在索引,从0开始.  
     * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */  
    @SuppressWarnings("rawtypes")
	public static Class getInterfacesGenricType(Class clazz, int interfacerIndex, int index){
    	Type[] genTypes = clazz.getGenericInterfaces();//得到泛型接口
    	Type genType = genTypes[interfacerIndex];
        //如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class   
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;   
        }   
        //返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends DaoSupport<Buyer,Contact>就返回Buyer和Contact类型   
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();                   
        if (index >= params.length || index < 0) { 
        	 throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
        }      
        if (!(params[index] instanceof Class)) {
            return Object.class;   
        }   
        return (Class) params[index];
    }
    
    /**
     * 通过反射,获得指定类的接口的泛型参数的实际类型. 如BoundListRedisSupport<T> implements BoundListKey<T>
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Class getInterfacesGenricType(Class clazz){
    	return getInterfacesGenricType(clazz, 0, 0);
    }
    
	/**  
     * 通过反射,获得指定类的父类的第一个泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>  
     *  
     * @param clazz clazz 需要反射的类,该类必须继承泛型父类
     * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */  
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
    	return getSuperClassGenricType(clazz,0);
    }
	/**  
     * 通过反射,获得方法返回值泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
     *  
     * @param Method method 方法
     * @param int index 泛型参数所在索引,从0开始.
     * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */ 
    @SuppressWarnings("rawtypes")
	public static Class getMethodGenericReturnType(Method method, int index) {
    	Type returnType = method.getGenericReturnType();
    	if(returnType instanceof ParameterizedType){
    	    ParameterizedType type = (ParameterizedType) returnType;
    	    Type[] typeArguments = type.getActualTypeArguments();
            if (index >= typeArguments.length || index < 0) { 
            	 throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
            } 
    	    return (Class)typeArguments[index];
    	}
    	return Object.class;
    }
	/**  
     * 通过反射,获得方法返回值第一个泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
     *  
     * @param Method method 方法
     * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */ 
    @SuppressWarnings("rawtypes")
	public static Class getMethodGenericReturnType(Method method) {
    	return getMethodGenericReturnType(method, 0);
    }
    
	/**  
     * 通过反射,获得方法输入参数第index个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String, Buyer> maps, List<String> names){}
     *  
     * @param Method method 方法
     * @param int index 第几个输入参数
     * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
     */ 
    @SuppressWarnings("rawtypes")
	public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
    	List<Class> results = new ArrayList<Class>();
    	Type[] genericParameterTypes = method.getGenericParameterTypes();
    	if (index >= genericParameterTypes.length ||index < 0) {
             throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
        } 
    	Type genericParameterType = genericParameterTypes[index];
    	if(genericParameterType instanceof ParameterizedType){
    	     ParameterizedType aType = (ParameterizedType) genericParameterType;
    	     Type[] parameterArgTypes = aType.getActualTypeArguments();
    	     for(Type parameterArgType : parameterArgTypes){
    	         Class parameterArgClass = (Class) parameterArgType;
    	         results.add(parameterArgClass);
    	     }
    	     return results;
    	}
    	return results;
    }
	/**  
     * 通过反射,获得方法输入参数第一个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String, Buyer> maps, List<String> names){}
     *  
     * @param Method method 方法
     * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
     */ 
    @SuppressWarnings("rawtypes")
	public static List<Class> getMethodGenericParameterTypes(Method method) {
    	return getMethodGenericParameterTypes(method, 0);
    }
	/**  
     * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
     *  
     * @param Field field 字段
     * @param int index 泛型参数所在索引,从0开始.
     * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */ 
    @SuppressWarnings("rawtypes")
	public static Class getFieldGenericType(Field field, int index) {
    	Type genericFieldType = field.getGenericType();
    	
    	if(genericFieldType instanceof ParameterizedType){
    	    ParameterizedType aType = (ParameterizedType) genericFieldType;
    	    Type[] fieldArgTypes = aType.getActualTypeArguments();
    	    if (index >= fieldArgTypes.length || index < 0) { 
    	    	throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
            } 
    	    return (Class)fieldArgTypes[index];
    	}
    	return Object.class;
    }
	/**  
     * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
     *  
     * @param Field field 字段
     * @param int index 泛型参数所在索引,从0开始.
     * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */ 
    @SuppressWarnings("rawtypes")
	public static Class getFieldGenericType(Field field) {
    	return getFieldGenericType(field, 0);
    }
    
    /**
     * 给一个接口，返回这个接口的所有实现类
     * @author 肖佳
     * @param c
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class<Object>> getAllClassByInterface(Class c) {
        List<Class<Object>> returnClassList = new ArrayList<Class<Object>>();

        String packageName = c.getPackage().getName(); // 获得当前的包名
        try {
            List<Class<Object>> allClass = getClasses(packageName); // 获得当前包下以及子包下的所有类
            // 判断是否是同一个接口
            for (int i = 0; i < allClass.size(); i++) {
                if (c.isAssignableFrom(allClass.get(i))) { // 判断是不是一个接口
                    if (!c.equals(allClass.get(i))) { // 本身不加进去
                        returnClassList.add(allClass.get(i));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
        	log.error(e.getMessage(), e);
        } catch (IOException e) {
        	log.error(e.getMessage(), e);
        }

        return returnClassList;
    }

    /**
     * 从一个包中查找出所有的类，在jar包中不能查找
     * @author 肖佳
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static List<Class<Object>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<Object>> classes = new ArrayList<Class<Object>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }
    /**
     * 查找类
     * @author 肖佳
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	private static List<Class<Object>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<Object>> classes = new ArrayList<Class<Object>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add((Class<Object>) Class.forName(packageName + '.'
                        + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
    
}
