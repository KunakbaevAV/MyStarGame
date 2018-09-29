package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;

public class ButtonChooseShip extends AnimatedButton {
    private String path;
    public ButtonChooseShip(TextureAtlas atlas, String path) {
        super(atlas, path);
        this.path = path;
    }

    @Override
    public void doButtonAction() {
        System.out.println("Выбран корабль " + path);
    }
}
