package ru.geekbrains.pools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.Enemy1;
import ru.geekbrains.sprites.ships.Enemy2;
import ru.geekbrains.sprites.ships.Enemy3;
import ru.geekbrains.sprites.ships.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {
    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private ExplosionPull explosionPull;
    private Sound shotSound;
    private Rect worldBounds;
    private MainShip mainShip;
    private int shipType;

    public EnemyPool(TextureAtlas atlas,
                     BulletPool bulletPool,
                     ExplosionPull explosionPull,
                     Sound shotSound,
                     Rect worldBounds,
                     MainShip mainShip) {
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.explosionPull = explosionPull;
        this.shotSound = shotSound;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public BulletPool getBulletPool() {
        return bulletPool;
    }

    public Sound getShotSound() {
        return shotSound;
    }

    public MainShip getMainShip() {
        return mainShip;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    @Override
    protected Enemy newObject() {
        switch (shipType) {
            case 1:
                return new Enemy1(atlas, bulletPool, explosionPull, shotSound, worldBounds, mainShip);
            case 2:
                return new Enemy2(atlas, bulletPool, explosionPull, shotSound, worldBounds, mainShip);
            case 3:
                return new Enemy3(atlas, bulletPool, explosionPull, shotSound, worldBounds, mainShip);
            default:
                return new Enemy1(atlas, bulletPool, explosionPull, shotSound, worldBounds, mainShip);
        }
    }
}
