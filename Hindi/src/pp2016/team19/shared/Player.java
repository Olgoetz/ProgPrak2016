package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

import pp2016.team19.server.engine.Game;

public class Player extends Character implements Serializable {

	private static final long serialVersionUID = -3344880853631540753L;

	private String name;
	private String password;
	private boolean isLoggedIn;

	private boolean hasKey;
	private int numberOfPotions;
	private int potionEffect = 40;
	private int score;

	private LinkedList<Node> pathToPos = new LinkedList<Node>();

	public Player(String userName, String password) {
		super();

		setName(userName);
		setPassword(password);
		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
		setMaxHealth(getHealth());
	}

	public Player() {
		super();
		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
		setMaxHealth(getHealth());
		setName("Anonymous");
	}

	public Player(Game game) {
		super(game);

		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
		setMaxHealth(getHealth());
		setName("Anonymous");
	}

	public LinkedList<Node> moveToPos(int xGoal, int yGoal) {
		if (pathToPos.isEmpty()) {
			pathToPos = AStarSearch(this.getXPos(), this.getYPos(), xGoal, yGoal);
			pathToPos.addLast(new Node(xGoal, yGoal));
		}
		
		Tile[][] gameMap = getGame().getGameMap();
		System.out.println("\n");
		for (int i = 0; i < getGame().getGameSize(); i++) {
			for (int j = 0; j < getGame().getGameSize(); j++) {

				if (gameMap[i][j].isEntry()) {
					System.out.print("S ");

				} else if (gameMap[i][j].isExit()) {
					System.out.print("Z ");

				} else if (gameMap[i][j].containsKey()) {
					System.out.print("K ");

				} else if (gameMap[i][j].containsPotion()) {
					System.out.print("P ");
					
				} else if (gameMap[i][j].containsMonster()) {
					System.out.print("M ");

				} else if (gameMap[i][j].isRock()) {
					System.out.print("+ ");

				} else if (gameMap[i][j].isFloor()) {
					System.out.print("o ");

				}
				
			


			}
			System.out.println("");
		}
		
		return pathToPos;
	}

	// Method to take the key
	public void takeKey() {
		hasKey = true;
	}

	// Method to remove the key
	public void removeKey() {
		hasKey = false;
	}

	public int usePotion() {
		setNumberOfPotions(numberOfPotions - 1);
		health = Math.min(health + potionEffect, getMaxHealth());
		return health;
	}

	public void takePotion() {
		numberOfPotions++;
	}

	public void setNumberOfPotions(int quantity) {
		if (quantity >= 0)
			numberOfPotions = quantity;
	}

	public int getNumberOfPotions() {
		return numberOfPotions;
	}

	// Does the player have the key?
	public boolean hasKey() {
		return hasKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "Player: " + this.getName() + ", PW: " + this.getPassword() + ", Pos: " + this.getXPos() + ", "
				+ this.getYPos();
	}

	public Monster monsterToAttack() {
		for (int i = 0; i < getGame().getMonsters().size(); i++) {
			Monster m = getGame().getMonsters().get(i);

			// Is the player able to attack?
			boolean ableToAttack = false;
			if (m.getType() == 0)
				ableToAttack = true;
			if (m.getType() == 1)
				ableToAttack = hasKey;

			if ((Math.sqrt(Math.pow(getXPos() - m.getXPos(), 2) + Math.pow(getYPos() - m.getYPos(), 2)) < 2)
					&& ableToAttack) {
				return m;
			}
		}

		return null;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void increaseScore(int points) {
		score = score + points;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void logIn() {
		this.isLoggedIn = true;
	}
	public void logOut() {
		this.isLoggedIn = false;
	}
}
