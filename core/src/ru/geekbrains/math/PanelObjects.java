package ru.geekbrains.math;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.base.Sprite;

public class PanelObjects<T extends Sprite> extends Rect {
    private List<T> objects = new ArrayList<T>();
    public Rect worldBounds;

    public PanelObjects(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void add(T obj){
        objects.add(obj);
    }

    public T get(int index){
        return objects.get(index);
    }

    public List<T> getAll() {
        return objects;
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).setTop(worldBounds.getTop() - 0.15f * i);
            objects.get(i).draw(batch);
        }
    }

    public void touchDown(Vector2 touch, int pointer) {
        for (T object : objects) {
            object.touchDown(touch, pointer);
        }
    }

    public void touchUp(Vector2 touch, int pointer) {
        for (T object : objects) {
            object.touchUp(touch, pointer);
        }
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
    }

}
