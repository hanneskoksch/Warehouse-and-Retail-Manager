package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupRunnable implements Runnable{

    private static Logger log = LogManager.getLogger(Stock.class);


    // Background Thread Method for autosafe
    @Override
    public void run() {
        Stock stock = new Stock();
        try {
            while (true) {
                // safes every 10 seconds
                Thread.sleep(10 * 1000);
                stock.writeList();
                log.debug("Autosafe");
            }
        } catch (InterruptedException e) {
            log.warn(e);
        }
    }

}
