package ru.geekbrains.math;

import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.sprites.ships.Enemy;

public class EnemiesEmitter {
    private final EnemyPool enemyPool;
    private float generateInterval = 4f;
    private float generateTimer;

    public EnemiesEmitter(EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
    }

    public void generateEnemies(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0;
            Enemy enemy = enemyPool.obtain(); // добавить корабль
            enemy.setShip(getEnemyType());
        }

    }

    private int getEnemyType() {
        float type = (float) Math.random();
        if(type < 0.3f){
            return 1;
        }else if (type < 0.6f){
            return 2;
        }else if (type < 0.8f){
            return 3;
        }else if (type < 0.9){
            return 4;
        }else {
            return 5;
        }
    }
}
