package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundLayer {
    Texture b1;
    Texture b2;
    Texture b3;
    Texture b4;

    final String LAYERS = "maps/2 Background/Layers/";
    float movingX1 = 0;
    float movingX2 = 0;
    float movingX3 = 0;
    float movingX4 = 0;

    public BackgroundLayer()
    {
        b1 = new Texture(LAYERS + "1.png");
        b2 = new Texture(LAYERS + "2.png");
        b3 = new Texture(LAYERS + "3.png");
        b4 = new Texture(LAYERS + "4.png");
        b1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        b2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        b3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        b4.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
    }

    public void parrallaxDrawing(SpriteBatch batch, Player player, BackgroundLayer bgLayer, OrthographicCamera camera, StateMachine state)
    {
        float padding = 0;
        if (state.playerisAttacking && state.playerisRotating)
            padding = 170;
        batch.draw(bgLayer.b1, player.sprite.getX() - 1024 + padding, 2200, (int)movingX1, 0, 4000, 1000);
        batch.draw(bgLayer.b2, player.sprite.getX() - 1024 + padding, 2200, (int)movingX2, 0, 4000, 1000);
        batch.draw(bgLayer.b3, player.sprite.getX() - 1024 + padding, 2200, (int)movingX3, 0, 4000, 1000);
        batch.draw(bgLayer.b4, player.sprite.getX() - 1024 + padding, 2200, (int)movingX4, 0, 4000, 1000);
        batch.setProjectionMatrix(camera.combined);
    }

    public BackgroundLayer parallax(BackgroundLayer bgLayer, SpriteBatch batch, OrthographicCamera camera, StateMachine state,
                                                                                                                Player player)
    {
        if ((state.playerisMoving && !state.playerCollideLeft) || (state.playerisMoving && !state.playerCollideRight)) {
            if (state.playerisRotating) {
                movingX1 -= 10f * Gdx.graphics.getDeltaTime();
                movingX2 -= 15f * Gdx.graphics.getDeltaTime();
                movingX3 -= 20f * Gdx.graphics.getDeltaTime();
                movingX4 -= 25f * Gdx.graphics.getDeltaTime();
            } else {
                movingX1 += 10f * Gdx.graphics.getDeltaTime();
                movingX2 += 15f * Gdx.graphics.getDeltaTime();
                movingX3 += 20f * Gdx.graphics.getDeltaTime();
                movingX4 += 25f * Gdx.graphics.getDeltaTime();
            }
        }
        this.parrallaxDrawing(batch, player, bgLayer, camera, state);
        return bgLayer;
    }
}
