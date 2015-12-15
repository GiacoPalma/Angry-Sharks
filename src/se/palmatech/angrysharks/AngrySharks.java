package se.palmatech.angrysharks;

import se.palmatech.angrysharks.interfaces.IActionResolver;
import se.palmatech.angrysharks.interfaces.IAdMob;
import se.palmatech.angrysharks.interfaces.IShare;
import se.palmatech.angrysharks.screens.Splash;

import com.badlogic.gdx.Game;

public class AngrySharks extends Game {
	/*private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	*/
	public IAdMob admob;
	public IShare twitter;
	public IActionResolver actionResolver;
	
	public AngrySharks(IShare twitter, IAdMob admob, IActionResolver actionResolver){
		this.twitter = twitter;
		this.admob = admob;
		this.actionResolver = actionResolver;
	}
	
	
	@Override
	public void create() {	
		setScreen(new Splash(this));
		/*
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		*/
	}

	@Override
	public void dispose() {
		super.dispose();
		/*
		batch.dispose();
		texture.dispose();
		*/
	}

	@Override
	public void render() {
		super.render();
		/*
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		*/
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();	
	}

	@Override
	public void resume() {
		super.resume();
	}
}
