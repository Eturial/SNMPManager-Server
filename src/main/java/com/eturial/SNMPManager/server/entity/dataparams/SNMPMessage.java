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

    private SnmpPDU snmpPDU;

    public SNMPMessage(int versionValue, String community, SnmpPDU snmpPDU) {
        this.versionValue = versionValue;
        this.version = "Version-" + (versionValue - 1);

        this.community = community;

        this.snmpPDU = snmpPDU;
    }

    public SNMPMessage() {

    }

    public void setVersionValue(Integer versionValue) {
        this.versionValue = versionValue;
        this.version = "Version-" + (versionValue - 1);
    }

    @Override
    public String toString() {
        return "SNMPMessage{" +
                "\n\t\t\tversion='" + version + '\'' +
                ", community='" + community + '\'' +
                ",\n\t\t\t" + snmpPDU + "\n" +
                '}';
    }
}
