package com.kiiik.utils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年8月24日
 */
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;  
  
/** 
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。 
 *  
 */  
public class RSAUtil {  
      
   
    /** 
     * * 生成密钥对 * 
     *  
     * @return KeyPair * 
     * @throws EncryptException 
     */  
    public static KeyPair generateKeyPair(String rsaKeyStore) throws Exception {  
        try {  
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低  
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());  
            KeyPair keyPair = keyPairGen.generateKeyPair();  
            System.out.println(keyPair.getPrivate());  
            System.out.println(keyPair.getPublic());  
            saveKeyPair(keyPair,rsaKeyStore);  
            return keyPair;  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }  
    }  
  
    public static KeyPair getKeyPair(String rsaKeyStore) throws Exception {  
        FileInputStream fis = new FileInputStream(rsaKeyStore);  
        ObjectInputStream oos = new ObjectInputStream(fis);  
        KeyPair kp = (KeyPair) oos.readObject();  
        oos.close();  
        fis.close();  
        return kp;  
    }  
  
    public static void saveKeyPair(KeyPair kp,String rsaKeyStore) throws Exception {  
  
        FileOutputStream fos = new FileOutputStream(rsaKeyStore);  
        ObjectOutputStream oos = new ObjectOutputStream(fos);  
        // 生成密钥  
        oos.writeObject(kp);  
        oos.close();  
        fos.close();  
    }  
  
    /** 
     * * 生成公钥 * 
     *  
     * @param modulus * 
     * @param publicExponent * 
     * @return RSAPublicKey * 
     * @throws Exception 
     */  
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus,  
            byte[] publicExponent) throws Exception {  
        KeyFactory keyFac = null;  
        try {  
            keyFac = KeyFactory.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        } catch (NoSuchAlgorithmException ex) {  
            throw new Exception(ex.getMessage());  
        }  
  
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(  
                modulus), new BigInteger(publicExponent));  
        try {  
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);  
        } catch (InvalidKeySpecException ex) {  
            throw new Exception(ex.getMessage());  
        }  
    }  
  
    /** 
     * * 生成私钥 * 
     *  
     * @param modulus * 
     * @param privateExponent * 
     * @return RSAPrivateKey * 
     * @throws Exception 
     */  
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,  
            byte[] privateExponent) throws Exception {  
        KeyFactory keyFac = null;  
        try {  
            keyFac = KeyFactory.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        } catch (NoSuchAlgorithmException ex) {  
            throw new Exception(ex.getMessage());  
        }  
  
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(  
                modulus), new BigInteger(privateExponent));  
        try {  
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);  
        } catch (InvalidKeySpecException ex) {  
            throw new Exception(ex.getMessage());  
        }  
    }  
  
    /** 
     * * 加密 * 
     *  
     * @param key 
     *            加密的密钥 * 
     * @param data 
     *            待加密的明文数据 * 
     * @return 加密后的数据 * 
     * @throws Exception 
     */  
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {  
        try {  
            Cipher cipher = Cipher.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, pk);  
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024  
            // 加密块大小为127  
            // byte,加密后为128个byte;因此共有2个加密块，第一个127  
            // byte第二个为1个byte  
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小  
            int leavedSize = data.length % blockSize;  
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1  
                    : data.length / blockSize;  
            byte[] raw = new byte[outputSize * blocksSize];  
            int i = 0;  
            while (data.length - i * blockSize > 0) {  
                if (data.length - i * blockSize > blockSize)  
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i  
                            * outputSize);  
                else  
                    cipher.doFinal(data, i * blockSize, data.length - i  
                            * blockSize, raw, i * outputSize);  
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到  
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了  
                // OutputSize所以只好用dofinal方法。  
  
                i++;  
            }  
            return raw;  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }  
    }  
  
    /** 
     * * 解密 * 
     *  
     * @param key 
     *            解密的密钥 * 
     * @param raw 
     *            已经加密的数据 * 
     * @return 解密后的明文 * 
     * @throws Exception 
     */  
    @SuppressWarnings("static-access")
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {  
        try {  
            Cipher cipher = Cipher.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            cipher.init(cipher.DECRYPT_MODE, pk);  
            int blockSize = cipher.getBlockSize();  
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);  
            int j = 0;  
  
            while (raw.length - j * blockSize > 0) {  
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));  
                j++;  
            }  
            return bout.toByteArray();  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }  
    }  
  
    /** 
     * * * 
     *  
     * @param args * 
     * @throws Exception 
     * 
     */  
    public static void main(String[] args) throws Exception {  
    	String RSAKeyStore = "d:/data/rsa/RSAKey.txt";  
    	String rsaKeyStore = RSAKeyStore;
    	System.out.println(RSAUtil.getKeyPair(rsaKeyStore)
    			.getPublic()
    			.toString()
    			.replace("\n", "#")
    			.split("#")[2].trim()
    			.split(":")[1].trim()
    			/*.replace("\n", "#")
    			.split("#")[1].trim()
    			.split(":")[1].trim()*/);
    	System.out.println(RSAUtil.getKeyPair(rsaKeyStore)
    			.getPublic()
    			.toString()
    			.replace("\n", "#")
    			.split("#")[1].trim()
    			.split(":")[1].trim());
       // decRequest(rsaKeyStore);
    }  
    /**
     * 32132!1321321gfedcba
     * @ #不行
     *作者 : iechenyb<br>
     *方法描述: 说点啥<br>
     *创建时间: 2017年7月15日
     *@param rsaKeyStore
     *@throws Exception
     */
    public static void decRequest(String rsaKeyStore) throws Exception{
    	String encodeStr = "139f380f832c8efb205df7761c6899facd097a4f4af65f198c8df718478d3c87dbeddd41c23c2b93af5e64bc80b2c6b92c29b5e2f47637baed235ccd6ad51a1d28c2cc9f2650860ccf3d2148fdf8bb83b55b8da87fbe5db1b8985b560a907d470c9de72dd15c35c88db44614e0e438036792e1865855bf1e98c2f81f9343ca74";
    	byte[] en_result = new BigInteger(encodeStr, 16).toByteArray();
    	byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(rsaKeyStore).getPrivate(),
				en_result);
    	//倒叙输出123456789-> 987654321
    	System.out.println("将请求进行解密:"+new String(de_result));
    }
}  