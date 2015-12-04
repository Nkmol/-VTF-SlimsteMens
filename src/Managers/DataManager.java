package Managers;

public class DataManager {

	private static DataManager instance = null;
	
	protected DataManager() {
		
	}
	
	public static DataManager getInstance() {
		if(instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
}
