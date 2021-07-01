package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public ArrayList<Item> items;
    final public int ITEMS_SIZE = 3;

    final private String TILES = "maps/1 Tiles/";

    public Items() {
        this.items = setItems();
    }

    private ArrayList<Item> setItems()
    {
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item(new TextureRegion(new Texture(TILES + "Tile_02.png")), "Dirtgrass",0, 0));
        items.add(new Item(new TextureRegion(new Texture(TILES + "Tile_12.png")), "Dirt",1, 0));
        return items;
    }

    public Item getItemWithString(String toGet)
    {
        for (Item item : this.items) {
            if (item.ITEMNAME.equalsIgnoreCase(toGet)) {
                return item;
            }
        }
        System.err.println(toGet + ": This item does not exist !");
        return null;
    }

    public Item getWithId(int toGet)
    {
        return items.get(toGet);
    }
}
