package com.gdx.alpha.entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Класс "Микроб" родительский класс для сущностей вирус, бактерия и кондом
 * вирусы умеют срелять себе подобными, но не умеют размножаться
 * бактерии умеют размножаться, но не умеют срелять
 * Created by Ro|)e|\| on 31.01.2015.
 */
public class Microbe extends Actor {
    protected Vector2 position;
    protected float speed;
    private Bounds bound;
    protected int health;
    private int price;
    private Array<VirusBullet> bulletsArray;
    protected String entity;

    Microbe(){
        this.position = new Vector2(0.0f, 0.0f);
        this.speed = 0.0f;
        bound = new Bounds(0,0,0,0);
        this.health = 0;
        this.price = 0;
        this.bulletsArray = new Array<VirusBullet>();
        this.entity = "";
    }

    Microbe(Vector2 position, float speed){
        //super(position,speed);
        this.position = position;
        this.speed = speed;
        this.bound = new Bounds(0,0,0,0);
        this.health = 0;
        this.price = 0;
        this.bulletsArray = new Array<VirusBullet>();
        entity = "";
    }

    //установка позиции микроба
    public void setPosition(Vector2 position){
        this.position = position;
    }

    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }
//получение позиции микроба
    public Vector2 getPosition(){
        return this.position;
    }

    public float getPositionX(){
        return this.position.x;
    }

    public float getPositionY(){
        return this.position.y;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    @Override
    public boolean remove() {
        return super.remove();
    }

    public void changeHealth(int value){
        this.health -= value;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){return this.health;}

    public Array<VirusBullet> getBulletsArray(){
        return bulletsArray;
    }

    public Bounds getBound(){
        return bound;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEntity() {return entity;}
}
