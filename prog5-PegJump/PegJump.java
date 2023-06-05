package student;

/* 
 * This Student class is meant to contain your algorithm.
 * You should implement the static methods:
 *
 *   solvePegJump - finds a solution and the number of nodes examined in the search
 *                  it should fill in the jumpList argument with the jumps that form
 *                  your solution
 *
 * The input is a PegJumpPuzzle object, which has methods:
 *   numHoles()       returns the number of holes, numbered starting at 0 
 *   getStartHole()   returns the starting hole that is initially empty
 *   jumpIterator()   returns an iterator that iterates through the possible jumps
 *   numJumps()       returns the number of possible jump moves in the puzzle board
 *                    (I never used this.)
 *                    
 * The jumpIterator() returns Jump objects which each represents a valid jump with three
 * fields: 'from', 'over', and 'dest'
 * There are getter methods to get these fields.  
 *   getFrom()         Returns the hole that jump is "from".
 *   getOver()         Returns the hole that the jump is "over".
 *   getDest()         Returns the hole that is the "dest"-ination.
 */
import java.util.ArrayList;
import java.util.Iterator;

import tester.*;

public class PegJump {

	// simple example routine that just repeatedly finds the first valid jump until
	// it fails
	//
	// returns:
	// the number of jumps tried
	// and as a modifiable argument, it fills in the jumpList
	public static boolean isSolved = false;
	public static int startHole;

	public static long solvePegJump(PegJumpPuzzle puzzle, ArrayList<Jump> jumpList) {

		startHole = puzzle.getStartHole();
		long jumpCnt = 0;
		isSolved = false;
		// initialize the puzzle
		// make an array to keep tracks of where the pegs are
		// and put pegs in all holes except the starting hole
		boolean pegs[] = new boolean[puzzle.numHoles()]; // hole numbers start at 0
		for (int i = 0; i < puzzle.numHoles(); i++)
			pegs[i] = true; // fill all holes
		pegs[puzzle.getStartHole()] = false; // clear starting hole
		
		//begin recursion
		jumpCnt = recursion(puzzle, jumpList, jumpCnt, pegs);

		return jumpCnt;
	}
	
	/**
	 * Recursive method to scan through each possible combination of peg jumps
	 * @param puzzle
	 * @param jumpList
	 * @param jumpCnt
	 * @param pegs
	 * @return
	 */
	public static long recursion(PegJumpPuzzle puzzle, ArrayList<Jump> jumpList, long jumpCnt, boolean pegs[]) {
		Iterator<Jump> jitr = puzzle.jumpIterator();
		//iterates through possible jumps
		while (jitr.hasNext()) {
			Jump j = jitr.next();
			//sets jump info
			int from = j.getFrom();
			int over = j.getOver();
			int dest = j.getDest();
			//if jump is valid
			if (pegs[from] && pegs[over] && !pegs[dest]) {
				jumpList.add(j); // add to the result list
				pegs[from] = false; // do the jump
				pegs[over] = false;
				pegs[dest] = true;
				jumpCnt++;

				//checks if solved
				isSolved(pegs, dest);
				if (isSolved) {
					return (jumpCnt);
				}
				//recursively calls self
				jumpCnt = recursion(puzzle, jumpList, jumpCnt, pegs);
				//checks again if solved
				if (isSolved) {
					return (jumpCnt);
				}
				//if this point is reached, jump did not work
				
				//remove jump
				jumpList.remove(jumpList.size() - 1);
				pegs[from] = true; // do the jump
				pegs[over] = true;
				pegs[dest] = false;
				jumpCnt--;
			}
		}

		return jumpCnt;
	}

	/**
	 * uses last peg location, and amount of pegs on board to determine if board is solved
	 * @param pegs
	 * @param node
	 * in place of return, sets a global boolean isSolved to true, which can be used to stop recursion as is
	 */
	public static void isSolved(boolean pegs[], int node) {
		int pegCount = 0;
		for (boolean i : pegs) {
			if (i) {
				pegCount++;
			}
		}
		if (pegCount == 1 && node == startHole) {
			isSolved = true;
		}

	}

}