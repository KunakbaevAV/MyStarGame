package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.BulletPool;

public class Enemy1 extends Enemy {

    public Enemy1(TextureAtlas atlas, BulletPool bulletPool, Sound shotSound, MainShip mainShip) {
        super(atlas, "shipEnemy1", bulletPool, shotSound, mainShip);
        setHeightProportion(0.2f);
        v0 = new Vector2(0, -0.2f);
        bulletHeight = 0.1f;
        bulletV.set(0,-0.3f);
        bulledDamage = 1;
        reloadInterval = 3;
        hp = 1;
    }
}
