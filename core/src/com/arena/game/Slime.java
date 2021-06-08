package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import javax.swing.plaf.nimbus.State;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;

public class Slime {
    Animator animator;
    SpriteBatch batch;
    Sprite sprite;
    Collider collider;
    StateMachine state;
    Circle agroZone;

    public Texture run_img;
    public Animation<TextureRegion> run;
    public TextureRegion currentFrame;
    public TextureRegion[] frames;
    int frame = 0;

    Vector2 velocity;

    final static float JUMPFORCE = 500f;
    final static float MOVEX = 50f;
    final static int SLIMEFRAME = 17;
    final static int MAXCOOLDOWN = 5;
    final static int MINCOOLDOWN = 3;
    int theCoolDown = 0;

    float gravity = 50 * 9.81f;
    float stateTime = 0;
    float deltaTime = 0;
    float coolDown = 0;
    float jumpForce = 500f;
    float moveX = 50f;

    final String SLIME = "Slime/";

    public Slime(float posx, float posy, float width, float height)
    {
        animator = new Animator();
        animator.initializeSlimeAnimation(this);
        currentFrame = run.getKeyFrame(stateTime);
        batch = new SpriteBatch();
        sprite = new Sprite();
        sprite.setBounds(0, 0, width, height);
        sprite.setRegion(currentFrame);
        velocity = new Vector2().add(posx, posy);
        sprite.setPosition(velocity.x, velocity.y);
        collider = new Collider();
        state = new StateMachine();
        frames = run.getKeyFrames();
        agroZone = collider.createCircle(150f, velocity);
        theCoolDown = (int)(MINCOOLDOWN + (Math.random() * ((MAXCOOLDOWN - MINCOOLDOWN) + 1)));
    }

    private float checkCoolDown(float coolDown, StateMachine state, float theCoolDown)
    {
        if ((int)coolDown >= theCoolDown) {
            if (state.slimeCollideLeft) {
                this.jumpForce = JUMPFORCE;
                this.moveX = MOVEX;
                state.slimeGoRight = true;
                state.slimeGoLeft = false;
            } else if (state.slimeCollideRight) {
                this.jumpForce = JUMPFORCE;
                this.moveX = -MOVEX;
                state.slimeGoLeft = true;
                state.slimeGoRight = false;
            } else {
                this.jumpForce = JUMPFORCE;
                this.moveX = 0;
                if (state.slimeGoLeft) {
                    this.moveX = -MOVEX;
                } else {
                    this.moveX = MOVEX;
                }
            }
            state.slimeISJumping = true;
        }
        return coolDown;
    }

    private int isAnimationFinished(int frame, float frameSpeed)
    {
        if (this.stateTime >= frameSpeed) {
            this.stateTime = 0;
            frame++;
        }
        return frame;
    }

    private int slimeAnimation(int frame, float frameSpeed, TextureRegion[] frames, StateMachine state)
    {
        if (state.slimeISJumping) {
            if (frame != SLIMEFRAME) {
                if (this.stateTime >= frameSpeed) {
                    frame++;
                    this.stateTime = 0;
                }
            }
        } if (frame > 0 && frame != SLIMEFRAME)
            frame = isAnimationFinished(frame, frameSpeed);
        this.currentFrame = frames[frame];
        return frame;
    }

    private int loopAnimation(int frame)
    {
        return (frame == SLIMEFRAME) ? 0: frame;
    }

    private float move(Vector2 velocity, float deltaTime, StateMachine state, float coolDown)
    {
        if (state.slimeIsGrounded && jumpForce <= 0) {
            jumpForce = 0;
            moveX = 0;
            state.slimeISJumping = false;
        }
        coolDown = checkCoolDown(coolDown, state, this.theCoolDown);
        velocity.x += moveX * deltaTime;
        velocity.y += jumpForce * deltaTime;
        jumpForce += -(1000f * deltaTime);
        if ((int)coolDown >= this.theCoolDown)
            coolDown = 0;
        return coolDown;
    }

    public void render(OrthographicCamera camera, StateMachine state)
    {
        batch.begin();
        this.frame = slimeAnimation(this.frame, 0.1f, frames, state);
        this.frame = loopAnimation(this.frame);
        sprite.setRegion(currentFrame);
        sprite.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        batch.end();
    }

    public void update(OrthographicCamera camera, TiledMapTileLayer collisionLayer, Player player)
    {
        coolDown += Gdx.graphics.getDeltaTime();
        deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += Gdx.graphics.getDeltaTime();
        agroZone.setPosition(velocity);
        state = collider.checkSlimeAggroZone(this.agroZone, player.sprite.getX(), player.sprite.getY(), state);
        render(camera, this.state);
        sprite.setPosition(velocity.x, velocity.y);
        state = collider.getSlimeWorldCollision(this, state, collisionLayer);
        this.coolDown = move(this.velocity, deltaTime, this.state, this.coolDown);
    }

    public void dispose()
    {
        batch.dispose();
    }
}
