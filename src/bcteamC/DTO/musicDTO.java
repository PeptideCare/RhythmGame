package bcteamC.DTO;

public class musicDTO {

	private int Id;
	private String name;
	private String difficulty;
	
	public musicDTO(int Id, String name, String difficulty) {
		this.Id = Id;
		this.name = name;
		this.difficulty = difficulty;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	
}
