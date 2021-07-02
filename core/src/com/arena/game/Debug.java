package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {
    SpriteBatch batch;
    BitmapFont fps;

    final float FPS_X = Gdx.graphics.getWidth() - 70;
    final float FPS_Y = Gdx.graphics.getHeight() - 30;

    public Debug()
    {
        batch = new SpriteBatch();
        fps = new BitmapFont();
    }

    private void drawFPS()
    {
        fps.setColor(Color.RED);
        fps.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), FPS_X, FPS_Y);
    }

    private void closeWindow()
    {
        if (Gdx.input.isKeyJustPressed(ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void update()
    {
        closeWindow();
        render();
    }

    public void render()
    {
        batch.begin();
        this.drawFPS();
        batch.end();
    }

    public void dispose()
    {
        batch.dispose();
        fps.dispose();
    }
}
