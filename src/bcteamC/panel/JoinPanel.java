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

public class JoinPanel extends JPanel {
	private ImageIcon joinActionButtonImage = new ImageIcon(JoinPanel.class.getResource("/images/button-join-action.png"));
	private ImageIcon joinBackButtonImage = new ImageIcon(JoinPanel.class.getResource("/images/button-back.png"));
	
	private ImageIcon joinActionEnteredButtonImage = new ImageIcon(JoinPanel.class.getResource("/images/button-join-action-enter.png"));
	private ImageIcon joinBackEnteredButtonImage = new ImageIcon(JoinPanel.class.getResource("/images/button-back-enter.png"));

	private JButton joinActionButton = new JButton(joinActionButtonImage);
	private JButton joinBackButton = new JButton(joinBackButtonImage);
	
	public JoinPanel() {
		setVisible(true);
		setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setBackground(new Color(255, 0, 0, 0));
		setLayout(null);
		
		/* label */
		JLabel lbl_id = new JLabel("ID :");
		lbl_id.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_id.setBounds(80, 200, 200, 60);
		lbl_id.setForeground(Color.white);
		add(lbl_id);

		JLabel lbl_password = new JLabel("Password :");
		lbl_password.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_password.setBounds(80, 300, 200, 60);
		lbl_password.setForeground(Color.white);
		add(lbl_password);

		JLabel lbl_nickname = new JLabel("Nickname :");
		lbl_nickname.setFont(new Font("Lato", Font.BOLD, 30));
		lbl_nickname.setBounds(80, 400, 200, 60);
		lbl_nickname.setForeground(Color.white);
		add(lbl_nickname);

		/* textField */
		JTextField txt_id = new JTextField();
		txt_id.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_id.setBounds(280, 200, 250, 60);
		txt_id.setColumns(10);
		add(txt_id);
		
		JPasswordField txt_password = new JPasswordField();
		txt_password.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_password.setBounds(280, 300, 250, 60);
		txt_password.setColumns(10);
		add(txt_password);

		JTextField txt_nickname = new JTextField();
		txt_nickname.setFont(new Font("Lato", Font.PLAIN, 30));
		txt_nickname.setBounds(280, 400, 250, 60);
		txt_nickname.setColumns(10);
		add(txt_nickname);
		
		/* joinActionButton */
		joinActionButton.setBounds(310, 500, 180, 60);
		joinActionButton.setBorderPainted(false);
		joinActionButton.setContentAreaFilled(false);
		joinActionButton.setFocusPainted(false);
		joinActionButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("id: "+ txt_id.getText());
				System.out.println("password: "+ txt_password.getPassword().length);
				System.out.println("nickname: "+ txt_nickname.getText());
				
				if((txt_id != null && !txt_id.getText().isEmpty()) &&
						(txt_nickname != null && !txt_nickname.getText().isEmpty()) &&
						(txt_password != null)) {
					// DB 연동 - 회원가입 처리
					
					String id = txt_id.getText();
					String password = txt_password.getText();
					String nickname = txt_nickname.getText();

					String mId = "";
					String mPw = "";
					
					memberDAO dao = new memberDAO();
					
					ArrayList<memberDTO> list = dao.select(id);
					
					for (int i = 0; i < list.size(); i++) {
						memberDTO dto = list.get(i);
						mId = dto.getId();
						mPw = dto.getPw();
					}
					
					// 이미 가입 했을 시
					if (id.equals(mId)) {
						JOptionPane.showMessageDialog(null, "이미 가입하신 회원입니다.");
						
						// 정상적인 회원가입
					} else {

						dao.insert(id, password, nickname);
						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
					}
							
					txt_id.setText("");
					txt_password.setText("");
					txt_nickname.setText("");
					
					// 메인메뉴 화면 이동
					((MenuFrame)getTopLevelAncestor()).moveToPanel("menu");
				} else if(txt_password.getPassword().length <= 3){
					JOptionPane.showMessageDialog(getRootPane(), "비밀번호를 4자리 이상 입력하세요.");
				} else {
					JOptionPane.showMessageDialog(getRootPane(), "회원 정보를 모두 입력하세요.");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				joinActionButton.setIcon(joinActionEnteredButtonImage);
				joinActionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				joinActionButton.setIcon(joinActionButtonImage);
				joinActionButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

		});
		add(joinActionButton);
		
		/* joinBackButton */
		joinBackButton.setVisible(true);
		joinBackButton.setBounds(90, 500, 180, 60);
		joinBackButton.setBorderPainted(false);
		joinBackButton.setContentAreaFilled(false);
		joinBackButton.setFocusPainted(false);
		joinBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 메인메뉴 화면 이동
				((MenuFrame)getTopLevelAncestor()).moveToPanel("menu");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				joinBackButton.setIcon(joinBackEnteredButtonImage);
				joinBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				joinBackButton.setIcon(joinBackButtonImage);
				joinBackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		add(joinBackButton);
	}
}
