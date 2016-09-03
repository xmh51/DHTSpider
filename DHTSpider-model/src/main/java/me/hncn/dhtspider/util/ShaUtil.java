package me.hncn.dhtspider.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by XMH on 2016/6/12.
 */
public class ShaUtil {
    /**
     * 获得 byte 非 补码形式的  以 Cp1252 编码的字符串
     * @param str  str 待 sha1 字符串
     * @return byte 非 补码形式的  以 Cp1252 编码的字符串
     */
    public static String sha1Cp1252(String str) {


        byte bytes[] = DigestUtils.sha1(str);
        try {
            return new String(bytes, "Cp1252");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 获得 byte 非 补码形式的  以 Cp1252 编码的字符串
     * @param str  str 待 sha1 字符串
     * @return byte 非 补码形式的  以 Cp1252 编码的字符串
     */
    public static byte[] sha1Byte(String str) {
        return DigestUtils.sha1(str);

    }
    /**
     * 获得 16位 sha1 摘要
     * @param bytes  待 sha1 bytes
     * @return sha1 后的16进制字符串
     */
    public static String sha1Hex(byte bytes[]) {

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                digest.update(bytes);
                byte messageDigest[] = digest.digest();

                return byteToHexString(messageDigest);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        return "";
    }
    /**
     * 获得 16位 sha1 摘要
     * @param str 待 sha1 字符串
     * @return sha1 后的16进制字符串
     */
    public static String sha1Hex(String str) {

        try {
            return sha1Hex(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 以cp1252编码的字符串 转成  16进制字符串
     * @param str cp1252编码的字符串
     * @return 16进制字符串
     */
    public static String cp1252ToHex(String str) throws UnsupportedEncodingException {
        byte bytes[] = str.getBytes("Cp1252");
        return byteToHexString(bytes);
    }

    public static String byteToHexString(byte bytes[]){
        StringBuilder sb = new StringBuilder();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < bytes.length; i++) {
            String shaHex = Integer.toHexString(bytes[i] & 0xFF);
            if (shaHex.length() < 2) {
                sb.append(0);
            }
            sb.append(shaHex);
        }
        return sb.toString().toUpperCase();
    }

    /**
     *  16进制字符串 转  Cp1252 编码 字符串
     * @param str  16进制字符串
     * @return Cp1252 编码 字符串
     */
    public static String hexToCp1252(String str) throws UnsupportedEncodingException {
        byte bytes[] = hexStringToBytes(str);
        return new String(bytes, "Cp1252");

    }

    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String aa = sha1Cp1252("aaaa");
        String bb = hexToCp1252(sha1Hex("aaaa"));
        System.out.println(aa.equals(bb));
        System.out.println();
        System.out.println(cp1252ToHex(aa));
        System.out.println(sha1Hex("aaaa"));
    }

}
