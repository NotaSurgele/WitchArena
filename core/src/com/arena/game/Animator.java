package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
    float frameDuration;
    int FRAME_COLS;
    int FRAME_ROWS;

    public Animator(int cols, int rows)
    {
        this.FRAME_COLS = cols;
        this.FRAME_ROWS = rows;
    }

    public Animation<TextureRegion> getAnimation(Texture img, Animation<TextureRegion> animation, float frameDuration)
    {
        if (img == null) {
            System.err.println("You should initialise sheet!!");
            return null;
        }
        this.frameDuration = frameDuration;
        TextureRegion[][] tmp = TextureRegion.split(
                        img,
                img.getWidth() / FRAME_COLS,
                img.getHeight() / FRAME_ROWS
        );
        TextureRegion[] animationFrame = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrame[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(frameDuration, animationFrame);
        return animation;
    }
}
