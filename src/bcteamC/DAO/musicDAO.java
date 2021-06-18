package bcteamC.DAO;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import bcteamC.DTO.musicDTO;

public class musicDAO {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@1.242.161.226:1521:orcl";
	String id = "c##scott";
	String pw = "tiger";
	
	public musicDAO() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	// select
	
	public ArrayList<musicDAO> select(String name) {
		
		ArrayList<musicDAO> list = new ArrayList<musicDAO>();
		
		java.sql.Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			con = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM music WHERE name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			res = pstmt.executeQuery();
			
			
			while(res.next()) {
				int Id = res.getInt("musicId");
				String musicName = res.getString("name");
				String difficulty = res.getString("difficulty");
				
				musicDTO music = new musicDTO(Id, musicName, difficulty);
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
		
		public ArrayList<musicDAO> insert(int Id, String name, String difficulty) {
			
			java.sql.Connection con = null;
			java.sql.PreparedStatement pstmt = null;
			
			try {
				con = DriverManager.getConnection(url, id, pw);
				
				String sql = "INSERT INTO game VALUES (music_seq.nextval,?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, difficulty);
							
				int result = pstmt.executeUpdate();
				
				if(result == 1) {
					System.out.println(name + "노래를 등록하였습니다.");
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
