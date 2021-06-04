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

public class Slime {
    Animator animator;
    SpriteBatch batch;
    Sprite sprite;
    Collider collider;

    public Texture run_img;
    public Animation<TextureRegion> run;
    public TextureRegion currentFrame;

    Vector2 velocity;

    float gravity = 6 * 1.8f;
    float stateTime = 0;
    float deltaTime = 0;
    float coolDown = 0;

    final String SLIME = "Slime/";

    public Slime()
    {
        animator = new Animator();
        animator.initializeSlimeAnimation(this);
        currentFrame = run.getKeyFrame(stateTime);
        batch = new SpriteBatch();
        sprite = new Sprite();
        sprite.setBounds(0, 0, 200, 200);
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
            velocity.y -= gravity * deltaTime;
    }

    private float move(float coolDown,  StateMachine state)
    {
        if (!state.slimeIsMoving) {
            if (coolDown >= 1 && coolDown <= 1.4) {
                velocity.x += 3 * deltaTime;
            } else if (coolDown >= 1.4f) {
                state.slimeIsMoving = false;
                coolDown = 0f;
                deltaTime = 0f;
            }
        }
        return coolDown;
    }

    public void update(OrthographicCamera camera, StateMachine state, TiledMapTileLayer collisionLayer)
    {
        coolDown += Gdx.graphics.getDeltaTime();
        deltaTime += Gdx.graphics.getDeltaTime();
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = run.getKeyFrame(stateTime, true);
        render(camera);
        sprite.setPosition(velocity.x, velocity.y);
        collider.getSlimeWorldCollision(this, state, collisionLayer);
        gravity(deltaTime, state);
        //coolDown = move(coolDown, state);
    }

    public void dispose()
    {
        batch.dispose();
    }
}
