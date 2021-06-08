package com.arena.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemys {
    Slime slime[];

    public Enemys() {
        slime = new Slime[2];
        slime[0] = new Slime(200, 1000, 130, 130);
        slime[1] = new Slime(800, 1000, 130, 130);
    }

    public void update(OrthographicCamera camera, TiledMapTileLayer colLayer, Player player) {
        slime[0].update(camera, colLayer, player);
        slime[1].update(camera, colLayer, player);
    }

    public void dispose()
    {
        slime[0].dispose();
        slime[1].dispose();
    }
}
