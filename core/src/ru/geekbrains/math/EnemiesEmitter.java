package ru.geekbrains.math;

import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.MainShip;

public class EnemiesEmitter {
    private final EnemyPool enemyPool;
    private float generateInterval = 4f;
    private float generateTimer;
    private MainShip mainShip;

    public EnemiesEmitter(EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
        this.mainShip = enemyPool.getMainShip();
    }

    public void generateEnemies(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0;
            Enemy enemy = enemyPool.obtain(); // добавить корабль
            enemy.setShip(getEnemyTypeByLevel());
            updateGenerateInterval();
        }
    }

    private void updateGenerateInterval() {
        generateInterval = 3 / mainShip.getLevel() + 1;
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

    private int getEnemyTypeByLevel() {
        int type = getEnemyType();
        if (type > mainShip.getLevel()) return mainShip.getLevel();
        return type;
    }
}
