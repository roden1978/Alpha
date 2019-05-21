package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.gdx.alpha.game.GameManager;

/**
 * Created by admin on 07.01.2015.
 */
public class LevelScreen extends ObjectScreen {

    private String NAME;
    private float lifeTime;
    private float time;
    private Stage stage;
    private Table table;
    private Label heading;
    private Skin skin, button_skin,girlsFaceSkin;
    private TextureAtlas textureAtlas, buttonAtlas, girlsFaceAtlas;
    private Texture backgr;
    private BitmapFont bitmapFont;
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private ScreenManager screenManager;
    private ScrollPane scrollPane;
    private List girlsnames;
    private TextButton playButton, backButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private Array<String> names;
    private Image  background, girl_image;
    private Label scores, sperm, scoresAmountLabel, spermAmountLabel;
    private String scoreAmount, spermAmount;
    private int[] girlStatus;
    private ObjectScreen gameScreen;
    private int level;

    public LevelScreen(ScreenManager screenManager){
        NAME = "LevelScreen";
        lifeTime = 0.0f;
        time = 0.0f;
        level = 0;
        this.screenManager = screenManager;
        scoreAmount = "0000";
        spermAmount = "0000";
        girlStatus = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0};

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas(Gdx.files.internal("pane/scroll_ui.pack"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/buttons.pack"));
        girlsFaceAtlas = new TextureAtlas(Gdx.files.internal("girls/girls.pack"));
        names = new Array<String>(new String[]{"YULI","ANNA","LILA","LIZA","CAMERON","ALICE","MEGAN",
                "AMELI","NATASHA","ELENA","CLAUDIA","ALLISON","PAMELA","SOFIA","SVETLANA"});
        skin = new Skin(Gdx.files.internal("ui/ui_settings.json"),textureAtlas);
        button_skin = new Skin(buttonAtlas);
        girlsFaceSkin = new Skin(girlsFaceAtlas);
        table = new Table();
        girl_image = new Image(girlsFaceSkin.getDrawable("girl001o"));
        backgr = new Texture(Gdx.files.internal("ui/menu_back.png"));
        background = new Image(backgr);


        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("JFRocSol_rus.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters="АБВГДЕЁЖЗИКЛМНОПРСТУФХЦШЩЬЫЪЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ";
        bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        //bitmapFont.setScale(2.0f);
        freeTypeFontGenerator.dispose();

        girlsnames = new List(skin);
        girlsnames.setItems(names);

        scrollPane = new ScrollPane(girlsnames,skin);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.up = button_skin.getDrawable("button_up");
        textButtonStyle.down = button_skin.getDrawable("button_down");
        textButtonStyle.pressedOffsetX = 5.0f;
        textButtonStyle.pressedOffsetY = -5.0f;

        playButton = new TextButton("PLAY", textButtonStyle);
        playButton.pad(20.0f);
        playButton.padBottom(25.0f);

        backButton = new TextButton("BACK",textButtonStyle);
        backButton.pad(20.0f);
        backButton.padBottom(25.0f);

        heading = new Label("ALPHA", skin,"default");
        scoresAmountLabel = new Label("SCORES: ",skin,"style36");
        spermAmountLabel = new Label("SPERM: ",skin,"style36");

        scores = new Label(scoreAmount,skin,"style36");
        sperm = new Label(spermAmount,skin,"style36");
    }

    @Override
    public void show() {
        table.clear();
        table.setFillParent(true);
        table.top();
        table.add(heading).colspan(2).expandX().row();
        table.add(scrollPane);
        table.add(girl_image).row();
        table.add(scoresAmountLabel).right();
        table.add(scores).left().row();
        table.add(spermAmountLabel).right();
        table.add(sperm).left().row();
        table.add(backButton).left().pad(20.0f).bottom();
        table.add(playButton).right().pad(20.0f).bottom();
        //table.debug();
        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);


        backButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(new MenuScreen(screenManager));
                super.touchUp(event, x, y, pointer, button);
            }
        });

        playButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen = new GameScreen(screenManager, level);
                screenManager.setCurrentScreen(gameScreen);

                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();

        switch (girlsnames.getSelectedIndex()){
            case 0:
                if (girlStatus[0] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl001o"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 0;
                break;
            case 1:
                if (girlStatus[1] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl002o"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 1;
                break;
            case 2:
                if (girlStatus[2] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl003o"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 2;
                break;
            case 3:
                if (girlStatus[3] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl004o"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 3;
                break;
            case 4:
                if (girlStatus[4] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl005o"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 4;
                break;
            case 5:
                if (girlStatus[5] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 5;
                break;
            case 6:
                if (girlStatus[6] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 6;
                break;
            case 7:
                if (girlStatus[7] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 7;
                break;
            case 8:
                if (girlStatus[8] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 8;
                break;
            case 9:
                if (girlStatus[9] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 9;
                break;
            case 10:
                if (girlStatus[10] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 10;
                break;
            case 11:
                if (girlStatus[11] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 11;
                break;
            case 12:
                if (girlStatus[12] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 12;
                break;
            case 13:
                if (girlStatus[13] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 13;
                break;
            case 14:
                if (girlStatus[14] == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(true);
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 14;
                break;
        }
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
