package springboot.project.utils;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {
    private static String keyStr = "b62a5237-7eb7-4b35-aa862809503ca";
    private static String ivStr = "1234567890123456";

    private static SecretKey key = new SecretKeySpec(keyStr.getBytes(StandardCharsets.UTF_8), "AES");
    private static IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes(StandardCharsets.UTF_8));

    @SneakyThrows
    public static String encrypt (String rawData) {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] encryptedBytes = cipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8));
        String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Encrypted: " + encryptedData);
        return encryptedData;
    }

    @SneakyThrows
    public static String decrypt(String encryptData) {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);

        System.out.println("Decrypted: " + decryptedData);
        return  decryptedData;
    }

}
