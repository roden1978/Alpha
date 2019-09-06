package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ro|)e|\| on 07.01.2015.
 */
public class LevelScreen extends ObjectScreen {

    private String NAME;
    private float lifeTime;
    private float time;
    private Stage stage;
    private Table table;
    private Label heading;
    private Skin skin, button_skin,girlsFaceSkin,girlsFaceSkinPart2,girlsFaceSkinPart3;
    private TextureAtlas textureAtlas, buttonAtlas, girlsFaceAtlas, girlFacesPart2Atlas, girlFacesPart3Atlas;
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
    private Label scores, sperm, scoresAmountLabel, spermAmountLabel, currentGirl, space;
    private String scoreAmount, spermAmount;
    //private int[] girlStatus;
    private ObjectScreen gameScreen;
    private int level;
    //Члены класса для загрузки и выгрузки параметров уровней
    private String[] levels;
    private String[] levelString;
    private String[] param;
    private Array<Integer> levelNumber;
    private Array<Integer> score;
    private Array<Integer> sperms;
    private Array<Integer> complete;
    private Array<Integer> available;
    private String line;
    //private int currentSelectedName;


    public LevelScreen(ScreenManager screenManager){
        NAME = "LevelScreen";
        lifeTime = 0.0f;
        time = 0.0f;
        level = 0;
        this.screenManager = screenManager;
        scoreAmount = "";
        spermAmount = "";
        //currentSelectedName = screenManager.getCurrentSelectedGirl();

        //girlStatus = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0};

        //Инициализируем массивы параметров уровней
        levelNumber =new Array<Integer>();
        score = new Array<Integer>();
        sperms = new Array<Integer>();
        complete = new Array<Integer>();
        available = new Array<Integer>();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas(Gdx.files.internal("pane/scroll_ui.pack"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/buttons.pack"));
        girlsFaceAtlas = new TextureAtlas(Gdx.files.internal("girls/girls.pack")); ///first part girl faces
        girlFacesPart2Atlas = new TextureAtlas(Gdx.files.internal("girls/gfpart02.pack"));
        girlFacesPart3Atlas = new TextureAtlas(Gdx.files.internal("girls/gfpart03.pack"));
        names = new Array<String>(new String[]{"YULI","ANNA","LILA","LIZA","CAMERON","ALICE","MEGAN",
                "AMELI","NATASHA","ELENA","SASHA","KATRINA","KYLIE","LANA","MIA"});
        skin = new Skin(Gdx.files.internal("ui/ui_settings.json"),textureAtlas);
        button_skin = new Skin(buttonAtlas);
        girlsFaceSkin = new Skin(girlsFaceAtlas);
        girlsFaceSkinPart2 = new Skin(girlFacesPart2Atlas);
        girlsFaceSkinPart3 = new Skin(girlFacesPart3Atlas);
        table = new Table();
        girl_image = new Image(girlsFaceSkin.getDrawable("girl001o"));
        backgr = new Texture(Gdx.files.internal("ui/menu_back.png"));
        background = new Image(backgr);


        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("JFRocSol_rus.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters="АБВГДЕЁЖЗИКЛМНОПРСТУФХЦШЩЬЫЪЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ";
        bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        freeTypeFontGenerator.dispose();

        girlsnames = new List(skin);
        girlsnames.setItems(names);
        girlsnames.setSelectedIndex(screenManager.getCurrentSelectedGirl());

        scrollPane = new ScrollPane(girlsnames,skin);
        //scrollPane.setScrollY(40 * girlsnames.getSelectedIndex());
        //scrollPane.setScrollbarsVisible(true);
        //scrollPane.scrollTo(0.0f, 40 * girlsnames.getSelectedIndex(), 0.0f, 0.0f);


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
        currentGirl = new Label("",skin,"style56");
        space = new Label("",skin,"style56");

        readLevelParams();
        if (!screenManager.getScreenMusic().isPlaying() && screenManager.getOnoff())
            screenManager.getScreenMusic().play();
    }

    @Override
    public void show() {
        table.clear();
        table.setFillParent(true);
        table.top();
        table.add(heading).colspan(2).expandX().row();
        table.add(scrollPane);
        table.add(girl_image).row();
        table.add(space);
        table.add(currentGirl).center().row();
        table.add(scoresAmountLabel).right();
        table.add(scores).left().row();
        table.add(spermAmountLabel).right();
        table.add(sperm).left().row();
        table.add(backButton).left().pad(20.0f).bottom();
        table.add(playButton).right().pad(20.0f).bottom();
        background.setFillParent(true);
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

        playButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (screenManager.getOnoff()){
                    screenManager.getButtonClickSound().play();
                    screenManager.getLevelBeginSound().play();
                }
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
                if (available.get(0) == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl001o"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 0;
                setScoreSperm(level);
                break;
            case 1:
                if (available.get(1) == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl002o"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 1;
                setScoreSperm(level);
                break;
            case 2:
                if (available.get(2) == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl003o"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 2;
                setScoreSperm(level);
                break;
            case 3:
                if (available.get(3) == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl004o"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 3;
                setScoreSperm(level);
                break;
            case 4:
                if (available.get(4) == 1){
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl005o"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 4;
                setScoreSperm(level);
                break;
            case 5:
                if (available.get(5) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart2.getDrawable("g006"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 5;
                setScoreSperm(level);
                break;
            case 6:
                if (available.get(6) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart2.getDrawable("g007"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 6;
                setScoreSperm(level);
                break;
            case 7:
                if (available.get(7) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart2.getDrawable("g008"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 7;
                setScoreSperm(level);
                break;
            case 8:
                if (available.get(8) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart2.getDrawable("g009"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 8;
                setScoreSperm(level);
                break;
            case 9:
                if (available.get(9) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart2.getDrawable("g010"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 9;
                setScoreSperm(level);
                break;
            case 10:
                if (available.get(10) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart3.getDrawable("sasha"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 10;
                setScoreSperm(level);
                break;
            case 11:
                if (available.get(11) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart3.getDrawable("katrina"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 11;
                setScoreSperm(level);
                break;
            case 12:
                if (available.get(12) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart3.getDrawable("kylie"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 12;
                setScoreSperm(level);
                break;
            case 13:
                if (available.get(13) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart3.getDrawable("lana"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 13;
                setScoreSperm(level);
                break;
            case 14:
                if (available.get(14) == 1){
                    girl_image.setDrawable(girlsFaceSkinPart3.getDrawable("mia"));
                    playButton.setVisible(true);
                    currentGirl.setText(names.get(girlsnames.getSelectedIndex()));
                    screenManager.setCurrentSelectedGirl(girlsnames.getSelectedIndex());
                }else {
                    girl_image.setDrawable(girlsFaceSkin.getDrawable("girl_close"));
                    playButton.setVisible(false);
                }
                level = 14;
                setScoreSperm(level);
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

   //Считываем параметры уровней из файла
    private void readLevelParams() {
     /*   FileHandle handle = Gdx.files.internal("levelparam.txt");
        line = handle.readString();*/
       /* levels = line.split("#");
        for (int i = 0; i < levels.length; i++) {
            levelString = levels[i].split(";");
            //System.out.println(levels[i]);
            levelNumber.add(Integer.valueOf(levelString[0].trim()));
            score.add(Integer.valueOf(levelString[1].trim()));
            sperms.add(Integer.valueOf(levelString[2].trim()));
            complete.add(Integer.valueOf(levelString[3].trim()));
            available.add(Integer.valueOf(levelString[4].trim()));
            //System.out.println(levelString[0]+" "+levelString[1]+" "+levelString[2]+" "+levelString[3]+" "+levelString[4]);
        }*/
       // System.out.println(line +" "+levels[0]+" "+levels.length);

        Preferences levelPref = Gdx.app.getPreferences("levelparam");
        for (int i = 0; i < 15; i ++){
            levelNumber.add(levelPref.getInteger("levelNumber_"  + i));
            score.add(levelPref.getInteger("score_" + i));
            sperms.add(levelPref.getInteger("sperm_" + i));
            complete.add(levelPref.getInteger("complete_" + i));
            available.add(levelPref.getInteger("available_" + i));
        }
        available.set(0, 1);
        //levelPref.flush();
    }
    //Обновление счетчиков очков и спрм на экране выбора уровня
    private void setScoreSperm(Integer i){
        scores.setText(String.valueOf(score.get(i)));
        sperm.setText(String.valueOf(sperms.get(i)));
    }
}
