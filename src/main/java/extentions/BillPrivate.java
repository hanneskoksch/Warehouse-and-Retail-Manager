package extentions;

import core.interfaces.IBills;
import core.businessLogic.BillManager;
import core.Cart;

import java.util.Date;

public class BillPrivate implements IBills {

    private Cart cartObject;
    private Date date;
    private float totalPrice;
    private int mwst = 16;
    private float totalPriceMwst;


    public BillPrivate() {
        cartObject = new Cart();
        totalPrice = Float.parseFloat(cartObject.calculateTotalPrice());
        date = new Date();
        totalPriceMwst = (totalPrice/100)*mwst;
    }


    public void printBill(){
        BillManager bm = new BillManager();

        System.out.println("\n------------------------\n------- The Shop -------\n------------------------\n");
        System.out.println(date.toString());
        System.out.println("Rechnungsstellung für Privatkunden\n");

        System.out.println(bm.printItems());

        System.out.println("---------------");
        System.out.println("Gesamt:  " + totalPrice + "€");
        System.out.println("Mehrwertsteuer (16%): " + String.format(java.util.Locale.US,"%.2f",totalPriceMwst) + "€");
        System.out.println("Gesamt + Mwst: " + String.format(java.util.Locale.US,"%.2f",(totalPrice + totalPriceMwst)) + "€\n");
        System.out.println("------------------------\n");

    }


}
