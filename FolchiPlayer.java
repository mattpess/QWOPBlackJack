/**
 * CPSC 110
 * Apr 3, 2017
 * I pledge
 * @author Chance Folchi
 */
package project2;

/**
 *
 */
public class FolchiPlayer implements PlayerIf {

	/* (non-Javadoc)
	 * @see project2.PlayerIf#doesPlayerHit(java.lang.String[], java.lang.String)
	 */
	@Override
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {
		int points = BlackjackRules.countPoints(playerCards); 
		 
		// insert your code here
		if (dealerUpCard.equals("8") && points > 12)
			return false;
		if (dealerUpCard.equals("7") && points > 12)
			return false;
		if (dealerUpCard.equals("6") && points > 12)
			return false;
		if (dealerUpCard.equals("5") && points > 12)
			return false;
		if (dealerUpCard.equals("4") && points > 12)
			return false;
		if (dealerUpCard.equals("3") && points > 12)
			return false;
		if (dealerUpCard.equals("2") && points > 12)
			return false;
	else if (points > 12)
		return false;
	else
			return true;

	}

	/* (non-Javadoc)
	 * @see project2.PlayerIf#placeBet(int, java.lang.String[], int)
	 */
	@Override
	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		// insert your code here
		int bet = 15;
		if (bet > bank)
			bet = bank;
	
		return bet;
	}

	@Override
	public void setName(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
	
		

			
	