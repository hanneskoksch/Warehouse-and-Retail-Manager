package extentions;

import core.interfaces.IBills;
import core.businessLogic.BillManager;
import core.Cart;

import java.util.Date;

public class BillBusiness implements IBills {

    private Cart cartObject;
    private Date date;
    private float totalPrice;

    public BillBusiness() {
        cartObject = new Cart();
        totalPrice = Float.parseFloat(cartObject.calculateTotalPrice());
        date = new Date();
    }

    @Override
    public void printBill() {
        BillManager bm = new BillManager();

        System.out.println("\n------------------------\n------- The Shop -------\n------------------------\n");
        System.out.println(date.toString());
        System.out.println("Rechnungsstellung für Gewerbekunden\n");

        System.out.println(bm.printItems());

        System.out.println("---------------");
        System.out.println("Gesamt:  " + totalPrice + "€");
        System.out.println("Der Preis versteht sich ohne Mehrwertsteuer.\n");
        System.out.println("------------------------\n");
    }

}
