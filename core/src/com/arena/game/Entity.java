package com.arena.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    Player player;
    Enemys enemys;
    StateMachine state;
    Collider collider;
    Items items;

    public Entity(SpriteBatch batch)
    {
        player = new Player(500, 2000);
        enemys = new Enemys();
        state = new StateMachine();
        collider = new Collider();
        items = new Items();
    }

    public void update(SpriteBatch batch)
    {
        collider.playerHitByEntity(this);
        state.update(player);
        enemys.update(player.camera, player.colLayer, player);
        player.update(state, batch, items);
    }

    public void dispose()
    {
        player.dispose();
        enemys.dispose();
    }
}
