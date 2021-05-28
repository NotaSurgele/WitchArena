package com.arena.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.w3c.dom.Text;

public class Arena extends ApplicationAdapter {
	Player player;
	Debug debug;
	StateMachine state;
	SpriteBatch batch;

	Texture img;

	//System variable
	float stateTime = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(0,0);
		debug = new Debug();
		state = new StateMachine();
		img = new Texture("idle_left.png");
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		state.getPlayerState(player);
		state.applyPlayerState(player);
		player.update(state, batch);
		debug.render();
		batch.end();
	}

	@Override
	public void dispose () {
		img.dispose();
		batch.dispose();
		player.dispose();
		debug.dispose();
	}
}
