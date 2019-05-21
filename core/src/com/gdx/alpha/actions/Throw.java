package com.gdx.alpha.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.gdx.alpha.entitys.Axe;
import com.gdx.alpha.entitys.Bounds;

/**
 * Created by Admin on 27.01.15.
 */
public class Throw extends Actor{
    /*
    Типы оружия:
     0 - топор;
     1 - камень;
     2 - дубина
     */
    private Vector2 position;
    private float speed;
    private static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private float interval;
    private float intervalDefault;
    private float intervalCurrent;
    private float intervalDelta;
    private Array<Axe> axeArray;
    private int weaponType;
    private Vector2 playerPosition;
    private Axe removedAxe = null;
    private TextureAtlas axeAtlas;
    private boolean throwing;

    public Throw(int weaponType, Vector2 playerPosition, TextureAtlas axeAtlas){
        this.weaponType = weaponType;
        this.playerPosition = playerPosition;
        this.axeAtlas = axeAtlas;
        intervalDefault = 0.3f;
        throwing = true;
        updatePosition(playerPosition);
        switch(weaponType){
            case 0:
                interval = intervalDefault;  //скорость бросания оружия
                intervalCurrent = interval;
                intervalDelta = 0.0f;
                axeArray = new Array<Axe>();
                //removedAxe = new Axe();
                break;
            case 1:
                //
                break;
            case 2:
                //
                break;
        }

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

        switch(weaponType){
            case 0:
                if (intervalDelta > interval){
                    axeArray.add(new Axe(new Vector2(playerPosition),axeAtlas));
                    intervalDelta = 0.0f;
                    //System.out.println("X: "+playerPosition.x  + " Y: " + playerPosition.y + " size " + axeArray.size);
                }
         break;
            case 1:
                //
                break;
            case 2:
                //
                break;
        }


    }

    public void updatePosition(Vector2 playerPosition){
        this.playerPosition.x = playerPosition.x - 26.0f;
        this.playerPosition.y = playerPosition.y + 70.0f;
    }

    public Array<Axe> getAxeArray(){
        return axeArray;
    }

    public void setInterval(float interval){
        this.interval = interval;
    }
    public void restoreDefaultInterval(){
        this.interval = intervalDefault;
    }
    public void restoreIntervalCurrent(){
        this.interval = intervalCurrent;
    }
    public float getIntervalCurrent(){
        return interval;
    }
    public void setThrowing(boolean throwing){
        this.throwing = throwing;
    }
}
