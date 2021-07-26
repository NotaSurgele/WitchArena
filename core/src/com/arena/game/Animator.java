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

    //Assets Path
    final String PLAYER_PATH = "player/";
    final String ENEMYS_PATH = "enemys/Enemy/";

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
        player.idleRight_img = new Texture(PLAYER_PATH + "B_witch_idle.png");
        player.idleLeft_img = new Texture(PLAYER_PATH + "idle_left.png");
        player.moveRight_img = new Texture(PLAYER_PATH + "B_witch_run.png");
        player.moveLeft_img = new Texture(PLAYER_PATH + "run_left.png");
        player.attackRight_img = new Texture(PLAYER_PATH + "B_witch_attack.png");
        player.attackLeft_img = new Texture(PLAYER_PATH + "attack_left.png");
        player.chargeRight_img = new Texture(PLAYER_PATH + "B_witch_charge.png");
        player.chargeLeft_img = new Texture(PLAYER_PATH + "charge_left.png");
        player.idleRight = player.animator.getAnimation(player.idleRight_img, player.idleRight, 0.1f, 1, 6);
        player.idleLeft = player.animator.getAnimation(player.idleLeft_img, player.idleLeft, 0.1f, 1, 6);
        player.attackLeft = player.animator.getAnimation(player.attackLeft_img, player.attackLeft, 0.1f, 1, 9);
        player.attackRight = player.animator.getAnimation(player.attackRight_img, player.attackRight, 0.1f, 1, 9);
        player.moveRight = player.animator.getAnimation(player.moveRight_img, player.moveRight, 0.1f, 1, 8);
        player.moveLeft = player.animator.getAnimation(player.moveLeft_img, player.moveLeft, 0.1f, 1, 8);
        player.chargeRight = player.animator.getAnimation(player.chargeRight_img, player.chargeRight, 0.1f, 1, 5);
        player.chargeLeft = player.animator.getAnimation(player.chargeLeft_img, player.chargeLeft, 0.1f, 1, 5);
        return player;
    }

    public Slime initializeSlimeAnimation(Slime slime)
    {
        slime.run_img = new Texture( "enemys/slime.png");
        slime.run = this.getAnimation(slime.run_img, slime.run, 0.1f, 18, 1);
        return slime;
    }

    public Animation<TextureRegion> initializeCursorAnimation(Cursor c, Texture t, float frameDuration, int cols, int rows)
    {
        return c.cursorAnimation = this.getAnimation(t, c.cursorAnimation, frameDuration, cols, rows);
    }

    public TextureRegion setPlayerCurrentFrame(Player player, StateMachine state) {
        if (state.isPlaying) {
            if (state.playerisMoving || state.playerIsFlying) {
                if (state.playerisRotating) {
                    player.currentFrame = player.moveLeft.getKeyFrame(player.stateTime, true);
                } else {
                    player.currentFrame = player.moveRight.getKeyFrame(player.stateTime, true);
                }
            } else if (state.playerIsCharging) {
                if (state.playerisRotating) {
                    player.currentFrame = player.chargeLeft.getKeyFrame(player.stateTime, true);
                } else {
                    player.currentFrame = player.chargeRight.getKeyFrame(player.stateTime, true);
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
        if (state.playerIsCharging && state.playerIsGrounded && !state.playerisMoving) {
            player.sprite.setBounds(player.velocity.x, player.velocity.y, player.staticX + 30, player.staticY + 40);
            player.camera.position.set(player.sprite.getX() - player.sprite.getWidth() / 2, player.sprite.getY(), 0);
            player.sprite.draw(batch);
            return player.sprite;
        } if (state.playerisAttacking) {
            if (state.playerisRotating) {
                player.sprite.setBounds(player.velocity.x - 170, player.velocity.y, player.changeX, player.changeY);
                player.camera.position.set(player.sprite.getX() + 170, player.sprite.getY(), 0);
            } else {
                player.sprite.setBounds(player.velocity.x, player.velocity.y, player.changeX, player.changeY);
            }
            player.sprite.draw(batch);
        } else {
            player.sprite.setBounds(player.velocity.x, player.velocity.y, player.staticX, player.staticY);
            player.sprite.draw(batch);
        }
        return player.sprite;
    }
}
