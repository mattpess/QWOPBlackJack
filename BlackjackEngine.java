/**
 * QWOP Bois
 * 6/12/2019
 * CPSC 470
 * BlackjackEngine functions to run a full game of blackjack until a single player is left. 
 * This class handles the dealer, consults players using the PlayerIf format for strategies, and automates the game.
 *
 */

package project2;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlackjackEngine {

	// Singleton instance of the engine
	private static BlackjackEngine instance;
	// Game list of players
	private ArrayList<PlayerIf> players;
	// Banks corresponding to the player of the same index as the players list
	private int[] banks;
	// Server that may be used depending on the choice of the user
	private BlackjackOnline server;
	
	/**
	 * Private constructor; only accessed if there is not currently an existing copy of the singleton
	 * @param players - list of players
	 * @param banks - list of bank values
	 */
	private BlackjackEngine(ArrayList<PlayerIf> players, int[] banks) {
		this.players = players;
		this.banks = banks;
		this.server = null;
	}
	
	/**
	 * Private constructor; only accessed if there is not currently an existing instance, creating one with a server.
	 * @param players - list of players
	 * @param banks - list of bank values
	 * @param server - online server
	 */
	public BlackjackEngine(ArrayList<PlayerIf> players, int[] banks, BlackjackOnline server) {
		this.players = players;
		this.banks = banks;
		this.server = server;
	}

	/**
	 * Public access for BlackjackEngine objects; checks if an instance exists already and provides it if so.
	 * @param players - list of players
	 * @param banks - list of bank values
	 * @return BlackjackEngine object for running Blackjack games.
	 */
	public static BlackjackEngine getInstance(ArrayList<PlayerIf> players, int[] banks) {
		if(instance == null) {
			instance = new BlackjackEngine(players, banks);
		}
		return instance;
	}

	public static BlackjackEngine getInstance(ArrayList<PlayerIf> players, int[] banks, BlackjackOnline server) {
		if(instance == null) {
			instance = new BlackjackEngine(players, banks, server);
		}
		return instance;
	}
	

	
	public void playGame() {
		// players and banks are already initialized
		

		// create array of bets (player at index 0 corresponds to bets at index 0, etc etc)
		int round = 1;

		
		int [] bets = new int[players.size()];
		ArrayList<String> names = new ArrayList<String>();
		for(PlayerIf player : players) {
			names.add(player.getName());
		}
		
		while(players.size() > 1) {
			
			this.print("\n\nROUND " + round +"\n\n");

			// generate a random list of cards for a sample deck of 52

			int shoeSize = 52;
			int numCardsLeft = shoeSize;
			String[] deck = createNewDeck(shoeSize);
			String[] playedCards = new String[shoeSize];

			// place bets
			
			int betIndex = 0;
			
			// loop to get each player's betting strategy and place it into the bets[] array.
			
			for(PlayerIf player : players) {
				bets[betIndex] = player.placeBet(banks[betIndex], playedCards, numCardsLeft);
				this.print(names.get(betIndex) + " Bank = " + banks[betIndex]);
				this.print(names.get(betIndex) + " bets " + bets[betIndex] + "\n==========");
				betIndex++;
			}
			

			//deal initial cards
			
			// array of array of strings keeps track of player hands
			String[][] playerHands = new String[players.size()][10];
			
			// nextShoeIndex tracks where in the deck we currently are
			int nextShoeIndex = 0;
			// player index tracks what player we are dealing to next.
			int playerIndex = 0;
			
			// This loop creates empty hands for players (full of "0") and deals the first 2 cards for each.
			// These are maintained in the created 2D array.
			for(PlayerIf player : players) {
		
				playerHands[playerIndex] = createEmptyHand(10);
				playerHands[playerIndex][0] = deck[nextShoeIndex];
				playerHands[playerIndex][1] = deck[nextShoeIndex + 1];

				// index for cards left, playerIndex, and nextShoeIndex must be maintained after every loop
				numCardsLeft -= 2;
				playerIndex++;
				nextShoeIndex += 2;
			}

			// The dealer now gets his own hand, which has separate variables from the players for consistent calling
			String[] dealerCards = createEmptyHand(10);
			String dealerUpCard = "";
			int nextDealerHandIndex = 2;
			dealerCards[0] = deck[nextShoeIndex];
			dealerCards[1] = deck[nextShoeIndex + 1];
			
			// The dealer always has one card revealed
			dealerUpCard = dealerCards[1];
			
			nextShoeIndex += 2;
			numCardsLeft -= 2;

			// A loop for the actual gameplay of the round
			// Each player consults their strategy and determines if they will hit or not
			playerIndex = 0;
			for(PlayerIf player : players){
				// each player only has 2 cards currently, so they will all start at index 2 for the next card
				int handIndex = 2;
				boolean doesPlayerHit = true;
				while (doesPlayerHit) {
					printCurrentHandInfo(playerHands[playerIndex]);
					doesPlayerHit = player.doesPlayerHit(playerHands[playerIndex], dealerUpCard);
					if (doesPlayerHit) {
						playerHands[playerIndex][handIndex] = deck[nextShoeIndex];
						nextShoeIndex++;
						numCardsLeft--;
						handIndex++;
						this.print(names.get(playerIndex) +  " hits.");
					}
					else
						this.print(names.get(playerIndex) + " stands.");
				}
				playerIndex++;
			}

			// The dealer now decides after all players have gone if he will hit or stay based on his own rigid strategy
			boolean doesDealerHit = true;
			while (doesDealerHit) {
				printCurrentHandInfo(dealerCards);
				doesDealerHit = BlackjackRules.doesDealerHit(dealerCards);
				if (doesDealerHit) {
					dealerCards[nextDealerHandIndex] = deck[nextShoeIndex];
					nextShoeIndex++;
					numCardsLeft--;
					nextDealerHandIndex++;
					this.print("Dealer hits.");
				}
				else
					this.print("Dealer stands.");
			}

			// Points are now determined; the dealer is posted first, then each player gets their own points calculated
			int dealerPoints = BlackjackRules.countPoints(dealerCards);
			this.print("==========\nDealer has: " + dealerPoints);
			
			playerIndex = 0;
			int[] points = new int[players.size()];
			
			while(playerIndex < players.size()) {
				points[playerIndex] = BlackjackRules.countPoints(playerHands[playerIndex]);
				this.print(names.get(playerIndex) + " has: " + points[playerIndex]);
				playerIndex++;
			}
			

			// Figure out the winner
			
			// Firstly, check for dealer Blackjack; this happens when the first two cards are an Ace and a face card or a 10.
			// We check the point value and that there are only two cards (thus the third card is blank)
			if(dealerPoints == 21 && dealerCards[2] == "0") {
				playerIndex = 0;
				for(String[] hands : playerHands) {
					// check that each player does or does not have Blackjack as well
					if(points[playerIndex] == 21 && hands[2] == "0") {
						this.print("Push game for "+ names.get(playerIndex) + " with their own Blackjack.");
					}  else {
					// if no Blackjack, the player loses equal to their bet
						this.print(names.get(playerIndex) + " lost to the dealer's Blackjack.");
						banks[playerIndex] -= bets[playerIndex];
					}
				playerIndex++;
				}
			}
			
			// Next, we deal with if the dealer went above 21
			// In this case, all players win if they were below or at 21
			
			else if(dealerPoints > 21) {
				this.print("Dealer has busted.");
				playerIndex = 0;
				for(String[] hands : playerHands) {
					// check that each player did not go over as well
					if(points[playerIndex] < 22) {
						// check for Blackjack for x1.5 bet
						if(points[playerIndex] == 21 && hands[2] == "0") {
							this.print(names.get(playerIndex) + " wins with a Blackjack.");
							banks[playerIndex] += bets[playerIndex] * 1.5;
						// otherwise this is a normal win
						}  else {
						this.print(names.get(playerIndex) + 1 + " wins.");
						banks[playerIndex] += bets[playerIndex];
						}
					}  else {
					// the player has gone over 21, and loses their bet
						this.print(names.get(playerIndex) + " has also busted.");
						banks[playerIndex] -= bets[playerIndex];
					}
				playerIndex++;
				}
			}
			
			// Otherwise, we have to compare each player to the dealer's points (still check for player Blackjack at the beginning)
			else {
				playerIndex = 0;
				for(String[] hands : playerHands) {
					// player has won with 21 points because dealer does not have 21
					if(points[playerIndex] == 21 && dealerPoints != 21) {
						// check that player has Blackjack for bonus money
						if(hands[2] == "0") {
							this.print(names.get(playerIndex) + " wins with a Blackjack.");
							banks[playerIndex] += bets[playerIndex] * 1.5;
						} else {
							this.print(names.get(playerIndex) + " wins.");
							banks[playerIndex] += bets[playerIndex];
						}
					// player has a higher point value
					} else if(points[playerIndex] > dealerPoints) {
						if(points[playerIndex] > 21) {
							this.print(names.get(playerIndex) + " busts.");
							banks[playerIndex] -= bets[playerIndex];
						}else {
							this.print(names.get(playerIndex) + " wins.");
							banks[playerIndex] += bets[playerIndex];
						}
					// player has a lower point value
					} else if(points[playerIndex] < dealerPoints) {
						this.print(names.get(playerIndex) + " lost.");
						banks[playerIndex] -= bets[playerIndex];
					// player has an equal point value
					} else {
						this.print(names.get(playerIndex) + " pushes.");
					}

						playerIndex++;
				}
			}
			
			this.print("==========");
			// print out player banks
			playerIndex = 0;
			while(playerIndex < players.size()) {
				this.print(names.get(playerIndex) + "'s bank = " + banks[playerIndex]);
				playerIndex++;
			}
			
			// remove any players with zero or less in their bank (though less than zero should be impossible...?)
			playerIndex = 0;
			int[] deadPlayers = new int[players.size()];
			int size = 0;
			for(PlayerIf player : players) {
				if(banks[playerIndex] <= 0) {
					this.print(names.get(playerIndex) + " has been removed from the game after losing all their money.");
					deadPlayers[size] = players.indexOf(player);
					size++;
					names.remove(playerIndex);
				}
				playerIndex++;
			}
			// There is issues with removing things from an ArrayList while looping through it, so instead we write down which index for each player that loses this round
			// Here we can delete them
			size--;
			while(size >= 0) {
				players.remove(deadPlayers[size]);
				int index = deadPlayers[size];
				while(index < players.size()) {
					banks[index] = banks[index+1];
					index++;
				}
				size--;
			}
		round++;
		}
		
		this.print("Game over. The final remaining player is " + names.get(0));
		this.print("Thanks for playing!");
		if(server != null) {
			server.close();
		}
	}

	/**
	 * @param handSize - the number of card slots in the hand
	 * @return an array of empty card slots
	 */
	private static String[] createEmptyHand(int handSize) {
		// create empty array
		String[] hand = new String[handSize];
		for (int i=0; i<handSize; i++) {
			hand[i] = "0";
		}
		return hand;
	}

	/**
	 * @param shoeSize - the number of cards in the "shoe". This is usually a multiple of 52
	 * @return an array of cards representing a deck of the specified number of cards
	 */
	private static String[] createNewDeck(int shoeSize) {

		String[] deck = new String[shoeSize];
                int k = 0;
                for (int i = 0; i <= 3; i++) { //Builds deck of 52 cards
                    for (int j = 1; j <= 13; j++) {
                        switch(j) {
                            case 1:
                                deck[k] = "A";
                                break;
                            case 11:
                                deck[k] = "J";
                                break;
                            case 12:
                                deck[k] = "Q";
                                break;
                            case 13:
                                deck[k] = "K";
                                break;
                            default:
                                deck[k] = "" + j;
                            
                        }
                        k++;
                    }
                }
                //Deck shuffles and returns
                List<String> deckShuff = Arrays.asList(deck);
                Collections.shuffle(deckShuff);
		return deck;
	}
	
	/**
	 * @param cards - an array of cards to be printed
	 */
	private static void printCurrentHandInfo(String[] cards) {
		
		String printout = "";
		int totalPoints = BlackjackRules.countPoints(cards);
		printout += "\nCurrent Hand\nCards:";
		for (String card : cards) {
			if (!card.equals("0")) {
				printout += " " + card;
			}
		}
		printout += ("\nPoints: " + totalPoints);
		instance.print(printout);
	}
	
	private void print(String message) {
		if(server == null) {
			System.out.println(message);
		} else {
			server.write(message);
		}
	}
}
