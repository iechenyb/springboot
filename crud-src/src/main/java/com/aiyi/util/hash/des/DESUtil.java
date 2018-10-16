package com.aiyi.util.hash.des;

/**
 * DES加解密算法操作类
 * @author AnLuTong
 *
 */
public interface DESUtil {
 
    /**
     * 功能：加密 (UTF-8)
     * 
     * @author 宋立君
     * @date 2014年07月03日
     * @param source
     *      源字符串
     * @param charSet
     *      编码
     * @return String
     * @throws UnsupportedEncodingException
     *       编码异常
     */
    public String encrypt(String source);
 
    /**
     * 
     * 功能：解密 (UTF-8)
     * 
     * @author 宋立君
     * @date 2014年07月03日
     * @param encryptedData
     *      被加密后的字符串
     * @return String
     * @throws UnsupportedEncodingException
     *       编码异常
     */
    public String decrypt(String encryptedData);
 
    /**
     * 功能：加密
     * 
     * @author 宋立君
     * @date 2014年07月03日
     * @param source
     *      源字符串
     * @param charSet
     *      编码
     * @return String
     * @throws UnsupportedEncodingException
     *       编码异常
     */
    public String encrypt(String source, String charSet);
 
    /**
     * 
     * 功能：解密
     * 
     * @author 宋立君
     * @date 2014年07月03日
     * @param encryptedData
     *      被加密后的字符串
     * @param charSet
     *      编码
     * @return String
     * @throws UnsupportedEncodingException
     *       编码异常
     */
    public String decrypt(String encryptedData, String charSet);
 
 

}
