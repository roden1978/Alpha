package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Admin on 18.02.15.
 */
public class ParallaxBackgroundLayer01 extends Actor {
    private Array<TextureRegion> backgroundDownLayer1;
    private Array<TextureRegion> backgroundUpLayer1;


    private Vector2 downLayer10Position;
    private Vector2 downLayer11Position;
    private Vector2 downLayer12Position;

    private Vector2 upLayer10Position;
    private Vector2 upLayer11Position;
    private Vector2 upLayer12Position;


    public ParallaxBackgroundLayer01(TextureAtlas backgroundAtlas){
        backgroundDownLayer1 = new Array<TextureRegion>(3);
        backgroundUpLayer1 = new Array<TextureRegion>(3);

        for (int i = 0; i < 3; i++) {
            backgroundDownLayer1.add(backgroundAtlas.findRegion("layer02down"));
            backgroundUpLayer1.add(backgroundAtlas.findRegion("layer02up"));
        }

        downLayer10Position = new Vector2(Gdx.graphics.getWidth() - backgroundDownLayer1.get(0).getRegionWidth(), 0);
        downLayer11Position = new Vector2(0, 0);
        downLayer12Position = new Vector2(- backgroundDownLayer1.get(0).getRegionWidth(), 0);

        upLayer10Position = new Vector2(Gdx.graphics.getWidth() - backgroundUpLayer1.get(0).getRegionWidth(),
                Gdx.graphics.getHeight() - backgroundUpLayer1.get(0).getRegionHeight());
        upLayer11Position = new Vector2(0,Gdx.graphics.getHeight() - backgroundUpLayer1.get(0).getRegionHeight());
        upLayer12Position = new Vector2(- backgroundUpLayer1.get(0).getRegionWidth(),
                Gdx.graphics.getHeight() - backgroundUpLayer1.get(0).getRegionHeight());


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int i = 0; i < 3; i++) {
            switch (i){
                case 0:
                    batch.draw(backgroundDownLayer1.get(0),downLayer10Position.x, downLayer10Position.y);
                    batch.draw(backgroundUpLayer1.get(0),upLayer10Position.x, upLayer10Position.y);
                    break;
                case 1:
                    batch.draw(backgroundDownLayer1.get(1),downLayer11Position.x, downLayer11Position.y);
                    batch.draw(backgroundUpLayer1.get(1),upLayer11Position.x, upLayer11Position.y);
                    break;
                case 2:
                    batch.draw(backgroundDownLayer1.get(2),downLayer12Position.x, downLayer12Position.y);
                    batch.draw(backgroundUpLayer1.get(2),upLayer12Position.x, upLayer12Position.y);
                    break;
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        downLayer10Position.x += 2;
        downLayer11Position.x += 2;
        downLayer12Position.x += 2;
        if (downLayer10Position.x >= Gdx.graphics.getWidth())
            downLayer10Position.x = -backgroundDownLayer1.get(0).getRegionWidth();
        if (downLayer11Position.x >= Gdx.graphics.getWidth())
            downLayer11Position.x = -backgroundDownLayer1.get(0).getRegionWidth();
        if (downLayer12Position.x >= Gdx.graphics.getWidth())
            downLayer12Position.x = -backgroundDownLayer1.get(0).getRegionWidth();


        upLayer10Position.x += 2;
        upLayer11Position.x += 2;
        upLayer12Position.x += 2;
        if (upLayer10Position.x >= Gdx.graphics.getWidth())
            upLayer10Position.x = -backgroundUpLayer1.get(0).getRegionWidth();
        if (upLayer11Position.x >= Gdx.graphics.getWidth())
            upLayer11Position.x = -backgroundUpLayer1.get(0).getRegionWidth();
        if (upLayer12Position.x >= Gdx.graphics.getWidth())
            upLayer12Position.x = -backgroundUpLayer1.get(0).getRegionWidth();
    }
}
