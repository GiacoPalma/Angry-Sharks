package se.palmatech.angrysharks.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAccessor implements TweenAccessor<Actor>{
	
	public static final int SCALE = 0, Y = 1, RGB = 2, ALPHA = 3;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case SCALE:
			returnValues[0] = target.getScaleX();
			returnValues[0] = target.getScaleY();
			return 2;
		case Y:
			returnValues[0] = target.getY();
			return 1;
		case RGB:
			returnValues[0] = target.getColor().r;
			returnValues[0] = target.getColor().g;
			returnValues[0] = target.getColor().b;
			return 3;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch(tweenType){
		case SCALE:
			target.setScale(newValues[0], newValues[1]);
			break;
		case Y:
			target.setY(newValues[0]);
			break;
		case RGB:
			target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
			break;
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}
	}

}
