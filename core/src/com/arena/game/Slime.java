package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Slime {
    Animator animator;
    SpriteBatch batch;
    Sprite sprite;

    public Texture run_img;
    public Animation<TextureRegion> run;
    public TextureRegion currentFrame;

    Vector2 velocity;

    float stateTime = 0;
    float deltaTime = 0;

    final String SLIME = "Slime/";

    public Slime()
    {
        animator = new Animator();
        animator.initializeSlimeAnimation(this);
        currentFrame = run.getKeyFrame(stateTime);
        batch = new SpriteBatch();
        sprite = new Sprite();
        sprite.setBounds(0, 0, 50, 50);
        sprite.setRegion(currentFrame);
        sprite.setPosition(200, 200);
        velocity = new Vector2();
    }

    public void render(float stateTime)
    {
        batch.begin();
        currentFrame = run.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.draw(batch);
        batch.end();
    }

    private void gravity()
    {

    }

    public void update()
    {
        deltaTime += Gdx.graphics.getDeltaTime();
        stateTime += Gdx.graphics.getDeltaTime();
        render(stateTime);
    }

    public void dispose()
    {

    }
}
