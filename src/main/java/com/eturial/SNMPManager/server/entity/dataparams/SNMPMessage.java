package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

/**
 * SNMP 报文
 * @author Eturial
 * @date 2022/5/4
 */

@Data
public class SNMPMessage {

    /**
     * SNMP Version-1(0)  Version-2:(1)  Version-3:(2)
     */
    private Integer versionValue;
    private String version;

    /**
     * 共同体（community）： 作为管理进程和代理进程之间的明文口令
     */
    private String community;

    /**
     * SNMP PDU类型 get-request(0) get-next-request(1)
     *             get-response(2) set-request(3)
     *             trap(4)
     */
    private Integer pduTypeValue;
    private String pduType;

    private final String[] snmpPduType = {
            "get-request (0)", "get-next-request (1)", "get-response (2)",
            "set-request (3)", "trap (4)"};

    private Object snmpPDU;

    public SNMPMessage(int versionValue, String community, int pduTypeValue, Object snmpPDU) {
        this.versionValue = versionValue;
        this.version = "Version-" + (versionValue - 1);

        this.community = community;

        this.pduTypeValue = pduTypeValue;
        this.pduType= snmpPduType[pduTypeValue];

        this.snmpPDU = snmpPDU;
    }

}
