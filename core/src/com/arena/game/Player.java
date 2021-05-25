package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    SpriteBatch batch;
    Texture img;
    Animator animator;
    Animation<TextureRegion>idle;
    float stateTime;
    TextureRegion currentFrame = null;

    public Player()
    {
        animator = new Animator(1, 6);
        batch = new SpriteBatch();
        img = new Texture("B_witch_idle.png");
        idle = animator.getAnimation(img, idle, 0.1f);
    }


    public void render(TextureRegion currentFrame)
    {
        batch.begin();
        batch.draw(currentFrame, 50, 50, 70, 100);
        batch.end();
    }

    public void Update()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = idle.getKeyFrame(stateTime, true);
        render(currentFrame);
    }

    public void dispose()
    {
        batch.dispose();
        img.dispose();
    }
}
