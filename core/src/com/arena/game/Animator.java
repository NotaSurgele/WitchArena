package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.badlogic.gdx.Input.Keys.*;
import com.badlogic.gdx.Gdx;
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

    public Player initializePlayerAnimation(Player player)
    {
        player.idleRight_img = new Texture("B_witch_idle.png");
        player.idleLeft_img = new Texture("idle_left.png");
        player.moveRight_img = new Texture("B_witch_run.png");
        player.moveLeft_img = new Texture("run_left.png");
        player.attackRight_img = new Texture("B_witch_attack.png");
        player.attackLeft_img = new Texture("attack_left.png");
        player.idleRight = player.animator.getAnimation(player.idleRight_img, player.idleRight, 0.1f, 1, 6);
        player.idleLeft = player.animator.getAnimation(player.idleLeft_img, player.idleLeft, 0.1f, 1, 6);
        player.attackLeft = player.animator.getAnimation(player.attackLeft_img, player.attackLeft, 0.1f, 1, 9);
        player.attackRight = player.animator.getAnimation(player.attackRight_img, player.attackRight, 0.1f, 1, 9);
        player.moveRight = player.animator.getAnimation(player.moveRight_img, player.moveRight, 0.1f, 1, 8);
        player.moveLeft = player.animator.getAnimation(player.moveLeft_img, player.moveLeft, 0.1f, 1, 8);
        return player;
    }

    public TextureRegion setPlayerCurrentFrame(Player player, StateMachine state) {
        if (state.isPlaying) {
            if (state.playerisMoving) {
                if (state.playerisRotating) {
                    player.currentFrame = player.moveLeft.getKeyFrame(player.stateTime, true);
                } else {
                    player.currentFrame = player.moveRight.getKeyFrame(player.stateTime, true);
                }
            } else if (!state.playerisMoving && !state.playerisAttacking) {
                if (state.playerisRotating) {
                    player.currentFrame = player.idleLeft.getKeyFrame(player.stateTime, true);
                } else {
                    player.currentFrame = player.idleRight.getKeyFrame(player.stateTime, true);
                }
            } else if (state.playerisAttacking) {
                if (state.playerisRotating) {
                    player.currentFrame = player.attackLeft.getKeyFrame(player.attackTime, true);
                } else {
                    player.currentFrame = player.attackRight.getKeyFrame(player.attackTime, true);
                }
            }
        }
        return player.currentFrame;
    }

    public float getAttackTime(StateMachine state, float attackTime)
    {
        if (state.playerisAttacking) {
            attackTime += Gdx.graphics.getDeltaTime();
        } else {
            attackTime = 0;
        }
        return attackTime;
    }

    public Sprite updatePlayerSprite(Player player, StateMachine state, SpriteBatch batch)
    {
        if (state.playerisAttacking) {
            if (state.playerisRotating) {
                player.sprite.setBounds(player.x - 170, player.y, player.changeX, player.changeY);
            } else {
                player.sprite.setBounds(player.x, player.y, player.changeX, player.changeY);
            }
            player.sprite.draw(batch);
        } else {
            player.sprite.setBounds(player.x, player.y, player.staticX, player.staticY);
            player.sprite.draw(batch);
        }
        return player.sprite;
    }
}
