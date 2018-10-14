package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprites.ships.MainShip;

public class ButtonTest extends AnimatedButton {
    private MainShip mainShip;
    private GameScreen gameScreen;

    public ButtonTest(TextureAtlas atlas, String path) {
        super(atlas, path);
//        this.mainShip = mainShip;
//        this.gameScreen = gameScreen;
        setHeightProportion(0.2f);
    }

    @Override
    public void doButtonAction() {
        System.out.println(this.toString() + " нажата");
    }
}
