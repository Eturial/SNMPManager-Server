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
        return stringRes;
    }

    /**
     * 将int型整数转换为字节数组
     *
     * @param num int整数
     * @return 字节数组
     */
    public static byte[] intToBytes(int num) {
        int n = 0;
        byte[] result;
        if (num <= 255 && num >= 0) {
            return new byte[]{(byte) (num)};
        } else if (num <= 65535 && num >= 256) {
            result = new byte[2];
            for (int i = 1; i >= 0; i--) {
                result[i] = (byte) (num >> n);
                n = n + 8;
            }
            return result;
        } else if (num <= 16777215 && num >= 65536) {
            result = new byte[3];
            for (int i = 2; i >= 0; i--) {
                result[i] = (byte) (num >> n);
                n = n + 8;
            }
            return result;
        } else if (num >= 16777216) {
            result = new byte[4];
            for (int i = 3; i >= 0; i--) {
                result[i] = (byte) (num >> n);
                n = n + 8;
            }
            return result;
        }
        return null;
    }

    public static int bytesToInt(byte[] data) {
        byte[] bytes = {0, 0, 0, 0};
        System.arraycopy(data, 0, bytes, (4 - data.length), data.length);
        return bytes[0] << 24 | (bytes[1] & 0xff) << 16 | (bytes[2] & 0xff) << 8 | (bytes[3] & 0xff);
    }

    /**
     * 将字节数组转换为十进制数组
     *
     * @param bytes 字节数组
     * @return 返回转换后的十进制数组
     */
    public static int[] byteToDec(byte[] bytes) {
        int[] dec = new int[bytes.length];
        int i = 0;
        for (byte b : bytes) {
            dec[i++] = b & 0xff;
        }
        return dec;
    }
}
