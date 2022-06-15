package com.eturial.SNMPManager.server.entity.return_value;

import com.eturial.SNMPManager.server.entity.dataparams.RequestAndResponse;
import com.eturial.SNMPManager.server.entity.dataparams.Trap;
import lombok.Data;

/**
 * @author Eturial
 * @date 2022/6/4
 */
@Data
public class ReturnTrapAndPacket {
    Trap trap;
    byte[] packet;
}
