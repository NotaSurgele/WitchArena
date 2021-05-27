package com.arena.game;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;

public class StateMachine {

    //GameState
    public boolean isPlaying = true;

    //Player variable
    public boolean playerisMoving = false;
    public boolean playerisAttacking = false;

    //Others

    public StateMachine() {}

    public StateMachine getPlayerState() {
        if (isPlaying) {
            if (Gdx.input.isKeyPressed(D) || Gdx.input.isKeyPressed(Q)) {
                if (this.playerisAttacking) {
                    return this;
                }
                this.playerisAttacking = false;
                this.playerisMoving = true;
            } if (Gdx.input.isButtonPressed(LEFT)) {
                if (this.playerisMoving) {
                    return this;
                }
                this.playerisMoving = false;
                this.playerisAttacking = true;
            }
        }
        return this;
    }
}
