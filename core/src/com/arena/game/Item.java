package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Item {

    public final TextureRegion TEXTUREITEM;
    public final String ITEMNAME;
    public final int ID;
    public final int HOWMANY;

    public Item(TextureRegion textureItem, String itemName, int id, int howMany) {
        this.TEXTUREITEM = textureItem;
        this.ITEMNAME = itemName;
        this.ID = id;
        this.HOWMANY = howMany;
    }
}
