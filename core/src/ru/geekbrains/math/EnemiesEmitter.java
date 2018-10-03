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
            getEnemyType(enemyPool); // выбор типа корабля
            enemyPool.obtain(); // добавить корабль
        }
    }

    private void getEnemyType(EnemyPool enemyPool) {
        float type = (float) Math.random();
        if(type < 0.5f){
            enemyPool.setShipType(1);
        }else if (type < 0.8f){
            System.out.println(2);
            enemyPool.setShipType(2);
        }else {
            enemyPool.setShipType(3);
        }
    }
}
