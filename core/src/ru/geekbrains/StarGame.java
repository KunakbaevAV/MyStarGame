package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekbrains.screen.BaseGameScreen;
import ru.geekbrains.screen.MenuScreen;
import ru.geekbrains.screen.TestScreen;

public class StarGame extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		setScreen(new TestScreen(this));
	}
}
