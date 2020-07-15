package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpDamage extends AnimatedButton {
    private MainShip mainShip;
    private GameScreen gameScreen;

    public ButtonUpDamage(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path);
        this.mainShip = mainShip;
        this.gameScreen = gameScreen;
        setHeightProportion(0.2f);
    }

    @Override
    public void doButtonAction() {
        mainShip.setBulledDamage(mainShip.getBulledDamage() + 1);
        mainShip.setBulletHeight(mainShip.getBulletHeight() + 0.03f);
        gameScreen.continueGame();
    }
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(0.3f);
    }
}
