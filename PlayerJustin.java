
package project2;

/**
 *
 * @author Justin
 */
public class PlayerJustin implements PlayerIf {
    
    private String name;
    
    @Override
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		return (points < 16);

	}


	@Override
	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		int bet = 10;
		// change your bet amount here if you wish
		
		
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
