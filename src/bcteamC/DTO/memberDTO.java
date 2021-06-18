package bcteamC.DTO;

public class memberDTO {

	private String Id;
	private String Pw;
	private String nickname;
	
	public memberDTO(String Id, String Pw, String nickname) {
		this.Id = Id;
		this.Pw = Pw;
		this.nickname = nickname;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPw() {
		return Pw;
	}

	public void setPw(String pw) {
		Pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
