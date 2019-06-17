package com.gdx.alpha.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.gdx.alpha.entitys.Axe;
import com.gdx.alpha.entitys.Cudgel;
import com.gdx.alpha.entitys.Mace;
import com.gdx.alpha.entitys.Stone;
import com.gdx.alpha.entitys.Weapon;

/**
 * Created by Ro|)e|\| on 27.01.15.
 */
public class Throw extends Actor{
    /*
    Типы оружия:
     0 - топор;
     1 - камень;
     2 - дубина
     */
       //private static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    //private static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private float interval;
    private float intervalDefault;
    private float intervalDelta;
    private float weaponInterval;
    private Array<Weapon> weaponArray;
    private int weaponType;
    private Vector2 playerPosition;
    //private Axe removedAxe;
    //private TextureAtlas axeAtlas;
    private boolean throwing;
    private Sound throwAxeSound;
    private float weaponDelay;
    private Boolean isDelay;

    public Throw(int weaponType, Vector2 playerPosition){
        this.weaponType = weaponType;
        this.playerPosition = playerPosition;
        //this.axeAtlas = axeAtlas;
        throwAxeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/axeThrow.mp3"));
        intervalDefault = 0.3f;
        weaponInterval = 6.0f;
        throwing = true;
        updatePosition(playerPosition);
        interval = intervalDefault;  //скорость бросания оружия
        //float intervalCurrent = interval;
        intervalDelta = 0.0f;
        weaponDelay = 0.0f;
        isDelay = false;
        weaponArray = new Array<Weapon>();


    }

    public void changeType(int weaponType){
        this.weaponType = weaponType;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*
        switch(weaponType){
            case 0:
               for (int i = 0; i < axeArray.size; i++)
                    axeArray.get(i).draw(batch, parentAlpha);
                break;
            case 1:
                //
                break;
            case 2:
                //
                break;
        }
*/
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (throwing)
            intervalDelta += delta;
        else
            intervalDelta = 0.0f;

        delayWeaponChange(delta);

       /* if (!isDelay)
            weaponType = 0;*/

        Weapon weapon;
        switch(weaponType){
            case 1:
                if (intervalDelta > interval){
                    weapon = new Mace(new Vector2(playerPosition));
                    setInterval(weapon.getInterval());
                    weaponArray.add(weapon);
                    intervalDelta = 0.0f;
                    throwAxeSound.play();
                    isDelay = true;
                    //System.out.println("X: "+playerPosition.x  + " Y: " + playerPosition.y + " size " + axeArray.size);
                }
                break;
            case 2:
                if (intervalDelta > interval) {
                    weapon = new Stone(new Vector2(playerPosition));
                    setInterval(weapon.getInterval());
                    weaponArray.add(weapon);
                    intervalDelta = 0.0f;
                    throwAxeSound.play();
                    isDelay = true;
                }
                    //System.out.println("X: "+playerPosition.x  + " Y: " + playerPosition.y + " size " + axeArray.size);
                break;
            case 3:
                if (intervalDelta > interval) {
                    weapon = new Cudgel(new Vector2(playerPosition));
                    setInterval(weapon.getInterval());
                    weaponArray.add(weapon);
                    intervalDelta = 0.0f;
                    throwAxeSound.play();
                    isDelay = true;
                }
                //System.out.println("X: "+playerPosition.x  + " Y: " + playerPosition.y + " size " + axeArray.size);
                break;
                default:
                if (intervalDelta > interval){
                    weaponArray.add(new Axe(new Vector2(playerPosition)));
                    intervalDelta = 0.0f;
                    throwAxeSound.play();
                    isDelay = false;
                    //System.out.println("X: "+playerPosition.x  + " Y: " + playerPosition.y + " size " + axeArray.size);
                }
                break;
        }
    }

    public void updatePosition(Vector2 playerPosition){
        this.playerPosition.x = playerPosition.x - 26.0f;
        this.playerPosition.y = playerPosition.y + 70.0f;
    }

    public Array<Weapon> getWeaponArray(){
        return weaponArray;
    }

    private void setInterval(float interval){
        this.interval = interval;
    }
    private void restoreDefaultInterval(){
        this.interval = intervalDefault;
    }
   /* public void restoreIntervalCurrent(){
        this.interval = intervalCurrent;
    }
    public float getIntervalCurrent(){
        return interval;
    }*/
    public void setThrowing(boolean throwing){
        this.throwing = throwing;
    }
    /*public Boolean getThrowing(){return throwing;}*/


    private void delayWeaponChange(float delta){ //Задержка до того как оружие сменится на оружие по умолчанию (топор)
        if (isDelay && weaponType != 0)
            weaponDelay += delta;
        if (weaponInterval < weaponDelay && isDelay) {
            weaponType = 0;
            restoreDefaultInterval();
            isDelay = false;
           weaponDelay = 0;
        }
        //System.out.println("XDelta: "+delta  + " WeaponDelay: " + weaponDelay);
    }
}
