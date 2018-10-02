package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import ru.geekbrains.math.EnemiesEmitter;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.sprites.Background;
import ru.geekbrains.sprites.BigStar;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.ships.MainShip;
import ru.geekbrains.sprites.Star;

public class GameScreen extends BaseScreen {
    private static final int WHITE_STAR_COUNT = 2048;
    private static final int RED_STAR_COUNT = 84;
    private static final int ORANGE_STAR_COUNT = 42;
    private List<Sprite> spites;
    private TextureAtlas atlas;
    private int shipNumber;

    private MainShip mainShip;
    private BulletPool bulletPool;
    private Sound shotMainSound;
    private Sound shotEnemySound;
    private Music gameMusic;

    private EnemyPool enemyPool;
    private EnemiesEmitter enemiesEmitter;

    GameScreen(Game game, Music gameMusic) {
        super(game);
        this.gameMusic = gameMusic;
        shotMainSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        shotEnemySound = Gdx.audio.newSound(Gdx.files.internal("sounds/shotProt1.wav"));
    }

    @Override
    public void show() {
        super.show();
        spites = new ArrayList<Sprite>();
        atlas = new TextureAtlas("textures/textures.pack");
        addBackgroud();
        addStars();
        bulletPool = new BulletPool();
        addMainShip();
        gameMusic.play();
        gameMusic.setVolume(VOLUME);
        enemyPool = new EnemyPool(atlas, bulletPool, shotEnemySound, worldBounds, mainShip);
        enemiesEmitter = new EnemiesEmitter(enemyPool);
    }

    private void addMainShip() {
        mainShip = new MainShip(
                atlas,
                bulletPool,
                shotMainSound);
        spites.add(mainShip);
    }

    private void addBackgroud() {
        Texture backgroudTexture = new Texture("space.png");
        TextureRegion region = new TextureRegion(backgroudTexture);
        Sprite background = new Background(region);
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
        checkCollisions();
        deleteAllDestroed();
        draw();
    }

    private void deleteAllDestroed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void checkCollisions() {

    }

    private void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Sprite s : spites) {
            s.draw(batch);
        }
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Sprite s : spites) {
            s.update(delta);
        }
        bulletPool.updateActiveObjects(delta);
        enemyPool.updateActiveObjects(delta);
        enemiesEmitter.generateEnemies(delta);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
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
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        shotMainSound.dispose();
        shotEnemySound.dispose();
        gameMusic.dispose();
        super.dispose();
    }
}
