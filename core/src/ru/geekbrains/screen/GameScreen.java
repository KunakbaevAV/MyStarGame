package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.Font;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.EnemiesEmitter;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.PanelObjects;
import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.pools.ExplosionPull;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.Bullet;
import ru.geekbrains.sprites.buttons.ButtonNewGame;
import ru.geekbrains.sprites.buttons.bonuses.ButtonLeftWeapon;
import ru.geekbrains.sprites.buttons.bonuses.ButtonRightWeapon;
import ru.geekbrains.sprites.buttons.bonuses.ButtonUpDamage;
import ru.geekbrains.sprites.buttons.bonuses.ButtonUpDamageLeft;
import ru.geekbrains.sprites.buttons.bonuses.ButtonUpDamageRight;
import ru.geekbrains.sprites.buttons.bonuses.ButtonUpHP;
import ru.geekbrains.sprites.buttons.bonuses.ButtonUpReload;
import ru.geekbrains.sprites.buttons.MessageGameOver;
import ru.geekbrains.sprites.buttons.bonuses.PanelBonuses;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.MainShip;

public class GameScreen extends BaseGameScreen {

    private final float SIDE = 0.01f;
    private final String FRAGS = "Frags: ";
    private final String HP = "HP: ";
    private final String LEVEL = "Level: ";
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private Font font;

    private float intervalGameOver = 3;
    private float timerGameOver = 0;

    private float blackoutDraw = 1;

    private enum GameMode {Play, LevelUp, GameOver}

    private GameMode gameMode;

    private MainShip mainShip;
    private BulletPool bulletPool;
    private Sound expSound;
    private Music gameMusic;

    private EnemyPool enemyPool;
    private EnemiesEmitter enemiesEmitter;
    private ExplosionPull explosionPull;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private PanelBonuses bonuses;

    GameScreen(Game game, Music gameMusic) {
        super(game);
        addMusic(gameMusic);
    }

    @Override
    public void show() {
        super.show();
        addLabels();
        addMainShip();
        enemyPool = new EnemyPool(atlas, bulletPool, explosionPull, worldBounds, mainShip);
        enemiesEmitter = new EnemiesEmitter(enemyPool);
        addButtons();
        addBonuses();
        gameMode = GameMode.Play;
    }

    private void addBonuses() {
        bonuses = new PanelBonuses(this.worldBounds);
        bonuses.add(new ButtonLeftWeapon(atlas, "leftWeapon", mainShip, this));
        bonuses.add(new ButtonRightWeapon(atlas, "rightWeapon", mainShip, this));
        bonuses.add(new ButtonUpDamage(atlas, "upDamage", mainShip, this));
        bonuses.add(new ButtonUpDamageLeft(atlas, "leftWeaponDamage", mainShip, this));
        bonuses.add(new ButtonUpDamageRight(atlas, "rightWeaponDamage", mainShip, this));
        bonuses.add(new ButtonUpReload(atlas, "upReload", mainShip, this));
        bonuses.add(new ButtonUpHP(atlas, "upHP", mainShip, this));
    }

    private void addButtons() {
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(this);
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

    @Override
    public void render(float delta) {
        super.render(delta);
        checkCollisions();
        deleteAllDestroed();
    }

    @Override
    public void draw() {
        super.draw();
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        explosionPull.drawActiveObjects(batch);
        printInfo();
        switch (gameMode) {
            case Play:
                batch.setColor(1, 1, 1, 1);
                break;
            case LevelUp:
                bonuses.draw(batch);
                break;
            case GameOver:
                batch.setColor(1, 1, 1, 1);
                messageGameOver.draw(batch);
                buttonNewGame.draw(batch);
                btnExit.draw(batch);
                batch.setColor(blackoutDraw, blackoutDraw, blackoutDraw, blackoutDraw);
                break;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
                break;
            case GameOver:
                blackoutAnimation(delta);
                break;
        }
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbFrags.append(FRAGS).append(mainShip.getFrags());
        font.draw(batch, sbFrags, worldBounds.getLeft() + SIDE + 0.15f, worldBounds.getTop() - SIDE, Align.left);

        sbHP.setLength(0);
        sbHP.append(HP).append(mainShip.getHp());
        font.draw(batch, sbHP, 0, worldBounds.getBottom() + 0.05f + SIDE, Align.center);

        sbLevel.setLength(0);
        sbLevel.append(LEVEL).append(mainShip.getLevel());
        font.draw(batch, sbLevel, worldBounds.getRight() - SIDE, worldBounds.getTop() - SIDE, Align.right);
    }

    public void nextLevel() {
        gameMode = GameMode.LevelUp;
    }

    public void continueGame() {
        gameMode = GameMode.Play;
    }

    private void blackoutAnimation(float delta) {
        timerGameOver += delta;
        if (blackoutDraw > 0.003) {
            blackoutDraw -= 0.001;
        } else {
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
        timerGameOver = 0;
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
        switch (gameMode) {
            case Play:
                mainShip.touchDown(touch, pointer);
                break;
            case LevelUp:
                bonuses.touchDown(touch, pointer);
                break;
            case GameOver:
                if (timerGameOver > intervalGameOver) {
                    buttonNewGame.touchDown(touch, pointer);
                }
                break;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        switch (gameMode) {
            case Play:
                mainShip.touchDown(touch, pointer);
                break;
            case LevelUp:
                bonuses.touchUp(touch, pointer);
                break;
            case GameOver:
                if (timerGameOver > intervalGameOver) {
                    buttonNewGame.touchUp(touch, pointer);
                }
                break;
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        mainShip.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        bonuses.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPull.dispose();
        expSound.dispose();
        gameMusic.dispose();
        font.dispose();
    }
}
