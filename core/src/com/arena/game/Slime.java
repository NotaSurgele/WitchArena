package com.arena.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Slime {
    Animator animator;

    public Texture run_img;
    public Animation<TextureRegion> run;
    public TextureRegion currentFrame;

    float stateTime = 0;
    float deltaTime = 0;

    final String SLIME = "Slime/";

    public Slime() {
        animator = new Animator();
        animator.initializeSlimeAnimation(this);
        currentFrame = run.getKeyFrame(stateTime);
    }

}
