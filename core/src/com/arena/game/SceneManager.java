package com.arena.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    Entity entity;

    enum SCENE {
        GAME,
        PAUSE
    }

    public SceneManager(SpriteBatch batch)
    {
        entity = new Entity(batch);
    }

    public void drawGame(SpriteBatch batch)
    {
        this.entity.update(batch);
    }

    public void drawScene(SpriteBatch batch)
    {
        SCENE scene = SCENE.GAME;
        switch (scene) {
            case GAME: drawGame(batch); break;
            case PAUSE: System.out.println("salut"); break;
            default: break;
        }
    }
}
