import core.*;
import core.exceptions.IdNotUniqueException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestJSON {

    @Test
    public void testStockItems() throws IdNotUniqueException {
        Stock stock = new Stock();
        DisplayStock d = new DisplayStock();
        d.readJson();
        stock.createItem("Radlader", 123456, 15, "Spielzeug", 19.99f, "gutes Gerät!");
        stock.createItem("Flachzange", 125432, 3, "Spielzeug", 999.99f, "denkbar Günstig!");
        stock.createItem("Boxhandschuhe", 442345, 12, "Spielzeug", 139.99f, "jetzt zuschlagen!");
        stock.createItem("Karl Ess Business Coaching", 124253, 3, "Spielzeug", 599.99f, "direkt auf den Mount Everest");
        stock.createItem("Lastkraftwagen", 321432, 21, "Sport", 169.99f, "500PS! dynamisches Gerät");
        stock.createItem("Apple Imac", 244332, 3, "Flex", 9999.99f, "Die Vision hinter dem iMac war schon immer, das Desktoperlebnis verändern – mit leistungsstarker, leicht zu bedienender Technologie in einem eleganten All-in-One Design. Der neue iMac bringt diese Idee auf die nächste Ebene, mit noch mehr großartigen Tools, um so ziemlich alles zu machen. Der iMac kommt mit den neuesten Prozessoren, schnellerem Arbeitsspeicher und phänomenaler Grafik. Und all das siehst du auf dem hellsten, brillantesten Retina Display, das es je beim Mac gab. Er ist alles, was du willst.Mit noch mehr Leistung.");

        stock.writeList();
        File f = new File("stock.json");
        Assert.assertNotNull(d.getProductData());
    }



    @Test
    public void testCategoryList() {
        Categories c = new Categories();
        c.readJson();
        Assert.assertNotNull(Categories.getCategoryList());
    }

}