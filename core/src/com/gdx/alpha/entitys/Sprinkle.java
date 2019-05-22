package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Admin on 26.01.15.
 */
public class Sprinkle extends Actor {
    //private Sperm sperm;
    private Vector2 pos;
    private Vector2 dir;
    private float speed; //добавить в конструктор чтобы можно было задавать скорость движение взависимости от времени длительности уровня
    private static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private static final float SPERM_WIDTH = 123.0f;
    private static final float SPERM_HEIGHT = 46.0f;
    public Array<Sperm> sprinkle;
    private int[][] sprinklePos = {{0,1,1,0,0,0,0},
                                   {1,1,1,1,1,0,0},
                                   {1,1,1,1,1,1,1},
                                   {1,1,1,1,1,0,0},
                                   {0,1,1,0,0,0,0}
    };

    public Sprinkle(TextureAtlas spermAtlas){
        sprinkle = new Array<Sperm>();
        pos = new Vector2();
        dir = new Vector2();
        speed = 0.625f; //0.0625 - 5 минут скорость капли чем больше число тем выше скорость движения капли
     for (int i = 0; i < 5; i++) {
         for (int j = 0; j < 7; j++) {
             if (sprinklePos[i][j] != 0) {
                 pos.x = SCREEN_WIDTH + SPERM_WIDTH / 2 * j;
                 pos.y = (SCREEN_HEIGHT / 2 + (SPERM_HEIGHT / 2 + SPERM_HEIGHT)) - SPERM_HEIGHT  * i;
                 sprinkle.add(new Sperm(new Vector2(pos.x, pos.y), dir, speed, spermAtlas));
                 //System.out.println("X: "+pos.x  + " Y: " + pos.y);
             }

         }
     }
    }

    public Array<Sperm> getSpermArray(){
        return sprinkle;
    }
}
