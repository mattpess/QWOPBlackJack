/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project2;
import java.util.Arrays;
/**
 *
 */
public class PlayerChris implements PlayerIf {
    
    private String name;
    
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		// implement your strategy for deciding whether to hit or not
		if(points >= 19) {
			return false;
		}else {
			//honestly this is where i would try to rely on luck
			if(points <= 16) {
				return true;
			//I normally stay on a 17 or above
			}else {
				return false;
			}
		}
	}

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
