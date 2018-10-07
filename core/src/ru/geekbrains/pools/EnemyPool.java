package ru.geekbrains.pools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {
    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private ExplosionPull explosionPull;
    private Rect worldBounds;
    private MainShip mainShip;

    public EnemyPool(TextureAtlas atlas,
                     BulletPool bulletPool,
                     ExplosionPull explosionPull,
                     Rect worldBounds,
                     MainShip mainShip) {
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.explosionPull = explosionPull;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public BulletPool getBulletPool() {
        return bulletPool;
    }

    public MainShip getMainShip() {
        return mainShip;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(atlas, "shipEnemy1", explosionPull, bulletPool, worldBounds, mainShip);
    }
}
