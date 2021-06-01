package com.arena.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    private Rectangle getBodyHitbox(Player player)
    {
        Rectangle bodyHitbox = new Rectangle();

        bodyHitbox.height = player.sprite.getBoundingRectangle().height;
        bodyHitbox.width = player.sprite.getBoundingRectangle().width;
        bodyHitbox.x = player.sprite.getX();
        bodyHitbox.y = player.sprite.getY() + 9;
        return bodyHitbox;
    }

    //OlderCollsion just keep it in case i have to change
    // You just have to create an object layer in Tiled

    public StateMachine playerIsColliding(Player player, StateMachine state)
    {
        MapObjects mapObjects = player.collisionLayer.getObjects();
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject obj : rectangleObjects) {
            Rectangle object = obj.getRectangle();
            Rectangle feetHitbox = getFeetHitbox(player);
            Rectangle bodyHitbox = getBodyHitbox(player);

            if (bodyHitbox.overlaps(object)) {
                System.out.println("Hello world !");
            } if (!state.playerIsGrounded) {
                if (feetHitbox.overlaps(object)) {
                    state.playerIsGrounded = true;
                    return state;
                }
            } else if (state.playerIsJumping || state.playerisMoving) {
                if (feetHitbox.overlaps(object)) {
                    state.playerIsGrounded = true;
                    return state;
                } else {
                    state.playerIsGrounded = false;
                }
            }
        }
        return state;
    }

    public void getPlayerWorldCollision(Player player, StateMachine state)
    {
        Rectangle bodyHitbox = getBodyHitbox(player);
        TiledMapTileLayer.Cell bottomMid = player.colLayer.getCell((int)((bodyHitbox.x + (bodyHitbox.width / 2) - 31) / 32), (int)bodyHitbox.y / 32);
        TiledMapTileLayer.Cell bottomLeft = player.colLayer.getCell((int) (bodyHitbox.x + 31) / 32, (int) bodyHitbox.y / 32);
        TiledMapTileLayer.Cell bottomRight = player.colLayer.getCell((int) ((bodyHitbox.x + bodyHitbox.width) - 31) / 31, (int) bodyHitbox.y / 32);
        TiledMapTileLayer.Cell left = player.colLayer.getCell((int) bodyHitbox.x / 31, (int) (bodyHitbox.y + (bodyHitbox.height / 2)) / 32);
        TiledMapTileLayer.Cell right = player.colLayer.getCell((int) (bodyHitbox.x + bodyHitbox.width) / 33, (int) (bodyHitbox.y + (bodyHitbox.height / 2)) / 32);

        if (left != null) {
            state.playerCollideLeft = true;
        } else {
            state.playerCollideLeft = false;
        } if (right != null) {
            state.playerCollideRight = true;
        } else {
            state.playerCollideRight = false;
        } if (bottomMid == null && bottomLeft == null && bottomRight == null)
            state.playerIsGrounded = false;
        if (!state.playerIsGrounded) {
            if (bottomMid != null) {
                state.playerIsGrounded = true;
            }
            if (bottomLeft != null) {
                state.playerIsGrounded = true;
            }
            if (bottomRight != null) {
                state.playerIsGrounded = true;
            }
        }
    }

}