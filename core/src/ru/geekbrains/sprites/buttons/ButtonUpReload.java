package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonUpReload extends AnimatedButton {
    private MainShip mainShip;
    private GameScreen gameScreen;

    public ButtonUpReload(TextureAtlas atlas, String path, MainShip mainShip, GameScreen gameScreen) {
        super(atlas, path);
        this.mainShip = mainShip;
        this.gameScreen = gameScreen;
        setHeightProportion(0.2f);
    }

    @Override
    public void doButtonAction() {
        float reload = mainShip.getReloadInterval();
        mainShip.setReloadInterval(reload - (reload/10*1));
        gameScreen.continueGame();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(-0.1f);
    }
}
