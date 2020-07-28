/** 
 * @项目名称: FSP
 * @文件名称: FileUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件工具类
 * @author tanchong
 *
 */
public class FileUtil {
	protected static Log LOG = LogFactory.getLog(FileUtil.class);
	/**
	 * 写入txt文件，可以在原文件内容的基础上追加内容(并判断目录是否存在，不存在则生成目录)
	 * 
	 * @param value 写入文件内容
	 * @param fileCatage 文件父目录；
	 * @param fileName 文件名字；
	 * @param code 文件的编码；
	 * @throws IOException
	 */
	public static void writeFile(String value, String fileCatage, String fileName, String code) {
		File file = null;
		FileOutputStream out = null;
		try {
			file = new File(fileCatage);
			if (!file.isDirectory())
				file.mkdir();
			else {
				file = new File(fileCatage + fileName);
				if (!file.exists())
					file.createNewFile();
				out = new FileOutputStream(file, true);
				out.write(value.getBytes(code));
//				out.close();
			}
		} catch (IOException e) {
			LOG.error("writeFile error", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.warn("writeFile close error", e);
				}
			}
		}
	}

	/***
	 * 覆盖原来的内容；
	 * 
	 * @param filePath 文件的路径
	 * @param content 保存的内容；
	 * @return
	 */
	public static boolean saveFile(String filePath, String content) {
		boolean successful = true;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(filePath), false);
			fout.write(content.getBytes());
		} catch (FileNotFoundException e1) {
			successful = false;
			LOG.error("saveFile error", e1);
		} catch (IOException e) {
			successful = false;
			LOG.error("saveFile error", e);
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					LOG.error("saveFile error", e);
				}
			}
		}
		return successful;
	}

	/**
	 * 删除文件的综合操作( 根据路径删除指定的目录或文件，无论存在与否)
	 * 
	 * @param sPath 要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				// 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 将流中的文本读入一个 BufferedReader 中
	 * 
	 * @param filePath 文件路径
	 * @param code 编码格式
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader readToBufferedReader(String filePath, String code) throws IOException {
		BufferedReader bufferedReader = null;
		File file = new File(filePath);
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), code));
		}
		return bufferedReader;
	}

	/**
	 * 将流中的文本读入一个 StringBuffer 中
	 * 
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	public static StringBuffer readToBuffer(String filePath, String code) {
		StringBuffer buffer = new StringBuffer();
		InputStream is = null;
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			if (!file.exists())
				return null;
			is = new FileInputStream(filePath);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), code));
			// 用来保存每行读取的内容
			String line = reader.readLine(); // 读取第一行
			while (line != null) { // 如果 line 为空说明读完了
				buffer.append(line); // 将读到的内容添加到 buffer 中
				// buffer.append("\n"); // 添加换行符
				line = reader.readLine(); // 读取下一行
			}
		} catch (Exception e) {
			LOG.error("readToBuffer error", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOG.error("readToBuffer reader close error", e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOG.error("readToBuffer Stream close error", e);
				}
			}
		}
		return buffer;
	}

	/**
	 * 加载文件
	 * @param filePath 文件路径
	 * @param charset 编码
	 * @return
	 */
	public static String loadFile(String filePath, String charset) {
		FileInputStream fin = null;
		StringBuffer sb = new StringBuffer();
		try {
			fin = new FileInputStream(new File(filePath));
			byte[] buffer = new byte[Integer.MAX_VALUE];
			int start = -1;
			while ((start = fin.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, start, charset));
			}
		} catch (Exception e) {
			LOG.error("loadFile error", e);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					LOG.error("loadFile error", e);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取某个目录下所有文件或者获取某个文件的大小； 单位：MB
	 * 
	 * @param file
	 * @return
	 */
	public static double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小,以“兆”为单位
				double size = (double) file.length() / 1024 / 1024;
				return size;
			}
		} else {
			System.out.println("获取文件大小错误！！文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}

	/**
	 * 获取某个目录下所有的文件的全路径和文件名的集合；
	 * 
	 * @return
	 */
	public static List<List<String>> getAllFile(String mulu) {
		File file = new File(mulu);
		File[] files = file.listFiles();
		List<List<String>> ret = new ArrayList<List<String>>();
		List<String> allFilePath = new ArrayList<String>();
		List<String> allFileName = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				allFilePath.add(files[i].toString());
				allFileName.add(files[i].getName());
			}
		}
		ret.add(allFilePath);
		ret.add(allFileName);
		return ret;
	}
}
