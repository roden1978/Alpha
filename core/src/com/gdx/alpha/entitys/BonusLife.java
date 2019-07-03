package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BonusLife extends Actor {

    private TextureRegion textureRegion;
    private Vector2 position;
    private Bounds bonusLifeBound;

    public BonusLife(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        this.position = new Vector2(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight());
        this.bonusLifeBound = new Bounds(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.textureRegion, this.position.x, this.position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (position.y > Gdx.graphics.getHeight() / 10.0f)
            this.position.y -= 10;

        if (position.x < Gdx.graphics.getWidth() && position.y <= Gdx.graphics.getHeight() / 10.0f)
            this.position.x += 1;

        this.bonusLifeBound.update(this.position.x, this.position.y, this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight());
    }

    public Bounds getBonusLifeBound() {
        return this.bonusLifeBound;
    }
}
