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

	//System variable
	float stateTime;

	@Override
	public void create () {
		player = new Player(50,50);
		debug = new Debug();
		state = new StateMachine();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		state.getPlayerState();
		state.applyPlayerState(player);
		player.update(state);
		debug.render();
	}

	@Override
	public void dispose () {
		player.dispose();
		debug.dispose();
	}
}
