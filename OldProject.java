/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project.old;

import java.util.Random;

/**
 * A program that can be used by students to test their Player algorithms against a randomly dealt
 * hand. The second line of the main() method must be changed by replacing "SamplePlayer" with the name of
 * the student's file (usually StudentLastNamePlayer)
 */
public class OldProject {

	/**
	 * @param args - not used
	 */
	public static void main(String[] args) {
		// create and initialize player
		SamplePlayer player1 = new SamplePlayer(); // change this to your class name like YourLastNamePlayer
		int bank1 = 100;
		
		// generate a random list of cards for a sample deck of 20
		int shoeSize = 20;
		int numCardsLeft = shoeSize;
		String[] deck = createNewDeck(shoeSize);
		String[] playedCards = new String[shoeSize];
		// place bets
		int bet1 = player1.placeBet(bank1, playedCards, numCardsLeft);
		System.out.println("Player Bank = " + bank1);
		System.out.println("Player bets " + bet1 + "\n==========");
		
		//deal initial cards
		String[] playerCards = createEmptyHand(5);
		String[] dealerCards = createEmptyHand(5);
		String dealerUpCard = "";
		int nextShoeIndex = 0;
		int nextPlayerHandIndex = 2;
		int nextDealerHandIndex = 2;
		playerCards[0] = deck[0];
		dealerCards[0] = deck[1];
		playerCards[1] = deck[2];
		dealerCards[1] = deck[3];
		numCardsLeft -= 4;
		dealerUpCard = dealerCards[1];
		nextShoeIndex = 4;
		
		// player's hand
		boolean doesPlayerHit = true;
		while (doesPlayerHit) {
			printCurrentHandInfo(playerCards);
			doesPlayerHit = player1.doesPlayerHit(playerCards, dealerUpCard);
			if (doesPlayerHit) {
				playerCards[nextPlayerHandIndex] = deck[nextShoeIndex];
				nextShoeIndex++;
				numCardsLeft--;
				nextPlayerHandIndex++;
				System.out.println("Player hits.");
			}
			else
				System.out.println("Player stands.");
		}
		
		// dealer's hand
		boolean doesDealerHit = true;
		while (doesDealerHit) {
			printCurrentHandInfo(dealerCards);
			doesDealerHit = BlackjackRules.doesDealerHit(dealerCards);
			if (doesDealerHit) {
				dealerCards[nextDealerHandIndex] = deck[nextShoeIndex];
				nextShoeIndex++;
				numCardsLeft--;
				nextDealerHandIndex++;
				System.out.println("Dealer hits.");
			}
			else
				System.out.println("Dealer stands.");
		}

		int dealerPoints = BlackjackRules.countPoints(dealerCards);
		int playerPoints = BlackjackRules.countPoints(playerCards);
		System.out.println("==========\nDealer has: " + dealerPoints);
		System.out.println("Player has: " + playerPoints);
		
		// figure out winner - this is not correct for all cases, but it is close enough to test with		
		if (dealerPoints > playerPoints && dealerPoints < 22 
				|| playerPoints > 21) {
			System.out.println("Player lost!");
			bank1-=bet1;
		} else if (dealerPoints == 21) {// check dealer blackjack
			if (dealerCards[2]=="0") {
				if (playerPoints == 21 && playerCards[2]=="0") {
					System.out.println("Push game!");
				}
				else {
					System.out.println("Dealer has Blackjack!!!");
					bank1-=bet1;
				}
			} else if (playerPoints == dealerPoints){
				System.out.println("Push game!");
			}
		} else if(playerPoints == 21 && playerCards[2]=="0") { // check player blackjack
			System.out.println("Player has Blackjack!!!");
			bank1+= 1.5*bet1;
		} else if (playerPoints == dealerPoints){
			System.out.println("Push game!");
		} else {
			System.out.println("Player won!");
			bank1+= bet1;
		}
		System.out.println("Player bank = " + bank1);
                	
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
		Random random = new Random();
		for (int i=0; i<shoeSize; i++) {
			int rNum = random.nextInt(13);
			switch (rNum) {
			case 0:
				deck[i] = "K";
				break;
			case 1:
				deck[i] = "A";
				break;
			case 2:
				deck[i] = "2";
				break;
			case 3:
				deck[i] = "3";
				break;
			case 4:
				deck[i] = "4";
				break;
			case 5:
				deck[i] = "5";
				break;
			case 6:
				deck[i] = "6";
				break;
			case 7:
				deck[i] = "7";
				break;
			case 8:
				deck[i] = "8";
				break;
			case 9:
				deck[i] = "9";
				break;
			case 10:
				deck[i] = "10";
				break;
			case 11:
				deck[i] = "J";
				break;
			case 12:
				deck[i] = "Q";
				break;
			}
		}
		return deck;
	}
	
	/**
	 * @param cards - an array of cards to be printed
	 */
	private static void printCurrentHandInfo(String[] cards) {
		
		int totalPoints = BlackjackRules.countPoints(cards);
		System.out.print("\nCurrent Hand\nCards:");
		for (String card : cards) {
			if (!card.equals("0")) {
				System.out.print(" " + card);
			}
		}
		System.out.println("\nPoints: " + totalPoints);
	}
}

