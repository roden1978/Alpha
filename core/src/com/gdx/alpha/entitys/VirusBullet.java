package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by admin on 01.02.2015.
 */
public class VirusBullet extends Microbe {
    private TextureAtlas virusBulletsAtlas;
    private TextureRegion virusBulletTexture;
    private Vector2 direction;
    private double angel = 0;
    private Bounds bulletBound;
    private float boundWidth;
    private float boundHeight;

    public VirusBullet(int bulletType, Vector2 direction, TextureAtlas virusBulletsAtlas) {
        this.direction = direction;
        this.virusBulletsAtlas = virusBulletsAtlas;
        init(bulletType);
        boundWidth = virusBulletTexture.getRegionWidth();
        boundHeight = virusBulletTexture.getRegionHeight();
        bulletBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
        setPrice(10);
    }

    public VirusBullet(Vector2 position, float speed, int bulletType, Vector2 direction, TextureAtlas virusBulletsAtlas) {
        super(position, speed);
        this.direction = direction;
        this.virusBulletsAtlas = virusBulletsAtlas;
        init(bulletType);
        boundWidth = virusBulletTexture.getRegionWidth();
        boundHeight = virusBulletTexture.getRegionHeight();
        bulletBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
    }

    public VirusBullet(float x, float y, float speed, int bulletType, Vector2 direction, TextureAtlas virusBulletsAtlas) {
        super(x, y, speed);
        this.direction = direction;
        this.virusBulletsAtlas = virusBulletsAtlas;
        init(bulletType);
        boundWidth = virusBulletTexture.getRegionWidth();
        boundHeight = virusBulletTexture.getRegionHeight();
        bulletBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
    }

    public void init(int bulletType){
        switch (bulletType){
            case 0:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir001_bullet"));
                break;
            case 1:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir002_bullet"));
                break;
            case 2:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir003_bullet"));
                break;
            case 3:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir004_bullet"));
                break;
            case 4:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir005_bullet"));
                break;
            case 5:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir006_bullet"));
                break;
            case 6:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir007_bullet"));
                break;
            case 7:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir008_bullet"));
                break;
            case 8:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir009_bullet"));
                break;
            case 9:
                virusBulletTexture = new TextureRegion(virusBulletsAtlas.findRegion("vir010_bullet"));
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(virusBulletTexture,position.x,position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.position.x += Math.cos(angel) * speed;
        this.position.y += Math.sin(angel) * speed;
        bulletBound.update(position.x, position.y, boundWidth, boundHeight);
    }

    @Override
    public Bounds getBound() {
        return bulletBound;
    }

    public void updateAngel(){
        angel = Math.atan2(direction.y - position.y, direction.x - position.x);
    }
}
