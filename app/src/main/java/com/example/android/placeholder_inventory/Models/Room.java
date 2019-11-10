package com.example.android.placeholder_inventory.Models;

import java.util.HashMap;
import java.util.Map;

public class Room {
    // Room properties
    public String name;
    public String userId; //Only the userId not the entire User object

    public Room() {
        // Default public constructor
    }

    public Room(String roomName, String userId) {
        this.name = roomName;
        this.userId = userId;
    }

    // Object to Map to use with FireBase
    public Map<String, Object> makeMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("name", name);
        return result;
    }
}
