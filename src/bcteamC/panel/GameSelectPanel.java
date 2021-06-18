package bcteamC.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bcteamC.Main;
import bcteamC.DAO.gameDAO;
import bcteamC.DTO.gameDTO;
import bcteamC.frame.GameSelectFrame;

public class GameSelectPanel extends JPanel {

	private ImageIcon prevButtonImage = new ImageIcon(GameSelectPanel.class.getResource("/images/button-prev.png"));
	private ImageIcon nextButtonImage = new ImageIcon(GameSelectPanel.class.getResource("/images/button-next.png"));
	private ImageIcon normalButtonImage = new ImageIcon(GameSelectPanel.class.getResource("/images/button-normal.png"));
	private ImageIcon hardButtonImage = new ImageIcon(GameSelectPanel.class.getResource("/images/button-hard.png"));
	private ImageIcon myProfileImage = new ImageIcon(GameSelectPanel.class.getResource("/images/button-myprofile.png"));
	private ImageIcon gameBackButtonImage = new ImageIcon(
			GameSelectPanel.class.getResource("/images/button-game-back.png"));

	private ImageIcon prevEnteredButtonImage = new ImageIcon(
			GameSelectPanel.class.getResource("/images/button-prev-enter.png"));
	private ImageIcon nextEnteredButtonImage = new ImageIcon(
			GameSelectPanel.class.getResource("/images/button-next-enter.png"));
	private ImageIcon normalEnteredButtonImage = new ImageIcon(
			GameSelectPanel.class.getResource("/images/button-normal-enter.png"));
	private ImageIcon hardEnteredButtonImage = new ImageIcon(
			GameSelectPanel.class.getResource("/images/button-hard-enter.png"));
	private ImageIcon gameBackEnteredButtonImage = new ImageIcon(
			GameSelectPanel.class.getResource("/images/button-game-back-enter.png"));

	private JButton prevButton = new JButton(prevButtonImage);
	private JButton nextButton = new JButton(nextButtonImage);
	private JButton normalButton = new JButton(normalButtonImage);
	private JButton hardButton = new JButton(hardButtonImage);
	private JButton myProfile = new JButton(myProfileImage);
	private JButton gameBackButton = new JButton(gameBackButtonImage);

	String musicName[] = { "Link", "Fresh", "Sunburst" };
	int index = 0;

	int nscore[] = new int[3];
	int hscore[] = new int[3];
	private static String difficulty = "";

	public static String getDifficulty() {
		return difficulty;
	}

	public GameSelectPanel(int i) {
	}

	public GameSelectPanel() {

		Main main = new Main();
		index = main.getNowSelected();
		
		setVisible(true);
		setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setBackground(new Color(255, 0, 0, 0));
		setLayout(null);

		LoginPanel loginPanel = new LoginPanel(0);
		boolean guest = loginPanel.isGuest();

		/* gameBackButton */
		gameBackButton.setBounds(20, 50, 60, 60);
		gameBackButton.setBorderPainted(false);
		gameBackButton.setContentAreaFilled(false);
		gameBackButton.setFocusPainted(false);
		gameBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 로그인 화면 이동
				loginPanel.setGuest(false);
				((GameSelectFrame) getTopLevelAncestor()).moveToFrame("menu");
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				gameBackButton.setIcon(gameBackEnteredButtonImage);
				gameBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gameBackButton.setIcon(gameBackButtonImage);
				gameBackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(gameBackButton);

		if (guest == false) {

			/* myProfile */
			myProfile.setBounds(90, 50, 60, 60);
			myProfile.setBorderPainted(false);
			myProfile.setContentAreaFilled(false);
			myProfile.setFocusPainted(false);
			myProfile.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					// 마이프로필 화면 이동
					((GameSelectFrame) getTopLevelAncestor()).moveToPanel("myProfile");
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					myProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					myProfile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			});
			add(myProfile);
		}

		/* Label */
		JLabel lbl_normal = new JLabel("Normal :");
		lbl_normal.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_normal.setBounds(370, 560, 200, 60);
		lbl_normal.setForeground(Color.white);
		add(lbl_normal);

		JLabel lbl_hard = new JLabel("Hard :");
		lbl_hard.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_hard.setBounds(700, 560, 200, 60);
		lbl_hard.setForeground(Color.white);
		add(lbl_hard);

		/* textField */
		JTextField txt_normal = new JTextField();
		txt_normal.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_normal.setBounds(500, 560, 150, 60);
		
		gameDAO gdao = new gameDAO();
		ArrayList<gameDTO> nlist = gdao.selectScore("normal", musicName[index]);
		
		int temp = 0;
		
		for (int i = 0; i < nlist.size(); i++) {
			gameDTO dto = nlist.get(i);
			nscore[index] = dto.getMusicScore();
			if (nscore[index] > temp) {
				temp = nscore[index];
			}
		}
		
		String nscore = String.valueOf(temp);

		// DB 연동 - normal 점수 세팅
		txt_normal.setText(nscore);

		txt_normal.setOpaque(false);
		txt_normal.setForeground(Color.white);
		txt_normal.setBorder(null);
		add(txt_normal);

		JTextField txt_hard = new JTextField();
		txt_hard.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_hard.setBounds(800, 560, 150, 60);

		// DB 연동 - hard 점수 세팅
		
		ArrayList<gameDTO> hlist = gdao.selectScore("hard", musicName[index]);

		temp = 0;
		
		for (int i = 0; i < hlist.size(); i++) {
			gameDTO dto = hlist.get(i);
			hscore[index] = dto.getMusicScore();
			if (hscore[index] > temp) {
				temp = hscore[index];
			}
		}
		
		String hscore = String.valueOf(temp);
		
		txt_hard.setText(hscore);

		txt_hard.setOpaque(false);
		txt_hard.setForeground(Color.white);
		txt_hard.setBorder(null);
		add(txt_hard);

		/* prevButton */
		prevButton.setBounds(140, 310, 180, 60);
		prevButton.setBorderPainted(false);
		prevButton.setContentAreaFilled(false);
		prevButton.setFocusPainted(false);
		prevButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 이전 곡
				((GameSelectFrame) getTopLevelAncestor()).clickPrev();

				if (index <= 0) {
					index = 2;
					normalScore(index, txt_normal);
					hardScore(index, txt_hard);
				} else {
					index -= 1;
					normalScore(index, txt_normal);
					hardScore(index, txt_hard);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				prevButton.setIcon(prevEnteredButtonImage);
				prevButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				prevButton.setIcon(prevButtonImage);
				prevButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(prevButton);

		/* nextButton */
		nextButton.setBounds(960, 310, 180, 60);
		nextButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setFocusPainted(false);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 다음 곡
				((GameSelectFrame) getTopLevelAncestor()).clickNext();

				if (index >= 2) {
					index = 0;
					normalScore(index, txt_normal);
					hardScore(index, txt_hard);

				} else {
					index += 1;
					normalScore(index, txt_normal);
					hardScore(index, txt_hard);
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nextButton.setIcon(nextEnteredButtonImage);
				nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextButton.setIcon(nextButtonImage);
				nextButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(nextButton);

		/* normalButton */
		normalButton.setBounds(400, 630, 180, 60);
		normalButton.setBorderPainted(false);
		normalButton.setContentAreaFilled(false);
		normalButton.setFocusPainted(false);
		normalButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 게임 화면 이동(normal 난이도)
				Main.diff = "Normal";
				difficulty = "normal";
				((GameSelectFrame) getTopLevelAncestor()).moveToFrame("gameStart");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				normalButton.setIcon(normalEnteredButtonImage);
				normalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				normalButton.setIcon(normalButtonImage);
				normalButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(normalButton);

		/* hardButton */
		hardButton.setBounds(720, 630, 180, 60);
		hardButton.setBorderPainted(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 게임 화면 이동(hard 난이도)
				Main.diff = "Hard";
				difficulty = "hard";
				((GameSelectFrame) getTopLevelAncestor()).moveToFrame("gameStart");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				hardButton.setIcon(hardEnteredButtonImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(hardButton);
	}
	
	public void normalScore(int index, JTextField txt_normal) {
		gameDAO gdao = new gameDAO();

		String musicName[] = {"Link", "Fresh", "Sunburst"};
		int nscore[] = new int[3];

		ArrayList<gameDTO> nlist = gdao.selectScore("normal", musicName[index]);

		int temp = 0;

		for (int i = 0; i < nlist.size(); i++) {
			gameDTO dto = nlist.get(i);
			nscore[index] = dto.getMusicScore();
			if (nscore[index] > temp) {
				temp = nscore[index];
			}
		}

		String score = String.valueOf(temp);

		txt_normal.setText(score);
		txt_normal.setOpaque(false);
		txt_normal.setForeground(Color.white);
		txt_normal.setBorder(null);
		add(txt_normal);
	}

	public void hardScore(int index, JTextField txt_hard) {
		
		String musicName[] = {"Link", "Fresh", "Sunburst"};
		int hscore[] = new int[3];
		
		gameDAO gdao = new gameDAO();

		ArrayList<gameDTO> hlist = gdao.selectScore("hard", musicName[index]);

		int temp = 0;

		for (int i = 0; i < hlist.size(); i++) {
			gameDTO dto = hlist.get(i);
			hscore[index] = dto.getMusicScore();
			if (hscore[index] > temp) {
				temp = hscore[index];
			}
		}

		String score = String.valueOf(temp);

		txt_hard.setText(score);
		txt_hard.setOpaque(false);
		txt_hard.setForeground(Color.white);
		txt_hard.setBorder(null);
		add(txt_hard);
	}
}
