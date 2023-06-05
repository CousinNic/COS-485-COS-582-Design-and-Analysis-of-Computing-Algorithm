package student;

import tester.Player;
import java.util.*;

/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method: chooseTeams()
 * The input is:
 *   an array of Players (with fields: name and skill)
 *   numTeams - the number of teams to create
 *   
 * Your goal is to divide the players into teams that all have the same 
 * number of players and as equal a total skill sum for each team as is possible.
 * 
 * You don't need to find the optimal result, but try to find something good.
 * 
 * You should return a 2D array of Players where each row is a team.
 */
public class TeamBalancer {

	// demo code that just assigns players in-order to teams
	public static Player[][] chooseTeams(Player[] players, int numTeams) {
		int numPlayers = players.length;
		int playersPerTeam = numPlayers / numTeams; // you may assume this works out evenly
		Player temp;

		// Sort array of players ############################################
		for (int i = 0; i < players.length; i++) {
			for (int a = 0; a < players.length - 1; a++) {
				if (players[a].getSkill() < players[a + 1].getSkill()) {

					temp = players[a];
					players[a] = players[a + 1];
					players[a + 1] = temp;
				}
			}
		}
		// ##################################################################
		int[] skillSums = new int[numTeams];

		// assign players to teams #################################################
		Player[][] teams = new Player[numTeams][playersPerTeam];
		int weakTeam = 0;
		int p = 0; // index into players array

		// loops through players best to worst, assigning each player to the current
		// lowest ranked team.
		// giving a near evenly matched ranking.
		for (int pos = 0; pos < playersPerTeam; pos++) {
			for (int team = 0; team < numTeams; team++) {
				skillSums = findTeamSums(teams, numTeams, numPlayers);
				weakTeam = weakestTeam(skillSums);
				teams[weakTeam][pos] = players[p++];
			}
		}
		// ######################################################################

		// calculate team skill sums
		skillSums = findTeamSums(teams, numTeams, numPlayers);

		// ###############refine##############################################
		Random random = new Random();
		int rowOne;
		int rowTwo;
		int Colum;
		Player tempPlayer;
		int[] newSkillSums;
		int averageScore = (int) findSolutionScore(skillSums);

		//chooses 10000 random player swaps to see which swaps can improve the teams.
		for (int i = 0; i < 10000; i++) {
			//finds random colum and rows to swap
			rowOne = random.nextInt(teams.length - 0);
			rowTwo = random.nextInt(teams.length - 0);
			Colum = random.nextInt(teams[0].length - 0);

			//swaps the players
			tempPlayer = teams[rowOne][Colum];
			teams[rowOne][Colum] = teams[rowTwo][Colum];
			teams[rowTwo][Colum] = tempPlayer;
			
			//recalculates the team sums
			newSkillSums = findTeamSums(teams, numTeams, numPlayers);

			//checks of matchmaking has been improved, if so the saved skillsums is updated
			if (newSkillSums[rowOne] == averageScore || newSkillSums[rowTwo] == averageScore) {
				skillSums = newSkillSums;
				//otherwise the swap is undone
			} else {
				tempPlayer = teams[rowOne][Colum];
				teams[rowOne][Colum] = teams[rowTwo][Colum];
				teams[rowTwo][Colum] = tempPlayer;
			}
		}

		// ##################################################################

		// just print the skill sums
		System.out.println();
		System.out.println("team skill sums:");
		for (int i = 0; i < numTeams; i++)
			System.out.print(" " + skillSums[i]);
		System.out.println();

		return teams;
	}

	/**
	 * method to calculate the skill sum of each team
	 * @param teams
	 * @param numTeams
	 * @param numPlayers
	 * @return array of all teams skill sums
	 */
	public static int[] findTeamSums(Player[][] teams, int numTeams, int numPlayers) {
		int playersPerTeam = numPlayers / numTeams;
		int[] skillSums = new int[numTeams];
		//iterates through teams
		for (int team = 0; team < numTeams; team++)
			//iterates through players, and adds each players score to its team score
			for (int pos = 0; pos < playersPerTeam; pos++)
				if (teams[team][pos] != null) {
					skillSums[team] += teams[team][pos].getSkill();
				}
		return skillSums;
	}

	/**
	 * method to take skill sums, and return the lowest ranked team
	 * @param skillSums
	 * @return location of lowest ranked team
	 */
	public static int weakestTeam(int[] skillSums) {
		int team = 0;
		int smallestSum = 1000;

		//iterates through teams, and checks for smallest score
		for (int i = 0; i < skillSums.length; i++) {
			if (skillSums[i] < smallestSum) {
				smallestSum = skillSums[i];
				team = i;
			}
		}
		return team;
	}

	/**
	 * a method to add each teams player scores to find the colutionScore
	 * @param skillSums
	 * @return array of each teams scores
	 */
	public static double findSolutionScore(int[] skillSums) {
		int length = skillSums.length;
		int sum = 0;

		// sum of all values in array using for loop
		for (int i = 0; i < skillSums.length; i++) {
			sum += skillSums[i];
		}

		double average = sum / length;

		return average;
	}

}
