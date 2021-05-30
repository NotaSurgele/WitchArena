package com.arena.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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
        System.out.println(feetHitbox.y);
        return feetHitbox;
    }

    private Rectangle getPlayerHitbox(Player player, Rectangle test)
    {
        Rectangle playerHitbox = new Rectangle();

        playerHitbox.height = test.height;
        playerHitbox.width = player.sprite.getBoundingRectangle().width + 5;
        playerHitbox.x = player.sprite.getX();
        playerHitbox.y = (player.sprite.getY() - ((player.sprite.getHeight() / 2) - player.sprite.getHeight() + 32));
        System.out.println(playerHitbox.y);
        System.out.println(player.sprite.getY());
        return playerHitbox;
    }

    //Collision detection with the ground
    public float[] playerIsColliding(Player player, StateMachine state, float[] oldPos)
    {
        MapObjects mapObjects = player.collisionLayer.getObjects();
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject obj : rectangleObjects) {
            Rectangle rectangle = obj.getRectangle();
            Rectangle feetHitbox = getFeetHitbox(player);
            Rectangle playerHitbox = getPlayerHitbox(player, feetHitbox);

            if (playerHitbox.overlaps(rectangle)) {
                System.out.println("hello world");
                state.playerColliding = true;
                player.sprite.setX(oldPos[0]);
                player.sprite.setY(oldPos[1]);
            } else {
                state.playerColliding = false;
            } if (feetHitbox.overlaps(rectangle)) {
                state.playerIsGrounded = true;
                return oldPos;
            } else {
                state.playerIsGrounded = false;
            }
        }
        return oldPos;
    }

    public Player playerMovementCollision(Player player)
    {
        return player;
    }
}