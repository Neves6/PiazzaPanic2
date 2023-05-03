package com.team3gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team3gdx.game.MainGameClass;
import com.team3gdx.game.entity.Customer;

/**
 * Main menu screen
 * @author Team3Gdx
 * @author Neves6
 */
public class MainScreen implements Screen {
    final MainGameClass game;
    float v = 0;
    float s = 0;

    int gameResolutionX;
    int gameResolutionY;

    float buttonwidth;
    float buttonheight;

    float xSliderMin;
    float xSliderMax;

    float sliderWidth;

    Button sb;
    Button eb;
    Button em;
    Button mm;
    Button hm;
    Button lb;
    Button ad;
    Button tb;
    Button ts;
    Button eg;
    TextButton ls1;
    TextButton ls2;
    TextButton ls3;

    Rectangle volSlide;
    Rectangle volSlideBackgr;
    Rectangle musSlide;
    Rectangle musSlideBackgr;

    OrthographicCamera camera;
    Viewport viewport;

    Texture vButton;
    Texture vControl;
    Texture background;
    Texture scenarioButton;
    Texture endlessButton;
    Texture easyMode;
    Texture moderateMode;
    Texture hardMode;
    Texture leaderBoard;
    Texture exitGame;
    Texture audio;
    Texture audioEdit;
    Texture tutorial;
    Texture tutorialScreen;
    Texture loadData;
    Stage stage;
    Preferences slot1, slot2, slot3;

    /**
     * Enum for the different states of the menu
     * @author Team3Gdx
     * @author Neves6
     */
    enum STATE {
        main, audio, leaderboard, new_game
    }

    STATE state;

    /**
     * Constructor for main menu screen
     * @author Team3Gdx
     * @author Neves6
     *
     * @param game - Entry point class
     */
    public MainScreen(final MainGameClass game) {
        this.game = game;
        this.gameResolutionX = Gdx.graphics.getWidth();
        this.gameResolutionY = Gdx.graphics.getHeight();
        this.buttonwidth = (float) gameResolutionX / 3;
        this.buttonheight = (float) gameResolutionY / 6;

        this.volSlide = new Rectangle();
        volSlide.width = 3 * buttonheight / 12;
        volSlide.height = 3 * buttonheight / 12;

        this.volSlideBackgr = new Rectangle();
        volSlideBackgr.width = 2 * buttonwidth / 6;
        volSlideBackgr.height = buttonheight / 6;

        this.musSlide = new Rectangle();
        musSlide.width = 3 * buttonheight / 12;
        musSlide.height = 3 * buttonheight / 12;

        this.musSlideBackgr = new Rectangle();
        musSlideBackgr.width = 2 * buttonwidth / 6;
        musSlideBackgr.height = buttonheight / 6;

        this.xSliderMin = gameResolutionX / 2.0f + buttonwidth / 12;
        this.xSliderMax = xSliderMin + volSlideBackgr.width;
        this.sliderWidth = volSlideBackgr.width;

        slot1 = Gdx.app.getPreferences("slot1");
        slot2 = Gdx.app.getPreferences("slot2");
        slot3 = Gdx.app.getPreferences("slot3");
    }

    /**
     * What should be done when the screen is shown
     * @author Team3Gdx
     * @author Neves6
     */
    @Override
    public void show() {
        float currentMusicVolumeSliderX = (MainGameClass.musicVolumeScale * sliderWidth) + xSliderMin;
        float currentGameVolumeSliderX = (MainGameClass.gameVolumeScale * sliderWidth) + xSliderMin;
        volSlide.setPosition(currentGameVolumeSliderX, 2 * gameResolutionY / 5.0f - buttonheight / 2 + buttonheight / 6
                + volSlideBackgr.height / 2 - volSlide.height / 2);
        volSlideBackgr.setPosition((gameResolutionX / 2.0f) + buttonwidth / 12,
                2 * gameResolutionY / 5.0f - buttonheight / 2 + buttonheight / 6);
        musSlide.setPosition(currentMusicVolumeSliderX, 2 * gameResolutionY / 5.0f - buttonheight / 2
                + 4 * buttonheight / 6 + musSlideBackgr.height / 2 - musSlide.height / 2);
        musSlideBackgr.setPosition((gameResolutionX / 2.0f) + buttonwidth / 12,
                2 * gameResolutionY / 5.0f - buttonheight / 2 + 4 * buttonheight / 6);

        state = STATE.main;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameResolutionX, gameResolutionY);
        viewport = new FitViewport(gameResolutionX, gameResolutionY, camera);

        vButton = new Texture(Gdx.files.internal("uielements/vButton.jpg"));
        vControl = new Texture(Gdx.files.internal("uielements/vControl.png"));
        scenarioButton = new Texture(Gdx.files.internal("uielements/newScenarioGame.png"));
        endlessButton = new Texture(Gdx.files.internal("uielements/newEndlessGame.png"));
        easyMode = new Texture(Gdx.files.internal("uielements/EasyMode.png"));
        moderateMode = new Texture(Gdx.files.internal("uielements/ModerateMode.png"));
        hardMode = new Texture(Gdx.files.internal("uielements/HardMode.png"));
        background = new Texture(Gdx.files.internal("uielements/MainScreenBackground.jpg"));
        leaderBoard = new Texture(Gdx.files.internal("uielements/leaderboard1.png"));
        audio = new Texture(Gdx.files.internal("uielements/audio.png"));
        audioEdit = new Texture(Gdx.files.internal("uielements/background.png"));
        tutorial = new Texture(Gdx.files.internal("uielements/homeScreenTutorial.png"));
        tutorialScreen = new Texture(Gdx.files.internal("uielements/PP2TutorialPage.jpg"));
        exitGame = new Texture(Gdx.files.internal("uielements/exitgame.png"));
        loadData = new Texture(Gdx.files.internal("uielements/SaveDataButton.png"));

        sb = new Button(new TextureRegionDrawable(scenarioButton));
        eb = new Button(new TextureRegionDrawable(endlessButton));
        em = new Button(new TextureRegionDrawable(easyMode));
        mm = new Button(new TextureRegionDrawable(moderateMode));
        hm = new Button(new TextureRegionDrawable(hardMode));
        lb = new Button(new TextureRegionDrawable(leaderBoard));
        ad = new Button(new TextureRegionDrawable(audio));
        tb = new Button(new TextureRegionDrawable(tutorial));
        ts = new Button(new TextureRegionDrawable(tutorialScreen));
        eg = new Button(new TextureRegionDrawable(exitGame));
        TextButton.TextButtonStyle tbStyle = new TextButton.TextButtonStyle();
        tbStyle.font = game.font;
        tbStyle.up = new TextureRegionDrawable(loadData);
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

        sb.setPosition(gameResolutionX / 10.0f, 6 * gameResolutionY / 7.0f - buttonheight / 2);
        eb.setPosition(gameResolutionX / 10.0f, 5 * gameResolutionY / 7.0f - buttonheight / 2);
        em.setPosition(gameResolutionX / 2.0f, 6 * gameResolutionY / 7.0f - buttonheight / 2);
        em.setVisible(false);
        mm.setPosition(gameResolutionX / 2.0f, 5 * gameResolutionY / 7.0f - buttonheight / 2);
        mm.setVisible(false);
        hm.setPosition(gameResolutionX / 2.0f, 4 * gameResolutionY / 7.0f - buttonheight / 2);
        hm.setVisible(false);
        tb.setPosition(gameResolutionX / 10.0f, 4 * gameResolutionY / 7.0f - buttonheight / 2);
        lb.setPosition(gameResolutionX / 10.0f, 3 * gameResolutionY / 7.0f - buttonheight / 2);
        ad.setPosition(gameResolutionX / 10.0f, 2 * gameResolutionY / 7.0f - buttonheight / 2);
        eg.setPosition(gameResolutionX / 10.0f, gameResolutionY / 7.0f - buttonheight / 2);
        ts.setFillParent(true);
        ts.setVisible(false);

        lb.setSize(buttonwidth, buttonheight);
        ad.setSize(buttonwidth, buttonheight);
        eg.setSize(buttonwidth, buttonheight);
        tb.setSize(buttonwidth, buttonheight);
        sb.setSize(buttonwidth, buttonheight);
        eb.setSize(buttonwidth, buttonheight);
        em.setSize(buttonwidth, buttonheight);
        mm.setSize(buttonwidth, buttonheight);
        hm.setSize(buttonwidth, buttonheight);
        ls1.setSize(3 * buttonwidth / 4, 3 * buttonheight / 4);
        ls2.setSize(ls1.getWidth(), ls1.getHeight());
        ls3.setSize(ls2.getWidth(), ls2.getHeight());
        ls1.setPosition(ad.getX() + 7 * ad.getWidth() / 4, ad.getY() + 3 * ad.getHeight() / 4);
        ls2.setPosition(ls1.getX(), ls1.getY() - ls1.getHeight());
        ls3.setPosition(ls2.getX(), ls2.getY() - ls2.getHeight());


        ad.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state = STATE.audio;
                em.setVisible(false);
                mm.setVisible(false);
                hm.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        sb.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.gameMode = "Scenario";
                em.setVisible(true);
                mm.setVisible(true);
                hm.setVisible(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        eb.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.gameMode = "Endless";
                em.setVisible(true);
                mm.setVisible(true);
                hm.setVisible(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        lb.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state = STATE.leaderboard;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        //difficulty is tied to the number of customers generated and how frequently.
        //As difficulty increases, the maximum number of customers permitted onscreen increases
        //and the interval between spawning customers is shortened dramatically.
        em.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Easy mode has customers generating ~25% slower, similarly ~25% more patient in waiting
                //and only individually- no groups. The required number of orders served in scenario
                // mode is lowered by 5.
                GameScreen.difficulty = "Easy";
                GameScreen.scenarioLimit = 5;
                GameScreen.CUSTOMER_SPAWNCAP = 1;
                GameScreen.cc.reInitCustArr();
                Customer.maxWaitTime *= 1.25f;
                GameScreen.upperSpawnInterval *= 1.25f;
                GameScreen.lowerSpawnInterval *= 1.25f;
                GameScreen.spawnInterval *= 1.25f;
                state = STATE.new_game;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        mm.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Moderate mode has customers generating and waiting at the base rate
                //and allows for pairs of customers to spawn at any given time.
                //The required number of orders served in scenario mode is 10.
                GameScreen.difficulty = "Moderate";
                GameScreen.scenarioLimit = 10;
                GameScreen.CUSTOMER_SPAWNCAP = 2;
                GameScreen.cc.reInitCustArr();
                state = STATE.new_game;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        hm.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Hard mode has customers generating ~50% faster and allows for groups of up to 3 customers
                //to spawn at any given time. Customers are ~25% less patient in waiting to be served also.
                //The required number of orders served in scenario mode is the raised by 5.
                GameScreen.difficulty = "Hard";
                GameScreen.scenarioLimit = 15;
                GameScreen.CUSTOMER_SPAWNCAP = 3;
                GameScreen.cc.reInitCustArr();
                Customer.maxWaitTime *= 0.75f;
                GameScreen.upperSpawnInterval *= 0.5f;
                GameScreen.lowerSpawnInterval *= 0.5f;
                GameScreen.spawnInterval *= 0.5f;
                state = STATE.new_game;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        tb.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ts.setVisible(true);
                em.setVisible(false);
                mm.setVisible(false);
                hm.setVisible(false);
            }
        });
        ts.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ts.setVisible(false);
            }
        });
        eg.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (state == STATE.main) {
                    Gdx.app.exit();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });

        //Load button handlers
        ls1.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if ((int) slot1.getLong("score") > 0 && slot1.getInteger("reputation") > 0) {
                    MainGameClass.lateLoadSlot = 1;
                    game.resetGameScreen();
                    state = STATE.new_game;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ls2.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if ((int) slot2.getLong("score") > 0 && slot2.getInteger("reputation") > 0) {
                    MainGameClass.lateLoadSlot = 2;
                    game.resetGameScreen();
                    state = STATE.new_game;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ls3.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if ((int) slot3.getLong("score") > 0 && slot3.getInteger("reputation") > 0) {
                    MainGameClass.lateLoadSlot = 3;
                    game.resetGameScreen();
                    state = STATE.new_game;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(sb);
        stage.addActor(eb);
        stage.addActor(em);
        stage.addActor(mm);
        stage.addActor(hm);
        stage.addActor(ls1);
        stage.addActor(ls2);
        stage.addActor(ls3);
        stage.addActor(lb);
        stage.addActor(ad);
        stage.addActor(tb);
        stage.addActor(eg);
        stage.addActor(ts);

    }

    /**
     * Main menu render method
     *
     * @author Team3Gdx
     * @param delta - some change in time
     */
    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.setProjectionMatrix(camera.combined);
        game.mainScreenMusic.play();

        game.batch.begin();
        game.batch.draw(background, 0, 0, gameResolutionX, gameResolutionY);
        game.batch.end();
        stage.act();
        stage.draw();
        changeScreen(state);

        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            state = STATE.main;
        }
    }

    /**
     * Change screen to specified state
     *
     * @author Team3Gdx
     * @param state - state to change screen to
     */
    public void changeScreen(STATE state) {
        if (state == STATE.new_game) {
            game.mainScreenMusic.dispose();
            game.setScreen(game.getGameScreen());
        }
        if (state == STATE.leaderboard) {
            game.mainScreenMusic.dispose();
            game.setScreen(game.getLeaderBoardScreen());
        }
        if (state == STATE.audio) {
            musicVolumeUpdate();
            gameVolumeUpdate();

            game.batch.begin();
            game.batch.draw(audioEdit, (float) gameResolutionX / 2, (float) 2 * gameResolutionY / 5 - buttonheight / 2,
                    buttonwidth / 2, buttonheight);

            game.batch.draw(vControl, volSlideBackgr.getX(), volSlideBackgr.getY(), volSlideBackgr.width,
                    volSlideBackgr.height);
            game.batch.draw(vButton, volSlide.getX() - volSlide.width / 2, volSlide.getY(), volSlide.width,
                    volSlide.height);

            game.batch.draw(vControl, musSlideBackgr.getX(), musSlideBackgr.getY(), musSlideBackgr.width,
                    musSlideBackgr.height);
            game.batch.draw(vButton, musSlide.getX() - musSlide.width / 2, musSlide.getY(), musSlide.width,
                    musSlide.height);

            game.batch.end();
        }
    }

    /**
     * Update music volume slider
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
     * Update game volume slider
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
                MainGameClass.gameVolumeScale = s;
            }
        }
    }

    /**
     * Resize window
     *
     * @author Team3Gdx
     * @param width  - new window width
     * @param height - new window height
     */
    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
