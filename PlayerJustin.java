/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project.old;

/**
 *
 */
public class PlayerJustin {

	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		return (points < 16);
	}

	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		int bet = 10;
		// change your bet amount here if you wish
				
		if (bet>bank)
			bet = bank;
		return bet;
	}

}