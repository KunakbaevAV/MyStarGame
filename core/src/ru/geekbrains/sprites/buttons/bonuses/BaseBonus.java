package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public abstract class BaseBonus extends AnimatedButton {
    MainShip mainShip;
    GameScreen gameScreen;

    BaseBonus(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path);
        this.mainShip = mainShip;
        this.gameScreen = gameScreen;
        setHeightProportion(0.2f);
    }

    public abstract boolean show();
}
