package core;

import core.interfaces.IDisplayEssentials;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisplayCart implements IDisplayEssentials {

    private static ObservableList<Item> cart = FXCollections.observableArrayList();
    private static Logger log = LogManager.getLogger(DisplayCart.class);


    /**
     * Adds an transmitted item to the shoppingcart
     * @param itemobject Item object
     */
    @Override
    public void addItemToList(Item itemobject){
        cart.add(itemobject);
        log.info("Item moved from stock to cart: " + itemobject.getName());
    }



    /**
     * Grands access to itemList for the CartController-Class
     * @return itemList
     */
    @Override
    public ObservableList<Item> getProductData() {
        ObservableList<Item> returnCart = cart;
        return returnCart;
    }


    public void resetCart(){
        cart.removeAll(cart);
    }
}
