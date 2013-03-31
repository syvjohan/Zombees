package gamestates;

enum GameState { SinglePlayer, MultiPlayer, Menu, Exit }

public abstract class GameStateBase {
	
	public abstract GameState GetNextState();
	
}
