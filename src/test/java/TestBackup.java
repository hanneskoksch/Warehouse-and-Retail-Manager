import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestBackup {

    // Has to be executed while Application is running
    @Test
    public void BackupDeletedFile(){
        File jsonFile = new File("stock.json");
        jsonFile.delete();
        try {
            Thread.sleep(11*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(jsonFile.exists());
    }

}
