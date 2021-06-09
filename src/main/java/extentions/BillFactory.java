package extentions;

import core.interfaces.IBills;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BillFactory {

    private static Logger log = LogManager.getLogger(BillFactory.class);

    public static IBills createBill(String billtype) {
        switch (billtype){
            case "Gewerbekunden":
                return new BillBusiness();
            case  "Privatkunden":
                return new BillPrivate();
            default:
                return null;
        }

    }


}
