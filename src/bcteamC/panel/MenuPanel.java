package bcteamC.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bcteamC.Main;
import bcteamC.frame.GameSelectFrame;
import bcteamC.frame.MenuFrame;

public class MenuPanel extends JPanel {
	private ImageIcon startButtonImage = new ImageIcon(MenuPanel.class.getResource("/images/button-start.png"));
	private ImageIcon joinButtonImage = new ImageIcon(MenuPanel.class.getResource("/images/button-join.png"));
	private ImageIcon exitButtonImage = new ImageIcon(MenuPanel.class.getResource("/images/button-exit.png"));
	
	private ImageIcon startEnteredButtonImage = new ImageIcon(MenuPanel.class.getResource("/images/button-start-enter.png"));
	private ImageIcon joinEnteredButtonImage = new ImageIcon(MenuPanel.class.getResource("/images/button-join-enter.png"));
	private ImageIcon exitEnteredButtonImage = new ImageIcon(MenuPanel.class.getResource("/images/button-exit-enter.png"));

	private JButton startButton = new JButton(startButtonImage);
	private JButton joinButton = new JButton(joinButtonImage);
	private JButton exitButton = new JButton(exitButtonImage);
	
	public MenuPanel() {
		setVisible(true);
		setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setBackground(new Color(255, 0, 0, 0));
		setLayout(null);
		
		/* startButton */
		startButton.setBounds(80, 180, 350, 80);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 로그인 화면 이동
				((MenuFrame)getTopLevelAncestor()).moveToPanel("login");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startEnteredButtonImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(startButton);
		
		/* joinButton */
		joinButton.setBounds(80, 320, 350, 80);
		joinButton.setBorderPainted(false);
		joinButton.setContentAreaFilled(false);
		joinButton.setFocusPainted(false);
		joinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 회원가입 화면 이동
				((MenuFrame)getTopLevelAncestor()).moveToPanel("join");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				joinButton.setIcon(joinEnteredButtonImage);
				joinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				joinButton.setIcon(joinButtonImage);
				joinButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(joinButton);
		
		/* exitButton */
		exitButton.setBounds(80, 460, 350, 80);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitEnteredButtonImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(exitButton);
	}
}
