package se.palmatech.angrysharks.interfaces;

public interface IActionResolver {
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(int score);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS();
	public void getAchievementsGPGS();
}
