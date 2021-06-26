package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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

    Tiles tiles;
    Tiles.TilesId id;

    final String TESTING_MAP = "maps/testing_map/map.tmx";

    //Perlin Noise
    int nOutputSize = 256;
    float[] fNoiseSeed1D;
    float[] fPerlinNoise1D;
    int nOctaveCount = 11;
    int i = 0;

    float x1 = 0;
    float x2 = 0;
    final int CHUNKSIZE = Gdx.graphics.getWidth();

    double chunkEntireSizeRight = Gdx.graphics.getWidth();
    double chunkLoadingRight = 640;
    double countChunk = 0;

    public Maps(OrthographicCamera camera, SpriteBatch batch)
    {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.camera.update();
        map = new TiledMap();
        collisionLayer = new TiledMapTileLayer(10000, Gdx.graphics.getHeight(), 32, 32);
        map.getLayers().add(collisionLayer);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        bgLayer = new BackgroundLayer();
        tiles = new Tiles();
        id = new Tiles.TilesId();
        OnUserCreate();
    }

    private boolean perlinNoise1D(int nCount, float[] fSeed, int nOctaves, float[] fOutput)
    {
        for (int i = 0; i < nCount; i++) {
            float fNoise = 0.0f;
            float fScale = 2.0f; // To change for creating mountain

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
        return true;
    }

    private void updateSeed(float[] fNoiseSeed1D)
    {
        for (int e = 0; e < nOutputSize; e += 32) {
            fNoiseSeed1D[e] = (float) Math.random() / 1f;
                /*map.getLayers().remove(collisionLayer);
                collisionLayer = new TiledMapTileLayer(3000, Gdx.graphics.getHeight(), 32, 32);*/
            //System.out.println(this.countChunk);
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

    private void chunkLoadingSystem(Player player, StateMachine state)
    {
        if (!state.playerisRotating) {
            if (player.sprite.getX() >= this.chunkLoadingRight) {
                this.x1 = (float) (player.sprite.getX() + (this.chunkEntireSizeRight - player.sprite.getX()));
                this.chunkLoadingRight += this.CHUNKSIZE;
                this.chunkEntireSizeRight += this.CHUNKSIZE;
                map.getLayers().add(collisionLayer);
                updateSeed(this.fNoiseSeed1D);
                this.countChunk++;
                this.x2 = player.sprite.getHeight();
            }
        }
    }

    public boolean drawPerlinNoise1D()
    {
        for (int x = 0; x < nOutputSize; x += 32) {
            int y = (int) ((fPerlinNoise1D[x] * (float) Gdx.graphics.getHeight() / 2) + (float) Gdx.graphics.getHeight() / 2);
            for (int f = -y, i = 0; f < Gdx.graphics.getHeight() / 2; f += 32) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (i == 0) {
                    cell.setTile(new StaticTiledMapTile(tiles.DIRTGRASS));
                    cell.getTile().setId(id.DIRTGRASS_ID);
                    this.collisionLayer.setCell((int) (x1 + x) / 32, (int) (x2 + -f) / 32, cell);
                } else {
                    cell.setTile(new StaticTiledMapTile(tiles.DIRT));
                    cell.getTile().setId(id.DIRT_ID);
                    this.collisionLayer.setCell((int) (x1 + x) / 32, (int) (x2 + -f) / 32, cell);
                }
                i++;
            }
        }
        return true;
    }

    public void addBlock(TextureRegion block, int x, int y)
    {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(block));
        this.collisionLayer.setCell(x / 32, y / 32, cell);
    }

    public void getBlockId(int x, int y)
    {
        System.out.println(this.collisionLayer.getCell(x / 32, y / 32).getTile().getId());
    }

    public void render(Player player, StateMachine state)
    {
        this.camera.update();
        this.batch.begin();
        bgLayer = bgLayer.parallax(this.bgLayer, this.batch, this.camera, state, player);
        chunkLoadingSystem(player, state);
        perlinNoise1D(nOutputSize, fNoiseSeed1D, nOctaveCount, fPerlinNoise1D);
        drawPerlinNoise1D();
        this.camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        this.batch.end();
    }

    public void dispose() {
        map.dispose();
    }
}
