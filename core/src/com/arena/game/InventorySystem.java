package com.arena.game;

import java.util.ArrayList;

public class InventorySystem {

    ArrayList<Item> inventory;

    public InventorySystem() {
        inventory = new ArrayList<>();
    }

    public void addItem(int id, Items items)
    {
        Item toAdd = items.items.get(id);

        toAdd.HOWMANY += 1;
        this.inventory.add(toAdd);
    }

    public Item getItem(int id)
    {
        return this.inventory.get(id);
    }

    public Item getItemWithString(String toGet)
    {
        for (Item item : inventory) {
            if (item.ITEMNAME.equalsIgnoreCase(toGet)) {
                return item;
            }
        }
        System.err.println(toGet + ": This item does not exist !");
        return null;
    }

    public void removeItem(int id)
    {
        this.inventory.remove(id);
    }
}
