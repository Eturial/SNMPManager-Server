package com.eturial.SNMPManager.server.service.manager;

import com.eturial.SNMPManager.server.entity.dataparams.SNMPMessage;
import com.eturial.SNMPManager.server.service.encode_and_decode.Decode;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.DecodeImpl;
import com.eturial.SNMPManager.utils.ChangeUtils;
import com.eturial.SNMPManager.utils.ShowPacket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Eturial
 * @date 2022/6/4
 */

public class ReceiveTrap implements Runnable{
    DatagramSocket socket;
    byte[] data = new byte[1500];
    DatagramPacket datagramPacket;

    Decode decode = new DecodeImpl();

    public ReceiveTrap() {
        try {
            this.socket = new DatagramSocket(162);
            this.datagramPacket = new DatagramPacket(data, data.length);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                socket.receive(datagramPacket);

                byte[] snmpData = new byte[datagramPacket.getLength()];
                byte[] temp = datagramPacket.getData();
                System.arraycopy(temp, 0, snmpData, 0, datagramPacket.getLength());

                System.out.println("Receive SNMP Trap:");
                SNMPMessage response = decode.getSNMPMessage(snmpData);
                System.out.println(ChangeUtils.byteArrayToHexString(snmpData));
                ShowPacket.showPacket(snmpData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
