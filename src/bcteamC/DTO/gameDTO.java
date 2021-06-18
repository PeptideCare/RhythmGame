package bcteamC.DTO;

public class gameDTO {
	
	private int gameId;
	private String userId;
	private int musicId;
	private int perfect;
	private int good;
	private int miss;
	private int musicScore;
	
	public gameDTO(int gameId, String userId, int musicId, int perfect, int good, int miss, int musicScore) {
		this.gameId = gameId;
		this.userId = userId;
		this.musicId = musicId;
		this.perfect = perfect;
		this.good = good;
		this.miss = miss;
		this.musicScore = musicScore;		
	}

	public int getGameId() {
		return gameId;
	}



	public void setGameId(int gameId) {
		this.gameId = gameId;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMusicId() {
		return musicId;
	}

	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}

	public int getPerfect() {
		return perfect;
	}

	public void setPerfect(int perfect) {
		this.perfect = perfect;
	}

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getMiss() {
		return miss;
	}

	public void setMiss(int miss) {
		this.miss = miss;
	}

	public int getMusicScore() {
		return musicScore;
	}

	public void setMusicScore(int musicScore) {
		this.musicScore = musicScore;
	}
	
	
}
