package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpDamageLeft extends BaseBonus {

    public ButtonUpDamageLeft(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path, mainShip, gameScreen);
    }

    @Override
    public void doButtonAction() {
        mainShip.setLeftBulletDamage(mainShip.getLeftBulletDamage() + 1);
        gameScreen.continueGame();
    }

    public boolean show() {
        return (mainShip.getLevel() + 2) % 3 == 0 && mainShip.isLeftWeapon();
    }
}
