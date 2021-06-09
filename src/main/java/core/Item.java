package core;

import javafx.beans.property.*;
import org.json.simple.JSONObject;

public class Item {

    final private String name;
    final private int itemnr;
    private int amount;
    final private String category;
    final private float price;
    final private String description;
    final private StringProperty namefx;
    final private FloatProperty pricefx;
    final private IntegerProperty amountfx;
    final private IntegerProperty itemnrfx;
    private int amountInCart;
    private IntegerProperty amountInCartfx;
    private FloatProperty priceTotal;


    /**
     * Constructor to create an item object
     * @param name          name
     * @param itemnr        itemnr
     * @param amount        amount
     * @param category      category
     * @param price         price
     * @param description   description
     */
    public Item(String name, int itemnr, int amount, String category, float price, String description) {
        this.name = name;
        this.itemnr = itemnr;
        this.amount = amount;
        this.category = category;
        this.price = price;
        this.description = description;
        namefx = new SimpleStringProperty(name);
        pricefx = new SimpleFloatProperty(price);
        amountfx = new SimpleIntegerProperty(amount);
        itemnrfx = new SimpleIntegerProperty(itemnr);
        //this.amountInCartfx = new SimpleIntegerProperty(0);
    }

    /**
     * returns the name of the item
     * @return  name
     */
    public String getName() {
        return name;
    }
    /**
     * returns the itemnr of the item
     * @return  itemnr
     */
    public int getItemnr() { return itemnr; }

    /**
     * returns the amount within the stock of the item
     * @return  amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * returns the category of the item
     * @return  category
     */
    public String getCategory() { return category; }

    /**
     * returns the price of the item
     * @return  price
     */
    public float getPrice() {
        return price;
    }

    /**
     * returns the description of the item
     * @return  description
     */
    public String getDescription() { return description; }


    /**
     * returns the name of the item for JavaFX
     * @return  name for JavaFX
     */
    public StringProperty getNameFX(){
        return namefx;
    }

    /**
     * returns the price of the item for JavaFX
     * @return  price for JavaFX
     */
    public FloatProperty getPriceFX(){
        return pricefx;
    }

    /**
     * returns the amount in storage of the item for JavaFX
     * @return  amount for JavaFX
     */
    public IntegerProperty getAmountFX(){
        return amountfx;
    }

    /**
     * returns the itemnr of the item for JavaFX
     * @return  itemnr for JavaFX
     */
    public IntegerProperty getItemnrFX(){
        return itemnrfx;
    }

    /**
     * returns the total price (amount in cart * price) of the item for JavaFX
     * @return priceTotal for JavaFX
     */
    public FloatProperty getPriceTotalFX() {
        return priceTotal;
    }

    /**
     * returns the amount of the item in the cart for JavaFX
     * @return amountInCartfx for JavaFX
     */
    public IntegerProperty getamountInCartFX() {
        return amountInCartfx;
    }

    /**
     * Reurns item as an JSON-Object to save Item in JSON-File
     * @return JSONObject
     */
    public JSONObject getItemAsJSONObject(){
        JSONObject item = new JSONObject();
        item.put("name", name);
        item.put("price", price);
        item.put("amount", amount);
        item.put("category",category);
        item.put("itemnr",itemnr);
        item.put("description",description);
        return item;
    }

    /**
     * Updates amount.
     * @param amountInCart
     */
    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
        amountInCartfx = new SimpleIntegerProperty(amountInCart);
        amount -= amountInCart;
        //String.format(java.util.Locale.US,"%.2f", floatValue);
        String total = String.format(java.util.Locale.US,"%.2f",price*(float)amountInCart);
        priceTotal = new SimpleFloatProperty(Float.parseFloat(total));
    }

}
