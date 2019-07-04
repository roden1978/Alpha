package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AlarmClock extends BonusItems {

    private TextureRegion textureRegion;
    private Vector2 position;

    public AlarmClock(TextureRegion textureRegion) {
       // super(textureRegion);
        this.textureRegion = textureRegion;
        this.position = new Vector2(Gdx.graphics.getWidth()/4.0f, Gdx.graphics.getHeight());
        bonusItemsBound = new Bounds(Gdx.graphics.getWidth()/4.0f, Gdx.graphics.getHeight(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        type = 2;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       //super.draw(batch, parentAlpha);
        batch.draw(this.textureRegion, this.position.x, this.position.y);
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        if (this.position.y > Gdx.graphics.getHeight() / 10.0f)
            this.position.y -= 10;

        if (this.position.x < Gdx.graphics.getWidth() && this.position.y <= Gdx.graphics.getHeight() / 10.0f)
            this.position.x += 1;

        bonusItemsBound.update(this.position.x, this.position.y, this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight());
    }

    public Vector2 getPosition() {return this.position;}
}
