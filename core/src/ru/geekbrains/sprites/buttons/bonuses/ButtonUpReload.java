package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpReload extends BaseBonus {

    public ButtonUpReload(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path, mainShip, gameScreen);
    }

    @Override
    public void doButtonAction() {
        float reload = mainShip.getReloadInterval();
        mainShip.setReloadInterval(reload - (reload/10*1));
        gameScreen.continueGame();
    }

    public boolean show(){
        return mainShip.getLevel() % 2 == 0;
    }
}
