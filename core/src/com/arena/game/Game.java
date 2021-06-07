package com.arena.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {

    Entity entity;

    public Game(SpriteBatch batch) {
        entity = new Entity(batch);
    }

    public void drawGame(SpriteBatch batch)
    {
        entity.update(batch);
    }

    public void dipose()
    {
        entity.dispose();
    }
}
