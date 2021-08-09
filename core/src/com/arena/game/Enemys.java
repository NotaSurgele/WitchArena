package com.arena.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemys {

    Slime slime[];

    public Enemys() {
        slime = new Slime[3];
        slime[0] = new Slime(1280, 5000, 130, 130);
        slime[1] = new Slime(2500, 5000, 130, 130);
        slime[2] = new Slime(5000, 5000, 130, 130);
    }

    public void update(OrthographicCamera camera, TiledMapTileLayer colLayer, Player player) {
        for (Slime s : slime) {
            s.update(camera, colLayer, player);
        }
    }

    public void dispose()
    {
        for (Slime s : slime) {
            s.dispose();
        }
    }
}
