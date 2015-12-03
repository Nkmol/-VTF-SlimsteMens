package Models;

public class Player {
	/* MIJN EERSTE COMMIT */
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
