package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Panel extends Sprite {
    private List<Sprite> sprites;
    private int squareSide;

    public Panel(TextureRegion region) {
        super(region);
//        this.sprites.addAll(sprites);
//        squareSide = (int)Math.ceil(Math.sqrt(sprites.size()));
    }

//    @Override
//    public String toString() {
//        return "squareSide=" + squareSide + Arrays.toString(sprites.toArray());
//    }
}
