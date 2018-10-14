package ru.geekbrains.screen;

import com.badlogic.gdx.Game;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.PanelObjects;
import ru.geekbrains.sprites.buttons.ButtonTest;

public class TestScreen extends BaseGameScreen {
    PanelObjects<Sprite> buttons;

    public TestScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        buttons = new PanelObjects<Sprite>(this.worldBounds);
        buttons.add(new ButtonTest(atlas, "upHP"));
        buttons.add(new ButtonTest(atlas, "upDamage"));
        buttons.add(new ButtonTest(atlas, "upReload"));
    }

    @Override
    public void draw() {
        super.draw();
        buttons.draw(batch);
    }
}
