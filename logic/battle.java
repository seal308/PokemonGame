package logic;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import pokemonBattleJUnit.Move;
import presentation.Scene;

public class Battle {
	private Trainer player;
	private Trainer computer;
	private MoveGenerator moveGen;

	/*
	 * TODO: If comp attack no effect, if another poke, go to next pokemon
	 */

	public Battle() {
		player = new Trainer("Drew");
		computer = new Trainer("Jessie");
		// startBattle();
	}

	public void computerTurn() {

		Scene.printToScreen("\n" + computer.getName() + "\'s turn...");

		moveGen = new MoveGenerator();

		Pokemon compPoke = computer.getCurrPokemon();
		// should have 4 moves in here
		HashSet<Move> compMoves = compPoke.getMoves();
		int randIndex = new Random().nextInt(compMoves.size());

		int i = 0;
		Move attackMove = null;
		// only 4 items so doesn't take long to search
		for (Move move : compMoves)
		{
			if (i == randIndex)
			{
				attackMove = move;
			}
			i++;
		}
		// if player is defeated, player.getCurrPokemon is null
		compPoke.attack(player.getCurrPokemon(), attackMove);

		// return (compPoke.getName() + " uses " + attackMove.toString());
	}

	public void playerTurn() {
		// display available moves
		// user selects move
		// attack computer pokemon with move
		HashSet<Move> playerMovesSet = player.getCurrPokemon().getMoves();
		Move[] playerMoves = new Move[playerMovesSet.size()];
		// I don't really get how the below line works
		playerMovesSet.toArray(playerMoves);

		Scanner in;
		boolean validInput = false;
		while (validInput == false)
		{
			Scene.printToScreen("\nEnter the # of the move you want to use");

			for (int i = 0; i < playerMoves.length; i++)
			{
				System.out.print(i + 1 + ": " + playerMoves[i].toString() + "\n");
			}
			try
			{
				in = new Scanner(System.in);
				int userIndex = in.nextInt();
				userIndex--;
				if (userIndex >= 0 && userIndex <= 3)
				{
					validInput = true;
					player.getCurrPokemon().attack(computer.getCurrPokemon(), playerMoves[userIndex]);
				} else
				{
					Scene.printToScreen("Invalid command #");
				}
			} catch (InputMismatchException e)
			{
				Scene.printToScreen("Invalid command #, this is not a number");
			}

		}

		/*
		for (Move move : playerMoves)
		{
			System.out.print(move.toString() + ", ");
		}
		
		System.out.println();
		*/

	}

	public Trainer getPlayer() {
		return player;
	}

	public Trainer getComputer() {
		return computer;
	}

	public boolean isGameOver() {
		boolean gameOver = false;
		if (player.checkLost() || computer.checkLost())
		{
			gameOver = true;
		}

		return gameOver;
	}

	public Trainer whoWon() {
		Trainer winner;
		if (player.checkLost())
		{
			winner = computer;
		} else
		{
			winner = player;
		}

		return winner;
	}

	public void startBattle() {

		while (isGameOver() == false)
		{
			Pokemon playerPoke = player.getCurrPokemon();
			Pokemon computerPoke = computer.getCurrPokemon();

			Scene.printToScreen(player.getName() + "\'s " + playerPoke.getName() + " hp=" + playerPoke.getHP() + " vs "
					+ computer.getName() + "\'s " + computer.getCurrPokemon().getName() + " hp="
					+ computerPoke.getHP());
			computerTurn();
			if (isGameOver() == false)
			{
				playerTurn();
			}
			System.out.println("\n\n");
		}
		if (whoWon().equals(player))
		{
			Scene.printToScreen("Trainer " + player.getName() + " defeated Trainer " + computer.getName());
		} else
		{
			Scene.printToScreen("Trainer " + computer.getName() + " defeated Trainer " + player.getName());
		}
	}

}
