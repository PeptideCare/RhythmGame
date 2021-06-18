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
import bcteamC.panel.JoinPanel;
import bcteamC.panel.LoginPanel;
import bcteamC.panel.MenuPanel;

public class MenuFrame extends JFrame {
	/* Background & init */
	private Image screenImage;
	private Graphics screenGraphic;
	private Image backgroundImage = new ImageIcon(MenuFrame.class.getResource("/images/background-intro.jpg")).getImage();

	private JLabel menubar = new JLabel(new ImageIcon(MenuFrame.class.getResource("/images/background-menubar.png")));
	private ImageIcon closeButtonImage = new ImageIcon(MenuFrame.class.getResource("/images/button-close.png"));
	private ImageIcon closeEnteredButtonImage = new ImageIcon(MenuFrame.class.getResource("/images/button-close-enter.png"));
	private JButton closeButton = new JButton(closeButtonImage);
	private int mouseX, mouseY;
	
	/* Panel */
	private MenuPanel menuPanel;
	private JoinPanel joinPanel;
	private LoginPanel loginPanel;
	
	/* Music */
	private Music introMusic;
	
	public MenuFrame() {
		init();
		
		moveToPanel("menu");
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
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(backgroundImage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}
	
	public void moveToPanel(String panel) {
		if(menuPanel != null) remove(menuPanel);
		if(joinPanel != null) remove(joinPanel);
		if(loginPanel != null) remove(loginPanel);
		
		switch(panel) {
			case "menu":
				if(introMusic == null) {
					introMusic = new Music("Joakim Karud-Fresh Start.mp3", true);
					introMusic.start();
				}
				menuPanel = new MenuPanel();
				add(menuPanel);
				break;
			case "join":
				joinPanel = new JoinPanel();
				add(joinPanel);
				break;
			case "login":
				loginPanel = new LoginPanel();
				add(loginPanel);
				break;
		}

		revalidate();
		repaint();
	}
	
	public void moveToFrame() {
		introMusic.close();
		dispose();
		new GameSelectFrame();
	}
}
