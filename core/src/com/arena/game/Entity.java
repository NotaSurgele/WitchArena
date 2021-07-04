package com.arena.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    Player player;
    Enemys enemys;
    Maps map;
    StateMachine state;
    Collider collider;
    Items items;

    public boolean mapIsLoaded = false;

    public Entity(SpriteBatch batch)
    {
        player = new Player(1500000, 5000);
        map = new Maps(player.camera);
        enemys = new Enemys();
        state = new StateMachine();
        collider = new Collider();
        items = new Items();
        mapIsLoaded = map.OnUserCreate(player, state);
    }

    public void update(SpriteBatch batch)
    {
        if (mapIsLoaded == true) {
            map.render(player, state);
            collider.playerHitByEntity(this);
            state.update(player);
            enemys.update(player.camera, map.collisionLayer, player);
            player.update(state, batch, map.collisionLayer, items, this);
        }
    }

    public void dispose()
    {
        map.dispose();
        player.dispose();
        enemys.dispose();
    }
}
