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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bcteamC.Main;
import bcteamC.DAO.memberDAO;
import bcteamC.DTO.memberDTO;
import bcteamC.frame.MenuFrame;

public class LoginPanel extends JPanel {
	private ImageIcon loginButtonImage = new ImageIcon(LoginPanel.class.getResource("/images/button-login.png"));
	private ImageIcon guestButtonImage = new ImageIcon(LoginPanel.class.getResource("/images/button-guest.png"));
	private ImageIcon loginBackButtonImage = new ImageIcon(LoginPanel.class.getResource("/images/button-back.png"));

	private ImageIcon loginEnteredButtonImage = new ImageIcon(
			LoginPanel.class.getResource("/images/button-login-enter.png"));
	private ImageIcon guestEnteredButtonImage = new ImageIcon(
			LoginPanel.class.getResource("/images/button-guest-enter.png"));
	private ImageIcon loginBackEnteredButtonImage = new ImageIcon(
			LoginPanel.class.getResource("/images/button-back-enter.png"));

	private JButton loginButton = new JButton(loginButtonImage);
	private JButton guestButton = new JButton(guestButtonImage);
	private JButton loginBackButton = new JButton(loginBackButtonImage);
	private static boolean guest = false;

	public boolean isGuest() {
		return guest;
	}
	
	public static void setGuest(boolean guest) {
		LoginPanel.guest = guest;
	}

	private static String mID;

	public static String getmID() {
		return mID;
	}

	public LoginPanel(int i) {
	}

	public LoginPanel() {
		setVisible(true);
		setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setBackground(new Color(255, 0, 0, 0));
		setLayout(null);

		/* label */
		JLabel lbl_id = new JLabel("ID :");
		lbl_id.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_id.setBounds(80, 250, 200, 60);
		lbl_id.setForeground(Color.white);
		add(lbl_id);

		JLabel lbl_password = new JLabel("Password :");
		lbl_password.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_password.setBounds(80, 350, 200, 60);
		lbl_password.setForeground(Color.white);
		add(lbl_password);

		/* textField */
		JTextField txt_id = new JTextField();
		txt_id.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_id.setBounds(280, 250, 250, 60);
		txt_id.setColumns(10);
		add(txt_id);

		JPasswordField txt_password = new JPasswordField();
		txt_password.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_password.setBounds(280, 350, 250, 60);
		txt_password.setColumns(10);
		add(txt_password);

		/* loginButton */
		loginButton.setBounds(90, 520, 400, 60);
		loginButton.setBorderPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setFocusPainted(false);
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("id: " + txt_id.getText());
				System.out.println("password: " + txt_password.getPassword().length);

				if ((txt_id != null && !txt_id.getText().isEmpty()) && (txt_password != null)) {
					// DB 연동 - 로그인 처리

					mID = txt_id.getText();
					System.out.println(mID);
					String mPass = txt_password.getText();

					memberDAO dao = new memberDAO();

					ArrayList<memberDTO> list = dao.select(mID);

					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(getRootPane(), "존재하지 않는 회원입니다.");
					} else {
						for (int i = 0; i < list.size(); i++) {
							memberDTO dto = list.get(i);
							String id = dto.getId();
							String pw = dto.getPw();

							if (mID.equals(id) && mPass.equals(pw)) {

								System.out.println(id + "님이 로그인 하셨습니다.");

								txt_id.setText("");
								txt_password.setText("");

								// 곡 선택 화면 이동
								((MenuFrame) getTopLevelAncestor()).moveToFrame();
							} else {
								JOptionPane.showMessageDialog(getRootPane(), "회원정보가 일치하지 않습니다.");
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(getRootPane(), "회원 정보를 모두 입력하세요.");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setIcon(loginEnteredButtonImage);
				loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setIcon(loginButtonImage);
				loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(loginButton);

		/* guestButton */
		guestButton.setBounds(310, 450, 180, 60);
		guestButton.setBorderPainted(false);
		guestButton.setContentAreaFilled(false);
		guestButton.setFocusPainted(false);
		guestButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 곡 선택 화면으로 이동
				mID = "guest";
				guest = true;
				((MenuFrame) getTopLevelAncestor()).moveToFrame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				guestButton.setIcon(guestEnteredButtonImage);
				guestButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				guestButton.setIcon(guestButtonImage);
				guestButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(guestButton);

		/* loginBackButton */
		loginBackButton.setVisible(true);
		loginBackButton.setBounds(90, 450, 180, 60);
		loginBackButton.setBorderPainted(false);
		loginBackButton.setContentAreaFilled(false);
		loginBackButton.setFocusPainted(false);
		loginBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 메인메뉴 화면 이동
				((MenuFrame) getTopLevelAncestor()).moveToPanel("menu");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginBackButton.setIcon(loginBackEnteredButtonImage);
				loginBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginBackButton.setIcon(loginBackButtonImage);
				loginBackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(loginBackButton);
	}
}
