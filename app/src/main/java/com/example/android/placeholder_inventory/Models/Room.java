package com.example.android.placeholder_inventory.Models;

import java.util.HashMap;
import java.util.Map;

public class Room {
    // Room properties
    public String name;
    private String itemId;
    private String itemDescription;

    public Room() {
        // Default public constructor
    }

    // Two constructors one for "Rooms" and one for other objects?
    // Or one for those that have no children and another for containers
    public Room(String roomName, String itemId, String itemDescription) {
        this.name = roomName;
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

    public String getItemId(){
        return itemId;
    }

    public String getItemDescription(){
        return itemDescription;
    }
}
