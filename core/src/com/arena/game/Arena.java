package com.arena.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Arena extends ApplicationAdapter {
	Player player;
	Debug debug;
	StateMachine state;
	SpriteBatch batch;
	//System variable
	float stateTime = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(50,50);
		debug = new Debug();
		state = new StateMachine();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		state.getPlayerState(player);
		state.applyPlayerState(player);
		player.update(state);
		debug.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		debug.dispose();
	}
}
