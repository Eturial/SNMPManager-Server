package com.eturial.SNMPManager.server.entity.dataparams;

/**
 * @author Eturial
 * @date 2022/5/28
 *
 * ASN.1 定义的通用类型表
 */

public class UniversalType {
    public static final String[] types = {"",
            "BOOLEAN (1)", "INTEGER (2)", "BIT STRING (3)",
            "OCTET STRING (4)", "NULL (5)", "OBJECT IDENTIFIER (6)"};
    public static final String INTEGER = "INTEGER (2)";
    public static final String String = "OCTET STRING (4)";
    public static final String NULL = "NULL (5)";
    public static final String IDENTIFIER = "OBJECT IDENTIFIER (6)";
}
