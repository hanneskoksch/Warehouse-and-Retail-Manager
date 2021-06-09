package core.businessLogic;

import javafx.collections.ObservableList;
import core.DisplayCart;
import core.Item;

import java.util.Date;

public class BillManager {

    private DisplayCart displayCart = new DisplayCart();
    private ObservableList<Item> cart;
    private Date date;


    public BillManager() {
        cart = displayCart.getProductData();
        date = new Date();
    }


    public String printItems(){

        String printedItemlist = "";

        for (int i = 0; i < cart.size(); i++) {
            Item currentItem = cart.get(i);
            String name = currentItem.getName();
            float price = currentItem.getPrice();

            if (name.length()>10){
                printedItemlist += name.substring(0,7) + "...   " + price + "\n";
            } else {
                printedItemlist += name + "   " + price + "\n";
            }
        }

        return printedItemlist;
    }


}
