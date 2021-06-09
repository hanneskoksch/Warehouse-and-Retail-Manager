package core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class Categories {

    private static ObservableList<String> categoryList = FXCollections.observableArrayList();
    private static Logger log = LogManager.getLogger(Categories.class);


    /**
     * function to open local JSON-File with saved categories
     */
    public void readJson() {

        JSONParser parser = new JSONParser();
        JSONArray categoryArray = null;

        try (FileReader reader = new FileReader("categories.json")) {
            categoryArray = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            log.warn(e);
        }

        makeList(categoryArray);
    }


    /**
     * Transforms the content of the local JSON-File into an observable list to choose from categories
     * @param categoryArray contains data from local JSON-File
     */
    private void makeList(JSONArray categoryArray) {
        for (int i = 0; i < categoryArray.size(); i++) {
            JSONObject category = (JSONObject) categoryArray.get(i);
            categoryList.add((String) category.get("key"));
        }
    }


    /**
     * Grands access to itemList for the NewItemController-Class
     * @return
     */
    public static ObservableList<String> getCategoryList() {
        ObservableList<String> returnCategories = categoryList;
        return returnCategories;
    }

}
