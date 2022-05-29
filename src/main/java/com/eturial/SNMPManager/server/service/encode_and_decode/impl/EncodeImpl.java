package com.eturial.SNMPManager.server.service.encode_and_decode.impl;

import com.eturial.SNMPManager.server.entity.dataparams.*;
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
           lengthByte = MergeUtils.mergeTwoBytes(lengthHead, lengthByte);
        }
        return lengthByte;
    }

    @Override
    public byte[] getTypeCode(int type) {
        byte[] valueByte = ChangeUtils.intToBytes(type);
        valueByte[0] |= (0x20);
        return valueByte;
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
    public byte[] getIntegerCode(String str) {
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
        //  T  UNIVERSAL 16 SEQUENCE/SEQUENCE OF 序列
        byte[] typeByte = getTypeCode(16);

        byte[] res = new byte[0];
        for(Variable variable : variableArrayList) {
            // V
            byte[] oID = getOIDCode(variable.getName());
            byte[] valueByte;
            switch (variable.getValueType()) {
                case UniversalType.INTEGER :
                    valueByte = getIntegerCode(variable.getValue());
                    break;
                case UniversalType.String :
                    valueByte = getStrCode(variable.getValue());
                    break;
                case UniversalType.NULL :
                    valueByte = getNullCode();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + variable.getValueType());
            }
            byte[] variableValueByte = MergeUtils.mergeTwoBytes(oID, valueByte);
            // L
            byte[] lengthByte = getLengthCode(variableValueByte.length);
            // 合并
            byte[] variableByte = MergeUtils.tlvMerge(typeByte, lengthByte, variableValueByte);
            res = MergeUtils.mergeTwoBytes(res, variableByte);
        }
        // L
        byte[] lengthByte = getLengthCode(res.length);

        return MergeUtils.tlvMerge(typeByte, lengthByte, res);
    }

    @Override
    public byte[] getSnmpPDUCode(SnmpPDU snmpPDU) {
        byte[] variableBindingsCode = getVariableBindingsCode(snmpPDU.getVariableBindings());

        // Error status 和 Error index
        byte[] errorByte = {2, 1, 0, 2, 1, 0};
        byte[] res = MergeUtils.mergeTwoBytes(errorByte, variableBindingsCode);

        //  Request ID
        //  V
        byte[] valueByte = getIntegerCode(String.valueOf(snmpPDU.getRequestAndResponse().getRequestId()));
        //  L
        byte[] lengthByte = getLengthCode(valueByte.length);
        //  T  UNIVERSAL 2 INTEGER
        byte[] typeByte = ChangeUtils.intToBytes(2);
        //  合并
        res = MergeUtils.mergeTwoBytes(MergeUtils.tlvMerge(typeByte, lengthByte, valueByte), res);
        //  编码PDU type的TL
        //  T
        byte[] tByte = new byte[0];
        if (snmpPDU.getPduTypeValue() == 0) {
            tByte = ChangeUtils.intToBytes(160);
        } else if (snmpPDU.getPduTypeValue() == 1) {
            tByte = ChangeUtils.intToBytes(161);
        } else if (snmpPDU.getPduTypeValue() == 3) {
            tByte = ChangeUtils.intToBytes(163);
        }
        //  L
        byte[] lByte = getLengthCode(res.length);

        return MergeUtils.tlvMerge(tByte, lByte, res);
    }

    @Override
    public byte[] getSnmpMessageCode(SNMPMessage snmpMessage) {
        byte[] snmpPDUCode = getSnmpPDUCode(snmpMessage.getSnmpPDU());
        // community
        byte[] communityCode = getStrCode(snmpMessage.getCommunity());
        byte[] valueByte = MergeUtils.mergeTwoBytes(communityCode, snmpPDUCode);
        // version
        byte[] versionCode = getIntegerCode(String.valueOf(snmpMessage.getVersionValue()));
        valueByte = MergeUtils.mergeTwoBytes(versionCode, valueByte);

        // L
        byte[] lByte = getLengthCode(valueByte.length);
        // T
        byte[] typeByte = getTypeCode(16);

        return MergeUtils.tlvMerge(typeByte, lByte, valueByte);
    }
}
