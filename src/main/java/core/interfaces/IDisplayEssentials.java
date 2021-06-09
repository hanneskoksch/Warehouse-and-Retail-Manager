package core.interfaces;

import javafx.collections.ObservableList;
import core.Item;

public interface IDisplayEssentials {

    // Adds a transmitted item to the list
    void addItemToList(Item itemobject);

    // Grands access to list
    ObservableList<Item> getProductData();

}
