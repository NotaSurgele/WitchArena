package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class Player {
    //basic
    Input input;
    SpriteBatch batch;

    //random value
    public float x = 50;
    public float y = 50;

    //animation
    boolean isRotate;
    public Texture idle_img;
    public Texture left_right_img;
    public Animator animator;
    public Animation<TextureRegion> idle;
    public Animation<TextureRegion> left_right;
    float stateTime;
    public TextureRegion currentFrame = null;

    public Player()
    {
        animator = new Animator();
        batch = new SpriteBatch();
        idle_img = new Texture("B_witch_idle.png");
        left_right_img = new Texture("B_witch_run.png");
        left_right = animator.getAnimation(left_right_img, left_right, 0.090f, 1, 8);
        idle = animator.getAnimation(idle_img, idle, 0.090f, 1, 6);
    }

    public void Render(TextureRegion currentFrame)
    {
        batch.begin();
        batch.draw(currentFrame, x, y, 70, 100);
        batch.end();
    }

    public void Update()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = input.Move();
        Render(currentFrame);
    }

    public void Dispose()
    {
        batch.dispose();
        idle_img.dispose();
    }

    public static class Input {

        Player player;

        public Input(Player player) {
            this.player = player;
        }

        public TextureRegion Move()
        {
            player.currentFrame = player.idle.getKeyFrame(player.stateTime, true);
            if (player.isRotate && !player.currentFrame.isFlipX()) {
                player.currentFrame.flip(true, false);
            }
            if (!player.isRotate && player.currentFrame.isFlipX()) {
                player.currentFrame.flip(true, false);
            }
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q))
            {
                player.x -= 5;
                player.currentFrame = player.left_right.getKeyFrame(player.stateTime, true);
                if (!player.currentFrame.isFlipX()) {
                    player.currentFrame.flip(true, false);
                    player.isRotate = true;
                }
            }
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
                player.x += 5;
                player.currentFrame = player.left_right.getKeyFrame(player.stateTime, true);
                if (player.currentFrame.isFlipX()) {
                    player.currentFrame.flip(true, false);
                    player.isRotate = false;
                }
            }
            return player.currentFrame;
        }
    }
}
