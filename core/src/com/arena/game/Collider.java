package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;

public class Collider {

    public Collider() {}

    private Rectangle getFeetHitbox(Player player)
    {
        Rectangle feetHitbox = new Rectangle();

        feetHitbox.height = player.sprite.getBoundingRectangle().height / 2;
        feetHitbox.width = player.sprite.getBoundingRectangle().width;
        feetHitbox.x = player.sprite.getX();
        feetHitbox.y = (player.sprite.getY() - ((player.sprite.getHeight() / 2) - player.sprite.getHeight() + 32));
        return feetHitbox;
    }

    private Rectangle getSlimeHitbox(Slime slime)
    {
        Rectangle hitbox = new Rectangle().set(slime.sprite.getX(), slime.sprite.getY(), slime.sprite.getWidth() - 32, slime.sprite.getHeight() - 32);
        return hitbox;
    }

    private Rectangle getBodyHitbox(Player player)
    {
        Rectangle bodyHitbox = new Rectangle();

        bodyHitbox.height = player.sprite.getBoundingRectangle().height;
        bodyHitbox.width = player.sprite.getBoundingRectangle().width;
        bodyHitbox.x = player.sprite.getX();
        bodyHitbox.y = player.sprite.getY() + 9;
        return bodyHitbox;
    }

    /**
     * This is the current used World Collision with the player
     * @param player
     * @param state
     */
    public void getPlayerWorldCollision(Player player, StateMachine state, Entity entity)
    {
        Rectangle bodyHitbox = getBodyHitbox(player);
        TiledMapTileLayer.Cell bottomMid = entity.map.collisionLayer.getCell((int)((bodyHitbox.x + (bodyHitbox.width / 2)) / 32), (int)bodyHitbox.y / 32);
        TiledMapTileLayer.Cell bottomLeft = entity.map.collisionLayer.getCell((int) (bodyHitbox.x + 33) / 32, (int) bodyHitbox.y / 32);
        TiledMapTileLayer.Cell bottomRight = entity.map.collisionLayer.getCell((int) ((bodyHitbox.x + bodyHitbox.width) - 33) / 32, (int) bodyHitbox.y / 32);
        TiledMapTileLayer.Cell left = entity.map.collisionLayer.getCell((int) (bodyHitbox.x + 20) / 32, (int) (bodyHitbox.y + (bodyHitbox.height / 2)) / 32);
        TiledMapTileLayer.Cell right = entity.map.collisionLayer.getCell((int) (bodyHitbox.x + 64) / 32, (int) (bodyHitbox.y + (bodyHitbox.height / 2)) / 32);
        TiledMapTileLayer.Cell stairsRight = entity.map.collisionLayer.getCell((int) ((bodyHitbox.x + bodyHitbox.width) - 30) / 32, (int) (bodyHitbox.y + 5) / 32);
        TiledMapTileLayer.Cell stairsLeft = entity.map.collisionLayer.getCell((int) (bodyHitbox.x + 30) / 32, (int) (bodyHitbox.y + 5) / 32);

        if (left != null) {
            state.playerCollideLeft = true;
        } else {
            state.playerCollideLeft = false;
        } if (right != null) {
            state.playerCollideRight = true;
        } else {
            state.playerCollideRight = false;
        }

        if (stairsRight != null && right == null && state.playerisMoving) {
            state.playerStairsRightColliding = true;
        } else {
            state.playerStairsRightColliding = false;
        }

        if (stairsLeft != null && left == null && state.playerisMoving) {
            state.playerStairsLeftColliding = true;
        } else {
            state.playerStairsLeftColliding = false;
        }

        if (bottomMid == null && bottomLeft == null && bottomRight == null)
            state.playerIsGrounded = false;

        if (!state.playerIsGrounded) {
            if (bottomMid != null) {
                state.playerIsGrounded = true;
            } else {
                state.playerIsGrounded = false;
            }
            if (bottomLeft != null && left == null) {
                state.playerIsGrounded = true;
            }
            if (bottomRight != null && right == null) {
                state.playerIsGrounded = true;
            }
        }
    }

    private Rectangle getEntityHitbox(Sprite sprite)
    {
        Rectangle hitbox = new Rectangle().set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        return hitbox;
    }

    public StateMachine getSlimeWorldCollision(Slime slime, StateMachine state, TiledMapTileLayer collisionLayer)
    {
        Rectangle hitbox = getEntityHitbox(slime.sprite);
        TiledMapTileLayer.Cell bottom = collisionLayer.getCell((int)((hitbox.x + (hitbox.width / 2)) / 32), (int)(hitbox.y - 4) / 32);
        TiledMapTileLayer.Cell left = collisionLayer.getCell((int) (hitbox.x - 32) / 31, (int) (hitbox.y + (hitbox.height / 2)) / 32);
        TiledMapTileLayer.Cell right = collisionLayer.getCell((int) (hitbox.x + hitbox.width + 32) / 33, (int) (hitbox.y + (hitbox.height / 2)) / 32);

        if (right != null) {
            state.slimeCollideRight = true;
        } else {
            state.slimeCollideRight = false;
        }
        if (left != null) {
            state.slimeCollideLeft = true;
        } else {
            state.slimeCollideLeft = false;
        }
        if (bottom != null) {
            state.slimeIsGrounded = true;
        } else {
            state.slimeIsGrounded = false;
        }
        return state;
    }

    public Circle createCircle(float radius, Vector2 position)
    {
        Circle circle = new Circle();

        circle.setRadius(radius);
        circle.setPosition(position);
        return circle;
    }

    public StateMachine checkSlimeAggroZone(Circle circle, float x, float y, StateMachine state)
    {
        if (circle.contains(x, y)) {
            if (x - circle.x > 0) {
                state.slimeGoLeft = false;
                state.slimeGoRight = true;
            } else if (x - circle.x < 0) {
                state.slimeGoRight = false;
                state.slimeGoLeft = true;
            }
        }
        return state;
    }

    public void playerHitByEntity(Entity entity)
    {
        Rectangle playerHitbox = getBodyHitbox(entity.player);

        for (Slime sl : entity.enemys.slime) {
            Rectangle slimeHitbox = getSlimeHitbox(sl);
            if (slimeHitbox.overlaps(playerHitbox)) {
                //System.out.println("Hit by entity: " + numb);
                entity.player.health -= 0.1f;
            }
        }
    }
}