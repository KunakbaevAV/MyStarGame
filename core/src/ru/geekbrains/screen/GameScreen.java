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
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.EnemiesEmitter;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.pools.ExplosionPull;
import ru.geekbrains.sprites.Background;
import ru.geekbrains.sprites.BigStar;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.Bullet;
import ru.geekbrains.sprites.buttons.ButtonExit;
import ru.geekbrains.sprites.buttons.ButtonNewGame;
import ru.geekbrains.sprites.buttons.ButtonUpDamage;
import ru.geekbrains.sprites.buttons.ButtonUpHP;
import ru.geekbrains.sprites.buttons.ButtonUpReload;
import ru.geekbrains.sprites.buttons.MessageGameOver;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.MainShip;
import ru.geekbrains.sprites.Star;

public class GameScreen extends BaseScreen {
    private static final int WHITE_STAR_COUNT = 2048;
    private static final int RED_STAR_COUNT = 84;
    private static final int ORANGE_STAR_COUNT = 42;
    private BigStar bigStar;

    private final float SIDE = 0.01f;
    private final String FRAGS = "Frags: ";
    private final String HP = "HP: ";
    private final String LEVEL = "Level: ";
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private Font font;

    private final String LEVEL_UP = "New level: ";
    private StringBuilder sbNextLevel;
    private Font fontNextLevel;
    private float intervalLabel = 3;
    private float timerLabel = 0;
    private final float LABEL_Y = 0.5f;
    private float posLabelY;

    private float blackoutDraw = 1;

    private enum GameMode {Play, LevelUp, GameOver}

    private GameMode gameMode;

    private List<Sprite> spites;
    private TextureAtlas atlas;

    private MainShip mainShip;
    private BulletPool bulletPool;
    private Sound expSound;
    private Music gameMusic;

    private EnemyPool enemyPool;
    private EnemiesEmitter enemiesEmitter;
    private ExplosionPull explosionPull;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;
    private ButtonExit buttonExit;
    private ButtonUpDamage buttonUpDamage;
    private ButtonUpHP buttonUpHP;
    private ButtonUpReload buttonUpReload;
    private final int maxDamage = 3;

    GameScreen(Game game, Music gameMusic) {
        super();
        addMusic(gameMusic);
    }

    @Override
    public void show() {
        super.show();
        spites = new ArrayList<Sprite>();
        atlas = new TextureAtlas("textures/textures.pack");
        addBackgroud();
        addStars();
        addLabels();
        addMainShip();
        enemyPool = new EnemyPool(atlas, bulletPool, explosionPull, worldBounds, mainShip);
        enemiesEmitter = new EnemiesEmitter(enemyPool);
        addButtons();
        gameMode = GameMode.Play;
    }

    private void addButtons() {
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(this);
        buttonExit = new ButtonExit(atlas);
        buttonUpDamage = new ButtonUpDamage(atlas, "upDamage", mainShip, this);
        buttonUpHP = new ButtonUpHP(atlas, "upHP", mainShip, this);
        buttonUpReload = new ButtonUpReload(atlas, "upReload", mainShip, this);
    }

    private void addMusic(Music gameMusic) {
        this.gameMusic = gameMusic;
        expSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bang.wav"));
        gameMusic.play();
        gameMusic.setLooping(true);
        gameMusic.setVolume(VOLUME);
    }

    private void addLabels() {
        font = new Font("font/myFont.fnt", "font/myFont.png");
        font.setFrontSize(0.04f);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();

        sbLevel = new StringBuilder();
        fontNextLevel = new Font("font/myFont.fnt", "font/myFont.png");
        fontNextLevel.setFrontSize(0.06f);
        sbNextLevel = new StringBuilder();
    }

    private void addMainShip() {
        explosionPull = new ExplosionPull(atlas, expSound);
        bulletPool = new BulletPool();
        mainShip = new MainShip(
                atlas,
                explosionPull,
                bulletPool,
                this);
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
        printInfo();
        switch (gameMode) {
            case Play:
                batch.setColor(1,1,1,1);
                break;
            case LevelUp:
                if (mainShip.getBulledDamage() < maxDamage) buttonUpDamage.draw(batch);
                buttonUpHP.draw(batch);
                buttonUpReload.draw(batch);
                break;
            case GameOver:
                batch.setColor(1, 1, 1, 1);
                messageGameOver.draw(batch);
                buttonNewGame.draw(batch);
                batch.setColor(blackoutDraw, blackoutDraw, blackoutDraw, blackoutDraw);
                buttonExit.draw(batch);
                break;
        }
        batch.end();
    }

    private void update(float delta) {
        for (Sprite s : spites) {
            s.update(delta);
        }
        explosionPull.updateActiveObjects(delta);

        if (mainShip.isDestroyed()) gameMode = GameMode.GameOver;

        switch (gameMode) {
            case Play:
                mainShip.update(delta);
                enemyPool.updateActiveObjects(delta);
                enemiesEmitter.generateEnemies(delta);
                bulletPool.updateActiveObjects(delta);
                break;
            case LevelUp:
                labelAnimation(delta);
                break;
            case GameOver:
                blackoutAnimation(delta);
                break;
        }
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbFrags.append(FRAGS).append(mainShip.getFrags());
        font.draw(batch, sbFrags, worldBounds.getLeft() + SIDE, worldBounds.getTop() - SIDE, Align.left);

        sbHP.setLength(0);
        sbHP.append(HP).append(mainShip.getHp());
        font.draw(batch, sbHP, 0, worldBounds.getBottom() + 0.05f + SIDE, Align.center);

        sbLevel.setLength(0);
        sbLevel.append(LEVEL).append(mainShip.getLevel());
        font.draw(batch, sbLevel, worldBounds.getRight() - SIDE, worldBounds.getTop() - SIDE, Align.right);

        if (gameMode == GameMode.LevelUp)
            fontNextLevel.draw(batch, sbNextLevel, 0, posLabelY, Align.center);
    }

    public void nextLevel() {
        sbNextLevel.setLength(0);
        sbNextLevel.append(LEVEL_UP).append(mainShip.getLevel());
        gameMode = GameMode.LevelUp;
    }

    public void continueGame() {
        gameMode = GameMode.Play;
    }

    private void labelAnimation(float delta) {
        timerLabel += delta;
        posLabelY -= 0.001f;
        if (timerLabel > intervalLabel) {
            timerLabel = 0;
            posLabelY = LABEL_Y;
        }
    }

    private void blackoutAnimation(float delta) {
        if (blackoutDraw > 0.003){
            blackoutDraw -= 0.001;
        }else{
            blackoutDraw = 0;
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
                mainShip.doDamage(enemy.getHp() * 10);
                enemy.doDamage(enemy.getHp());
            }
        }

        // попадание во вражеский корабль
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) continue;
            for (Bullet bullet : bulletPool.getActiveObjects()) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) continue;
                if (bullet.isCollision(enemy, 0.7f)) {
                    bullet.boomBullet();
                    enemy.doDamage(bullet.getDamage());
                }
            }
        }

        // попадание в нас
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) continue;
            if (bullet.isCollision(mainShip, 0.6f)) {
                mainShip.doDamage(bullet.getDamage());
                bullet.boomBullet();
            }
        }
    }

    public void startNewGame() {
        gameMode = GameMode.Play;
        bulletPool.freeAllActiveObjects();
        explosionPull.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        mainShip.startNewGame();
        bigStar.startNewGame();
        blackoutDraw = 1;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (gameMode != GameMode.GameOver) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (gameMode != GameMode.GameOver) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        for (Sprite s : spites) {
            s.touchDown(touch, pointer);
        }
        switch (gameMode) {
            case Play:
                mainShip.touchDown(touch, pointer);
                break;
            case LevelUp:
                if (mainShip.getBulledDamage() < maxDamage) buttonUpDamage.touchDown(touch, pointer);
                buttonUpHP.touchDown(touch, pointer);
                buttonUpReload.touchDown(touch, pointer);
                break;
            case GameOver:
                buttonNewGame.touchDown(touch, pointer);
                buttonExit.touchDown(touch, pointer);
                break;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        for (Sprite s : spites) {
            s.touchUp(touch, pointer);
        }
        switch (gameMode) {
            case Play:
                mainShip.touchDown(touch, pointer);
                break;
            case LevelUp:
                if (mainShip.getBulledDamage() < maxDamage) buttonUpDamage.touchUp(touch, pointer);
                buttonUpHP.touchUp(touch, pointer);
                buttonUpReload.touchUp(touch, pointer);
                break;
            case GameOver:
                buttonNewGame.touchUp(touch, pointer);
                buttonExit.touchUp(touch, pointer);
                break;
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
        buttonExit.resize(worldBounds);
        buttonUpDamage.resize(worldBounds);
        buttonUpHP.resize(worldBounds);
        buttonUpReload.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPull.dispose();
        expSound.dispose();
        gameMusic.dispose();
        font.dispose();
        fontNextLevel.dispose();
    }
}
