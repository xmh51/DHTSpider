package me.hncn.dhtspider.util;

import java.util.Random;

/**
 * Created by XMH on 2016/6/10.
 */
public class RandomUtil {
    private static String chars;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            tmp.append(ch);

        // 添加一些特殊字符
        tmp.append("!@#$%");
        chars = tmp.toString();
    }

    private final Random random = new Random();


    public static String getRandom(int length) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(chars.charAt((int) (Math.random() * (26 + 26 + 10 + 5))));
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandom(21));
    }
}
