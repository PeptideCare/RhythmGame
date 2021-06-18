package bcteamC.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import bcteamC.Main;
import bcteamC.DAO.gameDAO;
import bcteamC.DAO.memberDAO;
import bcteamC.DTO.gameDTO;
import bcteamC.DTO.memberDTO;
import bcteamC.music.Music;
import bcteamC.panel.GameSelectPanel;
import bcteamC.panel.LoginPanel;

public class Game extends Thread {
	private Image noteRouteLineImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRouteLine.png"))
			.getImage();
	private Image judgmentLineImage = new ImageIcon(Game.class.getResource("/images/image-game-judgementLine.png"))
			.getImage();
	private Image gameInfoImage = new ImageIcon(Game.class.getResource("/images/image-game-gameInfo.png")).getImage();
	private Image noteRouteSImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteDImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteFImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteSpace1Image = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteJImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteKImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image noteRouteLImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png"))
			.getImage();
	private Image flareImage;
	private Image judgeImage;
	private Image comboImage;
	private Image scoreImage = new ImageIcon(Game.class.getResource("/images/image-game-score.png")).getImage();
	private Image rankImage = new ImageIcon(Game.class.getResource("/images/image-game-rank.png")).getImage();

	private Music gameMusic;
	private String musicName;
	private String titleName;
	private String diff;
	private int playTime;
	private Timer timer;
	private boolean isGameEnd = false;

	private int pointX;
	private int gameScore = 0;
	private int gameCombo = 0;
	private int perfectCnt = 0;
	private int goodCnt = 0;
	private int missCnt = 0;
	private int maxCombo = 0;

	private Beat[] beats = null;
	private ArrayList<Note> noteList = new ArrayList<Note>();

	public Game(String musicName, String titleName, String diff, int playTime) {
		this.musicName = musicName;
		this.titleName = titleName;
		this.diff = diff;
		this.playTime = playTime;
		gameMusic = new Music(this.musicName, false);
	}

	public void screenDrawEnd(Graphics2D g) { // 게임 종료 시
		g.drawImage(scoreImage, 315, 60, null);
		g.drawImage(rankImage, 315, 280, null);

		// 글자 렌더링(글자 부드럽게 보임)
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));

		// score
		g.drawString(String.format("%06d", gameScore), 660, 165);

		g.setFont(new Font("Arial", Font.BOLD, 25));

		// perfectCnt
		g.drawString(String.format("%03d", perfectCnt), 520, 195);

		// goodCnt
		g.drawString(String.format("%03d", goodCnt), 825, 195);

		// missCnt
		g.drawString(String.format("%03d", missCnt), 500, 245);

		// maxCombo
		g.drawString(String.format("%03d", maxCombo), 875, 245);

		/* label */
		g.setFont(new Font("돋움", Font.BOLD, 30));

		g.drawString("순위", 400, 330);
		g.drawString("점수", 600, 330);
		g.drawString("이름", 800, 330);

		// DB 연동
		g.setFont(new Font("돋움", Font.BOLD, 20));

		LoginPanel loginPanel = new LoginPanel(0);
		String mId = loginPanel.getmID();
		Main main = new Main();
		int index = main.getNowSelected();
		int musicId = 0;

		gameDAO dao = new gameDAO();
		ArrayList<gameDTO> list = dao.selectByScore(mId, gameScore);
		
		GameSelectPanel GSP = new GameSelectPanel();
		String difficulty = GSP.getDifficulty();
		int gscore = -1;

		if (difficulty.equals("normal")) {
			if (index == 0) {
				musicId = 1;
			} else if (index == 1) {
				musicId = 3;
			} else if (index == 2) {
				musicId = 5;
			}
			if (list.isEmpty()) {
				dao.insert(mId, musicId, perfectCnt, goodCnt, missCnt, gameScore);
			}
			
			for (int i = 0; i < list.size(); i++) {
				gameDTO dto = list.get(i);
				gscore = dto.getMusicScore();
				
				if (gscore == -1) {
					dao.insert(mId, musicId, perfectCnt, goodCnt, missCnt, gameScore);
				}
			}
		} else {
			if (index == 0) {
				musicId = 2;
			} else if (index == 1) {
				musicId = 5;
			} else if (index == 2) {
				musicId = 6;
			}
			if (list.isEmpty()) {
				dao.insert(mId, musicId, perfectCnt, goodCnt, missCnt, gameScore);
			}
			
			for (int i = 0; i < list.size(); i++) {
				gameDTO dto = list.get(i);
				gscore = dto.getMusicScore();
				
				if (gscore == 0) {
					dao.insert(mId, musicId, perfectCnt, goodCnt, missCnt, gameScore);
				}
			}
		}

		ArrayList<gameDTO> glist = dao.selectBydifficulty(musicId);
		ArrayList<Integer> scores = new ArrayList<Integer>();
		Map<Integer, String> map = new HashMap<Integer, String>();

		// 스코어 불러오기

		for (int i = 0; i < glist.size(); i++) {
			gameDTO dto = glist.get(i);
			scores.add(dto.getMusicScore());
			map.put(dto.getMusicScore(), dto.getUserId());
		}

		scores.sort(Comparator.reverseOrder());
		String rank = "";
		String score = "";
		String Id = "";

		int height = 370;
		for (int i = 0; i < scores.size(); i++) {
			rank = String.valueOf(i + 1);
			score = String.valueOf(scores.get(i));
			Id = map.get(scores.get(i));
			if (Id == null) {
				Id = "guest";
			}
			
			g.drawString(rank, 410, height + (40 * i));
			g.drawString(score, 610, height + (40 * i));
			g.drawString(Id, 810, height + (40 * i));
		}
	}

	public void screenDraw(Graphics2D g) { // 게임 시작 시
		if (isGameEnd) {
			screenDrawEnd(g);
		} else {
			// noteRouteImage
			// x좌표 : 228, 332, 436, 540, 640, 744, 848, 952
			Image[] imageList = { noteRouteSImage, noteRouteDImage, noteRouteFImage, noteRouteSpace1Image,
					noteRouteSpace2Image, noteRouteJImage, noteRouteKImage, noteRouteLImage };
			pointX = 228;
			for (int i = 0; i < 8; i++) {
				g.drawImage(imageList[i], pointX, 30, null);

				if (i != 3)
					pointX += 104;
				else
					pointX += 100;
			}

			// noteRouteLineImage
			// x좌표 : 224, 328, 432, 536, 740, 844, 948, 1052
			pointX = 224;
			for (int i = 0; i < 8; i++) {
				g.drawImage(noteRouteLineImage, pointX, 30, null);

				if (i != 3)
					pointX += 104;
				else
					pointX += 204;
			}
			// 판정 라인
			g.drawImage(gameInfoImage, 0, 660, null);
			g.drawImage(judgmentLineImage, 0, 580, null);

			/* 노트 애니메이션 구현 */
			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				if (note.getY() > 620) {
					judgeImage = new ImageIcon(Game.class.getResource("/images/image-game-judgeMiss.png")).getImage();
					comboImage = null;
					missCnt++;
					gameCombo = 0;
				}
				if (!note.isProceeded()) {
					noteList.remove(i);
					i--;
					break;
				} else {
					note.screenDraw(g);
				}
			}

			// 글자 렌더링(글자 부드럽게 보임)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// 키보드 표시
			// x좌표 : 270, 374, 478, 582, 786, 890, 994
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			String[] stringList = { "S", "D", "F", "Space Bar", "J", "K", "L" };
			pointX = 270;
			for (int i = 0; i < 7; i++) {
				g.drawString(stringList[i], pointX, 609);

				if (i == 3)
					pointX += 204;
				else
					pointX += 104;
			}

			// 하단 좌측 - 곡명
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString(titleName, 20, 702);

			// 하단 중앙 - 점수
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("Elephant", Font.BOLD, 30));
			g.drawString(String.format("%06d", gameScore), 565, 702);

			// 하단 우측 - 난이도
			if (diff.equals("Normal"))
				g.setColor(Color.green);
			else
				g.setColor(Color.red);

			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString(diff, 1160, 702);

			// 플레어
			g.drawImage(flareImage, 152, 220, null);

			// 콤보
			if (gameCombo > 1) {
				g.drawImage(comboImage, 540, 200, null);
				g.setColor(Color.orange);
				g.setFont(new Font("Arial", Font.BOLD, 30));
				g.drawString(String.valueOf(gameCombo), 630, 380);
			}

			// 판정
			g.drawImage(judgeImage, 452, 400, null);
		}
	}

	@Override
	public void run() {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				close();
			}
		};
		timer.schedule(task, playTime);

		dropNotes();
	}

	public void close() {
		gameMusic.close();
		timer.cancel();
		isGameEnd = true;
	}

	public void exit() {
		gameMusic.close();
		noteList = null;
		this.interrupt();
	}

	public void dropNotes() {
		int startTime = 0;
		int gap = 0;

		switch (titleName) {
		case "Jim Yosef - Link":
			startTime = 4000 - Main.REACH_TIME * 1000;
			gap = 128;
			makeBeats(startTime, gap);
			break;

		case "Tobu - Candy Land":
			startTime = 4000 - Main.REACH_TIME * 1000;
			gap = 120;
			makeBeats(startTime, gap);
			break;
		case "Tobu&Itro - Sunburst":
			startTime = 4000 - Main.REACH_TIME * 1000;
			gap = 120;
			makeBeats(startTime, gap);
			break;
		}

		gameMusic.start();

		int i = 0;
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;

			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
				dropped = true;
			}
			if (!dropped) {
				try {
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void makeBeats(int startTime, int gap) {
		try {
			int beatCount = countBeat();
			int[] time = new int[beatCount];
			String[] noteType = new String[beatCount];

			String path = Game.class.getResource("/text").getPath();
			path = java.net.URLDecoder.decode(path, "UTF-8");
			BufferedReader br = new BufferedReader(new FileReader(path + "\\" + titleName + " " + diff + ".txt"));

			int i = 0;
			String str;
			while ((str = br.readLine()) != null) {
				String[] temp = str.split(" ");
				time[i] = Integer.parseInt(temp[0]);
				noteType[i] = temp[1];
				++i;
			}
			br.close();

			beats = new Beat[beatCount];
			for (int k = 0; k < beatCount; ++k) {
				beats[k] = new Beat(startTime + gap * time[k], noteType[k]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int countBeat() throws Exception {
		String path = Game.class.getResource("/text").getPath();
		path = java.net.URLDecoder.decode(path, "UTF-8");
		BufferedReader br = new BufferedReader(new FileReader(path + "\\" + titleName + " " + diff + ".txt"));

		int beatCount = 0;
		while (br.readLine() != null) {
			beatCount++;
		}
		br.close();
		return beatCount;
	}

	public void judgeNotes(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);

			if (input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
		}
	}

	public void judgeEvent(String judge) {
		if (!judge.equals("None")) {
			flareImage = new ImageIcon(Game.class.getResource("/images/image-game-flare.png")).getImage();
			comboImage = new ImageIcon(Game.class.getResource("/images/image-game-combo.png")).getImage();
		}

		switch (judge) {
		case "Good":
			judgeImage = new ImageIcon(Game.class.getResource("/images/image-game-judgeGood.png")).getImage();
			goodCnt++;
			comboScore(50);
			break;
		case "Perfect":
			judgeImage = new ImageIcon(Game.class.getResource("/images/image-game-judgePerfect.png")).getImage();
			perfectCnt++;
			comboScore(100);
			break;
		}
	}

	public void comboScore(int sum) {
		gameCombo++;
		if (gameCombo > 1) {
			gameScore += sum + (50 * (Math.log10(gameCombo) / Math.log10(2)));
			if (maxCombo < gameCombo) {
				maxCombo = gameCombo;
			}
		} else {
			gameScore += sum;
		}
	}

	public void pressS() {
		judgeNotes("S");
		noteRouteSImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png")).getImage();
	}

	public void releaseS() {
		noteRouteSImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

	public void pressD() {
		judgeNotes("D");
		noteRouteDImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png")).getImage();
	}

	public void releaseD() {
		noteRouteDImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

	public void pressF() {
		judgeNotes("F");
		noteRouteFImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png")).getImage();
	}

	public void releaseF() {
		noteRouteFImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

	public void pressSpace() {
		judgeNotes("Space");
		noteRouteSpace1Image = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png"))
				.getImage();
		noteRouteSpace2Image = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png"))
				.getImage();
	}

	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

	public void pressJ() {
		judgeNotes("J");
		noteRouteJImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png")).getImage();
	}

	public void releaseJ() {
		noteRouteJImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

	public void pressK() {
		judgeNotes("K");
		noteRouteKImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png")).getImage();
	}

	public void releaseK() {
		noteRouteKImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

	public void pressL() {
		judgeNotes("L");
		noteRouteLImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoutePress.png")).getImage();
	}

	public void releaseL() {
		noteRouteLImage = new ImageIcon(Game.class.getResource("/images/image-game-noteRoute.png")).getImage();
	}

}
