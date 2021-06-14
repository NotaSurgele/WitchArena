package com.arena.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    Player player;
    Enemys enemys;
    Maps map;
    StateMachine state;
    Collider collider;

    public Entity(SpriteBatch batch)
    {
        player = new Player(500, 2000);
        map = new Maps(player.camera, batch);
        enemys = new Enemys();
        player.getCollLayer(map.collisionLayer);
        state = new StateMachine();
        collider = new Collider();
    }

    public void update(SpriteBatch batch)
    {
        map.render(player, state);
        collider.playerHitByEntity(this);
        state.update(player);
        enemys.update(player.camera, player.colLayer, player);
        player.update(state, batch, map.collisionLayer);
    }

    public void dispose()
    {
        map.dispose();
        player.dispose();
        enemys.dispose();
    }
}
