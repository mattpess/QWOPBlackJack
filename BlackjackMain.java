/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project2;

import java.util.Scanner;

import project2.PlayerIf;

import java.util.ArrayList;

/** 
*
 * @Update: 2150 12JUNE
 */
public class BlackjackMain {
    
    //Delicious global variables
    private static int playerNum;
    private static BlackjackEngine engineState;
    private static ArrayList<PlayerIf> players;
    private static int[] playerBank;
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        Scanner scanner = new Scanner(System.in);
    	
        //Instantiates the main object
        BlackjackMain main = new BlackjackMain();
        System.out.println("Choose from the following options: \n1. Play locally.\n2. Set up an online server for others to connect to.");
        if(scanner.nextLine().equals("2")) {
        	BlackjackOnline server = new BlackjackOnline();
        	main.setUpOnline(server);
        	engineState = BlackjackEngine.getInstance(players, playerBank, server);
            main.printRules();
            main.run();
        } else {
        	main.setUp();
            engineState = BlackjackEngine.getInstance(players, playerBank);
            main.printRules();
            main.run();
        }
    }
    
    
    private void setUpOnline(BlackjackOnline server) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    	 players = new ArrayList<>();
         
    	 server.write("How many players would you like?");
         
         // Instead of using nextInt(), since that messes with the following nextLine, we will just convert from string to int. 
         playerNum = Integer.parseInt(server.read());
         
         playerBank = new int[playerNum];
         for (int i = 0; i < playerNum; i++) {
        	 server.write("What is Player " + (i + 1) + "'s name? Current options are Folchi, Matt, and Justin.");
             
             // If-chain statement that handles all types of players
             // Reflection portion of code
             // The "program2." is tacked onto any valid input so reflection works
             
             String input = server.read();
             
             try {
                 PlayerIf player = (PlayerIf) Class.forName("project2.Player" + input).newInstance();
                 player.setName(input);
                 players.add(player);
                 } catch (InstantiationException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				} catch (IllegalAccessException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				} catch (ClassNotFoundException e) {
 					PlayerIf player = (PlayerIf) Class.forName("project2.SamplePlayer").newInstance();
                                        player.setName(input);
                                        players.add(player);
 				}
             playerBank[i] = 100;
         }
         
		
	}


	//Play game stuff
    public void run() {
        engineState.playGame();
    }
    
    //Sets up number of players, banks, and creates the players
    public void setUp() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    	
        players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("How many players?");
        
        // Instead of using nextInt(), since that messes with the following nextLine, we will just convert from string to int. 
        playerNum = Integer.parseInt(scanner.nextLine());
        
        playerBank = new int[playerNum];
        for (int i = 0; i < playerNum; i++) {
            System.out.println("What is Player " + (i + 1) + "'s name?");
            System.out.println("Current options are Folchi, Matt, Justin, and Chris.");
            
            // If-chain statement that handles all types of players
            // Reflection portion of code
            // The "program2." is tacked onto any valid input so reflection works
            
            String input = scanner.nextLine();
             
            try {
                 PlayerIf player = (PlayerIf) Class.forName("project2.Player" + input).newInstance();
                 player.setName(input);
                 players.add(player);
                 } catch (InstantiationException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				} catch (IllegalAccessException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				} catch (ClassNotFoundException e) {
 					PlayerIf player = (PlayerIf) Class.forName("project2.SamplePlayer").newInstance();
                                        player.setName(input);
                                        players.add(player);
 				}
             
             playerBank[i] = 100;
         }
        
        scanner.close();
    }
    
    
    public void printRules() {
    	
        System.out.println("\n\nYour goal, along with other players, is to get a higher value than the dealer, without going above 21.");
        System.out.println("The value of each card is based on the number shown. Face cards count as 10, and aces can be worth 11 or 1, which can be changed at any time.");
        System.out.println("Players choose on their turn to keep hitting, which is to get another card, or to stay, which is to stop at the current value they have.");
        System.out.println("Afterwards, the dealer does the same, except that they follow the strict rule that they continue to hit until they are above 16.");
        System.out.println("At the end, values are compared between the player and the dealer to see if they double their bet, lose it, or 'push' if the values are even.");
        System.out.println("If the dealer gets a Blackjack (21 points with their starting two cards), all player automatically lose unless they also have a Blackjack, in which case they push.");
        System.out.println("In all other cases, players getting a Blackjack receive x1.5 their initial bet.\n\n");
        
    }
    
}