package com.arena.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {

    Entity entity;
    BuildSystem build;

    public Game(SpriteBatch batch) {
        entity = new Entity(batch);
        build = new BuildSystem();
    }

    public void drawGame(SpriteBatch batch)
    {
        this.build.update(this.entity);
        this.entity.update(batch);
    }

    public void dipose()
    {
        this.entity.dispose();
    }
}
