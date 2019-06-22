package com.gdx.alpha.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.gdx.alpha.actions.Throw;
import com.gdx.alpha.effects.HitParticleEffect;
import com.gdx.alpha.entitys.BacteriasColony;
import com.gdx.alpha.entitys.Bacteriophage;
import com.gdx.alpha.entitys.Bacterium;
import com.gdx.alpha.entitys.Microbe;
import com.gdx.alpha.entitys.Ovum;
import com.gdx.alpha.entitys.Player;
import com.gdx.alpha.entitys.ScoreCloud;
import com.gdx.alpha.entitys.Sperm;
import com.gdx.alpha.entitys.Sprinkle;
import com.gdx.alpha.entitys.VirusBullet;
import com.gdx.alpha.entitys.Weapon;

import java.io.IOException;

/**
 * Created by Ro|)e|\| on 21.01.15.
 */
public class GameManager {

    private int level;
    private String[] levels;
    private String[] levelString;
    private String[] param;
    public Array<String> typeEnemie;
    public Array<Float> posX;
    public Array<Float> posY;
    public Array<Integer> weight;
    public Array<Float> speed;
    public Array<Float> time;

    public TextureAtlas virusAtlas;
    public TextureAtlas spermAtlas;
    public TextureAtlas virusBulletAtlas;
    public TextureAtlas backgroundAtlas;
    //private TextureAtlas bactiaAtlas;
    //public TextureAtlas axeAtlas;
    public TextureAtlas ovumAtlas;
    public TextureAtlas lifeAtlas;
    //public TextureAtlas lifeCountAtlas;
    public TextureAtlas bacteriophageAtlas;
    public TextureAtlas bacteriophage_weapon_maceAtlas;
    public TextureAtlas bacteriophage_weapon_stoneAtlas;
    private TextureAtlas bacteriophage_weapon_cudgelAtlas;

    private TextureAtlas bacteriumAtlas;

    private TextureAtlas cavemanAtlas;
    private TextureAtlas caveman_newlifeAtlas;
    private TextureAtlas lifeScaleAtlas;
    private TextureAtlas lifeCountAtlas;

    //эффекты частиц
    public ParticleEffect blow;
    public ParticleEffect blow_small;
    public ParticleEffect hit;
    public ParticleEffect ovum_effect;
    //массив части hit (столкновение оружия с врагом)
    public Array<HitParticleEffect> hitParticleEffectArray;

    //шрифт для облака очков
    public BitmapFont fontScoreCloudRed;
    public BitmapFont fontScoreCloudGreen;
    // массив облаков очков
    public Array<ScoreCloud> scoreCloudArray;

    //массивы объектов взаимодействия при столкновениях
    //массив врагов
    private Array<Microbe> enemies;
    //массив пуль врагов
    public Array<VirusBullet> bullets;
    //массив брошеных топоров игрока
    public Array<Weapon> weapons;
    //массив сперматозоидов
    public Array<Sperm> sperms;
    //массив бактериофагов
    private Array<Bacteriophage> bacteriophages;
    //объект "Яйцеклетка"
    public Ovum ovum;
    //объект "Игрок"
    public Player player;
    //объект "Капля"
    private Sprinkle sprinkle;
    //Объект "Колония бактерий"
    private Array<BacteriasColony> colonys;
    //действие бросок оружия
    private Throw throwWeapon;
    //бактериофаги
    public Bacteriophage bacteriophage;
    //движущийся фон игры
    public ParallaxBackgroundLayer00 backgroundLayer00;
    public ParallaxBackgroundLayer01 backgroundLayer01;

    private String line;
    //Признак конца уровня
    private Boolean level_end;
    //Время задежки для отработки эффектов
    private float delta_time_particle_effect;
    //Запущен ли ovum_effect
    private Boolean ovum_effectStart;

    //элементы интерфейса строки состояния игры
    private TextureAtlas uiAtlas;
    private TextureAtlas textureAtlas;
    private Label scoreCountLabel;//starImage;
    private Label spermCountLabel;//spermImage;
    //private Label lifeCountLabel;
    public Image pauseImage;
    private Label scoresLabel;
    private Label spermLabel;
    //private Label lifeLabel;
    private Skin textSkin;
    private Integer scoresAmount;
    private Integer spermAmount;

    public Table uiTable;

   /* //public Lifes getLifes() {
        return lifes;
    }*/

   // private Lifes lifes;

    GameManager(int level) {
        this.level = level;
        typeEnemie = new Array<String>();
        posX = new Array<Float>();
        posY = new Array<Float>();
        weight = new Array<Integer>();
        speed = new Array<Float>();
        time = new Array<Float>();

        enemies = new Array<Microbe>(50);
        bullets = new Array<VirusBullet>(50);
        weapons = new Array<Weapon>(30);
        sperms = new Array<Sperm>(50);
        bacteriophages = new Array<Bacteriophage>(10);
        colonys = new Array<BacteriasColony>(10);

        hitParticleEffectArray = new Array<HitParticleEffect>(10);
        scoreCloudArray = new Array<ScoreCloud>(10);
        delta_time_particle_effect = 3.0f;
        ovum_effectStart = false;

        line = "";

        level_end = false;

        try {
            loadLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GameManager create");
    }

    void loadLevel() throws IOException {
        FileHandle handle = Gdx.files.internal("level.txt");
        line = handle.readString();
        levels = line.split("#");
        for (int i = 0; i < levels.length; i++) {
            levelString = levels[i].split(":");
            if (Integer.valueOf(levelString[0].trim()) == level) {
                param = levelString[1].split(";");
                typeEnemie.add(String.valueOf(param[0]));
                posX.add(Float.valueOf(param[1]));
                posY.add(Float.valueOf(param[2]));
                weight.add(Integer.valueOf(param[3]));
                speed.add(Float.valueOf(param[4]));
                time.add(Float.valueOf(param[5]));
            }
        }
    }

    void loadSources() {
        virusAtlas = new TextureAtlas(Gdx.files.internal("viruses/viruses.pack"));
        spermAtlas = new TextureAtlas(Gdx.files.internal("sperm/sperm.pack"));
        virusBulletAtlas = new TextureAtlas(Gdx.files.internal("bullets/bullets.pack"));
        //axeAtlas = new TextureAtlas(Gdx.files.internal("axe/axe.pack"));
        ovumAtlas = new TextureAtlas(Gdx.files.internal("ovum/ovum.pack"));
        backgroundAtlas = new TextureAtlas(Gdx.files.internal("background/background.pack"));
        uiAtlas = new TextureAtlas(Gdx.files.internal("uiGame/uiGameSrc.pack"));
        textureAtlas = new TextureAtlas(Gdx.files.internal("pane/scroll_ui.pack"));
        textSkin = new Skin(Gdx.files.internal("ui/ui_settings.json"), textureAtlas);
        blow = new ParticleEffect();
        blow.load(Gdx.files.internal("effects/blow"), Gdx.files.internal("effects"));
        blow_small = new ParticleEffect();
        blow_small.load(Gdx.files.internal("effects/blow_small"), Gdx.files.internal("effects"));
        hit = new ParticleEffect();
        hit.load(Gdx.files.internal("effects/hit"), Gdx.files.internal("effects"));
        ovum_effect = new ParticleEffect();
        ovum_effect.load(Gdx.files.internal("effects/ovum"), Gdx.files.internal("effects"));
        fontScoreCloudRed = new BitmapFont(Gdx.files.internal("font/font_red_32.fnt"));
        fontScoreCloudGreen = new BitmapFont(Gdx.files.internal("font/font_green_32.fnt"));
        lifeAtlas = new TextureAtlas(Gdx.files.internal("uiGame/lifescale.pack"));
        bacteriophageAtlas = new TextureAtlas(Gdx.files.internal("bacteriophage/health.pack"));
        bacteriophage_weapon_maceAtlas = new TextureAtlas(Gdx.files.internal("bacteriophage/mace.pack"));
        bacteriophage_weapon_stoneAtlas = new TextureAtlas(Gdx.files.internal("bacteriophage/stone.pack"));
        bacteriophage_weapon_cudgelAtlas = new TextureAtlas(Gdx.files.internal("bacteriophage/cudgel.pack"));
        cavemanAtlas = new TextureAtlas(Gdx.files.internal("caveman/caveman.pack"));
        caveman_newlifeAtlas = new TextureAtlas(Gdx.files.internal("caveman/newlife.pack"));
        lifeScaleAtlas = new TextureAtlas(Gdx.files.internal("uiGame/lifescale.pack"));
        lifeCountAtlas = new TextureAtlas(Gdx.files.internal("caveman/cm_life.pack"));
        bacteriumAtlas = new TextureAtlas(Gdx.files.internal("bacterias/bacteriums.pack"));
    }

    void buildGeneralPlayers() {
        try {
            player = new Player(new Vector2(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f),
                    cavemanAtlas, lifeScaleAtlas,lifeCountAtlas, caveman_newlifeAtlas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        backgroundLayer00 = new ParallaxBackgroundLayer00(backgroundAtlas);
        backgroundLayer01 = new ParallaxBackgroundLayer01(backgroundAtlas);
        sprinkle = new Sprinkle(spermAtlas);
        ovum = new Ovum(ovumAtlas);
    }

    void buildActions() {
        throwWeapon = new Throw(0, new Vector2(player.getPositionX(), player.getPositionY()));
    }

    public Integer getScoresAmount() {
        return scoresAmount;
    }

    void setScoresAmount(Integer scoresAmount) {
        this.scoresAmount = scoresAmount;
    }

    public Integer getSpermAmount() {
        return spermAmount;
    }

    void setSpermAmount(Integer spermAmount) {
        this.spermAmount = spermAmount;
    }

    void buildUiStateString() {
        scoresAmount = 0;
        spermAmount = 0;
        scoreCountLabel = new Label("SC:", textSkin, "style36");
        spermCountLabel = new Label("SP:", textSkin, "style36");
        //lifeCountLabel = new Label("LIFE:", textSkin, "style36");
        pauseImage = new Image(uiAtlas.findRegion("pause"));
        //pauseImage.setScale(2.0f, 2.0f);
        spermLabel = new Label("000", textSkin, "style36");
        scoresLabel = new Label("000", textSkin, "style36");
        //создаем таблицу со строкой состояния
        uiTable = new Table();
        //заполняем таблицу состояний значениями
        uiTable.clear();
        uiTable.setFillParent(true);
        uiTable.top().left();
        uiTable.add(pauseImage).left().row();
        uiTable.add(scoreCountLabel).right().spaceRight(10.0f);
        uiTable.add(scoresLabel).row();
        uiTable.add(spermCountLabel).right().spaceRight(10.0f);
        uiTable.add(spermLabel).row();
    }

    void updateScoresAmount() {
        scoresLabel.setText(String.valueOf(scoresAmount));
    }

    void updateSpermAmount() {
        spermLabel.setText(String.valueOf(spermAmount));
    }


    void setLevelEnd(Boolean level_end){
        this.level_end = level_end;
    }

    Boolean getLevelEnd(){
        return this.level_end;
    }

    void setDeltaTimeParticleEffect(float delta_time_paricle_effect){
        this.delta_time_particle_effect = delta_time_paricle_effect;
    }
    float getDeltaTimeParticleEffect(){
        return this.delta_time_particle_effect;
    }

    void setOvumEffectStart(Boolean start){
        this.ovum_effectStart = start;
    }

    Boolean getOvumEffectStart(){
        return this.ovum_effectStart;
    }

    Array<Bacteriophage> getBacteriophages() {
        return bacteriophages;
    }

    public Throw getThrowWeapon() {
        return throwWeapon;
    }

    public TextureAtlas getBacteriophage_weapon_cudgelAtlas() {
        return bacteriophage_weapon_cudgelAtlas;
    }

    public Array<Microbe> getEnemies() {
        return enemies;
    }

    public Array<BacteriasColony> getBacteriasColonys() {return colonys;}

    //public Array<BacteriasColony> getBacterias (){return bacterias;}

    public Sprinkle getSprinkle() {return sprinkle;}

    public TextureAtlas getBacteriumAtlas() {
        return bacteriumAtlas;
    }
}//end of class