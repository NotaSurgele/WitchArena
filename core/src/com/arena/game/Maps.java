package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import static com.badlogic.gdx.Input.Keys.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//1280 720

public class Maps {
    TiledMap map;
    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    TiledMapTileLayer collisionLayer;
    SpriteBatch batch;
    BackgroundLayer bgLayer;
    Texture grass;
    Texture dirt;

    final String TESTING_MAP = "maps/testing_map/map.tmx";

    //Perlin Noise
    int nOutputSize = 256;
    float[] fNoiseSeed1D;
    float[] fPerlinNoise1D;
    int nOctaveCount = 11;

    public Maps(OrthographicCamera camera, SpriteBatch batch)
    {
        this.camera = camera;
        this.batch = new SpriteBatch();
        camera.update();
        map = new TmxMapLoader().load(TESTING_MAP);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
        bgLayer = new BackgroundLayer();
        grass = new Texture("maps/1 Tiles/Tile_02.png");
        dirt = new Texture("maps/1 Tiles/Tile_12.png");
        OnUserCreate();
    }

    private void perlinNoise1D(int nCount, float[] fSeed, int nOctaves, float[] fOutput)
    {
        for (int i = 0; i < nCount; i++) {
            float fNoise = 0.0f;
            float fScale = 1.0f;

            for (int j = 0; j < nOctaves; j++) {
                int nPitch = nCount >> j;
                int nSample1 = (i / nPitch) * nPitch;
                int nSample2 = (nSample1 + nPitch) % nCount;

                float fBlend = (float) (i - nSample1) / (float) nPitch;
                float fSample = (1.0f - fBlend) * fSeed[nSample1] + fBlend * fSeed[nSample2];
                fNoise += fSample * fScale;
                fScale = fScale / 2.0f;
            }
            fOutput[i] = fNoise;
        }
    }

    private boolean OnUserCreate()
    {
        nOutputSize = Gdx.graphics.getWidth();
        fNoiseSeed1D = new float[nOutputSize];
        fPerlinNoise1D = new float[nOutputSize];

        for (int i = 0; i < nOutputSize; i++) {
            fNoiseSeed1D[i] = (float) Math.random() / 1f;
        }
        return true;
    }

    private void drawPerlinNoise1D()
    {
        for (int x = 0; x < nOutputSize; x += 32) {
            int y = (int) ((fPerlinNoise1D[x] * (float) Gdx.graphics.getHeight() / 2) + (float) Gdx.graphics.getHeight() / 2);
            for (int f = -y; f < Gdx.graphics.getHeight() / 2; f += 32) {
                batch.draw(dirt, x, -f);
            }
        }
    }

    public void render(Player player, StateMachine state)
    {
        this.batch.begin();
        batch.setProjectionMatrix(player.camera.combined);
        bgLayer = bgLayer.parallax(bgLayer, this.batch, this.camera, state, player);
        perlinNoise1D(nOutputSize, fNoiseSeed1D, nOctaveCount, fPerlinNoise1D);
        drawPerlinNoise1D();

        if (Gdx.input.isKeyJustPressed(Z)) {
            for (int i = 0; i < nOutputSize; i++) fNoiseSeed1D[i] = (float)Math.random() / 1f;
        }

        //mapRenderer.setView(camera);
        //mapRenderer.render();
        this.batch.end();
    }

    public void dispose() {
        map.dispose();
    }
}
