import com.eturial.SNMPManager.server.service.manager.ReceiveTrap;

/**
 * @author Eturial
 * @date 2022/6/4
 */

public class TrapTest {

    public static void main(String[] args) {
        ReceiveTrap receiveTrap = new ReceiveTrap();
        receiveTrap.run();
    }
}
