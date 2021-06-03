package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {
    SpriteBatch batch;
    BitmapFont fps;
    Input.TextInputListener input;

    public Debug()
    {
        batch = new SpriteBatch();
        fps = new BitmapFont();
    }

    private void drawFPS()
    {
        fps.setColor(Color.RED);
        fps.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1220, 700);
    }

    public void render()
    {
        batch.begin();
        drawFPS();
        batch.end();
    }

    public void dispose()
    {
        batch.dispose();
        fps.dispose();
    }
}
