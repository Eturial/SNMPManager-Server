package com.eturial.SNMPManager.utils;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public class ChangeUtils {
    public static StringBuilder byteArrayToHexString(byte[] array) {
        StringBuilder stringRes = new StringBuilder();
        for (byte v: array) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            String temp = Integer.toHexString(0xFF & v);
            if (temp.length() == 1) {// 每个字节8位，转为16进制标志，2个16进制位
                temp = "0" + temp;
            }
            temp += " ";
            stringRes.append(temp);
        }
        System.out.println(stringRes.toString());
        return stringRes;
    }

    /**
     * 将int型整数转换为字节数组
     *
     * @param number int整数
     * @return 字节数组
     */
    public static byte[] intToBytes(int number) {
        int n = 0;
        byte[] result;
        if (number <= 255 && number >= 0) {
            return new byte[]{(byte) (number)};
        } else if (number <= 65535 && number >= 256) {
            result = new byte[2];
            for (int i = 1; i >= 0; i--) {
                result[i] = (byte) (number >> n);
                n = n + 8;
            }
            return result;
        } else if (number <= 16777215 && number >= 65536) {
            result = new byte[3];
            for (int i = 2; i >= 0; i--) {
                result[i] = (byte) (number >> n);
                n = n + 8;
            }
            return result;
        } else if (number >= 16777216) {
            result = new byte[4];
            for (int i = 3; i >= 0; i--) {
                result[i] = (byte) (number >> n);
                n = n + 8;
            }
            return result;
        }
        return new byte[1];
    }
}
