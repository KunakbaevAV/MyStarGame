package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {
    //    private String[] stars = {"star", "star2", "star3"};
    private Vector2 v;
    private Rect wordBounds;

    public Star(TextureAtlas atlas, String path, float min, float max) {
        super(atlas, path);
        float random = Rnd.nextFloat(min, max);
        setHeightProportion(random);
        v = new Vector2(Rnd.nextFloat(-0.005f, 0.005f), random * -1);
    }

    private void checkAndHandleBounds() {
        if (getLeft() > wordBounds.getRight()) setRight(wordBounds.getLeft());
        if (getRight() < wordBounds.getLeft()) setLeft(wordBounds.getRight());
        if (getTop() < wordBounds.getBottom()) setBottom(wordBounds.getTop());
        if (getBottom() > wordBounds.getTop()) setTop(wordBounds.getBottom());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.wordBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        setPosition(posX, posY);
    }
}

