package com.example.android.placeholder_inventory.models;

import java.util.HashMap;
import java.util.Map;

public class UserItem {
    // UserItem properties
    private String name;
    private String itemId;
    private String itemDescription;

    public UserItem() {
        // Default public constructor
    }


    public UserItem(String itemName, String itemId, String itemDescription) {
        this.name = itemName;
        this.itemId = itemId;
        this.itemDescription = itemDescription;
    }

    // Object to Map to use with FireBase
    public Map<String, Object> makeMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("itemId", itemId);
        result.put("itemDescription", itemDescription);
        return result;
    }

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
