/**
 * QWOP Bois
 * 6/11/2019
 * CPSC 470
 * Matthew Pessolano's implementation of the PlayerIf class
 * 
 */
package project2;

import java.util.Arrays;

/**
 *
 */
public class PlayerMatt  implements PlayerIf {

    private String name;
    
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		int dealerPoints = BlackjackRules.getCardPoints(dealerUpCard);
		
		// cannot bust on next hit
		if(points < 12) {
			return true;
		}
		
		// dealer is in a good range and our points are still low
		if(points == 12 && (dealerPoints < 4 || dealerPoints > 6)){
			return true;
		}
		
		// now in range where aces matter; logic splits here
		
		int totalPoints = 0;
		int aces = 0;
		for (String card : playerCards) {
			totalPoints += BlackjackRules.getCardPoints(card);
			if (card.equals("A"))
				aces++;
		}
		while (totalPoints > 21 && aces > 0) {
			totalPoints -= 10;
			aces--;
		}
		
		if(Arrays.stream(playerCards).anyMatch("A"::equals) && aces != 0) {
			
			// if we have an ace we can play more aggressively without losing (assuming ace has not already been turned into a 1)
			if(points < 18) {
				return true;
			}
			
			// only hit if the dealer has a high card showing or an ace
			if(points == 18 && dealerPoints > 8) {
				return true;
			}
			
			// otherwise player will stay
		
		// No aces logic
		} else {
			
			// only hit on high dealer showing
			if(points < 17 && dealerPoints > 6 ){
				return true;
			}
			
			// otherwise you have enough points to be safe to stay
		}

		return false;
	}

	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		
		// Base bet is bank divided by 20, rounded up
		int bet = (int) Math.ceil((double)bank / 20);
		
		// Betting strategy based on card-counting what has previously been shown
		// Lower values are better in the discard, higher values are worse
		
		// bet stays at base value if no cards have been played
		if (playedCards.length != 0){
			int value = 0;
			for(String card : playedCards) {
				if(card == "2")
					value += 1;
				else if(card == "3")
					value += 1;
				else if(card == "4")
					value += 1;
				else if(card == "5")
					value += 1;
				else if(card == "6")
					value += 1;
				else if(card == "10")
					value -= 1;
				else if(card == "J")
					value -= 1;
				else if(card == "Q")
					value -= 1;
				else if(card == "K")
					value -= 1;
				else if(card == "A")
					value -= 1;
				}
				
				// add value to bet
				bet += value;
				
				// if low number of cards remaining, double effect of value
				if(numCardsLeft < playedCards.length) {
					bet += value;
				}
				
				// check bet is not zero or negative
				if(bet <= 0) {
					bet = 1;
				}
			}
		
				
		if (bet>bank)
			bet = bank;
		return bet;
	}

    @Override
    public void setName(String input) {
        this.name = input;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
