package ru.geekbrains.pools;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

//    @Override
//    protected void log() {
//        System.out.println("Bullet active/free: " + activeObjects.size() + "/" + freeObjects.size());
//    }
}
