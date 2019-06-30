package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ro|)e|\| on 18.02.15.
 */
public class ParallaxBackgroundLayer01 extends Actor {
    private Array<TextureRegion> backgroundDownLayer0;
    private Array<TextureRegion> backgroundUpLayer0;

    private Array<Vector2> downLayerPosition;
    private Array<Vector2> upLayerPosition;

    private float SCREEN_WIDTH;
    private Integer PART_COUNT;


    ParallaxBackgroundLayer01(TextureAtlas backgroundAtlas){

        SCREEN_WIDTH = Gdx.graphics.getWidth();
        float SCREEN_HEIGHT = Gdx.graphics.getHeight();
        PART_COUNT = (int) Math.ceil(SCREEN_WIDTH / (double) backgroundAtlas.findRegion("layer02down").getRegionWidth()) + 1;

        backgroundDownLayer0 = new Array<TextureRegion>(PART_COUNT);
        backgroundUpLayer0 = new Array<TextureRegion>(PART_COUNT);

        downLayerPosition = new Array<Vector2>();
        upLayerPosition = new Array<Vector2>();

        for (int i = 0; i < PART_COUNT; i++) {
            backgroundDownLayer0.add(backgroundAtlas.findRegion("layer02down"));
            backgroundUpLayer0.add(backgroundAtlas.findRegion("layer02up"));

            downLayerPosition.add(new Vector2(SCREEN_WIDTH - backgroundDownLayer0.get(i).getRegionWidth() * (i + 1), 0));
            upLayerPosition.add(new Vector2(SCREEN_WIDTH - backgroundUpLayer0.get(i).getRegionWidth() * (i + 1),
                    SCREEN_HEIGHT - backgroundUpLayer0.get(i).getRegionHeight()));

            System.out.println("DLPositionX: " + downLayerPosition.get(i).x + " Pos " + i +" R_width " + backgroundDownLayer0.get(i).getRegionWidth() +
                    "SC_width" + SCREEN_WIDTH);
        }
        System.out.println("PART_COUNT: " + PART_COUNT);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int i = 0; i < PART_COUNT; i++) {
            batch.draw(backgroundDownLayer0.get(i),downLayerPosition.get(i).x, downLayerPosition.get(i).y);
            batch.draw(backgroundUpLayer0.get(i),upLayerPosition.get(i).x, upLayerPosition.get(i).y);

        }
    }

    @Override
    public void act(float delta) {
        //super.act(delta);
        for (int i = 0; i < PART_COUNT; i++){
            downLayerPosition.get(i).x += 2;
            if (downLayerPosition.get(i).x >= SCREEN_WIDTH){
                downLayerPosition.get(i).x =  - (backgroundDownLayer0.get(i).getRegionWidth() * (PART_COUNT - 1) - SCREEN_WIDTH) -
                        backgroundDownLayer0.get(i).getRegionWidth() + (downLayerPosition.get(i).x - SCREEN_WIDTH);
            }

            upLayerPosition.get(i).x += 2;
            if (upLayerPosition.get(i).x >= SCREEN_WIDTH){
                upLayerPosition.get(i).x = - (backgroundUpLayer0.get(i).getRegionWidth() * (PART_COUNT - 1) - SCREEN_WIDTH) -
                        backgroundUpLayer0.get(i).getRegionWidth() + (upLayerPosition.get(i).x - SCREEN_WIDTH);
            }
        }
    }
}
