package com.eturial.SNMPManager.utils;

import java.util.ArrayList;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public class MergeUtils {
    public static byte[] tlvMerge(byte[] t, byte[] l, byte[] v) {
        byte[] res = new byte[t.length + l.length + v.length];
        System.arraycopy(t, 0, res, 0, t.length);
        System.arraycopy(l, 0, res, t.length, l.length);
        System.arraycopy(v, 0, res, t.length + l.length, v.length);

        return res;
    }

    public static byte[] tlvMerge(byte[] t, byte[] l, ArrayList<Byte> v) {
        byte[] res = new byte[v.size() + l.length + t.length];
        System.arraycopy(t, 0, res, 0, t.length);
        System.arraycopy(l, 0, res, t.length, l.length);
        int k = 0;
        for(int i = t.length + l.length; i < res.length; i++)
            res[i] = v.get(k++);

        return res;
    }

    public static byte[] lengthMerge(byte[] lengthHead, byte[] l) {
        byte[] res = new byte[lengthHead.length + l.length];
        System.arraycopy(lengthHead, 0, res, 0, lengthHead.length);
        System.arraycopy(l, 0, res, lengthHead.length, l.length);

        return res;
    }
}
