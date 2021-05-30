package com.arena.game;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;

public class StateMachine {

    //GameState
    public boolean isPlaying = true;

    //Player variable
    public boolean playerisMoving = false;
    public boolean playerisAttacking = false;
    public boolean playerisRotating = false;
    public boolean playerIsGrounded = false;
    public boolean playerIsFlying = true;
    public boolean playerIsJumping = false;

    //Others

    public StateMachine() {}

    public StateMachine getPlayerState(Player player) {
        if (isPlaying) {
            boolean left = false;

            if ((left = Gdx.input.isKeyPressed(D)) || Gdx.input.isKeyPressed(Q)) {
                this.playerisRotating = !left;
                this.playerisAttacking = false;
                this.playerisMoving = true;
            } else if (Gdx.input.isButtonPressed(LEFT)) {
                this.playerisMoving = false;
                this.playerisAttacking = true;
            } else {
                this.playerisMoving = false;
                this.playerisAttacking = false;
            }
        }
        return this;
    }

    void applyPlayerState(Player player)
    {
        if (this.playerisMoving) {
            player.move();
        }
    }
}