/** 
 * @项目名称: FSP
 * @文件名称: JsonRedisSerializer<T> implements RedisSerializer<T>  
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util.redis;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import cn.uce.omg.sso.util.JsonUtils;

/**
 * Redis对JSON对象的序列化
 * @author huangting
 * @date 2017年6月9日 下午2:38:35
 */
public class JsonRedisSerializer<T> implements RedisSerializer<T>  {
	//字符编码
	private final Charset charset;

	public JsonRedisSerializer() {
		this(Charset.forName("UTF8"));
	}
	
	/**
	 * 
	 * @param charset
	 */
	public JsonRedisSerializer(Charset charset) {
		Assert.notNull(charset);
		this.charset = charset;
	}

  /**
	* 系列化
	* <p>Description: </p> 
	* @param t
	* @return
	* @throws SerializationException 
	* @see org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return null;
		}
		return JsonUtils.toJsonString(t).getBytes(charset);
	}

  /**
	* 反序列化
	* <p>Description: </p> 
	* @param bytes
	* @return
	* @throws SerializationException 
	* @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
	 */
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}
		return (T) JsonUtils.jsonParseObject(new String(bytes, charset));
	}
	
}
