
package project.old;

public class BlackjackRules {

	/**
	 * @param cards- an array of cards ("A", "K", etc. where "0" is an empty slot with no card)
	 * @return the total points in the hand, where Aces are converted to 1 point only when necessary to
	 * keep the total under 22
	 */
	public static int countPoints(String[] cards) {
		// loop through and convert cards
		int totalPoints = 0;
		int aces = 0;
		for (String card : cards) {
			totalPoints += getCardPoints(card);
			if (card.equals("A"))
				aces++;
		}
		while (totalPoints > 21 && aces > 0) {
			totalPoints -= 10;
			aces--;
		}
		return totalPoints;
	}

	/**
	 * @param dealerCards - an array of cards ("A", "K", etc. where "0" is an empty slot with no card)
	 * @return true if the dealer wants to hit, false if the dealer wants to stand
	 */
	public static boolean doesDealerHit(String[] dealerCards) {
		int totalPoints = countPoints(dealerCards);
		return (totalPoints < 17);
	}

	/**
	 * @param card - a single card ("A", "K", etc. where "0" is an empty slot with no card)
	 * @return the number of points that card is worth, where Aces are always considered to be 11 points
	 */
	public static int getCardPoints(String card) {
		int points = 0;
		switch (card) {
		case "A":
			points = 11; break;
		case "K": case "Q": case "J": case "10":
			points = 10; break;
		case "9":
			points = 9; break;
		case "8":
			points = 8; break;
		case "7":
			points = 7; break;
		case "6":
			points = 6; break;
		case "5":
			points = 5; break;
		case "4":
			points = 4; break;
		case "3":
			points = 3; break;
		case "2":
			points = 2; break;
		}
		return points;
	}

}
