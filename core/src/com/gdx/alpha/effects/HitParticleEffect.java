package com.gdx.alpha.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by admin on 21.02.2015.
 */
public class HitParticleEffect extends Actor {
    private ParticleEffect effect;
    private boolean complete = false;
    private float interval = 0.0f;
    private float timeEffect = 0.0f;

    public HitParticleEffect(ParticleEffect effect, float timeEffect){
        this.effect = effect;
        this.timeEffect = timeEffect;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        interval += delta;
        isCompleteEffect(interval);
        effect.update(delta);
    }

    public void startEffect(){
        effect.start();
    }
    public void isCompleteEffect(float interval){
        if (interval > timeEffect)
            complete = true;
    }
    public void resetEffect(){
        effect.reset();
    }

    public void setPositionEffect(float x, float y){
        effect.setPosition(x, y);
    }

    public void allowCompletionEffect(){
        effect.allowCompletion();
    }

    public boolean isComplete() {
        return complete;
    }
}
