package Models;

public class Player {

	private String name;
	private Role role;
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
	}
	
	public void changeRole(Role role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
}
