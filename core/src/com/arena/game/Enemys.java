package com.arena.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemys {
    Slime slime;

    public Enemys() {
        slime = new Slime();
    }

    public void update(OrthographicCamera camera, StateMachine state, TiledMapTileLayer colLayer) {
        slime.update(camera, colLayer);
    }

    public void dispose()
    {
        slime.dispose();
    }
}
