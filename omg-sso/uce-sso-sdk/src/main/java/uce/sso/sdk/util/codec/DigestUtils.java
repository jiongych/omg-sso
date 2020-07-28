/** 
 * @项目名称: FSP
 * @文件名称: DigestUtils 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.util.codec;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import uce.sso.sdk.util.StringUtils;
import uce.sso.sdk.util.binary.Hex;

public class DigestUtils {

	private static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
		byte[] buffer = new byte[1024];
		int read = data.read(buffer, 0, 1024);

		while (read > -1) {
			digest.update(buffer, 0, read);
			read = data.read(buffer, 0, 1024);
		}

		return digest.digest();
	}

	private static byte[] getBytesUtf8(String data) {
		return StringUtils.getBytesUtf8(data);
	}

	static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	private static MessageDigest getSha256Digest() {
		return getDigest("SHA-256");
	}

	private static MessageDigest getSha384Digest() {
		return getDigest("SHA-384");
	}

	private static MessageDigest getSha512Digest() {
		return getDigest("SHA-512");
	}

	private static MessageDigest getShaDigest() {
		return getDigest("SHA");
	}

	public static byte[] md5(byte[] data) {
		return getMd5Digest().digest(data);
	}

	public static byte[] md5(InputStream data) throws IOException {
		return digest(getMd5Digest(), data);
	}

	public static byte[] md5(String data) {
		return md5(getBytesUtf8(data));
	}

	public static String md5Hex(byte[] data) {
		return Hex.encodeHexString(md5(data));
	}

	public static String md5Hex(InputStream data) throws IOException {
		return Hex.encodeHexString(md5(data));
	}

	public static String md5Hex(String data) {
		return Hex.encodeHexString(md5(data));
	}

	public static byte[] sha(byte[] data) {
		return getShaDigest().digest(data);
	}

	public static byte[] sha(InputStream data) throws IOException {
		return digest(getShaDigest(), data);
	}

	public static byte[] sha(String data) {
		return sha(getBytesUtf8(data));
	}

	public static byte[] sha256(byte[] data) {
		return getSha256Digest().digest(data);
	}

	public static byte[] sha256(InputStream data) throws IOException {
		return digest(getSha256Digest(), data);
	}

	public static byte[] sha256(String data) {
		return sha256(getBytesUtf8(data));
	}

	public static String sha256Hex(byte[] data) {
		return Hex.encodeHexString(sha256(data));
	}

	public static String sha256Hex(InputStream data) throws IOException {
		return Hex.encodeHexString(sha256(data));
	}

	public static String sha256Hex(String data) {
		return Hex.encodeHexString(sha256(data));
	}

	public static byte[] sha384(byte[] data) {
		return getSha384Digest().digest(data);
	}

	public static byte[] sha384(InputStream data) throws IOException {
		return digest(getSha384Digest(), data);
	}

	public static byte[] sha384(String data) {
		return sha384(getBytesUtf8(data));
	}

	public static String sha384Hex(byte[] data) {
		return Hex.encodeHexString(sha384(data));
	}

	public static String sha384Hex(InputStream data) throws IOException {
		return Hex.encodeHexString(sha384(data));
	}

	public static String sha384Hex(String data) {
		return Hex.encodeHexString(sha384(data));
	}

	public static byte[] sha512(byte[] data) {
		return getSha512Digest().digest(data);
	}

	public static byte[] sha512(InputStream data) throws IOException {
		return digest(getSha512Digest(), data);
	}

	public static byte[] sha512(String data) {
		return sha512(getBytesUtf8(data));
	}

	public static String sha512Hex(byte[] data) {
		return Hex.encodeHexString(sha512(data));
	}

	public static String sha512Hex(InputStream data) throws IOException {
		return Hex.encodeHexString(sha512(data));
	}

	public static String sha512Hex(String data) {
		return Hex.encodeHexString(sha512(data));
	}

	public static String shaHex(byte[] data) {
		return Hex.encodeHexString(sha(data));
	}

	public static String shaHex(InputStream data) throws IOException {
		return Hex.encodeHexString(sha(data));
	}

	public static String shaHex(String data) {
		return Hex.encodeHexString(sha(data));
	}
}