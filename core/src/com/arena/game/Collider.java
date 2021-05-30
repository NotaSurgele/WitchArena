package com.arena.game;

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

    //Collision detection with the ground
    public boolean playerIsGrounded(Player player)
    {
        MapObjects mapObjects = player.collisionLayer.getObjects();
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject obj : rectangleObjects) {
            Rectangle rectangle = obj.getRectangle();
            Rectangle playerHitbox = new Rectangle();
            playerHitbox.x = player.sprite.getBoundingRectangle().x + 35;
            playerHitbox.y = player.sprite.getBoundingRectangle().y + 9f;
            if (playerHitbox.overlaps(rectangle)) {
                return true;
            }
        }
        return false;
    }

    public Player playerMovementCollision(Player player)
    {
        return player;
    }
}
