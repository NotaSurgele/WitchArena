package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

//1280 720

public class Maps {
    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;

    final String TESTING_MAP = "maps/testing_map/map.tmx";

    public Maps(OrthographicCamera camera) {
        this.camera = camera;
        camera.update();
        map = new TmxMapLoader().load(TESTING_MAP);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose()  {
        map.dispose();
    }
}
