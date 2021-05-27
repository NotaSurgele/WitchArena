package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import static com.badlogic.gdx.Input.Keys.*;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import jdk.javadoc.internal.doclets.formats.html.markup.BodyContents;
import sun.jvm.hotspot.gc.shared.Space;

public class Player {

    //basic
    OrthographicCamera camera;
    Sprite sprite;
    Texture texture;

    //value
    public float x;
    public float y;
    public float moveSpeed = 5f;
    static final int changeX = 250;
    static final int changeY = 100;
    static final int staticX = 80;
    static final int staticY = 100;

    Texture img;


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
    float stateTime = 0;
    float attackTime = 0;

    //useful

    public Player(float posX, float posY)
    {
        this.x = posX;
        this.y = posY;
        camera = new OrthographicCamera(1280, 720);
        camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.update();
        animator = new Animator();
        animator.initializePlayerAnimation(this);
        currentFrame = idleRight.getKeyFrame(stateTime, true);
        camera = new OrthographicCamera(1280 ,720);
        sprite = new Sprite();
        sprite.setBounds(0, 0, staticX, staticY);
        sprite.setRegion(currentFrame);
        sprite.setPosition(x, y);
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
    public void render(TextureRegion currentFrame, StateMachine state, SpriteBatch batch)
    {
        camera.position.set(x, y, 0);
        attackTime = animator.getAttackTime(state, attackTime);
        sprite = animator.updatePlayerSprite(this, state, batch);
    }

    public void update(StateMachine state, SpriteBatch batch)
    {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.setPlayerCurrentFrame(this, state);
        sprite.setRegion(currentFrame);
        render(currentFrame, state, batch);
    }

    public void dispose()
    {
        idleRight_img.dispose();
    }

}
