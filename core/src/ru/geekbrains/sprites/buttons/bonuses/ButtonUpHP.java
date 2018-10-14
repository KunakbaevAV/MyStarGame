package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpHP extends BaseBonus {

    public ButtonUpHP(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path, mainShip, gameScreen);
    }

    @Override
    public void doButtonAction() {
        mainShip.setHp(mainShip.getHp() + 10 + mainShip.getLevel());
        gameScreen.continueGame();
    }

    public boolean show(){
        return true;
    }
}
