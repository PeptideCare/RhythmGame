package bcteamC.DAO;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import bcteamC.DTO.memberDTO;

public class memberDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@1.242.161.226:1521:orcl";
	String id = "c##scott";
	String pw = "tiger";
	
	public memberDAO() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	// select
	
	public ArrayList<memberDTO> select(String Id) {
		
		ArrayList<memberDTO> list = new ArrayList<memberDTO>();
		
		java.sql.Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			con = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Id);
			res = pstmt.executeQuery();
			
			
			while(res.next()) {
				String memberId = res.getString("id");
				String Pw = res.getString("password");
				String nickName = res.getString("nickname");
				
				memberDTO member = new memberDTO(memberId, Pw, nickName);
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(res != null) res.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
		
		//insert
		
		public ArrayList<memberDTO> insert(String Id, String Password, String nickName) {
			
			java.sql.Connection con = null;
			java.sql.PreparedStatement pstmt = null;
			
			try {
				con = DriverManager.getConnection(url, id, pw);
				
				String sql = "INSERT INTO member VALUES (?,?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, Id);
				pstmt.setString(2, Password);
				pstmt.setString(3, nickName);
							
				int result = pstmt.executeUpdate();
				
				if(result == 1) {
					System.out.println(Id + "님이 회원가입을 하셨습니다.");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return null;
			
		}
}
