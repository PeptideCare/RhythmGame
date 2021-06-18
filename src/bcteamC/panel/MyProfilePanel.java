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

public class MyProfilePanel extends JPanel {
	private ImageIcon profileBackButtonImage = new ImageIcon(MyProfilePanel.class.getResource("/images/button-game-back.png"));
	private ImageIcon prevButtonImage = new ImageIcon(MyProfilePanel.class.getResource("/images/button-prev.png"));
	private ImageIcon nextButtonImage = new ImageIcon(MyProfilePanel.class.getResource("/images/button-next.png"));
	
	private ImageIcon profileBackEnteredButtonImage = new ImageIcon(MyProfilePanel.class.getResource("/images/button-game-back-enter.png"));
	private ImageIcon prevEnteredButtonImage = new ImageIcon(MyProfilePanel.class.getResource("/images/button-prev-enter.png"));
	private ImageIcon nextEnteredButtonImage = new ImageIcon(MyProfilePanel.class.getResource("/images/button-next-enter.png"));

	private JButton profileBackButton = new JButton(profileBackButtonImage);
	private JButton prevButton = new JButton(prevButtonImage);
	private JButton nextButton = new JButton(nextButtonImage);
	
	String Name[] = {"Link", "Fresh", "Sunburst"};
	String musicName[] = {"Jim Yosef - Link", "Joakim Karud - Fresh Start", "Tobu&Itro - Sunburst"};
	int index = 0;
	
	public MyProfilePanel(int i) {}
	
	public MyProfilePanel() {
		setVisible(true);
		setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setBackground(new Color(255, 0, 0, 0));
		setLayout(null);
		
		/* profileBackButton */
		profileBackButton.setBounds(20, 50, 60, 60);
		profileBackButton.setBorderPainted(false);
		profileBackButton.setContentAreaFilled(false);
		profileBackButton.setFocusPainted(false);
		profileBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 게임 선택 화면 이동
				((GameSelectFrame)getTopLevelAncestor()).moveToPanel("gameSelect");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				profileBackButton.setIcon(profileBackEnteredButtonImage);
				profileBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				profileBackButton.setIcon(profileBackButtonImage);
				profileBackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(profileBackButton);
		
		/* Label */
		JLabel lbl_myPage = new JLabel("My Page");
		lbl_myPage.setFont(new Font("Lato", Font.BOLD, 50));
		lbl_myPage.setBounds(550, 80, 300, 60);
		lbl_myPage.setForeground(Color.white);
		add(lbl_myPage);
		
		JLabel lbl_normal = new JLabel("Normal");
		lbl_normal.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_normal.setBounds(400, 230, 200, 60);
		lbl_normal.setForeground(Color.white);
		add(lbl_normal);

		JLabel lbl_hard = new JLabel("Hard");
		lbl_hard.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_hard.setBounds(800, 230, 200, 60);
		lbl_hard.setForeground(Color.white);
		add(lbl_hard);
		
		/* textField */
		
		gameDAO dao = new gameDAO();
		LoginPanel loginPanel = new LoginPanel(0);
		String mId = loginPanel.getmID();

		int nheight = 270;
		int hheight = 270;
		
		JTextField txt_musicName = new JTextField();
		txt_musicName.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_musicName.setBounds(520, 195, 350, 60);
		// DB 연동 - 곡 이름
		txt_musicName.setText(musicName[index]);
		txt_musicName.setOpaque(false);
		txt_musicName.setForeground(Color.white);
		txt_musicName.setBorder(null);
		add(txt_musicName);
		
		normalScore(mId, Name[index], nheight);	
		hardScore(mId, Name[index],  hheight);
		
		/* prevButton */
		prevButton.setBounds(100, 340, 180, 60);
		prevButton.setBorderPainted(false);
		prevButton.setContentAreaFilled(false);
		prevButton.setFocusPainted(false);
		prevButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 이전 곡 정보
				if(index > 0) {
					index--;
					normalScore(mId, Name[index], nheight);
					hardScore(mId, Name[index],  hheight);
				} else {
					index = musicName.length - 1;
					normalScore(mId, Name[index], nheight);	
					hardScore(mId, Name[index],  hheight);
				}
				txt_musicName.setText(musicName[index]);
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
		nextButton.setBounds(1000, 340, 180, 60);
		nextButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setFocusPainted(false);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// 다음 곡 정보
				if(index < musicName.length - 1) {
					index++;
					
				} else {
					index = 0;
					
				}
				txt_musicName.setText(musicName[index]);
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
		
	}
	
	public void normalScore(String mId, String musicName, int nheight) {
		gameDAO dao = new gameDAO();
		ArrayList<gameDTO> glist = dao.selectBydifficulty(mId, "normal", musicName);
		for (int i = 0; i < glist.size(); i++) {
			gameDTO ndto = glist.get(i);
			String nscore = String.valueOf(ndto.getMusicScore());
			
			JTextField txt_nomalScore = new JTextField();
			txt_nomalScore.setFont(new Font("Lato", Font.PLAIN, 30));
			txt_nomalScore.setBounds(400, nheight, 150, 60);
			
			// Nomal 점수
			txt_nomalScore.setText(nscore);
			txt_nomalScore.setOpaque(false);
			txt_nomalScore.setForeground(Color.white);
			txt_nomalScore.setBorder(null);
			add(txt_nomalScore);
			nheight += 30;
		}
	}
	
	public void hardScore(String mId, String musicName, int hheight) {
		
		
		gameDAO dao = new gameDAO();
		ArrayList<gameDTO> hlist = dao.selectBydifficulty(mId, "hard", musicName);
		for (int i = 0; i < hlist.size(); i++) {
			gameDTO hdto = hlist.get(i);
			String hscore = String.valueOf(hdto.getMusicScore());
			
			JTextField txt_hardScore = new JTextField();
			txt_hardScore.setFont(new Font("Lato", Font.PLAIN, 30));
			txt_hardScore.setBounds(785, hheight, 150, 60);
			
			// hard 점수
			txt_hardScore.setText(hscore);
			txt_hardScore.setOpaque(false);
			txt_hardScore.setForeground(Color.white);
			txt_hardScore.setBorder(null);
			add(txt_hardScore);
			hheight += 30;
		}
	}
}
