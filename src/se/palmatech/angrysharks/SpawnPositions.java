package se.palmatech.angrysharks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpawnPositions {
	
	private Array<Vector2> positions;
	
	public SpawnPositions(Enemy enemyData){
		positions = new Array<Vector2>();
		
		//row1
		Vector2 spawnPos1 = new Vector2(2, enemyData.getHeight());
		Vector2 spawnPos2 = new Vector2(spawnPos1.x+enemyData.getWidth()+2, spawnPos1.y);
		Vector2 spawnPos3 = new Vector2(spawnPos2.x+enemyData.getWidth()+2, spawnPos1.y);
		Vector2 spawnPos7 = new Vector2(spawnPos3.x+enemyData.getWidth()+2, spawnPos1.y);
		Vector2 spawnPos8 = new Vector2(spawnPos7.x+enemyData.getWidth()+2, spawnPos1.y);
		Vector2 spawnPos11 = new Vector2(spawnPos8.x+enemyData.getWidth()+2, spawnPos1.y);
		
		//row2
		Vector2 spawnPos4 = new Vector2(spawnPos1.x, spawnPos1.y+enemyData.getHeight()+2);
		Vector2 spawnPos5 = new Vector2(spawnPos2.x, spawnPos4.y);
		Vector2 spawnPos6 = new Vector2(spawnPos3.x, spawnPos4.y);
		Vector2 spawnPos9 = new Vector2(spawnPos7.x, spawnPos4.y);
		Vector2 spawnPos10 = new Vector2(spawnPos8.x, spawnPos4.y);
		Vector2 spawnPos12 = new Vector2(spawnPos11.x, spawnPos4.y);
		
		positions.add(spawnPos1);
		positions.add(spawnPos2);
		positions.add(spawnPos3);
		positions.add(spawnPos4);
		positions.add(spawnPos5);
		positions.add(spawnPos6);
		positions.add(spawnPos7);
		positions.add(spawnPos8);
		positions.add(spawnPos9);
		positions.add(spawnPos10);
		
		
		
	}
	
	public Vector2 getRandomPos(){
		Vector2 randPos = new Vector2();
		randPos = positions.random();
		positions.removeValue(randPos, false);
		return randPos;
	}
	
	public void addNewPos(Vector2 pos){
		positions.add(pos);
	}

	public boolean hasPos(){
		if(positions.size > 0){
			return true;
		}else{
			return false;
		}
		
	}
}
