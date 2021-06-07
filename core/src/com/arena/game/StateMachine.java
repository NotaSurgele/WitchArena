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
    public boolean playerColliding = false;
    public boolean playerCollideLeft = false;
    public boolean playerIsCharging = false;
    public boolean playerCollideRight = false;
    float cd = 0;
    //Enemy

    //Slime
    public boolean slimeIsGrounded = false;
    public boolean slimeCollideLeft = false;
    public boolean slimeCollideRight = false;
    public boolean slimeGoLeft = false;
    public boolean slimeGoRight = true;
    public boolean slimeISJumping = true;
    //Others

    public StateMachine() {}

    public void update(Player player)
    {
        this.getPlayerState(player);
    }

    public StateMachine getPlayerState(Player player) {
        if (isPlaying) {
            boolean left = false;

            if ((left = Gdx.input.isKeyPressed(D)) || Gdx.input.isKeyPressed(Q)) {
                this.playerisRotating = !left;
                this.playerisAttacking = false;
                this.playerisMoving = true;
            } else if (Gdx.input.isButtonPressed(LEFT) && !this.playerIsJumping) {
                if (this.playerisMoving) {
                    this.playerIsCharging = false;
                    cd = 0;
                }
                this.playerIsCharging = true;
                if (this.playerIsCharging && !this.playerisAttacking) {
                    this.cd += Gdx.graphics.getDeltaTime();
                }
                this.playerisMoving = false;
                if (this.playerIsGrounded && cd > 3f) {
                    this.playerisAttacking = true;
                    this.playerIsCharging = false;
                }
                if (player.attackRight.isAnimationFinished(player.attackTime) && this.playerisAttacking) {
                    this.playerisAttacking = false;
                    cd = 0;
                }
            } else {
                cd = 0;
                this.playerisMoving = false;
                this.playerisAttacking = false;
                this.playerIsCharging = false;
            }
        }
        return this;
    }
}