package com.eturial.SNMPManager.server.service;

import com.alibaba.fastjson.JSONObject;
import com.eturial.SNMPManager.server.entity.dataparams.*;
import com.eturial.SNMPManager.server.service.manager.SendRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * @author Eturial
 * @date 2022/6/2
 */

@Service
public class Server {
    public void sendAndReceive() {
        VariableBindings variableBindings = new VariableBindings();
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        Variable variable1 = new Variable("1.3.6.1.2.1.1.1.311", 2, "20");
        variableArrayList.add(variable1);
        variableBindings.setVariableList(variableArrayList);

        Trap trap = new Trap();
        RequestAndResponse requestAndResponse = new RequestAndResponse(1,0, 0);
        SnmpPDU snmpPDU = new SnmpPDU(1, trap, requestAndResponse, variableBindings);

        SNMPMessage snmpMessage = new SNMPMessage(0, "xust", snmpPDU);

//        System.out.println(ChangeUtils.byteArrayToHexString(encode.getSnmpMessageCode(snmpMessage)));

        SendRequest sendRequest = new SendRequest(snmpMessage, "127.0.0.1");
        sendRequest.run();
    }
}
