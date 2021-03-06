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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.viewport.FillViewport;
import org.graalvm.compiler.hotspot.amd64.AMD64MathStub;
import sun.jvm.hotspot.gc.shared.Space;

public class Player {

    //basic
    OrthographicCamera camera;
    Sprite sprite;
    InventorySystem inventory;

    //value
    public Vector2 velocity;
    public Vector2 moveV;
    public final float moveSpeed = 300f;
    public final double gravity = 60 * 9.8f;
    public float jumpForce = 300f;
    static final int changeX = 250;
    static final int changeY = 100;
    static final int staticX = 80;
    static final int staticY = 100;
    static final int paddingY = 200;
    static final int cameraX = Gdx.graphics.getWidth();
    static final int cameraY = Gdx.graphics.getHeight();
    static final float JUMPING_VALUE = 1f;
    float health = 100f;
    private float jumping = JUMPING_VALUE;

    //animation
    Animator animator;
    public Texture idleRight_img;
    public Texture idleLeft_img;
    public Texture attackRight_img;
    public Texture attackLeft_img;
    public Texture moveRight_img;
    public Texture moveLeft_img;
    public Texture chargeRight_img;
    public Texture chargeLeft_img;
    public Animation<TextureRegion> idleRight;
    public Animation<TextureRegion> idleLeft;
    public Animation<TextureRegion> moveRight;
    public Animation<TextureRegion> moveLeft;
    public Animation<TextureRegion> attackRight;
    public Animation<TextureRegion> attackLeft;
    public Animation<TextureRegion> chargeRight;
    public Animation<TextureRegion> chargeLeft;
    public TextureRegion currentFrame = null;
    float stateTime = 0;
    float attackTime = 0;

    //useful
    Collider collider;
    public float deltaTime = 0;

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
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
        camera.setToOrtho(false, cameraX, cameraY);
        camera.update();
        moveV = new Vector2();
        collider = new Collider();
        inventory = new InventorySystem();
    }

    public float getX()
    {
        return this.sprite.getX();
    }

    public float getY()
    {
        return this.sprite.getY();
    }

    private void checkHealth(float health)
    {
        if (health <= 0)
            System.out.println("Dead");
    }

    private float getCenteredCameraPosX(Sprite sprite)
    {
        final int paddingX = 170;
        return sprite.getX() + paddingX;
    }

    private float getCenteredCameraPosY(Sprite sprite)
    {
        return sprite.getY() + paddingY;
    }

    private void gravity(StateMachine state)
    {
        if (!state.playerIsGrounded && !state.playerIsJumping) {
            moveV.y = -(float)gravity * Gdx.graphics.getDeltaTime() - this.jumping;
            velocity.y += moveV.y;
        }
    }

    public StateMachine move(StateMachine state)
    {
        if (!state.playerCollideRight && Gdx.input.isKeyPressed(D)) {
            moveV.x = moveSpeed * deltaTime;
            this.velocity.x += moveV.x;
        }
        if (!state.playerCollideLeft && Gdx.input.isKeyPressed(Q)) {
            moveV.x = moveSpeed * deltaTime;
            this.velocity.x -= moveV.x;
            state.playerisMoving = true;
        }
        return state;
    }

    public void playerCameraUnZoom()
    {
        if (Gdx.input.isKeyPressed(M)) {
            this.camera.viewportWidth += 20;
            this.camera.viewportHeight += 20;
        }
    }

    private void jumping(StateMachine state, Vector2 velocity, Sprite sprite) {
        if (state.playerisAttacking || state.playerIsCharging)
            return;
        state.playerIsFlying = !state.playerIsGrounded;
        if (state.playerIsGrounded && Gdx.input.isKeyJustPressed(SPACE)) {
            state.playerIsJumping = true;
            state.playerIsGrounded = false;
        }
        if (state.playerIsJumping && !state.playerIsGrounded || state.playerIsJumping && jumpForce >= 0) {
            velocity.y += jumpForce * deltaTime * 4;
            jumpForce += -(800f * deltaTime);
        } else {
            jumpForce = 300f;
            state.playerIsJumping = false;
        }
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

    public OrthographicCamera getCameraPositionRelativeToPlayer(Player player)
    {
        return player.camera;
    }

    public void render(StateMachine state, SpriteBatch batch)
    {
        attackTime = animator.getAttackTime(state, attackTime);
        sprite = animator.updatePlayerSprite(this, state, batch);
        this.camera = setCameraPositionRelativeToPlayer(state);
        camera.update();
        inventory.update(this, batch);
        batch.setProjectionMatrix(camera.combined);
    }

    public void checkInventory()
    {
        //System.out.println("Inventory items size : " + inventory.inventory.size());
        /*for (Item item : inventory.inventory) {
            System.out.println("ItemName: " + item.ITEMNAME + "\nStack: " + item.HOWMANY + "\nId: " + item.ID);
        }
        */
        return;
    }

    public void playerSetPosition(float x, float y)
    {
        this.velocity.x = this.sprite.getX() + x;
        this.velocity.y = this.sprite.getY() + y;
    }

    private void playerStairClimbing(StateMachine state)
    {
        if (state.playerStairsRightColliding && !state.playerisRotating) {
            this.playerSetPosition(3.2f, 3.2f);
        } else if (state.playerStairsLeftColliding && state.playerisRotating) {
            this.playerSetPosition(-3.2f, 3.2f);
        }
    }

    public void update(StateMachine state, SpriteBatch batch, TiledMapTileLayer currentLayer, Items items, Entity entity)
    {
        checkInventory();
        System.out.println(this.sprite.getX());
        stateTime += Gdx.graphics.getDeltaTime();
        deltaTime = Gdx.graphics.getDeltaTime();
        currentFrame = animator.setPlayerCurrentFrame(this, state);
        collider.getPlayerWorldCollision(this, state, entity);
        state = move(state);
        playerStairClimbing(state);
        gravity(state);
        jumping(state, this.velocity, this.sprite);
        sprite.setRegion(currentFrame);
        playerCameraUnZoom();
        render(state, batch);
    }

    public void dispose()
    {
        idleRight_img.dispose();
        idleLeft_img.dispose();
        moveLeft_img.dispose();
        moveRight_img.dispose();
        attackLeft_img.dispose();
        attackRight_img.dispose();
        inventory.dispose();
    }
}
