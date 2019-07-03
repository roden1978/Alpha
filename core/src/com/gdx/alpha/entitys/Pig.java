package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pig extends Actor {

    private TextureRegion textureRegion;
    private Boolean isDraw;
    private Boolean forward;
    private Vector2 position;

    public Pig(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        this.isDraw = false;
        this.forward = true;
        this.position = new Vector2(- textureRegion.getRegionWidth(), 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        if (this.isDraw){
            batch.draw(this.textureRegion, this.position.x, this.position.y);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isDraw){
            if (this.position.x <= 0.0f && this.forward)
                this.position.x += 5.0f;
            if (this.position.x >= 0.0f)
                this.forward = false;
            if (!this.forward)
                this.position.x -= 5.0f;
            if (this.position.x <= - this.textureRegion.getRegionWidth()) {
                this.isDraw = false;
                this.forward = true;
            }
        }
    }
    public void setIsDraw(Boolean isDraw) {this.isDraw = isDraw;}

    public Boolean getIsDraw(){return this.isDraw;}
}
