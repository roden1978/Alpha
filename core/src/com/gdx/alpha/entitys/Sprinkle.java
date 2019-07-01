package com.gdx.alpha.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ro|)e|\| on 26.01.15.
 */
public class Sprinkle extends Actor{
    //private Sperm sperm;
    private Vector2 position;
    private TextureAtlas spermAtlas;
    private float speed; //добавить в конструктор чтобы можно было задавать скорость движение взависимости от времени длительности уровня и разрешения экрана
    private static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private static final float SPERM_WIDTH = 123.0f;
    private static final float SPERM_HEIGHT = 46.0f;
    private Array<Sperm> sprinkleArray;
    private int[][] sprinklePos01 = {{0,1,1,0,0,0,0},
                                    {1,0,0,1,1,0,0},
                                    {1,0,0,0,0,1,1},
                                    {1,0,0,1,1,0,0},
                                    {0,1,1,0,0,0,0}};
    private int[][] sprinklePos02 = {{0,0,1,0,0,0,0},
                                    {0,1,0,0,0,0,0},
                                    {1,1,1,1,1,1,1},
                                    {0,1,0,0,0,0,0},
                                    {0,0,1,0,0,0,0}};
    private int[][] sprinklePos03 = {{0,0,1,1,1,0,0},
                                    {0,1,0,0,0,1,0},
                                    {1,0,0,0,0,0,1},
                                    {0,1,0,0,0,1,0},
                                    {0,0,1,1,1,0,0}};

    public Sprinkle(TextureAtlas spermAtlas, float koeff, int level){
        this.sprinkleArray = new Array<Sperm>();
        this.position = new Vector2();
        this.spermAtlas = spermAtlas;
        this.speed = 0.625f * koeff - 0.1f * level; //0.0625 - 5 минут скорость капли чем больше число тем выше скорость движения капли
        System.out.println("Speed: "+speed);
        randomizeSprinkle();
    }

    public Array<Sperm> getSpermArray(){
        return this.sprinkleArray;
    }

    private void randomizeSprinkle(){
        switch (MathUtils.random(0, 2)){
            case 0:
                createSprinkle(sprinklePos01);
                break;
            case 1:
                createSprinkle(sprinklePos02);
                break;
            case 2:
                createSprinkle(sprinklePos03);
                break;
        }
    }
    private void createSprinkle(int[][] sprinkleFigure){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (sprinkleFigure[i][j] != 0) {
                    this.position.x = SCREEN_WIDTH + SPERM_WIDTH / 2 * j;
                    this.position.y = (SCREEN_HEIGHT / 2 + (SPERM_HEIGHT / 2 + SPERM_HEIGHT)) - SPERM_HEIGHT  * i;
                    this.sprinkleArray.add(new Sperm(new Vector2(this.position.x, this.position.y), this.speed, this.spermAtlas));
                    //System.out.println("X: "+pos.x  + " Y: " + pos.y);
                }

            }
        }
    }
}
