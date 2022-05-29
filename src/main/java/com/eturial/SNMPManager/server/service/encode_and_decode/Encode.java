package com.eturial.SNMPManager.server.service.encode_and_decode;

import com.eturial.SNMPManager.server.entity.dataparams.VariableBindings;

import java.util.ArrayList;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public interface Encode {
    /**
     * @param length
     * @return length 编码
     */
    byte[] getLengthCode(int length);

    /**
     * @param objectID   Variable对象(Name) 的值
     * @return UNIVERSAL 6 OBJECT IDENTIFIER类型 编码
     */
    byte[] getOIDCode(String objectID);

    /**
     * @return UNIVERSAL 5 NULL类型 编码
     */
    byte[] getNullCode();

    /**
     * @param str
     * @return UNIVERSAL 4 OCTET STRING类型 编码
     */
    byte[] getStrCode(String str);

    /**
     * @param str
     * @return UNIVERSAL 2 INTEGER类型 编码
     */
    byte[] getIntegerCoding(String str);

    /**
     * @param variableBindings
     * @return Variable bindings 编码
     */
    byte[] getVariableBindingsCode(VariableBindings variableBindings);
}
