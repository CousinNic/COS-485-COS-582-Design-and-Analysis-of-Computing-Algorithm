package student;

/* 
 * This Skyline class is meant to contain your algorithm.
 * You should implement the static method: findSkyline
 * The input is an ArrayList of Point objects representing the buildings.
 * The output should be a new ArrayList of Point objects with all the buildings
 * merged into a single profile. (Or more than 1 profile if they are non-overlapping.)
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Skyline {
	// Example routine that just looks at the input list of points
	// and constructs a bounding rectangle.
	// This is interesting, but not the correct solution to this problem.
	public static ArrayList<Point> findSkyline(ArrayList<Point> input) {

		// return both points if only one building(two points) is detected
		if (input.size() == 2) {
			return input;

			// if there is more than one building
		} else {
			// splits buildings in half by points, assuming each building consists of two
			// points, Example: 10 will be split into 4 and 6 instead of 5 and 5
			int mid = (input.size() / 2) / 2 * 2;

			// output will be the returned, possibly merged value.
			ArrayList<Point> output = new ArrayList<Point>();
			// left consists of all buildings on t he left half of the split
			ArrayList<Point> left = new ArrayList<Point>();
			left.addAll(input.subList(0, mid));
			// right consists of all buildings on the right half of split
			ArrayList<Point> right = new ArrayList<Point>();
			right.addAll(input.subList(mid, input.size()));

			// merge the result of recursivelly called findSkyline left and right
			output = (mergeSkyline(findSkyline(left), findSkyline(right)));
			return output;
		}
	}

	/**
	 * @param left:  array of points representing left most building
	 * @param right: array of points representing right most building
	 * @return an array of the left and right building points merged
	 */
	public static ArrayList<Point> mergeSkyline(ArrayList<Point> left, ArrayList<Point> right) {

		ArrayList<Point> merged = new ArrayList<Point>();
		int rightx = 0;
		int leftx = 0;
		int oldHeightLeft = 0;
		int oldHeightRight = 0;

		// runs through each point
		while (leftx < left.size() && rightx < right.size()) {

			// assigns leftP and rightP to save my time and precious sanity
			Point leftP = left.get(leftx);
			Point rightP = right.get(rightx);

			// if left and right points have the same x
			// ############################################################################################
			if (leftP.x == rightP.x) {
				// creates new point with larger y of left and right points
				merged.add(new Point(leftP.x, Math.max(leftP.y, rightP.y)));
				// saves heights and increments x values for later
				oldHeightLeft = leftP.y;
				oldHeightRight = rightP.y;
				rightx++;
				leftx++;

				// if left comes before right
				// ############################################################################################
			} else if (leftP.x < rightP.x) {
				// places point if y is greater than old points
				if (leftP.y > oldHeightRight && leftP.y > oldHeightLeft) {
					merged.add(leftP);
				} else {
					// created modified point if y is not greater than old points, and is less than
					// or equal to the old right point height
					if (leftP.y <= oldHeightRight) {
						// prevents extra points from being added
						if (oldHeightRight != merged.get(merged.size() - 1).y)
							merged.add(new Point(leftP.x, oldHeightRight));
					} else {
						merged.add(leftP);
					}

				}
				leftx++;
				oldHeightLeft = leftP.y;

				// if right comes before left
				// ############################################################################################
			} else {
				// places point if y is greater than old points
				if (rightP.y > oldHeightLeft && rightP.y > oldHeightRight) {
					merged.add(rightP);
				} else {
					// created modified point if y is not greater than old points, and is less than
					// or equal to the old right point height
					if (rightP.y <= oldHeightLeft) {
						// prevents extra points from being added
						if (oldHeightLeft != merged.get(merged.size() - 1).y)
							merged.add(new Point(rightP.x, oldHeightLeft));
					} else {
						merged.add(rightP);
					}
				}
				rightx++;
				oldHeightRight = rightP.y;
			}
			// handles final missed points
		}
		while (leftx < left.size()) {
			merged.add(left.get(leftx));
			leftx++;
		}
		while (rightx < right.size()) {
			merged.add(right.get(rightx));
			rightx++;
		}
		return merged;
	}
}
