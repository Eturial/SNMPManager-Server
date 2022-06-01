package com.eturial.SNMPManager.server.service.encode_and_decode;

import com.eturial.SNMPManager.server.entity.dataparams.SNMPMessage;
import com.eturial.SNMPManager.server.entity.dataparams.SnmpPDU;
import com.eturial.SNMPManager.server.entity.dataparams.VariableBindings;
import com.eturial.SNMPManager.server.entity.return_value.ReturnResponseAndPacket;

import java.io.UnsupportedEncodingException;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public interface Decode {
    /**
     * 解码数据包，得到SNMP整体
     * @param packet
     * @return 解码得到的SNMP
     */
    SNMPMessage getSNMPMessage(byte[] packet) throws UnsupportedEncodingException;

    /**
     * 解码长度
     * @param packet
     * @return 长度值 和 长度占的字节数
     */
    int[] getPacketLength(byte[] packet);

    /**
     * 解码类型
     * @param packet
     * @return 类型值
     */
    int getPacketType(byte[] packet);

    /**
     * 去掉x字节的数据包
     * @param packet
     * @return 剩下的数据包
     */
    byte[] cutPacket(byte[] packet, int x);

    /**
     * 解码数据包，得到SNMP PDU
     * @param packet
     * @return SNMP PDU
     */
    SnmpPDU getSnmpPDU(byte[] packet);

    /**
     * 解码数据包，得到Response
     * @param packet
     * @return Variable bindings
     */
    ReturnResponseAndPacket getResponse(byte[] packet);

    /**
     * 解码数据包，得到Variable bindings
     * @param packet
     * @return Variable bindings
     */
    VariableBindings getVariableBindings(byte[] packet);

    /**
     * 解码数据包，得到OID
     * @param packet
     * @return Variable bindings
     */

}
