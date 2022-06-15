package com.eturial.SNMPManager.server.entity.return_value;

import lombok.Data;

/**
 * @author Eturial
 * @date 2022/6/4
 */

@Data
public class ReturnOidAndPacket {
    String OID;
    byte[] packet;
}
