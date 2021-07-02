package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tiles {

    final public String TILEPATH = "maps/1 Tiles/";

    final TextureRegion DIRTGRASS = new TextureRegion(new Texture(TILEPATH + "Tile_02.png"));
    final TextureRegion DIRT = new TextureRegion(new Texture(TILEPATH + "Tile_12.png"));
    final TextureRegion STONE = new TextureRegion(new Texture("maps/Stone_Tile.png"));
    public Tiles() { }

    public static class TilesId {

        public final int DIRTGRASS_ID = 0;
        public final int DIRT_ID = 1;
        public final int STONE_ID = 2;

        public TilesId() {}
    }
}
