package pp2016.team19.server.engine;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import pp2016.team19.shared.*;
/**
 * 
 * @author Tobias Schrader
 *
 */
public class GameEngine extends TimerTask{
	LinkedBlockingQueue<Message> messagesFromGame;
	Game game;
	Player player;
	ServerEngine engine;
	public GameEngine(ServerEngine engine, Game game, Player player,LinkedBlockingQueue<Message> messagesFromGame) {
		this.game=game;
		this.player=player;
		this.messagesFromGame = messagesFromGame;
		this.engine = engine;
	}
	
	public void run() {
		for(Monster monster: game.Monsters) {
			//Move Monster
		}
//Player Commands
			Message message = this.messagesFromGame.poll();
			if (message != null) {
				this.distributor(message);
			}
	}
	/**
	 * Determines action depending on subtype
	 * @param message
	 */
	public void distributor(Message message) {
		switch(message.getSubType()) {
		case Move:
			this.playerMove(message);
		default:
			break;
		}
	}
/**
 * Executes player movement command
 * @param message
 */
	private void playerMove(Message message) {
		switch(message.direction) {
		case 0: //MoveUp
			if (game.gameMap[player.getXPos()][player.getYPos()+1].isFloor()) {
				player.setPos(player.getXPos(),player.getYPos()+1);
				engine.messagesToClient.addElement(MessMoveCharacterAns(player.getXPos(),player.getYPos(),type,subtype,true));
			} else {
				engine.messagesToClient.addElement(MessMoveCharacterAns(player.getXPos(),player.getYPos(),type,subtype,false));
			}
				
		}
	}
}