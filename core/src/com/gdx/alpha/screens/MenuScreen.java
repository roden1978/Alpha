package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.gdx.alpha.entitys.BacteryA;
import com.gdx.alpha.entitys.Sperm;

import java.io.IOException;


/**
 * Created by Ro|)e|\| on 02.01.2015.
 */
public class MenuScreen extends ObjectScreen {
    private String NAME;
    private float lifeTime;
    private float time;
    private BitmapFont bitmapFont;
    private BitmapFont bitmapFont72;
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontParameter parameter;
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton exitButton, playButton, aboutButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private Label heading;
    private Label.LabelStyle headingStyle;
    private TextureAtlas textureAtlas, soundAtlas;
    private TextureAtlas spermAtlas;
    private Texture menu_back;
    private Image background, soundOnOff;
    private ScreenManager screenManager;
    private Array<Sperm> spermObject;
    private BacteryA bacteriaA;
    private Array<BacteryA> bacteriaAArray;
    private Skin soundSkin;
    private StringBuilder stringBuilder;


    public MenuScreen(ScreenManager screenManager){
        NAME = "MenuScreen";
        lifeTime = 0.0f;
        time = 0.0f;
        spermObject = new Array <Sperm>();
        bacteriaAArray = new Array<BacteryA>();
        this.screenManager = screenManager;
        stringBuilder = new StringBuilder();

        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("JFRocSol_rus.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.characters="АБВГДЕЁЖЗИКЛМНОПРСТУФХЦШЩЬЫЪЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ";
        parameter.size = 40;
        bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        bitmapFont72 = new BitmapFont(Gdx.files.internal("font/stonefont.fnt"));
        bitmapFont72.getData().setScale(1.5f);
        freeTypeFontGenerator.dispose();

        textureAtlas = new TextureAtlas(Gdx.files.internal("ui/buttons.pack"));
        spermAtlas = new TextureAtlas(Gdx.files.internal("sperm/sperm.pack"));
        soundAtlas = new TextureAtlas(Gdx.files.internal("ui/sound.pack"));
        menu_back = new Texture(Gdx.files.internal("ui/menu_back.png"));


        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        background = new Image(menu_back);
        if (screenManager.getOnoff())
            soundOnOff = new Image(soundAtlas.findRegion("sound_on"));
        else
            soundOnOff = new Image(soundAtlas.findRegion("sound_off"));
        skin = new Skin(textureAtlas);
        soundSkin = new Skin(soundAtlas);

        table = new Table();

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.font = bitmapFont;
        textButtonStyle.pressedOffsetX = 5;
        textButtonStyle.pressedOffsetY = -5;
        exitButton = new TextButton("EXIT",textButtonStyle);
        exitButton.pad(20.0f);
        exitButton.padBottom(25.0f);

        playButton = new TextButton("PLAY", textButtonStyle);
        playButton.pad(20.0f);
        playButton.padBottom(25.0f);

        aboutButton = new TextButton("ABOUT", textButtonStyle);
        aboutButton.pad(20.0f);
        aboutButton.padBottom(25.0f);

        headingStyle = new Label.LabelStyle();
        headingStyle.font = bitmapFont72;
        heading = new Label("ALPHA",headingStyle);
    }
    @Override
    public void show() {
        //super.show();
        table.clear();
        table.setBounds(0, 0, stage.getWidth(), stage.getHeight());
        table.top();
        table.add(heading).colspan(5).expandX().spaceBottom(stage.getHeight()/10).align(Align.center).row();
        table.add().width(table.getWidth() / 5 * 3);
        table.add(playButton).spaceBottom(playButton.getHeight()/10).fillX().row();
        table.add().width(table.getWidth() / 5 * 3);
        table.add(aboutButton).spaceBottom(aboutButton.getHeight()/10).fillX().row();
        table.add().width(table.getWidth() / 5 * 3);
        table.add(exitButton).spaceBottom(exitButton.getHeight()).fillX().row();
        table.add().width(table.getWidth() / 5 * 3);
        table.add(soundOnOff).center();
        background.setFillParent(true);
        stage.addActor(background);

        for (int i = 0; i < 10; i++){
            spermObject.add(new Sperm(new Vector2(stage.getWidth(), MathUtils.random(50, stage.getHeight() - 50)),
                    MathUtils.random(10),spermAtlas)); //new Vector2(0,stage.getHeight()),
            stage.addActor(spermObject.get(i));
        }

        for (int i = 0; i < 10; i++){
            bacteriaA = new BacteryA(new Vector2(0,MathUtils.random(50, stage.getHeight() - 50)),
                    new Vector2(0,0),MathUtils.random(10));
            bacteriaA.setPosition(-bacteriaA.getTextureRegionWidth(), MathUtils.random(50, stage.getHeight() - 50));
            bacteriaAArray.add(bacteriaA);
            stage.addActor(bacteriaAArray.get(i));
        }

        stage.addActor(table);

        exitButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.exit();
                if (screenManager.getOnoff())
                    screenManager.getButtonClickSound().play();
                return true; //super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                Gdx.app.exit();
            }
        });

        playButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (screenManager.getOnoff())
                    screenManager.getButtonClickSound().play();
                screenManager.setCurrentScreen(new LevelScreen(screenManager));
                super.touchUp(event, x, y, pointer, button);
            }
        });

        aboutButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(new AboutScreen(screenManager));
                if (screenManager.getOnoff())
                    screenManager.getButtonClickSound().play();
                super.touchUp(event, x, y, pointer, button);
            }
        });

        soundOnOff.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (screenManager.getOnoff()){
                    soundOnOff.setDrawable(soundSkin.getDrawable("sound_off"));
                    if (screenManager.getOnoff())
                        screenManager.getButtonClickSound().play();

                    screenManager.setOnoff(false);

                    if (screenManager.getScreenMusic().isPlaying())
                        screenManager.getScreenMusic().stop();


                    saveSoundParamFile(screenManager.getOnoff());

                }else{
                    soundOnOff.setDrawable(soundSkin.getDrawable("sound_on"));

                    screenManager.setOnoff(true);

                    if (screenManager.getOnoff())
                        screenManager.getButtonClickSound().play();

                    screenManager.getScreenMusic().play();

                    saveSoundParamFile(screenManager.getOnoff());

                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        screenManager.getScreenMusic().setLooping(true);
        if (screenManager.getOnoff())
            screenManager.getScreenMusic().play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //stage.setDebugAll(true);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //super.resize(width, height);
        stage.getViewport().update(width, height, false);
        table.invalidateHierarchy();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        textureAtlas.dispose();
        bitmapFont.dispose();
        bitmapFont72.dispose();
        menu_back.dispose();
    }

    @Override
    public float getLifeTime() {
        return super.getLifeTime();
    }

    @Override
    public float getTime() {
        return super.getTime();
    }

    @Override
    public String getNAME() {
        return super.getNAME();
    }

    private void saveSoundParamFile(Boolean onOff) {
        Preferences pref = Gdx.app.getPreferences("sound");
        pref.putBoolean("sound", onOff);
        pref.flush();
    }
}
