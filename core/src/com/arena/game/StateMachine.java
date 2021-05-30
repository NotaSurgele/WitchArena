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

}