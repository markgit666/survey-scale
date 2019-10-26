package com.yinxt.surveyscale.common.util;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA工具类
 *
 * @author yinxt
 * @version 1.0
 * @date 2019-10-26 14:24
 */
public class RSAUtil {

    private static final String CIPHER_TYPE = "RSA";
    private static final String CHARACTER_TYPE = "UTF-8";

    /**
     * rsa加密
     *
     * @param target    明文
     * @param publicKey BASE64编码的密钥（公钥）
     * @return
     * @throws Exception
     */
    public static String encrypt(String target, String publicKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] publicKeyByte = decoder.decode(publicKey);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance(CIPHER_TYPE).generatePrivate(new X509EncodedKeySpec(publicKeyByte));
        Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        Base64.Encoder encoder = Base64.getEncoder();
        String cipherText = encoder.encodeToString(cipher.doFinal(target.getBytes(CHARACTER_TYPE)));
        return cipherText;
    }

    /**
     * rsa解密
     *
     * @param target
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String target, String privateKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] targetByte = decoder.decode(target.getBytes(CHARACTER_TYPE));
        byte[] privateKeyByte = decoder.decode(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance(CIPHER_TYPE).generatePrivate(new PKCS8EncodedKeySpec(privateKeyByte));
        Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        String plainText = new String(cipher.doFinal(targetByte));
        return plainText;
    }
}
