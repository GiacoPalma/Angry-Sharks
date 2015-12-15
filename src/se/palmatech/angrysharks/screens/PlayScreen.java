package se.palmatech.angrysharks.screens;

import java.util.ArrayList;
import java.util.Iterator;

import se.palmatech.angrysharks.AngrySharks;
import se.palmatech.angrysharks.Enemy;
import se.palmatech.angrysharks.Friend;
import se.palmatech.angrysharks.ScorePop;
import se.palmatech.angrysharks.SpawnPositions;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class PlayScreen implements Screen {
	private SpriteBatch batch;
	private Sprite background;
	private Sprite readyBackground;
	private Sprite gameoverBackground;
	private TweenManager manager;
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Array<Friend> friends = new Array<Friend>();
	private Array<ScorePop> scorePops = new Array<ScorePop>();
	
	private Iterator<Friend> friendIterator;
	private Iterator<ScorePop> scorePopIterator;
	
	private int enemySpawnTime;
	private int friendSpawnTime;
	private int spawnTimer;
	private int frSpawnTimer;
	private AngrySharks game;
	private BitmapFont scoreFont;
	private BitmapFont comboFont;
	private int score;
	private int points;
	private int multiplier;
	private int enemiesCombo;
	private String scoreString;
	private String timeElapsed;
	private Enemy enemyData;
	private SpawnPositions spawnpos;
	private int lifecycle;
	private Sound success;
	private Sound fail;
	private float timer;
	private enum GameState {READY, RUNNING, PAUSED, GAMEOVER};
	private GameState state;
	private Music music;
	private ScorePop scorePop;
	
	//ACHIEVEMENTS
	private static final String ACHIEVEMENT_SHARK_ATTACK = "CgkIsYzBroAXEAIQBA";
	private static final String ACHIEVEMENT_TAP_MASTER = "CgkIsYzBroAXEAIQBQ";
	
	//READY STATE
	private Stage stage;
	private Table table;
	private Label tap;
	
	//LIVES
	private int lives;
	private int remainingLives;
	private Texture livesTexture;
	private Texture lostLifeTexture;
	
	public PlayScreen(AngrySharks game){
		this.game = game;
		
	
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/maintheme3.wav"));
		music.setLooping(true);
		score = 0;
		scoreString = "Score: ";
		timeElapsed = "";
		
		enemyData = new Enemy(new Vector2(0,0), 200);
		scorePop = new ScorePop(enemyData.getWidth(), enemyData.getHeight());
		multiplier = 1;
		enemiesCombo = 0;
		points = 10;
		spawnpos = new SpawnPositions(enemyData);
		enemySpawnTime = 200;
		friendSpawnTime = 300;
		spawnTimer = 200;
		frSpawnTimer = 200;
		lifecycle = 200;
		timer = 0;
		success = Gdx.audio.newSound(Gdx.files.internal("sound/success.wav"));
		fail = Gdx.audio.newSound(Gdx.files.internal("sound/fail.wav"));
		
		//LIVES
		lives = 3;
		remainingLives = 3;
		livesTexture = new Texture(Gdx.files.internal("img/shark.png"));
		lostLifeTexture = new Texture(Gdx.files.internal("img/shark_lost.png"));
		
		//READY STATE STAGE 
		stage = new Stage();
		table = new Table();
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/MyriadPro-Bold.ttf"));
		scoreFont = generator.generateFont(Gdx.graphics.getHeight()/10);
		comboFont = generator.generateFont(Gdx.graphics.getHeight()/10);
		
		generator.dispose();
		
		LabelStyle tapStyle = new LabelStyle(scoreFont, Color.WHITE);
		tap = new Label("TAP WHEN READY!", tapStyle);
		tap.setFillParent(true);
		tap.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/5);
		table.add(tap).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/5);
		table.setFillParent(true);
		table.debug();
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta) {
		
		switch(state){
		case READY:
			updateReady();
			break;
		case RUNNING:
			updateRunning(delta);
			break;
		case PAUSED:
			updatePause();
			break;
		case GAMEOVER:
			updateGameOver();
			break;
		}
		//updateRunning(delta);
	}
	
	public void updateRunning(float delta){
		Gdx.gl.glClearColor(0.42f,0.67f,0.95f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		manager.update(delta);
		friendIterator = friends.iterator();
		scorePopIterator = scorePops.iterator();
		//System.out.println("friends = "+friends.size);
		//spawntimer goes up every loop iteration and when it hits a certain number we spawn a new enemy
		spawnTimer++;
		frSpawnTimer++;
		if(frSpawnTimer >= friendSpawnTime){
			if(spawnpos.hasPos()){
				System.out.println("spawn friend");
				frSpawnTimer = 0;
				Vector2 spawnPosi = spawnpos.getRandomPos();
				Friend friend = new Friend(spawnPosi, lifecycle);
				friends.add(friend);
				//increase spawn rate and reduce maxlife
				if(lifecycle > 5 && friendSpawnTime > 30){
					lifecycle -= 5;
					friendSpawnTime -= 20;
				}
			}
		}

		if(spawnTimer >= enemySpawnTime){
			if(spawnpos.hasPos()){
				
				System.out.println("spawn Enemy");
				spawnTimer = 0;
				
				//Check if score is higher than 5000. if it is then spawn two sharks at once
				
					Enemy enemy_spawn = new Enemy(spawnpos.getRandomPos(), lifecycle);
					enemies.add(enemy_spawn);
				
				
				//reduce maxlife
				lifecycle -= 5;
				if(lifecycle <= 0){
					lifecycle = 0;
				}
				
				//increase spawn rate 
				if(score > 5000){
					enemySpawnTime = 30;
				}
				if(enemySpawnTime > 50){
					enemySpawnTime -= 20;
				}
			}
		}
		
		//check if all enemies and friends are gone but spawnpos has no positions left, if so fill spawnpos with new positions
		if(spawnpos.hasPos() == false && enemies.size() <= 0 && friends.size <= 0){
			spawnpos = new SpawnPositions(enemyData);
		}
		//iterate arrays and update every enemy and friend. check for dead and hit
		if(enemies.size() > 0){
			for (int i = 0; i < enemies.size();i++){
				enemies.get(i).update(delta);
				if(enemies.get(i).isDead()){
					spawnpos.addNewPos(enemies.get(i).getPosition());
					enemiesCombo = 0;
					multiplier = 1;
					remainingLives -= 1;
					fail.play();
					if(remainingLives <= 0){
						state = GameState.GAMEOVER;
					}
					enemies.remove(i);
					continue;
				}
				if(enemies.get(i).isHit()){
					enemiesCombo++;
					if(enemiesCombo == 5){
						multiplier = 2;
						points = 10 * multiplier;
						
					}
					if(enemiesCombo == 10){
						multiplier = 3;
						points = 10 * multiplier;
					}
					if(enemiesCombo == 15){
						multiplier = 4;
						points = 10 * multiplier;
					}
					if(enemiesCombo == 20){
						if(game.actionResolver.getSignedInGPGS()){
							game.actionResolver.unlockAchievementGPGS(ACHIEVEMENT_SHARK_ATTACK);
						}
						multiplier = 5;
						points = 10 * multiplier;
					}
					if(enemiesCombo == 50){
						if(game.actionResolver.getSignedInGPGS()){
						game.actionResolver.unlockAchievementGPGS(ACHIEVEMENT_TAP_MASTER);
						}
					}
					
					success.play();
					ScorePop spop = new ScorePop(enemies.get(i).getWidth(), enemies.get(i).getHeight());
					spop.setPosition(enemies.get(i).getPosition());
					spop.setMultiplier(multiplier);
					scorePops.add(spop);
					
					enemies.remove(i);
					score = score+points;
					continue;
					
				}
			}
		}
		while(friendIterator.hasNext()){

			Friend friend = friendIterator.next();
			friend.update();
			if(friend.isDead()){
				spawnpos.addNewPos(friend.getPosition());
				friendIterator.remove();
			}
			if(friend.isHit()){
				fail.play();
				spawnpos.addNewPos(friend.getPosition());
				friendIterator.remove();
				state = GameState.GAMEOVER;
			}
		}
		
		while(scorePopIterator.hasNext()){
			ScorePop pop = scorePopIterator.next();
			pop.update();
			if(pop.done){
				spawnpos.addNewPos(pop.getPosition());
				scorePopIterator.remove();
			}
		}
		
		
		batch.begin();
		background.draw(batch);
		scoreString = "Score: "+score;
		scoreFont.setColor(1f, 0.45f, 0f, 1f);
		scoreFont.draw(batch, scoreString, 5, (Gdx.graphics.getHeight()));
		comboFont.setColor(1f, 0.45f, 0f, 1f);
		comboFont.draw(batch, "Combo: x"+multiplier, Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/3), Gdx.graphics.getHeight());
		for(int i = 0; i < friends.size;i++){

			friends.get(i).draw(batch, delta);
		}
		for(int i = 0; i < enemies.size();i++){
			enemies.get(i).draw(batch);
		}
		for(int i = 0; i < scorePops.size;i++){
			scorePops.get(i).draw(batch);
		}
		//draw lives depending on how many remain
		int liveHeight = Gdx.graphics.getHeight()/10;
		int liveWidth = Gdx.graphics.getWidth()/20;
		if(remainingLives == lives){
			batch.draw(livesTexture, Gdx.graphics.getWidth()/2-liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(livesTexture, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(livesTexture, Gdx.graphics.getWidth()/2+liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
		}
		if(remainingLives == lives-1){
			batch.draw(lostLifeTexture, Gdx.graphics.getWidth()/2-liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(livesTexture, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(livesTexture, Gdx.graphics.getWidth()/2+liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
		}
		if(remainingLives == lives-2){
			batch.draw(lostLifeTexture, Gdx.graphics.getWidth()/2-liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(lostLifeTexture, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(livesTexture, Gdx.graphics.getWidth()/2+liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			}
		if(remainingLives == lives-3){
			batch.draw(lostLifeTexture, Gdx.graphics.getWidth()/2-liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(lostLifeTexture, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
			batch.draw(lostLifeTexture, Gdx.graphics.getWidth()/2+liveWidth, Gdx.graphics.getHeight()-liveHeight, liveWidth, liveHeight);
		}
		
		
		batch.end();
		
		/*for(int i = 0; i< enemies.size();i++){
			sr.rect(enemies.get(i).getPosition().x, enemies.get(i).getPosition().y, enemies.get(i).getWidth(), enemies.get(i).getHeight());
		}*/
	}
	
	public void updatePause(){
		batch.begin();
		scoreFont.draw(batch, "PAUSED", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		batch.end();
	}
	
	public void updateReady(){
		Gdx.gl.glClearColor(0.42f,0.67f,0.95f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(readyBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		if(Gdx.input.isTouched()){
			state = GameState.RUNNING;
		}
	}
	
	public void updateGameOver(){
		
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver(game, score));
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		//SHOW ADS
		this.game.admob.showAds(true);
		
		state = GameState.READY;
		batch = new SpriteBatch();
		manager = new TweenManager();
		music.play();
		
		//GAME OVER BACKGROUND
		Texture goTexture = new Texture("img/gameover.png");
		gameoverBackground = new Sprite(goTexture);
		gameoverBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//RUNNING BACKGROUND
		Texture bgTexture = new Texture("img/background.png");
		background = new Sprite(bgTexture);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//READY BACKGROUND
		Texture readyTexture = new Texture("img/bg_ready.png");
		readyBackground = new Sprite(readyTexture);
		readyBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//PAUSE MENU
		
		
		
		
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
		batch.dispose();
		background.getTexture().dispose();
		readyBackground.getTexture().dispose();
		success.dispose();
		fail.dispose();
		scoreFont.dispose();
		stage.dispose();
		music.dispose();
	}

}
