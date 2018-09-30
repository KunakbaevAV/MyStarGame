package ru.geekbrains.pools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.Enemy1;
import ru.geekbrains.sprites.ships.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {
    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private Sound shotSound;
    private MainShip mainShip;

    public EnemyPool(TextureAtlas atlas,
                     BulletPool bulletPool,
                     Sound shotSound,
                     MainShip mainShip) {
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.shotSound = shotSound;
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

    @Override
    protected Enemy newObject() {
        return new Enemy1(atlas, bulletPool, shotSound, mainShip);
    }
}
