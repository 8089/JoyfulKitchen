package com.joyful.joyfulkitchen.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Util {
	/**
	 * 给指定字符串按照md5算法加密
	 * @param psd 需要加密的密码 加盐处理
	 * @return md5 后的字符串
	 */
	public static String encoder(String psd) {
		//1 指定加密算法类型
		try {
			// 加盐处理
			psd = psd + "jkichen";
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// 2.将需要加密的字符串中转换成byte类型的数组，然后进行随机哈希过程
			byte[] bs = digest.digest(psd.getBytes());
			//System.out.println(bs.length);
			// 3.循环遍历bs，然后让其生成 32位字符串，固定写法
			// 4,拼接字符串过程
			StringBuffer sb = new StringBuffer();
			for(byte b: bs) {
				int i = b & 0xff;
				// int类型的i 需要转换成 16 机制字符
				String hexString = Integer.toHexString(i);
//				System.out.println(hexString);
				if(hexString.length()<2) {
					hexString = "0"+hexString;
				}

				sb.append(hexString);
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

}
