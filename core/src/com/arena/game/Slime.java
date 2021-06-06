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

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;

public class Slime {
    Animator animator;
    SpriteBatch batch;
    Sprite sprite;
    Collider collider;

    public Texture run_img;
    public Animation<TextureRegion> run;
    public TextureRegion currentFrame;
    public TextureRegion[] test = new TextureRegion[18];

    Vector2 velocity;

    float gravity = 50 * 9.81f;
    float stateTime = 0;
    float deltaTime = 0;
    float coolDown = 0;
    float jumpForce = 900f;
    int i = 0;

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
    }

    public void render(OrthographicCamera camera)
    {
        batch.begin();
        sprite.setRegion(currentFrame);
        sprite.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        batch.end();
    }

    private void gravity(float deltaTime, StateMachine state)
    {
        if (!state.slimeIsGrounded)
            velocity.y += -gravity * deltaTime;
        return;
    }

    private void move(Vector2 velocity, float deltaTime, StateMachine state)
    {
        if (!state.slimeIsGrounded) {
            velocity.y += jumpForce * deltaTime;
            jumpForce += -(300f * deltaTime);
        } else {
            jumpForce = 10f;
        }
    }

    public void update(OrthographicCamera camera, StateMachine state, TiledMapTileLayer collisionLayer)
    {
        coolDown += Gdx.graphics.getDeltaTime();
        deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += Gdx.graphics.getDeltaTime();
        //currentFrame = run.getKeyFrame(stateTime, true);
        test = run.getKeyFrames();
        /*if (i != 17) {
            if (i == 8) {
                if ((stateTime) >= 4) {
                    i++;
                    stateTime = 0f;
                }
            } else {
                i++;
            }
            currentFrame = test[i];
        } else {
            i = 0;
        }*/
        render(camera);
        sprite.setPosition(velocity.x, velocity.y);
        state = collider.getSlimeWorldCollision(this, state, collisionLayer);
        move(this.velocity, deltaTime, state);
        //gravity(deltaTime, state);
    }

    public void dispose()
    {
        batch.dispose();
    }
}
