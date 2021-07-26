package com.arena.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    Game game;

    enum SCENE {
        GAME,
        PAUSE
    }

    public static SCENE scene = SCENE.GAME;

    public SceneManager(SpriteBatch batch)
    {
        game = new Game(batch);
    }

    public void drawGame(SpriteBatch batch)
    {
        this.game.drawGame(batch);
    }

    public void disposePause()
    {
        return;
    }

    public void disposeGame()
    {
        this.game.dipose();
    }

    public void drawScene(SpriteBatch batch)
    {
        switch (this.scene) {
            case GAME: drawGame(batch); break;
            case PAUSE: System.out.println("PAUSE"); break;
            default: break;
        }
    }

    public void dispose()
    {
        switch(scene) {
            case GAME: disposeGame();
            default: break;
        }
    }
}
