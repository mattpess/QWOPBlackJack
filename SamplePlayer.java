/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project2;

/**
 *
 */
public class SamplePlayer implements PlayerIf {
        
        private String name = "Sample Player";
    
	/* (non-Javadoc)
	 * @see project2.PlayerIf#doesPlayerHit(java.lang.String[], java.lang.String)
	 */
	@Override
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		// implement your strategy for deciding whether to hit or not

		return false;
	}

	/* (non-Javadoc)
	 * @see project2.PlayerIf#placeBet(int, java.lang.String[])
	 */
	@Override
	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		int bet = 10;
		// change your bet amount here if you wish
		
		
		
		if (bet>bank)
			bet = bank;
		return bet;
	}

        public void setName(String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
        
}
