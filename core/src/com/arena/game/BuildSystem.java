package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.Input.Keys.*;

public class BuildSystem {

    public Vector3 mousePosition;

    public BuildSystem() {
        mousePosition = new Vector3();
    }

    public void getMousePosition()
    {
        this.mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
    }

    public void constructBlock(Entity entity)
    {
        entity.player.camera.unproject(mousePosition);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            System.out.println(this.mousePosition);
            entity.map.addBlock(entity.map.tiles.DIRT, (int)this.mousePosition.x, (int)this.mousePosition.y);
        }
    }

    public void update(Entity entity)
    {
        constructBlock(entity);
        getMousePosition();
    }
}
