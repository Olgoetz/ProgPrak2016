package pp2016.team19.server.engine;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import pp2016.team19.shared.*;

public class GameEngine extends TimerTask{
	LinkedBlockingQueue<Message> messagesFromGame;
	Game game;
	Player player;
	public GameEngine(Game game, Player player,LinkedBlockingQueue<Message> messagesFromGame) {
		this.game=game;
		this.player=player;
		this.messagesFromGame = messagesFromGame;
	}
	
	public void run() {
		for(Monster monster: game.Monsters) {
			//Move Monster
		}
		try { //Player Commands
			Message message = this.messagesFromGame.poll();
			if (message != null) {
				this.distributor(message);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void distributor(Message message) {
		switch(message.getSubType()) {
		case Move:
			this.playerMove(message);
		default:
			break;
		}
	}

	private void playerMove(Message message) {
		switch(message.direction) {
		case 0: //MoveUp
			if (game.gameMap[player.getXPos()][player.getYPos()+1].isFloor()) {
				player.setPos(player.getXPos(),player.getYPos()+1);
				engine.MessagesToClient.addElement(MessMoveCharacterAns(player.getXPos(),player.getYPos(),type,subtype,true));
			} else {
				engine.MessagesToClient.addElement(MessMoveCharacterAns(player.getXPos(),player.getYPos(),type,subtype,false));
			}
				
		}
	}
}