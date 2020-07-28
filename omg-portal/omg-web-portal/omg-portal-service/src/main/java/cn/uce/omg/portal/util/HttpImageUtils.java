package cn.uce.omg.portal.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpImageUtils {
	public static String URL_PATH = "http://www.uc56.com/page/images/about_img_01.jpg";
	 
	public HttpImageUtils() {
		
	}
 
	//把从服务器获得图片的输入流InputStream写到本地磁盘
	public static void saveImageToDisk() {
		InputStream inputStream = getInputStream();
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("D:\\test1.jpg");
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
 
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
 
		}
 
	}
 
	// 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
	public static InputStream getInputStream() {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
 
		try {
			URL url = new URL(URL_PATH);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(3000);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);
 
			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();
 
			}
 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		return inputStream;
 
	}
 
	public static void main(String args[]) throws IOException {
		// 从服务器端获得图片，保存到本地
		saveImageToDisk();
/*		InputStream inputStream = getInputStream();
		MultipartFile multipartFile = new MockMultipartFile("temp.jpg","temp.jpg","", inputStream);*/
	}

}
