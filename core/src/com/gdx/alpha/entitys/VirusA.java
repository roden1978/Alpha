package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by admin on 01.02.2015.
 */
public class VirusA extends Microbe {

    private TextureAtlas virusTextureAtlas;
    private TextureAtlas bulletTextureAtlas;
    private TextureAtlas lifeScaleAtlas;
    private TextureRegion virusTextureRegion;
    private static Player player = null;
    private VirusBullet bullet=null;
    private Array<VirusBullet> bulletsArray;
    private int weight;
    private int maxHealth;
    private float interval;
    private float intervalDelta;
    private Bounds virusBound;
    private LifeScale lifeScale;
    private float boundWidth;
    private float boundHeight;
    private float degree;

    public VirusA(Vector2 position, float speed, int weight, Player player,
                  TextureAtlas virusTextureAtlas, TextureAtlas bulletTextureAtlas, TextureAtlas lifeScaleAtlas) {
        super(position, speed);
        this.weight = weight;
        this.player = player;
        degree = 0;
        this.virusTextureAtlas = virusTextureAtlas;
        this.bulletTextureAtlas = bulletTextureAtlas;
        this.lifeScaleAtlas = lifeScaleAtlas;
        init();
        virusBound = new Bounds(position.x, position.y, boundWidth, boundHeight);
        lifeScale = new LifeScale(lifeScaleAtlas,position.x + boundWidth/2 - lifeScaleAtlas.findRegion("green").getRegionWidth()/2,
                position.y + boundHeight,lifeScaleAtlas.findRegion("green").getRegionWidth());
        maxHealth = health;
    }

    public void init(){
        intervalDelta = 0.0f;
        bulletsArray = new Array<VirusBullet>();
        switch (weight){
            case 0://small
                setHealth(70);
                setPrice(100);
                speed = 1.25f;
                interval = 0.6f;
                virusTextureRegion = virusTextureAtlas.findRegion("vir001_small");
                boundWidth = virusTextureAtlas.findRegion("vir001_small").getRegionWidth();
                boundHeight = virusTextureAtlas.findRegion("vir001_small").getRegionHeight();
                break;
            case 1://normal
                setHealth(100);
                setPrice(200);
                speed = 1.0f;
                interval = 1.0f;
                virusTextureRegion = virusTextureAtlas.findRegion("vir001_normal");
                boundWidth = virusTextureAtlas.findRegion("vir001_normal").getRegionWidth();
                boundHeight = virusTextureAtlas.findRegion("vir001_normal").getRegionHeight();
                break;
            case 2://big
                setHealth(150);
                setPrice(300);
                speed = 0.5f;
                interval = 1.5f;
                virusTextureRegion = virusTextureAtlas.findRegion("vir001_big");
                boundWidth = virusTextureAtlas.findRegion("vir001_big").getRegionWidth();
                boundHeight = virusTextureAtlas.findRegion("vir001_big").getRegionHeight();
                break;
            case 3://super big
                setHealth(200);
                setPrice(500);
                speed = 0.25f;
                interval = 2.0f;
                virusTextureRegion = virusTextureAtlas.findRegion("vir001_superbig");
                boundWidth = virusTextureAtlas.findRegion("vir001_superbig").getRegionWidth();
                boundHeight = virusTextureAtlas.findRegion("vir001_superbig").getRegionHeight();
                break;
        }
    }

    public void shot(float delta){
        intervalDelta += delta;

                if (intervalDelta > interval){//изменить тип пуль
                    bullet = new VirusBullet(0,new Vector2 (player.getPositionX(),
                            player.getPositionY() + player.getSpriteRegionHeight()/2),bulletTextureAtlas);
                    bullet.setPosition(position.x + virusTextureRegion.getRegionWidth(),
                            position.y + virusTextureRegion.getRegionWidth() / 2 - bullet.getWidth() / 2);
                    bullet.setSpeed(interval * 8);
                    bullet.updateAngel();
                    bulletsArray.add(bullet);
                    intervalDelta = 0.0f;
                    //System.out.println("X: "+bullet.getPositionX()  + " Y: " + bullet.getPositionY() + " size " + bulletsArray.size);
                }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(virusTextureRegion, this.position.x, this.position.y,virusTextureRegion.getRegionWidth()/2,
                    virusTextureRegion.getRegionHeight()/2,virusTextureRegion.getRegionWidth(),virusTextureRegion.getRegionHeight(),
                    1,1,degree);
        lifeScale.draw(batch,parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        shot(delta);
        this.position.x += speed;
        if (degree < -359.0f)
            degree = 0.0f;
        degree -=5.0f;
        virusBound.update(position.x, position.y, boundWidth, boundHeight);
        lifeScale.setWidth(33*health/maxHealth);
        lifeScale.setPosition(position.x + boundWidth/2 - lifeScaleAtlas.findRegion("green").getRegionWidth()/2,
                position.y + boundHeight);
    }

    @Override
    public Bounds getBound() {
        return virusBound;
    }

    @Override
    public Array<VirusBullet> getBulletsArray() {
        return bulletsArray;
    }

}
