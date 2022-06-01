package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

/**
 * @author Eturial
 * @date 2022/5/28
 */

@Data
public class SnmpPDU {
    private RequestAndResponse requestAndResponse;

    private Trap trap;

    /**
     * 变量绑定表(Variable bindings)：变量绑定列表，由变量名和变量值对组成。
     * 在检索请求报文中，变量的值应为 0。
     */
    private VariableBindings variableBindings;

    /**
     * SNMP PDU类型 get-request(0) get-next-request(1)
     *             get-response(2) set-request(3)
     *             trap(4)
     */
    private Integer pduTypeValue;
    private String pduType;

    private static final String[] snmpPduType = {
            "get-request (0)", "get-next-request (1)", "get-response (2)",
            "set-request (3)", "trap (4)"};

    public SnmpPDU(int pduTypeValue, Trap trap, RequestAndResponse requestAndResponse, VariableBindings variableBindings) {
        this.pduTypeValue = pduTypeValue;
        this.pduType= snmpPduType[pduTypeValue];
        if(pduTypeValue == 4)
            this.trap = trap;
        else
            this.requestAndResponse = requestAndResponse;

        this.variableBindings = variableBindings;
    }

    public SnmpPDU() {

    }

    public void setPduTypeValue(Integer pduTypeValue) {
        this.pduTypeValue = pduTypeValue;
        this.pduType = snmpPduType[pduTypeValue];
    }

    @Override
    public String toString() {
        if(pduTypeValue != 4)
            return "SnmpPDU{\n\t\t\t\t\t" + pduType + '\'' +
                requestAndResponse +
                ", \n\t\t\t\t\t" + variableBindings + "\n\t\t\t" +
                '}';
        else
            return "SnmpPDU{\n\t\t\t\t\t" + pduType + '\'' +
                    trap +
                    ", \n\t\t\t\t\t" + variableBindings + "\n\t\t\t" +
                    '}';
    }
}
