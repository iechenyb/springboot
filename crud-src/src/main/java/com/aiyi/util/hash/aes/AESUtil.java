package com.aiyi.util.hash.aes;

/**
 * AES加解密
 * @author 郭胜凯
 * @time 2016年5月25日上午11:29:26
 * @email 719348277@qq.com
 *
 */
public interface AESUtil {

	/**
	 * 解密
	 * @param content 等待解密内容
	 * @param password 解密密码
	 * @return 解密结果
	 */
	public byte[] decrypt(byte[] content, String password); 
	
	/**
	 * 加密
	 * @param content 等待加密内容
	 * @param password 加密密码
	 * @return 加密结果
	 */
	public byte[] encrypt(String content, String password);
	
	/**
	 * 文本解密
	 * @param content 等待解密内容
	 * @param password 解密密码
	 * @return 解密结果
	 */
	public String decryptOfString(String content, String password);
	
	/**
	 * 文本加密
	 * @param content 等待加密内容
	 * @param password 加密密码
	 * @return 加密结果
	 */
	public String encryptOfString(String content, String password);


}
