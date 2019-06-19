package com.gdx.alpha.entitys;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class BacteriasColony extends Actor {
    private Vector2 position;
    private Array<Bacterium> bacteriumArray;
    private Array<Microbe> colony;
    private static final float SPERM_WIDTH = 50.0f;
    private static final float SPERM_HEIGHT = 50.0f;
    //private int[][] colony_position;
    private int figureType;
    private int colony_position[][] = {{1,1,1,1},
        {1,1,1,1},
        {1,1,1,1},
        {1,1,1,1},};

    void BacteriasColony(){

        randomizeColonyFigure();
    }

    private void randomizeColonyFigure()
    {
        figureType = MathUtils.random(0,4);
        switch (figureType){
            case 0:
               //
                break;
            case 1:
                //
                break;
            case 2:
                //
                break;
            case 3:
                //
                break;
            case 4:
                //
                break;
        }
    }
}// end of class
