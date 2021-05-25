package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {
    SpriteBatch batch;
    BitmapFont fps;

    public Debug()
    {
        batch = new SpriteBatch();
        fps = new BitmapFont();
    }

    public void render()
    {
        batch.begin();
        fps.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1220, 700);
        batch.end();
    }

    public void dispose()
    {
        batch.dispose();
        fps.dispose();
    }
}
