package se.palmatech.angrysharks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Friend{

	private Vector2 position = new Vector2();
	private int width;
	private int height;
	private int lifecycle;
	private int maxLife;
	private boolean isDead = false;
	private boolean isHit = false;
	private Texture texture;
	private Rectangle bounds;
	private Rectangle touch_bounds;
	private TextureRegion currentFrame;
	private int clearHeight;
	private int clearRatio;
	private int startHeight;
	private ParticleEffect splash;
	private boolean hasAppeared = false;
	
	//animation varaibles
	private static final int ANIM_COLS = 2;
	private static final int ANIM_ROWS = 1;
	private float stateTime;
	
	private Animation animation;
	private TextureRegion[] frames;
	
	
	
	public Friend(Vector2 vecPos, int maxLife){
		   
			this.width = Gdx.graphics.getWidth()/6;
			this.height = Gdx.graphics.getHeight()/4;
			
		
			this.position.x = vecPos.x;
			this.position.y = vecPos.y;
			this.lifecycle = 0;
			this.maxLife = maxLife;
			this.texture = new Texture("img/dolphin.png");
			this.clearHeight = 0;//this.texture.getHeight();
			this.bounds = new Rectangle(this.position.x, this.position.y, this.width, this.height);
			this.clearRatio = this.texture.getHeight()/this.height;	
			this.startHeight = 0;
			
			//frames prep
			TextureRegion[][] tmp = TextureRegion.split(this.texture, this.texture.getWidth()/ANIM_COLS, this.getHeight()/ANIM_ROWS);
			this.frames = new TextureRegion[ANIM_COLS * ANIM_ROWS];
			int index = 0;
			for(int i = 0; i < ANIM_ROWS; i++){
				for (int j = 0; j < ANIM_COLS;j++){
					System.out.println(tmp[i][j]);
					this.frames[index++] = tmp[i][j];
				}
			}
			
			//prep animation
			this.animation = new Animation(0.2f, this.frames);
			this.stateTime = 0;
			
			
			//Particle effect
			this.splash = new ParticleEffect();
			this.splash.load(Gdx.files.internal("effect/water3.p"), Gdx.files.internal("img"));
			this.splash.setPosition(this.position.x+(this.width/2), this.position.y);
			this.splash.start();
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
	public Texture getTexture(){
		return this.texture;
	}
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public boolean isDead(){
		return this.isDead;
	}
	public boolean isHit(){
		return this.isHit;
	}
	
	public void appear(){
		if(this.clearHeight >= this.texture.getHeight()){
			hasAppeared = true;
		}else{
			this.clearHeight += (this.clearRatio*2)+8;
			
		}
		
		if(this.height <= this.startHeight){
			
		}else{
			this.startHeight +=10;
		}
	}
	
	public void disappear(){
		this.clearHeight -= this.clearRatio*2+8;
		this.startHeight -= 10;
		
		if(this.startHeight == 0){
			this.isDead = true;
		}
	}
	
	public void update(){
		if(hasAppeared == false){
			appear();
		}
		if(hasAppeared == true && lifecycle >= maxLife){
			disappear();
		}
		bounds.set(this.position.x, this.position.y, this.width, this.height);
		
		if(Gdx.input.isTouched()){
			touch_bounds = new Rectangle(Gdx.input.getX(), (Gdx.graphics.getHeight()-Gdx.input.getY()), 5, 5);
			System.out.println(bounds.overlaps(touch_bounds));
			
			
			if(bounds.overlaps(touch_bounds)){
				this.isHit = true;
				System.out.println("true");
			}
		}
		
			lifecycle++;
		
	}
	
	public void draw(SpriteBatch batch, float delta){
		stateTime += Gdx.graphics.getDeltaTime();
		this.currentFrame = this.animation.getKeyFrame(stateTime, true);
		this.currentFrame.setRegionHeight(this.clearHeight);
		batch.draw(this.currentFrame, this.position.x, this.position.y, 0, 0, this.width, this.startHeight, 1f, 1f, 0f);
		this.splash.draw(batch, delta);
	}
	
}
