package se.palmatech.angrysharks.screens;

import se.palmatech.angrysharks.AngrySharks;
import se.palmatech.angrysharks.interfaces.IAdMob;
import se.palmatech.angrysharks.interfaces.IShare;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOver implements Screen {
	
	//ACHIEVEMENTS
	private static final String ACHIEVEMENT_SHARK_TAPPER = "CgkIsYzBroAXEAIQAg";
	private static final String ACHIEVEMENT_DOLPHIN_HERO = "CgkIsYzBroAXEAIQAw";

	private static final String ACHIEVEMENT_TIME_CONSUMER = "CgkIsYzBroAXEAIQBg";

	private Stage stage;//create a stage to put elements like "buttons and stuff"
	private TextureAtlas atlas; //the text button has a texture and its a picture not a color.
	private Skin skin; //skin is the appearance of our buttons and everything
	private Table table;
	private Table buttonTable;
	private ImageButton btnShare, btnRetry, btnLeader, btnAchievement;
	private Texture bg_text;
	private Music music;
	private AngrySharks game;
	private int score;
	private Label yourScore;
	private Label yourHighscore;
	private BitmapFont scoreFont;
	private BitmapFont highscoreFont;
	private int highscore;
	
	public GameOver(AngrySharks game, int score){
		music = Gdx.audio.newMusic(Gdx.files.internal("music/maintheme3.wav"));
		music.setLooping(true);
		this.game = game;
		this.score = score;
		
		FileHandle file = Gdx.files.local("savefile/highscore.txt");
		
		//Write
		
		
		//Read
		if(file.exists()){
		  highscore = Integer.valueOf(file.readString());
		  if(score > highscore){
				file.writeString(Integer.toString(score),false);
				if(game.actionResolver.getSignedInGPGS()){
					game.actionResolver.submitScoreGPGS(score);
				}
			}
		}else{
		  highscore = 0;
		  file.writeString(Integer.toString(score),false);
		}
		
		
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/MyriadPro-Bold.ttf"));
		scoreFont = generator.generateFont(Gdx.graphics.getHeight()/10);
		highscoreFont = generator.generateFont(Gdx.graphics.getHeight()/10);
		generator.dispose();
		
		
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.42f,0.67f,0.95f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(game.actionResolver.getSignedInGPGS()){
			if(score > 1000)game.actionResolver.unlockAchievementGPGS(ACHIEVEMENT_SHARK_TAPPER);
			if(score > 5000)game.actionResolver.unlockAchievementGPGS(ACHIEVEMENT_DOLPHIN_HERO);
			if(score > 10000)game.actionResolver.unlockAchievementGPGS(ACHIEVEMENT_TIME_CONSUMER);
		}
		stage.act(delta);//updates everything withing the stage
		stage.getSpriteBatch().begin();
		stage.getSpriteBatch().draw(bg_text, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getSpriteBatch().end();
		stage.draw();//draws everything within the stage
		
		//Table.drawDebug(stage);
				
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		bg_text = new Texture(Gdx.files.internal("img/gameover.png"));
		
		
		music.play();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//atlas = new TextureAtlas("ui/button.pack");
		//skin = new Skin(atlas);
		
		atlas = new TextureAtlas("ui/gameoverbtn.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		buttonTable = new Table(skin);
		
		table.size(Gdx.graphics.getWidth(), (Gdx.graphics.getHeight()/3)*2);
		buttonTable.size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
		
		stage.addActor(table);//add table to stage	
		stage.addActor(buttonTable);
		stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//creating fonts
		LabelStyle scoreStyle = new LabelStyle(scoreFont, Color.WHITE);
		yourScore = new Label("Your final score is: "+score, scoreStyle);
		
		LabelStyle highscoreStyle = new LabelStyle(scoreFont, Color.ORANGE);
		if(score > highscore){
			yourHighscore = new Label("Highscore: "+score, highscoreStyle);
		}else{
			yourHighscore = new Label("Highscore: "+highscore, highscoreStyle);
		}
		/*/creating the button
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button");//Using same button image right now for both up and down that's why getDrawwable is the same
		textButtonStyle.down = skin.getDrawable("button");
		textButtonStyle.pressedOffsetX = 1;//this moves the text right in the button 
		textButtonStyle.pressedOffsetY = -1;//this moves the text down in the button
		textButtonStyle.font = white;
		*/
		
		
		ImageButtonStyle imagebtnStyleShare = new ImageButtonStyle();
		imagebtnStyleShare.up = skin.getDrawable("sharebt1n");
		imagebtnStyleShare.down = skin.getDrawable("sharebt1n");
		
		ImageButtonStyle imagebtnStyleRetry = new ImageButtonStyle();
		imagebtnStyleRetry.up = skin.getDrawable("retry1");
		imagebtnStyleRetry.down = skin.getDrawable("retry1");
		
		ImageButtonStyle imagebtnStyleLeader = new ImageButtonStyle();
		imagebtnStyleLeader.up = skin.getDrawable("leaderboards");
		imagebtnStyleLeader.down = skin.getDrawable("leaderboards");
		
		ImageButtonStyle imagebtnStyleAchievement = new ImageButtonStyle();
		imagebtnStyleAchievement.up = skin.getDrawable("achievements");
		imagebtnStyleAchievement.down = skin.getDrawable("achievements");
		
		
		btnShare = new ImageButton(imagebtnStyleShare);
		btnShare.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//Twitter share
				if(game.twitter.isPC == false){
					game.twitter.share(score);
				}
			}
		});
		
		btnRetry = new ImageButton(imagebtnStyleRetry);
		btnRetry.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
			}
		});
		
		btnLeader = new ImageButton(imagebtnStyleLeader);
		btnLeader.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(game.actionResolver.getSignedInGPGS()){
					if(score > highscore){
						game.actionResolver.submitScoreGPGS(score);
					}
					game.actionResolver.getLeaderboardGPGS();
				}else{
					game.actionResolver.loginGPGS();
				}
			}
		});
		
		btnAchievement = new ImageButton(imagebtnStyleAchievement);
		btnAchievement.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(game.actionResolver.getSignedInGPGS()){
					game.actionResolver.getAchievementsGPGS();
				}else{
					game.actionResolver.loginGPGS();
				}
			}
		});
		//table.padTop(Gdx.graphics.getHeight()/10);
		//table.center();
		table.add(yourScore).width(Gdx.graphics.getWidth()/1.3f).height(Gdx.graphics.getHeight()/4).center();
		table.row();
		table.add(yourHighscore).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/4).center();
		//table.row();
		buttonTable.add(btnShare).width(Gdx.graphics.getWidth()/4).height(Gdx.graphics.getHeight()/6);//add twitter button to table
		//table.row();
		buttonTable.add(btnRetry).width(Gdx.graphics.getWidth()/4).height(Gdx.graphics.getHeight()/6);//add retry button to table
		//buttonTable.getCell(btnRetry).spaceTop(Gdx.graphics.getHeight()/10);
		buttonTable.row();
		buttonTable.add(btnLeader).width(Gdx.graphics.getWidth()/4).height(Gdx.graphics.getHeight()/6);
		buttonTable.add(btnAchievement).width(Gdx.graphics.getWidth()/4).height(Gdx.graphics.getHeight()/6);

		table.debug();//shows us where the cells start and end 
		table.setBounds(0, Gdx.graphics.getHeight()/3, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);
		buttonTable.getCell(btnShare).spaceRight(10);
		buttonTable.getCell(btnLeader).spaceRight(10);
		buttonTable.debug();
		
		this.game.admob.showAds(false);
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
		music.dispose();
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
