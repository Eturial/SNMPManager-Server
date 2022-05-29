package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

/**
 * @author Eturial
 * @date 2022/5/28
 */

@Data
public class SnmpPDU {
    private RequestAndResponse requestAndResponse;

    private Trap trap;

    private VariableBindings variableBindings;
}
