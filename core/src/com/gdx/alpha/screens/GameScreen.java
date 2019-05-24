package com.gdx.alpha.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.alpha.actions.Throw;
import com.gdx.alpha.entitys.Ovum;
import com.gdx.alpha.entitys.Player;
import com.gdx.alpha.entitys.Sprinkle;
import com.gdx.alpha.entitys.VirusA;
import com.gdx.alpha.game.GameDriver;
import com.gdx.alpha.game.GameManager;

/**
 * Created by admin on 19.01.2015.
 */
public class GameScreen extends ObjectScreen implements InputProcessor{

    private String NAME;
    private float lifeTime;
    private float time;
    private int level;
    private ScreenManager screenManager;
    private GameDriver gameDriver;
    private OrthographicCamera camera;
    private Viewport viewport;
    private static final int READY_STATE = 0;
    private static final int RUNNING_STATE = 1;
    private static final int PAUSE_STATE = 2;
    private static final int LEVEL_END_STATE = 3;
    private static final int END_STATE = 4;

    private float WIDTH;
    private float HEIGHT;

    private int state;
    private float delta;

    private Stage gameStage; //, uiStage
    private Player player = null;
    private Vector2 playerPosition;

    private BitmapFont font;
    private SpriteBatch spriteBatch;

    private Skin uiSkin;

//опрос устройств ввода
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK){
            if (state == PAUSE_STATE || state == END_STATE || state == LEVEL_END_STATE)
                screenManager.setCurrentScreen(new LevelScreen(screenManager));

            state = PAUSE_STATE;
        }
        if (keycode == Input.Keys.P)
            state = PAUSE_STATE;
            //screenManager.setCurrentScreen(new LevelScreen(screenManager));
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK)
        //    screenManager.setCurrentScreen(new LevelScreen(screenManager));
        //System.out.println(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("X: "+screenX);
        System.out.println("Y: " + screenY);
        System.out.println("State in: " + state);
        if (state == PAUSE_STATE)
            state = RUNNING_STATE;
        gameDriver.gameManager.player.setThrowing(true);
        gameDriver.gameManager.throwWeapon.setThrowing(true);
        System.out.println("X >: " + (int)gameDriver.gameManager.pauseImage.getImageX() +
                " X <: " + (int)(gameStage.getWidth() - (gameStage.getWidth() - gameDriver.gameManager.pauseImage.getImageWidth()))+
                " Y <: " + (int)(gameStage.getHeight() - (gameStage.getHeight() - gameDriver.gameManager.pauseImage.getImageHeight())) +
                " Y >: " + 0);
        if (screenX > (int)gameDriver.gameManager.pauseImage.getImageX() &&
                screenX < (int)(gameStage.getWidth() - (gameStage.getWidth() - gameDriver.gameManager.pauseImage.getImageWidth()))&&
                screenY < (int)(gameStage.getHeight() - (gameStage.getHeight() - gameDriver.gameManager.pauseImage.getImageHeight())) &&
                screenY > 0.0f){
//(int) (gameStage.getHeight() - gameDriver.gameManager.pauseImage.getImageY() - gameDriver.gameManager.pauseImage.getImageHeight())
            state = PAUSE_STATE;
            System.out.println("State out: " + state);
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        gameDriver.gameManager.player.setThrowing(false);
        gameDriver.gameManager.throwWeapon.setThrowing(false);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        gameDriver.gameManager.player.setThrowing(true);
        gameDriver.gameManager.throwWeapon.setThrowing(true);

        if(screenX < gameStage.getWidth() &&
                screenX > gameDriver.gameManager.player.getSpriteRegionWidth() &&
                screenY  > gameDriver.gameManager.player.getSpriteRegionHeight() / 2 &&
                screenY < gameStage.getHeight() - gameDriver.gameManager.player.getSpriteRegionHeight() / 2){

            playerPosition.x = screenX - gameDriver.gameManager.player.getSpriteRegionWidth()*3 - gameDriver.gameManager.player.getSpriteRegionWidth() / 10;
            playerPosition.y = (gameStage.getHeight() - screenY) - gameDriver.gameManager.player.getSpriteRegionHeight() / 2;
            gameDriver.gameManager.player.setPosition(playerPosition);
            gameDriver.gameManager.throwWeapon.updatePosition(playerPosition);
        }
        //System.out.println("X: " + player.getPosition().x + " Y: " + player.getPosition().y);
        //System.out.println("X: " + screenX + " Y: " + screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //player.setPosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
// конструктор
    public GameScreen(ScreenManager screenManager, int level){
        NAME = "GameScreen";
        lifeTime = 0.0f;
        time = 0.0f;
        delta = 0.0f;
        playerPosition = new Vector2(0,0);
        state = READY_STATE;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        this.screenManager = screenManager;
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        //weaponType = 0;
        this.level = level;
        font = new BitmapFont(Gdx.files.internal("font/stonefont.fnt"));
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        //создаем игровую сцену
        gameStage = new Stage();
        //создаем сцену со строкой состояния
        //uiStage = new Stage();

    }

    @Override
    public void show() {
        //super.show();
        //создаем класс управления игрой
        gameDriver = new GameDriver(this, level);
        gameDriver.addGeneralActorsToScene();
}

    public void update(float delta){
        if (delta > 0.1f)
            delta = 0.1f;

        switch (state){
            case READY_STATE:
                //готовность игры к запуску
                updateReady();
                break;
            case RUNNING_STATE:
                //процес игры
                updateRunning(delta);
                break;
            case PAUSE_STATE:
                //пауза
                updatePause();
                break;
            case LEVEL_END_STATE:
                //уровень пройден
                updateLevelEnd();
                break;
            case END_STATE:
                //игра закончена
                updateGameOver();
                break;
        }
    }

    //Обработка состояний игры
    //состояние готовноти
    private void updateReady(){
        if (Gdx.input.justTouched()) {
            state = RUNNING_STATE;
        }
    }
    //состояние игрового процесса
    private void updateRunning(float delta){

        //обновляем состояние игровой сцены
        gameStage.act(delta);
        //обновляем состояние строки состояний
        //uiStage.act(delta);
        gameDriver.gameDrive(delta);

    }
    //состояние паузы
    private void updatePause(){
        if (Gdx.input.justTouched() && state == RUNNING_STATE) {
            //state = RUNNING_STATE;
            state = PAUSE_STATE;
        }
    }
    //состояние завершения уровня
    private void updateLevelEnd(){
        if (Gdx.input.justTouched()){
            state = LEVEL_END_STATE;
        }
    }
    //состояние конец игры
    private void updateGameOver(){
        if (Gdx.input.justTouched()) {
            screenManager.setCurrentScreen(new LevelScreen(screenManager));
        }
    }

    public void drawStates(){
        switch (state){
            case READY_STATE:
                //готовность игры к запуску
                presentReady();
                break;
            case RUNNING_STATE:
                //процес игры
                presentRunning();
                break;
            case PAUSE_STATE:
                //пауза
                presentPaused();
                break;
            case LEVEL_END_STATE:
                //уровень пройден
                presentLevelEnd();
                break;
            case END_STATE:
                //игра закончена
                presentGameOver();
                break;
        }
    }
    //отрисова состояний игры
    //отрисовка состояния готовности
    private void presentReady(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
            font.draw(spriteBatch,"TAP TO PLAY",gameStage.getWidth()/2 - font.getBounds("TAP TO PLAY").width/2,
                    gameStage.getHeight()/2 + font.getBounds("TAP TO PLAY").height/2);
        spriteBatch.end();
    }
    //отрисовка состояния игры
    private void presentRunning(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        //uiStage.getBatch().setProjectionMatrix(camera.combined);
        gameStage.getBatch().setProjectionMatrix(camera.combined);
        //gameStage.setDebugAll(true);
        gameStage.draw();
        //uiStage.draw();
    }
    //отрисовка состояния паузы
    private void presentPaused(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
        font.draw(spriteBatch,"PAUSE",gameStage.getWidth()/2 - font.getBounds("PAUSE").width/2,
                gameStage.getHeight()/2 + font.getBounds("PAUSE").height/2);
        font.draw(spriteBatch,"TAP TO CONTINUE",gameStage.getWidth()/2 - font.getBounds("TAP TO CONTINUE").width/2,
                gameStage.getHeight()/2 - font.getBounds("TAP TO CONTINUE").height*2);
        font.draw(spriteBatch,"OR BACK (ESC) TO EXIT",gameStage.getWidth()/2 - font.getBounds("OR BACK (ESC) TO EXIT").width/2,
                gameStage.getHeight()/2 - font.getBounds("OR BACK TO EXIT").height*3);
        spriteBatch.end();
    }
    //отрисовка состояния окончания уровная
    private void presentLevelEnd(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
        font.draw(spriteBatch,"LEVEL COMPLETE",gameStage.getWidth()/2 - font.getBounds("LEVEL COMPLETE").width/2,
                gameStage.getHeight()/2 + font.getBounds("LEVEL COMPLETE").height/2);
        spriteBatch.end();
    }
    //отрисовка окончания игры
    private void presentGameOver(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
        font.draw(spriteBatch,"GAME OVER",gameStage.getWidth()/2 - font.getBounds("GAME OVER").width/2,
                gameStage.getHeight()/2 + font.getBounds("GAME OVER").height/2);
        spriteBatch.end();
    }


    @Override
    public void render(float delta) {
        //super.render(delta);
        //Gdx.gl.glClearColor(0, 0, 0, 1); //Gdx.gl.glClearColor(1, 0.784f, 0.784f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        update(delta);
        drawStates();

    }

    @Override
    public void resize(int width, int height) {
       // super.resize(width, height);
        camera.setToOrtho(true,WIDTH,HEIGHT );
        gameStage.getCamera().update();
        //uiStage.getCamera().update();
    }

    @Override
    public void pause() {
        super.pause();
        if (state == RUNNING_STATE)
            state = PAUSE_STATE;
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
        font.dispose();
        gameStage.dispose();
        super.dispose();
       // uiStage.dispose();
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

    public Stage getGameStage(){
        return gameStage;
    }

    public void setGameState(int state){
        this.state = state;
    }
}
