package com.architecture.spring.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES256 암호화 관련 처리를 하는 class
 * @author JENNI
 * @version 1.0
 * @since 2022.02.06
 */

public class AES256 {
    // 인코딩 타입
    private static String encoding_type = "UTF-8";
    // 암호화 모드(Algorithm: AES, Block Cipher Mode: CBC, Padding: PKCS5)
    private static String transformation = "AES/CBC/PKCS5Padding";
    // 암호화 알고리즘
    private static String algorithm = "AES";
    // 암호화 키(임시로 하드 코딩)
    private final String key = "01r3dw55489adjr7123dffs9879f1qr4";
    // Initialization vector(16byte)
    private final String iv = key.substring(0, 16);

    // 암호화
    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(encoding_type));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 복호화
    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, encoding_type);
    }
}
