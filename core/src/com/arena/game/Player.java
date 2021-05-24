package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private SpriteBatch batch;
    private Texture img;

    public void SetSprite(String imgPath)
    {
        this.batch = new SpriteBatch();
        this.img = new Texture(imgPath);
    }

    public void Draw()
    {
        if (this.img == null) {
            System.err.println("Couldn't open Texture");
            return;
        }
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    public void toDispose()
    {
        batch.dispose();
        img.dispose();
    }

}
