package me.hncn.dhtspider.util;

/**
 * Created by XMH on 2016/6/12.
 */
public class RadixUtil {
    /**
     * 2进制转16进制
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString)
    {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4)
        {
            iTmp = 0;
            for (int j = 0; j < 4; j++)
            {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * 16进制转2进制
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString)
    {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++)
        {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }
}
