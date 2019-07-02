package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Condom extends Microbe {
    private Array<TextureAtlas> textureAtlasArray;
    private TextureAtlas textureAtlas;
    private TextureAtlas lifeScaleAtlas;
    private Animation<TextureRegion> animation;
    private Vector2 position;
    private Bounds condomBound;
    private LifeScale lifeScale;
    private float condomBoundWidth;
    private float condomBoundHeight;
    private int maxHealth;
    private float stateTime;
    private float frameDuration;

    public Condom(Vector2 position, Array<TextureAtlas> textureAtlasArray, TextureAtlas lifeScaleAtlas) {
        super(position, 10.0f);
        this.position = position;
        this.stateTime = 0.0f;
        this.frameDuration = 1/20f;
        this.textureAtlasArray = textureAtlasArray;
        this.lifeScaleAtlas = lifeScaleAtlas;
        this.animation = new Animation<TextureRegion>(this.frameDuration, getCondomTextureAtlas().getRegions());
        this.condomBound = new Bounds(this.position.x, this.position.y,
                this.textureAtlas.getRegions().get(0).getRegionWidth(),
                this.textureAtlas.getRegions().get(0).getRegionHeight());
        this.condomBoundWidth = this.condomBound.getBox().getWidth();
        this.condomBoundHeight = this.condomBound.getBox().getHeight();
        this.lifeScale = new LifeScale(lifeScaleAtlas,this.position.x + this.condomBoundWidth/2.0f - this.lifeScaleAtlas.findRegion("green").getRegionWidth()/2.0f,
                position.y + this.condomBoundHeight, this.lifeScaleAtlas.findRegion("green").getRegionWidth());
        setPrice(200);
        setHealth(120);
        maxHealth = health;
        this.entity = "c";
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(this.animation.getKeyFrame(this.stateTime, true),this.position.x, this.position.y);
        this.lifeScale.draw(batch, parentAlpha);
    }

    @Override
    public Bounds getBound(){return this.condomBound;}

    @Override
    public void act(float delta) {
        //super.act(delta);
        this.stateTime +=delta;
        this.position.x += speed;

        this.condomBound.update(this.position.x, this.position.y, this.condomBoundWidth, this.condomBoundHeight);
        this.lifeScale.setWidth(33.0f*health/maxHealth);
        this.lifeScale.setPosition(this.position.x + this.condomBoundWidth/2.0f - this.lifeScaleAtlas.findRegion("green").getRegionWidth()/2.0f,
                this.position.y + this.condomBoundHeight);
    }

    private TextureAtlas getCondomTextureAtlas() {
        switch (MathUtils.random(0, 3)) {
            case 0:
                this.textureAtlas = this.textureAtlasArray.get(0);
                break;
            case 1:
                this.textureAtlas = this.textureAtlasArray.get(1);
                break;
            case 2:
                this.textureAtlas = this.textureAtlasArray.get(2);
                break;
            case 3:
                this.textureAtlas = this.textureAtlasArray.get(3);
                break;
            default:
                this.textureAtlas = this.textureAtlasArray.get(0);
                break;
        }
        return this.textureAtlas;
    }
}
