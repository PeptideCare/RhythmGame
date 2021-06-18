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
import bcteamC.game.Game;
import bcteamC.game.KeyListener;
import bcteamC.panel.GameStartPanel;

public class GameStartFrame extends JFrame {
	/* Background & init */
	private Image screenImage;
	private Graphics screenGraphic;
	private Image backgroundImage = new ImageIcon(GameStartFrame.class.getResource("/images/background-game-start.jpg")).getImage();
	
	private JLabel menubar = new JLabel(new ImageIcon(GameStartFrame.class.getResource("/images/background-menubar.png")));
	private ImageIcon closeButtonImage = new ImageIcon(GameStartFrame.class.getResource("/images/button-close.png"));
	private ImageIcon closeEnteredButtonImage = new ImageIcon(GameStartFrame.class.getResource("/images/button-close-enter.png"));
	private JButton closeButton = new JButton(closeButtonImage);
	private int mouseX, mouseY;
	
	/* Panel */
	private GameStartPanel gameStartPanel;
	private boolean isgameStart = false;
	
	/* Game */
	public static Game game;

	public GameStartFrame() {
		init();
		
		moveToPanel("gameStart");
		gameStart();
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(backgroundImage, 0, 0, null);
		if(isgameStart) game.screenDraw(g);
		paintComponents(g);
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}
	
	public void init() {
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
	
	public void gameStart() {
		addKeyListener(new KeyListener());
		setFocusable(true);
		game = new Game(Main.track.get(Main.nowSelected).getMusicName(), Main.track.get(Main.nowSelected).getTitleName(), Main.diff,
				Main.track.get(Main.nowSelected).getPlayTime());
		game.start();
	}

	public void moveToPanel(String panel) {
		if(gameStartPanel != null) remove(gameStartPanel);
		
		switch(panel) {
			case "gameStart":
				isgameStart = true;
				gameStartPanel = new GameStartPanel();
			    add(gameStartPanel);
				break;
		}
		revalidate();
		repaint();
	}

	public void moveToFrame() {
		game.exit();
		new GameSelectFrame();
		dispose();
	}
}
