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

        bodyHitbox.height = player.sprite.getBoundingRectangle().height / 2;
        bodyHitbox.width = player.sprite.getBoundingRectangle().width + 5;
        bodyHitbox.x = player.sprite.getBoundingRectangle().x;
        bodyHitbox.y = player.sprite.getY() + player.sprite.getHeight() / 2;
        return bodyHitbox;
    }

    //Collision detection with the ground
    public StateMachine playerIsColliding(Player player, StateMachine state)
    {
        MapObjects mapObjects = player.collisionLayer.getObjects();
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject obj : rectangleObjects) {
            Rectangle object = obj.getRectangle();
            Rectangle feetHitbox = getFeetHitbox(player);
        }
        return state;
    }

    Rectangle getBottom(float x, float y, float width, float height)
    {
        Rectangle bottom = new Rectangle();
        bottom.x = x;
        bottom.y = y - height - 32;
        bottom.width = 32;
        bottom.height = 32;
        return bottom;
    }

    public void test_two(Player player, StateMachine state)
    {
        MapObjects mapObjects = player.collisionLayer.getObjects();
        Array<RectangleMapObject> rectangleMapObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject obj : rectangleMapObjects) {
            Rectangle object = obj.getRectangle();
            Rectangle bottom = getBottom(player.sprite.getX(), player.sprite.getY(), player.sprite.getWidth(), player.sprite.getHeight());

            if (bottom.overlaps(object)) {
                state.playerIsGrounded = true;
                return;
            } else {
                state.playerIsGrounded = false;
            }
        }
    }

    public boolean test(Player player, StateMachine state)
    {
        TiledMapTileLayer.Cell cell = player.colLayer.getCell((int)(player.sprite.getX() / 31), (int)player.sprite.getY() / 32);
        if (cell != null) {
            if (player.moveV.y < 0)
                state.playerIsGrounded = true;
            if (player.moveV.x > 0 )
                System.out.println("Collision Droite");
        } else {
            state.playerIsGrounded = false;
        }
        return true;
    }

    public Player playerMovementCollision(Player player)
    {
        return player;
    }
}