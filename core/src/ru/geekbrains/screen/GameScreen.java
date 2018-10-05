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
import ru.geekbrains.pools.ExplosionPull;
import ru.geekbrains.sprites.Background;
import ru.geekbrains.sprites.BigStar;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.Bullet;
import ru.geekbrains.sprites.ButtonNewGame;
import ru.geekbrains.sprites.MessageGameOver;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.MainShip;
import ru.geekbrains.sprites.Star;

public class GameScreen extends BaseScreen {
    private static final int WHITE_STAR_COUNT = 2048;
    private static final int RED_STAR_COUNT = 84;
    private static final int ORANGE_STAR_COUNT = 42;
    private BigStar bigStar;

    private boolean gameOver;

    private List<Sprite> spites;
    private TextureAtlas atlas;

    private MainShip mainShip;
    private BulletPool bulletPool;
    private Sound shotMainSound;
    private Sound shotEnemySound;
    private Sound expSound;
    private Music gameMusic;

    private EnemyPool enemyPool;
    private EnemiesEmitter enemiesEmitter;
    private ExplosionPull explosionPull;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    GameScreen(Game game, Music gameMusic) {
        super();
        this.gameMusic = gameMusic;
        shotMainSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        shotEnemySound = Gdx.audio.newSound(Gdx.files.internal("sounds/shotProt1.wav"));
        expSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bang.wav"));
    }

    @Override
    public void show() {
        super.show();
        spites = new ArrayList<Sprite>();
        atlas = new TextureAtlas("textures/textures.pack");
        addBackgroud();
        addStars();
        bulletPool = new BulletPool();
        gameMusic.play();
        gameMusic.setVolume(VOLUME);
        explosionPull = new ExplosionPull(atlas, expSound);
        addMainShip();
        enemyPool = new EnemyPool(atlas, bulletPool, explosionPull, shotEnemySound, worldBounds, mainShip);
        enemiesEmitter = new EnemiesEmitter(enemyPool);
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(this);
    }

    private void addMainShip() {
        mainShip = new MainShip(
                atlas,
                explosionPull,
                bulletPool,
                shotMainSound);
    }

    private void addBackgroud() {
        Texture backgroudTexture = new Texture("space.png");
        TextureRegion region = new TextureRegion(backgroudTexture);
        Sprite background = new Background(region);
        spites.add(background);
    }

    private void addStars() {
        bigStar = new BigStar(atlas);
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

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroed();
        draw();
    }

    private void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Sprite s : spites) {
            s.draw(batch);
        }
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        explosionPull.drawActiveObjects(batch);
        if (gameOver) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }

    private void update(float delta) {
        for (Sprite s : spites) {
            s.update(delta);
        }
        explosionPull.updateActiveObjects(delta);
        bulletPool.updateActiveObjects(delta);

        gameOver = mainShip.isDestroyed();
        if (!gameOver) {
            mainShip.update(delta);
            enemyPool.updateActiveObjects(delta);
            enemiesEmitter.generateEnemies(delta);
        }
    }

    private void deleteAllDestroed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPull.freeAllDestroyedActiveObjects();
    }

    private void checkCollisions() {
        // столкновение кораблей
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            if (mainShip.isCollision(enemy, 0.7f)) {
                enemy.destroy();
                enemy.destroyShipOut();
                mainShip.doDamage(enemy.getHp() * 10);
            }
        }

        // попадание во вражеский корабль
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            for (Bullet bullet : bulletPool.getActiveObjects()) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) continue;
                if (bullet.isCollision(enemy, 0.7f)) {
                    bullet.destroy();
                    enemy.doDamage(bullet.getDamage());
                }
            }
        }

        // попадание в нас
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) continue;
            if (bullet.isCollision(mainShip, 0.6f)) {
                mainShip.doDamage(bullet.getDamage());
                bullet.destroy();
            }
        }
    }

    public void startNewGame() {
        gameOver = false;
        bulletPool.freeAllActiveObjects();
        explosionPull.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        mainShip.startNewGame();
        bigStar.startNewGame();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!gameOver) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!gameOver) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        for (Sprite s : spites) {
            s.touchDown(touch, pointer);
        }
        if (!gameOver) {
            mainShip.touchDown(touch, pointer);
        } else {
            buttonNewGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        for (Sprite s : spites) {
            s.touchUp(touch, pointer);
        }
        if (!gameOver) {
            mainShip.touchDown(touch, pointer);
        } else {
            buttonNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        for (Sprite s : spites) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPull.dispose();
        shotMainSound.dispose();
        shotEnemySound.dispose();
        expSound.dispose();
        gameMusic.dispose();
        super.dispose();
    }
}
