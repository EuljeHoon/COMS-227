package hw2;

/**
 * Model of a Monopoly-like game. Two players take turns rolling dice to move
 * around a board. The game ends when one of the players has at least
 * MONEY_TO_WIN money or one of the players goes bankrupt (has negative money).
 * 
 * @author YOUR_NAME_HERE
 */
public class CyGame extends java.lang.Object{
/**
 * Do nothing square type.
 */
	public static final int DO_NOTHING = 0;
	/**
	 * Pass go square type.
	 */
	public static final int PASS_GO = 1;
	/**
	 * Cyclone square type.
	 */
	public static final int CYCLONE = 2;
	/**
	 * Pay the other player square type.
	 */
	public static final int PAY_PLAYER = 3;
	/**
	 * Get an extra turn square type.
	 */
	public static final int EXTRA_TURN = 4;
	/**
	 * Jump forward square type.
	 */
	public static final int JUMP_FORWARD = 5;
	/**
	 * Stuck square type.
	 */
	public static final int STUCK = 6;
	/**
	 * Points awarded when landing on or passing over go.
	 */
	public static final int PASS_GO_PRIZE = 200;
	/**
	 * The amount payed to the other player per unit when landing on a
	 * PAY_PLAYER square.
	 */
	public static final int PAYMENT_PER_UNIT = 20;
	/**
	 * The amount of money required to win.
	 */
	public static final int MONEY_TO_WIN = 400;
	/**
	 * The cost of one unit.
	 */
	public static final int UNIT_COST = 50;
	
	/**
	 * The variables for number of squares, starting money, player 1,2's square, and Money, current Player, player 1,2's units, and square type and the boolean for game end.
	 */
	private int numSquares;
	private int startingMoney;
	private int player1Sq;
	private int player2Sq;
	private int player1M;
	private int player2M;
	private int currentPlayer;
	private int player1Unit;
	private int player2Unit;
	private int type;
	private boolean gameEnd;
	
	/**
	 * It gives player 1,2's starting money and units, square, and lastly,current player, and state false for gameEnd.
	 * @param numSquares
	 * @param startingMoney
	 */
	public CyGame(int numSquares, int startingMoney) {
			this.numSquares = numSquares;
			this.startingMoney = startingMoney;
			player1Sq = 0;
			player2Sq = 0;
			player1M = startingMoney;
			player2M = startingMoney;
			currentPlayer = 1;
			player1Unit = 1;
			player2Unit = 1;
			gameEnd = false;
	}
	
	/**
	 * This part is for the buy unit. It shows players are losing money if they buy units and gaining units. And also it shows when players can buy the units.
	 */
	public void buyUnit() {
		if (getSquareType(player1Sq) == DO_NOTHING && player1M >= UNIT_COST && getCurrentPlayer() == 1 && !isGameEnded()) {
					player1M -= UNIT_COST;
					player1Unit += 1;
					endTurn();
			}
		else if (getSquareType(player2Sq) == DO_NOTHING && player2M >= UNIT_COST && getCurrentPlayer() == 2 && !isGameEnded()) {
					player2M -= UNIT_COST;
					player2Unit += 1;
					endTurn();
			}
		}
	
	/**
	 * If current player was 1, next player is 2. And if current player was 2, next player is 1. 
	 */
	public void endTurn() {
			if (getCurrentPlayer() == 1) {
				currentPlayer = 2;
			}
			else if (getCurrentPlayer() == 2) {
				currentPlayer = 1;
			}
		}
	
	
	/**
	 * return current player.	
	 * @return
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * return player 1's Money.
	 * @return
	 */
	public int getPlayer1Money() {
		return player1M;
	}
		
	/**
	 * return player 1's Square.
	 * @return
	 */
	public int getPlayer1Square() {
		return player1Sq;
	}
	
	/**
	 * return player 1's Unit.
	 * @return
	 */
	public int getPlayer1Units() {
		return player1Unit;
	}
	
	/**
	 * return player 2's Money.
	 * @return
	 */
	public int getPlayer2Money() {
		return player2M;
	}
	
	/**
	 * return player 2's Square.
	 * @return
	 */
	public int getPlayer2Square() {
		return player2Sq;
	}
	
	/**
	 * return player 2's Unit.
	 * @return
	 */
	public int getPlayer2Units() {
		return player2Unit;
	}
	
	/**
	 * It gives functions for each squares. And return functions.
	 * @param square
	 * @return
	 */
	public int getSquareType(int square) {
		
		if (square == 0) {
			type = PASS_GO;
		}
		else if (square == numSquares - 1) {
			type = CYCLONE;
		}
		else if (square % 5 == 0) {
			type = PAY_PLAYER;
		}
		else if (square % 7 == 0 || square % 11 == 0) {
			type = EXTRA_TURN;
		}
		else if (square % 3 == 0) {
			type = STUCK;
		}
		else if (square % 2 == 0) {
			type = JUMP_FORWARD;
		}

		else {
			type = DO_NOTHING;
		}
			
		return type;
	}
	
	/**
	 * It shows when game is end.
	 * @return
	 */
	public boolean isGameEnded() {
		if(player1M >= MONEY_TO_WIN || player2M >= MONEY_TO_WIN || player1M < 0 || player2M < 0) {
			gameEnd = true;
		}
		return gameEnd;
	}
		
	/**
	 * It shows when players arrived on the certain square, it shows the functions of each squares.
	 * @param value
	 */
	public void roll (int value) {
		int newSquare;
		
		if (gameEnd == true) {
			return;
		}
		else if (gameEnd == false) {
			if (currentPlayer == 1) {
				if (getSquareType(player1Sq) == STUCK && value % 2 != 0) {
					endTurn();
					return;
				}
				else {
					newSquare = player1Sq + value;
					player1Sq = player1Sq + value;
					player1Sq = player1Sq % numSquares;
				}
				if (getSquareType(player1Sq) == PASS_GO || newSquare > numSquares) {
					player1M += PASS_GO_PRIZE;
					player1Unit += 1;
				}
				else if (getSquareType(player1Sq) == CYCLONE) {
					player1Sq = player2Sq;
				}
				else if (getSquareType(player1Sq) == PAY_PLAYER) {
					player1M -= PAYMENT_PER_UNIT * player2Unit;
					player2M += PAYMENT_PER_UNIT * player2Unit;
				}
				else if (getSquareType(player1Sq) == JUMP_FORWARD) {
					player1Sq += 4;
					if (player1Sq > numSquares) {
						player1Sq = player1Sq % numSquares;
					}
				}
				else if (getSquareType(player1Sq) == EXTRA_TURN) {
					endTurn();
				}
				endTurn();	
			}
			
			else if (currentPlayer == 2) {
				if (currentPlayer == 2) {
					if (getSquareType(player2Sq) == STUCK && value % 2 != 0) {
						endTurn();
						return;
					}
					else {
						newSquare = player1Sq + value;
						player2Sq += value;
						player2Sq = player2Sq % numSquares;
					}
					if (getSquareType(player2Sq) == PASS_GO || newSquare > numSquares) {
						player2M += PASS_GO_PRIZE;
						player2Unit += 1;
					}
					else if (getSquareType(player2Sq) == CYCLONE) {
						player2Sq = player1Sq;
					}
					else if (getSquareType(player2Sq) == PAY_PLAYER) {
						player2M -= PAYMENT_PER_UNIT * player1Unit;
						player1M += PAYMENT_PER_UNIT * player1Unit;
					}
					else if (getSquareType(player2Sq) == JUMP_FORWARD) {
						player2Sq += 4;
						if (player2Sq > numSquares) {
							player2Sq = player2Sq % numSquares;
						}
					}
					else if (getSquareType(player2Sq) == EXTRA_TURN) {
						endTurn();
					}
					endTurn();	
				}
			}
		}
	}

/**
 * It shows when players are selling their units. They are gaining money if they sold their units.	
 */
	public void sellUnit() {
		if (player1Unit > 0 && getCurrentPlayer()==1 && !isGameEnded()) {
			player1Unit -= 1;
			player1M += UNIT_COST;
			endTurn();
		}
			
		else if (player2Unit > 0 && getCurrentPlayer()==2 && !isGameEnded()) {
			player2Unit -= 1;
			player2M += UNIT_COST;
			endTurn();
		}
	}
		
		
/**
 * return the result of this code.		
 */
	public String toString() {
		String fmt = "Player 1%s: (%d, %d, $%d) Player 2%s: (%d, %d, $%d)";
		String player1Turn = "";
		String player2Turn = "";
		if (getCurrentPlayer() == 1) {
			player1Turn = "*";
		} 
		else {
			player2Turn = "*";
		}
		return String.format(fmt, player1Turn, getPlayer1Square(), getPlayer1Units(), getPlayer1Money(),player2Turn, getPlayer2Square(), getPlayer2Units(), getPlayer2Money());
	}
}