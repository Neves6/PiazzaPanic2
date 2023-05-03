package com.team3gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team3gdx.game.MainGameClass;
import com.team3gdx.game.entity.Cook;
import com.team3gdx.game.entity.CustomerController;
import com.team3gdx.game.entity.Entity;
import com.team3gdx.game.food.OrderCard;
import com.team3gdx.game.station.StationManager;
import com.team3gdx.game.util.CollisionTile;
import com.team3gdx.game.util.Control;
import com.team3gdx.game.util.Power;
import com.team3gdx.game.util.PowerUnit;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * GameScreen is the main screen of the game. It is where the game is played.
 * It contains the game loop and the game logic.
 *
 * @author Team3Gdx
 * @author Neves6
 **/
public class GameScreen implements Screen {

    public static long score;
    public static long accumulatedScore;
    public static int scenarioLimit;
    public static String gameMode;
    public static String difficulty;
    public static int customersServed;
    public static int CUSTOMER_SPAWNCAP;
    final MainGameClass game;
    final MainScreen ms;

    public static int reputation;
    public static boolean orderJustServed;
    public static Queue<OrderCard> orderCards;
    public static float spawnInterval;
    public static float upperSpawnInterval;
    public static float lowerSpawnInterval;
    public float spawnTime;
    public float targetTime;
    public boolean timerRunning = false;
    Rectangle volSlideBackgr;
    Rectangle volSlide;
    Rectangle musSlideBackgr;
    Rectangle musSlide;
    Rectangle audioBackground;
    Rectangle optionsBackground;
    Texture ESC;
    Texture MENU;
    Texture BACKTOMAINSCREEN;
    Texture TUTORIAL;
    Texture TUTORIALSCREEN;
    Texture RESUME;
    Texture AUDIO;
    Texture audioEdit;
    Texture vControl;
    Texture vButton;
    Texture oneRep;
    Texture twoRep;
    Texture fullRep;
    Texture RECIPEMENU;
    Texture RECIPEMENUICON;
    Texture POWERICON1;
    Texture POWERICON2;
    Texture POWERICON3;

    Texture GAMEOVER;
    Texture FULLSCREEN;
    Texture SAVEDATA;
    Image lowRep;
    static Image medRep;
    static Image maxRep;
    Image mb;
    Button mn;
    Button lm;
    Button go;
    Button rms;
    Button rs;
    Button ad;
    Button st;
    Button ts;
    Button btms;
    Button fs;
    TextButton ss1;
    TextButton ss2;
    TextButton ss3;
    TextButton ls1;
    TextButton ls2;
    TextButton ls3;
    public static CollisionTile[][] CLTiles;
    Viewport uiViewport;
    Viewport worldViewport;
    Stage stage;
    Stage stage2;
    OrthographicCamera uiCamera;
    public static OrthographicCamera worldCamera;

    public enum STATE {
        Pause, Continue, main, audio
    }

    public static STATE state1;
    float v;
    float s;
    int gameResolutionX;
    int gameResolutionY;
    float buttonwidth;
    float buttonheight;
    float xSliderMax;
    float xSliderMin;
    float sliderWidth;

    float audioBackgroundWidth;
    float audioBackgroundHeight;
    float audioBackgroundx;
    float audioBackgroundy;
    long startTime;
    long timeOnStartup;
    long tempTime, tempThenTime;
    public static Control control;
    TiledMapRenderer tiledMapRenderer;
    public static TiledMap map1;
    public static Cook[] cooks;
    public static Cook[] cooks_after;
    public static int currentCookIndex;
    public static Cook cook;
    public static CustomerController cc;
    InputMultiplexer multi;
    StationManager stationManager;
    int lateLoadSlot;
    Preferences slot1, slot2, slot3;

    /**
     * Constructor to initialise game screen;
     *
     * @param game - Main entry point class
     * @param ms   - Title screen class
     * @author Team3Gdx
     * @author Neves6
     */
    public GameScreen(MainGameClass game, MainScreen ms, int lateLoadSlot) {
        accumulatedScore = 0;
        gameMode = "";
        difficulty = "";
        scenarioLimit = 1;
        CUSTOMER_SPAWNCAP = 0;
        customersServed = 0;
        reputation = 3;
        spawnInterval = 5.0f;
        upperSpawnInterval = 30000f;
        lowerSpawnInterval = 1000f;
        spawnTime = 0.0f;
        targetTime = 0.0f;
        orderJustServed = false;
        orderCards = new LinkedList<>();
        this.game = game;
        this.ms = ms;
        this.calculateBoxMaths();
        control = new Control();
        // map = new TmxMapLoader().load("map/art_map/prototype_map.tmx");
        map1 = new TmxMapLoader().load("map/art_map/customertest.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map1);
        constructCollisionData(map1);
        cc = new CustomerController(map1);
        cooks = new Cook[]{new Cook(new Vector2(64 * 5, 64 * 3), 1), new Cook(new Vector2(64 * 5, 64 * 5), 2)};
        currentCookIndex = 0;
        cook = cooks[currentCookIndex];
        stationManager = new StationManager();
        this.lateLoadSlot = lateLoadSlot;
        slot1 = Gdx.app.getPreferences("slot1");
        slot2 = Gdx.app.getPreferences("slot2");
        slot3 = Gdx.app.getPreferences("slot3");
        Power.resetPower();
    }

    /**
     * Add a new chef to the game
     *
     * @author Neves6
     */
    public static void addnewchef() {
        if (score + accumulatedScore >= 500) {
            cooks_after = new Cook[]{new Cook(new Vector2(cooks[0].getX(), cooks[0].getY()), 1), new Cook(new Vector2(cooks[1].getX(), cooks[1].getY()), 2), new Cook(new Vector2(64 * 5, 64), 3)};
            cooks = cooks_after;
            cook.locked = false;
            currentCookIndex = 2;
            cook = cooks[currentCookIndex];
            subScore(500);
        }
    }


    /**
     * Things that should be done while the game screen is shown
     *
     * @author Team3Gdx
     * @author Neves6
     */
    public void show() {
        // Start Frame Timer
        startTime = System.currentTimeMillis();
        timeOnStartup = startTime;
        tempThenTime = startTime;
        // Set Positions of Sliders
        float currentMusicVolumeSliderX = (MainGameClass.musicVolumeScale * sliderWidth) + xSliderMin;
        float currentGameVolumeSliderX = (MainGameClass.gameVolumeScale * sliderWidth) + xSliderMin;
        musSlide.setPosition(currentMusicVolumeSliderX, audioBackgroundy + 4 * audioBackgroundHeight / 6
                + musSlideBackgr.getHeight() / 2 - musSlide.getHeight() / 2);
        volSlide.setPosition(currentGameVolumeSliderX, audioBackgroundy + audioBackgroundHeight / 6
                + volSlideBackgr.getHeight() / 2 - volSlide.getHeight() / 2);
        // Inherit Textures from MainScreen
        vButton = ms.vButton;
        vControl = ms.vControl;
        // Start Cameras
        uiCamera = new OrthographicCamera();
        worldCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, gameResolutionX, gameResolutionY);
        worldCamera.setToOrtho(false, gameResolutionX, gameResolutionY);
        // Set Initial State
        state1 = STATE.Continue;
        // Start Viewports
        worldViewport = new FitViewport(gameResolutionX, gameResolutionY, worldCamera);
        uiViewport = new FitViewport(gameResolutionX, gameResolutionY, uiCamera);
        // Start Stages
        stage = new Stage(uiViewport);
        stage2 = new Stage(uiViewport);
        // Create InputMultiplexer
        multi = new InputMultiplexer(stage, control);
        // Load Textures
        MENU = new Texture(Gdx.files.internal("uielements/settings.png"));
        ESC = new Texture(Gdx.files.internal("uielements/background.png"));
        BACKTOMAINSCREEN = new Texture(Gdx.files.internal("uielements/exitmenu.png"));
        TUTORIAL = new Texture(Gdx.files.internal("uielements/homeScreenTutorial.png"));
        TUTORIALSCREEN = new Texture(Gdx.files.internal("uielements/PP2TutorialPage.jpg"));
        RESUME = new Texture(Gdx.files.internal("uielements/resume.png"));
        AUDIO = new Texture(Gdx.files.internal("uielements/audio.png"));
        audioEdit = new Texture(Gdx.files.internal("uielements/background.png"));
        oneRep = new Texture(Gdx.files.internal("uielements/OneRep.png"));
        twoRep = new Texture(Gdx.files.internal("uielements/TwoRep.png"));
        fullRep = new Texture(Gdx.files.internal("uielements/fullRep.png"));
        RECIPEMENU = new Texture(Gdx.files.internal("uielements/recipeMenu.png"));
        RECIPEMENUICON = new Texture(Gdx.files.internal("uielements/recipeMenuIcon.png"));
        GAMEOVER = new Texture(Gdx.files.internal("uielements/GameOver.png"));
        FULLSCREEN = new Texture(Gdx.files.internal("uielements/FullscreenButton.png"));
        SAVEDATA = new Texture(Gdx.files.internal("uielements/SaveDataButton.png"));
        // Create Buttons and Images

        mn = new Button(new TextureRegionDrawable(MENU));
        lm = new Button(new TextureRegionDrawable(RECIPEMENUICON));
        go = new Button(new TextureRegionDrawable(GAMEOVER));
        rms = new Button(new TextureRegionDrawable(RECIPEMENU));
        ad = new Button(new TextureRegionDrawable(AUDIO));
        rs = new Button(new TextureRegionDrawable(RESUME));
        st = new Button(new TextureRegionDrawable(TUTORIAL));
        ts = new Button(new TextureRegionDrawable(TUTORIALSCREEN));
        fs = new Button(new TextureRegionDrawable(FULLSCREEN));
        TextButton.TextButtonStyle tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = game.font;
        tbStyle.up = new TextureRegionDrawable(SAVEDATA);
        ss1 = new TextButton("Save Slot 1", tbStyle);
        ss2 = new TextButton("Save Slot 2", tbStyle);
        ss3 = new TextButton("Save Slot 3", tbStyle);
        if ((int) slot1.getLong("score") == 0 && slot1.getInteger("reputation") == 0)
            ls1 = new TextButton("Load Slot 1\nEMPTY", tbStyle);
        else
            ls1 = new TextButton("Load Slot 1\n" + ((int) slot1.getLong("score")) + " Score / " + slot1.getInteger("reputation") + " Rep", tbStyle);
        if ((int) slot2.getLong("score") == 0 && slot2.getInteger("reputation") == 0)
            ls2 = new TextButton("Load Slot 2\nEMPTY", tbStyle);
        else
            ls2 = new TextButton("Load Slot 2\n" + ((int) slot2.getLong("score")) + " Score / " + slot2.getInteger("reputation") + " Rep", tbStyle);
        if ((int) slot3.getLong("score") == 0 && slot3.getInteger("reputation") == 0)
            ls3 = new TextButton("Load Slot 3\nEMPTY", tbStyle);
        else
            ls3 = new TextButton("Load Slot 3\n" + ((int) slot3.getLong("score")) + " Score / " + slot3.getInteger("reputation") + " Rep", tbStyle);
        btms = new Button(new TextureRegionDrawable(BACKTOMAINSCREEN));
        lowRep = new Image(new TextureRegionDrawable(oneRep));
        medRep = new Image(new TextureRegionDrawable(twoRep));
        maxRep = new Image(new TextureRegionDrawable(fullRep));
        mb = new Image(new TextureRegionDrawable(ESC));


        // Position and Scale Buttons
        mn.setPosition(gameResolutionX / 40.0f, 18 * gameResolutionY / 20.0f);
        mn.setSize(buttonwidth, buttonheight);
        mb.setPosition(optionsBackground.getX(), optionsBackground.getY() + 15);
        mb.setSize(optionsBackground.getWidth(), optionsBackground.getHeight() - 30);
        lm.setPosition(mn.getX(), mn.getY() - 4 * buttonheight);
        lm.setSize(7 * (buttonheight / 3) + 30, 7 * (buttonheight / 2));
        rms.setPosition(gameResolutionX / 6, 10);
        rms.setSize(3 * (gameResolutionX - optionsBackground.getHeight()) / 4, gameResolutionY - optionsBackground.getHeight() - 30);
        rms.setVisible(false);
        rs.setPosition(gameResolutionX / 40.0f + 1, 18 * gameResolutionY / 20.0f);
        rs.setSize(buttonwidth, buttonheight);
        ad.setPosition(rs.getX() + rs.getWidth() + 2 * (gameResolutionX / 40.0f - gameResolutionX / 50.0f), rs.getY());
        ad.setSize(buttonwidth, buttonheight);
        fs.setPosition(ad.getX() + ad.getWidth() + 2 * (gameResolutionX / 40.0f - gameResolutionX / 50.0f), ad.getY());
        fs.setSize(buttonwidth, buttonheight);
        st.setPosition(fs.getX() + fs.getWidth() + 2 * (gameResolutionX / 40.0f - gameResolutionX / 50.0f),
                fs.getY());
        st.setSize(buttonwidth, buttonheight);

        ts.setFillParent(true);
        ts.setVisible(false);

        lowRep.setPosition(gameResolutionX / 2.0f - 40, 19 * gameResolutionY / 20.0f - 110);
        lowRep.setSize(300, 80);
        medRep.setPosition(lowRep.getX(), lowRep.getY());
        medRep.setSize(lowRep.getWidth(), lowRep.getHeight());
        maxRep.setPosition(lowRep.getX(), lowRep.getY());
        maxRep.setSize(lowRep.getWidth(), lowRep.getHeight());
        ss1.setPosition(gameResolutionX / 40.0f, 9 * gameResolutionY / 20.0f);
        ss1.setSize(2 * buttonwidth, 3 * buttonheight);
        ss2.setPosition(ss1.getX(), ss1.getY() - ss1.getHeight());
        ss2.setSize(ss1.getWidth(), ss1.getHeight());
        ss3.setPosition(ss2.getX(), ss2.getY() - ss2.getHeight());
        ss3.setSize(ss1.getWidth(), ss1.getHeight());
        ls1.setPosition(ss1.getX() + ss1.getWidth(), ss1.getY());
        ls1.setSize(ss1.getWidth(), ss1.getHeight());
        ls2.setPosition(ss2.getX() + ss2.getWidth(), ss2.getY());
        ls2.setSize(ss1.getWidth(), ss1.getHeight());
        ls3.setPosition(ss3.getX() + ss3.getWidth(), ss3.getY());
        ls3.setSize(ss1.getWidth(), ss1.getHeight());
        go.setPosition(lowRep.getX() - lowRep.getX() / 6, lowRep.getY() / 5);
        go.setSize(2 * lowRep.getWidth(), 9 * lowRep.getHeight());
        go.setVisible(false);
        btms.setPosition(st.getX() + st.getWidth() + 2 * (gameResolutionX / 40.0f - gameResolutionX / 50.0f), st.getY());
        btms.setSize(buttonwidth, buttonheight);
        // Add Listeners to Buttons
        mn.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state1 = STATE.Pause;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        lm.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state1 = STATE.Pause;
                super.touchUp(event, x, y, pointer, button);
                for (Actor actor : stage2.getActors()) {
                    if (actor != rms) {
                        actor.setVisible(false);
                    }
                }
                lm.setVisible(false);
                rms.setVisible(true);
            }
        });
        rms.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state1 = STATE.Continue;
                super.touchUp(event, x, y, pointer, button);
                for (Actor actor : stage2.getActors()) {
                    if (actor != go) {
                        actor.setVisible(true);
                    }
                }
                lm.setVisible(true);
                rms.setVisible(false);
            }
        });
        go.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.gameMusic.dispose();
                game.resetGameScreen();
                if (gameMode == "scenario") {
                    game.resetGameScreen();
                    game.setScreen(game.getMainScreen());
                } else {
                    game.getLeaderBoardScreen().addLeaderBoardData("PLAYER1",
                            (int) Math.floor((startTime - timeOnStartup) / 1000f));
                    game.resetGameScreen();
                    game.setScreen(game.getLeaderBoardScreen());
                }
            }
        });
        rs.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state1 = STATE.Continue;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ad.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state1 = STATE.audio;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        st.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ts.setVisible(true);
            }
        });
        ts.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ts.setVisible(false);
            }
        });
        btms.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state1 = STATE.main;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        //fullscreen button handler
        fs.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(gameResolutionX, gameResolutionY);
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
                super.touchUp(event, x, y, pointer, button);
            }

        });
        //Save and Load button handlers
        ss1.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                saveGame(1);
                state1 = STATE.Continue;
                slot1 = Gdx.app.getPreferences("slot1");
                ls1.setText("Load Slot 1\n" + ((int) slot1.getLong("score")) + " Score / " + slot1.getInteger("reputation") + " Rep");
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ss2.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                saveGame(2);
                state1 = STATE.Continue;
                slot2 = Gdx.app.getPreferences("slot2");
                ls2.setText("Load Slot 2\n" + ((int) slot2.getLong("score")) + " Score / " + slot2.getInteger("reputation") + " Rep");
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ss3.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                saveGame(3);
                state1 = STATE.Continue;
                slot3 = Gdx.app.getPreferences("slot3");
                ls3.setText("Load Slot 3\n" + ((int) slot3.getLong("score")) + " Score / " + slot3.getInteger("reputation") + " Rep");
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ls1.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if ((int) slot1.getLong("score") > 0 && slot1.getInteger("reputation") > 0) {
                    loadGame(1);
                    state1 = STATE.Continue;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ls2.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if ((int) slot2.getLong("score") > 0 && slot2.getInteger("reputation") > 0) {
                    loadGame(2);
                    state1 = STATE.Continue;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ls3.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if ((int) slot3.getLong("score") > 0 && slot3.getInteger("reputation") > 0) {
                    loadGame(3);
                    state1 = STATE.Continue;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        // Add Buttons to Stages
        stage.addActor(lowRep);
        stage.addActor(medRep);
        stage.addActor(maxRep);
        stage.addActor(mn);
        stage.addActor(lm);
        stage2.addActor(mb);
        stage2.addActor(rms);
        stage2.addActor(go);
        stage2.addActor(st);
        stage2.addActor(rs);
        stage2.addActor(fs);
        stage2.addActor(ss1);
        stage2.addActor(ss2);
        stage2.addActor(ss3);
        stage2.addActor(ls1);
        stage2.addActor(ls2);
        stage2.addActor(ls3);
        stage2.addActor(btms);
        stage2.addActor(ad);
        stage2.addActor(ts);
        // Handles the spawning of customers at semi-regular intervals
        Timer.schedule(new Timer.Task() {
            public void run() {
                if (!timerRunning) {
                    targetTime = (float) (Math.random() * upperSpawnInterval + lowerSpawnInterval);
                    spawnTime = 0.0f;
                    timerRunning = true;
                } else {
                    spawnTime += spawnInterval * 1000;
                    if (spawnTime >= targetTime) {
                        cc.spawnCustomer();
                        timerRunning = false;
                    }
                }
            }
        }, 0, spawnInterval);
        if (lateLoadSlot >= 1 && lateLoadSlot <= 3) {
            this.loadGame(lateLoadSlot);
        }
    }

    ShapeRenderer selectedPlayerBox = new ShapeRenderer();

    /**
     * Render method for main game
     *
     * @param delta - some change in time
     * @author Team3Gdx
     * @author Neves6
     */

    public void render(float delta) {
        // Clear Screen
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Set Input Processor
        Gdx.input.setInputProcessor(multi);
        // Set Projection Matrices for Game Rendering
        game.shapeRenderer.setProjectionMatrix(worldCamera.combined);
        game.batch.setProjectionMatrix(worldCamera.combined);
        // Render Bottom Map Layer
        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render(new int[]{0});
        // Draw Cook Legs
        game.batch.begin();
        for (Cook curCook : cooks)
            curCook.draw_bot(game.batch);
        game.batch.end();
        // Render Top Map Layer
        tiledMapRenderer.render(new int[]{1});
        // Draw Cook Top Hald
        stationManager.handleStations(game.batch);
        drawHeldItems();
        game.batch.begin();
        for (Cook curCook : cooks)
            curCook.draw_top(game.batch);
        cc.drawCustTop(game.batch);
        game.batch.end();
        // Move Cook
        tempTime = System.currentTimeMillis();
        if (!cook.locked && Tutorial.complete)
            cook.update(control, (tempTime - tempThenTime), CLTiles);
        tempThenTime = tempTime;
        checkInteraction(cook, game.shapeRenderer);
        // Set Matrix for UI Elements
        Matrix4 uiMatrix = worldCamera.combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.setProjectionMatrix(uiMatrix);
        // Draw UI Elements
        drawUI();
        drawPower();
        // Set Matrix Back to Game Matrix

        setCameraLerp(delta);

        game.batch.setProjectionMatrix(worldCamera.combined);
        // Move Camera

        worldCamera.update();
        uiCamera.update();
        // Play Music
        game.gameMusic.play();
        // Draw Interactive UI Elements
        stage.act();
        stage.draw();
        // Jump to State-Specific Logic
        game.batch.setProjectionMatrix(uiMatrix);
        changeScreen(state1);
        game.batch.setProjectionMatrix(worldCamera.combined);

        checkCookSwitch();
        // Check Customer Wait Times
        for (int i = 0; i < cc.customers.length; i++) {
            if (cc.customers[i] != null && cc.customers[i].locked) {
                System.out.println("Customer " + cc.customers[i]);
                if (cc.customers[i].hasExpired()) {
                    System.out.println("Customer has expired " + System.currentTimeMillis() / 1000);
                    cc.delCustomer(cc.customers[i]);
                    updateRep();
                }
            }
        }
        // Check Game Over
        if (orderJustServed) {
            checkGameOver();
            orderJustServed = false;
        }
    }

    /**
     * Used to draw powers depending on power stack.
     *
     * @return
     * @author Neves6
     */
    private boolean drawPower() {
        if (!Power.isPowerEmpty()) {
            game.batch.begin();
            for (PowerUnit powerUnit : Power.getPowerStack()) {
                if (powerUnit.isVisible()) {
                    powerUnit.render(game.batch);
                }
            }
            game.batch.end();
            return true;
        }
        return false;
    }

    /**
     * Change selected cook
     *
     * @author Team3Gdx
     */
    private void checkCookSwitch() {
        if (control.tab && Tutorial.complete) {
            cook.locked = false;
            currentCookIndex += currentCookIndex < cooks.length - 1 ? 1 : -currentCookIndex;
            cook = cooks[currentCookIndex];
        }
        if (control.shift && Tutorial.complete) {
            cook.locked = false;
            currentCookIndex -= currentCookIndex > 0 ? 1 : -cooks.length + 1;
            cook = cooks[currentCookIndex];
        }

        control.interact = false;
        control.drop = false;
        control.flip = false;
        control.tab = false;
        control.shift = false;
    }

    public static final float MAX_WAIT_TIME = 20000; //Customer wait time in ms

    /**
     * Draw UI elements
     *
     * @author Team3Gdx
     * @author Neves6
     */
    private void drawUI() {
        if (orderCards.size() != 0) {
            game.batch.begin();
            float x = orderCards.peek().getX();
            float y = orderCards.peek().getY();
            //stupid verbose for-loop to prevent concurrentModification errors
            for (Iterator<OrderCard> iterator = orderCards.iterator(); iterator.hasNext(); ) {
                OrderCard order = iterator.next();
                float recordedSize = orderCards.size();
                if (order.hasExpired()) {
                    iterator.remove();
                    updateRep();
                }
                game.batch.draw(order.getTexture(), x, y, order.getWidth(), order.getHeight());
                x += 110;
                if (orderCards.size() < recordedSize) {
                    x -= 110 * (recordedSize - orderCards.size());
                }
            }
            game.batch.end();
        }
        if (!Power.isPowerEmpty() && !Power.isPowerFull()) {

        }
        for (int i = 0; i < cooks.length; i++) {
            if (i == currentCookIndex) {
                selectedPlayerBox.setAutoShapeType(true);
                selectedPlayerBox.begin(ShapeType.Line);

                selectedPlayerBox.setColor(Color.GREEN);
                selectedPlayerBox.rect(Gdx.graphics.getWidth() - 128 * cooks.length + i * 128,
                        Gdx.graphics.getHeight() - 128 - 8, 128, 128);
                selectedPlayerBox.end();
            }
            game.batch.begin();
            cooks[i].draw_top(game.batch, new Vector2(Gdx.graphics.getWidth() - 128 * cooks.length + i * 128,
                    Gdx.graphics.getHeight() - 256));
            game.batch.end();
        }
        score = (startTime - timeOnStartup) / 1000;
        game.batch.begin();
        game.font.draw(game.batch, Long.toString(score + accumulatedScore), gameResolutionX / 2f + gameResolutionX / 10f, 19 * gameResolutionY / 20f);
        game.font.draw(game.batch, "Score:", gameResolutionX / 2f, 19 * gameResolutionY / 20f);
        game.batch.end();
    }

    /**
     * Change camera movement type depending on position to cook
     *
     * @param delta - some change in time
     * @author Team3Gdx
     */
    private void setCameraLerp(float delta) {
        if (!Tutorial.complete) {
            worldCamera.position.lerp(new Vector3(Tutorial.getStagePos(), 0), .065f);
            if (control.tab) {
                Tutorial.nextStage();
            } else if (control.shift) {
                Tutorial.previousStage();
            }
            Tutorial.drawBox(game.batch, delta * 20);
        } else {
            if (Math.abs(worldCamera.position.x - cook.pos.x) < 2
                    && Math.abs(worldCamera.position.y - cook.pos.y) < 2) {
                worldCamera.position.x = cook.pos.x;
                worldCamera.position.y = cook.pos.y;
            } else {
                worldCamera.position.lerp(new Vector3(cook.pos.x, cook.pos.y, 0), .065f);
            }
        }
    }

    /**
     * Draws the held items for all cooks on the screen
     *
     * @author Team3Gdx
     */
    private void drawHeldItems() {
        for (Cook ck : cooks) {
            int itemIndex = 0;
            for (Entity ingredient : ck.heldItems) {
                ingredient.pos = new Vector2(ck.pos.x + 16, ck.pos.y + 112 + itemIndex * 8);
                ingredient.draw(game.batch);
                itemIndex++;
            }
        }
    }

    long nowTime = 0;
    long thenTime = 0;

    /**
     * Changes game window state
     *
     * @param state1 - the state to change to
     * @author Team3Gdx
     * @author Neves6
     */
    public void changeScreen(STATE state1) {
        if (state1 == STATE.main) {
            game.gameMusic.dispose();
            game.resetGameScreen();
            game.setScreen(game.getMainScreen());

        }
        if (state1 == STATE.Pause) {
            thenTime = System.currentTimeMillis() - timeOnStartup;
            Gdx.input.setInputProcessor(stage2);
            stage2.act();
            stage2.draw();
        }
        if (state1 == STATE.audio) {
            musicVolumeUpdate();
            gameVolumeUpdate();
            checkState();

            Gdx.input.setInputProcessor(stage2);
            game.batch.begin();
            game.batch.draw(ESC, optionsBackground.getX(), optionsBackground.getY() + 15, optionsBackground.getWidth(),
                    optionsBackground.getHeight() - 30);
            game.batch.end();
            stage2.act();
            stage2.draw();
            game.batch.begin();
            game.batch.draw(audioEdit, audioBackground.getX(), audioBackground.getY(), audioBackground.getWidth(),
                    audioBackground.getHeight());
            game.batch.draw(vControl, volSlideBackgr.getX(), volSlideBackgr.getY(), volSlideBackgr.getWidth(),
                    volSlideBackgr.getHeight());
            game.batch.draw(vButton, volSlide.getX() - volSlide.getWidth() / 2, volSlide.getY(), volSlide.width,
                    volSlide.height);
            game.batch.draw(vControl, musSlideBackgr.getX(), musSlideBackgr.getY(), musSlideBackgr.getWidth(),
                    musSlideBackgr.getHeight());
            game.batch.draw(vButton, musSlide.getX() - musSlide.getWidth() / 2, musSlide.getY(), musSlide.width,
                    musSlide.height);
            game.batch.end();
        }
        if (state1 == STATE.Continue) {
            nowTime = System.currentTimeMillis() - timeOnStartup;
            startTime += nowTime - thenTime;
            cc.updateCustomers();
            thenTime = System.currentTimeMillis() - timeOnStartup;
        }
    }

    /**
     * Checks to see whether escape has been pressed to pause the game
     *
     * @author Team3Gdx
     */
    public void checkState() {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            state1 = STATE.Pause;
        }
    }

    /**
     * Updates the music volume slider
     *
     * @author Team3Gdx
     */
    public void musicVolumeUpdate() {
        float fromTopy = Gdx.input.getY();
        float fromBottomy = gameResolutionY - fromTopy;
        float x = Gdx.input.getX();
        boolean change = musSlide.getY() <= fromBottomy & fromBottomy <= musSlide.getY() + musSlide.getHeight();
        if (Gdx.input.isTouched() & change) {
            if (x >= musSlideBackgr.getX() & x <= musSlideBackgr.getX() + musSlideBackgr.getWidth()) {
                musSlide.setPosition(Gdx.input.getX(), musSlide.getY());
                v = (musSlide.getX() - musSlideBackgr.getX()) / musSlideBackgr.getWidth();
                if (v < 0.01) {
                    v = 0;
                }
                game.mainScreenMusic.setVolume(v);
                game.gameMusic.setVolume(v);
                MainGameClass.musicVolumeScale = v;
            }
        }
    }

    /**
     * Updates the game volume slider
     *
     * @author Team3Gdx
     */
    public void gameVolumeUpdate() {
        float fromTopy = Gdx.input.getY();
        float fromBottomy = gameResolutionY - fromTopy;
        float x = Gdx.input.getX();
        boolean change = volSlide.getY() <= fromBottomy & fromBottomy <= volSlide.getY() + volSlide.getHeight();
        if (Gdx.input.isTouched() & change) {
            if (x >= volSlideBackgr.getX() & x <= volSlideBackgr.getX() + volSlideBackgr.getWidth()) {
                volSlide.setPosition(Gdx.input.getX(), volSlide.getY());
                s = (volSlide.getX() - volSlideBackgr.getX()) / volSlideBackgr.getWidth();
                if (s < 0.01) {
                    s = 0;
                }
                // game.sound.setVolume(game.soundid, s);
                MainGameClass.gameVolumeScale = s;
            }
        }
    }

    /**
     * Calculates coordinates for UI element scaling;
     *
     * @author Team3Gdx
     * @author Neves6
     */
    private void calculateBoxMaths() {
        this.gameResolutionX = ms.gameResolutionX;
        this.gameResolutionY = ms.gameResolutionY;
        this.buttonwidth = gameResolutionX / 10.0f;
        this.buttonheight = gameResolutionY / 20.0f;

        this.audioBackgroundWidth = gameResolutionX / 6.0f;
        this.audioBackgroundHeight = gameResolutionY / 6.0f;

        this.audioBackgroundx = gameResolutionX / 40.0f + buttonwidth
                + 2 * (gameResolutionX / 40.0f - gameResolutionX / 50.0f);
        this.audioBackgroundy = 7 * gameResolutionY / 10.0f;

        this.optionsBackground = new Rectangle();
        optionsBackground.setPosition(gameResolutionX / 50.0f, 35 * gameResolutionY / 40.0f);
        optionsBackground.width = 5 * (buttonwidth + 2 * (gameResolutionX / 40.0f - gameResolutionX / 50.0f));
        optionsBackground.height = 4 * gameResolutionY / 40.0f;

        this.audioBackground = new Rectangle();
        audioBackground.setPosition(audioBackgroundx, audioBackgroundy);
        audioBackground.width = audioBackgroundWidth;
        audioBackground.height = audioBackgroundHeight;

        this.volSlide = new Rectangle();
        volSlide.width = audioBackgroundHeight / 4.0f;
        volSlide.height = audioBackgroundHeight / 4.0f;

        this.volSlideBackgr = new Rectangle();
        volSlideBackgr.width = 2 * audioBackgroundWidth / 3.0f;
        volSlideBackgr.height = audioBackgroundHeight / 6.0f;
        volSlideBackgr.setPosition(audioBackgroundx + audioBackgroundWidth / 6,
                audioBackgroundy + audioBackgroundHeight / 6);

        this.musSlide = new Rectangle();
        musSlide.width = audioBackgroundHeight / 4.0f;
        musSlide.height = audioBackgroundHeight / 4.0f;

        this.musSlideBackgr = new Rectangle();
        musSlideBackgr.width = 2 * audioBackgroundWidth / 3.0f;
        musSlideBackgr.height = audioBackgroundHeight / 6.0f;
        musSlideBackgr.setPosition(audioBackgroundx + audioBackgroundWidth / 6,
                audioBackgroundy + 4 * audioBackgroundHeight / 6);

        this.xSliderMin = audioBackgroundx + audioBackgroundWidth / 6;
        this.xSliderMax = xSliderMin + volSlideBackgr.width;
        this.sliderWidth = volSlideBackgr.width;
    }

    /**
     * Construct an array of CollisionTile objects for collision detection
     *
     * @param mp- game tilemap
     * @author Team3Gdx
     */
    private void constructCollisionData(TiledMap mp) {
        TiledMapTileLayer botlayer = (TiledMapTileLayer) mp.getLayers().get(0);
        int mapwidth = botlayer.getWidth();
        int mapheight = botlayer.getHeight();
        CLTiles = new CollisionTile[mapwidth][mapheight];
        TiledMapTileLayer toplayer = (TiledMapTileLayer) mp.getLayers().get(1);
        int topwidth = toplayer.getWidth();
        int topheight = toplayer.getHeight();
        for (int y = 0; y < topheight; y++) {
            for (int x = 0; x < topwidth; x++) {
                TiledMapTileLayer.Cell tl2 = toplayer.getCell(x, y);
                if (tl2 != null) {
                    CLTiles[x][y] = new CollisionTile(x * 64, y * 64, 64, 64);
                }
            }
        }
        for (int y = 0; y < mapheight; y++) {
            for (int x = 0; x < mapwidth; x++) {
                TiledMapTileLayer.Cell tl = botlayer.getCell(x, y);
                if (tl != null) {
                    TiledMapTile tlt = tl.getTile();
                    MapProperties mpr = tlt.getProperties();
                    //Checks if tile have the "name" custom property
                    //In this implementation only the floor tiles have "name" property
                    if (mpr.get("name") == null) {
                        CLTiles[x][y] = new CollisionTile(x * 64, y * 64, 64, 64);
                    } else {
                        if (y != 0) {
                            if (CLTiles[x][y - 1] != null) {
                                CLTiles[x][y - 1] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * return viewed tile at vector positions
     *
     * @param pos for positinos
     * @return cell object
     * @author Neves6
     */
    public static Cell returnCell(Vector2 pos) {
        return ((TiledMapTileLayer) map1.getLayers().get(1)).getCell((int) pos.x, (int) pos.y);
    }


    /**
     * Check the tile the cook is looking at for interaction
     *
     * @param ck - Selected cook
     * @param sr - ShapeRenderer to draw the coloured box
     * @author Team3Gdx
     * @author Neves6
     */
    public void checkInteraction(Cook ck, ShapeRenderer sr) {
        float centralCookX = ck.getX() + ck.getWidth() / 2;
        float centralCookY = ck.getY();
        int cellx = (int) Math.floor(centralCookX / 64);
        int celly = (int) Math.floor(centralCookY / 64);
        int checkCellX = cellx;
        int checkCellY = celly;
        checkCellX += ck.getDirection().x;
        checkCellY += ck.getDirection().y + 1;
        Cell viewedTile = ((TiledMapTileLayer) map1.getLayers().get(1)).getCell(checkCellX, checkCellY);

        if (viewedTile != null) {
            Object stationType = viewedTile.getTile().getProperties().get("Station");
            if (stationType != null) {
                stationManager.checkInteractedTile((String) viewedTile.getTile().getProperties().get("Station"),
                        new Vector2(checkCellX, checkCellY));
            } else {
                stationManager.checkInteractedTile("", new Vector2(checkCellX, checkCellY));
            }
        }

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(1, 0, 1, 1));
        sr.rect(checkCellX * 64, checkCellY * 64, 64, 64);
        sr.end();
    }

    /**
     * Check if the game is over
     *
     * @author Team3Gdx
     * @author Neves6
     */
    public void checkGameOver() {
        if (Objects.equals(gameMode, "Scenario")) {
            if (customersServed >= scenarioLimit) {
                game.getLeaderBoardScreen().addLeaderBoardData("PLAYER1",
                        (int) Math.floor((startTime - timeOnStartup) / 1000f));
                game.resetGameScreen();
                this.resetStatic();
                game.setScreen(game.getLeaderBoardScreen());
            }
        }
    }

    /**
     * @param points - points to add to the score
     * @author Neves6
     */
    public static void addScore(long points) {
        accumulatedScore += points;
    }

    /**
     * @param points - points to subtract from the score
     * @author Neves6
     */
    public static void subScore(long points) {
        accumulatedScore -= points;
    }

    /**
     * Updates the reputation point visibility
     *
     * @author Neves6
     */
    public void updateRep() {
        switch (reputation) {
            case 3:
                reputation -= 1;
                maxRep.setVisible(false);
                break;
            case 2:
                reputation -= 1;
                medRep.setVisible(false);
            case 1:
                lowRep.setVisible(false);
                state1 = STATE.Pause;
                for (Actor actor : stage2.getActors()) {
                    actor.setVisible(false);
                }
                go.setVisible(true);
        }
    }

    /**
     * Adds a reputation point
     *
     * @author Neves6
     */
    public static void addRep() {
        switch (reputation) {
            case 2:
                reputation += 1;
                maxRep.setVisible(true);
            case 1:
                reputation += 1;
                medRep.setVisible(true);
        }
    }

    /**
     * Resets game parameters
     *
     * @author Neves6
     */
    public void resetStatic() {
        customersServed = 0;
        reputation = 3;
        orderCards = new LinkedList<>();
        spawnInterval = 5.0f;
        upperSpawnInterval = 30000;
        lowerSpawnInterval = 1000;
        Power.getPowerStack().clear();
        this.spawnTime = 0.0f;
        this.targetTime = 0.0f;
        this.timerRunning = false;
    }

    /**
     * Resize game screen - Not used in fullscreen mode
     *
     * @param width  - width to resize to
     * @param height - height to resize to
     * @author Team3Gdx
     */
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        worldViewport.update(width, height);
        uiViewport.update(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }

    /**
     * Save game to a slot
     *
     * @param slotNo - slot to save to
     * @author Neves6
     */
    public void saveGame(int slotNo) {
        Preferences slot = Gdx.app.getPreferences("slot" + slotNo);
        slot.putLong("score", score);
        slot.putLong("accumulatedScore", accumulatedScore);
        slot.putInteger("reputation", reputation);
        slot.putInteger("customersServed", customersServed);
        slot.putFloat("spawnInterval", spawnInterval);
        slot.putFloat("upperSpawnInterval", upperSpawnInterval);
        slot.putFloat("lowerSpawnInterval", lowerSpawnInterval);
        slot.putFloat("spawnTime", spawnTime);
        slot.putFloat("targetTime", targetTime);
        slot.putBoolean("timerRunning", timerRunning);
        slot.putFloat("timeOnStartup", timeOnStartup);
        slot.putFloat("startTime", startTime);
        slot.putString("gameMode", gameMode);
        slot.putInteger("scenarioLimit", scenarioLimit);
        slot.putInteger("CUSTOMER_SPAWNCAP", CUSTOMER_SPAWNCAP);
        slot.putFloat("cook0x", (int) cooks[0].getX());
        slot.putFloat("cook0y", (int) cooks[0].getY());
        slot.putFloat("cook1x", (int) cooks[1].getX());
        slot.putFloat("cook1y", (int) cooks[1].getY());
        if (cooks.length > 2) {
            slot.putFloat("cook2x", (int) cooks[2].getX());
            slot.putFloat("cook2y", (int) cooks[2].getY());
        } else {
            slot.putFloat("cook2x", -1);
            slot.putFloat("cook2y", -1);
        }
        slot.flush();
        cc.saveCC(slotNo);
        Power.savePower(slotNo);
    }

    /**
     * Load game from a slot
     *
     * @param slotNo - slot to load from
     * @author Neves6
     */
    public void loadGame(int slotNo) {
        // load back all preferences saved in saveGame
        Preferences slot = Gdx.app.getPreferences("slot" + slotNo);
        score = slot.getLong("score");
        accumulatedScore = slot.getLong("accumulatedScore");
        reputation = slot.getInteger("reputation");
        customersServed = slot.getInteger("customersServed");
        spawnInterval = slot.getFloat("spawnInterval");
        upperSpawnInterval = slot.getFloat("upperSpawnInterval");
        lowerSpawnInterval = slot.getFloat("lowerSpawnInterval");
        spawnTime = slot.getFloat("spawnTime");
        targetTime = slot.getFloat("targetTime");
        timerRunning = slot.getBoolean("timerRunning");
        timeOnStartup = (long) slot.getFloat("timeOnStartup");
        startTime = (long) slot.getFloat("startTime");
        gameMode = slot.getString("gameMode");
        scenarioLimit = slot.getInteger("scenarioLimit");
        CUSTOMER_SPAWNCAP = slot.getInteger("CUSTOMER_SPAWNCAP");
        cooks[0].setX(slot.getFloat("cook0x"));
        cooks[0].setY(slot.getFloat("cook0y"));
        cooks[1].setX(slot.getFloat("cook1x"));
        cooks[1].setY(slot.getFloat("cook1y"));
        if (cooks.length > 2) {
            cooks[2].setX(slot.getFloat("cook2x"));
            cooks[2].setY(slot.getFloat("cook2y"));
        }
        cc.loadCC(slotNo);
        Power.loadPower(slotNo);
    }
}
