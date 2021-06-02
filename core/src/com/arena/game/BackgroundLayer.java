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

    public BackgroundLayer parallax(BackgroundLayer bgLayer, SpriteBatch batch, OrthographicCamera camera, StateMachine state,
                                                                                                                Player player)
    {
        batch.begin();
        if (state.playerisMoving) {
            if (state.playerisRotating) {
                movingX1 -= 0.3f;
                movingX2 -= 0.5f ;
                movingX3 -= 0.7f ;
                movingX4 -= 1.0f ;
            } else {
                movingX1 += 0.3f ;
                movingX2 += 0.5f ;
                movingX3 += 0.7f ;
                movingX4 += 1.0f;
            }
        }
        batch.draw(bgLayer.b1,0, 500, (int)movingX1, 0, 2000, 1500);
        batch.draw(bgLayer.b2,0, 500, (int)movingX2, 0, 2000, 1000);
        batch.draw(bgLayer.b3,0, 500, (int)movingX3, 0, 2000, 1000);
        batch.draw(bgLayer.b4,0, 500, (int)movingX4, 0, 2000, 1000);
        batch.setProjectionMatrix(camera.combined);
        //batch.setProjectionMatrix(camera.view);
        batch.end();
        return bgLayer;
    }
}
