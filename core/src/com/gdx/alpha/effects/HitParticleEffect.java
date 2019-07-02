package com.gdx.alpha.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by admin on 21.02.2015.
 */
public class HitParticleEffect extends Actor {
    private ParticleEffect effect;
    private boolean complete = false;
    private float interval;
    private float timeEffect;

    public HitParticleEffect(ParticleEffect effect, float timeEffect){
        this.effect = effect;
        this.timeEffect = timeEffect;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.effect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.interval += delta;
        isCompleteEffect();
        this.effect.update(delta);
    }

    public void startEffect(){
        this.effect.start();
    }
    private void isCompleteEffect(){
        if (this.interval > this.timeEffect)
            this.complete = true;
    }
    public void resetEffect(){
        this.effect.reset();
    }

    public void setPositionEffect(float x, float y){
        this.effect.setPosition(x, y);
    }

    public void allowCompletionEffect(){
        this.effect.allowCompletion();
    }

    public boolean isComplete() {
        return this.complete;
    }
}
