package se.palmatech.angrysharks.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite> {

	public static final int ALPHA = 0;
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType){
			case ALPHA:
				returnValues[0] = target.getColor().a; //we set the value here. and use it below as newValues[0]
				return 1;//return as many  as returnValues added. e.g if we have another returnValues[1] below the first we return 2;
			default:
				assert false;
				return -1;
		}
		
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType){
			case ALPHA:
				target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
				break;
			default:
				assert false;
		}
	}

}
