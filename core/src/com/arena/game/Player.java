package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.badlogic.gdx.Input.Keys.*;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import sun.jvm.hotspot.gc.shared.Space;

public class Player {
    //basic
    Input input;
    SpriteBatch batch;

    //random value
    public float x = 50;
    public float y = 50;

    //animation
    public boolean isRotate;
    public Texture idle_img;
    public Texture left_right_img;
    public Texture attackImg;
    Animator animator;
    public Animation<TextureRegion> idle;
    public Animation<TextureRegion> left_right;
    public Animation<TextureRegion> attack;
    float stateTime;
    public TextureRegion currentFrame = null;

    //Statemachine
    public StateMachine state;

    public Player()
    {
        state = new StateMachine();
        animator = new Animator();
        batch = new SpriteBatch();
        idle_img = new Texture("B_witch_idle.png");
        left_right_img = new Texture("B_witch_run.png");
        left_right = animator.getAnimation(left_right_img, left_right, 0.090f, 1, 8);
        idle = animator.getAnimation(idle_img, idle, 0.090f, 1, 6);
        attackImg = new Texture("B_witch_attack.png");
        attack = animator.getAnimation(attackImg, attack, 0.1f, 1, 9);
    }

    public void render(TextureRegion currentFrame, StateMachine state)
    {
        batch.begin();
        if (state.isAttacking)
        {
            batch.draw(currentFrame, x, y, 200, 100);
        } else {
            batch.draw(currentFrame, x, y, 70, 100);
        }
        batch.end();
    }

    public void update()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = input.move();
        currentFrame = input.attack();
        render(currentFrame, state);
    }

    public void dispose()
    {
        batch.dispose();
        idle_img.dispose();
    }

    public static class Input {

        Player player;

        public Input(Player player) {
            this.player = player;
        }

        private Player checkIdleFlip(Player player)
        {
            player.state.isMoving = false;
            player.state.isAttacking = false;
            player.currentFrame = player.idle.getKeyFrame(player.stateTime, true);
            if (player.isRotate && !player.currentFrame.isFlipX()) {
                player.currentFrame.flip(true, false);
            } if (!player.isRotate && player.currentFrame.isFlipX()) {
                player.currentFrame.flip(true, false);
            }
            return player;
        }

        public TextureRegion move()
        {
            player = checkIdleFlip(player);
            if (Gdx.input.isKeyPressed(Q)) {
                player.state.isMoving = true;
                player.x -= 4f;
                player.currentFrame = player.left_right.getKeyFrame(player.stateTime, true);
                if (!player.currentFrame.isFlipX()) {
                    player.currentFrame.flip(true, false);
                    player.isRotate = true;
                }
            } if (Gdx.input.isKeyPressed(D)) {
                player.x += 4f;
                player.state.isMoving = true;
                player.currentFrame = player.left_right.getKeyFrame(player.stateTime, true);
                if (player.currentFrame.isFlipX()) {
                    player.currentFrame.flip(true, false);
                    player.isRotate = false;
                }
            }
            return player.currentFrame;
        }

        public TextureRegion attack()
        {
            player.currentFrame = move();
            if (player.state.isMoving) {
                return player.currentFrame;
            } if (Gdx.input.isKeyPressed(SPACE)) {
                player.state.isAttacking = true;
                player.currentFrame = player.attack.getKeyFrame(player.stateTime, true);
                if (player.isRotate && !player.currentFrame.isFlipX() && player.state.isAttacking) {
                    player.currentFrame.flip(true, false);
                } if (!player.isRotate && player.currentFrame.isFlipX()) {
                    player.currentFrame.flip(true, false);
                }
            }
            return player.currentFrame;
        }
    }
}
