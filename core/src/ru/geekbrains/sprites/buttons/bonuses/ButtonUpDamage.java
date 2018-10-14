package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpDamage extends BaseBonus {

    public ButtonUpDamage(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path, mainShip, gameScreen);
    }

    @Override
    public void doButtonAction() {
        mainShip.setBulledDamage(mainShip.getBulledDamage() + 1);
        gameScreen.continueGame();
    }

    public boolean show(){
        return (mainShip.getLevel() + 1) % 3 == 0;
    }
}
