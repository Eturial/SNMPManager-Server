package com.eturial.SNMPManager.utils;

import com.eturial.SNMPManager.server.entity.dataparams.SNMPMessage;
import com.eturial.SNMPManager.server.service.encode_and_decode.Decode;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.DecodeImpl;

import java.io.UnsupportedEncodingException;

/**
 * @author Eturial
 * @date 2022/6/1
 */

public class ShowPacket {
    public static void showPacket(byte[] packet) throws UnsupportedEncodingException {
        DecodeImpl decode = new DecodeImpl();
        SNMPMessage snmpMessage = decode.getSNMPMessage(packet);
        System.out.println(snmpMessage.toString());
    }
}
