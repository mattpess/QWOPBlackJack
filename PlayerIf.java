/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project2;

/**
 * Defines the template for each player's strategies on hitting and betting. Each player will "implement"
 * the PlayerIf interface, which allows the casino program to call each player's code.
 */
public interface PlayerIf {

        
    
	/**
	 * Determines if a player hits (takes another card) or stands
	 * @param playerCards - an array of cards ("A", "K", "Q", etc.)
	 * @param dealerUpCard - a single value showing the dealer's "up card"
	 * @return true if the player wants to hit, false if the player wants to stand
	 */
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard);
	
	/**
	 * Determines a player's bet for the hand
	 * @param bank - the amount the player has banked. The bet cannot exceed this amount.
	 * @param playedCards - an array of cards that have been played from the current "shoe"
	 * @param numCardsLeft - the number of cards that remain in the current "shoe"
	 * @return the amount of the bet
	 */
	public int placeBet(int bank, String[] playedCards, int numCardsLeft);

    public void setName(String input);
    public String getName();

}
