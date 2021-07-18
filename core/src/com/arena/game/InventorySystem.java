package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.*;

import java.util.ArrayList;

public class InventorySystem {

    ArrayList<Item> inventory;
    Items items;
    Sprite inventoryGUI;
    Texture inventoryGUITexture;

    private boolean inventoryRender = false;

    public InventorySystem() {
        this.inventory = new ArrayList<>();
        this.items = new Items();
        this.inventoryGUITexture = new Texture("GUI/Inventory-prototype.png");
        this.inventoryGUI = new Sprite();
        this.inventoryGUI.setRegion(this.inventoryGUITexture);
        this.inventoryGUI.setBounds(0,0, 270, 50);
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

    public void openAndCloseInventory(Player player, SpriteBatch batch)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            this.inventoryRender = !this.inventoryRender;
        } if (this.inventoryRender) {
            this.inventoryGUI.setPosition(player.getX() - (this.inventoryGUI.getWidth() / 2), player.getY() - (this.inventoryGUI.getHeight() / 2));
            this.inventoryGUI.draw(batch);
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

    public void update(Player player, SpriteBatch batch)
    {
        this.openAndCloseInventory(player, batch);
    }

    public void dispose()
    {
        this.inventoryGUITexture.dispose();
    }
}
