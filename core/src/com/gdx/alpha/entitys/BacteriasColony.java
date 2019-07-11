package com.gdx.alpha.entitys;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class BacteriasColony extends Actor {
    private Vector2 position;
    private float bac_pos_x;
    private float bac_pos_y;
    private TextureAtlas bacteriumAtlas;
    private Array<Microbe> colony;
    private static final float BAC_WIDTH = 50.0f;
    private static final float BAC_HEIGHT = 50.0f;
    private float defaultDelay;
    private float delay;
    private Boolean next_item;
    private Boolean next;
    private int figureType;
    private int bacteriumType;
    private int[][] colony_option0 = {{1,1,1,1},
                                      {1,1,1,1},
                                      {1,1,1,1},
                                      {1,1,1,1}};
    private int[][] colony_option1 = {{0,1,1,0},
                                      {1,1,1,1},
                                      {1,1,1,1},
                                      {0,1,1,0}};
    private int[][] colony_option2={{1,0,0,1},
                                    {1,0,0,1},
                                    {1,0,0,1},
                                    {1,0,0,1}};
    private int[][] colony_option3={{1,1,1,1},
                                    {1,0,0,1},
                                    {1,0,0,1},
                                    {1,1,1,1}};
    private int[][] colony_option4={{0,1,1,0},
                                    {0,1,1,0},
                                    {0,1,1,0},
                                    {0,1,1,0}};
    private int[][] colony_option5={{0,1,1,0},
                                    {1,1,1,1},
                                    {1,1,1,1},
                                    {0,1,1,0}};
    private int[][] colony_option6={{1,0,0,1},
                                    {0,1,1,0},
                                    {0,1,1,0},
                                    {1,0,0,1}};

    public BacteriasColony(Vector2 position, TextureAtlas bacteriumAtlas){
        this.position = position;
        this.bac_pos_x = position.x;
        this.bac_pos_y = position.y;
        this.bacteriumAtlas = bacteriumAtlas;
        colony = new Array<Microbe>();
        defaultDelay = delay = 0.2f;
        next_item = false;
        next = false;

        randomizeColonyFigure();
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
       if (next)
           Delay(delta);
    }

    private void randomizeColonyFigure()
    {
        figureType = MathUtils.random(0,6);
        switch (figureType){
            case 0:
                createColony(colony_option0);
                break;
            case 1:
                createColony(colony_option1);
                break;
            case 2:
                createColony(colony_option2);
                break;
            case 3:
                createColony(colony_option3);
                break;
            case 4:
                createColony(colony_option4);
                break;
            case 5:
                createColony(colony_option5);
                break;
            case 6:
                createColony(colony_option6);
                break;
        }
    }
    private void createColony(int[][] colony_options){
        bacteriumType = MathUtils.random(0,9);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (colony_options[i][j] != 0) {
                    position.x = bac_pos_x + (BAC_WIDTH * j);
                    position.y = bac_pos_y - (BAC_HEIGHT  * i); //(BAC_HEIGHT / 2 + BAC_HEIGHT) - BAC_HEIGHT  * i; // ;
                    colony.add(new Bacterium(new Vector2(position.x, position.y),bacteriumAtlas,bacteriumType));
                    System.out.println("Bacterium X: "+position.x  + " Y: " + position.y);
                }

            }
        }
    }

    public Array<Microbe> getBacteriumArray(){
        return colony;
    }

  /*  public Boolean getNext_item() {
        return next_item;
    }

    public void setNext_item(Boolean next_item) {
        this.next_item = next_item;
    }*/

    private void Delay(float delta){
        delay -= delta;
        if (delay < 0){
            this.next_item = true;
            delay = defaultDelay;
            System.out.println("Delay: " + delay);
        }
    }

  /*  public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }*/
}// end of class
