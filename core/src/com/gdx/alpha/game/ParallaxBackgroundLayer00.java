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
public class ParallaxBackgroundLayer00 extends Actor {
    private Array<TextureRegion> backgroundDownLayer0;
    private Array<TextureRegion> backgroundUpLayer0;

    private Vector2 downLayer00Position;
    private Vector2 downLayer01Position;
    private Vector2 downLayer02Position;

    private Vector2 upLayer00Position;
    private Vector2 upLayer01Position;
    private Vector2 upLayer02Position;


    ParallaxBackgroundLayer00(TextureAtlas backgroundAtlas){
        backgroundDownLayer0 = new Array<TextureRegion>(3);
        backgroundUpLayer0 = new Array<TextureRegion>(3);

        for (int i = 0; i < 3; i++) {
            backgroundDownLayer0.add(backgroundAtlas.findRegion("layer01down"));
            backgroundUpLayer0.add(backgroundAtlas.findRegion("layer01up"));
        }

        //downLayer00Position = new Vector2(Gdx.graphics.getWidth() - backgroundDownLayer0.get(0).getRegionWidth(), 0);
        downLayer00Position = new Vector2(backgroundDownLayer0.get(0).getRegionWidth(), 0);
        downLayer01Position = new Vector2(0, 0);
        downLayer02Position = new Vector2(- backgroundDownLayer0.get(0).getRegionWidth(), 0);

       /* upLayer00Position = new Vector2(Gdx.graphics.getWidth() - backgroundUpLayer0.get(0).getRegionWidth(),
                Gdx.graphics.getHeight() - backgroundUpLayer0.get(0).getRegionHeight());*/
        upLayer00Position = new Vector2(backgroundUpLayer0.get(0).getRegionWidth(),
                Gdx.graphics.getHeight() - backgroundUpLayer0.get(0).getRegionHeight());
        upLayer01Position = new Vector2(0,Gdx.graphics.getHeight() - backgroundUpLayer0.get(0).getRegionHeight());
        upLayer02Position = new Vector2(- backgroundUpLayer0.get(0).getRegionWidth(),
                Gdx.graphics.getHeight() - backgroundUpLayer0.get(0).getRegionHeight());


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int i = 0; i < 3; i++) {
            switch (i){
                case 0:
                    batch.draw(backgroundDownLayer0.get(i),downLayer00Position.x, downLayer00Position.y);
                    batch.draw(backgroundUpLayer0.get(i),upLayer00Position.x, upLayer00Position.y);
                    break;
                case 1:
                    batch.draw(backgroundUpLayer0.get(i),upLayer01Position.x, upLayer01Position.y);
                    batch.draw(backgroundDownLayer0.get(i),downLayer01Position.x, downLayer01Position.y);
                    break;
                case 2:
                    batch.draw(backgroundUpLayer0.get(i),upLayer02Position.x, upLayer02Position.y);
                    batch.draw(backgroundDownLayer0.get(i),downLayer02Position.x, downLayer02Position.y);
                    break;
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        downLayer00Position.x += 3;
        downLayer01Position.x += 3;
        downLayer02Position.x += 3;
        if (downLayer00Position.x >= Gdx.graphics.getWidth())
            downLayer00Position.x = - (backgroundDownLayer0.get(0).getRegionWidth() *2.0f -
                    Gdx.graphics.getWidth()) - backgroundDownLayer0.get(0).getRegionWidth() + (downLayer00Position.x - Gdx.graphics.getWidth());
        if (downLayer01Position.x >= Gdx.graphics.getWidth())
            downLayer01Position.x = - (backgroundDownLayer0.get(0).getRegionWidth() *2.0f -
                    Gdx.graphics.getWidth()) - backgroundDownLayer0.get(0).getRegionWidth() + (downLayer01Position.x - Gdx.graphics.getWidth());
        if (downLayer02Position.x >= Gdx.graphics.getWidth())
            downLayer02Position.x = - (backgroundDownLayer0.get(0).getRegionWidth() *2.0f -
                    Gdx.graphics.getWidth()) - backgroundDownLayer0.get(0).getRegionWidth() + (downLayer02Position.x - Gdx.graphics.getWidth());

        upLayer00Position.x += 3;
        upLayer01Position.x += 3;
        upLayer02Position.x += 3;
        if (upLayer00Position.x >= Gdx.graphics.getWidth())
            upLayer00Position.x = - (backgroundUpLayer0.get(0).getRegionWidth() *2.0f -
                    Gdx.graphics.getWidth()) - backgroundUpLayer0.get(0).getRegionWidth() + (upLayer00Position.x - Gdx.graphics.getWidth());
        if (upLayer01Position.x >= Gdx.graphics.getWidth())
            upLayer01Position.x = - (backgroundUpLayer0.get(0).getRegionWidth() *2.0f -
                    Gdx.graphics.getWidth()) - backgroundUpLayer0.get(0).getRegionWidth() + (upLayer01Position.x - Gdx.graphics.getWidth());
        if (upLayer02Position.x >= Gdx.graphics.getWidth())
            upLayer02Position.x = - (backgroundUpLayer0.get(0).getRegionWidth() *2.0f -
                    Gdx.graphics.getWidth()) - backgroundUpLayer0.get(0).getRegionWidth() + (upLayer02Position.x - Gdx.graphics.getWidth());
      /*  downLayer00Position.x += 3;
        downLayer01Position.x += 3;
        downLayer02Position.x += 3;
        if (downLayer00Position.x >= Gdx.graphics.getWidth())
            downLayer00Position.x = -backgroundDownLayer0.get(0).getRegionWidth();
        if (downLayer01Position.x >= Gdx.graphics.getWidth())
            downLayer01Position.x = -backgroundDownLayer0.get(0).getRegionWidth();
        if (downLayer02Position.x >= Gdx.graphics.getWidth())
            downLayer02Position.x = -backgroundDownLayer0.get(0).getRegionWidth();

        upLayer00Position.x += 3;
        upLayer01Position.x += 3;
        upLayer02Position.x += 3;
        if (upLayer00Position.x >= Gdx.graphics.getWidth())
            upLayer00Position.x = -backgroundUpLayer0.get(0).getRegionWidth();
        if (upLayer01Position.x >= Gdx.graphics.getWidth())
            upLayer01Position.x = -backgroundUpLayer0.get(0).getRegionWidth();
        if (upLayer02Position.x >= Gdx.graphics.getWidth())
            upLayer02Position.x = -backgroundUpLayer0.get(0).getRegionWidth();*/

    }
}
