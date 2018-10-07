package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpHP extends AnimatedButton {
    private MainShip mainShip;
    private GameScreen gameScreen;

    public ButtonUpHP(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path);
        this.mainShip = mainShip;
        this.gameScreen = gameScreen;
        setHeightProportion(0.2f);
    }

    @Override
    public void doButtonAction() {
        mainShip.setHp(mainShip.getHp() + 20);
        gameScreen.continueGame();
    }
}
