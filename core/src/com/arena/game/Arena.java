package com.arena.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.w3c.dom.Text;

public class Arena extends ApplicationAdapter {
	Player player;
	Debug debug;
	StateMachine state;
	SpriteBatch batch;
	Maps map;

	//System variable
	float stateTime = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(200,1500);
		debug = new Debug();
		state = new StateMachine();
		map = new Maps(player.camera);
		player.getCollLayer(map.collisionLayer);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		player.getCollisionLayer(map.map.getLayers().get(0));
		map.render(player, batch, state);
		state.update(player);
		player.update(state, batch);
		debug.render();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		debug.dispose();
	}
}
