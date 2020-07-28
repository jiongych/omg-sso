/** 
 * @项目名称: FSP
 * @文件名称: DesUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author zhangd
 * @date 2015年3月11日 上午10:05:24
 * 
 */
@SuppressWarnings("restriction")
public class DesUtil {

	private static Key key;
	private static String seckey = "seckey";
	private static final String DES_ALGORITHM = "DES";

	/**
	 * 
	 * @param seckey
	 */
	public DesUtil(String seckey) {
		try {
			if (StringUtil.isEmpty(seckey)) {
				seckey = DesUtil.seckey;
			}
			KeyGenerator generator = KeyGenerator.getInstance(DES_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(seckey.getBytes());
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:16:37
	 */
	public String encypt(String str) {
		BASE64Encoder base64 = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes();
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encyptStrBytes = cipher.doFinal(strBytes);
			return base64.encode(encyptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 * @param str
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:16:43
	 */
	public String decypt(String str) {
		BASE64Decoder base64 = new BASE64Decoder();
		try {
			byte[] strBytes = base64.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encyptStrBytes = cipher.doFinal(strBytes);

			return new String(encyptStrBytes, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 * @param secretKey
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月9日 下午6:16:47
	 */
	public static SecretKey generateKey(String secretKey) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(DES_ALGORITHM);
		DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
		keyFactory.generateSecret(keySpec);
		return keyFactory.generateSecret(keySpec);
	}

	public static void main(String[] args) throws Exception {
		/*DesUtil des1 = new DesUtil("hbhk");

		String str = "hbhk";
		String en = des1.encypt(str);
		System.out.println(en);

		DesUtil des2 = new DesUtil("hbhk");
		String de = des2.decypt("BGV3WAvtdZ4=");
		System.out.println(de);

		DesUtil des3 = new DesUtil("hbhk1");
		String de3 = des3.decypt("BGV3WAvtdZ4=");
		System.out.println(de3);*/
	}
}
