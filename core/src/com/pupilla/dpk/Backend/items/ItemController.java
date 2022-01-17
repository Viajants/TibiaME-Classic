package com.pupilla.dpk.Backend.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;

public class ItemController {


        // Hashmap where we store our items as "item name"-"item" pairs
        private HashMap<String, NewItemClass> items = new HashMap<String, NewItemClass>();

        public ItemController() {
            loadItemsFromJSON();
        }

        private void loadItemsFromJSON() {
            Json json = new Json();
            items = json.fromJson(HashMap.class, Gdx.files.internal("items.json"));
        }
    }

