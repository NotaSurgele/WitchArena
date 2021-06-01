package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

//1280 720

public class Maps {
    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    TiledMapTileLayer collisionLayer;
    TiledMapImageLayer test;

    final String TESTING_MAP = "maps/testing_map/map.tmx";
    final String LAYERS = "maps/2 Background/Layers/";

    public Maps(OrthographicCamera camera) {
        this.camera = camera;
        camera.update();
        map = new TmxMapLoader().load(TESTING_MAP);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
        test = (TiledMapImageLayer) map.getLayers().get(3);
    }

    public void render(Player player, SpriteBatch batch, StateMachine state) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose()  {
        map.dispose();
    }
}
