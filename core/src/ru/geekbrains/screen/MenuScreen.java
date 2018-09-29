package ru.geekbrains.screen;
/**
 * author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.BigStar;
import ru.geekbrains.sprites.ButtonExit;
import ru.geekbrains.sprites.ButtonStart;
import ru.geekbrains.sprites.Star;

public class MenuScreen extends BaseScreen {

    private static final int WHITE_STAR_COUNT = 2048;
    private static final int RED_STAR_COUNT = 84;
    private static final int ORANGE_STAR_COUNT = 42;
    private List<Sprite> spites;

    private TextureAtlas atlas;
    private Game game;
    private Rect worldBound;
    private Sound gameSound;
    private Sound menuSound;

    public MenuScreen(Game game) {
        super(game);
        this.game = game;
        gameSound = Gdx.audio.newSound(Gdx.files.internal("sounds/backSound.mp3"));
        menuSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Twelve Titans Music - Monolith.mp3"));
        menuSound.play(1.0f);
    }

    @Override
    public void show() {
        super.show();
        spites = new ArrayList<Sprite>();
        atlas = new TextureAtlas("textures/textures.pack");
        addBackgroud();
        addStars();
        addButtons();
        addLogo();
    }

    private void addLogo() {
        Sprite logo = new Sprite(atlas, "logo");
        logo.setHeightProportion(0.3f);
        spites.add(logo);
    }

    private void addButtons() {
        Sprite btnExit = new ButtonExit(atlas);
        spites.add(btnExit);
        Sprite btnStart = new ButtonStart(this);
        spites.add(btnStart);
    }

    private void addBackgroud() {
        Texture backgroudTexture = new Texture("space.png");
        Sprite background = new Sprite(new TextureRegion(backgroudTexture), 1);
        background.setPosition(-0.5f, -0.5f);
        spites.add(background);
    }

    private void addStars() {
        Sprite bigStar = new BigStar(atlas);
        Sprite[] whiteStar = new Star[WHITE_STAR_COUNT];
        Sprite[] redStar = new Star[RED_STAR_COUNT];
        Sprite[] orangeStar = new Star[ORANGE_STAR_COUNT];

        for (int i = 0; i < whiteStar.length; i++) {
            whiteStar[i] = new Star(atlas, "star", 0.005f, 0.015f, 0.001f);
            spites.add(whiteStar[i]);
        }

        spites.add(bigStar);

        for (int i = 0; i < redStar.length; i++) {
            redStar[i] = new Star(atlas, "star2", 0.01f, 0.1f, 0.002f);
            spites.add(redStar[i]);
        }

        for (int i = 0; i < orangeStar.length; i++) {
            orangeStar[i] = new Star(atlas, "star3", 0.01f, 0.1f, 0.004f);
            spites.add(orangeStar[i]);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Sprite s : spites) {
            s.update(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Sprite s : spites) {
            s.draw(batch);
        }
        batch.end();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        for (Sprite s : spites) {
            s.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        for (Sprite s : spites) {
            s.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBound = worldBounds;
        for (Sprite s : spites) {
            s.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        atlas.dispose();
        menuSound.dispose();
        gameSound.dispose();
        super.dispose();
    }

    public void startGame(){
        menuSound.stop();
        game.setScreen(new GameScreen(game, gameSound));
    }
}

