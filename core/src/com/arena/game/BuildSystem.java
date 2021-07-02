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

    public void constructBlock(Entity entity)
    {
        entity.player.camera.unproject(mousePosition);
        if (entity.player.inventory.inventory.size() <= 0 || choose >= entity.player.inventory.inventory.size())
            return;
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            int id = entity.player.inventory.inventory.get(choose).ID;
            System.out.println("id = " + id);
            if (entity.player.inventory.getItem(id) != null && entity.player.inventory.getItem(id).HOWMANY > 0) {
                entity.map.addBlock(entity.player.inventory.getItem(id).TEXTUREITEM, (int) this.mousePosition.x, (int) this.mousePosition.y, entity.player.inventory.getItem(id).ID);
                entity.player.inventory.removeOwnedItem(id);
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE)) {
            entity.map.getBlockId((int) this.mousePosition.x, (int) this.mousePosition.y);
        }
    }

    public void destroyBlock(Entity entity)
    {
        if (Gdx.input.isKeyJustPressed(F)) {
            entity.map.removeBlock((int)this.mousePosition.x, (int)this.mousePosition.y, entity);
        }
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
        constructBlock(entity);
        destroyBlock(entity);
    }
}
