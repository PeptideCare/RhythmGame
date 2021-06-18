package bcteamC.frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bcteamC.Main;
import bcteamC.music.Music;
import bcteamC.panel.GameSelectPanel;
import bcteamC.panel.MyProfilePanel;

public class GameSelectFrame extends JFrame {
	/* Background & init */
	private Image screenImage;
	private Graphics screenGraphic;
	private Image gameSelectBackgroundImage = new ImageIcon(GameSelectFrame.class.getResource("/images/background-game-select.jpg")).getImage();
	private Image myProfileBackgroundImage = new ImageIcon(GameSelectFrame.class.getResource("/images/background-myprofile.jpg")).getImage();
	private Image backgroundImage = gameSelectBackgroundImage;
	private Image selectedImage = new ImageIcon(GameSelectFrame.class.getResource("/images/image-Jim Yosef-Link.png")).getImage();
	private Image myProfileImage = new ImageIcon(GameSelectFrame.class.getResource("/images/image-myprofile.png")).getImage();

	
	private JLabel menubar = new JLabel(new ImageIcon(MenuFrame.class.getResource("/images/background-menubar.png")));
	private ImageIcon closeButtonImage = new ImageIcon(MenuFrame.class.getResource("/images/button-close.png"));
	private ImageIcon closeEnteredButtonImage = new ImageIcon(MenuFrame.class.getResource("/images/button-close-enter.png"));
	private JButton closeButton = new JButton(closeButtonImage);
	private int mouseX, mouseY;
	private boolean isMyProfile = false;
	private boolean isGameSelect = false;
	
	/* Panel */
	GameSelectPanel gameSelectPanel;
	MyProfilePanel myProfilePanel;
	
	/* Music */
	private Music selectedMusic;
	
	public GameSelectFrame() {
		init();
		
		moveToPanel("gameSelect");
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(backgroundImage, 0, 0, null);
		if(isGameSelect) g.drawImage(selectedImage, 340, 100, null);
		if(isMyProfile) g.drawImage(myProfileImage, 290, 200, null);
		paintComponents(g);
		this.repaint();
	}

	private void init() {
		setUndecorated(true);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		setVisible(true);

		closeButton.setBounds(1245, 0, 30, 30);
		closeButton.setBorderPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setFocusPainted(false);
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				closeButton.setIcon(closeEnteredButtonImage);
				closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				closeButton.setIcon(closeButtonImage);
				closeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(closeButton);

		menubar.setBounds(0, 0, 1280, 30);
		menubar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menubar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menubar);
	}
	
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null) selectedMusic.close(); // 곡 넘길 때 재생하고 있는 곡 종료

		selectedImage = new ImageIcon(GameSelectFrame.class.getResource("/images/" + Main.track.get(nowSelected).getImage())).getImage();
		selectedMusic = new Music(Main.track.get(nowSelected).getMusicName(), true);
		selectedMusic.start();
	}
	
	public void clickPrev() {
		if(Main.nowSelected > 0) {
			Main.nowSelected--;
		} else {
			Main.nowSelected = Main.track.size() - 1;
		}
		selectTrack(Main.nowSelected);
	}

	public void clickNext() {
		if(Main.nowSelected < Main.track.size() - 1) {
			Main.nowSelected++;
		} else {
			Main.nowSelected = 0;
		}
		selectTrack(Main.nowSelected);
	}
	
	public void moveToPanel(String panel) {
		if(gameSelectPanel != null) remove(gameSelectPanel);
		if(myProfilePanel != null) remove(myProfilePanel);
		switch(panel) {
			case "gameSelect":
				backgroundImage = gameSelectBackgroundImage;
				isGameSelect = true;
				isMyProfile = false;
				if(selectedMusic == null || !selectedMusic.isStart())
					selectTrack(Main.nowSelected);
				
				gameSelectPanel = new GameSelectPanel();
				add(gameSelectPanel);
				break;
				
			case "myProfile":
				backgroundImage = myProfileBackgroundImage;
				isMyProfile = true;
				isGameSelect = false;
				selectedMusic.close();
				
				myProfilePanel = new MyProfilePanel();
				add(myProfilePanel);
				break;
		}
		revalidate();
		repaint();
	}

	public void moveToFrame(String frame) {
		switch(frame) {
			case "menu":
				selectedMusic.close();
				new MenuFrame();
				dispose();
				break;
			case "gameStart":
				selectedMusic.close();
				new GameStartFrame();
				dispose();
				break;
		}
	}
}
