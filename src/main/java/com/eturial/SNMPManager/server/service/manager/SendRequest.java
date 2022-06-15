package com.eturial.SNMPManager.server.service.manager;

import com.eturial.SNMPManager.server.entity.dataparams.SNMPMessage;
import com.eturial.SNMPManager.server.service.encode_and_decode.Encode;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.EncodeImpl;
import com.eturial.SNMPManager.utils.ChangeUtils;
import com.eturial.SNMPManager.utils.ShowPacket;

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
    byte[] bytes = new byte[1500];
    InetAddress ipaddress;

    Encode encode = new EncodeImpl();

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
        try {

            byte[] snmpData = encode.getSnmpMessageCode(snmpMessage);
            System.out.println("Send SNMP Message:");
//            System.out.println(Arrays.toString(snmpData));
            System.out.println(ChangeUtils.byteArrayToHexString(encode.getSnmpMessageCode(snmpMessage)));
            DatagramPacket datagramPacketSend = new DatagramPacket(snmpData, snmpData.length, ipaddress, 161);
            socket.setReuseAddress(true);
            socket.send(datagramPacketSend);

            DatagramPacket datagramPacketReceive = new DatagramPacket(bytes, bytes.length);
            socket.setSoTimeout(1000);
            socket.receive(datagramPacketReceive);

            byte[] temp = datagramPacketReceive.getData();

            byte[] response = new byte[datagramPacketReceive.getLength()];
            System.arraycopy(temp, 0, response, 0, datagramPacketReceive.getLength());

            System.out.println("Receive SNMP Response:");
            ShowPacket.showPacket(response);
            System.out.println(ChangeUtils.byteArrayToHexString(response));
        } catch (Exception e) {
            System.out.println("\n 响应超时！！！\n");
        }
    }
}
