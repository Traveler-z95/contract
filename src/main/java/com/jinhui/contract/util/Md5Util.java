package com.jinhui.contract.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;


/**
 * md5加密工具类
 */
public class Md5Util {
	private static Logger logger = LoggerFactory.getLogger(Md5Util.class);

	public static final String PSWORD_SALT = "jinhui";

	public static String pwdDigest(String password){
		return digest(password+PSWORD_SALT);
	}

	private static final  String digest(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			logger.error("{}.digest方法发生异常,异常原因{}",Md5Util.class.getCanonicalName(),e.getMessage());
			return null;
		}
	}
	
}
