package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tiles {

    final static String TILEPATH = "maps/1 Tiles/";

    final TextureRegion DIRTGRASS = new TextureRegion(new Texture(TILEPATH + "Tile_02.png"));
    final TextureRegion DIRT = new TextureRegion(new Texture(TILEPATH + "Tile_12.png"));

    public Tiles() {}
}
