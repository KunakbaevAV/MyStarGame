package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonLeftWeapon extends BaseBonus{

    public ButtonLeftWeapon(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path, mainShip, gameScreen);
    }

    @Override
    public void doButtonAction() {
        mainShip.setLeftWeapon(true);
        gameScreen.continueGame();
    }

    public boolean show(){
        return mainShip.getLevel() % 5 == 0 && !mainShip.isLeftWeapon();
    }
}
