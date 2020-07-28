package cn.uce.portal.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.utils.StringUtil;


/**
 * 
 * @Description: (GZIPHelper) 
 * @author XJ
 * @date 2018年3月29日 下午9:37:36
 */
public class GZIPHelper {
	private static Logger logger = LoggerFactory.getLogger(GZIPHelper.class);
	public static void main(String[] args) {

		String ticketData = "{\"<di v>=+</div>xx\":\"20170121 09:44:01\",\"fs\":[{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"003\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"005\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"004\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"007\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"008\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"026\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"033\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"034\",\"oids\":[\"0\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"035\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"037\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"038\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"041\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"042\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"047\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"046\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"048\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"051\",\"oids\":[\"1\"]},{\"usg\":[1,1,1,1,1,1,1],\"act\":0,\"fid\":\"053\",\"oids\":[\"4\"]}],\"qty\":1,\"sd\":\"20161021 09:44:01\",\"cd\":\"72016102116762039687\"}";
		// byte[] ticketBytes = ticketData.getBytes();
		try {
			String data = GZIPHelper.zip(ticketData);
			System.out.println("压缩：" + data);
			// String dataString =
			// "H4sIAAAAAAAAA53UMQ7CMAwF0KugP2ewEzdpcxXUAbWAOiHUMqCqdyeVQAobfGXIYL8hP5ZXnEdkeNEk6vUgXTbLonC4zMjHFY/5Wm511ekdTsOCLKVp2rlIKOA2jTuBot/cr7BhobEwsbAloY8kDGyqoQ5H/oHsdwQ21cCmaspCz0L2jcYOgLHhNGw4TT1yVmBpuS9PZHWY35siqnxvimEvpE9FY4peQhfbhO0FDnuFqWAEAAA=";
			// data = Base64.decodeBase64(Base64.encodeBase64String(data));
			data = GZIPHelper.unZip(data);
			System.out.println("解压： " + new String(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: (zip) 
	 * @param value
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:35:26
	 */
	public static String zip(String value) throws IOException {
		if (StringUtil.isNotEmpty(value)) {
			Long startTime = System.currentTimeMillis();
			String urlString = URLEncoder.encode(value, "UTF-8");
			byte[] dataBytes = zip(urlString.getBytes());
			Long endTime = System.currentTimeMillis();
			value = Base64.encodeBase64String(dataBytes);
			logger.info("压缩时间: " + (endTime - startTime) + "ms");
		}
		return value;
	}
	
	/**
	 * @Description: (unZip) 
	 * @param value
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:35:14
	 */
	public static String unZip(String value) throws IOException {
		if (StringUtil.isNotEmpty(value)) {
			byte[] dataBytes = Base64.decodeBase64(value);
			String unzipValue = new String(unzip(dataBytes));
			unzipValue = URLDecoder.decode(unzipValue, "UTF-8");
			return unzipValue;
		}
		return value;
	}
	
	/**
	 * @Description: (zip) 
	 * @param value
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:35:01
	 */
	public static byte[] zip(byte[] value) throws IOException {
		ByteArrayOutputStream output = null;
		GZIPOutputStream zip = null;
		try {
			output = new ByteArrayOutputStream();
			zip = new GZIPOutputStream(output);
			zip.write(value, 0, value.length);
			zip.close();
			byte[] arrayOfByte = output.toByteArray();
			return arrayOfByte;
		} finally {
			if (zip != null)
				zip.close();
			if (output != null)
				output.close();
		}
	}
	
	/**
	 * @Description: (unzip) 
	 * @param value
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:34:48
	 */
	public static byte[] unzip(byte[] value) throws IOException {
		ByteArrayOutputStream output = null;
		ByteArrayInputStream input = null;
		GZIPInputStream zip = null;
		try {
			output = new ByteArrayOutputStream();
			input = new ByteArrayInputStream(value);
			zip = new GZIPInputStream(input);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = zip.read(buffer)) > 0)
				output.write(buffer, 0, read);
			byte[] arrayOfByte1 = output.toByteArray();
			return arrayOfByte1;
		} finally {
			if (zip != null)
				zip.close();
			if (output != null)
				output.close();
			if (input != null)
				input.close();
		}
	}
}