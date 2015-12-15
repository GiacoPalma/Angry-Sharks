package se.palmatech.angrysharks.screens;

import se.palmatech.angrysharks.AngrySharks;
import se.palmatech.angrysharks.interfaces.IAdMob;
import se.palmatech.angrysharks.interfaces.IShare;
import se.palmatech.angrysharks.tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {
	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager manager;
	private AngrySharks game;
	private IShare twitter;
	private IAdMob admob;
	
	public Splash(AngrySharks game){
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
		
		manager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		manager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Texture splashTexture = new Texture("img/splash.png");
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(manager);//the tween animation for fading the splash in and out. the repeatyoYo repeats the animatikon in oppostie direction so it goes from 0 to 1 to 0.
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 1).setCallback(new TweenCallback(){
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				// TODO Auto-generated method stub
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
			}
		}).start(manager);
		manager.update(Float.MIN_VALUE);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}

}
