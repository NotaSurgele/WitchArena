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

    //Perlin Noise
    int nOutputSize = 256;
    float[] fNoiseSeed1D;
    float[] fPerlinNoise1D;
    int nOctaveCount = 4;

    float x1 = 0;
    float x2 = 0;
    final int CHUNKSIZE = Gdx.graphics.getWidth();

    double chunkEntireSizeRight = Gdx.graphics.getWidth();
    double chunkLoadingRight = 0;
    double countChunk = 0;

    public Maps(OrthographicCamera camera)
    {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.camera.update();
        map = new TiledMap();
        collisionLayer = new TiledMapTileLayer(Gdx.graphics.getWidth() * 50, Gdx.graphics.getHeight() * 20, 32, 32);
        collisionLayer.setName("Collision");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        bgLayer = new BackgroundLayer();
        tiles = new Tiles();
        id = new Tiles.TilesId();
        this.map.getLayers().add(this.collisionLayer);
    }

    private boolean perlinNoise1D(int nCount, float[] fSeed, int nOctaves, float[] fOutput)
    {
        for (int x = 0; x < nCount; x++) {
            float fNoise = 10.0f;
            float fScaleAcc = 1.0f;
            float fScale = 1.0f;

            for (int o = 0; o < nOctaves; o++) {
                int nPitch = nCount >> o;
                int nSample1 = (x / nPitch) * nPitch;
                int nSample2 = (nSample1 + nPitch) % nCount;

                float fBlend = (float) (x - nSample1) / (float) nPitch;

                float fSample = (1.0f - fBlend) * fSeed[nSample1] + fBlend * fSeed[nSample2];

                fScaleAcc += fScale;
                fNoise += fSample * fScale;
            }
            fOutput[x] = fNoise / fScaleAcc;
        }
        return true;
    }

    private void updateSeed(float[] fNoiseSeed1D)
    {
        for (int e = 0; e < nOutputSize; e ++) {
            fNoiseSeed1D[e] = (float) Math.random() / 1f;
        }
    }

    public boolean OnUserCreate(Player player, StateMachine state)
    {
        nOutputSize = Gdx.graphics.getWidth();
        fNoiseSeed1D = new float[nOutputSize];
        fPerlinNoise1D = new float[nOutputSize];

        updateSeed(fNoiseSeed1D);
        perlinNoise1D(nOutputSize, fNoiseSeed1D, nOctaveCount, fPerlinNoise1D);
        while (!chunkLoadingSystem(player, state));
        return true;
    }

    private boolean chunkLoadingSystem(Player player, StateMachine state)
    {
        if (player.sprite.getX() >= this.chunkLoadingRight) {
            this.x1 = (float) (player.sprite.getX() + (this.chunkEntireSizeRight - player.sprite.getX()));
            this.chunkLoadingRight += this.CHUNKSIZE;
            this.chunkEntireSizeRight += this.CHUNKSIZE;
            this.countChunk++;
            this.x2 = player.sprite.getHeight();
            updateSeed(this.fNoiseSeed1D);
            perlinNoise1D(nOutputSize, fNoiseSeed1D, nOctaveCount, fPerlinNoise1D);
            drawPerlinNoise1D();
            return false;
        }
        return true;
    }

    public boolean drawPerlinNoise1D()
    {
        for (int x = 0; x < nOutputSize; x += 32) {
            int y = (int) Math.round((fPerlinNoise1D[x] * (float) Gdx.graphics.getHeight() + 800) + (float) Gdx.graphics.getHeight() + 200);
            for (int f = -y, layer = 0; f < Gdx.graphics.getHeight(); f += 32) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (layer == 0) {
                    cell.setTile(new StaticTiledMapTile(tiles.DIRTGRASS));
                    cell.getTile().setId(id.DIRTGRASS_ID);
                    this.collisionLayer.setCell((int) (x1 + x) / 32, (int) (x2 + -f) / 32, cell);
                } else if (layer >= 1 && layer <= 20) {
                    cell.setTile(new StaticTiledMapTile(tiles.DIRT));
                    cell.getTile().setId(id.DIRT_ID);
                    this.collisionLayer.setCell((int) (x1 + x) / 32, (int) (x2 + -f) / 32, cell);
                } else if (layer > 20) {
                    cell.setTile(new StaticTiledMapTile(tiles.STONE));
                    cell.getTile().setId(id.STONE_ID);
                    this.collisionLayer.setCell((int) (x1 + x) / 32, (int) (x2 + -f) / 32, cell);
                }
                layer++;
            }
        }
        mapRenderer.setView(camera);
        mapRenderer.render();
        return true;
    }

    public void addBlock(TextureRegion block, int x, int y, int blockId)
    {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(block));
        this.collisionLayer.setCell(x / 32, y / 32, cell);
        this.collisionLayer.getCell(x / 32, y / 32).getTile().setId(blockId);
    }

    public void removeBlock(int x, int y, Entity entity)
    {
        if (this.collisionLayer.getCell(x / 32, y / 32) != null) {
            int id = this.collisionLayer.getCell(x / 32, y / 32).getTile().getId();
            this.collisionLayer.setCell(x / 32, y / 32, null);
            if (!entity.player.inventory.checkItem(id))
                entity.player.inventory.addItem(id);
            else
                entity.player.inventory.incrementOwnedItem(id);
        }
    }

    public void getBlockId(int x, int y)
    {
        TiledMapTileLayer.Cell cell = this.collisionLayer.getCell(x / 32, y / 32);

        if (cell != null) {
            System.out.println(cell.getTile().getId());
        } else {
            System.out.println("Tile does not exist !");
        }
        return;
    }

    public void render(Player player, StateMachine state)
    {
        this.camera.update();
        this.batch.begin();
        bgLayer = bgLayer.parallax(this.bgLayer, this.batch, this.camera, state, player);
        chunkLoadingSystem(player, state);
        this.camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        this.batch.end();
    }

    public void dispose()
    {
        map.dispose();
    }
}
