package com.arena.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    Player player;
    Enemys enemys;
    Maps map;
    StateMachine state;

    public Entity(SpriteBatch batch)
    {
        player = new Player(200, 1000);
        enemys = new Enemys();
        map = new Maps(player.camera, batch);
        player.getCollLayer(map.collisionLayer);
        state = new StateMachine();
    }

    public void update(SpriteBatch batch)
    {
        map.render(player, state);
        state.update(player);
        enemys.update(player.camera, player.colLayer, player);
        player.update(state, batch);
    }

    public void dispose()
    {
        player.dispose();
        enemys.dispose();
    }
}
