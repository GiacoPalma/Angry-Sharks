package se.palmatech.angrysharks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

//Class for the score that pops up when hitting a shark
public class ScorePop {
	private Texture texture;
	private Sprite sprite;
	private Vector2 position;
	private int width;
	private int height;
	private float alpha;
	private float drawY;
	private int multiplier;
	
	//savedPos which is exdactly the enemy position to give back later for the spawn position array
	private Vector2 savedPosition;
	
	//variable to check when the animation is done so that that position becomes available for spawn afterwards
	public boolean done = false;
	
	public ScorePop(int width, int height){
		texture = new Texture("img/score_pop_1.png");
		sprite = new Sprite(texture);
		this.width = width/2;
		this.height = height/4;
		sprite.setSize(this.width, this.height);
		this.alpha = 0;
		this.position = new Vector2();
		this.savedPosition = new Vector2();
	}

	public void setMultiplier(int multi){
		multiplier = multi;
		texture = new Texture("img/score_pop_"+multi+".png");
		sprite.setTexture(texture);
	}
	public void setPosition(Vector2 position){
		this.savedPosition = position;
		this.position.x = (position.x)+(this.width/2);
		this.position.y = (position.y)+(this.height/2);
		drawY = position.y;
	}
	
	public Vector2 getPosition(){
		return this.savedPosition;
	}
	
	
	public void draw(SpriteBatch batch){
		sprite.setPosition(position.x, drawY);
		sprite.draw(batch, alpha);
		//batch.setColor(1, 1, 1, alpha);
		//batch.draw(texture, position.x, drawY, width, height);
	}
	
	public void update(){
		if(alpha < 1){
			alpha += .03f;
		}
		if(alpha >= 1){
			this.done = true;
		}
		drawY++;
	}
}
