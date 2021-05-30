package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import java.lang.Math;
import static com.badlogic.gdx.Input.Keys.*;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.sqrt;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import org.graalvm.compiler.hotspot.amd64.AMD64MathStub;
import sun.jvm.hotspot.gc.shared.Space;

public class Player {

    //basic
    OrthographicCamera camera;
    Sprite sprite;

    //value
    public Vector2 velocity;
    public final float moveSpeed = 2 * 2;
    public final double gravity = 3.4 * 1.8f;
    static final int changeX = 250;
    static final int changeY = 100;
    static final int staticX = 80;
    static final int staticY = 100;
    static final int paddingX = 170;
    static final int paddingY = 200;
    static final int cameraX = Gdx.graphics.getWidth();
    static final int cameraY = Gdx.graphics.getHeight();
    static final float JUMPING_VALUE = 1f;
    private float oldY = 0;
    private float jumping = JUMPING_VALUE;

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
    Collider collider;
    public float deltaTime = 0;
    public MapLayer collisionLayer;

    public Player(float posX, float posY)
    {
        velocity = new Vector2();
        velocity.x = posX;
        velocity.y = posY;
        animator = new Animator();
        animator.initializePlayerAnimation(this);
        currentFrame = idleRight.getKeyFrame(stateTime, true);
        sprite = new Sprite();
        sprite.setBounds(0, 0, staticX, staticY);
        sprite.setRegion(currentFrame);
        sprite.setPosition(velocity.x, velocity.y);
        camera = new OrthographicCamera(cameraX, cameraY);
        camera.setToOrtho(false, cameraX, cameraY);
        camera.update();
        collider = new Collider();
    }

    private float getCenteredCameraPosX(Sprite sprite)
    {
        return sprite.getX() + paddingX;
    }

    private float getCenteredCameraPosY(Sprite sprite)
    {
        return sprite.getY() + paddingY;
    }

    public void getCollisionLayer(MapLayer layer)
    {
        if (this.collisionLayer == null) {
            this.collisionLayer = layer;
        }
    }

    private void gravity(StateMachine state)
    {
        state.playerIsGrounded = collider.playerIsGrounded(this);
        if (!state.playerIsGrounded) {
            velocity.y -= gravity * deltaTime - this.jumping;
        } else {
            deltaTime = 1.3f;
        }
    }

    public void move()
    {
        if (Gdx.input.isKeyPressed(D)) {
            this.velocity.x += moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Q)) {
            this.velocity.x -= moveSpeed * deltaTime;
        }
    }

    private float jumping(float oldY, StateMachine state) {
        state.playerIsFlying = !state.playerIsGrounded;

        if (state.playerIsGrounded && Gdx.input.isKeyJustPressed(SPACE)) {
            this.jumping = JUMPING_VALUE;
            oldY = this.velocity.y;
            state.playerIsJumping = true;
            state.playerIsGrounded = false;
        }
        if (this.jumping <= 0) {
            state.playerIsJumping = false;
        }
        if (state.playerIsJumping) {
            velocity.y += (float)(-0.5f * -gravity * power(deltaTime, 3.8f) * 2) - this.jumping;
            if (velocity.y >= (oldY + 80)) {
                this.jumping -= 0.07f;
                this.deltaTime = 1.3f;
            }
        }
        return oldY;
    }

    private OrthographicCamera setCameraPositionRelativeToPlayer(StateMachine state)
    {
        if (state.playerisAttacking && state.playerisRotating) {
            camera.position.set(this.getCenteredCameraPosX(this.sprite), this.getCenteredCameraPosY(this.sprite), 0);
        } else {
            camera.position.set(sprite.getX(), sprite.getY() + 200, 0);
        }
        return camera;
    }

    //Libgdx Rendering function
    public void render(StateMachine state, SpriteBatch batch)
    {
        attackTime = animator.getAttackTime(state, attackTime);
        sprite = animator.updatePlayerSprite(this, state, batch);
        this.camera = setCameraPositionRelativeToPlayer(state);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    public void update(StateMachine state, SpriteBatch batch)
    {
        if (state.isPlaying) {
            stateTime += Gdx.graphics.getDeltaTime();
            deltaTime += Gdx.graphics.getDeltaTime();
            currentFrame = animator.setPlayerCurrentFrame(this, state);
            gravity(state);
            oldY = jumping(oldY, state);
            sprite.setRegion(currentFrame);
            render(state, batch);
        }
    }

    public void dispose()
    {
        idleRight_img.dispose();
        idleLeft_img.dispose();
        moveLeft_img.dispose();
        moveRight_img.dispose();
        attackLeft_img.dispose();
        attackRight_img.dispose();
    }

}
