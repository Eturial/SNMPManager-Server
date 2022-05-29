import com.eturial.SNMPManager.server.entity.dataparams.*;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.EncodeImpl;
import com.eturial.SNMPManager.utils.ChangeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public class MethodTest {
    public static void main(String[] args) {
        EncodeImpl encode = new EncodeImpl();

        VariableBindings variableBindings = new VariableBindings();
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        Variable variable1 = new Variable("1.3.6.1.2.1.1.1.0", 2, "20");
        variableArrayList.add(variable1);
        variableBindings.setVariableList(variableArrayList);

        Trap trap = new Trap();
        RequestAndResponse requestAndResponse = new RequestAndResponse(1,0, 0);
        SnmpPDU snmpPDU = new SnmpPDU(1, trap, requestAndResponse, variableBindings);

        SNMPMessage snmpMessage = new SNMPMessage(1, "xust", snmpPDU);

        System.out.println(ChangeUtils.byteArrayToHexString(encode.getSnmpMessageCode(snmpMessage)));
    }
}
