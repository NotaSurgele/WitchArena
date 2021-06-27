package com.arena.game;

import java.util.ArrayList;

public class InventorySystem {

    ArrayList<Item> inventory;
    Items items;

    public InventorySystem() {
        inventory = new ArrayList<>();
        items = new Items();
    }

    public void addItem(int itemId)
    {
        Item toAdd = items.items.get(itemId);

        toAdd.HOWMANY += 1;
        this.inventory.add(toAdd);
    }

    public void incrementOwnedItem(int id)
    {
        for (int toIncrement = 0; toIncrement != inventory.size(); toIncrement++) {
            if (inventory.get(toIncrement).ID == id)
                inventory.get(toIncrement).HOWMANY += 1;
        }
    }

    public boolean checkItem(int id)
    {
        for (int toCheck = 0; toCheck != this.inventory.size(); toCheck++) {
            if (this.inventory.get(toCheck) != null) {
                if (this.inventory.get(toCheck).ID == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public Item getItem(int id)
    {
        for (Item item : this.inventory) {
            if (item.ID == id)
                return item;
        }
        return null;
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

    public void destroyItem(int toRemove)
    {
        if (this.inventory.get(toRemove).HOWMANY <= 0)
            this.inventory.remove(toRemove);
    }

    public void removeOwnedItem(int id)
    {
        for (int toRemove = 0; toRemove != this.inventory.size(); toRemove++) {
            if (this.inventory.get(toRemove).ID == id) {
                this.inventory.get(toRemove).HOWMANY -= 1;
                destroyItem(toRemove);
                return;
            }
        }
    }
}
