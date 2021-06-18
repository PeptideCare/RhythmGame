package bcteamC;

import java.util.ArrayList;

import bcteamC.frame.MenuFrame;
import bcteamC.music.Track;

public class Main {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int NOTE_SPEED = 7; // 노트 떨어지는 속도
	public static final int SLEEP_TIME = 10; // 일정 주기로 떨어지는 시간
	public static final int REACH_TIME = 2; // 노트가 생성되고 판정라인까지 떨어지는 시간
	public static final ArrayList<Track> track = new ArrayList<Track>();
	public static String diff;
	public static int nowSelected = 0;
	
	public static int getNowSelected() {
		return nowSelected;
	}


	public static void main(String[] args) {
		// Track - (image, musicName, titleName, playTime) playTime : 재생시간 + 3초
		track.add(new Track("image-Jim Yosef-Link.png", "Jim Yosef-Link.mp3", "Jim Yosef - Link", 10000)); // 227000
		track.add(new Track("image-Tobu-Candy Land.png", "Tobu-Candy Land.mp3", "Tobu - Candy Land", 201000));
		track.add(new Track("image-Tobu&Itro-Sunburst.png", "Tobu&Itro-Sunburst.mp3", "Tobu&Itro - Sunburst", 193000));
		
		new MenuFrame();
	}
}
