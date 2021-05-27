package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
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
import jdk.javadoc.internal.doclets.formats.html.markup.BodyContents;
import sun.jvm.hotspot.gc.shared.Space;

public class Player {
    //basic
    SpriteBatch batch;

    //value
    public float x;
    public float y;
    public float moveSpeed = 5f;

    //animation

    Animator animator;
    public Texture idleRight_img;
    public Texture idleLeft_img;
    public Texture attackRight_img;
    public Texture attackLeft_img;
    public Texture moveRight_img;
    public Texture moveLeft_img;
    public Animation<TextureRegion> idleRight;
    public Animation<TextureRegion> idleLeft;
    public Animation<TextureRegion> moveRight;
    public Animation<TextureRegion> moveLeft;
    public Animation<TextureRegion> attackRight;
    public Animation<TextureRegion> attackLeft;
    public TextureRegion currentFrame = null;
    float stateTime;

    //useful

    public Player(float posX, float posY)
    {
        this.x = posX;
        this.y = posY;
        animator = new Animator();
        batch = new SpriteBatch();
        animator.initializePlayerAnimation(this);
        currentFrame = idleRight.getKeyFrame(stateTime, true);
    }

    public void move()
    {
        if (Gdx.input.isKeyPressed(D)) {
            this.x += moveSpeed;
        }
        if (Gdx.input.isKeyPressed(Q)) {
            this.x -= moveSpeed;
        }
    }

    //Libgdx Rendering function
    public void render(TextureRegion currentFrame, StateMachine state)
    {
        batch.begin();
        if (state.playerisRotating && state.playerisAttacking) {
            batch.draw(currentFrame, (x - 65), y);
        } else {
            batch.draw(currentFrame, x, y);
        }
        batch.end();
    }

    public void update(StateMachine state)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.setPlayerCurrentFrame(this, state);
        render(currentFrame, state);
    }

    public void dispose()
    {
        batch.dispose();
        idleRight_img.dispose();
    }

}
