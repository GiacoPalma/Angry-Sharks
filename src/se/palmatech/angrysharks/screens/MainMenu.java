package se.palmatech.angrysharks.screens;

import se.palmatech.angrysharks.AngrySharks;
import se.palmatech.angrysharks.tween.ActorAccessor;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen{
	
	private Stage stage;//create a stage to put elements like "buttons and stuff"
	private TextureAtlas atlas; //the text button has a texture and its a picture not a color.
	private Skin skin; //skin is the appearance of our buttons and everything
	private Table table;
	private ImageButton btnPlay, btnExit;
	private Image heading;
	private TweenManager manager;
	private Texture bg_text;
	private Music music;
	private Texture title;
	private AngrySharks game;
	
	//private OrthographicCamera cam;
	public MainMenu(AngrySharks game){
		music = Gdx.audio.newMusic(Gdx.files.internal("music/maintheme3.wav"));
		music.setLooping(true);
		title = new Texture("ui/title.png");
		this.game = game;
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.42f,0.67f,0.95f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.act(delta);//updates everything withing the stage
		stage.getSpriteBatch().begin();
		stage.getSpriteBatch().draw(bg_text, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getSpriteBatch().end();
		stage.draw();//draws everything within the stage
		
		
		//Table.drawDebug(stage);
		
		
		
		manager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	
	}

	@Override
	public void show() {
		
		//cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Background
		bg_text = new Texture(Gdx.files.internal("img/titlescreen4.png"));
		
		
		music.play();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//atlas = new TextureAtlas("ui/button.pack");
		//skin = new Skin(atlas);
		
		atlas = new TextureAtlas("ui/buttons.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		table.left();
		table.padLeft(10);
		table.setFillParent(true);//container for buttons and so on
		
		//creating fonts
		
		/*/creating the button
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button");//Using same button image right now for both up and down that's why getDrawwable is the same
		textButtonStyle.down = skin.getDrawable("button");
		textButtonStyle.pressedOffsetX = 1;//this moves the text right in the button 
		textButtonStyle.pressedOffsetY = -1;//this moves the text down in the button
		textButtonStyle.font = white;
		*/
		ImageButtonStyle imagebtnStylePL = new ImageButtonStyle();
		imagebtnStylePL.up = skin.getDrawable("playbtn");
		imagebtnStylePL.down = skin.getDrawable("playbtn");
		
		ImageButtonStyle imagebtnStyleEX = new ImageButtonStyle();
		imagebtnStyleEX.up = skin.getDrawable("exitbtn");
		imagebtnStyleEX.down = skin.getDrawable("exitbtn");
		
		/*buttonExit = new TextButton("EXIT", textButtonStyle);
		//buttonExit.setBounds(0,  0, 20, 20);
		System.out.println(Gdx.graphics.getHeight());
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		//buttonExit.pad(20);

		buttonPlay = new TextButton("PLAY", textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
			}
		});
		//buttonPlay.pad(20);
		*/
		btnPlay = new ImageButton(imagebtnStylePL);
		btnPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
			}
		});
		
		btnExit = new ImageButton(imagebtnStyleEX);
		btnExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		
		//creating heading
		heading = new Image(title);
		heading.setFillParent(false);
		//heading.setWidth(300f);
		//heading.setHeight(200f);
		//heading.setScale(0.5f);
		
		table.add(heading).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/4);//add heading to table
		table.getCell(heading).spaceBottom(60);//get heading and set space at the bottom
		table.row();//create new row
		table.add(btnPlay).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/5);//add play button
		table.row();
		table.add(btnExit).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/5);//add exit button to table
		table.debug();//shows us where the cells start and end 
		stage.addActor(table);//add table to stage
		
		//create animations
		manager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		//start animations. create a timeline that does paralelle animations to differnet objects
		//heading color animation
		
		//Tween.set(table, ActorAccessor.SCALE).target(0, 0);
		
		
		//heading and button fade-in
		Timeline.createParallel().beginParallel()
			.push(Tween.from(heading, ActorAccessor.ALPHA, 1.5f).target(0))
			.push(Tween.from(btnPlay, ActorAccessor.ALPHA, 1.5f).target(0))
			.push(Tween.from(btnExit, ActorAccessor.ALPHA, 1.5f).target(0))
			.end().start(manager);//end is to show where the timeline ends and start is to start the timeline animation
		
		//table fade-in
		Tween.from(table, ActorAccessor.ALPHA, 1.5f).target(0).start(manager);
		Tween.from(table, ActorAccessor.Y, 1.5f).target(Gdx.graphics.getHeight()/16).repeatYoyo(Tween.INFINITY, 0).start(manager);
		
		//scale
		//Tween.to(heading, ActorAccessor.SCALE, 2.0f).target(0.5f, 0.5f).repeatYoyo(Tween.INFINITY, 0).start(manager);
		
		manager.update(Float.MIN_VALUE);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		music.pause();
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		music.play();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		music.dispose();
		
	}

}
