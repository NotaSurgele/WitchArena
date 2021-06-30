package com.arena.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.Input.Keys.*;

public class BuildSystem {

    public Vector3 mousePosition;
    int choose = 0;

    public BuildSystem() {
        mousePosition = new Vector3();
    }

    public void getMousePosition()
    {
        this.mousePosition.set(Gdx.input.getX(), Gdx.input.getY(),0);
    }

    public float getMouseX()
    {
        return this.mousePosition.x;
    }

    public float getMouseY()
    {
        return this.mousePosition.y;
    }

    public void update(Entity entity)
    {
        if (Gdx.input.isKeyPressed(NUM_1)) {
            choose = 0;
        }
        if (Gdx.input.isKeyPressed(NUM_2)) {
            choose = 1;
        }
        getMousePosition();
    }
}
