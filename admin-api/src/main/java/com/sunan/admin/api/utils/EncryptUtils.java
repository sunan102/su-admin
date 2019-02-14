package com.sunan.admin.api.utils;


import java.security.MessageDigest;

/**
 * Created by Administrator on 2017/11/16.
 */
public class EncryptUtils {
    public static void main(String[] args) {
//        System.out.println(encodeByMD5("1"));
        String tel = "13116151240";
        tel=tel.replaceAll("(\\d{4})?\\d{4}(.*)","$1****$2");
        System.out.println(tel);
    }


    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 对字符串进行MD5编码
     *
     * @param originString
     * @return
     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(originString.getBytes());
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 十六进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成16进制形式的字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
