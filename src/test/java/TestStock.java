import core.exceptions.IdNotUniqueException;
import core.Stock;
import core.exceptions.ValuesNotValidException;
import org.junit.Assert;
import org.junit.Test;

public class TestStock {
    Stock stock = new Stock();

    @Test
    public void CheckValidItem(){
        boolean nice = true;
        try{
            stock.checkValues("Hallo","100111","12","flex","12.45","Dies ist ein Test");
        }catch (ValuesNotValidException v){
            System.out.println(v);
            nice = false;
        }
        Assert.assertTrue(nice);
    }

    @Test
    public void CheckInvalidItem() {
        boolean nice = true;
        try{
            stock.checkValues("Hallo","KeineNummer","12","flex","12.45","Dies ist ein Test");
        }catch (ValuesNotValidException v){
            System.out.println(v);
            nice = false;
        }
        Assert.assertFalse(nice);
    }

    @Test
    public void CheckInvalidItemNR() {
        boolean nice = true;
        try{
            stock.checkValues("Hallo","111111","12a","flex","12.45","Dies ist ein Test");
        }catch (ValuesNotValidException v){
            System.out.println(v);
            nice = false;
        }
        Assert.assertFalse(nice);
    }

    @Test
    public void CheckInvalidItemPrice() {
        boolean nice = true;
        try{
            stock.checkValues("Hallo","111111","12","flex","1.2.45","Dies ist ein Test");
        }catch (ValuesNotValidException v){
            System.out.println(v);
            nice = false;
        }
        Assert.assertFalse(nice);
    }

    @Test
    public void CheckDescriptionLength() {
        boolean nice = true;
        try{
            stock.checkValues("Hallo","111111","12","flex","12.45","Dies ist ein Test der eine eindeutig viel zu lange Beschreibung enthält - da sollte definitv ein Fehler auftreten");
        }catch (ValuesNotValidException v){
            System.out.println(v);
            nice = false;
        }
        Assert.assertFalse(nice);
    }

    @Test
    public void CheckIDUniqueMethod(){
        boolean success = true;
        try {
            stock.createItem("Radlader", 123456, 15, "Spielzeug", 19.99f, "gutes Gerät!");
            stock.createItem("Radlader", 123456, 15, "Spielzeug", 19.99f, "gutes Gerät!");
        }catch(IdNotUniqueException id){
            success = false;
        }
        Assert.assertFalse(success);
    }
}
