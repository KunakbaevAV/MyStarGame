package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import ru.geekbrains.sprites.Background;
import ru.geekbrains.sprites.BigStar;
import ru.geekbrains.sprites.Star;
import ru.geekbrains.sprites.buttons.ButtonExit;

public class BaseGameScreen extends BaseScreen {
    private static final int WHITE_STAR_COUNT = 2048;
    private static final int RED_STAR_COUNT = 84;
    private static final int ORANGE_STAR_COUNT = 42;
    Game game;
    BigStar bigStar;
    Sprite btnExit;
    TextureAtlas atlas = new TextureAtlas("textures/textures.pack");
    final List<Sprite> spites = new ArrayList<Sprite>();

    BaseGameScreen(Game game) {
        this.game = game;
        bigStar = new BigStar(atlas, "bigStar");
    }

    public BaseGameScreen(Game game, String bigStarName) {
        this.game = game;
        this.bigStar = new BigStar(atlas, bigStarName);
    }

    @Override
    public void show() {
        super.show();
//        atlas ;
        addBackgroud();
        addStars();
        addButtons();
    }

    private void addButtons() {
        btnExit = new ButtonExit(atlas);
        spites.add(btnExit);
    }

    private void addBackgroud() {
        Texture backgroudTexture = new Texture("space.png");
        TextureRegion region = new TextureRegion(backgroudTexture);
        Sprite background = new Background(region);
        spites.add(background);
    }

    private void addStars() {

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
        drawBatch();
    }

    public void update(float delta) {
        for (Sprite s : spites) {
            s.update(delta);
        }
    }

    public void draw() {
        for (Sprite s : spites) {
            s.draw(batch);
        }
    }

    private void drawBatch(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        draw();
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
        for (Sprite s : spites) {
            s.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
    }

}
