package bcteamC.music;

public class Track {
	private String image;
	private String musicName;
	private String titleName;
	private int playTime;
	
	public Track(String image, String musicName, String titleName, int playTime) {
		this.image = image;
		this.musicName = musicName;
		this.titleName = titleName;
		this.playTime = playTime;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
}
