package student;

/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method: findRoute
 * The input is:
 *   the number of rows and columns of
 *   a 2D array of integer heights
 *   a staring Point where row = start.y and col = start.x
 *   a goal Point
 * 
 * You should return an ArrayList of Points(x = col, y = row) for the least cost path
 * from the start to the goal.
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class MountainClimber {
	final static double SQRT2 = Math.sqrt(2.0);
	// Example routine that finds the most direct path.
	// This is a greedy algorithm that would minimize the distance traveled,
	// however it does not find the least cost path when you also consider the
	// cost of height changes.
	public static ArrayList<Point> findRoute(int rows, int cols, int[][] grid, Point start, Point goal) {
		//System.out.println(start);
		
		ArrayList<Point> path = new ArrayList<Point>();
		
		//add start point to path
		path.add(start);
		Point current = start;
		double cost = 0.0; 

		//save grid sizes for future use
		int lengthX = grid.length-1;
		int lengthY = grid[0].length-1;
		
		HashMap<Point, Node> candidateNodes = new HashMap<Point, Node>();
		HashMap<Point, Node> storedNodes = new HashMap<Point, Node>();

		//store start node as candidate
		candidateNodes.put(start, new Node(0.0));
		Point tempPoint;

		do {
			Double lowest = 10000.0;
			Point lowestPoint = new Point(0,0);
			//########search candidate set for the point with the smallest distance
			for (Point key : candidateNodes.keySet()) {
				if(candidateNodes.get(key).distanceFromSource<lowest) {
					lowest = candidateNodes.get(key).distanceFromSource;
					lowestPoint = key;
				}
			}
			tempPoint = lowestPoint;
			storedNodes.put(tempPoint, candidateNodes.get(tempPoint));
			//#######################################################################
				
			//###########################################Check and update all nearby graph points###################
			//verify point is not already locked in, then check every adjacent node and update canidate nodes
			if (storedNodes.containsKey(tempPoint)) {
				if (!storedNodes.containsKey(new Point(tempPoint.x + 1, tempPoint.y)) && tempPoint.x < lengthY) {
					updateDistance(tempPoint, new Point(tempPoint.x + 1, tempPoint.y), candidateNodes, storedNodes,grid,false);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x - 1, tempPoint.y)) && tempPoint.x >= 1) {
					updateDistance(tempPoint, new Point(tempPoint.x - 1, tempPoint.y), candidateNodes, storedNodes,grid,false);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x, tempPoint.y + 1)) && tempPoint.y < lengthX) {
					updateDistance(tempPoint, new Point(tempPoint.x, tempPoint.y + 1), candidateNodes, storedNodes,grid,false);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x, tempPoint.y - 1)) && tempPoint.y >= 1) {
					updateDistance(tempPoint, new Point(tempPoint.x, tempPoint.y - 1), candidateNodes, storedNodes,grid,false);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x + 1, tempPoint.y + 1)) && tempPoint.x < lengthY && tempPoint.y < lengthX) {
					updateDistance(tempPoint, new Point(tempPoint.x + 1, tempPoint.y + 1), candidateNodes, storedNodes,grid,true);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x - 1, tempPoint.y - 1)) && tempPoint.x >= 1 && tempPoint.y >= 1) {
					updateDistance(tempPoint, new Point(tempPoint.x - 1, tempPoint.y - 1), candidateNodes, storedNodes,grid,true);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x + 1, tempPoint.y - 1)) && tempPoint.y >= 1 && tempPoint.x < lengthY) {
					updateDistance(tempPoint, new Point(tempPoint.x + 1, tempPoint.y - 1), candidateNodes, storedNodes,grid,true);
				}
				if (!storedNodes.containsKey(new Point(tempPoint.x - 1, tempPoint.y + 1)) && tempPoint.x >= 1 && tempPoint.y < lengthX) {
					updateDistance(tempPoint, new Point(tempPoint.x - 1, tempPoint.y + 1), candidateNodes, storedNodes,grid,true);
				}
				//deletes the current candidate node from the list
				candidateNodes.remove(tempPoint);
				current = tempPoint;
			}//####################################################################################################
		} while (!current.equals(goal));
		
		Point currentPoint = goal;
		cost = storedNodes.get(goal).distanceFromSource;		
		
		//add discovered path to "path"
		while(!start.equals(currentPoint)) {
			path.add(currentPoint);
			currentPoint = storedNodes.get(currentPoint).parrentPoint;
			cost = storedNodes.get(currentPoint).distanceFromSource;	
		}
		//move start point to end of list
		path.remove(0);
		path.add(start);
		//reverse list 
		Collections.reverse(path);
		cost = storedNodes.get(goal).distanceFromSource;
		
		System.out.println("Cost = " + cost);
		return path;
	}

	/**
	 * method to update candidate node list given the current node to act as the parent, along with the new node 
	 * @param currentPoint : to act as parent node
	 * @param newPoint : point to be added as a candidate node
	 * @param candidateNodes : the map of candidate nodes to add to
	 * @param storedNodes : to retrive the parent nodes distance
	 * @param grid : grid to find height of current point for distance calculation
	 * @param isDiagonal : boolean to determine if vertical or diagonal for distance calculations
	 */
	public static void updateDistance(Point currentPoint, Point newPoint, HashMap<Point, Node> candidateNodes,HashMap<Point, Node> storedNodes, int[][] grid, boolean isDiagonal) {
		Double cost;
		
		//save the diference in height of new node - current node
		int dheight = grid[newPoint.y][newPoint.x] - grid[currentPoint.y][currentPoint.x];
		dheight = Math.abs(dheight);
		
		//calculate distance depending on direction
		if (!isDiagonal) // moving horiz or vertical
			cost = 1.0 + (dheight * dheight * dheight);
		else // moving diagonally
			cost = SQRT2 + ((dheight * dheight * dheight) / 2.0);
		
		//if node is allready a candidate, check if distance is smaller then update
		if (candidateNodes.containsKey(newPoint)) {
			if (candidateNodes.get(newPoint).distanceFromSource > (candidateNodes.get(currentPoint).getDistance() + cost)) {
			candidateNodes.get(newPoint).distanceFromSource = candidateNodes.get(currentPoint).distanceFromSource + cost;
			candidateNodes.get(newPoint).parrentPoint = currentPoint;
			}
		//else node is not yet a candidate, so create new
		} else {
			candidateNodes.put(newPoint, new Node(currentPoint, storedNodes.get(currentPoint).distanceFromSource + cost));
		}
		
	}
}

/**
 * Node class to store a nodes parrent point, with distance from the start
 * @author Nic Drummey
 */
class Node {

	public Point parrentPoint;
	public double distanceFromSource;

	//saves nodes parrent point with a distance from start.
	public Node(Point parrentPoint, double distanceFromSource) {
		this.parrentPoint = parrentPoint;
		this.distanceFromSource = distanceFromSource;
	}

	//for initalizing the start with no parrent node
	public Node(double distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
		parrentPoint = null;
	}
	public Double getDistance() {
		return distanceFromSource;
	}

}
