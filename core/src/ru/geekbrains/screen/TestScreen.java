package ru.geekbrains.screen;

import com.badlogic.gdx.Game;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.ScreenObjects;
import ru.geekbrains.sprites.buttons.ButtonTest;
import ru.geekbrains.sprites.buttons.ButtonUpHP;

public class TestScreen extends BaseGameScreen {
    ScreenObjects<Sprite> buttons;

    public TestScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        buttons = new ScreenObjects<Sprite>(this.worldBounds);
        buttons.objects.add(new ButtonTest(atlas, "upHP"));
        buttons.objects.add(new ButtonTest(atlas, "upDamage"));
        buttons.objects.add(new ButtonTest(atlas, "upReload"));
    }

    @Override
    public void draw() {
        super.draw();
        buttons.draw(batch);
    }
}
