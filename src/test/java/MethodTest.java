import com.eturial.SNMPManager.server.service.encode_and_decode.impl.EncodeImpl;
import com.eturial.SNMPManager.utils.ChangeUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Eturial
 * @date 2022/5/29
 */

public class MethodTest {
    public static void main(String[] args) {
        EncodeImpl encode = new EncodeImpl();
        System.out.println(Arrays.toString(encode.getOIDCode("1.3.6.1.2.1.1.1.0")));
        System.out.println(Arrays.toString(encode.getIntegerCoding("20")));
        System.out.println(Arrays.toString(encode.getStrCode("xust")));
    }
}
