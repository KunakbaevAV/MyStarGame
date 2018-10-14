package ru.geekbrains.sprites.buttons.bonuses;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.PanelObjects;
import ru.geekbrains.math.Rect;

public class PanelBonuses extends PanelObjects<BaseBonus> {
    private float currenPosY;

    public PanelBonuses(Rect worldBounds) {
        super(worldBounds);
        currenPosY = worldBounds.getTop();
    }

    @Override
    public void draw(SpriteBatch batch) {
        currenPosY = worldBounds.getTop();
        for (int i = 0; i < getAll().size(); i++) {
            if (get(i).show()){
                get(i).setTop(currenPosY);
                get(i).draw(batch);
                currenPosY -= 0.15f;
            }
        }
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        for (BaseBonus bonus: getAll()) {
            if (bonus.show()) bonus.touchDown(touch, pointer);
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        for (BaseBonus bonus: getAll()) {
            if (bonus.show()) bonus.touchUp(touch, pointer);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
