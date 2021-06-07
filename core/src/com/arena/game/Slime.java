package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import javax.swing.plaf.nimbus.State;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;

public class Slime {
    Animator animator;
    SpriteBatch batch;
    Sprite sprite;
    Collider collider;
    StateMachine state;

    public Texture run_img;
    public Animation<TextureRegion> run;
    public TextureRegion currentFrame;
    public TextureRegion[] frames;
    int frame = 0;

    Vector2 velocity;

    final static float JUMPFORCE = 500f;
    final static float MOVEX = 50f;
    final static int SLIMEFRAME = 17;

    float gravity = 50 * 9.81f;
    float stateTime = 0;
    float deltaTime = 0;
    float coolDown = 0;
    float jumpForce = 500f;
    float moveX = 50f;
    float animCoolDown = 0f;

    final String SLIME = "Slime/";

    public Slime()
    {
        animator = new Animator();
        animator.initializeSlimeAnimation(this);
        currentFrame = run.getKeyFrame(stateTime);
        batch = new SpriteBatch();
        sprite = new Sprite();
        sprite.setBounds(0, 0, 130, 130);
        sprite.setRegion(currentFrame);
        velocity = new Vector2().add(200, 1000);
        sprite.setPosition(velocity.x, velocity.y);
        collider = new Collider();
        state = new StateMachine();
        frames = run.getKeyFrames();
    }

    private void gravity(float deltaTime, StateMachine state)
    {
        if (!state.slimeIsGrounded) {
            velocity.y += -gravity * deltaTime;
        }
        return;
    }

    private float checkCoolDown(float coolDown, StateMachine state)
    {
        if (coolDown >= 3f) {
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

    private int slimeAnimation(int frame, float frameSpeed, TextureRegion[] frames, StateMachine state)
    {
        if (state.slimeISJumping) {
            if (frame != SLIMEFRAME) {
                if (this.jumpForce >= 250 && this.jumpForce <= 200) {
                    frame = 8;
                } else {
                    if (this.stateTime >= frameSpeed) {
                        frame++;
                        this.stateTime = 0;
                    }
                }
            }
        } else if (frame > 0 && frame != SLIMEFRAME && !state.slimeISJumping) {
            if (this.stateTime >= frameSpeed) {
                frame++;
                this.stateTime = 0;
            }
        }
        this.currentFrame = frames[frame];
        return frame;
    }

    private float move(Vector2 velocity, float deltaTime, StateMachine state, float coolDown)
    {
        if (state.slimeIsGrounded && jumpForce <= 0) {
            jumpForce = 0;
            moveX = 0;
            state.slimeISJumping = false;
        }
        coolDown = checkCoolDown(coolDown, state);
        velocity.x += moveX * deltaTime;
        velocity.y += jumpForce * deltaTime;
        jumpForce += -(1000f * deltaTime);
        if (coolDown >= 3f)
            coolDown = 0;
        return coolDown;
    }

    public void render(OrthographicCamera camera, float animCoolDown, StateMachine state)
    {
        batch.begin();
        this.frame = slimeAnimation(this.frame, 0.1f, frames, state);
        if (this.frame == SLIMEFRAME) {
            this.frame = 0;
        }
        sprite.setRegion(currentFrame);
        sprite.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        batch.end();
    }

    public void update(OrthographicCamera camera, TiledMapTileLayer collisionLayer)
    {
        coolDown += Gdx.graphics.getDeltaTime();
        deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += Gdx.graphics.getDeltaTime();
        animCoolDown += Gdx.graphics.getDeltaTime();

        //currentFrame = run.getKeyFrame(stateTime, true);
        render(camera, animCoolDown, this.state);
        sprite.setPosition(velocity.x, velocity.y);
        state = collider.getSlimeWorldCollision(this, state, collisionLayer);
        this.coolDown = move(this.velocity, deltaTime, this.state, this.coolDown);
    }

    public void dispose()
    {
        batch.dispose();
    }
}
