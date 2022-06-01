package com.eturial.SNMPManager.server.service.encode_and_decode.impl;

import com.eturial.SNMPManager.server.entity.dataparams.*;
import com.eturial.SNMPManager.server.entity.return_value.ReturnResponseAndPacket;
import com.eturial.SNMPManager.server.service.encode_and_decode.Decode;
import com.eturial.SNMPManager.utils.ChangeUtils;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Eturial
 * @date 2022/5/29
 */
@Data
public class DecodeImpl implements Decode {
    SNMPMessage snmpMessage;
    @Override
    public SNMPMessage getSNMPMessage(byte[] packet) throws UnsupportedEncodingException {
        SNMPMessage snmpMessage = new SNMPMessage();

        // 解码TL
        packet = cutPacket(packet, 1);
        int length = getPacketLength(packet)[0];
        int lengthByte = getPacketLength(packet)[1];
        packet = cutPacket(packet, lengthByte);

        // 解码Version
        int version = packet[2] & 0xff;
        snmpMessage.setVersionValue(version);

        packet = cutPacket(packet, 4);

        // 解码Community
        length = getPacketLength(packet)[0];
        lengthByte = getPacketLength(packet)[1];
        packet = cutPacket(packet, lengthByte);
        String community = new String(Arrays.copyOfRange(packet, 0, length), "UTF-8");
        snmpMessage.setCommunity(community);
        packet = cutPacket(packet, length);

        // 解码SnmpPDU
        SnmpPDU snmpPDU = getSnmpPDU(packet);
        snmpMessage.setSnmpPDU(snmpPDU);
        this.snmpMessage = snmpMessage;
        return snmpMessage;
    }

    @Override
    public int[] getPacketLength(byte[] packet) {
        int length = packet[0];
        byte temp = 0;
        if(((packet[0] >> 7) & 0x1) == 1) {
            temp = (packet[0] &= ~(1 << 7));
            byte[] l = Arrays.copyOfRange(packet, 1, temp + 1);
            length = ChangeUtils.bytesToInt(l);
        }
        return new int[]{length, temp + 1};
    }

    @Override
    public int getPacketType(byte[] packet) {
        return 0;
    }

    @Override
    public byte[] cutPacket(byte[] packet, int x) {
        return Arrays.copyOfRange(packet, x, packet.length);
    }

    @Override
    public SnmpPDU getSnmpPDU(byte[] packet) {
        SnmpPDU snmpPDU = new SnmpPDU();
        Trap trap = new Trap();

        // SnmpPDU类型判断
        int typeValue = ChangeUtils.bytesToInt(new byte[]{packet[0]}) - 160;
        snmpPDU.setPduTypeValue(typeValue);
        packet = cutPacket(packet, 1);

        // trap
        if(typeValue == 4) {
            snmpPDU.setTrap(trap);
        } else {
            snmpPDU.setRequestAndResponse(getResponse(packet).getRequestAndResponse());
            packet = getResponse(packet).getPacket();
        }
        snmpPDU.setVariableBindings(getVariableBindings(packet));

        return snmpPDU;
    }

    @Override
    public ReturnResponseAndPacket getResponse(byte[] packet) {
        ReturnResponseAndPacket returnResponseAndPacket = new ReturnResponseAndPacket();
        RequestAndResponse requestAndResponse = new RequestAndResponse();

        int length = getPacketLength(packet)[0];
        int lengthByte = getPacketLength(packet)[1];
        packet= cutPacket(packet, lengthByte + 1);

        length = getPacketLength(packet)[0];
        lengthByte = getPacketLength(packet)[1];
        int requestId = ChangeUtils.bytesToInt(Arrays.copyOfRange(packet, 1, length + 1));
        requestAndResponse.setRequestId(requestId);
        packet = cutPacket(packet, length + lengthByte + 1);

        length = getPacketLength(packet)[0];
        lengthByte = getPacketLength(packet)[1];
        int errorStatus = ChangeUtils.bytesToInt(Arrays.copyOfRange(packet, 1, length + 1));
        requestAndResponse.setErrorStatusValue(errorStatus);
        packet = cutPacket(packet, length + lengthByte + 1);

        length = getPacketLength(packet)[0];
        lengthByte = getPacketLength(packet)[1];
        int errorIndex = ChangeUtils.bytesToInt(Arrays.copyOfRange(packet, 1, length + 1));
        requestAndResponse.setErrorIndex(errorIndex);
        packet = cutPacket(packet, length + lengthByte);

        returnResponseAndPacket.setRequestAndResponse(requestAndResponse);
        returnResponseAndPacket.setPacket(packet);
        return returnResponseAndPacket;
    }

    @Override
    public VariableBindings getVariableBindings(byte[] packet) {
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        VariableBindings variableBindings = new VariableBindings();
        packet = cutPacket(packet, 1);
        int length = getPacketLength(packet)[0];
        int lengthByte = getPacketLength(packet)[1];
        packet = cutPacket(packet, lengthByte);

        while(packet.length != 0) {
            Variable variable = new Variable();
            packet = cutPacket(packet, 1);

            length = getPacketLength(packet)[0];
            lengthByte = getPacketLength(packet)[1];
            packet = cutPacket(packet, lengthByte + 1);

            length = getPacketLength(packet)[0];
            lengthByte = getPacketLength(packet)[1];
            packet = cutPacket(packet, lengthByte);

            byte y = (byte) (packet[0] % 40);
            byte x = (byte) (packet[0] / 40);
            String oID = String.valueOf(x) + "." + String.valueOf(y);
            for(int i = 1; i < length; i++)
                oID += "." + String.valueOf(packet[i]);
            variable.setName(oID);

            packet = cutPacket(packet, length);

            packet = cutPacket(packet, 1);
            length = getPacketLength(packet)[0];
            lengthByte = getPacketLength(packet)[1];
            packet = cutPacket(packet, lengthByte);

            y = (byte) (packet[0] % 40);
            x = (byte) (packet[0] / 40);
            oID = String.valueOf(x) + "." + String.valueOf(y);
            for(int i = 1; i < length; i++) {
                if(((packet[i] >> 7) & 0x1) == 1) {
                    int k = i;
                    ArrayList<Byte> list = new ArrayList<>();
                    while(((packet[k] >> 7) & 0x1) == 1) {
                        list.add((packet[k] &= ~(1 << 7)));
                        k++;
                    }
                    list.add(packet[k]);
                    int res = 0;
                    for(int j = 0; j < list.size(); j++) {
                        res += list.get(j) * Math.pow(128,list.size() - j - 1);
                    }
                    oID += "." + String.valueOf(res);
                    i += k - i;
                    continue;
                }
                oID += "." + String.valueOf(packet[i]);
            }
            packet = cutPacket(packet, length);
            variable.setValue(oID);
            variableArrayList.add(variable);
        }
        variableBindings.setVariableList(variableArrayList);

        return variableBindings;
    }
}
