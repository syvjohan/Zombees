package gamestates;

public class GameStateManager {
	
	static GameStateManager singleton;
	
	GameStateBase currentState = null;
	
	public static GameStateManager getInstance() {
		if(singleton == null) {
			singleton = new GameStateManager();
		}

		return singleton;
	}


	public static void update(double deltaTime) {
		singleton.memberUpdate(deltaTime);
	}
	
	
	private GameStateManager()  {
	
	}	

	private void memberUpdate(double deltaTime) {

	}
	
	
}
