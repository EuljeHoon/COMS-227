package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;
import api.Direction;
import api.Move;
import api.Orientation;

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 * @author Jehoon Park
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left cornner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of blocks that are positioned on the board.
	 */
	private ArrayList<Block> blocks;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory;
	private Move move;
	/**
	 * This is for the block which are grabbed. it notifies the grabbed block in cell.
	 */
	private Block grabbedBlock;
	/**
	 * This is counting how many block did player moved. It count up when moveGrabbedBlock worked.
	 */
	private int moveCount;
	/**
	 * This is for the Column in grid. It gives a size (how big) of the Column.
	 */
	private int colSize;
	/**
	 * This is for the Row in grid. It gives a size (how big) of the row.
	 */
	private int rowSize;
	/**
	 * This is the block that had been grabbed. It can be also used for the moveGrabbedBlock.
	 */
	private Cell grabbedCell;

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		// TODO
		this.moveHistory = new ArrayList<Move>();
		this.grid = grid;
		this.blocks = new ArrayList<Block>();
		this.moveCount = 0;
		this.colSize = grid[0].length;
		this.rowSize = grid.length;
		
		for (int i = 0; i < blocks.size(); i++) {
			this.blocks.add(blocks.get(i));
			Block temp = blocks.get(i);
			int col = temp.getFirstCol();
			int row = temp.getFirstRow();
			
			if (temp.getOrientation() == HORIZONTAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					this.grid[row][col + j].setBlock(temp);
				}
			} else if (temp.getOrientation() == VERTICAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					this.grid[row + j][col].setBlock(temp);
				}
			}
		}

	}

	/**
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		// TODO
		grabbedBlock = grid[row][col].getBlock();
		grabbedCell = grid[row][col];
		

	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		// TODO
		grabbedBlock = null;
		grabbedCell = null;
	}

	/**
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 */
	public Block getGrabbedBlock() {
		// TODO
		return grabbedBlock;
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {
		// TODO
		return grabbedCell;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	public boolean canPlaceBlock(int row, int col) {
		if((grid[row][col].isFloor() || grid[row][col].isExit()) && !grid[row][col].hasBlock()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		// TODO
		return moveCount;
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		// TODO
		//int rowSize = grid[0].length;
		return rowSize;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		// TODO
		//int colSize = grid.length;
		return colSize;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		// TODO
		return grid[row][col];
	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	public ArrayList<Block> getBlocks() {
		// TODO
		return blocks;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		// TODO
		//check if the grid has a block at exit square
		for (int i = 0; i < blocks.size(); i++) {
			Block temp = blocks.get(i);
			if (temp.getOrientation() == HORIZONTAL) {
				int col = temp.getFirstCol();
				int row = temp.getFirstRow();
				for (int j = 0; j < temp.getLength(); j++) {
					if(grid[row][col + j].isExit()) {
						return true;
					}
				}
			} else if (temp.getOrientation() == VERTICAL) {
				int col = temp.getFirstCol();
				int row = temp.getFirstRow();
				for (int j = 0; j < temp.getLength(); j++) {
					if (grid[row + j][col].isExit()) {
						return true;
					}
				}
				
			}
			
		}
		return false;
	}
	/**
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBlock(Direction dir) {
		// TODO
	
		if (grabbedBlock == null || isGameOver()) {
			return;
		}
			
			Block temp = grabbedBlock;
			//grabbedBlock.move(dir);
			//int oldRow = grabBlock.getFirst
			
			int col = temp.getFirstCol();
			int row = temp.getFirstRow();

			if (grabbedBlock.getOrientation() == HORIZONTAL) {
				if (dir == LEFT && (canPlaceBlock(row, col - 1))) {
					
				}
				else if (dir == RIGHT && (canPlaceBlock(row, col + grabbedBlock.getLength()))) {
					
				}
				else {
					return;
				}
			}
			else {
				if(dir == UP && (canPlaceBlock(row - 1, col))) {
					
				}
				else if (dir == DOWN && (canPlaceBlock(row + grabbedBlock.getLength(), col))) {
					
				}
				else {
					return;
				}
			}
			if (temp.getOrientation() == HORIZONTAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					grid[row][col + j].clearBlock();
				}
			} else if (temp.getOrientation() == VERTICAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					grid[row + j][col].clearBlock();
				}
			}
			
			temp.move(dir);
			col = temp.getFirstCol();
			row = temp.getFirstRow();

			
			if (temp.getOrientation() == HORIZONTAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					grid[row][col + j].setBlock(temp);
				}
			} else if (temp.getOrientation() == VERTICAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					grid[row + j][col].setBlock(temp);
				}
			}
			
			
			
			grabbedCell = grid[row][col];
			moveHistory.add(new Move(grabbedBlock, dir));
			moveCount++;
			
	}
			

   /**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 */
	public void reset() {
		// TODO
		moveCount = 0;
		
		moveHistory.clear();
		
		for (int i = 0; i < blocks.size(); i ++) {
			blocks.get(i).reset();
		}
		for (int i = 0; i < grid.length; i ++) {
			for (int j = 0; j < grid[0].length; j ++) {
				grid[i][j].clearBlock();
			}
		}
		
		for (int i = 0; i < blocks.size(); i++) {
			Block temp = blocks.get(i);
			int col = temp.getFirstCol();
			int row = temp.getFirstRow();
			
			if (temp.getOrientation() == HORIZONTAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					grid[row][col + j].setBlock(temp);
				}
			} else if (temp.getOrientation() == VERTICAL) {
				for (int j = 0; j < temp.getLength(); j++) {
					grid[row + j][col].setBlock(temp);
				}
			}
		}
		

	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		// TODO
		if (isGameOver()) {
			return null;
		}
		ArrayList<Move> legalMoves = new ArrayList <Move>();
		for (int i = 0; i < blocks.size(); i ++) {
			int row = blocks.get(i).getFirstRow();
			int col = blocks.get(i).getFirstCol();
			if(blocks.get(i).getOrientation() == Orientation.HORIZONTAL) {
				if(canPlaceBlock(row, col - 1)) {
					legalMoves.add(new Move(blocks.get(i), LEFT));
				}
				else if(canPlaceBlock(row, col + blocks.get(i).getLength())) {
					legalMoves.add(new Move(blocks.get(i), RIGHT));
				}
			}
			else {
				if(canPlaceBlock(row - 1, col)) {
					legalMoves.add(new Move(blocks.get(i), UP));
				}
				else if(canPlaceBlock(row + blocks.get(i).getLength(), col)) {
					legalMoves.add(new Move(blocks.get(i), DOWN));
				}
			}
		}
		
		return legalMoves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		// TODO

		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		// TODO
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
