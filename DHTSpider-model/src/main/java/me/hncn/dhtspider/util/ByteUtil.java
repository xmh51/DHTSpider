package me.hncn.dhtspider.util;

import java.util.Random;

/**
 * Created by XMH on 2016/6/19.
 */
public class ByteUtil {
    public static byte getRanDomByte(){
        Random rand = new Random();
        int i = rand.nextInt(256); //生成0-256以内的随机数
        if(i>127){
            i = i-256;
        }
        return (byte) i;
    }

    public static byte[] getRanDomBytes(int length){
        byte[] bytes = new byte[length];
        for(int i =0;i<bytes.length;i++){
            bytes[i] = getRanDomByte();
        }
        return bytes;
    }

    public static byte[] BytesTobytes(Byte[] bytes){
        byte[] result =  new byte[bytes.length];
        for(int i = 0;i<bytes.length;i++){
            result[i]  = bytes[i];
        }
        return result;
    }

    public static Byte[] bytesToBytes(byte[] bytes){
        Byte[] result =  new Byte[bytes.length];
        for(int i = 0;i<bytes.length;i++){
            result[i]  = bytes[i];
        }
        return result;
    }

    public static byte[] intToByteArray (final int integer) {
        int byteNum = (40 - Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer)) / 8;
        byte[] byteArray = new byte[4];

        for (int n = 0; n < byteNum; n++)
            byteArray[3 - n] = (byte) (integer >>> (n * 8));

        return (byteArray);
    }

    /**
     * 从小到大取值
     * @param num
     * @param length
     * @return
     */
    public static byte[] intToByteArray (final int num,int length) {
        byte[] byteArray  = intToByteArray(num);
        byte[] result = new byte[length];
        for(int i = 0;i<length;i++){
            result[i] = byteArray[4-i-1];
        }
        return result;
    }

    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
}
