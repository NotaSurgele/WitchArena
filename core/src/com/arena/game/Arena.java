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
	Entity entity;
	Debug debug;
	StateMachine state;
	SpriteBatch batch;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		debug = new Debug();
		state = new StateMachine();
		entity = new Entity(batch);
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		entity.update(batch, state);
		debug.render();
		batch.end();
	}

	@Override
	public void dispose ()
	{
		batch.dispose();
		debug.dispose();
	}
}
