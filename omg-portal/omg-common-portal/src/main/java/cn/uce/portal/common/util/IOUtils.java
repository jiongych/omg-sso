package cn.uce.portal.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @Description: aili框架核心处理和基本支持
 * @author zhangd
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class IOUtils {
	private static Log log = LogFactory.getLog(IOUtils.class);

	private IOUtils() {
	}

	public static void copy(InputStream input, OutputStream output) throws IOException {
		copy(input, output, false);
	}
	
	public static void writeFile(InputStream input, OutputStream output) throws IOException {
		copy(input, output, false);
	}

	public static void copy(InputStream input, OutputStream output, boolean isClose) throws IOException {
		int len = 0;
		byte[] buffer = new byte[8192];
		try {
			while ((len = input.read(buffer, 0, 8192)) != -1) {
				output.write(buffer, 0, len);
			}
			output.flush();
		} finally {
			if (isClose) {
				// 使用公用代码
				close(input);
				close(output);
			}
		}
	}

	public static void close(Closeable resource) {
		try {
			if (resource != null) {
				resource.close();
			}
		} catch (IOException exp) {
			log.error(exp.getMessage());
		}
	}
	public static boolean deleteFile(File fileToDelete) {
		if ((fileToDelete == null) || (!fileToDelete.exists())) {
			return true;
		}
		boolean result = deleteChildren(fileToDelete);
		result &= fileToDelete.delete();
		return result;
	}

	public static boolean deleteChildren(File parent) {
		if ((parent == null) || (!parent.exists())) {
			return false;
		}
		boolean result = true;
		if (parent.isDirectory()) {
			File[] files = parent.listFiles();
			if (files == null)
				result = false;
			else {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if ((!file.getName().equals("."))
							&& (!file.getName().equals(".."))) {
						if (file.isDirectory())
							result &= deleteFile(file);
						else {
							result &= file.delete();
						}
					}
				}
			}
		}
		return result;
	}

	public static void moveFile(File src, File targetDirectory)
			throws IOException {
		if (!src.renameTo(new File(targetDirectory, src.getName())))
			throw new IOException("Failed to move " + src + " to "
					+ targetDirectory);
	}

	public static void copyFile(File src, File dest) throws IOException {
		FileInputStream fileSrc = new FileInputStream(src);
		FileOutputStream fileDest = new FileOutputStream(dest);
		copyInputStream(fileSrc, fileDest,4096);
	}
	public static void copyFile(File src, File dest,int buffer) throws IOException {
		FileInputStream fileSrc = new FileInputStream(src);
		FileOutputStream fileDest = new FileOutputStream(dest);
		copyInputStream(fileSrc, fileDest,buffer);
	}
	public static void copyInputStream(InputStream in, OutputStream out,int buffer)
			throws IOException {
		byte[] bts = new byte[buffer];
		int len = in.read(bts);
		while (len >= 0) {
			out.write(bts, 0, len);
			len = in.read(bts);
		}
		in.close();
		out.close();
	}

	public static void mkdirs(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				throw new IOException("Failed to create directory '" + dir
						+ "', regular file already existed with that name");
			}

		} else if (!dir.mkdirs())
			throw new IOException("Failed to create directory '" + dir + "'");
	}
}
