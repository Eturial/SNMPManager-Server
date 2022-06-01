package com.eturial.SNMPManager.server.entity.return_value;

import com.eturial.SNMPManager.server.entity.dataparams.RequestAndResponse;
import lombok.Data;

/**
 * @author Eturial
 * @date 2022/6/1
 */

@Data
public class ReturnResponseAndPacket {
    RequestAndResponse requestAndResponse;
    byte[] packet;
}
