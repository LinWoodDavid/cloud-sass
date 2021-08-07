package com.weimengchao.common.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * =================================
 * Created by David on 2018/11/9.
 * mail:    17610897521@163.com
 * 描述:      RSA 加密工具类
 */
@Slf4j
public class RSAUtils {

    //非对称密钥算法
    private final String KEY_ALGORITHM = "RSA";

    /**
     * 生成长度为2048的密钥对
     *
     * @return keySize 密钥长度   推荐使用1024,2048 ，不允许低于1024
     * @throws NoSuchAlgorithmException
     */
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(2048);
    }

    /**
     * 生成密钥对
     *
     * @param keySize 密钥长度   推荐使用1024,2048 ，不允许低于1024
     * @return KeyPair
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        if (keySize < 1024) keySize = 2048;
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥长度
        keyPairGenerator.initialize(keySize);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 获取公钥字符串(Base64编码)
     *
     * @param keyPair 密钥对
     * @return PublicKey
     */
    public String publicKeyString(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return encodeByBase64(bytes);
    }

    /**
     * 获取公钥对象
     *
     * @param key key
     * @return PublicKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PublicKey publicKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        return keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 获取公钥    将Base64编码后的公钥转换成PublicKey对象
     *
     * @param pubStr 公钥字符串
     * @return
     * @throws Exception
     */
    public PublicKey publicKey(String pubStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = decodeByBase64(pubStr);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        //生成公钥
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 获取私钥(Base64编码)
     *
     * @param keyPair 密钥对
     * @return
     */
    public String privateKeyString(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return encodeByBase64(bytes);
    }

    /**
     * 获得私钥    将Base64编码后的私钥转换成PrivateKey对象
     *
     * @param priStr
     * @return
     * @throws Exception
     */
    public PrivateKey privateKey(String priStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = decodeByBase64(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 获得私钥
     *
     * @param key 私钥字节数组
     * @return
     * @throws Exception
     */
    public PrivateKey privateKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     *
     * @param content   加密内容
     * @param publicKey 公钥
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] publicKeyEncrypt(byte[] content, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    /**
     * 公钥解密
     *
     * @param content   解密内容
     * @param publicKey 公钥
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] publicKeyDecrypt(byte[] content, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    /**
     * 私钥加密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] privateKeyEncrypt(byte[] content, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    /**
     * 私钥解密
     *
     * @param content    要加密的内容
     * @param privateKey 私钥
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] privateKeyDecrypt(byte[] content, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    /**
     * 私钥解密
     *
     * @param cipherStr  密文字符串
     * @param privateKey 私钥
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public String privateKeyDecrypt(String cipherStr, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(decodeByBase64(cipherStr));
        return new String(bytes);
    }

    /**
     * 字节数组转Base64编码
     *
     * @param bytes
     * @return
     */
    public static String encodeByBase64(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * Base64编码转字节数组
     *
     * @param base64Key
     * @return
     * @throws IOException
     */
    public static byte[] decodeByBase64(String base64Key) throws IOException {
        return Base64Utils.decodeFromString(base64Key);
    }

    /**
     * RSA 公钥加密
     *
     * @param plaintext 明文/待加密
     * @param publicKey 公钥
     * @return cipher   密文
     * @throws NoSuchAlgorithmException  加密算法错误
     * @throws IOException               io异常
     * @throws InvalidKeySpecException   密钥格式异常
     * @throws IllegalBlockSizeException 非法块大小异常
     * @throws InvalidKeyException       密钥无效
     * @throws BadPaddingException       填充异常
     * @throws NoSuchPaddingException    没有找到填充异常
     */
    public String publicKeyEncrypt(String plaintext, PublicKey publicKey) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        byte[] bytes = publicKeyEncrypt(plaintext.getBytes(), publicKey);
        return encodeByBase64(bytes);
    }

}
