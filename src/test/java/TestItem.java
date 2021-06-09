import core.Item;
import org.junit.Assert;
import org.junit.Test;

public class TestItem {

    Item example = new Item("Test",421212,12,"flex",12.34f,"Ein sagenhaftes Objekt");

    @Test
    public void CheckItemAmount(){
        Assert.assertEquals(12,example.getAmount());
    }

    @Test
    public void CheckItemCart(){
        example.setAmountInCart(4);
        Assert.assertEquals(8,example.getAmount());
    }


}
