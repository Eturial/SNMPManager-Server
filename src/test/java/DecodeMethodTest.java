import com.eturial.SNMPManager.server.service.encode_and_decode.Decode;
import com.eturial.SNMPManager.server.service.encode_and_decode.Encode;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.DecodeImpl;
import com.eturial.SNMPManager.server.service.encode_and_decode.impl.EncodeImpl;
import org.junit.Test;

/**
 * @author Eturial
 * @date 2022/5/30
 */

public class DecodeMethodTest {
    public static void main(String[] args) {
        Decode decode = new DecodeImpl();
//        byte b = -127;
//        b &= ~(1 << 7); // set a bit to 0);
//        System.out.println(b);
        System.out.println("");
        System.out.println(decode.getPacketLength(new byte[]{(byte)130, (byte)128, 1}));
    }
}
