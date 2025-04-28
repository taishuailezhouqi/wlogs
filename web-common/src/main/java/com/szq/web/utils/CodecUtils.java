package com.szq.web.utils;

import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;


public class CodecUtils {

    private static final String AES_METHOD = "AES";
    private static final String AES_CIPHER = "AES/ECB/PKCS5Padding";
    public static final String ENCRYPT_KEY = "NgAbCJJGfUlQ6653";

    private CodecUtils() {
    }

    public static String aesEncode(String data, String encryptKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encryptKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, AES_METHOD);
        Cipher cipher = Cipher.getInstance(AES_CIPHER);
        byte[] byteContent = data.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);
    }

    public static String aesDecode(String data, String encryptKey) {
        try {
            byte[] aesKey = StringUtils.getBytesUtf8(encryptKey);
            SecretKeySpec key = new SecretKeySpec(aesKey, AES_METHOD);
            Cipher cipher = Cipher.getInstance(AES_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return StringUtils.newStringUtf8(cipher.doFinal(Base64.decodeBase64(data)));
        } catch (Exception e) {
            Log.sdk.error("解密失败...", e);
        }
        return null;

    }

    public static String getRequestUUID() {
        return System.currentTimeMillis() +
                "_" + UUID.randomUUID();
    }

    public static String generateUserToken(String account) {
        String seed = account + "" + CodecUtils.getRequestUUID();
        return DigestUtils.md5Hex(seed);
    }

    public static String getMySign(String data, String signKey) {
        return DigestUtils.md5Hex(data + signKey);
    }

    public static void main(String[] args) throws Exception {
        String target="eyJhbGciOiJIUzI1NiJ9.eyJjbGllbnRDb2RlIjoiMC4xMDAwNDcwMDMxMzY5ODgzNSIsImNoYW5uZWwiOiI1NTU1IiwiY3VycmVudE1pbGxTZWNvbmQiOjE3MTE2MTQzMDQ5NzQsInV1aWQiOiI0OGFmODhkZi0zYTkyLTRkNWMtYjVlMy05ODk4NDZjODlmNDIiLCJhY2NvdW50IjoieWQxNzExNjE0MzA0OTQ3IiwicGxhdGZvcm0iOjYsImlhdCI6MTcxMTYxNDMwNCwiZXhwIjoxNzEyMjE5MTA0fQ.3YAtOWE1MPzhW0ErTZ4xvd0_F0fD1xmvURJVB6cfCYE";
        String s4 = aesEncode(target, ENCRYPT_KEY);
        System.out.println("s4:<>"+s4);


        BigDecimal bigDecimal = new BigDecimal("-0.29");
        String s1 = bigDecimal.setScale(1, BigDecimal.ROUND_FLOOR).toString();
        System.out.println("s1:" + s1);
        String s2 = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).toString();
        System.out.println("s2:" + s2);


        Map<String, Object> param = Maps.newHashMap();
        param.put("phoneId", "8eb9e2a63ca549beb28c0fa26ba387d2");
        Map<String, Object> property = Maps.newHashMap();
        property.put("ro.com.cph.non_root", "0");
        param.put("property", GsonUtils.bean2JsonStr(property));
        String s3 = CodecUtils.aesEncode(GsonUtils.bean2JsonStr(param), ENCRYPT_KEY);
        System.out.println("s3," + s3);
        String s = CodecUtils.aesDecode(s3, ENCRYPT_KEY);
        System.out.println(s);


    }
}
