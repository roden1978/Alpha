package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Ro|)e|\| on 07.01.2015.
 */
public class AboutScreen extends ObjectScreen {

    private String NAME;
    private float lifeTime;
    private float time;
    private Stage stage;
    private Table table;
    private Skin skin, button_skin;
    private TextureAtlas textureAtlas, buttonAtlas;
    private Texture backgr;
    private BitmapFont bitmapFont;
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private ScreenManager screenManager;
    private TextButton backButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private Image  background;


    AboutScreen(ScreenManager screenManager){
        NAME = "LevelScreen";
        lifeTime = 0.0f;
        time = 0.0f;
        this.screenManager = screenManager;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas(Gdx.files.internal("pane/scroll_ui.pack"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/buttons.pack"));
        skin = new Skin(Gdx.files.internal("ui/ui_settings.json"),textureAtlas);
        button_skin = new Skin(buttonAtlas);
        table = new Table();
        backgr = new Texture(Gdx.files.internal("ui/AboutScreen.png"));
        background = new Image(backgr);


        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("JFRocSol_rus.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters="АБВГДЕЁЖЗИКЛМНОПРСТУФХЦШЩЬЫЪЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ";
        bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        freeTypeFontGenerator.dispose();


        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.up = button_skin.getDrawable("button_up");
        textButtonStyle.down = button_skin.getDrawable("button_down");
        textButtonStyle.pressedOffsetX = 5.0f;
        textButtonStyle.pressedOffsetY = -5.0f;

        backButton = new TextButton("BACK",textButtonStyle);
        backButton.pad(20.0f);
        backButton.padBottom(25.0f);


        if (!screenManager.getScreenMusic().isPlaying() && screenManager.getOnoff())
            screenManager.getScreenMusic().play();
    }

    @Override
    public void show() {
        table.clear();
        table.setFillParent(true);
        table.bottom();
        table.add(backButton).left().pad(20.0f).bottom();
        background.setX(Gdx.graphics.getWidth()/2.0f - background.getWidth()/2.0f);
        background.setY(Gdx.graphics.getHeight()/2.0f - background.getHeight()/2.0f);
        background.setScale(Gdx.graphics.getWidth() / 1280);
        stage.addActor(background);
        stage.addActor(table);


        backButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (screenManager.getOnoff())
                    screenManager.getButtonClickSound().play();
                screenManager.setCurrentScreen(new MenuScreen(screenManager));
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //super.resize(width, height);
        stage.getViewport().update(width, height, false);
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
        //super.dispose();
        stage.dispose();
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
        return NAME;
    }


}
