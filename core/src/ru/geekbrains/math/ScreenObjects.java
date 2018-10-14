package ru.geekbrains.math;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.base.Sprite;

public class ScreenObjects<T extends Sprite> extends Rect {
    public List<T> objects = new ArrayList<T>();
    private Rect worldBounds;

    public ScreenObjects(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void draw(SpriteBatch batch){
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).setTop(worldBounds.getTop() - 0.15f * i);
            objects.get(i).draw(batch);
        }
    }
}
