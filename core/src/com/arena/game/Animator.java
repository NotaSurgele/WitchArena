package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;

public class Animator {
    float frameDuration;
    int FRAME_COLS;
    int FRAME_ROWS;

    public Animator() {}

    public Animation<TextureRegion> getAnimation(Texture img, Animation<TextureRegion> animation, float frameDuration,
                                                                                                    int cols, int rows)
    {
        if (img == null) {
            System.err.println("You should initialise Texture!!");
            return null;
        }
        this.FRAME_COLS = cols;
        this.FRAME_ROWS = rows;
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
