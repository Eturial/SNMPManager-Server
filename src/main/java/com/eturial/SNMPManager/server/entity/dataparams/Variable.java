package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

/**
 * 变量绑定表中的单个变量
 * @author Eturial
 * @date 2022/5/4
 */

@Data
public class Variable {
    /**
     * SNMP对象标识符(Object Name)：OID
     */
    private String name = "";

    /**
     * 值类型(valueType)：值的类型
     */
    private Integer type;
    private String valueType;

    /**
     * 值(Value)：值
     */
    private String value;

    public Variable(String name, int valueType, String value) {
        this.name = name;

        this.type = valueType;
        this.valueType = UniversalType.types[valueType];

        this.value = value;
    }

    public Variable() {

    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
