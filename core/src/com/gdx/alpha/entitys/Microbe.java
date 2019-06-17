package com.gdx.alpha.entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Класс "Микроб" родительский класс для сущностей вирус и бактерия
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

    Microbe(){
        this.position = new Vector2(0.0f, 0.0f);
        this.speed = 0.0f;
        bound = new Bounds(0,0,0,0);
        this.health = 0;
        this.price = 0;
    }

    Microbe(Vector2 position, float speed){
        this.position = position;
        this.speed = speed;
        bound = new Bounds(0,0,0,0);
        this.health = 0;
        this.price = 0;
    }

    public Microbe (float x, float y, float speed){
        this.position.x = x;
        this.position.y = y;
        this.speed = speed;
        bound = new Bounds(0,0,0,0);
        this.health = 0;
        this.price = 0;
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
        return position;
    }

    public float getPositionX(){
        return position.x;
    }

    public float getPositionY(){
        return position.y;
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
    public int getHealth(){return health;}
    public Array<VirusBullet> getBulletsArray(){
        return null;
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
}
