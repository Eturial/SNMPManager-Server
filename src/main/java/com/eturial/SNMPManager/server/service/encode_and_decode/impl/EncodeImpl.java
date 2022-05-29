package com.eturial.SNMPManager.server.service.encode_and_decode.impl;

import com.eturial.SNMPManager.server.entity.dataparams.Variable;
import com.eturial.SNMPManager.server.entity.dataparams.VariableBindings;
import com.eturial.SNMPManager.server.service.encode_and_decode.Encode;
import com.eturial.SNMPManager.utils.ChangeUtils;
import com.eturial.SNMPManager.utils.MergeUtils;

import java.util.ArrayList;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public class EncodeImpl implements Encode {

    @Override
    public byte[] getLengthCode(int length) {
        byte[] lengthByte = ChangeUtils.intToBytes(length);
        if(length >= 127) {
           byte[] lengthHead = ChangeUtils.intToBytes(lengthByte.length);
           lengthHead[0] |= (0x80);
           lengthByte = MergeUtils.lengthMerge(lengthHead, lengthByte);
        }
        return lengthByte;
    }

    @Override  // 编码OID  T OBJECT IDENTIFIER
    public byte[] getOIDCode(String objectID) {
        String[] oID = objectID.split("\\.");
        // 1.3  组合编码，使用公式：(X*40)+Y
        ArrayList<Byte> list = new ArrayList<>();
        list.add((byte) (Byte.parseByte(oID[0]) * 40 + Byte.parseByte(oID[1])));

        int[] rest = new int[oID.length - 2];
        
        byte[] temp = new byte[0];
        for (int i = 0; i < rest.length; i++) {
            rest[i] = Integer.parseInt(oID[i + 2]);
            if(rest[i] > 255) {
                temp = ChangeUtils.intToBytes(rest[i]);
                for(int j = 0; j < temp.length; j++)
                    list.add(temp[j]);
            }
            if(temp.length == 0)
                list.add((byte)rest[i]);
        }

        // 长度length
        byte[] lengthByte = getLengthCode(list.size());
        //  T  UNIVERSAL 6 OBJECT IDENTIFIER
        byte[] typeByte = ChangeUtils.intToBytes(6);

        return MergeUtils.tlvMerge(typeByte, lengthByte, list);
    }

    @Override
    public byte[] getNullCode() {
        return new byte[]{5, 0};
    }

    @Override
    public byte[] getStrCode(String str) {
        //  V
        char[] temp = str.toCharArray();
        byte[] valueByte = new byte[temp.length];
        for (int i = 0; i < valueByte.length; i++) {
            valueByte[i] = (byte) temp[i];
        }
        //  L
        byte[] lengthByte = getLengthCode(valueByte.length);
        //  T  UNIVERSAL 4 OCTET STRING
        byte[] typeByte = ChangeUtils.intToBytes(4);

        return MergeUtils.tlvMerge(typeByte, lengthByte, valueByte);
    }

    @Override
    public byte[] getIntegerCoding(String str) {
        // V
        byte[] valueByte = ChangeUtils.intToBytes(Integer.parseInt(str));
        // L
        byte[] lengthByte = getLengthCode(valueByte.length);
        //  T  UNIVERSAL 2 INTEGER
        byte[] typeByte = ChangeUtils.intToBytes(2);

        return MergeUtils.tlvMerge(typeByte, lengthByte, valueByte);
    }

    @Override
    public byte[] getVariableBindingsCode(VariableBindings variableBindings) {
        ArrayList<Variable> variableArrayList = variableBindings.getVariableList();
        for(Variable variable : variableArrayList) {
            // V
            byte[] oID = getOIDCode(variable.getName());
            byte[] value = getStrCode(variable.getValue());
            // L
            byte[] lengthByte;

            //  T  UNIVERSAL 16 SEQUENCE/SEQUENCE OF 序列
            byte[] typeByte = ChangeUtils.intToBytes(16);
        }
        return null;
    }
}
