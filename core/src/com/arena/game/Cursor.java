package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Cursor {
    Animator animator;
    Sprite sprite;
    Animation<TextureRegion> cursorAnimation;
    TextureRegion currentFrame;
    TextureRegion[] cursorFrames;

    float animationTime = 0;
    float animationCooldown = 0;
    float frameDuration = 0;

    final float COOLDOWN = 5f;
    final int ANIMATIONMAX;

    boolean reset = false;

    int frame = 0;
    boolean flip = false;
    boolean cursorCatch = true;

    public Cursor() {
        this.animator = new Animator();
        this.cursorAnimation = this.animator.initializeCursorAnimation(this, new Texture("GUI/cursor.png"), 0.1f, 5, 1);
        this.currentFrame = this.cursorAnimation.getKeyFrame(this.animationTime);
        this.sprite = new Sprite();
        this.sprite.setBounds(Gdx.input.getX(), Gdx.input.getY(), this.currentFrame.getRegionWidth(), this.currentFrame.getRegionHeight());
        this.sprite.setRegion(this.currentFrame);
        this.cursorFrames = this.cursorAnimation.getKeyFrames();
        this.ANIMATIONMAX = this.cursorFrames.length - 1;
        this.frameDuration = this.cursorAnimation.getFrameDuration();
    }

    private void doCursorAnimation(Sprite sprite)
    {
        if (!this.flip) {
            if (this.frame != this.ANIMATIONMAX) {
                if (this.animationTime >= this.frameDuration) {
                    this.frame++;
                    this.animationTime = 0f;
                }
            }
        }
        if (this.frame >= this.ANIMATIONMAX) {
            this.flip = !this.flip;
            this.frame = this.ANIMATIONMAX;
        }
        if (this.flip) {
            if (this.frame > 0) {
                if (this.animationTime >= this.frameDuration) {
                    this.frame--;
                    this.animationTime = 0f;
                }
            }
            if (this.frame <= 0) {
                this.flip = !this.flip;
                this.frame = 0;
                this.animationCooldown = 0f;
            }
        }
        this.currentFrame = this.cursorFrames[frame];
        sprite.setRegion(this.currentFrame);
    }

    private void setCursorPosition(Sprite sprite)
    {
        sprite.setPosition(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()) - this.sprite.getHeight());
    }

    private void cursorOverScreen()
    {
        if ((this.sprite.getX() >= 10 && this.sprite.getX() <= Gdx.graphics.getWidth() - 10)) {
            Gdx.input.setCursorCatched(true);
            this.reset = false;
        } else {
            Gdx.input.setCursorCatched(false);
            if (!this.reset) {
                Gdx.input.setCursorPosition((int)this.sprite.getX(), Gdx.input.getY());
                this.reset = true;
            }
        }
    }

    public void update(SpriteBatch batch)
    {
        this.animationCooldown += Gdx.graphics.getDeltaTime();
        this.animationTime += Gdx.graphics.getDeltaTime();
        this.sprite.draw(batch);
        if (this.animationCooldown >= COOLDOWN) {
            this.doCursorAnimation(this.sprite);
        }
        this.setCursorPosition(this.sprite);
        this.cursorOverScreen();
    }
}
