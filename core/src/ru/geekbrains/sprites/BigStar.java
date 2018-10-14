package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class BigStar extends Sprite {
    private Vector2 v;
    private Rect wordBounds;

    public BigStar(TextureAtlas atlas, String name) {
        super(atlas, name);
        v = new Vector2(0,-0.02f);
        setHeightProportion(1);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.wordBounds = worldBounds;
        setRight(Rnd.nextFloat(0,1));
        setBottom(0.4f);
    }

    public void startNewGame(){
        setBottom(wordBounds.getTop());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }
}
