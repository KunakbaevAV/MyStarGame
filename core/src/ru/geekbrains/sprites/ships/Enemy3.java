package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.BulletPool;

public class Enemy3 extends Enemy {

    public Enemy3(TextureAtlas atlas,
                  BulletPool bulletPool,
                  Sound shotSound,
                  Rect worldBonds,
                  MainShip mainShip) {
        super(atlas,
                "shipEnemy3", // название корабля
                bulletPool,
                shotSound,
                worldBonds,
                mainShip);
        setHeightProportion (0.4f); // размеры корабля
        setEnemyShipV       (0, -0.03f); // скорость корабля
        this.bulletRegion = atlas.findRegion("shotEnemy2"); // название снаряда
        bulletHeight =      0.2f; // размеры снаряда
        bulletV.set         (0, -0.3f); // скорость снаряда
        bulledDamage =      3; // урон
        reloadInterval =    4f; // перезарядка
        hp =                2; // запас прочности
        reloadTimer = reloadInterval;
        generatePos();
    }
}
