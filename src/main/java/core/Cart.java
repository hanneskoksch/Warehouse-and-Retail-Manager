package core;

import javafx.collections.ObservableList;

/**
 * Methods related to the shopping cart
 */
public class Cart {
    /**
     * Calculates the total price of all items
     * @return price of all times
     */
    public String calculateTotalPrice() {
        DisplayCart c = new DisplayCart();
        ObservableList <Item> cart = c.getProductData();

        int length = cart.size();
        float totalPrice = 0;

        for (int i = 0; i < length; i++) {
            Item o = cart.get(i);
            totalPrice += o.getPriceTotalFX().get();
        }

        return String.format(java.util.Locale.US,"%.2f",totalPrice);
    }

}
