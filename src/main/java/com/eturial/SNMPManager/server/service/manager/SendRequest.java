package com.eturial.SNMPManager.server.service.manager;

import com.eturial.SNMPManager.server.entity.dataparams.SNMPMessage;
import com.eturial.SNMPManager.server.service.encode_and_decode.Decode;
import com.eturial.SNMPManager.server.service.encode_and_decode.Encode;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.DecodeImpl;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.EncodeImpl;
import com.eturial.SNMPManager.utils.ChangeUtils;
import com.eturial.SNMPManager.utils.ShowPacket;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;

/**
 * @author Eturial
 * @date 2022/5/29
 *
 * Manager 发送 Request
 */

public class SendRequest {
    /**
     * UDP Socket
     */
    public static DatagramSocket socket;
    SNMPMessage snmpMessage;
    byte[] bytes = new byte[1472];
    DatagramPacket dp;
    InetAddress ipaddress;

    Encode encode = new EncodeImpl();
    Decode decode = new DecodeImpl();

    static {
        try {
            socket = new DatagramSocket(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public SendRequest(SNMPMessage snmpMessage, String ipAddress) {
        try {
            this.ipaddress = InetAddress.getByName(ipAddress);
            this.snmpMessage = snmpMessage;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        byte[] snmpData2 = null;
        byte[] snmpDataTemp;
        try {

            byte[] snmpData = encode.getSnmpMessageCode(snmpMessage);
            System.out.println("Send SNMP Message:");
            System.out.println(ChangeUtils.byteArrayToHexString(encode.getSnmpMessageCode(snmpMessage)));

            dp = new DatagramPacket(snmpData, snmpData.length, ipaddress, 161);
            socket.setReuseAddress(true);
            socket.send(dp);

            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
            socket.setSoTimeout(1000);
            socket.receive(dp);
            snmpData2 = new byte[dp.getLength()];
            snmpDataTemp = dp.getData();
            System.arraycopy(snmpDataTemp, 0, snmpData2, 0, dp.getLength());

            System.out.println("Receive SNMP Response:");
            ShowPacket.showPacket(snmpData2);
            System.out.println(ChangeUtils.byteArrayToHexString(snmpData2));
        } catch (Exception e) {
            System.out.println("\n 响应超时！！！\n");
        }
    }
}
