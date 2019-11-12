package com.example.android.placeholder_inventory.Models;

import java.util.HashMap;
import java.util.Map;

public class Room {
    // Room properties
    public String name;
    private String itemId;
    private String userId; //Only the userId not the entire User object

    public Room() {
        // Default public constructor
    }

    // Two constructors one for "Rooms" and one for other objects?
    // Or one for those that have no children and another for containers
    public Room(String roomName, String userId, String key) {
        this.name = roomName;
        this.itemId = key;
        this.userId = userId;
    }

    // Object to Map to use with FireBase
    public Map<String, Object> makeMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("itemId", itemId);
        result.put("userId", userId);
        return result;
    }

    public String getItemId(){
        return itemId;
    }
}
